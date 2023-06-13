<template>
  <div class="dashboard">
    <div class="dashboard-container">
      <div class="dashboard-text">你好！{{ name }}</div>
    </div>

    <div
      class="echart"
      id="mychart"
      :style="{ float: 'left', width: '70%', height: '400px' , center: ['50%', '50%']}"
    ></div>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import * as echarts from 'echarts';


export default {
  name: 'Dashboard',
  data() {
    return {

    };

  },
  methods: {
    initEcharts() {
      const option = {
        title: {
          text: "各院系学生人数统计",
          subtext: "2020年",
          left: "50px"
        },
        tooltip: {},
        legend: {
          data: []
        },
        series: [
          {
            name: "人数",
            type: "pie",
            radius: "50%",
            center: ["50%", "50%"],
            left: "0",
            data: [
              {value: 150, name: "数学院" },
              { value: 200, name: "物理学院" },
              { value: 250, name: "医学院" },
              { value: 300, name: "计算机学院" },
              { value: 100, name: "音乐学院"}
            ]
          },
        ]
      };
      const myChart = echarts.init(document.getElementById("mychart"));// 图标初始化
      myChart.setOption(option);// 渲染页面
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        myChart.resize();
      });
    }
  },

  computed: {
    ...mapGetters([
      'name'
    ])
  },
  mounted() {
    this.initEcharts();
  },

}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 25px;
    line-height: 45px;
  }
}
</style>
