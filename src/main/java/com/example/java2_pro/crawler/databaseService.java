package com.example.java2_pro.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class databaseService {
    private Connection connection; // 数据库连接
    private final String host; // 数据库主机
    private final int port; // 数据库端口
    private final String user; // 数据库用户名
    private final String password; // 数据库密码
    private final String database; // 数据库名

    private final StanfordCoreNLPService stanfordCoreNLPService;

    public databaseService(String host, int port, String user, String password, String database) {
        // 初始化数据库连接配置
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
        connection = null;
        stanfordCoreNLPService = new StanfordCoreNLPService();
    }

    public boolean connect(){
        // 连接数据库
        try {
            connection = java.sql.DriverManager.getConnection(
                "jdbc:postgresql://" + host + ":" + port + "/" + database,
                user,
                password
//                "jdbc:postgresql://localhost:5432/postgres"
            );
            return true;
        } catch (java.sql.SQLException e) {
            System.out.println("Cannot connect to the database.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean isConnected() {
        return connection != null;
    } // 判断是否连接到数据库

    public void close() {
        // 关闭数据库连接
        try {
            connection.close();
        } catch (java.sql.SQLException e) {
            System.out.println("Cannot close the connection.");
            e.printStackTrace();
        }
    }

    private PreparedStatement prepareStatement(String sql) {
        // 准备 SQL 语句
        try {
            return connection.prepareStatement(sql);
        } catch (java.sql.SQLException e) {
            System.out.println("Cannot prepare the statement.");
            e.printStackTrace();
            return null;
        }
    }

    public void createTable() throws SQLException {
        // 创建数据库表
        Statement statement = this.connection.createStatement();
        statement.executeUpdate(
            "DROP TABLE IF EXISTS question;\n" +
                "DROP TABLE IF EXISTS tag;\n" +
                "DROP TABLE IF EXISTS connection_tag_and_question;\n" +
                "DROP TABLE IF EXISTS connection_tag_and_tag;\n" +
                "CREATE TABLE question(\n" +
                "   question_id int primary key ,\n" +
                "   answer_count int not null ,\n" +
                "   view_count int not null ,\n" +
                "   question_title text not null ,\n" +
                "   question_body text not null ,\n" +
                "   is_answered boolean not null ,\n" +
                "   favorite_count int not null ,\n" +
                "   last_activity_date timestamp not null ,\n" +
                "   creation_date timestamp not null \n" +
                ");\n" +
                "CREATE TABLE tag(\n" +
                "   tag_name text primary key \n" +
                ");\n" +
                "CREATE TABLE connection_tag_and_question(\n" +
                "   tag text not null ,\n" +
                "   question_id int not null\n" +
                ");\n" +
                "CREATE TABLE connection_tag_and_tag(\n" +
                "   related_tag text not null ,\n" +
                "   tag text not null\n" +
                ");"
        );
    }

    public void disableForeignKeyCheck() throws SQLException {
        // 关闭外键约束
        Statement statement = connection.createStatement();
        statement.execute("SET session_replication_role = replica;");
        statement.close();
    }

    public void enableForeignKeyCheck() throws SQLException {
        // 开启外键约束
        Statement statement = connection.createStatement();
        statement.execute("SET session_replication_role = DEFAULT;");
        statement.close();
    }

    private void insertQuestion(int question_id, int answer_count, int view_count, String question_title,
                               String question_body, boolean is_answered, int favorite_count,
                               Timestamp last_activity_date, Timestamp creation_date)
        throws SQLException{
        // 插入question表
        PreparedStatement statement = this.prepareStatement(
            "insert into question values (?,?,?,?,?,?,?,?,?);"
        );
        statement.setInt(1,question_id);
        statement.setInt(2,answer_count);
        statement.setInt(3,view_count);
        statement.setString(4,question_title);
        statement.setString(5,question_body);
        statement.setBoolean(6,is_answered);
        statement.setInt(7,favorite_count);
        statement.setTimestamp(8,last_activity_date);
        statement.setTimestamp(9,creation_date);
        statement.executeUpdate();
    }

    private void insertTag(String tag_name) throws SQLException{
        // 插入Tag表
        PreparedStatement statement = this.prepareStatement(
            "select count(*) from tag where tag_name = ?"
        );
        statement.setString(1,tag_name);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        if (resultSet.getInt(1) > 0) {
            // 插入过就跳过
            return;
        }else {
            statement = this.prepareStatement(
                "insert into tag values (?)"
            );
            statement.setString(1,tag_name);
            statement.executeUpdate();
        }
    }

    public void insertConnectionTagAndQuestion(String tag_name, int question_id) throws SQLException{
        // 插入tag_question表
        PreparedStatement statement = this.prepareStatement(
            "insert into connection_tag_and_question values (?,?)"
        );
        statement.setString(1,tag_name);
        statement.setInt(2,question_id);
        statement.executeUpdate();
    }

    public void insertConnectionTagAndTag(String related_tag_name, String tag_name) throws SQLException {
        // 插入tag_tag表
        PreparedStatement statement = this.prepareStatement(
            "insert into connection_tag_and_tag values (?,?)"
        );
        statement.setString(1,related_tag_name);
        statement.setString(2,tag_name);
        statement.executeUpdate();
    }

    private Timestamp convertDate(Integer date) {
        // 将Unix时间戳转换为Timestamp
        if (date == null) {
            return null;
        }else {
            return new Timestamp(date * 1000L);
        }
    }

    public void insertQuestionRecord(JSONObject questionJSON) throws SQLException {
        // 将一个问题的JSON数据插入到数据库中
        // 问题
        insertQuestion(
            questionJSON.getInteger("question_id"),
            questionJSON.getInteger("answer_count"),
            questionJSON.getInteger("view_count"),
            questionJSON.getString("title"),
            questionJSON.getString("body"),
            questionJSON.getBoolean("is_answered"),
            questionJSON.getInteger("favorite_count"),
            convertDate(questionJSON.getInteger("last_activity_date")),
            convertDate(questionJSON.getInteger("creation_date"))
        );
        // tag
        for (Object tag : questionJSON.getJSONArray("tags")) {
            insertTag((String) tag);
        }
        for (Object tag : questionJSON.getJSONArray("tags")) {
            insertConnectionTagAndQuestion((String) tag, questionJSON.getInteger("question_id"));
        }
//        Map<String, Integer> apiCount = stanfordCoreNLPService.getAllJavaAPI(
//            questionJSON.getString("body"));
//        for (Map.Entry<String, Integer> entry : apiCount.entrySet()) {
//            insertApi(entry.getKey());
//            insertConnectionQuestionAndApi(questionJSON.getInteger("question_id"), entry.getKey(),
//                entry.getValue());
//        }
    }

    @Deprecated
    public void insertTagRelation(JSONObject relatedTagJSON) throws SQLException {
        // 将相关联的tag插入数据库
        String tag_name = "";
        String related_tag_name = "";
        JSONArray itemsArray = relatedTagJSON.getJSONArray("items");
        for (int i = 0;i<itemsArray.size();i++){
            JSONObject item = (JSONObject) itemsArray.get(i);
            if (i == 0){
                tag_name = item.getString("name");
            }else {
                related_tag_name = item.getString("name");
                insertConnectionTagAndTag(related_tag_name,tag_name);
            }
        }
    }
    public void insertRelatedTag(List<JSONObject> relatedTagList) throws SQLException{
        // 将相关联的tag插入数据库
        String tag_name = relatedTagList.get(0).getString("name");
        String related_tag_name;
        if (relatedTagList.size()<=1){
            return;
        }else {
            for (int i = 1;i<relatedTagList.size();i++){
                related_tag_name = relatedTagList.get(i).getString("name");
                insertConnectionTagAndTag(related_tag_name,tag_name);
            }
        }
    }


}

