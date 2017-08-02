dashboard_tool = {
    form_clear: function () {
    },
    load_cup_mem: function (hostId) {

        var data1 = [];
        var data2 = [];
        var now = new Date(Date.now());
        //MEM数据
        $.ajax({
            data: {
                hostId: hostId,
                type: 12
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/dev/monitor/log',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                if (result.code == 10000) {
                    for(var i = 0 ; i<=result.data.length;i++){
                        if(result.data[i]!=null) {
                            var tmp_data1 = {
                                name: new Date(result.data[i].recTime),
                                value: [
                                    new Date(result.data[i].recTime),
                                    result.data[i].result * 100
                                ]
                            };
                            data1.push(tmp_data1);
                        }
                    }

                }
                dashboard_tool.set_option(data1,data2);
            },
        });
        //CPU 数据
        $.ajax({
            data: {
                hostId: hostId,
                type: 11//11是CPU
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/dev/monitor/log',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                if (result.code == 10000) {
                    for(var i = 0 ; i<=result.data.length;i++){
                        if(result.data[i]!=null) {
                            var tmp_data1 = {
                                name: new Date(result.data[i].recTime),
                                value: [
                                    new Date(result.data[i].recTime),
                                    result.data[i].result * 100
                                ]
                            };
                            //console.log(new Date(result.data[i].recTime));
                            data2.push(tmp_data1);
                        }
                    }
                }

                dashboard_tool.set_option(data1,data2);
                common_tool.process_finish();
            }
        });

    },

    set_option:function (data1,data2) {

        // 基于准备好的dom，初始化echarts实例
        var chart_disk = echarts.init(document.getElementById('disk'));
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
                    return [date.getFullYear(), date.getMonth() + 1, date.getDate()].join('/')+' '+ date.getHours()+':'+date.getMinutes()+':'+date.getSeconds() + ' : ' + params.value[1];
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
        chart_disk.setOption(option, true);
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

    load_task_log:function (hostId) {
        // 基于准备好的dom，初始化echarts实例
        var chart_task = echarts.init(document.getElementById('task'));
        var data_x = [];
        var data_fail = [];
        var data_all = [];
        //加载数据
        $.ajax({
            data: {
                hostId: hostId
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/dev/ssh/task/sum',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                if (result.code == 10000) {
                   for(var i=23;i>=0;i--){//倒序
                       var tmp = result.data[i];
                       data_x.push(tmp.dateRef.substring(4,8)+'-'+tmp.dateRef.substring(8,10)+'点');
                       data_fail.push(tmp.failCount);
                       data_all.push(tmp.allCount);

                   }
                   //console.log(data_x);
                }
                var option = {
                    title : {
                        text: 'SSH任务量'
                    },
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    legend: {
                        data:['总和','失败数量']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis : [{
                        type : 'category',
                        data : data_x
                    }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [

                        {
                            name: '总和',
                            type:'bar',
                            data:data_all,
                            markPoint : {
                                data : [
                                    {type : 'max', name: '最大值'},
                                    {type : 'min', name: '最小值'}
                                ]
                            },
                            itemStyle:{
                                normal:{color:'#6CA6CD'}
                            }
                        },
                        {
                            name:'失败数量',
                            type:'bar',
                            barGap: '-100%',
                            data:data_fail,
                            itemStyle:{
                                normal:{color:'#FF4040'}
                            }
                        }
                    ]
                };
                chart_task.setOption(option, true);
                common_tool.process_finish();
            }
        });




    },


    monitor:function (hostId,type){
        $.ajax({
            data: {
                hostId: hostId,
                type: type,
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/dev/ssh/host/monitor',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.code == 10000) {
                    common_tool.messager_show(result.msg);
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            }
        });
    },

    sleep:function (numberMillis) {
    var now = new Date();
    var exitTime = now.getTime() + numberMillis;
    while (true) {
        now = new Date();
        if (now.getTime() > exitTime)
            return;
    }
}

};
$(document).ready(function () {
    dashboard_tool.load_combox();
    $("#dashboard-reload-btn").click(function () {
        var hostId = $('#dashboard-host').combobox('getValue');
        //console.log(hostId);
        dashboard_tool.load_cup_mem(hostId);
        dashboard_tool.load_task_log(hostId);
    });

    $("#dashboard-start-btn").click(function () {
        var hostId = $('#dashboard-host').combobox('getValue');
        dashboard_tool.monitor(hostId,1);
    });

    $("#dashboard-stop-btn").click(function () {
        var hostId = $('#dashboard-host').combobox('getValue');
        dashboard_tool.monitor(hostId,2);
    });

});