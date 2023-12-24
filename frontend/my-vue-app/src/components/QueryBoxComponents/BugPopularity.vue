<template>
    <div>
        <el-row :gutter="20" class="grid-content bg-purple">
            <el-col ><div >
                <h2>Bug Popularity</h2>
                <div>

                </div>
            </div></el-col>
        </el-row>
    </div>
</template>

<script>

import {defineComponent} from "vue";
import * as echarts from 'echarts';

export default defineComponent({
    data() {
        return {

        }
    },
    mounted() {
        this.updateData();
    },
    methods: {
        updateData() {
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
                        const myChart = echarts.init(this.$refs.echartsContainer);
                        myChart.setOption({
                            series: [
                                {
                                    type: 'pie',
                                    data: this.data1
                                }
                            ]
                        });
                        const myChart2 = echarts.init(this.$refs.echartsContainer2);
                        myChart2.setOption({
                            series: [
                                {
                                    type: 'pie',
                                    data: this.data2
                                }
                            ]
                        });
                        const myChart3 = echarts.init(this.$refs.echartsContainer3);
                        myChart3.setOption({
                            series: [
                                {
                                    type: 'pie',
                                    data: this.data3
                                }
                            ]
                        });
                        const myChart4 = echarts.init(this.$refs.echartsContainer4);
                        myChart4.setOption({
                            series: [
                                {
                                    type: 'pie',
                                    data: this.data4
                                }
                            ]
                        });
                        // myChart.resize({
                        //     width: 400
                        // })
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
