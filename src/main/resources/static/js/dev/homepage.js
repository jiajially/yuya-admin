homepage_tool = {
    form_clear: function () {
        $("#homepage_form").form('reset');
        $("#homepage_form").form('clear');
    },
    load_homepage: function (count) {
        var hp_tmp_data=[];
        $.ajax({
            data: {

            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/dev/homepage/select',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.code == 10000) {
                    hp_tmp_data = result.data;
                }
            }
        });

        var html_data ='';
        for (var i=0;i<hp_tmp_data.length;i++){
            var tmp_date = new Date(hp_tmp_data[i].updateTime);
            var formate_date = tmp_date.getFullYear()+"年"+
                (tmp_date.getMonth()+1)+"月"+
                tmp_date.getDate()+"日"+" "+
                tmp_date.getHours()+":"+tmp_date.getMinutes()+":"+tmp_date.getSeconds();
            html_data+=
                /*"<div style='margin-left: 20px;margin-top: 20px;margin-bottom:10px;float: left'>" +
                "<div id='homepage-p"+i+"' style='background:#fafafa;'>" +
                "<p style='margin-left: 10px' >描述："+hp_tmp_data[i].description+"</p> " +
                "<p style='margin-left: 10px' >结果："+hp_tmp_data[i].result+"</p> " +
                "<p style='margin-left: 10px' >单位："+hp_tmp_data[i].unit+"</p> " +
                "<p style='margin-left: 10px' >更新："+hp_tmp_data[i].update+"</p> " +
                "</div>" +
                "</div>"*/
                "<div style='margin-left: 20px;margin-top: 20px;margin-bottom:10px;float: left'>" +
                "<div id='homepage-p"+i+"' class=\"easyui-panel\" style=\"width:320px;height:160px\""+
                "title='"+hp_tmp_data[i].title+"' data-options=\"tools:'#tt"+i+"'\">"+
                "<p style='margin-left: 10px' >描述："+hp_tmp_data[i].description+"</p> " +
                "<p style='margin-left: 10px' >结果："+hp_tmp_data[i].result+"</p> " +
                "<p style='margin-left: 10px' >单位："+hp_tmp_data[i].unit+"</p> " +
                "<p style='margin-left: 10px' >更新："+formate_date+"</p> " +
                "</div>"+
                "<div id=\"tt"+i+"\">"+
                "<a href=\"#\" class=\"icon-cancel\" onclick=\"javascript:homepage_tool.toolbox('"+hp_tmp_data[i].id+"',1)\"></a>"+
                "<a href=\"#\" class=\"icon-reload\" onclick=\"javascript:homepage_tool.toolbox('"+hp_tmp_data[i].id+"',2)\"></a>"+
                "</div>" +
                "</div>";
        };
        $("#homepage_data").html(html_data);

        for (var i=0;i<hp_tmp_data.length;i++){
            $('#homepage-p'+i+'').panel({
                width:320,
                height:160,
                title:hp_tmp_data[i].title,
                /*tools:[{
                    iconCls:'icon-reload',
                    handler:function(){
                        console.log( $("#homepage_data"));
                    }
                },{
                    iconCls:'icon-reload',
                    handler:function(){alert('更新结果')}
                },
                ]*/
            });
        }
    },
    save: function () {
        var form_isValid = $("#homepage_form").form('validate');
        if (!form_isValid) {
            common_tool.messager_show("请输入必填参数")
        }
        else {
            var host = $('#homepage-host').combobox('getValue');
            var title = $('#homepage_edit_dialog input[id="homepage-title"]').val();
            var description = $('#homepage_edit_dialog input[id="homepage-description"]').val();
            var unit = $('#homepage_edit_dialog input[id="homepage-unit"]').val();
            var cmd = $('#homepage_edit_dialog input[id="homepage-cmd"]').val();
            $.ajax({
                data: {
                    host: host,
                    title: title,
                    description: description,
                    unit: unit,
                    cmd: cmd,
                },
                traditional: true,
                method: 'post',
                url: getRootPath() + '/dev/homepage/insert',
                async: false,
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (result.code == 10000) {
                        $("#homepage_edit_dialog").dialog("close");
                        homepage_tool.form_clear();
                        $("#homepage_data").html('');
                        homepage_tool.load_homepage();
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
    init_edit_view: function (type) {
        var _tmpTitle = "新增指令";
        if (type == 2){
            _tmpTitle = "修改指令";
        }


        $("#homepage_edit_dialog").dialog({
            title: _tmpTitle,
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
               homepage_tool.form_clear();
            },
            buttons: [

                {
                    text: '保存',
                    width: 100,
                    iconCls: 'icon-save',
                    handler: function () {
                        if (type == 1) {
                            homepage_tool.save();
                        }
                        if (type == 2) {
                            homepage_tool.update();
                        }
                    }
                },
                {
                    text: '清除',
                    width: 100,
                    iconCls: 'icon-reload',
                    handler: function () {
                        homepage_tool.form_clear();
                    }
                },
                {
                    text: '取消',
                    width: 100,
                    iconCls: 'icon-add',
                    handler: function () {
                        homepage_tool.form_clear();
                        $("#host_edit_dialog").dialog('close');
                    }
                }
            ]
        })
    },
    load_combox: function () {
        $("#homepage-host").combobox({
            url:getRootPath() + '/host/select',
            method:'GET',
            valueField:'id',
            textField:'text',
            onShowPanel:function(){
                $("#homepage-host").combobox('reload',getRootPath() + '/host/select');
            },
        });
    },
    toolbox: function (id,type) {
            $.ajax({
                data: {
                    id: id,
                    type: type
                },
                traditional: true,
                method: 'get',
                url: getRootPath() + '/dev/homepage/toolbox',
                async: false,
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (result.code == 10000) {
                        homepage_tool.form_clear();
                        $("#homepage_data").html('');
                        homepage_tool.load_homepage();
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
    homepage_tool.load_homepage();
    homepage_tool.load_combox();
    $("#homepage-add-btn").click(function () {
        homepage_tool.init_edit_view(1);
    });

    $("#homepage-flash-btn").click(function () {
        $.ajax({
            data: {

            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/dev/homepage/flash',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.code == 10000) {
                    common_tool.messager_show(result.msg);
                }
            }
        });
        homepage_tool.load_homepage();
    });

});