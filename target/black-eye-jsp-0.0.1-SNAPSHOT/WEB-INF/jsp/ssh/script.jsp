<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/system/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/ssh/script.js"></script>
<div id="script-tool-bar" style="padding: 10px 10px 0 10px">
    <form id="user-search-form">
        <div class="easyui-linkbutton " id="script-flash-btn" data-options="iconCls:'icon-reload'" style="width:70px">刷新
        </div>
        <div class="easyui-linkbutton " id="script-save-btn" data-options="iconCls:'icon-add'" style="width:70px">新增</div>
        <div class="easyui-linkbutton " id="script-delete-btn" data-options="iconCls:'icon-remove'" style="width:70px">删除
        </div>
        <div class="easyui-linkbutton " id="script-stop-btn" data-options="iconCls:'icon-remove'" style="width:70px">停止
        </div>
        <span style="line-height: 26px; ">脚本任务:<input name="script-search-name" class="easyui-textbox"
                                                    style="line-height: 26px; "></span>
        <span style="line-height: 26px; ">主机:<input name="script-search-host" class="easyui-textbox"
                                                    style="line-height: 26px; "></span>
        <span style="line-height: 26px; ">指令:<input name="script-search-cmd" class="easyui-textbox"
                                                    style="line-height: 26px; "></span>
        <div class="easyui-linkbutton " id="script-select-btn" data-options="iconCls:'icon-search'" style="width:70px">搜索
        </div>
    </form>
</div>
<div id="script_grid" style="padding: 10px">

</div>

    <div id="script_new_dialog" class="easyui-tabs" style="width:100%;height:100%;">
        <div title="基础配置" style="float: left;height: 100%;display:none;">
            <form id="script_form">
            <input type="hidden" name="id" id="id">
            <div style="float: left;height: 100px;">
                <p style="padding: 10px;">&nbsp;名称:<input id="script-name" name="script-name"  class="easyui-textbox easyui-validatebox"
                                                          style="width: 300px;height: 35px"
                                                          data-options="
                                                          required:true" >
                </p>

                <p style="padding: 10px;">&nbsp;主机:<input id="script-host" name="script-host" class="easyui-combobox"
                                                          style="width: 300px;height: 35px"
                                                          data-options="
                                                          required:true" >
                </p>

                <p style="padding: 10px ;">&nbsp;指令:<input name="script-cmd" id="script-cmd"
                                                           style="width: 300px;height: 100px"
                                                           data-options="required:true"
                                                           multiline="true"
                                                           class="easyui-textbox easyui-validatebox"></p>
            </div>

            </form>
        </div>
        <div title="时间配置"  style="float: left;height: 100px;display:none;">
            <form id="script_time_form">
                <div style="float: left;height: 100px;">
                    <p style="padding: 10px;">&nbsp;开始:<input name="script-startTime" id="script-startTime" class="easyui-datetimebox" style="width:300px;height: 35px">
                    </p>
                    <p style="padding: 10px;">&nbsp;结束:<input name="script-endTime" id="script-endTime" class="easyui-datetimebox" style="width:300px;height: 35px">
                    </p>

                    <p style="padding: 10px ;">&nbsp;周期:<input name="script-rate" id="script-rate"style="width: 300px;height: 35px" placeholder="以秒为单位的数值"></p>
                </div>

            </form>
        </div>
        <!--
        <div title="Tab3"  style="float: left;height: 100px;display:none;">
            tab3
        </div>
        -->
    </div>


<div id="script_result_dialog">
    <form id="script_result_form">

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
