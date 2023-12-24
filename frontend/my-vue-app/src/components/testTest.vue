<script>
// import axios from 'axios'
export default {
  name: 'testTest',
  data() {
    return {
      num: 0
    }
  },
  methods: {
    fuzhi() {
      this.request.get(`/api/question/count`).then(res => {
        console.log(res)
        this.num = res
      })
    }
  }
}

</script>

<template>
  <div>
    <h1>测试组件</h1>
    <p>num: {{ num }}</p>
    <button @click="fuzhi"></button>
  </div>

</template>

<style scoped>

</style>
