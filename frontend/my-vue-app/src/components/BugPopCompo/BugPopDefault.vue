<template>
    <div>
        <!--        <h2>Bug Popularity</h2>-->
        <el-row>
            <!-- el-transfer -->
            <el-col :span="12" >

                <el-transfer v-model="value" :data="data" :titles="['Categories', 'Chosen']" class="transfer">
                    <!-- Add margin to the label inside the transfer component -->
                    <template slot="left-footer" slot-scope="{ data }">
                        <div v-for="item in data" :key="item.key" class="transfer-item">
                            <el-checkbox :label="item.key" class="checkbox">{{ item.label }}</el-checkbox>
                        </div>
                    </template>
                </el-transfer>
            </el-col>
            <!-- Chart container -->
            <el-col :span="12">
                <div class="chart-container">
                    <div ref="echartsContainer" class="chart"></div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import * as echarts from "echarts";

export default {
    data() {
        this.words = [
            'Tick and Move',
            'Sample1',
            'Sample2'
        ];
        this.keyword = [
            'IndentationError'
        ]
        const generateData = () => {
            const data = [];
            for (let i = 1; i <= this.words.length; i++) {
                data.push({
                    key: i,
                    label: this.words[i - 1],
                    disabled: i % 1 === 0,
                });
            }
            return data;
        };

        return {
            data: generateData(),
            value: [2,3],
            pieChartData : []
        };
    },
    mounted() {
        this.generateChart();
    },
    methods: {
        generateChart() {
            console.log('generate pie chart')
            // console.log(this.value)
            if (this.myChart) {
                this.myChart.dispose();
            }
            this.pieChartData = [];
            const requests = this.value.map((val) => {
                const str = this.keyword[val - 1];
                return this.request.get('/api/question/count/keyword/' + str)
                    .then(count => {
                        // console.log('------this.value------');
                        // console.log(val);
                        // console.log(this.words[val - 1]);
                        // console.log(count);
                        this.pieChartData.push({ value: count, name: this.words[val - 1] });
                    });
            });

            Promise.all(requests)
                .then(() => {
                    this.$nextTick(() => {
                        this.myChart = echarts.init(this.$refs.echartsContainer);
                        this.myChart.setOption({
                            tooltip: {
                                trigger: 'item'
                            },
                            legend: {
                                top: '5%',
                                left: 'center'
                            },
                            series: [
                                {
                                    name: 'Bug Popularity',
                                    type: 'pie',
                                    radius: ['40%', '70%'],
                                    avoidLabelOverlap: false,
                                    itemStyle: {
                                        borderRadius: 6,
                                        borderColor: '#e5e9f2',
                                        borderWidth: 3
                                    },
                                    label: {
                                        show: false,
                                        position: 'center'
                                    },
                                    emphasis: {
                                        label: {
                                            show: true,
                                            fontSize: 40,
                                            fontWeight: 'bold'
                                        }
                                    },
                                    labelLine: {
                                        show: false
                                    },
                                    data: this.pieChartData
                                }
                            ]
                        });
                    });
                });
        }
    }
};
</script>

<style scoped>
h2 {
    margin-bottom: 20px;
}

.el-row {
    margin-bottom: 20px;
}

.transfer-item {
    margin-bottom: 8px; /* Adjust the margin as needed */
}

.checkbox {
    margin-left: 0
}

.transfer {
    text-align: left;
}

.chart-container {
    box-sizing: border-box;
    flex: 1;
    margin: 0 5px;

}

.chart {
    width: 100%;
    height: 300px;
    background: #eceff3;
    border-radius: 10px;
}
</style>
/* Add more styling

