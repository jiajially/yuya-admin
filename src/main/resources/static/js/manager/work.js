work_tool = {
    form_clear: function () {
        $("#manager_work_form").form('reset');
        $("#manager_work_form").form('clear');
        $("#manager_work_add_form").form('reset');
        $("#manager_work_add_form").form('clear');
    },

    add_work:function () {
        $("#manager_work_add_dialog").dialog({
            title: "添加问题",
            iconCls: 'icon-save',
            closable: true,
            width: 400,
            height: 500,
            cache: false,
            modal: true,
            resizable: false,
            'onBeforeOpen': function () {

            },
            'onOpen': function () {

            },
            'onClose': function () {
                manager-work_tool.form_clear();
            },
            buttons: [

                {
                    text: '保存',
                    width: 80,
                    iconCls: 'icon-save',
                    handler: function () {
                        manager-work_tool.save();

                    }
                },
                {
                    text: '清除',
                    width: 80,
                    iconCls: 'icon-reload',
                    handler: function () {
                        manager-work_tool.form_clear();
                    }
                },
                {
                    text: '取消',
                    width: 80,
                    iconCls: 'icon-add',
                    handler: function () {
                        manager-work_tool.form_clear();
                        $("#manager-work_add_dialog").dialog('close');
                    }
                }
            ]
        })
    },
    deal_work:function (id) {
        $("#manager_work_deal_dialog").dialog({
            title: "处理问题（编号："+id+")",
            iconCls: 'icon-save',
            closable: true,
            width: 400,
            height: 560,
            cache: false,
            modal: true,
            resizable: false,
            'onBeforeOpen': function () {

            },
            'onOpen': function () {

            },
            'onClose': function () {
                manager-work_tool.form_clear();
            },
            buttons: [

                {
                    text: '处理',
                    width: 80,
                    iconCls: 'icon-save',
                    handler: function () {
                        manager-work_tool.deal();

                    }
                },
                {
                    text: '清除',
                    width: 80,
                    iconCls: 'icon-reload',
                    handler: function () {
                        manager-work_tool.form_clear();
                    }
                },
                {
                    text: '取消',
                    width: 80,
                    iconCls: 'icon-add',
                    handler: function () {
                        manager-work_tool.form_clear();
                        $("#manager-work_deal_dialog").dialog('close');
                    }
                }
            ]
        })
    },

    init_main_view: function () {
        var begin = null;
        var end = null;
        $("#manager_work_grid").datagrid({
            url: getRootPath() + '/manager/work/list',
            method: 'get',
            idField: "id",
            fitColumns: true,
            toolbar: '#manager-work-tool-bar',
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
                begin: begin,
                end: end
            },
            columns: [[
                {title: "选择", field: "ck", checkbox: true},
                {title: "记录编号", field: "id", width: 150, sortable: true},
                {title: "简述", field: "summary", width: 300, sortable: true},
                {title: "类型", field: "type", width: 150, sortable: true},
                {title: "优先级", field: "level", width: 150, sortable: true},
                {
                    title: "创建时间时间", field: "createdate", formatter: function (value, row, index) {
                    date = new Date(value);
                    timeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes()+":"+date.getSeconds();
                    return timeStr;
                }, width: 150, sortable: true
                },



            ]],
            onLoadSuccess: function (data) {

            },
            onDblClickRow: function (index, row) {

            }
        });

    },

    save: function () {
        var form_isValid = $("#manager_work_form").form('validate');
        if (!form_isValid) {
            common_tool.messager_show("请输入必填参数")
        }
        else {
            var summary = $('#manager-work_add_dialog input[id="manager-work-summary"]').val();
            var channel = $('#manager-work_add_dialog input[id="manager-work-channel"]').val();
            var informer = $('#manager-work_add_dialog input[id="manager-work-informer"]').val();

            $.ajax({
                data: {
                    summary:summary,
                    channel: channel,
                    informer: informer,

                },
                traditional: true,
                method: 'post',
                url: getRootPath() + '/manager/work/insert',
                async: true,
                dataType: 'json',
                beforeSend:function () {
                    common_tool.process_wait("加载中...")
                },
                success: function (result) {
                    common_tool.process_finish();
                    if (result.code == 10000) {
                        $("#manager-work_add_dialog").dialog("close");
                        manager-work_tool.form_clear();
                        manager-work_tool.init_main_view();
                        common_tool.messager_show(result.msg);
                        return false;
                    }
                    else {
                        common_tool.messager_show(result.msg);
                    }
                }
            });

        }

    },



    delete: function (id) {
        $.ajax({
            data: {
                id: id
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/manager/work/delete',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                common_tool.process_finish();
                if (result.code == 10000) {
                    manager-work_tool.form_clear();
                    manager-work_tool.init_main_view();
                    common_tool.messager_show(result.msg);
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            }
        });
    },


};


$(document).ready(function () {
    work_tool.init_main_view();
    $("#manager-work-add-btn").click(function () {
        var now =new Date();
        $("#manager_work_form").form('load', {
            "manager-work-date":now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds(),
        });
        manager-work_tool.add_manager-work();
    });
    $("#manager-work-deal-btn").click(function () {
        var works = $("#manager_work_grid").datagrid('getChecked');
        var work ;
        $.ajax({
            data: {
                id:works[0].id

            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/manager/manager-work/detail',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.code == 10000) {
                    work = result.data;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            }
        });
        if (manager-works.length == 0) {
            common_tool.messager_show("请选中你要处理的问题");
            return false;
        }

    });
    $("#manager-work-delete-btn").click(function () {
        var works = $("#manager_work_grid").datagrid('getChecked');
        if (works.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        $.messager.confirm('确认对话框', "您确认删除该条记录吗?", function (r) {
            if (r) {
                work_tool.delete(works[0].id);
            }
        });
    });

    $("#manager-work-flash-btn").click(function () {
        work_tool.form_clear();
        work_tool.init_main_view();
    });
    $("#manager-work-select-btn").click(function () {
        console.log($('#manager-work-search-begin').datetimebox('getValue'));
        work_tool.init_main_view();
    });


});