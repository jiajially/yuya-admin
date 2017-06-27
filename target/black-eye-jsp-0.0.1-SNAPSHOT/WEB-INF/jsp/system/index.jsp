
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>yuya-admin</title>
    <link type="image/x-icon" rel="shortcut icon" href="${pageContext.request.contextPath}/static/image/favicon.ico">
    <link type="image/x-icon" rel="bookmark" href="${pageContext.request.contextPath}/static/image/favicon.ico">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/icon.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/system/index.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/easyui/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/easyui/datagrid-groupview.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/system/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/system/index.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="easyui-layout">
<div data-options="region:'north',border:false" style="height:40px; background:#198ec2;padding: 0;">
    <div style="height: 40px; float: left; line-height: 40px">
        <span style="font-weight:bolder; font-size: large;padding-left: 10px;color: #ffffff">育芽监控平台（开发）</span>
    </div>
    <div style="float:right;height: 40px;padding: 0;">

    </div>
    <div style="float:right;height: 40px;padding-top: 0;">
        <span style="color: #ffffff ;font-weight: bolder;">欢迎您:测试账户</span> &nbsp;&nbsp;<input
            id="logout-btn" class="easyui-menubutton logout" value="安全退出">
    </div>
</div>
<div data-options="region:'west',border:true,split:true" style="text-align: center;width:160px;">
    <div class="easyui-accordion" data-options="border:false,fit:true,">
        <div title="系统管理" style="padding: 0 0 0 15px;">
            <ul style="list-style: none;padding: 0 0 0 0 ;">
                <div style="padding: 5px 10px;">
                    <li class="nav-list">
                        <div class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="border: 0;"
                             href="${pageContext.request.contextPath}/host/host">主机维护
                        </div>
                    </li>
                </div>
            </ul>
            <ul style="list-style: none;padding: 0 0 0 0 ;">
                <div style="padding: 5px 10px;">
                    <li class="nav-list">
                        <div class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="border: 0;"
                             href="${pageContext.request.contextPath}/ssh/script">SSH脚本配置
                        </div>
                    </li>
                </div>
            </ul>
            <ul style="list-style: none;padding: 0 0 0 0 ;">
                <div style="padding: 5px 10px;">
                    <li class="nav-list">
                        <div class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="border: 0;"
                             href="${pageContext.request.contextPath}/ssh/task">SSH执行状态
                        </div>
                    </li>
                </div>
            </ul>
        </div>
    </div>
</div>
<div data-options="region:'south',boder:true" style="height:26px;">
    <div style="text-align: center;line-height: 24px;font-size: inherit">
        作者:liug&nbsp;QQ:1071245673
    </div>

</div>
<div data-options="region:'center',border:false">
    <div id="tab" class="easyui-tabs" data-options="fit:true">
        <div title="首页">
            <pre>
${banner}
            </pre>
        </div>
    </div>
</div>
</body>
</html>
