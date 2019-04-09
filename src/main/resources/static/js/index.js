$(function () {
    $("body").attr("style", "padding-right: 0!important;");

    // 加载后点击首页
    window.onload = function () {
        $("#parent").trigger("click");
    };

    // 侧边栏
    $("#sidebars").slimScroll({
        width: '100%', //可滚动区域宽度
        height: '100%', //可滚动区域高度
        size: '0px' //滚动条宽度，即组件宽度
    });

    // $(".meunAllCard").click(function () {
    //     $(".meunAll li").removeClass("current");
    //     $(this).addClass("current");
    // });

    $.widget.bridge('uibutton', $.ui.button);

    // 防止页面后退
    history.pushState(null, null, "/cms/index");
    window.addEventListener('popstate', function () {
        history.pushState(null, null, "/cms/index");
        window.location.href = '/cms/parent';
        // window.location.reload();
    });

    $("#loginPage").click(function () {
        $.ajax({
            url: '/userLogout',
            type: "post",
            success: function (result) {
                if (result.status == 200) {
                    window.location.href = '/userLogin';
                } else if (result.status == 201) {
                    Swal.fire(
                        '系统错误',
                        '' + result.msg + '',
                        'error'
                    );
                    $("body").attr("style", "padding-right: 0!important;");
                }
            },
            error: function () {
                swal({
                    title: "系统错误",
                    text: "",
                    type: "error",
                    allowOutsideClick: false
                });
                $("body").attr("style", "padding-right: 0!important;");
            }
        });
    });

    // 禁止回车
    $(window).keydown(function (e) {
        var key = window.event ? e.keyCode : e.which;
        if (key == undefined) {
            return false;
        } else {
            if (key.toString() == "13") {
                return false;
            }
        }
    });
});