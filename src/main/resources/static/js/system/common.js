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
    },
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
//设置cookie
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}
//获取cookie
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}
//清除cookie
function clearCookie(name) {
    setCookie(name, "", -1);
}
function checkCookie() {
    var user = getCookie("username");
    if (user != "") {
        alert("Welcome again " + user);
    } else {
        user = prompt("Please enter your name:", "");
        if (user != "" && user != null) {
            setCookie("username", user, 365);
        }
    }
}
//checkCookie();

function getSession() {

    $.ajax({
        data: {},
        traditional: true,
        method: 'get',
        url: getRootPath() + '/system/session',
        async: false,
        dataType: 'json',
        success: function (result) {
            if (result.code == 10000) {
                //console.log(result.data);
              setCookie("username",result.data.zhName,1);
            }
            else {
                common_tool.messager_show(result.msg);
            }
        },
    });


}
getSession();



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


