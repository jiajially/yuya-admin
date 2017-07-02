dashboard_tool = {
    form_clear: function () {
    },
    load_cup_mem: function () {
        // 基于准备好的dom，初始化echarts实例
        var chart_disk = echarts.init(document.getElementById('disk'));

        function randomData() {
            now = new Date(+now + fiveSeconds);
            value = value + Math.random() * 21 - 10;
            var tmp = [];
            var tmp_data1 ={
                name: now.toString(),
                value: [
                    now,
                    Math.round(value/10)
                ]
            };
            var tmp_data2 ={
                name: now.toString(),
                value: [
                    now,
                    100-Math.round(value/10)
                ]
            };
            tmp.push(tmp_data1);
            tmp.push(tmp_data2);
            return tmp;
        }

        var data1 = [];
        var data2 = [];
        var now = +new Date(2017, 5, 29);
        var fiveSeconds = 120 * 1000;
        var value = Math.random() * 100;
        for (var i = 0; i < 1000; i++) {
            var _tmp_data = randomData();
            data1.push(_tmp_data[0]);
            data2.push(_tmp_data[1]);
        }

        var option = {
            title: {
                text: '远程主机运行状态'
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                formatter: function (params) {
                    params = params[0];
                    var date = new Date(params.name);
                    return [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/')+' '+ now.getHours()+':'+now.getMinutes()+':'+now.getSeconds() + ' : ' + params.value[1];
                },
                axisPointer: {
                    animation: false
                }
            },
            legend: {
                data:['内存','CPU']
            },
            xAxis: {
                type: 'time',
                splitLine: {
                    show: false
                }
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, '100%'],
                splitLine: {
                    show: false
                },
                max:100,
                min:0,
            },
            series: [{
                name: '内存',
                type: 'line',
                showSymbol:false,
                data: data1
            },{
                name: 'CPU',
                type: 'line',
                showSymbol:false,
                data: data2
            }]
        };

        setInterval(function () {

            for (var i = 0; i < 5; i++) {


                var _tmp_data = randomData();
                data1.shift();
                data2.shift();
                data1.push(_tmp_data[0]);
                data2.push(_tmp_data[1]);

            }
            chart_disk.setOption(option, true);
        }, 1000);
    },
    load_combox: function () {
        $("#dashboard-host").combobox({
            url:getRootPath() + '/host/select',
            method:'GET',
            valueField:'id',
            textField:'text',
            onShowPanel:function(){
                $("#dashboard-host").combobox('reload',getRootPath() + '/host/select');
            },
        });
    },

    load_task_log:function () {
        // 基于准备好的dom，初始化echarts实例
        var chart_task = echarts.init(document.getElementById('task'));
        var option = {
            title : {
                text: 'SSH任务量',
                subtext: '纯属虚构'
            },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data:['成功数量','失败数量','总和']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [{
                    type : 'category',
                    data : ['2点','4点','6点','8点','10点','12点','14点','16点','18点','20点','22点','24点']
                }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [

                    {
                        name:'成功数量',
                        type:'bar',
                        stack: '广告',
                        data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
                        itemStyle:{
                            normal:{color:'green'}
                        }
                    },
                    {
                        name:'失败数量',
                        type:'bar',
                        stack: '广告',
                        data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
                        itemStyle:{
                            normal:{color:'#CD3700'}
                        }
                    },
                    {
                        name: '总和',
                        type:'bar',
                        data:[4.6, 10.8,16, 49.6, 54.3, 147.4, 311.2, 344.4, 81.3, 38.8, 12.4, 4.6],
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        itemStyle:{
                            normal:{color:'#009ACD'}
                        }
                    }
                ]
            };
        chart_task.setOption(option, true);

    }

};
$(document).ready(function () {
    dashboard_tool.load_cup_mem();
    dashboard_tool.load_combox();
    dashboard_tool.load_task_log();
});