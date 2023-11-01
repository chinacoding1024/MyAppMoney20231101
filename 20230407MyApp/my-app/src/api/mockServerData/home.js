// mock数据模拟
import Mock from 'mockjs'

// 图表数据
let List = []
export default {
    getResultData: () => {
        //Mock.Random.float 产生随机数100到8000之间 保留小数 最小0位 最大0位
        for (let i = 0; i < 7; i++) {
            List.push(
                Mock.mock({
                    test1: Mock.Random.float(100, 8000, 0, 0),
                    test2: Mock.Random.float(100, 8000, 0, 0),
                    test3: Mock.Random.float(100, 8000, 0, 0),
                    test4: Mock.Random.float(100, 8000, 0, 0),
                    test5: Mock.Random.float(100, 8000, 0, 0),
                    test6: Mock.Random.float(100, 8000, 0, 0)
                })
            )
        }
        return {
            code: 20000,
            data: {
                // 饼图
                videoData: [
                    {
                        name: '小米',
                        value: 999
                    },
                    {
                        name: '苹果',
                        value: 1999
                    },
                    {
                        name: 'vivo',
                        value: 1800
                    },
                    {
                        name: 'oppo',
                        value: 5999
                    },
                    {
                        name: '魅族',
                        value: 4500
                    },
                    {
                        name: '三星',
                        value: 5500
                    }
                ],
                // 柱状图
                userData: [
                    {
                        date: '周一',
                        new: 5,
                        active: 200
                    },
                    {
                        date: '周二',
                        new: 10,
                        active: 500
                    },
                    {
                        date: '周三',
                        new: 12,
                        active: 550
                    },
                    {
                        date: '周四',
                        new: 60,
                        active: 800
                    },
                    {
                        date: '周五',
                        new: 65,
                        active: 550
                    },
                    {
                        date: '周六',
                        new: 53,
                        active: 770
                    },
                    {
                        date: '周日',
                        new: 33,
                        active: 170
                    }
                ],
                // 折线图
                orderData: {
                    date: ['20191001', '20191002', '20191003', '20191004', '20191005', '20191006', '20191007'],
                    data: List
                },
                tableData: [
                    {
                        name: '王小虎1',
                        address: '北京1',
                        date: '2016-05-03',
                    },
                    {
                        name: '王小虎2',
                        address: '北京2',
                        date: '2016-05-03',
                    },
                    {
                        name: '王小虎3',
                        address: '北京3',
                        date: '2016-05-03',
                    },
                    {
                        name: '王小虎4',
                        address: '北京4',
                        date: '2016-05-03',
                    },
                    {
                        name: '王小虎5',
                        address: '北京5',
                        date: '2016-05-03',

                    },
                    {
                        name: '王小虎6',
                        address: '北京6',
                        date: '2016-05-03',
                    }
                ]
            }
        }
    }
}
