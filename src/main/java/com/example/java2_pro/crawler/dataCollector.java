package com.example.java2_pro.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class dataCollector {
    private String key;
    private int totalNumber; // 获取的数据条数
    private int pageSize; // 分页获取数据时每页的大小 [1, 100]
    private int pageStep; // 分页获取数据时每次获取所隔的页数
    private int tagPageSize = 100; // 获取相关标签的条数
    private List<JSONObject> questionList;    // 获取问题JSON列表
    private List<String> tagList;                      // 在数据库中获取Tag列表
    private List<JSONObject> relatedTagList;        // 从网页中获取的相关Tag列表
    private int totalQuestions; // 当前StackOverflow上的问题总数（用来衡量数据爬取的普适性）
    private int NoAnsQuestionTotal; // 当前StackOverflow上的无回答问题总数（计算比例，用来衡量数据爬取的普适性）
    private final databaseService databaseService; // 数据库服务
    private Timestamp lastRefreshTime; // 上次刷新时间
    public Timestamp getLastRefreshTime() {  // 获取上次刷新时间
        return lastRefreshTime;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    } // 设置每页大小
    public void setPageStep(int pageStep) {
        this.pageStep = pageStep;
    }
    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public dataCollector (databaseService databaseService, int pageSize, int pageStep, int totalNumber, String key) throws IOException {
        // 自定义每页大小和每次获取所隔的页数
        this.databaseService = databaseService;
        questionList = new ArrayList<>();
//        answerList = new ArrayList<>();
//        commentList = new ArrayList<>();
        this.pageSize = pageSize;
        this.pageStep = pageStep;
        this.totalNumber = totalNumber;
        this.key = key;
        refresh();
    }
    public Connection connect() {
        try {
            // 加载PostgreSQL JDBC驱动
            Class.forName("org.postgresql.Driver");

            // 建立数据库连接
            String url = String.format("jdbc:postgresql://%s:%s/%s",this.databaseService.getHost(),this.databaseService.getPort(),this.databaseService.getDatabase());
//            String url = "jdbc:postgresql://localhost:5432/stackoverflow_db";
            String user = this.databaseService.getUser();
//            String user = "checker";
            String password = this.databaseService.getPassword();
//            String password = "123456";
            Connection connection = DriverManager.getConnection(url, user, password);

            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<String> getTagFromDatabase() throws SQLException {
        // 连接数据库
        ArrayList<String> resultList = new ArrayList<>();
        Connection connection = connect();
        String columnName = "tag_name";
        String tableName = "tag";
        String sql = "SELECT "+columnName+" FROM "+tableName;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String data = resultSet.getString(columnName);
            resultList.add(data);
        }
        connection.close();
        return resultList;
    }
    public void refresh() throws IOException {
        // 刷新数据，主要更新问题总数和无回答问题总数
        String url = "https://api.stackexchange.com/2.3/questions";
        String params = "filter=total&tagged=java&site=stackoverflow&key=gqjiH6ExBbic7NaMoFxC)w((";
        String apiURL = url + "?" + params;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(apiURL);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String responseBody = EntityUtils.toString(entity);
            JSONObject data = JSON.parseObject(responseBody);
            totalQuestions = data.getInteger("total");
        }
        response.close();
        httpClient.close();
        url = "https://api.stackexchange.com/2.3/questions/no-answers";
        params = "filter=total&tagged=java&site=stackoverflow&key=gqjiH6ExBbic7NaMoFxC)w((";
        apiURL = url + "?" + params;
        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(apiURL);
        response = httpClient.execute(httpGet);
        entity = response.getEntity();
        if (entity != null) {
            String responseBody = EntityUtils.toString(entity);
            JSONObject data = JSON.parseObject(responseBody);
            NoAnsQuestionTotal = data.getInteger("total");
        }
        response.close();
        httpClient.close();
        lastRefreshTime = new Timestamp(System.currentTimeMillis());
    }
    public int getTotalQuestions() throws IOException {
        // 获取stackOverflow上准确的问题总数
        return totalQuestions;
    }
    public int getNoAnsQuestionTotal() throws IOException {
        // 获取stackOverflow上准确的无回答问题总数
        return NoAnsQuestionTotal;
    }
    private static void info(String message) {
        //System out message with formatted time prefix
        System.out.printf("[%s] %s%n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), message);
    }
    public void collectData() throws IOException, SQLException {
        // 提示字体的颜色
        String purpleBoldColorCode = "\u001B[1;35m";
        String redColorCode = "\u001B[31m";
        String resetColorCode = "\u001B[0m";
        // 数据爬取
        int pageTotalWeb = totalQuestions / pageSize;   // 网站上的总页数
        int pageTotal = totalNumber / pageSize;         // 达到数据量所需的总页数
        System.out.println(pageTotal);
        pageTotal *= pageStep;
//        pageTotal += 1;                                 // 达到数据量最终到达的页
        System.out.println(pageTotal);
        if (pageTotal > pageTotalWeb){
            System.out.println(redColorCode+"终止页超出总页数"+resetColorCode);
            throw new RuntimeException();
        }

        //获取提问
        int sum = 0;
        for (int i = 1;i<=pageTotal;i+=pageStep) {
            // 按照activity排序，获取每隔pageStep页的pageSize个问题
            info(purpleBoldColorCode + "获取所有提问数据ing: " + (int)(100 * ((double)i / pageTotal)) + "%" + resetColorCode);
            String url = "https://api.stackexchange.com/2.3/questions";
            String params = String.format("page=%d&pagesize=%d&order=desc&sort=activity&tagged=java&site=stackoverflow&filter=!6WPIomo1fLW)M&key=%s", i, pageSize, key);
            String apiURL = url + "?" + params;
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(apiURL);                          // 设定请求
            CloseableHttpResponse response = httpClient.execute(httpGet);   // 发送请求并接收回复
            HttpEntity entity = response.getEntity();                       // 拿到回复中的实体
            if (entity != null){
                String responseBody = EntityUtils.toString(entity);
                JSONObject data = JSON.parseObject(responseBody);
                if (data.getJSONArray("items") == null) continue;
                for (int j = 0;j< pageSize;j++){
                    JSONObject item = data.getJSONArray("items").getJSONObject(j);
                    questionList.add(item);
                }
            }
            response.close();
            httpClient.close();

            info(redColorCode + "该轮爬取Question数: " + questionList.size()+resetColorCode);
            sum+=questionList.size();
            // 将问题插入数据库
            info(redColorCode + "插入该轮所有提问数据" + resetColorCode);
            int temp = questionList.size();
            for (int j = 0;j<temp;j++){
                info(purpleBoldColorCode + (int)(100*(double) j/questionList.size())+"%" +resetColorCode);
                JSONObject questionItem = questionList.get(j);
                databaseService.insertQuestionRecord(questionItem);
            }
            questionList = new ArrayList<>();
            info(redColorCode + "插入该轮提问数据完成" + resetColorCode);
        }
        info(redColorCode + "///////////////////////////////////////////" + resetColorCode);
        info(redColorCode + "插入共"+sum+"条问题数据" + resetColorCode);
        info(redColorCode + "///////////////////////////////////////////" + resetColorCode);

        // 将tag插入数据库
        tagList = getTagFromDatabase();
        int cnt_temp = 0;
        int total_temp = tagList.size();
        for (String tag: tagList){
            cnt_temp++;
            info(purpleBoldColorCode + "获取"+tag+"相关标签数据ing: " + (int)(100 * ((double)cnt_temp / total_temp)) + "%" + resetColorCode);
            String url = String.format("https://api.stackexchange.com/2.3/tags/%s/related",tag);
            String params = String.format("page=%d&pagesize=%d&site=stackoverflow&key=%s",1,tagPageSize,key);
            String apiURL = url + "?" + params;
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(apiURL);                          // 设定请求
            CloseableHttpResponse response = httpClient.execute(httpGet);   // 发送请求并接收回复
            HttpEntity entity = response.getEntity();                       // 拿到回复中的实体
            if (entity != null){
                String responseBody = EntityUtils.toString(entity);
                JSONObject data = JSON.parseObject(responseBody);
                if (data.getJSONArray("items") == null) continue;
//                JSONObject test= data.getJSONObject("items");
//                JSONArray test1 = data.getJSONArray("")
                relatedTagList = new ArrayList<>();
                for (int j = 0;j< data.getJSONArray("items").size();j++){
                    JSONObject item = data.getJSONArray("items").getJSONObject(j);
                    relatedTagList.add(item);
                }
            }
            response.close();
            httpClient.close();
            // 插入标签数据
            info(redColorCode + "插入"+tag+"相关标签数据" + resetColorCode);
            databaseService.insertRelatedTag(relatedTagList);

        }
    }
}
