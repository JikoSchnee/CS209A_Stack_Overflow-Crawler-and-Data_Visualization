<template>
    <div>
        <el-button type="text" style="width: auto" @click="dialogChange">Query</el-button>
        <el-button type="text" style="width: auto" @click="updateChartData">Update</el-button>
        <el-dialog
            title="Chart"
            :visible.sync="dialogVisible"
            width="70%"
            :before-close="handleClose">
            <span>这是一段信息</span>
            <span>
                <BarChart :chartData="barChartData"></BarChart>
            </span>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>


<script>
import BarChart from "@/components/Charts/BarChart.vue";

export default {
    components: {
        BarChart
    },
    props: {
        input: String,
        type: String
    },
    data() {
        return {
            dialogVisible: false,
            barChartData: {
                title: '图表1',
                categories: ['A', 'B', 'C', 'D', 'F'],
                seriesName: '销量',
                seriesData1: [10, 20, 15, 25, 30],
                seriesData2: [10, 10, 15, 10, 5],
                seriesData3: [2, 3, 4, 5, 6]
            }
        };
    },
    methods: {
        dialogChange() {
            this.updateChartData();
            this.dialogVisible = true;

        },
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(() => {
                    done();
                })
                .catch(() => {});
        },
        updateChartData() {
            // if (this.type === "TenTagPop"){
                // const apiUrl = `/api/tag/popularity/${this.input}`;
                // // 发起 API 请求
                // this.request.get(apiUrl).then(res => {
                //     console.log(res);
                // });
                const apiUrl = '/api/tag/popularity/ten'
                this.request.get(apiUrl)
                    .then(data => {
                        console.log(data);
                        // Process the data and update the chart
                        const topics = [];
                        const totalQuestionCounts = [];
                        const totalAnswerCounts = [];
                        const totalViewCounts = [];
                        const popularities = [];

                        data.forEach(item => {
                            topics.push(item.topic);
                            totalQuestionCounts.push(item.totalQuestionCount);
                            totalAnswerCounts.push(item.totalAnswerCount);
                            totalViewCounts.push(item.totalViewCount);
                            popularities.push(item.popularity);
                        });

                        // this.barChartData.title = this.type;
                        this.barChartData.categories = topics;
                        this.barChartData.seriesData1 = totalQuestionCounts;
                        this.barChartData.seriesData2 = totalAnswerCounts;
                        this.barChartData.seriesData3 = totalViewCounts;

                        return Promise.resolve();
                    })
                    .catch(error => {
                        console.error('Error fetching data:', error);
                        return Promise.reject();
                    });

            // }
            // else if (this.type === "BugBetweenCategories"){
            //
            // }else if (this.type === "BugWithinCategory"){
            //
            // }

        },
    }
};
</script>
