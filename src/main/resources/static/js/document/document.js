document_tool = {
    form_clear: function (type) {
        $("#document_"+type+"_tool_form").form('reset');
        $("#document_"+type+"_tool_form").form('clear');
        $("#document_"+type+"_tool_add_form").form('reset');
        $("#document_"+type+"_tool_add_form").form('clear');
        $("#document_"+type+"_tool_info_form").form('reset');
        $("#document_"+type+"_tool_info_form").form('clear');
        $("#document_"+type+"_tool_locate_form").form('reset');
        $("#document_"+type+"_tool_locate_form").form('clear');
        $("#document_"+type+"_tool_deal_form").form('reset');
        $("#document_"+type+"_tool_deal_form").form('clear');
        $("#document_"+type+"_tool-search-form").form('reset');
        $("#document_"+type+"_tool-search-form").form('clear');
    },


    init_main_view: function (begin,end,type,index) {
        $("#document_"+type+"_grid").datagrid({
            url: getRootPath() + '/document/list',
            method: 'get',
            idField: "id",
            fitColumns: true,
            toolbar: '#document-bu-tool-bar',
            rownumbers: true,
            animate: true,
            singleSelect: true,
            fit: true,
            border: false,
            pagination: true,
            striped: true,
            pagePosition: "bottom",
            pageNumber: 1,
            pageSize: 15,
            pageList: [15, 30, 45, 60],
            queryParams: {
                type: index,
                begin:begin,
                end:end
            },
            columns: [[
                {title: "选择", field: "ck", documentbox: true},
                {title: "编号", field: "id", width: 50, sortable: true},
                {title: "描述", field: "summary", width: 50, sortable: true},
                {title: "路径", field: "url", width: 50, sortable: true},
                {title: "文件后缀", field: "suffix", width: 50, sortable: true},
                {
                    title: "创建时间", field: "createtime", formatter: function (value, row, index) {
                    date = new Date(value);
                    timeStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                    if(value==null||value=="")timeStr = value;
                    return timeStr;
                }, width: 100, sortable: true
                }





            ]],
            onLoadSuccess: function (data) {

            },
            onDblClickRow: function (index, row) {
                document_tool.load_detail(type);
            }
        });

    },

    load_detail:function(type){
        var documents = $("#document_"+type+"_grid").datagrid('getChecked');
        if (documents.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }


        if (type=="online"){
            window.open(documents[0].url);
            return;
        }

        var arr=documents[0].url.split("/");
        var filename = arr[arr.length-1];
        var type = arr[arr.length-2];

        window.open(getRootPath() + '/document/download?type='+type+'&filename='+filename);

    },


    delete:function(type){
        var documents = $("#document_"+type+"_grid").datagrid('getChecked');
        if (documents.length == 0) {
            common_tool.messager_show("请至少选择一条记录");
            return false;
        }
        //执行删除
        $.ajax({
            data: {
                id:documents[0].id
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/document/delete',
            async: true,
            dataType: 'json',
            beforeSend:function () {
                common_tool.process_wait("加载中...")
            },
            success: function (result) {
                common_tool.messager_show(result.msg);
                if (result.code == 10000) {
                    common_tool.process_finish();
                }

            },
        });

    }

};

