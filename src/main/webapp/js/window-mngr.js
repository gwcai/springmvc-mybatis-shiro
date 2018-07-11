/**
 * Created by agaowei on 2017/6/22.
 */
var windowMngr = [];

jQuery(function($) {
    $(".main-container").height($(window).height() - $("#navbar").height() - $(".footer").height());
    if(typeof aceReady == 'function') aceReady($);
    $(window).resize(function(){
        $(".main-container").height($(document).height() - $("#navbar").height() - $(".footer").height());
    });
});

function openURL(obj) {
    var id = $(obj).attr("data-id");
    var idRef = "#" + id;
    if(!windowMngr[idRef]) {
        $("#tab-header").append("<li role=\"presentation\">"+
            "<a href=\"" + idRef + "\" role=\"tab\" data-toggle=\"tab\">"+
            $(obj).find(".menu-text").text()+
            "<button class=\"close\" type=\"button\" title=\"关闭\" onclick=\"return closeTab('"+idRef+"');\">×</button></a></li>");

        $("#tab-body").append("<div role=\"tabpanel\" class=\"tab-pane\" data-target=\"tab\" id=\""+id+"\">"+
            "<iframe src='"+$(obj).attr("url")+"' name='"+idRef+"'></iframe></div>");

        windowMngr[idRef]=idRef;
    }else{
        $(idRef)[0].children[0].src = $(obj).attr("url");   //重新加载页面
    }

    $("#tab-header").find('a[href='+idRef+']').tab('show');
}

function closeTab(idRef) {
    var removeActiveOne = $("#tab-body").find(idRef).hasClass("active");

    $("#tab-body").find(idRef).remove();
    $("#tab-header").find('a[href='+idRef+']').parent().remove();
    delete windowMngr[idRef];

    if(removeActiveOne) {
        $('#tab-header a:last').tab('show');
    }
}