<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#F9F9F9;text-align :center;padding-top:20%;">
     <img src="../../resources/admin/easyui/images/loading.gif" width="10%">
</div>
</body>
<script>
    var pc; 
    //不要放在$(function(){});中
    $.parser.onComplete = function () {
        if (pc) clearTimeout(pc);
        pc = setTimeout(closes, 500);//0.5秒钟后消失
    } 

    function closes() {
        $('#loading').fadeOut('normal', function () {
            $(this).remove();
        });
    }
</script>