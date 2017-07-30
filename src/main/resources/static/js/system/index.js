index_tool = {

    load_homepage: function (count) {
        var data=[
            {
                title:"1",
            }
        ];
        var html_data ='';
        for (var i=1;i<=count;i++){
            html_data+=
                "<div style='margin-left: 10px;margin-top: 10px;float: left'>" +
                    "<div id='p"+i+"' style='background:#fafafa;'>" +
                        "<p>panel content.</p> " +
                        "<p>panel content.</p> " +
                    "</div>" +
                "</div>"
        };
        $("#data").html(html_data);

        for (var i=1;i<=count;i++){
        $('#p'+i+'').panel({
            width:300,
            height:150,
            title:'My Panel',
            tools:[{
                iconCls:'icon-add',
                handler:function(){alert('new')}
            }
            ]
        });
        }
    },
};
$(document).ready(function () {
    index_tool.load_homepage(5);
    $("#logout-btn").click(function () {
        $.messager.confirm('确认对话框', "您确认退出系统吗?", function (r) {
            if (r) {
                $.ajax({
                    data: {},
                    method: 'get',
                    url: getRootPath() + '/system/logout',
                    async: false,
                    dataType: 'json',
                    success: function (result) {
                        location = getRootPath();
                    },
                });
            }
        });
    });
    $(".easyui-linkbutton").click(function () {
        var title = $(this).text();
        var href = $(this).attr("href");
        if ($('#tab').tabs('exists', title)) {
            $('#tab').tabs('select', title);
        } else {
            if ((href.indexOf('druid/angular2-index.html') != -1)) {
                var content = '<iframe scrolling="true" frameborder="0"  src="' + href + '" style="width:100%;height:100%;"></iframe>';
                $('#tab').tabs('add', {
                    tabWidth: 100,
                    tabHeight: 35,
                    fit: true,
                    title: title,
                    closable: true,
                    content: content,
                    border: true,
                    onLoadError: function () {
                        $('#tab').tabs('close',title)
                        $('#tab').tabs('add', {
                            tabWidth: 100,
                            tabHeight: 35,
                            fit: true,
                            title: '404',
                            closable: true,
                            href: getRootPath()+"/error/notFound",
                            border: true,
                        });
                    },
                });
            } else if (href.indexOf('swagger-ui.html') != -1) {
                window.open(href)
            }
            else {
                $('#tab').tabs('add', {
                    tabWidth: 100,
                    tabHeight: 35,
                    fit: true,
                    title: title,
                    closable: true,
                    href: href,
                    border: true,
                    onLoadError: function () {
                        $('#tab').tabs('close',title)
                        $('#tab').tabs('add', {
                            tabWidth: 100,
                            tabHeight: 35,
                            fit: true,
                            title: '404',
                            closable: true,
                            href: getRootPath()+"/error/notFound",
                            border: true,
                        });
                    },
                });
            }
            return false;
        }
    });

});
