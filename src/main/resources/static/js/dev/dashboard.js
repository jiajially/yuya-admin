dashboard_tool = {
    form_clear: function () {
    },
    load_cup_mem: function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('disk'));

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
            console.log(  _tmp_data);
        }

        option = {
            title: {
                text: '远程主机运行状态'
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
                console.log(_tmp_data);

            }
            myChart.setOption(option, true);
        }, 1000);
        console.log(randomData());
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
    }



};
$(document).ready(function () {
    dashboard_tool.load_cup_mem();
    dashboard_tool.load_combox();
});