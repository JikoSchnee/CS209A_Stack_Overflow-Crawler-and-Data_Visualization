<template>
    <div ref="echartsContainer" style="width: 100%; height: 400px;"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    props: {
        chartData: {
            type: Array,
            required: true,
        },
    },
    mounted() {
        // 基于准备好的 dom，初始化 echarts 实例
        const myChart = echarts.init(this.$refs.echartsContainer);

        // 使用传递的数据更新图表
        myChart.setOption({
            series: [
                {
                    type: 'pie',
                    data: this.chartData
                }
            ]
        });
    },
};
</script>

<style scoped>
/* 样式可以根据需要进行定义 */
</style>
