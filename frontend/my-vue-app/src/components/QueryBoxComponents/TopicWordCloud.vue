<template>
    <div>

        <div ref="wordCloudChart" style="width: 100%; height: 400px;"></div>
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
                <div>
                    <el-button type="primary" plain @click="queryWordCloud">query</el-button>
                </div>
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
                // { name: 'React', value: 80 },
                // { name: 'JavaScript', value: 70 },
            ],
            cnt: 200
        };
    },
    mounted() {
        this.drawWordCloud();
    },
    methods: {
        async queryWordCloud() {
            try {
                let str = '/api/tag/relevant/' + this.input + '/50'
                this.request.get(str)
                    .then(tagsArray => {
                        console.log(tagsArray)
                        // Convert the array of strings into an array of objects with name and value properties
                        const wordCloudData = tagsArray.map(tag => ({
                            name: tag,
                            // value: this.cnt-=3,
                            value: Math.floor(Math.random() * 100) + 1, // You can replace this with actual values
                        }));

                        // Update this.wordCloudData with the converted data
                        this.wordCloudData = wordCloudData;

                        // Redraw the word cloud with the updated data
                        this.drawWordCloud();
                    })
            } catch (error) {
                console.error('Error fetching data:', error);
                // Handle error appropriately (e.g., show an error message to the user)
            }
        },
        drawWordCloud() {
            const wordCloudChart = this.$refs.wordCloudChart;
            const myChart = echarts.getInstanceByDom(wordCloudChart);

            // Dispose of the existing chart instance if it exists
            if (myChart) {
                myChart.dispose();
            }

            // Initialize a new chart instance
            const newChart = echarts.init(wordCloudChart);

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
                                    return (
                                        'rgb(' +
                                        Math.round(Math.random() * 255) +
                                        ', ' +
                                        Math.round(Math.random() * 255) +
                                        ', ' +
                                        Math.round(Math.random() * 255) +
                                        ')'
                                    );
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
