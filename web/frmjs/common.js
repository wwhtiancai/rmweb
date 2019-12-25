Common_NS = {

    init : function() {
        $("input[readonly]").each(function(ele) {
            if (!$(ele).hasClass("readonly")) {
                $(ele).addClass("readonly");
            }
        })
    }

}

$(function() {

    Common_NS.init();

});