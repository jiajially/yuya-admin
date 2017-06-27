<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/system/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/ssh/host.js"></script>
<div id="host-tool-bar" style="padding: 10px 10px 0 10px">
    <form id="host-search-form">
        <div class="easyui-linkbutton " id="host-flash-btn" data-options="iconCls:'icon-reload'" style="width:70px">刷新
        </div>
        <div class="easyui-linkbutton " id="host-save-btn" data-options="iconCls:'icon-add'" style="width:70px">新增</div>
        <div class="easyui-linkbutton " id="host-update-btn" data-options="iconCls:'icon-edit'" style="width:70px">修改
        </div>
        <div class="easyui-linkbutton " id="host-delete-btn" data-options="iconCls:'icon-remove'" style="width:70px">删除
        </div>
        <%--<div class="easyui-linkbutton " id="user-detail-btn" data-options="iconCls:'icon-edit'" style="width:90px">查看详情--%>
        <%--</div>--%>
        <div class="easyui-linkbutton " id="host-enable-btn" data-options="iconCls:'icon-add'" style="width:70px">启用
        </div>
        <div class="easyui-linkbutton " id="host-forbidden-btn" data-options="iconCls:'icon-remove'" style="width:70px">
            禁用
        </div>
        <span style="line-height: 26px; ">主机:<input name="host-search-host" class="easyui-textbox"
                                                     style="line-height: 26px; "></span>
        <span style="line-height: 26px; ">PATH:<input name="host-search-envPath" class="easyui-textbox"
                                                    style="line-height: 26px; "></span>
        <div class="easyui-linkbutton " id="host-select-btn" data-options="iconCls:'icon-search'" style="width:70px">搜索
        </div>
    </form>
</div>
<div id="host_grid" style="padding: 10px">

</div>
<div id="host_edit_dialog">
    <form id="host_form">
        <input type="hidden" name="id" id="id">
        <div style="float: left;height: 200px;">
            <p style="padding: 10px;">主&nbsp;&nbsp;机:<input name="host-host" id="host-host" style="width: 300px;height: 35px"
                                                      data-options="required:true"
                                                      class="easyui-textbox easyui-validatebox"></p>
            <p style="padding: 10px ;">端&nbsp;&nbsp;口:<input name="host-port" id="host-port"
                                                       style="width: 300px;height: 35px"
                                                       data-options="required:true"
                                                       class="easyui-textbox easyui-validatebox"></p>
            <p style="padding: 10px ;">用&nbsp;&nbsp;户:<input name="host-username" id="host-username"
                                                       style="width: 300px;height: 35px"
                                                       data-options="required:true"
                                                       class="easyui-textbox easyui-validatebox"></p>
            <p style="padding: 10px ;">密&nbsp;&nbsp;码:<input name="host-password" id="host-password"
                                                       style="width: 300px;height: 35px"
                                                       data-options="required:true"
                                                       class="easyui-textbox easyui-validatebox"></p>

            <p style="padding: 10px ;">PATH:<input name="host-envPath" id="host-envPath"
                                                   style="width: 300px;height: 100px;"
                                                   data-options="required:false,disabled:true"
                                                   multiline="true"
                                                         class="easyui-textbox easyui-validatebox" ></p>
        </div>

    </form>
</div>

