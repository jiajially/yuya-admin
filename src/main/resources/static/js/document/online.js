
$(document).ready(function () {
    var index = 1;
    var type = "online";

    document_tool.init_main_view(null,null,type,index);

    $("#document-"+type+"-flash-btn").click(function () {
        document_tool.init_main_view(null,null,type,index);
    });
    $("#document-"+type+"-detail-btn").click(function () {
        document_tool.load_detail(type);
    });
    $("#document-"+type+"-add-btn").click(function () {

        $("#document_online_add_dialog").dialog({
            title: "新增在线文档",
            iconCls: 'icon-save',
            closable: true,
            width: 400,
            height: 270,
            cache: false,
            modal: true,
            resizable: false,
            'onBeforeOpen': function () {

            },
            'onOpen': function () {

            },
            'onClose': function () {
                document_tool.form_clear(type);
            },
            buttons: [

                {
                    text: '保存',
                    width: 80,
                    iconCls: 'icon-save',
                    handler: function () {
                        var form_isValid = $("#document_online_form").form('validate');
                        if (!form_isValid) {
                            common_tool.messager_show("请输入必填参数")
                        }
                        else {
                            var summary = $('#document_online_add_dialog input[id="document-online-summary"]').val();
                            var url = $('#document_online_add_dialog input[id="document-online-url"]').val();
                            alert(summary);
                            $.ajax({
                                data: {
                                    summary:summary,
                                    url: url,

                                },
                                traditional: true,
                                method: 'post',
                                url: getRootPath() + '/document/online/insert',
                                async: true,
                                dataType: 'json',
                                beforeSend:function () {
                                    common_tool.process_wait("加载中...")
                                },
                                success: function (result) {
                                    common_tool.process_finish();
                                    if (result.code == 10000) {
                                        $("#document_online_add_dialog").dialog("close");
                                        document_tool.form_clear(type);
                                        document_tool.init_main_view(null,null,type,index);
                                        common_tool.messager_show(result.msg);
                                        return false;
                                    }
                                    else {
                                        common_tool.messager_show(result.msg);
                                    }
                                }
                            });

                        }
                    }
                },
                {
                    text: '取消',
                    width: 80,
                    iconCls: 'icon-add',
                    handler: function () {
                        document_tool.form_clear(type);
                        $("#document_online_add_dialog").dialog('close');
                    }
                }
            ]
        })


    });
    $("#document-"+type+"-select-btn").click(function () {
        var begin = $("#document-"+type+"-search-begin").datebox('getValue');
        var end = $("#document-"+type+"-search-end").datebox('getValue');
        document_tool.init_main_view(begin,end,type,index);
    });

    $("#document-"+type+"-delete-btn").click(function () {
        $.messager.confirm('确认对话框', "您确认删除该文档么?", function (r) {
            if (r) {
                document_tool.delete(type);
                document_tool.init_main_view(null,null,type,index);
            }
        });

    });


});