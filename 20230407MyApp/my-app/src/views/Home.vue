<template>
  <div class="main">
    <el-row>
      <el-col :span="8" style="padding-right: 10px"
        ><div class="grid-content bg-purple">
          <el-card class="box-card">
            <div class="user-content">
              <img src="@/assets/img/user.jpg" class="user-img" />
              <div class="user-info">
                <p class="user-name">Admin</p>
                <p class="user-access">超级管理员</p>
              </div>
            </div>
            <div class="login-content">
              <p>上次登录的时间:<span>2023-02-25</span></p>
              <p>上次登录的地点:<span>西安</span></p>
            </div>
          </el-card>
          <el-card class="box-card" style="margin-top: 20px">
            <el-table :data="tableData" style="width: 100%">
              <el-table-column
                v-for="(val, key) in tableLabel"
                :prop="key"
                :label="val"
                :key="key"
              />

              <!-- <el-table-column prop="date" label="日期" width="180">
              </el-table-column>
              <el-table-column prop="name" label="姓名" width="180">
              </el-table-column>
              <el-table-column prop="address" label="地址">
              </el-table-column>  -->
            </el-table></el-card
          >
        </div></el-col
      >
      <el-col :span="16" style="padding-left: 10px">
        <div class="home-right-info">
          <el-card
            v-for="item in countData"
            :key="item.name"
            :body-style="{ display: 'flex' }"
          >
            <i
              class="icon"
              :class="`el-icon-${item.icon}`"
              :style="{ background: item.color }"
            ></i>
            <div class="detail-info">
              <p class="price">{{ item.value }}</p>
              <p class="desc">{{ item.name }}</p>
            </div>
          </el-card>
        </div>
        <el-card style="height: 280px">
          <!-- 折线图 -->
          <div ref="echartsLine" style="height: 280px"></div>
        </el-card>

        <div class="home-right-bottom">
          <el-card style="height: 260px">
            <!-- 柱状图 -->
            <div ref="echartsBar1" style="height: 260px"></div>
          </el-card>
          <el-card style="height: 260px">
            <!-- 柱状图 -->
            <div ref="echartsPie" style="height: 240px"></div>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { getData } from "@/api/index.js";
