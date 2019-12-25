Tools_NS = {

    showLoading : function(message) {
        $.blockUI({
            message :
            '<div style="height:155px;background-color:#fff;text-align:center;">' +
            '<div style="line-height:80px;height:80px;padding-top:20px;"><img src="/rmweb/images/greenThrobber_80x80.gif"/></div>' +
            '<div style="text-align:center;" class="med-header">' + message + '</div>' +
            '</div>',
            css : {
                top: ($(window).height() - 155) /2 + 'px',
                left: ($(window).width() - 400) /2 + 'px',
                width: '400px',
                height: '150px',
                border: '5px solid #fff',
                'border-radius': '5px',
                '-moz-border-radius': '5px',
                '-webkit-border-radius': '5px',
                cursor: 'default',
                'text-align': 'left',
                'background' : ''
            },
            overlayCSS: {
                backgroundColor: '#000',
                opacity: 0.8,
                cursor: 'wait'
            }
        });
    },

    closeLoading : function() {
        $.unblockUI();
    },

    closeDialog : function() {
        $.unblockUI();
    },

   /* showError : function(error, element) {
        if (element) {
            var errorTooltip = $('<div class="error-tooltip"><span class="outer-tip"></span><span class="inner-tip"></span></div>');
            $(errorTooltip).append(error);
            if ($(element).parents(".input-wrap").length > 0) {
                $(element).parents(".input-wrap").append(errorTooltip);
            } else {
                $(element).parent().append(errorTooltip);
            }
        } else {
            $(".error-container").append(error).show();
        }

    },*/

   /* showErrorMsg : function(errorMsg, element) {
        LLXD.showError($('<label>' + errorMsg + '</label>'), element);
    },*/

    removeError : function(label, element) {
        if (element) {
            if ($(element).parents(".input-wrap").length > 0) {
                $(element).parents(".input-wrap").children(".error-tooltip").remove();
            } else {
                $(element).siblings(".error-tooltip").remove();
            }
            $(element).removeClass("error");
        } else {
            $(".error-container").html("");
        }
    },

    showWarningDialog : function(warningMessage, callback) {
        $.blockUI({
            message :
            '<div style="background-color:#fff;text-align:left">' +
            '<div style="word-wrap:break-word;line-height:30px;font-size:18px;font-weight:bold;border-bottom:2px solid #e6e6e6;padding: 10px">' + warningMessage + '</div>' +
            '<div style="padding: 10px;text-align:center"><input id="warningDialogCloseBtn" type="button" class="btn btn-primary dialog-close-btn" value="确定"/></div>' +
            '</div>',
            css : {
                cursor: 'default',
                width: 400
            },
            overlayCSS: {
                backgroundColor: '#000',
                opacity: '0.6'
            }
        });
        $(".dialog-close-btn").click(function() {
            $.unblockUI();
            if (callback) {
                callback();
            }
        });
    },
    
    showSimpleConfirmDialog : function(message, confirm, cancel) {
        Tools_NS.showConfirmDialog({
            title : "提示",
            message : message,
            cancellable : true,
            width: 300,
            onCancel: cancel,
            onConfirm: confirm
        });
        
    },

    showConfirmDialog : function(options) {
        var confirmDialogHtml =
            '<div class="flat-dialog dialog-frame" id="' + options.id + '">' +
            '<div class="dialog-title">' +
            options.title +
            '</div>' +
            '<div class="dialog-body">' +
            options.message +
            '</div>' +
            '<div class="dialog-controls">' +
            (options.cancellable ? '<input type="button" class="btn btn-white dialog-cancel-btn" value="取消"/>' : '') +
            '<input type="button" class="btn btn-primary dialog-confirm-btn" value="确认"/>' +
            '</div>' +
            '</div>';
        $.blockUI({
            message: confirmDialogHtml,
            css : {
                width: options.width + 'px',
                left: ($(window).width() - options.width) /2 + 'px',
                top: options.height ? ($(window).height() - options.height) /2 + 'px' : '40%',
                border: 'none',
                cursor: 'default',
                background: ''
            },
            centerY: true
        });
        $("#" + options.id + " input.dialog-cancel-btn").click(function() {
            $.unblockUI();
            options.onCancel();
        });
        $("#" + options.id + " input.dialog-confirm-btn").click(options.onConfirm);
            $("#" + options.id + " input.dialog-cancel-btn").keydown(function(e) {
            if (e.keyCode == 13) {
                $("#" + options.id + " input.dialog-cancel-btn").click();
            }
        });
        $("#" + options.id + " input.dialog-confirm-btn").keydown(function(e) {
            if (e.keyCode == 13) {
                $("#" + options.id + " input.dialog-confirm-btn").click();
            }
        });

    }


}