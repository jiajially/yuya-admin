host_tool = {
    form_clear: function () {
        $("#host_form").form('reset');
        $("#host_form").form('clear');
        $("#host_detail_form").form('reset');
        $("#host_detail_form").form('clear');
        //$("#host_os_select").combobox('clear');
        $("#init_password_form").form('reset');
        $("#init_password_form").form('clear');
        $("#host-search-form").form('reset');
        $("#host-search-form").form('clear');
        $("#host-permissions").datagrid("uncheckAll");
        $("#jobs").treegrid("uncheckAll");
        $("#host_grid").treegrid("uncheckAll");
    },
    init_main_view: function () {
        var host = $("input[name='host-search-host']").val();
        var envPath = $("input[name='host-search-envPath']").val();
        console.log("===========",getRootPath() + '/host/list');
        $("#host_grid").datagrid({
            url: getRootPath() + '/host/list',
            method: 'get',
            idField: "id",
            fitColumns: true,
            toolbar: '#host-tool-bar',
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
                host: host,
                envPath: envPath
            },
            columns: [[
                {title: "选择", field: "ck", checkbox: true},
                {title: "描述", field: "summary", width: 124, sortable: true},
                {title: "主机", field: "host", width: 124, sortable: true},
                {title: "端口", field: "port", width: 124, sortable: true},
                {title: "登录用户", field: "username", width: 124, sortable: true},
                {
                    title: "验证状态", field: "valid", align: 'center', width: 60, formatter: function (value, row, index) {
                    if (value == true) {
                        return "<img src='/css/icons/greenlight.png' />";
                    }else{
                        return "<img src='/css/icons/redlight.png' />";
                    }

                }
                },
                {
                    title: "启用状态", field: "enable", align: 'center', width: 60, formatter: function (value, row, index) {
                    if (value == true) {
                        return "<img src='/css/icons/greenlight.png' />";
                    }else{
                        return "<img src='/css/icons/redlight.png' />";
                    }

                }
                },
                //{title: "PATH", field: "envPath", width: 150},
                {title: "操作系统", field: "os", width: 100},
                {title: "系统软件", field: "sw", width: 100},
                {
                    title: "创建时间", field: "createTime", formatter: function (value, row, index) {
                    date = new Date(value);
                    timeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
                    return timeStr;
                }, width: 120, sortable: true
                },
                {
                    title: "更新时间", field: "updateTime", formatter: function (value, row, index) {
                    date = new Date(value);
                    timeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
                    return timeStr;
                }, width: 120, sortable: true
                }
            ]],
            onLoadSuccess: function (data) {
                $(".isValid").switchbutton({
                    readonly: true,
                    onText: '有效',
                    offText: '失效',
                    width: 60
                });
                $(".isEnable").switchbutton({
                    readonly: true,
                    onText: '已启用',
                    offText: '已禁用',
                    width: 80
                })
            },
            onDblClickRow: function (index, row) {
                var hosts = $("#host_grid").datagrid('getChecked');
                console.log(hosts);
                if (hosts.length == 0) {
                    common_tool.messager_show("请至少选择一条记录");
                    return false;
                }
                $("#host_form").form('load', {
                    id: hosts[0].id,
                    "host-summary": hosts[0].summary,
                    "host-host": hosts[0].host,
                    "host-port": hosts[0].port,
                    "host-username": hosts[0].username,
                    "host-envPath": hosts[0].envPath,
                    "host-isValid": hosts[0].isValid,
                    "host-isEnable": hosts[0].isEnable,
                    "host-password":hosts[0].password
                });
                $("#host_detail_form").form('load', {
                    "host-os-name": hosts[0].osId,
                    "host-os-version": hosts[0].osVersion,
                    "host-sw-name": hosts[0].swId,
                    "host-sw-version": hosts[0].swVersion
                });
                host_tool.init_edit_view(2);
            }
        });

    },
    init_edit_view: function (type) {
        var _tmpTitle = "新增主机信息";
        if (type == 2){
            _tmpTitle = "修改主机信息";
        }


        $("#host_edit_dialog").dialog({
            title: _tmpTitle,
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
                host_tool.form_clear();
            },
            buttons: [

                {
                    text: '保存',
                    width: 80,
                    iconCls: 'icon-save',
                    handler: function () {
                        if (type == 1) {
                            host_tool.save();
                        }
                        if (type == 2) {
                            host_tool.update();
                        }
                    }
                },
                {
                    text: '验证',
                    width: 80,
                    iconCls: 'icon-ok',
                    handler: function () {
                        host_tool.host_valid();
                    }
                },
                {
                    text: '清除',
                    width: 80,
                    iconCls: 'icon-reload',
                    handler: function () {
                        host_tool.form_clear();
                    }
                },
                {
                    text: '取消',
                    width: 80,
                    iconCls: 'icon-add',
                    handler: function () {
                        host_tool.form_clear();
                        $("#host_edit_dialog").dialog('close');
                    }
                }
            ]
        })
    },

    save: function () {
        var form_isValid = $("#host_form").form('validate');
        if (!form_isValid) {
            common_tool.messager_show("请输入必填参数")
        }
        else {
            var summary = $('#host_edit_dialog input[id="host-summary"]').val();
            var host = $('#host_edit_dialog input[id="host-host"]').val();
            var port = $('#host_edit_dialog input[id="host-port"]').val();
            var username = $('#host_edit_dialog input[id="host-username"]').val();
            var password = $('#host_edit_dialog input[id="host-password"]').val();

            var host_os_id = $('#host-os-name').combobox('getValue');
            var host_os_name = $('#host-os-name').combobox('getText');
            var host_os_version =  $('#host_detail_form input[id="host-os-version"]').val();
            var host_sw_id = $('#host-sw-name').combobox('getValue');
            var host_sw_name = $('#host-sw-name').combobox('getText');
            var host_sw_version =  $('#host_detail_form input[id="host-sw-version"]').val();

            $.ajax({
                data: {
                    summary:summary,
                    host: host,
                    port: port,
                    username: username,
                    password: password,
                    osTypeId:host_os_id,
                    osversion:host_os_version,
                    swTypeId:host_sw_id,
                    swversion:host_sw_version,
                    osName:host_os_name,
                    swName:host_sw_name

                },
                traditional: true,
                method: 'post',
                url: getRootPath() + '/host/insert',
                async: true,
                dataType: 'json',
                beforeSend:function () {
                    common_tool.process_wait("加载中...")
                },
                success: function (result) {
                    common_tool.process_finish();
                    if (result.code == 10000) {
                        $("#host_edit_dialog").dialog("close");
                        host_tool.form_clear();
                        host_tool.init_main_view();
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
    host_valid: function () {
        var form_isValid = $("#host_form").form('validate');
        if (!form_isValid) {common_tool.messager_show("请输入必填参数");return false;}
        var id = $('#host_edit_dialog input[id="id"]').val();
        var summary = $('#host_edit_dialog input[id="host-summary"]').val();
        var host = $('#host_edit_dialog input[id="host-host"]').val();
        var port = $('#host_edit_dialog input[id="host-port"]').val();
        var username = $('#host_edit_dialog input[id="host-username"]').val();
        var password = $('#host_edit_dialog input[id="host-password"]').val();


        var host_os_id = $('#host-os-name').combobox('getValue');
        var host_os_name = $('#host-os-name').combobox('getText');
        var host_os_version =  $('#host_detail_form input[id="host-os-version"]').val();
        var host_sw_id = $('#host-sw-name').combobox('getValue');
        var host_sw_name = $('#host-sw-name').combobox('getText');
        var host_sw_version =  $('#host_detail_form input[id="host-sw-version"]').val();
        if (id==null||id=="")id=-1;
        $.ajax({
            data: {
                id: id,
                summary:summary,
                host: host,
                port: port,
                username: username,
                password:password,

                osTypeId:host_os_id,
                osversion:host_os_version,
                swTypeId:host_sw_id,
                swversion:host_sw_version,
                osName:host_os_name,
                swName:host_sw_name
            },
            traditional: true,
            method: 'post',
            url: getRootPath() + '/host/valid',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                common_tool.process_finish();
                if (result.code == 10000) {
                    $("#host_edit_dialog").dialog("close");
                    host_tool.form_clear();
                    host_tool.init_main_view();
                    common_tool.messager_show("验证成功");
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            }
        });
    },
    update: function (data) {
        var form_isValid = $("#host_form").form('validate');
        if (!form_isValid) {
            common_tool.messager_show("请输入必填参数")
        }else {
            var id = $('#host_edit_dialog input[id="id"]').val();
            var summary = $('#host_edit_dialog input[id="host-summary"]').val();
            var host = $('#host_edit_dialog input[id="host-host"]').val();
            var port = $('#host_edit_dialog input[id="host-port"]').val();
            var username = $('#host_edit_dialog input[id="host-username"]').val();
            var password = $('#host_edit_dialog input[id="host-password"]').val();

            var host_os_id = $('#host-os-name').combobox('getValue');
            var host_os_name = $('#host-os-name').combobox('getText');
            var host_os_version =  $('#host_detail_form input[id="host-os-version"]').val();
            var host_sw_id = $('#host-sw-name').combobox('getValue');
            var host_sw_name = $('#host-sw-name').combobox('getText');
            var host_sw_version =  $('#host_detail_form input[id="host-sw-version"]').val();
            $.ajax({
                data: {
                    id: id,
                    summary:summary,
                    host: host,
                    port: port,
                    username: username,
                    password:password,

                    osTypeId:host_os_id,
                    osversion:host_os_version,
                    swTypeId:host_sw_id,
                    swversion:host_sw_version,
                    osName:host_os_name,
                    swName:host_sw_name
                },
                traditional: true,
                method: 'post',
                url: getRootPath() + '/host/update',
                async: true,
                dataType: 'json',
                beforeSend:function () {
                    common_tool.process_wait("加载中...")
                },
                success: function (result) {
                    common_tool.process_finish();
                    if (result.code == 10000) {
                        $("#host_edit_dialog").dialog("close");
                        host_tool.form_clear();
                        host_tool.init_main_view();
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
            url: getRootPath() + '/host/delete',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                common_tool.process_finish();
                if (result.code == 10000) {
                    host_tool.form_clear();
                    host_tool.init_main_view();
                    common_tool.messager_show(result.msg);
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            }
        });
    },

    forbiddenHost: function (id) {
        $.ajax({
            data: {
                id: id
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/host/forbiddenHost',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                common_tool.process_finish();
                if (result.code == 10000) {
                    host_tool.form_clear();
                    host_tool.init_main_view();
                    common_tool.messager_show(result.msg);
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            }
        });
    },
    enableHost: function (id) {
        $.ajax({
            data: {
                id: id
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/host/enableHost',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                common_tool.process_finish();
                if (result.code == 10000) {
                    host_tool.form_clear();
                    host_tool.init_main_view();
                    common_tool.messager_show(result.msg);
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            }
        });
    },
    load_combox: function () {
        $("#host-os-name").combobox({
            url:getRootPath() + '/host/os/select',
            method:'GET',
            valueField:'id',
            textField:'text',
            onShowPanel:function(){
                $("#host-os-name").combobox('reload',getRootPath() + '/host/os/select');
            },
        });
        $("#host-sw-name").combobox({
            url:getRootPath() + '/host/sw/select',
            method:'GET',
            valueField:'id',
            textField:'text',
            onShowPanel:function(){
                $("#host-sw-name").combobox('reload',getRootPath() + '/host/sw/select');
            },
        });
    },

};


$(document).ready(function () {
    console.log("进来了。。。。");
    host_tool.init_main_view();
    host_tool.load_combox();
    $("#host-save-btn").click(function () {
        host_tool.init_edit_view(1);
    });
    $("#host-update-btn").click(function () {
        var hosts = $("#host_grid").datagrid('getChecked');
        if (hosts.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        $("#host_form").form('load', {
            id: hosts[0].id,
            "host-summary": hosts[0].summary,
            "host-host": hosts[0].host,
            "host-port": hosts[0].port,
            "host-username": hosts[0].username,
            "host-envPath": hosts[0].envPath,
            "host-isValid": hosts[0].isValid,
            "host-isEnable": hosts[0].isEnable,
            "host-password":hosts[0].password
        });
        $("#host_detail_form").form('load', {
            "host-os-name": hosts[0].osId,
            "host-os-version": hosts[0].osVersion,
            "host-sw-name": hosts[0].swId,
            "host-sw-version": hosts[0].swVersion
        });
        host_tool.init_edit_view(2);
    });
    $("#host-delete-btn").click(function () {
        var hosts = $("#host_grid").datagrid('getChecked');
        if (hosts.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        $.messager.confirm('确认对话框', "您确认删除该条记录吗?", function (r) {
            if (r) {
                host_tool.delete(hosts[0].id);
            }
        });
    });
    $("#host-enable-btn").click(function () {
        var hosts = $("#host_grid").datagrid('getChecked');
        if (hosts.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        if (hosts[0].enable == true) {

            common_tool.messager_show("该主机已经处于启用状态");
            return false;
        }
        if (hosts[0].valid == false) {

            common_tool.messager_show("该主机未验证，不能启用");
            return false;
        }
        $.messager.confirm('确认对话框', "您确认启用 \"" + hosts[0].host + "\" 主机吗?", function (r) {
            if (r) {
                host_tool.enableHost(hosts[0].id)
            }
        });
    });
    $("#host-forbidden-btn").click(function () {
        var hosts = $("#host_grid").datagrid('getChecked');
        if (hosts.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        if (hosts[0].enable == false) {
            common_tool.messager_show("该主机已经处于禁用状态");
            return false;
        }
        $.messager.confirm('确认对话框', "您确认禁用 \"" + hosts[0].host + "\" 主机吗?", function (r) {
            if (r) {
                host_tool.forbiddenHost(hosts[0].id)
            }
        });
    });
    $("#host-flash-btn").click(function () {
        host_tool.form_clear();
        host_tool.init_main_view();
    });
    $("#host-select-btn").click(function () {
        host_tool.init_main_view();
    });
    $("#host-detail-btn").click(function () {
        var hosts = $("#host_grid").datagrid('getChecked');
        console.log(hosts);
        if (hosts.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        $("#host_form").form('load', {
            id: hosts[0].id,
            "host-summary": hosts[0].summary,
            "host-host": hosts[0].host,
            "host-port": hosts[0].port,
            "host-username": hosts[0].username,
            "host-envPath": hosts[0].envPath,
            "host-isValid": hosts[0].isValid,
            "host-isEnable": hosts[0].isEnable,
            "host-password":hosts[0].password
        });
        $("#host_detail_form").form('load', {
            "host-os-name": hosts[0].osId,
            "host-os-version": hosts[0].osVersion,
            "host-sw-name": hosts[0].swId,
            "host-sw-version": hosts[0].swVersion
        });
        host_tool.init_edit_view(2);
    });

});