import * as echarts from "echarts";
export default {
  name: "Home",
  data() {
    return {
      countData: [
        {
          name: "样式1",
          value: "样式1",
          icon: "success",
          color: "#2ec7c9",
        },
        {
          name: "样式2",
          value: "样式2",
          icon: "star-on",
          color: "#ffb980",
        },
        {
          name: "样式3",
          value: "样式3",
          icon: "s-goods",
          color: "#5ab1ef",
        },
        {
          name: "样式4",
          value: "样式4",
          icon: "user",
          color: "#2ec7c9",
        },
        {
          name: "样式5",
          value: "样式5",
          icon: "s-home",
          color: "#ffb980",
        },
        {
          name: "样式6",
          value: "样式6",
          icon: "folder",
          color: "#5ab1ef",
        },
      ],
      tableLabel: {
        name: "姓名",
        date: "日期",
        address: "地址",
      },
      tableData: [
        {
          date: "2016-05-02",
          name: "王小虎",
          address: " 1518 弄",
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          address: " 1517 弄",
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          address: " 1519 弄",
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          address: " 1516 弄",
        },
      ],
    };
  },
  // mounted() {

  //   getData().then((res) => {
  //     this.tableData = res.data.tableData;
  //     //折线图start
  //     // 1、基于准备好的dom，初始化echarts实例
  //     const myChartLine = echarts.init(this.$refs.echartsLine);
  //     //2、 指定图表的配置项和数据
  //     var optionLine = {};
  //     //3、获取数据 xAxis
  //     const { orderData, userData, videoData } = res.data;
  //     const xAxisLine = Object.keys(orderData.data[0]);
  //     const xAxisData = { data: xAxisLine };
  //     optionLine.xAxis = xAxisData;
  //     optionLine.yAxis = {};
  //     optionLine.legend = xAxisData;

  //     optionLine.series = [];

  //     xAxisLine.forEach((key) => {
  //       optionLine.series.push({
  //         name: key,
  //         data: orderData.data.map((item) => item[key]),
  //         type: "line",
  //       });
  //     });
  //     console.log(optionLine);
  //     //4、使用刚指定的配置项和数据显示图表。
  //     myChartLine.setOption(optionLine);
  //     //折线图end

  //     //柱状图1start
  //     // 1、基于准备好的dom，初始化echarts实例
  //     const myChartBar1 = echarts.init(this.$refs.echartsBar1);
  //     //2、 指定图表的配置项和数据
  //     var optionBar1 = {
  //       legend: {
  //         // 图例文字颜色
  //         textStyle: {
  //           color: "#333",
  //         },
  //       },
  //       grid: {
  //         left: "20%",
  //       },
  //       // 提示框
  //       tooltip: {
  //         trigger: "axis",
  //       },
  //       xAxis: {
  //         type: "category", // 类目轴
  //         data: [],
  //         axisLine: {
  //           lineStyle: {
  //             color: "#17b3a3",
  //           },
  //         },
  //         axisLabel: {
  //           interval: 0,
  //           color: "#333",
  //         },
  //       },
  //       yAxis: [
  //         {
  //           type: "value",
  //           axisLine: {
  //             lineStyle: {
  //               color: "#17b3a3",
  //             },
  //           },
  //         },
  //       ],
  //       color: ["#2ec7c9", "#b6a2de"],
  //       series: [
  //         {
  //           name: "新增用户",
  //           data: userData.map((item) => {
  //             return item.new;
  //           }),
  //           type: "bar",
  //         },
  //         {
  //           name: "活跃用户",
  //           data: userData.map((item) => {
  //             return item.active;
  //           }),

  //           type: "bar",
  //         },
  //         // {
  //         //   name: "新增用户",
  //         //   data: userData.map((item) => item.new),
  //         //   type: "bar",
  //         // },
  //         // {
  //         //   name: "活跃用户",
  //         //   data: userData.map((item) => item.active),
  //         //   type: "bar",
  //         // },
  //       ],
  //     };
  //     //4、使用刚指定的配置项和数据显示图表。
  //     myChartBar1.setOption(optionBar1);
  //     //柱状图1end

  //     //饼状图1start
  //     // 1、基于准备好的dom，初始化echarts实例
  //     const myChartPie = echarts.init(this.$refs.echartsPie);
  //     //2、 指定图表的配置项和数据
  //     //饼状图1end
  //     const optionPie = {
  //       tooltip: {
  //         trigger: "item",
  //       },
  //       color: [
  //         "#0f78f4",
  //         "#dd536b",
  //         "#9462e5",
  //         "#a6a6a6",
  //         "#e1bb22",
  //         "#39c362",
  //         "#3ed1cf",
  //       ],
  //       series: [{ data: videoData, type: "pie" }],
  //     };
  //     myChartPie.setOption(optionPie);
  //   });
  // },
};
</script>
<style lang="less" scoped>
.main {
  .user-content {
    padding-bottom: 20px;
    margin-bottom: 20px;
    border-bottom: 1px solid#ccc;
    display: flex;
    align-items: center; //垂直居中
    .user-img {
      width: 150px;
      height: 150px;
      border-radius: 50%;
      margin-right: 40px;
    }
    .user-info {
      .user-name {
        font-size: 32px;
        margin-bottom: 10px;
      }
      .user-access {
        color: #999999;
      }
    }
  }
  .login-content {
    p {
      line-height: 28px;
      font-size: 14px;
      color: #999999;
      span {
        color: #666666;
        margin-left: 60px;
      }
    }
  }
  .home-right-info {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    .icon {
      width: 80px;
      height: 80px;
      font-size: 30px;
      text-align: center;
      line-height: 80px;
      color: #fff;
    }
    .detail-info {
      margin-left: 15px;
      display: flex;
      flex-direction: column;
      justify-content: center;

      .price {
        font-size: 30px;
        margin-bottom: 10px;
        line-height: 30px;
        height: 30px;
        text-align: left;
      }
      .desc {
        font-size: 14px;
        color: #999;
        text-align: left;
      }
    }
    .el-card {
      width: 32%;
      margin-bottom: 20px;
    }
  }
  .home-right-bottom {
    display: flex;
    justify-content: space-between;
    .el-card {
      width: 48%;
      height: 260px;
      margin-top: 20px;
    }
  }
}
</style>