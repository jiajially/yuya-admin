loadfile_tool = {
    form_clear: function () {
    },
    loadfile: function (editor) {
        var id = $('#loadfile-host').combobox('getValue');
        var file = $("input[name='loadfile-file']").val();
        var word = $("input[name='loadfile-char']").val();
        $.ajax({
            data: {
                id: id,
                file: file,
                word: word
            },
            traditional: true,
            method: 'get',
            url: getRootPath() + '/dev/cat',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.code == 10000) {
                    common_tool.messager_show(result.msg);
                    editor.txt.html('<pre>'+result.data.content+'</pre>')
                    loadfile_tool.loadfile_result(result.data.character,result.data.count)
                    return false;
                }
                else {
                    common_tool.messager_show(result.msg);
                }
            }
        });
    },
    loadfile_result: function (char,count) {

        //<span style="line-height: 26px; ">识别字符:<p id="loadfile-char"></p></span>
        //<span style="line-height: 26px; ">识别个数:<p id="loadfile-count"></p></span>
        $("#loadfile-result").html('<span style="line-height: 26px; ">识别字符:'+char+'</span>'+
            '<br /><span style="line-height: 26px; ">识别个数:'+count+'</span>');
        console.log($("#loadfile-result"))
    },

};
$(document).ready(function () {
    $("#loadfile-host").combobox({
        url:'',
        method:'GET',
        valueField:'id',
        textField:'text',
        onShowPanel:function(){
            $("#loadfile-host").combobox('reload',getRootPath() + '/host/select');
        },
    });
    var E = window.wangEditor;
    var editor = new E('#loadfile');
    // 或者 var editor = new E( document.getElementById('#editor') )
    editor.create();
    $("#loadfile-reload-btn").click(function () {
        loadfile_tool.loadfile(editor);
    });

});