<template>
    <div>
<!--        <el-button-group>-->
<!--            <el-button type="primary" plain @click="wordCloudVisible">Word Cloud</el-button>-->
<!--            <el-button type="primary" plain @click="barChartVisible">Bar Chart</el-button>-->
<!--        </el-button-group>-->
        <el-row :gutter="0" justify="center">
            <el-col :span="6" v-if="isWordCloudVisible">
                <div ref="wordCloudChart" style="width: 130%; height: 400px;"></div>
            </el-col>
            <el-col :span="10" v-if="isBarChartVisible" pull="100">
                <div ref="barChart" style="width: 185%; height: 400px;"></div>
            </el-col>
        </el-row>
        <el-row :gutter="20" class="el-row" type="flex" justify="center">
            <el-col :span="6">
                <div>
                    <el-input class="inputBox"
                              placeholder="Input Topic"
                              v-model="input"
                              clearable>

                    </el-input>
                </div>
            </el-col>
            <el-col :span="6">
                <el-button-group>
                    <el-button type="primary" plain @click="queryWordCloud">query</el-button>
                </el-button-group>
            </el-col>
        </el-row>

    </div>
</template>

<script>
import * as echarts from 'echarts';
import 'echarts-wordcloud'; // Make sure to import echarts-wordcloud

export default {
    data() {
        return {
            input: '',
            wordCloudData: [
                { name: 'Input Topic', value: 100 },
                { name: 'And', value: 80 },
                { name: 'Click Query', value: 70 },
            ],
            barChartData: [],
            cnt: 200,
            isWordCloudVisible: true,
            isBarChartVisible: true,
        };
    },
    mounted() {
        this.drawWordCloud();
        this.drawBarChart();
    },
    methods: {
        wordCloudVisible() {
            this.isWordCloudVisible = true
            this.isBarChartVisible = false

        },
        barChartVisible() {
            this.isWordCloudVisible = false
            this.isBarChartVisible = true
        },
        async queryWordCloud() {
            try {
                let str = '/api/relatedTag/' + this.input.replace(/\s+/g, '-').toLowerCase()
                this.request.get(str)
                    .then(data => {
                        console.log(data)
                        // Convert the array of strings into an array of objects with name and value properties
                        if (data.length >=1 ){
                            const maxCount = Math.max(...data.map(item => item.count));
                            this.wordCloudData = data.map(item => ({
                                name: item.relatedTag,
                                value: Math.floor(item.count/maxCount * 500) + 100, // You can replace this with actual values
                            }));
                            this.barChartData = data.map(item => ({
                                name: item.relatedTag,
                                value: item.count
                            }))
                            // Redraw the word cloud with the updated data
                            this.drawWordCloud();
                            this.drawBarChart();
                        }else {
                            console.log("fail")
                        }

                    })
            } catch (error) {
                console.error('Error fetching data:', error);
                // Handle error appropriately (e.g., show an error message to the user)
            }
        },
        drawBarChart() {
            const barChart = this.$refs.barChart;
            this.myChart2 = echarts.getInstanceByDom(barChart);
            // Dispose of the existing chart instance if it exists
            if (this.myChart2) {
                this.myChart2.dispose();
            }
            const newChart = echarts.init(barChart);
            const option = {
                dataset: [
                    {
                        dimensions: ['name', 'value'],
                        source: this.wordCloudData
                    },
                    {
                        transform: {
                            type: 'sort',
                            config: { dimension: 'value', order: 'desc' }
                        }
                    }
                ],
                xAxis: {
                    type: 'category',
                    axisLabel: { interval: 0, rotate: 30 }
                },
                yAxis: {},
                series: {
                    type: 'bar',
                    encode: { x: 'name', y: 'value' },
                    datasetIndex: 1
                },
                dataZoom: [
                    {
                        type: 'inside'
                    }
                ],
            }
            newChart.setOption(option)
            const zoomSize = 6;
            const dataAxis = this.wordCloudData.map(item => item.name); // 根据实际情况修改
            const data = this.barChartData.map(item => item.value); // 根据实际情况修改
            newChart.on('click', function (params) {
                console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
                newChart.dispatchAction({
                    type: 'dataZoom',
                    startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
                    endValue:
                        dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
                });
            });
        },
        drawWordCloud() {
            const wordCloudChart = this.$refs.wordCloudChart;
            this.myChart = echarts.getInstanceByDom(wordCloudChart);

            // Dispose of the existing chart instance if it exists
            if (this.myChart) {
                this.myChart.dispose();
            }

            // Initialize a new chart instance
            const newChart = echarts.init(wordCloudChart);
            const colorList = ['#f89b6299', '#afcf7cde', '#fece5b']
            const option = {
                series: [
                    {
                        type: 'wordCloud',
                        shape: 'circle',
                        gridSize: 30,
                        sizeRange: [20, 60],
                        rotationRange: [-45, 45],
                        textStyle: {
                            normal: {
                                color: function () {
                                    let index = Math.floor(Math.random() * colorList.length)
                                    return colorList[index]
                                },
                            },
                        },
                        data: this.wordCloudData,
                    },
                ],
            };

            newChart.setOption(option);
        },

    },
};
</script>

<style scoped>

.el-row {
    margin-bottom: 20px;

    &:last-child {
        margin-bottom: 0;
    }
}
.el-col {
    border-radius: 4px;
}
.inputBox {
    width: 200px;
}
</style>
