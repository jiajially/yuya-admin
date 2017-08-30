
$(document).ready(function () {
    var type = "error";

    check_tool.init_main_view(null,null,type);

    $("#check-"+type+"-flash-btn").click(function () {
        check_tool.init_main_view(null,null,type);
    });
    $("#check-"+type+"-detail-btn").click(function () {
        check_tool.load_detail(type);
    });
    $("#check-"+type+"-select-btn").click(function () {
        var begin = $("#check-"+type+"-search-begin").datebox('getValue');
        var end = $("#check-"+type+"-search-end").datebox('getValue');
        check_tool.init_main_view(begin,end,type);
    });



});