<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/system/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/ssh/task.js"></script>
<div id="task-tool-bar" style="padding: 10px 10px 0 10px">
    <form id="user-search-form">
        <div class="easyui-linkbutton " id="user-flash-btn" data-options="iconCls:'icon-reload'" style="width:70px">刷新
        </div>

        <span style="line-height: 26px; ">脚本任务:<input name="task-search-name" class="easyui-textbox"
                                                      style="line-height: 26px; "></span>
        <span style="line-height: 26px; ">主机:<input name="task-search-host" class="easyui-textbox"
                                                    style="line-height: 26px; "></span>
        <span style="line-height: 26px; ">指令:<input name="task-search-cmd" class="easyui-textbox"
                                                    style="line-height: 26px; "></span>
        <div class="easyui-linkbutton " id="task-select-btn" data-options="iconCls:'icon-search'" style="width:70px">搜索
        </div>
    </form>
</div>
<div id="task_grid" style="padding: 10px">

</div>

<form id="task_form">
    <div id="task_new_dialog" class="easyui-tabs" style="width:100%;height:100%;">
        <div title="基础配置" style="float: left;height: 100%;display:none;">
            <input type="hidden" name="id" id="id">
            <div style="float: left;height: 100px;">
                <p style="padding: 10px;">&nbsp;主机:<input id="task-host" name="task-host" class="easyui-combobox"
                                                          style="width: 300px;height: 35px"
                                                          data-options="
                                                          required:true,
                                                          valueField:'id',
                                                          textField:'text',
                                                          url:getRootPath() + '/host/select',
                                                          method: 'GET'">
                </p>

                <p style="padding: 10px ;">&nbsp;指令:<input name="task-cmd" id="task-cmd"
                                                           style="width: 300px;height: 100px"
                                                           data-options="required:true"
                                                           multiline="true"
                                                           class="easyui-textbox easyui-validatebox"></p>
            </div>

        </div>
        <div title="时间配置"  style="float: left;height: 100px;display:none;">
            tab2
        </div>
        <!--
        <div title="Tab3"  style="float: left;height: 100px;display:none;">
            tab3
        </div>
        -->
    </div>
</form>


<div id="task_result_dialog">
    <form id="task_result_form">

        <input name="result" id="result"style="width: 100%;height: 350px"multiline="true"class="easyui-textbox"></p>
    </form>
</div>

<div id="password_edit_dialog">
    <form id="init_password_form" style="padding: 20px 0 0 0;">
        <p style="padding: 10px 0 10px 23px ;">&nbsp;&nbsp;密码:<input name="newPassword" type="password" id="newPassword"
                                                                     style="width: 300px;height: 35px"
                                                                     data-options="required:true,validType:['length[6,20]']"
                                                                     class="easyui-textbox easyui-validatebox"></p>
        <p style="padding: 10px 0 10px 23px ;">重复密码:<input name="repeatNewPassword" type="password"
                                                           id="repeatNewPassword"
                                                           style="width: 300px;height: 35px"
                                                           data-options="required:true,validType:['length[6,20]']"
                                                           class="easyui-textbox easyui-validatebox"></p>
    </form>
</div>
