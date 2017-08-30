check_tool = {
    form_clear: function (type) {
        $("#check_"+type+"_tool_form").form('reset');
        $("#check_"+type+"_tool_form").form('clear');
        $("#check_"+type+"_tool_add_form").form('reset');
        $("#check_"+type+"_tool_add_form").form('clear');
        $("#check_"+type+"_tool_info_form").form('reset');
        $("#check_"+type+"_tool_info_form").form('clear');
        $("#check_"+type+"_tool_locate_form").form('reset');
        $("#check_"+type+"_tool_locate_form").form('clear');
        $("#check_"+type+"_tool_deal_form").form('reset');
        $("#check_"+type+"_tool_deal_form").form('clear');
        $("#check_"+type+"_tool-search-form").form('reset');
        $("#check_"+type+"_tool-search-form").form('clear');
    },


    init_main_view: function (begin,end,type) {
        $("#check_"+type+"_grid").datagrid({
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
                type: type,
                begin:begin,
                end:end
            },
            columns: [[
                {title: "选择", field: "ck", checkbox: true},
                {title: "编号", field: "id", width: 50, sortable: true},
                {title: "描述", field: "summary", width: 50, sortable: true},
                {
                    title: "操作时间", field: "exectime", formatter: function (value, row, index) {
                    date = new Date(value);
                    timeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                    if(value==null||value=="")timeStr = value;
                    return timeStr;
                }, width: 100, sortable: true
                }





            ]],
            onLoadSuccess: function (data) {

            },
            onDblClickRow: function (index, row) {
                check_tool.load_detail(type);
            }
        });

    },

    load_detail:function(type){
        var backups = $("#check_"+type+"_grid").datagrid('getChecked');
        if (backups.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }

        $.ajax({
            data: {
                id:backups[0].id
            },
            traditional: true,
            method: 'GET',
            url: getRootPath() + '/check/detail',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.code == 10000) {
                    $("#check_"+type+"_result_dialog").html('<pre>'+result.data.result+'</pre>');
                    $("#check_"+type+"_result_dialog").dialog({
                        title: backups[0].summary,
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


                        },
                        'onClose': function () {
                            check_tool.form_clear(type);
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

