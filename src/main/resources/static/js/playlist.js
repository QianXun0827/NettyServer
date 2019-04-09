$(function () {
    $("body").attr("style", "padding-right: 0!important;");
    $.baseUrl = parent.baseUrl;

    // 禁止input特殊字符
    preSql = function (obj) {
        var dom = $(obj),
            re = /select|update|delete|exec|count|’|"|=|;|>|<|%|_|\s+/ig;
        if (re.test(dom.val().toLowerCase())) {
            swal({
                title: "请勿输入特殊字符和关键字！",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            dom.val("");
            $("body").attr("style", "padding-right: 0!important;");
        }
    };

    // input正则
    $("#manageName").blur(function () {
        var regexp = /^[\u4e00-\u9fa5|\w]+$/,
            str = $("#manageName").val();
        if (regexp.test(str) == false && str != "") {
            swal({
                title: "请输入中文、英文或数字",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("#manageName").val("");
        }
        $("body").attr("style", "padding-right: 0!important;");
    });

    // 模态框隐藏事件
    $(".modal").on('hide.bs.modal', function () {
        $("body").attr("style", "padding-right: 0!important;");
    });

    // 站牌名称
    $('#shelterSelect').selectpicker({
        actionsBox: true,
        liveSearch: true,
        noneSelectedText: "请选择站牌",
        title: "请选择站牌",
        selectAllText: "全选",
        deselectAllText: '全不选'
    });

    $('#shelterSelect').on('loaded.bs.select', function (e) {
        $.ajax({
            url: '/cms/chooseShelter/0',
            success: function (data) {
                if (data.status === 200) {
                    var result = data.data;
                    for (var i = 0; i < result.length; i++) {
                        $('#shelterSelect').append("<option value=" + result[i].iccid + ">" + result[i].shelterName + "(" + result[i].shelterDirection + ")</option>");
                    }
                    $('#shelterSelect').selectpicker('refresh');
                }
            }
        })
    });

    // 素材类型
    $("#fodderSelect").selectpicker({
        noneSelectedText: "请选择素材类型",
        title: "请选择素材类型"
    });

    var iccid = $('#shelterSelect').selectpicker("val"),
        fodderName = $("#manageName").val(),
        type = $('#fodderSelect').selectpicker("val"),
        tableUrl;

    if (iccid == "") {
        iccid = "&1";
    }
    if (fodderName == "") {
        fodderName = "&0";
    }

    tableUrl = "/cms/selectFodderManage/" + iccid + "/" + fodderName + "/" + type + "/1";

    // 表格
    function initTable(tableUrl) {
        $("#table").bootstrapTable({
            url: tableUrl, // 获取表格数据的url
            cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
            striped: true, //表格显示条纹，默认为false
            pagination: true, // 在表格底部显示分页组件，默认false
            pageSize: 10, // 页面数据条数
            pageList: [10],
            //search: true,     //搜索
            clickToSelect: false, //点击行选择
            pageNumber: 1, // 首页页码
            sortOrder: "desc", // 排序规则
            sortName: "createTime", // 要排序的列。
            columns: [{
                checkbox: true, // 显示一个勾选框
                align: "center" // 居中显示
            }, {
                field: 'ids', // 返回json数据中的name
                title: '编号', // 表格表头显示文字
                align: 'center', // 左右居中
                valign: 'middle', // 上下居中
                width: 80,
                formatter: function (value, row, index) {
                    return index + 1
                }
            }, {
                field: "iccid", // 返回json数据中的name
                title: "站牌id", // 表格表头显示文字
                align: "center", // 左右居中
                valign: "middle", // 上下居中
                width: 200,
                visible: false
            }, {
                field: "shelterFodderId", // 返回json数据中的name
                title: "站牌素材id", // 表格表头显示文字
                align: "center", // 左右居中
                valign: "middle", // 上下居中
                width: 200,
                visible: false
            }, {
                field: "fodderId", // 返回json数据中的name
                title: "素材id", // 表格表头显示文字
                align: "center", // 左右居中
                valign: "middle", // 上下居中
                width: 200,
                visible: false
            }, {
                field: "shelterName",
                title: "站牌名称",
                align: "center",
                width: 200,
                valign: "middle"
            }, {
                field: "fodderName",
                title: "素材名称",
                align: "center",
                width: 200,
                valign: "middle"
            }, {
                field: "fodderType",
                title: "类型",
                align: "center",
                width: 100,
                valign: "middle",
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return "图片";
                    } else if (value == 2) {
                        return "视频";
                    } else if (value == 3) {
                        return "文字";
                    }
                }
            }, {
                field: "updateState",
                title: "状态",
                align: "center",
                width: 100,
                valign: "middle",
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return "发布中";
                    } else if (value == 1) {
                        return "发布成功";
                    } else if (value == 2) {
                        return "发布失败";
                    } else if (value == 3) {
                        return "下架中";
                    } else if (value == 4) {
                        return "下架成功";
                    } else if (value == 5) {
                        return "下架失败";
                    }
                }
            }, {
                field: "fodderArea",
                title: "发布区域",
                align: "center",
                width: 100,
                valign: "middle",
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return "区域一";
                    } else if (value == 2) {
                        return "区域二";
                    } else if (value == 3) {
                        return "区域三";
                    }
                }
            }, {
                field: "remark",
                title: "备注",
                align: "center",
                width: 200,
                valign: "middle",
                formatter: function (value, row, index) {
                    if (value == "" || value == null) {
                        return "无"
                    } else {
                        return value;
                    }
                }
            }, {
                field: "createTime",
                title: "创建时间",
                align: "center",
                width: 200,
                valign: "middle",
                //--修改--获取日期列的值进行转换
                // formatter: function (value, row, index) {
                //     return changeDateFormat(value);
                // }
            }, {
                field: "operation",
                title: "操作",
                align: "center",
                valign: "middle",
                width: 120,
                events: {
                    // 预览
                    "click #managePreview": function (e, value, row, index) {
                        $(function () {
                            var path = $.baseUrl + "/fodder/" + row.fodderId;
                            if (row.fodderType == 1) {
                                var picAds = $("#picture").get(0);
                                picAds.src = path;
                                $("#picture").attr("style", "width:100%");
                                $("#videoPlay").attr("src", "");
                                $("#videoPlay").css("display", "none");
                                $("#wordHtml").html('');
                            } else if (row.fodderType == 2) {
                                var videoAds = $("#videoPlay").get(0);
                                videoAds.src = path;
                                $("#videoPlay").css("display", "block");
                                $("#picture").attr("src", "");
                                $("#wordHtml").html('');
                            } else if (row.fodderType == 3) {
                                $("#wordHtml").html(row.fodderContext);
                                $("#picture,#videoPlay").attr("src", "");
                                $("#videoPlay").css("display", "none");
                            } else {
                                $("#wordHtml").html("暂无内容");
                                $("#picture,#videoPlay").attr("src", "");
                                $("#videoPlay").css("display", "none");
                            }
                            $("#previewModal").modal();
                        });
                    }
                },
                formatter: operateFormatter
            }],
        });
    }

    initTable(tableUrl);

    function operateFormatter(value, row, index) {
        return [
            '<button id="managePreview" style="margin-left: 0.3%" type="button" class="btn btn-default">预览</button>'
        ].join("");
    }

    //转换日期格式(时间戳转换为datetime格式)
    // function changeDateFormat(val) {
    //     var dateVal = val + "";
    //     if (val != null) {
    //         var date = new Date(
    //             parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10)
    //         );
    //         var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    //         var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    //         var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
    //         var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
    //         var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
    //         return (
    //             date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds
    //         );
    //     }
    // }

    // 预览隐藏清空内容
    $('#managePreview').on('hidden.bs.modal', function () {
        $("#videoPlay").attr("src", "");
        $("#picture").attr("src", "");
        $("#wordHtml").html("");
    });

    $('#sedShelter').selectpicker({
        actionsBox: true,
        liveSearch: true,
        noneSelectedText: "请选择站牌",
        title: "请选择站牌",
        selectAllText: "全选",
        deselectAllText: '全不选'
    });

    $('#sedShelter').on('loaded.bs.select', function (e) {
        $.ajax({
            url: '/cms/chooseShelter/0',
            success: function (data) {
                if (data.status === 200) {
                    var result = data.data;
                    for (var i = 0; i < result.length; i++) {
                        $('#sedShelter').append("<option value=" + result[i].iccid + ">" + result[i].shelterName + "(" + result[i].shelterDirection + ")</option>");
                    }
                    $('#sedShelter').selectpicker('refresh');
                }
            }
        })
    });

    // 查询
    $("#manageInquire").click(function () {
        var iccid = $('#shelterSelect').selectpicker("val"),
            fodderName = $("#manageName").val(),
            type = $('#fodderSelect').selectpicker("val"),
            tableUrl;

        if (iccid == "") {
            iccid = "&1";
        }
        if (fodderName == "") {
            fodderName = "&0";
        }

        tableUrl = "/cms/selectFodderManage/" + iccid + "/" + fodderName + "/" + type + "/1";

        $.ajax({
            url: tableUrl,
            type: "post",
            // data: {
            //     shelter: shelter,
            //     fodderType: fodderType,
            //     manageName: manageName
            // },
            // dataType: "json",
            // contentType: "application/json;charset=UTF-8",
            success: function (result) {
                $("#table").bootstrapTable('destroy');
                initTable(tableUrl);
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

    // 下架
    $("#manageDelete").click(function () {
        var tableUrl = "/cms/selectFodderManage/&1/&0/0/1";
        var row = $("#table").bootstrapTable('getSelections');
        var fodderDowMsg = [];

        for (var i in row) {
            fodderDowMsg.push({
                fodderId: row[i].fodderId,
                shelterFodderId: row[i].shelterFodderId,
                iccid: row[i].iccid,
                fodderArea: row[i].fodderArea,
                shelterMonitor: 4
            });
        }

        if (row.length == 0) {
            swal({
                title: "请选择要下架的素材",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
        } else {
            Swal.fire({
                title: '是否确定下架？',
                text: "",
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#6c757d',
                confirmButtonText: '下架',
                cancelButtonText: '取消',
                reverseButtons: true
            }).then((result) => {
                if (result.value) {
                    var jsonMsg = {
                        msgType: "cmsFodderDown",
                        cmsFodderDownMsg: {
                            cmsFodderDownMsgList: fodderDowMsg
                        }
                    };
                    parent.ws.send(JSON.stringify(jsonMsg));
                    Swal.fire(
                        '下架成功！',
                        '',
                        'success'
                    ).then((result) => {
                        if (result.value) {
                            $("#table").bootstrapTable('destroy');
                            initTable(tableUrl);
                            $("body").attr("style", "padding-right: 0!important;");
                        }
                    });
                }
            });
            $("body").attr("style", "padding-right: 0!important;");
        }
    });

    // 分区设置
    $("#managePublish").click(function () {
        $('#sedShelter').selectpicker('val', '');
        $('input[name="picRadio"]').prop("checked", false);
        $("#areaChoose").attr("style", "margin-top: 3%;display: none;");
    });

    // 选择区域展现声音选择
    var picRadio = $('input[name="picRadio"]');
    $.picRadioVal = "";
    picRadio.click(function () {
        for (var i in picRadio) {
            if (picRadio[i].checked) {
                $.picRadioVal = picRadio[i].value;
            }
        }
        $('input[name="area"]').prop("checked", false);
        if ($.picRadioVal == 2) {
            $("#areaChoose").attr("style", "margin: 3% 0 0 3%;");
            $("#area3").attr("style", "margin-left: 2%;display: none;");
        } else if ($.picRadioVal == 3) {
            $("#areaChoose").attr("style", "margin: 3% 0 0 3%;");
            $("#area3").attr("style", "margin-left: 2%;");
        } else {
            $("#areaChoose").attr("style", "margin: 3% 0 0 3%;display: none;");
        }
    });

    //  分区设置确认
    $("#picCheck").click(function () {
        var iccids = $('#sedShelter').selectpicker('val'),
            subareaNum = $('input:radio[name="picRadio"]:checked').val(),
            fodderVoiceArea = $('input:radio[name="area"]:checked').val(),
            tableUrl = "/cms/selectFodderManage/&1/&0/0/1";

        if (iccids == null) {
            swal({
                title: "请选择站牌",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
            return;
        }
        if (subareaNum == undefined) {
            swal({
                title: "请选择分区",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            return false;
        }

        if (subareaNum == 1) {
            fodderVoiceArea = 1
        } else if (subareaNum == 2 || subareaNum == 3) {
            if (fodderVoiceArea == undefined) {
                swal({
                    title: "请选择声音开启区域",
                    text: "",
                    type: "warning",
                    allowOutsideClick: false
                });
            } else {
                fodderVoiceArea = $('input:radio[name="area"]:checked').val();
            }
        }

        if (fodderVoiceArea != undefined) {
            var jsonMsg = {
                msgType: "cmsAdsSetting",
                cmsAdsSettingMsg: {
                    iccids: iccids,
                    subareaNum: subareaNum,
                    fodderVoiceArea: fodderVoiceArea
                }
            };
            $("#picCheck").attr('disabled', true);
            parent.ws.send(JSON.stringify(jsonMsg));

            swal({
                title: "已设置分区",
                text: "",
                type: "success",
                allowOutsideClick: false
            }).then((result) => {
                if (result.value) {
                    $("#picCheck").attr('disabled', false);
                    $("#partitionModal").modal('hide');
                    $("#table").bootstrapTable('destroy');
                    initTable(tableUrl);
                    $("body").attr("style", "padding-right: 0!important;");
                }
            });
        }
    });
});