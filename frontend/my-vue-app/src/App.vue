<template>
    <div id="app">
        <h1>Stack Overflow Data Visualization</h1>
        <h3>Topic Popularity</h3>
        <el-button @click="reloadTagPopularity">Reload Topic Popularity</el-button>
        <TopicPopularity ref="tagPopularityRef" />
        <h3>Bug Popularity</h3>
        <el-button @click="reloadBugPopularity">Reload Bug Popularity</el-button>
        <BugPopularity ref="bugPopularityRef"></BugPopularity>
        <el-button @click="reloadBugPopularity2">Reload Bug Popularity</el-button>
        <BugPopularity2 ref="bugPopularityRef2"></BugPopularity2>
        <H3>Related Topic</H3>
        <TopicWordCloud></TopicWordCloud>
        <el-backtop target=".page-component__scroll .el-scrollbar__wrap"></el-backtop>
    </div>
</template>

<script>
import TopicPopularity from "@/components/QueryBoxComponents/TopicPopularity.vue";
import BugPopularity from "@/components/QueryBoxComponents/BugPopularity.vue";
import TopicWordCloud from "@/components/QueryBoxComponents/TopicWordCloud.vue";
import BugPopularity2 from "@/components/QueryBoxComponents/BugPopularity2.vue";

export default {
    name: 'App',
    components: {
        TopicWordCloud,
        TopicPopularity,
        BugPopularity,
        BugPopularity2
    },
    data() {
        return {

        };
    },
    methods: {
        reloadTagPopularity() {
            // Call the reload method of the TagPopularity component using the ref
            this.$refs.tagPopularityRef.updateData();
        },
        reloadBugPopularity() {
            this.$refs.bugPopularityRef.generateChart();
        },
        reloadBugPopularity2() {
            this.$refs.bugPopularityRef2.generateChart();
        }
    }
};
</script>

<style scoped>
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
}
</style>
