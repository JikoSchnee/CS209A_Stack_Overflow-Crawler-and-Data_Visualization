<template>
    <div class="grid-container">
        <div>
            <h4>标题</h4>
        </div>
        <div class="flex-container">
            <el-input v-model="input" placeholder="请输入内容" style="width: 300px;"></el-input>
            <jump-box class="centered-box" :input="input" ref="jumpBox"></jump-box>
        </div>
    </div>
</template>

<script>
import JumpBox from "@/components/QueryBoxComponents/JumpBox.vue";
export default {
    data() {
        return {
            input: ''
        }
    },
    components: {
        'jump-box': JumpBox
    },
    methods: {

    }
}
</script>

<style scoped>
.grid-container {
    max-width: 300px; /* 设置最大宽度 */
    max-height: 600px; /* 设置最大高度 */
    margin: auto; /* 居中 */
    border: 2px solid #ccc; /* 添加边框 */
    padding: 10px; /* 可选：添加内边距 */
}

.flex-container {
    display: flex;
}

.centered-box {
    margin: auto;
    width: 70%;
}
</style>
