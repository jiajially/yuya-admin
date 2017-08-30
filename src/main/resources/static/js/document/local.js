
$(document).ready(function () {
    var index = 2;
    var type = "local";


    document_tool.init_main_view(null,null,type,index);

    $("#document-"+type+"-flash-btn").click(function () {
        document_tool.init_main_view(null,null,type,index);
    });
    $("#document-"+type+"-detail-btn").click(function () {
        document_tool.load_detail(type);
    });

    $("#document-"+type+"-add-btn").click(function () {

        $("#document_"+type+"_add_dialog").dialog({
            title: "上传文档",
            iconCls: 'icon-save',
            closable: true,
            width: 300,
            height: 180,
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
                    text: '上传',
                    width: 80,
                    iconCls: 'icon-save',
                    handler: function () {
                        $("#document_"+type+"_form").form('submit', {
                            success: function (result) {
                                var _result = JSON.parse(result);
                                common_tool.messager_show(_result.msg);
                                $("#document_"+type+"_add_dialog").dialog('close');
                                document_tool.init_main_view(null,null,type,index);
                                document_tool.form_clear(type);
                            }
                        });
                    }
                },
                {
                    text: '取消',
                    width: 80,
                    iconCls: 'icon-add',
                    handler: function () {
                        document_tool.form_clear(type);
                        $("#document_"+type+"_add_dialog").dialog('close');
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