//右下角显示消息
common_tool = {
    messager_show: function (msg) {
        $.messager.show({
            title: '系统提示',
            msg: msg,
            timeout: 2000,
            showType: 'slide'
        });
    },
    timestampToDateTime: function (value) {
        date = new Date(value);
        timeStr = date.getFullYear() + "-" + (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + "-" + date.getDate() + " " + (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ":" + (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ":" + (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
        return timeStr;
    },
    process_wait:function (msg) {
        $("#tab").tabs("loading",msg);
    },
    process_finish:function () {
        $("#tab").tabs("loaded")
    }
};
function getRootPath() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.lastIndexOf(pathName);
    var localhostPatht = curWwwPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1)
    //console.log(localhostPatht);
    return (localhostPatht /*+ projectName*/);
};
(function () {
    $.extend($.fn.tabs.methods, {
        //显示遮罩
        loading: function (jq, msg) {
            return jq.each(function () {
                var panel = $(this).tabs("getSelected");
                if (msg == undefined) {
                    msg = "正在加载数据，请稍候...";
                }
                $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: panel.width(), height: panel.height() }).appendTo(panel);
                $("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo(panel).css({ display: "block", left: (panel.width() - $("div.datagrid-mask-msg", panel).outerWidth()) / 2, top: (panel.height() - $("div.datagrid-mask-msg", panel).outerHeight()) / 2 });
            });
        }
        ,
        //隐藏遮罩
        loaded: function (jq) {
            return jq.each(function () {
                var panel = $(this).tabs("getSelected");
                panel.find("div.datagrid-mask-msg").remove();
                panel.find("div.datagrid-mask").remove();
            });
        }
    });
})(jQuery);


