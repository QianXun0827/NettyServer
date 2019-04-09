$(function () {
    $("body").attr("style", "padding-right: 0!important;");

    // 防止页面后退
    history.pushState(null, null, "/login");
    window.addEventListener('popstate', function () {
        history.pushState(null, null, "/login");
        window.location.reload();
    });


    // 禁止input特殊字符
    preSql = function (obj) {
        var dom = $(obj),
            re = /select|update|delete|exec|count|’|"|=|;|>|<|%|_|\s+/ig;
        if (re.test(dom.val().toLowerCase())) {
            swal({
                title: "请勿填写特殊字符和关键字！",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            dom.val("");
            $("body").attr("style", "padding-right: 0!important;");
        }
    };

    // input正则
    $('.registration-form input[type="text"], .registration-form input[type="password"]').on('blur', function () {
        var regexp = /^[\u4e00-\u9fa5|\w]+$/,
            str = $(this).val();
        if (regexp.test(str) == false && str != "") {
            swal({
                title: "请填写中文、英文或数字",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $(this).val("");
        }
        $("body").attr("style", "padding-right: 0!important;");
    });

    /*表单验证*/
    $('.registration-form input[type="text"], .registration-form input[type="password"]').on('focus', function () {
        $(this).removeClass('input-error');
    });

    $("#loginClick").click(function () {
        var userName = $('input[type="text"]').val();
        var passWord = $('input[type="password"]').val();

        if (userName == "" && passWord == "") {
            $('input[type="text"], input[type="password"]').addClass('input-error');
            swal({
                title: "请输入用户名和密码！",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
            return;
        }

        if (userName == "") {
            $('input[type="text"]').addClass('input-error');
            swal({
                title: "请输入用户名！",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
            return;
        }

        if (passWord == "") {
            $('input[type="password"]').addClass('input-error');
            swal({
                title: "请输入密码！",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
            return;
        }

        if (userName != "" && passWord != "") {
            $(this).removeClass('input-error');
        }

        $.ajax({
            url: '/userLogin',
            type: "post",
            data: {
                userName: userName,
                passWord: passWord
            },
            beforeSend: function () {
                // 禁用按钮防止重复提交
                $("#loginClick").attr('disabled', true);
            },
            success: function (result) {
                if (result.status == 200) {
                    swal({
                        title: "登录成功",
                        text: "",
                        type: "success",
                        allowOutsideClick: false
                    }).then((result) => {
                        if (result.value) {
                            window.location.href = '/cms/index';
                            $("#loginClick").attr('disabled', false);
                            $("body").attr("style", "padding-right: 0!important;");
                        }
                    });
                } else if (result.status == 201) {
                    Swal.fire(
                        '登录失败',
                        '' + result.msg + '',
                        'error'
                    );
                    $("body").attr("style", "padding-right: 0!important;");
                } else if (result.status == 303) {
                    Swal.fire(
                        '登录失败',
                        '' + result.msg + '',
                        'error'
                    );
                    $("body").attr("style", "padding-right: 0!important;");
                } else if (result.status == 304) {
                    Swal.fire(
                        '登录失败',
                        '' + result.msg + '',
                        'error'
                    );
                    $("body").attr("style", "padding-right: 0!important;");
                }
            },
            error: function () {
                swal({
                    title: "系统错误，登录失败",
                    text: "",
                    type: "error",
                    allowOutsideClick: false
                });
                $("#loginClick").attr('disabled', false);
                $("body").attr("style", "padding-right: 0!important;");
            },
            complete: function () {
                $("#loginClick").attr('disabled', false);
            }
        });
    });
});