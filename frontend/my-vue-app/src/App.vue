<template xmlns:el-col="http://www.w3.org/1999/html">
    <div id="app">
        <h1>Stack Overflow Data Visualization</h1>
        <h3>Topic Popularity</h3>
        <el-button type="primary" @click="reloadTagPopularity" circle plain><i class="el-icon-refresh"></i></el-button>
        <TopicPopularity ref="tagPopularityRef" />
        <h3>Bug Popularity</h3>


        <el-row justify="center" gutter="0">
            <el-button-group>
                <el-button type="primary" @click = "BetweenOnly" plain>Between</el-button>
                <el-button type="primary" @click = "SyntaxOnly" plain>Syntax Errors</el-button>
                <el-button type="primary" @click = "FatalOnly" plain>Fatal Errors</el-button>
                <el-button type="primary" @click = "ExceptOnly" plain>Exceptions</el-button>
                <el-button type="primary" @click = "AllErrors" plain>All</el-button>
            </el-button-group>
        </el-row>
        <div v-if="this.isBugPopDefault">
            <el-button type="primary" circle plain><i class="el-icon-refresh"></i></el-button>
            <BugPopDefault></BugPopDefault>
        </div>
        <div v-if="this.isBugPopularityBetweenVisible">
            <el-button type="primary" @click="reloadBugPopularity2" circle plain><i class="el-icon-refresh"></i></el-button>
            <BugPopularity2 ref="bugPopularityRef2"></BugPopularity2>
        </div>
        <div v-if="this.isBugPopularitySyntaxVisible">
            <el-button type="primary" @click="reloadBugPopularitySyntax" circle plain><i class="el-icon-refresh"></i></el-button>
            <BugPopularitySyntax ref="bugPopularityRefSyntax"></BugPopularitySyntax>
        </div>
        <div v-if="this.isBugPopularityFatalVisible">
            <el-button type="primary" @click="reloadBugPopularityFatal" circle plain><i class="el-icon-refresh"></i></el-button>
            <BugPopularityFatal ref="bugPopularityRefFatal"></BugPopularityFatal>
        </div>
        <div v-if="this.isBugPopularityExceptVisible">
            <el-button type="primary" @click="reloadBugPopularityExcept" circle plain><i class="el-icon-refresh"></i></el-button>
            <BugPopularityExcept ref="bugPopularityRefExcept"></BugPopularityExcept>
        </div>
        <div v-if="this.isBugPopularityVisible">
            <el-button type="primary" @click="reloadBugPopularity" circle plain><i class="el-icon-refresh"></i></el-button>
            <BugPopularity ref="bugPopularityRef"></BugPopularity>
        </div>



        <H3>Related Topic</H3>
        <TopicWordCloud></TopicWordCloud>
        <el-backtop target=".page-component__scroll .el-scrollbar__wrap"></el-backtop>
    </div>
</template>

<script>
import TopicPopularity from "@/components/TopicPopCompo/TopicPopularity.vue";
import TopicWordCloud from "@/components/QueryBoxComponents/TopicWordCloud.vue";
import BugPopularity from "@/components/BugPopCompo/BugPopularity.vue";
import BugPopularity2 from "@/components/BugPopCompo/BugPopularityAll.vue";
import BugPopularitySyntax from "@/components/BugPopCompo/BugPopularitySyntax.vue";
import BugPopularityFatal from "@/components/BugPopCompo/BugPopularityFatal.vue";
import BugPopularityExcept from "@/components/BugPopCompo/BugPopularityExcept.vue";
import BugPopDefault from "@/components/BugPopCompo/BugPopDefault.vue";

export default {
    name: 'App',
    components: {
        TopicWordCloud,
        TopicPopularity,
        BugPopularity,
        BugPopularity2,
        BugPopularitySyntax,
        BugPopularityFatal,
        BugPopularityExcept,
        BugPopDefault
    },
    data() {
        return {
            isBugPopDefault: true,
            isBugPopularityBetweenVisible: false,
            isBugPopularityVisible: false,
            isBugPopularitySyntaxVisible: false,
            isBugPopularityFatalVisible: false,
            isBugPopularityExceptVisible: false,

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
        },
        reloadBugPopularitySyntax() {
            this.$refs.bugPopularityRefSyntax.generateChart();
        },
        reloadBugPopularityFatal() {
            this.$refs.bugPopularityRefFatal.generateChart();
        },
        reloadBugPopularityExcept() {
            this.$refs.bugPopularityRefExcept.generateChart();
        },
        BetweenOnly() {
            this.isBugPopDefault = false;
            this.isBugPopularityBetweenVisible = true;
            this.isBugPopularitySyntaxVisible = false;
            this.isBugPopularityFatalVisible = false;
            this.isBugPopularityExceptVisible = false;
            this.isBugPopularityVisible = false;
        },
        SyntaxOnly() {
            this.isBugPopDefault = false;
            this.isBugPopularityBetweenVisible = false;
            this.isBugPopularitySyntaxVisible = true;
            this.isBugPopularityFatalVisible = false;
            this.isBugPopularityExceptVisible = false;
            this.isBugPopularityVisible = false;
        },
        FatalOnly() {
            this.isBugPopDefault = false;
            this.isBugPopularityBetweenVisible = false;
            this.isBugPopularitySyntaxVisible = false;
            this.isBugPopularityFatalVisible = true;
            this.isBugPopularityExceptVisible = false;
            this.isBugPopularityVisible = false;
        },
        ExceptOnly() {
            this.isBugPopDefault = false;
            this.isBugPopularityBetweenVisible = false;
            this.isBugPopularitySyntaxVisible = false;
            this.isBugPopularityFatalVisible = false;
            this.isBugPopularityExceptVisible = true;
            this.isBugPopularityVisible = false;
        },
        AllErrors() {
            this.isBugPopDefault = false;
            this.isBugPopularityBetweenVisible = false;
            this.isBugPopularitySyntaxVisible = false;
            this.isBugPopularityFatalVisible = false;
            this.isBugPopularityExceptVisible = false;
            this.isBugPopularityVisible = true;
        },
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
