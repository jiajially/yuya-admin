user_tool = {
    form_clear: function () {
        $("#auth_user_form").form('reset');
        $("#auth_user_form").form('clear');
        $("#auth_init_password_form").form('reset');
        $("#auth_init_password_form").form('clear');
        $("#auth-user-search-form").form('reset');
        $("#auth-user-search-form").form('clear');
        $("#auth-user-permissions").datagrid("uncheckAll");
        $("#auth_jobs").treegrid("uncheckAll");
        $("#auth_user_grid").treegrid("uncheckAll");
    },
    init_main_view: function () {
        var loginName = $("input[name='auth-user-search-loginName']").val();
        var zhName = $("input[name='auth-user--search-zhName']").val();
        var email = $("input[name='auth-user-search-email']").val();
        var phone = $("input[name='auth-user-search-phone']").val();
        var address = $("input[name='auth-user-search-address']").val();
        $("#auth_user_grid").datagrid({
            url: getRootPath() + '/user/list',
            method: 'get',
            idField: "id",
            fitColumns: true,
            toolbar: '#user-tool-bar',
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
                loginName: loginName,
                zhName: zhName,
                email: email,
                phone: phone,
                address: address,
            },
            columns: [[
                {title: "选择", field: "ck", checkbox: true},
                {title: "中文名", field: "zhName", width: 124, sortable: true},
                {title: "登录名", field: "loginName", width: 124, sortable: true},
                {title: "英文名", field: "enName", width: 124, sortable: true},
                {
                    title: "性别", width: 35, field: "sex", formatter: function (value, row, index) {
                    if (value == 1) {
                        return "男";
                    }
                    if (value == 2) {
                        return "女";
                    }
                }
                },
                {
                    title: "状态", field: "status", align: 'center', width: 87, formatter: function (value, row, index) {
                    if (value == 1) {
                        return "<input class='easyui-switchbutton status' checked />";
                    }
                    if (value == 3) {
                        return "<input class='easyui-switchbutton status' unchecked />";
                    }

                }
                },
                {title: "生日", field: "birth", width: 90},
                {title: "邮箱", field: "email", width: 130, sortable: true},
                {title: "电话", field: "phone", width: 130, sortable: true},
                {title: "地址", field: "address", width: 400},
                {
                    title: "可修改", field: "isFinal", formatter: function (value, row, index) {
                    if (value == 1) {
                        return "是";
                    }
                    if (value == 2) {
                        return "否";
                    }
                }, width: 50
                },
                {
                    title: "创建时间", field: "createTime", formatter: function (value, row, index) {
                    date = new Date(value);
                    timeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
                    return timeStr;
                }, width: 200
                },
                {
                    title: "更新时间", field: "updateTime", formatter: function (value, row, index) {
                    return common_tool.timestampToDateTime(value);
                }, width: 200
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
            onDblClickRow: function (index, row) {
                var users = $("#user_grid").datagrid('getChecked');
                if (users.length == 0) {
                    common_tool.messager_show("请至少选择一条记录");
                    return false;
                }
                $("#user_form").form('load', {
                    id: users[0].id,
                    loginName: users[0].loginName,
                    zhName: users[0].zhName,
                    enName: users[0].enName,
                    sex: users[0].sex,
                    birth: users[0].birth,
                    email: users[0].email,
                    phone: users[0].phone,
                    address: users[0].address,
                    password: '111111111111',
                });
                $("#user_edit_dialog").dialog({
                    title: '查看用户详情',
                    iconCls: 'icon-save',
                    closable: true,
                    width: 950,
                    height: 700,
                    cache: false,
                    modal: true,
                    resizable: false,
                    'onBeforeOpen': function () {

                    },
                    'onOpen': function () {
                        var users = $("#user_grid").datagrid('getChecked')[0];
                        for (var i = 0; i < users.permissions.length; i++) {
                            $("#user-permissions").datagrid("selectRecord", users.permissions[i].id);
                        }
                        for (var i = 0; i < users.userRoleOrganizations.length; i++) {
                            $("#jobs").treegrid("select", users.userRoleOrganizations[i].sysRoleOrganizationId);
                        }
                    },
                    'onClose': function () {
                        user_tool.form_clear();
                    },
                })
            }
        });
    },
    init_edit_view: function (type) {
        $("#auth_user_edit_dialog").dialog({
            title: '新增用户',
            iconCls: 'icon-save',
            closable: true,
            width: 400,
            height: 700,
            cache: false,
            modal: true,
            resizable: false,
            'onBeforeOpen': function () {

            },
            'onOpen': function () {
                if (type == 2) {
                    var users = $("#auth_user_grid").datagrid('getChecked')[0];
                    for (var i = 0; i < users.permissions.length; i++) {
                        $("#auth-user-permissions").datagrid("selectRecord", users.permissions[i].id);
                    }
                    for (var i = 0; i < users.userRoleOrganizations.length; i++) {
                        $("#auth-jobs").treegrid("select", users.userRoleOrganizations[i].sysRoleOrganizationId);
                    }
                }
            },
            'onClose': function () {
                user_tool.form_clear();
            },
            buttons: [
                {
                    text: '保存',
                    width: 100,
                    iconCls: 'icon-save',
                    handler: function () {
                        if (type == 1) {
                            user_tool.save();
                        }
                        if (type == 2) {
                            user_tool.update();
                        }
                    }
                },
                {
                    text: '清除',
                    width: 100,
                    iconCls: 'icon-reload',
                    handler: function () {
                        user_tool.form_clear();
                    }
                },
                {
                    text: '取消',
                    width: 100,
                    iconCls: 'icon-add',
                    handler: function () {
                        user_tool.form_clear();
                        $("#auth_user_edit_dialog").dialog('close');
                    }
                }
            ],
        })
    },

    save: function () {
        var form_isValid = $("#auth_user_form").form('validate');
        if (!form_isValid) {
            common_tool.messager_show("请输入必填参数")
        } else if ($("#auth-jobs").treegrid("getChecked").length == 0) {
            common_tool.messager_show('请选择职位');
        }
        // else if ($("#user-permissions").datagrid("getChecked").length == 0) {
        //     common_tool.messager_show('请选择权限');
        // } 
        else {
            var loginName = $('#auth_user_edit_dialog input[id="auth-uaer-loginName"]').val();
            var zhName = $('#auth_user_edit_dialog input[id="auth-uaer-zhName"]').val();
            var enName = $('#auth_user_edit_dialog input[id="auth-uaer-enName"]').val();
            var sex = $('#auth_user_edit_dialog select[id="auth-uaer-sex"]').combobox('getValue');
            var birth = $('#auth_user_edit_dialog input[id="auth-uaer-birth"]').datebox('getValue');
            var email = $('#auth_user_edit_dialog input[id="auth-uaer-email"]').val();
            var phone = $('#auth_user_edit_dialog input[id="auth-uaer-phone"]').val();
            var address = $('#auth_user_edit_dialog input[id="auth-uaer-address"]').val();
            var password = $('#auth_user_edit_dialog input[id="auth-uaer-password"]').val();
            var permissions = $('#auth_user_edit_dialog table[id="auth-uaer-permissions"]').datagrid("getChecked");
            var permissionIds = new Array();
            for (var i = 0; i < permissions.length; i++) {
                permissionIds[i] = permissions[i].id;
            }
            var jobs = $('#user_edit_dialog table[id="auth-jobs"]').treegrid("getChecked");
            var jobIds = new Array();
            for (var i = 0; i < jobs.length; i++) {
                jobIds[i] = jobs[i].id;
            }
            $.ajax({
                data: {
                    loginName: loginName,
                    zhName: zhName,
                    enName: enName,
                    sex: sex,
                    birth: birth,
                    email: email,
                    phone: phone,
                    address: address,
                    password: password,
                    permissionIds: permissionIds.toString(),
                    jobIds: jobIds.toString(),
                },
                traditional: true,
                method: 'post',
                url: getRootPath() + '/user/insert',
                async: false,
                dataType: 'json',
                success: function (result) {
                    if (result.code == 10000) {
                        $("#user_edit_dialog").dialog("close");
                        user_tool.form_clear();
                        user_tool.init_main_view();
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
        var form_isValid = $("#user_form").form('validate');
        if (!form_isValid) {
            common_tool.messager_show("请输入必填参数")
        } else if ($("#jobs").treegrid("getChecked").length == 0) {
            common_tool.messager_show('请选择职位');
        }
        // else if ($("#user-permissions").datagrid("getChecked").length == 0) {
        //     common_tool.messager_show('请选择权限');
        // }
        else {
            var id = $('#user_edit_dialog input[id="id"]').val();
            var loginName = $('#auth_user_edit_dialog input[id="auth-uaer-loginName"]').val();
            var zhName = $('#auth_user_edit_dialog input[id="auth-uaer-zhName"]').val();
            var enName = $('#auth_user_edit_dialog input[id="auth-uaer-enName"]').val();
            var sex = $('#auth_user_edit_dialog select[id="auth-uaer-sex"]').combobox('getValue');
            var birth = $('#auth_user_edit_dialog input[id="auth-uaer-birth"]').datebox('getValue');
            var email = $('#auth_user_edit_dialog input[id="auth-uaer-email"]').val();
            var phone = $('#auth_user_edit_dialog input[id="auth-uaer-phone"]').val();
            var address = $('#auth_user_edit_dialog input[id="auth-uaer-address"]').val();
            var password = $('#auth_user_edit_dialog input[id="auth-uaer-password"]').val();
            var permissions = $('#auth_user_edit_dialog table[id="auth-uaer-permissions"]').datagrid("getChecked");
            var permissionIds = new Array();
            for (var i = 0; i < permissions.length; i++) {
                permissionIds[i] = permissions[i].id;
            }
            var jobs = $('#user_edit_dialog table[id="auth-jobs"]').treegrid("getChecked");
            var jobIds = new Array();
            for (var i = 0; i < jobs.length; i++) {
                jobIds[i] = jobs[i].id;
            }
            $.ajax({
                data: {
                    id: id,
                    loginName: loginName,
                    zhName: zhName,
                    enName: enName,
                    sex: sex,
                    birth: birth,
                    email: email,
                    phone: phone,
                    address: address,
                    permissionIds: permissionIds.toString(),
                    jobIds: jobIds.toString(),
                },
                traditional: true,
                method: 'post',
                url: getRootPath() + '/user/update',
                async: false,
                dataType: 'json',
                success: function (result) {
                    if (result.code == 10000) {
                        $("#user_edit_dialog").dialog("close");
                        user_tool.form_clear();
                        user_tool.init_main_view();
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
            url: getRootPath() + '/user/delete',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.code == 10000) {
                    user_tool.form_clear();
                    user_tool.init_main_view();
                    common_tool.messager_show(result.msg);
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            },
        });
    },
    password_view: function (user) {
        $("#password_edit_dialog").dialog({
            title: '重置 ' + user.zhName + ' 密码',
            iconCls: 'icon-save',
            closable: true,
            width: 450,
            height: 250,
            cache: false,
            modal: true,
            resizable: false,
            'onClose': function () {
                user_tool.form_clear();
            },
            buttons: [
                {
                    text: '保存',
                    width: 100,
                    iconCls: 'icon-save',
                    handler: function () {
                        user_tool.update_password(user.id);
                    }
                },
                {
                    text: '清除',
                    width: 100,
                    iconCls: 'icon-reload',
                    handler: function () {
                        user_tool.form_clear();
                    }
                },
                {
                    text: '取消',
                    width: 100,
                    iconCls: 'icon-add',
                    handler: function () {
                        user_tool.form_clear();
                        $("#password_edit_dialog").dialog('close');
                    }
                }
            ],
        })
    },
    update_password: function (id) {
        var newPassword = $("#newPassword").val();
        var repeatNewPassword = $("#repeatNewPassword").val();
        $.ajax({
            data: {
                id: id,
                newPassword: newPassword,
                repeatNewPassword: repeatNewPassword,
            },
            traditional: true,
            method: 'post',
            url: getRootPath() + '/user/updatePassword',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.code == 10000) {
                    $("#password_edit_dialog").dialog("close");
                    user_tool.form_clear();
                    user_tool.init_main_view();
                    common_tool.messager_show(result.msg);
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            },
        });
    },
    forbiddenUser: function (id) {
        $.ajax({
            data: {
                id: id,
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/user/forbiddenUser',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.code == 10000) {
                    user_tool.form_clear();
                    user_tool.init_main_view();
                    common_tool.messager_show(result.msg);
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            },
        });
    },
    enableUser: function (id) {
        $.ajax({
            data: {
                id: id,
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/user/enableUser',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.code == 10000) {
                    user_tool.form_clear();
                    user_tool.init_main_view();
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
    user_tool.init_main_view();
    $("#auth-user-save-btn").click(function () {
        user_tool.init_edit_view(1);
    });
    $("#auth-user-update-btn").click(function () {
        var users = $("#user_grid").datagrid('getChecked');
        if (users.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        $("#auth_user_form").form('load', {
            id: users[0].id,
            loginName: users[0].loginName,
            zhName: users[0].zhName,
            enName: users[0].enName,
            sex: users[0].sex,
            birth: users[0].birth,
            email: users[0].email,
            phone: users[0].phone,
            address: users[0].address,
            password: '111111111111',
        });
        user_tool.init_edit_view(2);
    });
    $("#auth-user-delete-btn").click(function () {
        var users = $("#user_grid").datagrid('getChecked');
        if (users.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        $.messager.confirm('确认对话框', "您确认删除该条记录吗?", function (r) {
            if (r) {
                user_tool.delete(users[0].id);
            }
        });
    });
    $("#auth-user-detail-btn").click(function () {

    });
    $("#auth-user-enable-btn").click(function () {
        var users = $("#user_grid").datagrid('getChecked');
        if (users.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        if (users[0].status == 1) {

            common_tool.messager_show("该账号已经处于启用状态");
            return false;
        }
        $.messager.confirm('确认对话框', "您确认启用 " + users[0].zhName + " 账号吗?", function (r) {
            if (r) {
                user_tool.enableUser(users[0].id)
            }
        });
    });
    $("#auth-user-forbidden-btn").click(function () {
        var users = $("#auth_user_grid").datagrid('getChecked');
        if (users.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        if (users[0].status == 3) {
            common_tool.messager_show("该账号已经处于禁用状态");
            return false;
        }
        $.messager.confirm('确认对话框', "您确认禁用 " + users[0].zhName + " 账号吗?", function (r) {
            if (r) {
                user_tool.forbiddenUser(users[0].id)
            }
        });
    });
    $("#auth-user-flash-btn").click(function () {
        user_tool.form_clear();
        user_tool.init_main_view();
    });
    $("#auth-user-log-select-btn").click(function () {
        user_tool.init_main_view();
    });
    $("#auth-user-password-btn").click(function () {
        var users = $("#user_grid").datagrid('getChecked');
        if (users.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        user_tool.password_view(users[0]);
    });
});