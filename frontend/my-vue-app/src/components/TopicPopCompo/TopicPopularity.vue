<template>
    <div>
        <el-row :gutter="20" class="el-row">
            <el-col ><div class="grid-content">
<!--                <h2>Topic Popularity</h2>-->
                <div>
                    <div class="chart-row">
                        <div class="chart-container">
                            <div class="chart-title">Compared By Question Number</div>
                            <div ref="echartsContainer" class="chart"></div>
                        </div>
                        <div class="chart-container">
                            <div class="chart-title">Compared By Answer Number</div>
                            <div ref="echartsContainer2" class="chart"></div>
                        </div>
                    </div>
                    <div class="chart-row">
                        <div class="chart-container">
                            <div class="chart-title">Compared By View</div>
                            <div ref="echartsContainer3" class="chart"></div>
                        </div>
                        <div class="chart-container">
                            <div class="chart-title">Compared Comprehensively</div>
                            <div ref="echartsContainer4" class="chart"></div>
                        </div>
                    </div>
                </div>
            </div></el-col>
        </el-row>
    </div>
</template>

<script>

import {defineComponent} from "vue";
// import PieChart from "@/components/Charts/PieChart.vue";
import * as echarts from 'echarts';

export default defineComponent({
    // components: {PieChart},
    data() {
        return {
            data1: [],
            data2: [],
            data3: [],
            data4: []
        }
    },
    mounted() {
        this.updateData();
    },
    methods: {
        updateData() {
            console.log('update')
            if (this.myChart) {
                this.myChart.dispose();
            }
            if (this.myChart2) {
                this.myChart2.dispose();
            }
            if (this.myChart3) {
                this.myChart3.dispose();
            }
            if (this.myChart4) {
                this.myChart4.dispose();
            }
            this.request.get('/api/tag/popularity/ten')
                .then(data => {
                    console.log(data);
                    this.data1 = data.map(item => ({
                        value: item.totalQuestionCount,
                        name: item.topic,
                    }));
                    this.data2 = data.map(item => ({
                        value: item.totalAnswerCount,
                        name: item.topic,
                    }));
                    this.data3 = data.map(item => ({
                        value: item.totalViewCount,
                        name: item.topic,
                    }));
                    this.data4 = data.map(item => ({
                        value: item.popularity,
                        name: item.topic,
                    }));
                    this.$nextTick(() => {
                        this.myChart = echarts.init(this.$refs.echartsContainer);
                        this.myChart2 = echarts.init(this.$refs.echartsContainer2);
                        this.myChart3 = echarts.init(this.$refs.echartsContainer3);
                        this.myChart4 = echarts.init(this.$refs.echartsContainer4);

                        const myChart = echarts.init(this.$refs.echartsContainer);
                        myChart.setOption({
                            tooltip: {
                                trigger: 'item'
                            },
                            legend: {
                                top: '5%',
                                left: 'center'
                            },
                            series: [
                                {
                                    name: 'Compared By Question Number',
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
                                    data: this.data1
                                }
                            ]
                        });
                        const myChart2 = echarts.init(this.$refs.echartsContainer2);
                        myChart2.setOption({
                            tooltip: {
                                trigger: 'item'
                            },
                            legend: {
                                top: '5%',
                                left: 'center'
                            },
                            series: [
                                {
                                    name: 'Compared By Answer Number',
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
                                    data: this.data2
                                }
                            ]
                        });
                        const myChart3 = echarts.init(this.$refs.echartsContainer3);
                        myChart3.setOption({
                            tooltip: {
                                trigger: 'item'
                            },
                            legend: {
                                top: '5%',
                                left: 'center'
                            },
                            series: [
                                {
                                    name: 'Compared By View',
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
                                    data: this.data3
                                }
                            ]
                        });
                        const myChart4 = echarts.init(this.$refs.echartsContainer4);
                        myChart4.setOption({
                            tooltip: {
                                trigger: 'item'
                            },
                            legend: {
                                top: '5%',
                                left: 'center'
                            },
                            series: [
                                {
                                    name: 'Compared Comprehensively',
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
                                    data: this.data4
                                }
                            ]
                        });
                    });

                    console.log(this.data1)
                })
        }
    }
})


</script>

<style scoped>
.chart-row {
    display: flex;
}

.chart-container {
    box-sizing: border-box;
    flex: 1;
    margin: 0 5px;

}

.chart {
    width: 100%;
    height: 400px;
    background: #eceff3;
    border-radius: 10px;
}

.chart-title {
    text-align: center;
    font-weight: bold;
    margin-bottom: 10px;
}

.el-row {
    margin-bottom: 20px;

    &:last-child {
        margin-bottom: 0;
    }
}
.el-col {
    border-radius: 4px;
}
.bg-purple-dark {
    background: #99a9bf;
}
.bg-purple {
    background: #d3dce6;
}
.bg-purple-light {
    background: #e5e9f2;
}
.bg-brown {
    background: #6e260e;
}
.bg-orange-light {
    background: #e1c16e;
}
.bg-orange {
    background: #cd7f32;
}
.grid-content {
    border-radius: 4px;
    min-height: 36px;
}
.row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
}
</style>
