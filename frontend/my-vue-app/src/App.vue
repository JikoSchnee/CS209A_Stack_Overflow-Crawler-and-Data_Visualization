<template>
    <div id="app">
        <testTest />
        <query-box />
        <TopicPopularity></TopicPopularity>
        <BugPopularity></BugPopularity>
        <BarChart :chartData="barChartData1" />
        <BarChart :chartData="barChartData2" />
    </div>
</template>

<script>
import testTest from "@/components/testTest.vue";
import BarChart from "@/components/Charts/BarChart.vue";
import QueryBox from "@/components/QueryBox.vue";
import TopicPopularity from "@/components/QueryBoxComponents/TopicPopularity.vue";
import BugPopularity from "@/components/QueryBoxComponents/BugPopularity.vue";

export default {
    name: 'App',
    components: {
        TopicPopularity,
        BugPopularity,
        QueryBox,
        testTest,
        BarChart,
    },
    data() {
        return {
            barChartData1: {
                title: '图表1',
                categories: ['A', 'B', 'C', 'D', 'E'],
                seriesName: '销量',
                seriesData2: [10, 20, 15, 25, 30],
            },
            barChartData2: {
                title: '图表2',
                categories: ['X', 'Y', 'Z'],
                seriesName: '销量',
                seriesData2: [5, 15, 10],
            },
        };
    },
};
</script>

<style>
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
}
</style>
