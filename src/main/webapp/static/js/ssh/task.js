task_tool = {
    form_clear: function () {
    },
    init_main_view: function () {
        var name = $("input[name='task-search-name']").val();
        var hostname = $("input[name='task-search-host']").val();
        var cmd = $("input[name='task-search-cmd']").val();
        $("#task_grid").datagrid({
            url: getRootPath() + '/ssh/task/list',
            method: 'get',
            idField: "id",
            fitColumns: true,
            toolbar: '#task-tool-bar',
            rownumbers: true,
            animate: true,
            singleSelect: true,
            fit: true,
            border: false,
            pagination: true,
            striped: true,
            pagePosition: "bottom",
            pageNumber: 1,
            pageSize: 15,
            pageList: [15, 30, 45, 60],
            queryParams: {
                name: name,
                hostname: hostname,
                cmd: cmd,
            },
            columns: [[
                {title: "选择", field: "ck", checkbox: true},
                {title: "脚本任务", field: "name", width: 124, sortable: true},
                {title: "主机", field: "hostname", width: 124, sortable: true},
                {title: "指令", field: "cmd", width: 124, sortable: true},
                {
                    title: "执行状态", field: "status", align: 'center', width: 87, formatter: function (value, row, index) {
                    if (value == 1) {
                        return "<front style='color: #00ee00'>执行成功</front>";
                    }else if (value == 2) {
                        return "<front style='color: #CC2222'>执行失败</front>";
                    }else{
                        return "未知";
                    }

                }
                },

                {
                    title: "执行时间", field: "execTime", formatter: function (value, row, index) {
                    return common_tool.timestampToDateTime(value);
                }, width: 200
                },
                {title: "备注", field: "mark", width: 124, sortable: true},

            ]],
            onLoadSuccess: function (data) {
                $(".status").switchbutton({
                    readonly: true,
                    onText: '已启用',
                    offText: '已禁用',
                    width: 80,
                })
            },
            onDblClickRow: function (index, row) {
                var tasks = $("#task_grid").datagrid('getChecked');
                if (tasks.length == 0) {
                    common_tool.messager_show("请至少选择一条记录");
                    return false;
                }
                $.ajax({
                    data: {
                        id: tasks[0].id,
                    },
                    traditional: true,
                    method: 'GET',
                    url: getRootPath() + '/ssh/task/result',
                    async: false,
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == 10000) {
                            $("#task_result_dialog").dialog({
                                title: '查看执行结果',
                                iconCls: 'icon-save',
                                closable: true,
                                width: 600,
                                height: 400,
                                cache: false,
                                modal: true,
                                resizable: false,
                                'onBeforeOpen': function () {

                                },
                                'onOpen': function () {
                                    console.log(result);
                                    $("#task_result_dialog").html('<pre>'+result.data.result+'</pre>');


                                },
                                'onClose': function () {
                                    task_tool.form_clear();
                                },
                            })
                        }
                        else {
                            common_tool.messager_show(result.msg);
                        }
                    },
                });





            }
        });
    },



};
$(document).ready(function () {
    task_tool.init_main_view();


    $("#task-select-btn").click(function () {
        task_tool.init_main_view();
    });
});