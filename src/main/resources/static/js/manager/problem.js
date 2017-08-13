problem_tool = {
    form_clear: function () {
        $("#problem_form").form('reset');
        $("#problem_form").form('clear');
        $("#problem_add_form").form('reset');
        $("#problem_add_form").form('clear');
        $("#problem_info_form").form('reset');
        $("#problem_info_form").form('clear');
        $("#problem_locate_form").form('reset');
        $("#problem_locate_form").form('clear');
        $("#problem_deal_form").form('reset');
        $("#problem_deal_form").form('clear');
        $("#problem-search-form").form('reset');
        $("#problem-search-form").form('clear');
    },

    add_problem:function () {
        $("#problem_add_dialog").dialog({
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
                problem_tool.form_clear();
            },
            buttons: [

                {
                    text: '保存',
                    width: 80,
                    iconCls: 'icon-save',
                    handler: function () {
                        problem_tool.save();

                    }
                },
                {
                    text: '清除',
                    width: 80,
                    iconCls: 'icon-reload',
                    handler: function () {
                        problem_tool.form_clear();
                    }
                },
                {
                    text: '取消',
                    width: 80,
                    iconCls: 'icon-add',
                    handler: function () {
                        problem_tool.form_clear();
                        $("#problem_add_dialog").dialog('close');
                    }
                }
            ]
        })
    },
    deal_problem:function (id) {
        $("#problem_deal_dialog").dialog({
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
                problem_tool.form_clear();
            },
            buttons: [

                {
                    text: '处理',
                    width: 80,
                    iconCls: 'icon-save',
                    handler: function () {
                        problem_tool.deal();

                    }
                },
                {
                    text: '清除',
                    width: 80,
                    iconCls: 'icon-reload',
                    handler: function () {
                        problem_tool.form_clear();
                    }
                },
                {
                    text: '取消',
                    width: 80,
                    iconCls: 'icon-add',
                    handler: function () {
                        problem_tool.form_clear();
                        $("#problem_deal_dialog").dialog('close');
                    }
                }
            ]
        })
    },

    init_main_view: function () {
        var begin = null;
        var end = null;
        $("#problem_grid").datagrid({
            url: getRootPath() + '/manager/problem/list',
            method: 'get',
            idField: "id",
            fitColumns: true,
            toolbar: '#problem-tool-bar',
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
                {title: "问题编号", field: "id", width: 150, sortable: true},
                {title: "问题简述", field: "summary", width: 300, sortable: true},
                {
                    title: "上报时间", field: "date", formatter: function (value, row, index) {
                    date = new Date(value);
                    timeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
                    return timeStr;
                }, width: 150, sortable: true
                },
                {title: "上报渠道", field: "channel", width: 150, sortable: true},
                {title: "上报人", field: "informer", width: 150, sortable: true},
                {title: "处理情况", field: "status", width: 124, sortable: true},
                {
                    title: "完成日期", field: "finishdate", formatter: function (value, row, index) {
                    date = new Date(value);
                    timeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
                    if (value==null || value=='')timeStr="";
                    return timeStr;
                    }, width: 120, sortable: true
                }




            ]],
            onLoadSuccess: function (data) {

            },
            onDblClickRow: function (index, row) {

            }
        });

    },

    save: function () {
        var form_isValid = $("#problem_form").form('validate');
        if (!form_isValid) {
            common_tool.messager_show("请输入必填参数")
        }
        else {
            var summary = $('#problem_add_dialog input[id="problem-summary"]').val();
            var channel = $('#problem_add_dialog input[id="problem-channel"]').val();
            var informer = $('#problem_add_dialog input[id="problem-informer"]').val();

            $.ajax({
                data: {
                    summary:summary,
                    channel: channel,
                    informer: informer,

                },
                traditional: true,
                method: 'post',
                url: getRootPath() + '/manager/problem/insert',
                async: true,
                dataType: 'json',
                beforeSend:function () {
                    common_tool.process_wait("加载中...")
                },
                success: function (result) {
                    common_tool.process_finish();
                    if (result.code == 10000) {
                        $("#problem_add_dialog").dialog("close");
                        problem_tool.form_clear();
                        problem_tool.init_main_view();
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
    problem_valid: function () {
        var form_isValid = $("#problem_form").form('validate');
        if (!form_isValid) {common_tool.messager_show("请输入必填参数");return false;}
        var id = $('#problem_edit_dialog input[id="id"]').val();
        var summary = $('#problem_edit_dialog input[id="problem-summary"]').val();
        var problem = $('#problem_edit_dialog input[id="problem-problem"]').val();
        var port = $('#problem_edit_dialog input[id="problem-port"]').val();
        var username = $('#problem_edit_dialog input[id="problem-username"]').val();
        var password = $('#problem_edit_dialog input[id="problem-password"]').val();


        var problem_os_id = $('#problem-os-name').combobox('getValue');
        var problem_os_name = $('#problem-os-name').combobox('getText');
        var problem_os_version =  $('#problem_detail_form input[id="problem-os-version"]').val();
        var problem_sw_id = $('#problem-sw-name').combobox('getValue');
        var problem_sw_name = $('#problem-sw-name').combobox('getText');
        var problem_sw_version =  $('#problem_detail_form input[id="problem-sw-version"]').val();
        if (id==null||id=="")id=-1;
        $.ajax({
            data: {
                id: id,
                summary:summary,
                problem: problem,
                port: port,
                username: username,
                password:password,

                osTypeId:problem_os_id,
                osversion:problem_os_version,
                swTypeId:problem_sw_id,
                swversion:problem_sw_version,
                osName:problem_os_name,
                swName:problem_sw_name
            },
            traditional: true,
            method: 'post',
            url: getRootPath() + '/problem/valid',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                common_tool.process_finish();
                if (result.code == 10000) {
                    $("#problem_edit_dialog").dialog("close");
                    problem_tool.form_clear();
                    problem_tool.init_main_view();
                    common_tool.messager_show("验证成功");
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            }
        });
    },
    deal: function () {
        //var form_isValid = $("#problem_form").form('validate');
        //if (!form_isValid) {
        //    common_tool.messager_show("请输入必填参数")
        //}else {
            var id = $('#problem_deal_dialog input[id="problemId"]').val();

            var problem_detail =$('#problem_locate_form input[id="problem-detail"]').val();
            var problem_type = $('#problem-type').combobox('getValue');
            var problem_level =  $('#problem-level').combobox('getValue');
            var problem_system =$('#problem_locate_form input[id="problem-system"]').val();
            var needtime =$('#problem_deal_form input[id="problem-needtime"]').val();
            var needresources =$('#problem_deal_form input[id="problem-needresources"]').val();
            var result =$('#problem_deal_form input[id="problem-result"]').val();
            var dealer =$('#problem_deal_form input[id="problem-dealer"]').val();
            var recorder =$('#problem_deal_form input[id="problem-recorder"]').val();

            $.ajax({
                data: {
                    id: id,
                    detail:problem_detail,
                    type: problem_type,
                    level: problem_level,
                    system: problem_system,

                    needtime:needtime,
                    needresources:needresources,
                    result:result,
                    dealer:dealer,
                    recorder:recorder
                },
                traditional: true,
                method: 'post',
                url: getRootPath() + '/manager/problem/deal',
                async: true,
                dataType: 'json',
                beforeSend:function () {
                    common_tool.process_wait("加载中...")
                },
                success: function (result) {
                    common_tool.process_finish();
                    if (result.code == 10000) {
                        $("#problem_deal_dialog").dialog("close");
                        problem_tool.form_clear();
                        problem_tool.init_main_view();
                        common_tool.messager_show(result.msg);
                        return false;
                    }
                    else {
                        common_tool.messager_show(result.msg);
                    }
                }
            });

        //}
    },
    delete: function (id) {
        $.ajax({
            data: {
                id: id
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/problem/delete',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                common_tool.process_finish();
                if (result.code == 10000) {
                    problem_tool.form_clear();
                    problem_tool.init_main_view();
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
    problem_tool.init_main_view();
    $("#problem-add-btn").click(function () {
        var now =new Date();
        $("#problem_form").form('load', {
            "problem-date":now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds(),
        });
        problem_tool.add_problem();
    });
    $("#problem-deal-btn").click(function () {
        var problems = $("#problem_grid").datagrid('getChecked');
        var problem ;
        $.ajax({
            data: {
                id:problems[0].id

            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/manager/problem/detail',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.code == 10000) {
                    problem = result.data;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            }
        });
        if (problems.length == 0) {
            common_tool.messager_show("请选中你要处理的问题");
            return false;
        }
        $("#problem_info_form").form('load', {
            problemId: problems[0].id,
            "problem-info-summary": problems[0].summary,
            "problem-info-date": problems[0].date,
            "problem-info-channel": problems[0].channel,
            "problem-info-informer": problems[0].informer,
            "problem-info-status": problems[0].status,
            "problem-info-finishdate": problems[0].finishdate
        });
        $("#problem_locate_form").form('load', {
            "problem-detail": problem.detail,
            "problem-type": problem.type,
            "problem-level": problem.level,
            "problem-system": problem.system
        });
        $("#problem_deal_form").form('load', {
            "problem-needtime": problem.needtime,
            "problem-needresources": problem.needresources,
            "problem-result": problem.result,
            "problem-dealer": problem.dealer,
            "problem-recorder": problem.recorder
        });
        problem_tool.deal_problem(problems[0].id);
    });
    $("#problem-delete-btn").click(function () {
        var problems = $("#problem_grid").datagrid('getChecked');
        if (problems.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        $.messager.confirm('确认对话框', "您确认删除该条记录吗?", function (r) {
            if (r) {
                problem_tool.delete(problems[0].id);
            }
        });
    });

    $("#problem-flash-btn").click(function () {
        problem_tool.form_clear();
        problem_tool.init_main_view();
    });
    $("#problem-select-btn").click(function () {
        console.log($('#problem-search-begin').datetimebox('getValue'));
        problem_tool.init_main_view();
    });
    $("#problem-detail-btn").click(function () {
        var problems = $("#problem_grid").datagrid('getChecked');
        console.log(problems);
        if (problems.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        $("#problem_form").form('load', {
            id: problems[0].id,
            "problem-summary": problems[0].summary,
            "problem-problem": problems[0].problem,
            "problem-port": problems[0].port,
            "problem-username": problems[0].username,
            "problem-envPath": problems[0].envPath,
            "problem-isValid": problems[0].isValid,
            "problem-isEnable": problems[0].isEnable,
            "problem-password":problems[0].password
        });
        $("#problem_detail_form").form('load', {
            "problem-os-name": problems[0].osId,
            "problem-os-version": problems[0].osVersion,
            "problem-sw-name": problems[0].swId,
            "problem-sw-version": problems[0].swVersion
        });
        problem_tool.init_edit_view(2);
    });

});