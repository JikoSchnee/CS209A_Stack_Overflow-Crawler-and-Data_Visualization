
<template>
    <div>
      <div ref="echartsContainer" style="width: 100%; height: 400px;"></div>
<!--        <PieChart :chart-data="data1"></PieChart>-->
<!--        <PieChart :chart-data="data2"></PieChart>-->
<!--        <PieChart :chart-data="data3"></PieChart>-->
<!--        <PieChart :chart-data="data4"></PieChart>-->
    </div>
</template>

<script>

import {defineComponent} from "vue";
// import PieChart from "@/components/Charts/PieChart.vue";
import * as echarts from 'echarts';

export default defineComponent({
    // components: {PieChart},
    data(){
        return {
            data1: [],
            // data2: [],
            // data3: [],
            // data4: []
        }
    },
    mounted() {
        this.updateData();
    },

  methods: {
        updateData(){
            this.request.get('/api/tag/popularity/ten')
                .then(data => {
                    console.log(data);
                    // const tags = [];
                    // const questionCount = [];
                    // const answerCount = [];
                    // const viewCount = [];
                    // const totalCount = [];
                    // let cnt = 0;
                    this.data1 = data.map(item => ({
                        value: item.totalQuestionCount,
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
                  });

                    // data.forEach(item => {
                    //     cnt += 1;
                    //     tags.push(item.topic);
                    //     questionCount.push(item.totalQuestionCount);
                    //     answerCount.push(item.totalAnswerCount);
                    //     viewCount.push(item.totalViewCount);
                    //     totalCount.push(item.popularity);
                    // })
                    // for (let i = 0;i<cnt;i++) {
                    //     this.data1.push({value: questionCount[i], name: tags[i]})
                    //     this.data2.push({value: answerCount[i], name: tags[i]})
                    //     this.data3.push({value: viewCount[i], name: tags[i]})
                    //     this.data4.push({value: totalCount[i], name: tags[i]})
                    // }
                    console.log(this.data1)
                })
        }
    }
})


</script>

<style scoped>

</style>
