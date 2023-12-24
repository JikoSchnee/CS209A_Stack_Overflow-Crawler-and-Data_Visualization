<template>
    <div ref="echartsContainer" style="width: 100%; height: 400px;"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    props: {
        chartData: {
            type: Object,
            required: true,
        },
    },
    mounted() {
        // 基于准备好的 dom，初始化 echarts 实例
        const myChart = echarts.init(this.$refs.echartsContainer);

        // 使用传递的数据更新图表
        myChart.setOption({
            title: {
                text: this.chartData.title || 'ECharts 示例'
            },
            xAxis: {
                data: this.chartData.categories || []
            },
            yAxis: {},
            series: [
                {
                    type: 'bar',
                    data: this.chartData.seriesData1 || [],
                    barWidth: '2%',
                    barCategoryGap: '10%'
                },
                {
                    type: 'bar',
                    data: this.chartData.seriesData2 || [],
                    barWidth: '2%',
                    barCategoryGap: '10%'
                },
                {
                    type: 'bar',
                    data: this.chartData.seriesData3 || [],
                    barWidth: '2%',
                    barCategoryGap: '10%'
                }
            ]
        });
    },
};
</script>

<style scoped>
/* 样式可以根据需要进行定义 */
</style>
