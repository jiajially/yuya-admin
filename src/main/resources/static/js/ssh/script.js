script_tool = {
    form_clear: function () {
        $("#script_form").form('reset');
        $("#script_form").form('clear');
    },
    init_main_view: function () {
        var name = $("input[name='script-search-name']").val();
        var hostname = $("input[name='script-search-host']").val();
        var cmd = $("input[name='script-search-cmd']").val();
        $("#script_grid").datagrid({
            url: getRootPath() + '/ssh/script/list',
            method: 'get',
            idField: "id",
            fitColumns: true,
            toolbar: '#script-tool-bar',
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
                    title: "脚本状态", field: "status", align: 'center', width: 87, formatter: function (value, row, index) {
                    if (value == 1) {
                        return "调度中";
                    }else {
                        return "已停止";
                    }

                }
                },

                {title: "执行周期（s）", field: "rate", width: 124, sortable: true},
                {
                    title: "创建时间", field: "createTime", formatter: function (value, row, index) {
                    date = new Date(value);
                    timeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
                    return timeStr;
                }, width: 200, sortable: true
                },
                {
                    title: "开始时间", field: "startTime", formatter: function (value, row, index) {
                    date = new Date(value);
                    timeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
                    return timeStr;
                }, width: 200, sortable: true
                },
                {
                    title: "结束时间", field: "endTime", formatter: function (value, row, index) {
                    date = new Date(value);
                    timeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
                    return timeStr;
                }, width: 200, sortable: true
                },


            ]],
            onLoadSuccess: function (data) {
                $(".status").switchbutton({
                    readonly: true,
                    onText: '已启用',
                    offText: '已禁用',
                    width: 80,
                })
            },

        });
        $("#script-host").combobox({
            url:getRootPath() + '/host/select',
            method:'GET',
            valueField:'id',
            textField:'text',
            onShowPanel:function(){
                $("#script-host").combobox('reload',getRootPath() + '/host/select');
            },
        });
    },


    init_edit_view: function (type) {
        $("#script_new_dialog").dialog({
            title: '新增指令',
            iconCls: 'icon-save',
            closable: true,
            width: 400,
            height: 400,
            cache: false,
            modal: true,
            resizable: false,
            'onBeforeOpen': function () {

            },
            'onOpen': function () {

            },
            'onClose': function () {
                script_tool.form_clear();
            },
            buttons: [
                {
                    text: '保存',
                    width: 100,
                    iconCls: 'icon-save',
                    handler: function () {
                        if (type == 1) {
                            script_tool.save();
                        }
                        if (type == 2) {
                            script_tool.update();
                        }
                    }
                },
                {
                    text: '清除',
                    width: 100,
                    iconCls: 'icon-reload',
                    handler: function () {
                        script_tool.form_clear();
                    }
                },
                {
                    text: '取消',
                    width: 100,
                    iconCls: 'icon-add',
                    handler: function () {
                        script_tool.form_clear();
                        $("#script_new_dialog").dialog('close');
                    }
                }
            ],
        })
    },

    save: function () {
        var form_isValid = $("#script_form").form('validate');
        if (!form_isValid) {
            common_tool.messager_show("请输入必填参数");
            return false;
        }
        else {
            var name = $('#script_new_dialog input[id="script-name"]').val();
            var host = $('#script-host').combobox('getValue');
            var cmd = $('#script_new_dialog input[id="script-cmd"]').val();
            var startTime = $('#script-startTime').datetimebox('getValue')
            var endTime = $('#script-endTime').datetimebox('getValue')
            var rate = $('#script_new_dialog input[id="script-rate"]').val();
            console.log(name);
            console.log(host);
            console.log(cmd);
            console.log(startTime);
            console.log(endTime);
            console.log(rate);
            $.ajax({
                data: {
                    name: name,
                    host: host,
                    cmd: cmd,
                    startTime:startTime,
                    endTime:endTime,
                    rate:rate,
                },
                traditional: true,
                method: 'post',
                url: getRootPath() + '/ssh/script/insert',
                async: true,
                dataType: 'json',
                beforeSend:function () {
                    common_tool.process_wait("加载中...")
                },
                success: function (result) {
                    common_tool.process_finish();
                    if (result.code == 10000) {
                        $("#script_new_dialog").dialog("close");
                        script_tool.form_clear();
                        script_tool.init_main_view();
                        common_tool.messager_show(result.msg);
                        return false;
                    }
                    else {
                        common_tool.messager_show(result.msg);
                    }
                },
            });

        }

    },
    update: function (data) {
        var form_isValid = $("#script_form").form('validate');
        if (!form_isValid) {
            common_tool.messager_show("请输入必填参数")
        } else if ($("#jobs").treegrid("getChecked").length == 0) {
            common_tool.messager_show('请选择职位');
        }
        // else if ($("#user-permissions").datagrid("getChecked").length == 0) {
        //     common_tool.messager_show('请选择权限');
        // }
        else {
            var id = $('#script_new_dialog input[id="id"]').val();
            var name = $('#script_new_dialog input[id="script-name"]').val();
            var host = $('#script-host').combobox('getValue');
            var cmd = $('#script_new_dialog input[id="script-cmd"]').val();
            var startTime = $('#script-startTime').datetimebox('getValue')
            var endTime = $('#script-endTime').datetimebox('getValue')
            var rate = $('#script_new_dialog input[id="script-rate"]').val();

            $.ajax({
                data: {
                    id: id,
                    name: name,
                    host: host,
                    cmd: cmd,
                    startTime: startTime,
                    endTime: endTime,
                    rate: rate,
                },
                traditional: true,
                method: 'post',
                url: getRootPath() + '/ssh/script/update',
                async: true,
                dataType: 'json',
                beforeSend:function () {
                    common_tool.process_wait("加载中...")
                },
                success: function (result) {
                    common_tool.process_finish();
                    if (result.code == 10000) {
                        $("#script_new_dialog").dialog("close");
                        script_tool.form_clear();
                        script_tool.init_main_view();
                        common_tool.messager_show(result.msg);
                        return false;
                    }
                    else {
                        common_tool.messager_show(result.msg);
                    }
                },
            });

        }
    },
    delete: function (id) {
        $.ajax({
            data: {
                id: id,
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/ssh/script/delete',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                common_tool.process_finish();
                if (result.code == 10000) {
                    script_tool.form_clear();
                    script_tool.init_main_view();
                    common_tool.messager_show(result.msg);
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            },
        });
    },
    stopScript: function (id) {
        $.ajax({
            data: {
                id: id,
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/ssh/script/stop',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                common_tool.process_finish();
                if (result.code == 10000) {
                    script_tool.form_clear();
                    script_tool.init_main_view();
                    common_tool.messager_show(result.msg);
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            },
        });
    },
};
$(document).ready(function () {
    script_tool.init_main_view();
    $("#script-save-btn").click(function () {
        script_tool.init_edit_view(1);
    });
    $("#script-update-btn").click(function () {
        var scriptTasks = $("#script_grid").datagrid('getChecked');
        if (scriptTasks.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        $("#script_form").form('load', {
            id: scripts[0].id,
            loginName: scriptTasks[0].loginName,
            zhName: scriptTasks[0].zhName,
            enName: scriptTasks[0].enName,
            sex: scriptTasks[0].sex,
            birth: scriptTasks[0].birth,
            email: scriptTasks[0].email,
            phone: scriptTasks[0].phone,
            address: scriptTasks[0].address,
            password: '111111111111',
        });
        script_tool.init_edit_view(2);
    });
    $("#script-delete-btn").click(function () {
        var scripts = $("#script_grid").datagrid('getChecked');
        if (scripts.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        $.messager.confirm('确认对话框', "您确认删除该条记录吗?", function (r) {
            if (r) {
                script_tool.delete(scripts[0].id);
            }
        });
    });
    $("#script-enable-btn").click(function () {
        var scriptTasks = $("#script_grid").datagrid('getChecked');
        if (scriptTasks.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        if (scriptTasks[0].status == 1) {

            common_tool.messager_show("该账号已经处于启用状态");
            return false;
        }
        $.messager.confirm('确认对话框', "您确认启用 " + scriptTasks[0].zhName + " 账号吗?", function (r) {
            if (r) {
                script_tool.enableUser(scriptTasks[0].id)
            }
        });
    });
    $("#script-stop-btn").click(function () {
        var scripts = $("#script_grid").datagrid('getChecked');
        if (scripts.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        if (scripts[0].status == 2) {
            common_tool.messager_show("该脚本已停止");
            return false;
        }
        $.messager.confirm('确认对话框', "您确认要停止 \"" + scripts[0].name + "\" 脚本任务吗?", function (r) {
            if (r) {
                script_tool.stopScript(scripts[0].id)
            }
        });
    });
    $("#script-flash-btn").click(function () {
        script_tool.form_clear();
        script_tool.init_main_view();
    });
    $("#script-select-btn").click(function () {
        script_tool.init_main_view();
    });


});