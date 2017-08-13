check_bu_tool = {
    form_clear: function () {
        $("#check_bu_tool_form").form('reset');
        $("#check_bu_tool_form").form('clear');
        $("#check_bu_tool_add_form").form('reset');
        $("#check_bu_tool_add_form").form('clear');
        $("#check_bu_tool_info_form").form('reset');
        $("#check_bu_tool_info_form").form('clear');
        $("#check_bu_tool_locate_form").form('reset');
        $("#check_bu_tool_locate_form").form('clear');
        $("#check_bu_tool_deal_form").form('reset');
        $("#check_bu_tool_deal_form").form('clear');
        $("#check_bu_tool-search-form").form('reset');
        $("#check_bu_tool-search-form").form('clear');
    },


    init_main_view: function () {
        $("#check_bu_grid").datagrid({
            url: getRootPath() + '/check/list',
            method: 'get',
            idField: "id",
            fitColumns: true,
            toolbar: '#check-bu-tool-bar',
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
                path: "file/bu"
            },
            columns: [[
                {title: "选择", field: "ck", checkbox: true},
                {title: "文件名称", field: "name", width: 50, sortable: true},
                {title: "文件简述", field: "name", width: 50, sortable: true},
                {
                    title: "文件时间", field: "date", formatter: function (value, row, index) {
                    date = new Date(value);
                    timeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
                    if(value==null||value=="")timeStr = value;
                    return timeStr;
                }, width: 100, sortable: true
                },
                {title: "文件路径", field: "path", width: 124, sortable: true},
                {title: "绝对路径", field: "absolutePath", width: 124, sortable: true},





            ]],
            onLoadSuccess: function (data) {

            },
            onDblClickRow: function (index, row) {
                check_bu_tool.load_detail();
            }
        });

    },

    load_detail:function(){
        var backups = $("#check_bu_grid").datagrid('getChecked');
        if (backups.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }

        $.ajax({
            data: {
                path:backups[0].path
            },
            traditional: true,
            method: 'GET',
            url: getRootPath() + '/check/backup/detail',
            async: false,
            dataType: 'json',
            success: function (result) {
                console.log(result);
                if (result.code == 10000) {
                    $("#check_bu_result_dialog").dialog({
                        title: '查看备份：'+backups[0].name,
                        iconCls: 'icon-save',
                        closable: true,
                        width: 800,
                        height: 600,
                        cache: false,
                        modal: true,
                        resizable: false,
                        'onBeforeOpen': function () {

                        },
                        'onOpen': function () {
                            console.log(result.data);
                            $("#check_bu_result_dialog").html('<pre>'+result.data+'</pre>');


                        },
                        'onClose': function () {
                            check_bu_tool.form_clear();
                        },
                    })
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            },
        });
    }


};


$(document).ready(function () {
    check_bu_tool.init_main_view();

    $("#check-bu-flash-btn").click(function () {
        check_bu_tool.init_main_view();
    });
    $("#check-bu-detail-btn").click(function () {
        check_bu_tool.load_detail();
    });



});