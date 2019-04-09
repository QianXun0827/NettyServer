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

    // 状态
    $("#stateSelect").selectpicker({
        noneSelectedText: "请选择状态",
        title: "请选择状态"
    });

    var iccid = $('#shelterSelect').selectpicker("val"),
        fodderName = $("#manageName").val(),
        type = $('#fodderSelect').selectpicker("val"),
        updateState = $('#stateSelect').selectpicker("val"),
        tableUrl;

    if (iccid == "") {
        iccid = "&1";
    }
    if (fodderName == "") {
        fodderName = "&0";
    }

    tableUrl = "/cms/selectFodderManage/" + iccid + "/" + fodderName + "/" + type + "/" + updateState;

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
            sortName: "updateTime", // 要排序的列。
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
                field: "fodderContext",
                title: "素材内容",
                align: "center",
                width: 200,
                valign: "middle",
                visible: false
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
                field: "remark",
                title: "备注",
                align: "center",
                width: 150,
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
                width: 150,
                valign: "middle"
                //--修改--获取日期列的值进行转换
                // formatter: function (value, row, index) {
                //     return changeDateFormat(value);
                // }
            }, {
                field: "updateTime",
                title: "修改时间",
                align: "center",
                width: 150,
                valign: "middle"
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
                            $.ajax({
                                url: '/cms/previewFodder',
                                type: "post",
                                data: {
                                    fodderId: row.fodderId
                                },
                                success: function (result) {
                                    if (result.status == 200) {
                                        $("#previewModal").modal();
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
                                        }
                                    } else if (result.status == 201) {
                                        Swal.fire(
                                            '系统错误',
                                            '' + result.msg + '',
                                            'error'
                                        );
                                        $("body").attr("style", "padding-right: 0!important;");
                                    } else if (result.status == 300) {
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
                    },
                    // 重新发布
                    "click #rePublish": function (e, value, row, index) {
                        $(function () {
                            $('input[name="reSedBox"]').prop("checked", false);
                            $('.regionSelectpicker').selectpicker("val", "");
                            $('#reSedShelter').prop('disabled', true);
                            $('#reSedShelter').selectpicker('refresh');
                            $('input[name="sedBox"]').attr("disabled", "disabled");
                            $("#reSedTime").css("display", "none");
                            $("#reStartTime,#reEndTime").val("");

                            var content = "";
                            $.refodderId = row.fodderId;
                            $.refodderName = row.fodderName;
                            $.fodderContent = row.fodderContext;
                            $("#editName").val(row.fodderName);
                            $("#editModal").modal();
                            if (row.fodderType == 3) {
                                $.fodderFormat = "";
                            } else {
                                content = row.fodderContext.split(".");
                                $.fodderFormat = content[content.length - 1];
                            }
                        });
                    },
                },
                formatter: operateFormatter
            }],
        });
    }

    initTable(tableUrl);

    function operateFormatter(value, row, index) {
        if (row.updateState == 2 || row.updateState == 5) {
            return [
                '<div class="dropdown">\n' +
                '    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">操作\n' +
                '    </button>\n' +
                '    <div class="dropdown-menu">\n' +
                '      <a class="dropdown-item" href="javascript:void(0);" id="managePreview">预览</a>\n' +
                '      <div class="dropdown-divider"></div>\n' +
                '      <a class="dropdown-item" href="javascript:void(0);" id="rePublish">重新发布</a>\n' +
                '    </div>\n' +
                '  </div>'
            ].join("");
        } else {
            return [
                '<div class="dropdown">\n' +
                '    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">操作\n' +
                '    </button>\n' +
                '    <div class="dropdown-menu">\n' +
                '      <a class="dropdown-item" href="javascript:void(0);" id="managePreview">预览</a>\n' +
                '    </div>\n' +
                '  </div>'
            ].join("");
        }
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

    //得到当前的id
    function getIdSelections() {
        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
            return row.shelterFodderId;
        });
    }

    // 预览隐藏清空内容
    $('#managePreview').on('hidden.bs.modal', function () {
        $("#videoPlay").attr("src", "");
        $("#picture").attr("src", "");
        $("#wordHtml").html("");
    });

    // 重新发布--区域选择
    $("#reRegionSelect").selectpicker({
        noneSelectedText: "请选择区域",
        title: "请选择区域"
    });

    // 重新发布--站牌选择
    $('#reSedShelter').selectpicker({
        actionsBox: true,
        liveSearch: true,
        noneSelectedText: "请选择站牌",
        title: "请选择站牌",
        selectAllText: "全选",
        deselectAllText: '全不选'
    });

    $('#reRegionSelect').on('changed.bs.select', function (e) {
        var selectVal = $("#reRegionSelect").find("option:selected").attr('class');
        $.reSubareaNum = "";
        if (selectVal == "subareaNum1") {
            $.reSubareaNum = 1;
        } else if (selectVal == "subareaNum2") {
            $.reSubareaNum = 2;
        } else if (selectVal == "subareaNum3") {
            $.reSubareaNum = 3;
        }

        // $.reSubareaNum = $('#reRegionSelect').selectpicker('val');
        if ($.reSubareaNum != "") {
            $('#reSedShelter').html('');
            $.ajax({
                url: "/cms/chooseShelter/" + $.reSubareaNum,
                success: function (data) {
                    $('#reSedShelter').selectpicker('refresh');
                    if (data.data.length == 0) {
                        $('#reSedShelter').prop('disabled', true);
                        swal({
                            title: "该区域下暂无站牌选择！",
                            text: "",
                            type: "warning",
                            allowOutsideClick: false
                        });
                        $("body").attr("style", "padding-right: 0!important;");
                    } else {
                        var result = data.data;
                        for (var i = 0; i < result.length; i++) {
                            $('#reSedShelter').append("<option value=" + result[i].iccid + ">" + result[i].shelterName + "(" + result[i].shelterDirection + ")" + "</option>");
                        }
                    }
                    $('#reSedShelter').selectpicker('refresh');
                }
            })
        }
    });

    $('#reRegionSelect').on('hidden.bs.select', function (e) {
        if ($('#reRegionSelect').selectpicker('val') != "") {
            $('#reSedShelter').prop('disabled', false);
            $('#reSedShelter').selectpicker('refresh');
            $('input[name="reSedBox"]').removeAttr("disabled");
        }
    });

    // 重新发布--播放方式--轮播、插播
    $("#reRoutineaInput,#reVideoSedInput").click(function () {
        $("#reSedTime").css("display", "none");
    });

    // 重新发布--播放方式--预约播
    $("#rePreInput").click(function () {
        $("#reSedTime").css("display", "block");
    });

    // 重新发布--时间插件
    $("#reStartTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        startDate: new Date(),
        autoclose: true
    }).on('changeDate', function () {
        var reStartTime = $("#reStartTime").val();
        $("#reEndTime").datetimepicker('setStartDate', reStartTime);
    });

    $("#reEndTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        startDate: new Date(),
        autoclose: true
    }).on('changeDate', function () {
        var reEndTime = $("#reEndTime").val();
        $("#reStartTime").datetimepicker('setEndDate', reEndTime);
    });

    // 重新发布确定
    $("#editOK").click(function () {
        var iccids = $('#reSedShelter').selectpicker('val'),
            fodderArea = $('#reRegionSelect').selectpicker('val'),
            publishType,
            videoBox = $('[name="reSedBox"]'),
            beginTime = $('#reStartTime').val(),
            endTime = $('#reEndTime').val(),
            data;

        beginTime = beginTime.substring(0, 19);
        beginTime = beginTime.replace(/-/g, '/');
        var fodderBeginTime = new Date(beginTime).getTime();

        endTime = endTime.substring(0, 19);
        endTime = endTime.replace(/-/g, '/');
        var fodderEndTime = new Date(endTime).getTime();

        for (var i in videoBox) {
            if (videoBox[i].checked) {
                publishType = videoBox[i].value;
            }
        }

        if (fodderArea == "") {
            swal({
                title: "请选择区域",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
            return;
        }
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
        if (publishType == undefined) {
            swal({
                title: "请选择播放方式",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
            return;
        }
        if ($('#preInput').is(":checked")) {
            if (beginTime == "") {
                swal({
                    title: "请选择开始时间",
                    text: "",
                    type: "warning",
                    allowOutsideClick: false
                });
                $("body").attr("style", "padding-right: 0!important;");
                return;
            }
            if (endTime == "") {
                swal({
                    title: "请选择结束时间",
                    text: "",
                    type: "warning",
                    allowOutsideClick: false
                });
                $("body").attr("style", "padding-right: 0!important;");
                return;
            }
        }

        if (publishType == 2) {
            data = {
                fodderId: $.refodderId,
                iccids: iccids,
                fodderName: $.refodderName,
                publishType: publishType,
                fodderArea: fodderArea,
                fodderContent: $.fodderContent,
                fodderFormat: $.fodderFormat,
                shelterMonitor: 4,
                fodderBeginTime: fodderBeginTime,
                fodderEndTime: fodderEndTime
            }
        } else {
            data = {
                fodderId: $.refodderId,
                iccids: iccids,
                fodderName: $.refodderName,
                publishType: publishType,
                fodderArea: fodderArea,
                fodderContent: $.fodderContent,
                fodderFormat: $.fodderFormat,
                shelterMonitor: 4
            }
        }

        var reJsonMsg = {
            msgType: "cmsFodderPublish",
            cmsFodderPublishMsg: data
        };

        parent.ws.send(JSON.stringify(reJsonMsg));

        swal({
            title: "已发布",
            text: "",
            type: "success",
            allowOutsideClick: false
        }).then((result) => {
            if (result.value) {
                $("#editModal").modal('hide');
                $("body").attr("style", "padding-right: 0!important;");
            }
        });
    });

    // 查询
    $("#manageInquire").click(function () {
        var iccid = $('#shelterSelect').selectpicker("val"),
            fodderName = $("#manageName").val(),
            type = $('#fodderSelect').selectpicker("val"),
            updateState = $('#stateSelect').selectpicker("val");

        if (iccid == "") {
            iccid = "&1";
        }
        if (fodderName == "") {
            fodderName = "&0";
        }

        tableUrl = "/cms/selectFodderManage/" + iccid + "/" + fodderName + "/" + type + "/" + updateState;

        $.ajax({
            url: tableUrl,
            type: "post",
            // data: {
            //     iccid: iccid,
            //     fodderName: fodderName,
            //     type: type,
            //     updateState: updateState
            // },
            // dataType: "json",
            // contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.status == 200) {
                    $("#table").bootstrapTable('destroy');
                    initTable(tableUrl);
                } else if (result.status == 201) {
                    Swal.fire(
                        '查找失败',
                        '' + result.msg + '',
                        'error'
                    );
                    $("body").attr("style", "padding-right: 0!important;");
                }
            },
            error: function () {
                swal({
                    title: "系统错误，查找失败",
                    text: "",
                    type: "error",
                    allowOutsideClick: false
                }).then((result) => {
                    if (result.value) {
                        $("body").attr("style", "padding-right: 0!important;");
                    }
                });
            }
        });
    });

    // 删除
    $("#manageDelete").click(function () {
        var iccid = $('#shelterSelect').selectpicker("val"),
            fodderName = $("#manageName").val(),
            type = $('#fodderSelect').selectpicker("val"),
            updateState = $('#stateSelect').selectpicker("val"),
            tableUrl;

        if (iccid == "") {
            iccid = "&1";
        }
        if (fodderName == "") {
            fodderName = "&0";
        }

        tableUrl = "/cms/selectFodderManage/" + iccid + "/" + fodderName + "/" + type + "/" + updateState;

        var row = $("#table").bootstrapTable("getSelections"),
            publishArr = [],
            removeArr = [],
            succeedArr = [];

        for (var i in row) {
            if (row[i].updateState == 0) {
                publishArr.push(row[i].updateState);
            }
            if (row[i].updateState == 3) {
                removeArr.push(row[i].updateState);
            }
            if (row[i].updateState == 1) {
                succeedArr.push(row[i].updateState);
            }
        }

        var ids = getIdSelections();
        if (ids.length == 0) {
            swal({
                title: "请选择要删除的素材",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
        } else if (publishArr.length != 0) {
            swal({
                title: "状态错误",
                text: "发布中不允许删除",
                type: "error",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
        } else if (removeArr.length != 0) {
            swal({
                title: "状态错误",
                text: "下架中不允许删除",
                type: "error",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
        } else if (succeedArr.length != 0) {
            swal({
                title: "状态错误",
                text: "发布成功不允许删除",
                type: "error",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
        } else {
            Swal.fire({
                title: '是否确定删除？',
                text: "删除后将无法恢复!",
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#6c757d',
                confirmButtonText: '删除',
                cancelButtonText: '取消',
                reverseButtons: true
            }).then((result) => {
                if (result.value) {
                    $.ajax({
                        url: "/cms/deleteShelterFodder",
                        type: "post",
                        data: {
                            shelterFodderIds: ids
                        },
                        // dataType: "json",
                        // contentType: "application/json;charset=UTF-8",
                        success: function (data) {
                            Swal.fire(
                                '删除!',
                                '您的素材已被删除',
                                'success'
                            );
                            $("#table").bootstrapTable('destroy');
                            initTable(tableUrl);
                            $("body").attr("style", "padding-right: 0!important;");
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
                }
            });
            $("body").attr("style", "padding-right: 0!important;");
        }
    });

    // 发布模态框
    $("#managePublish").click(function () {
        $.fodderId = "";
        $('.regionSelectpicker').selectpicker("val", "");
        $('#sedShelter').prop('disabled', true);
        $('#sedShelter').selectpicker('refresh');
        // $('input[name="sedBox"],input[name="sedUpload"]').prop("checked", false);
        // $('input[name="sedBox"],input[name="sedUpload"],.sedButton').attr("disabled", "disabled");
        $('input[name="sedBox"]').prop("checked", false);
        $('input[name="sedBox"],#fodderUpload').attr("disabled", "disabled");
        $("#sedTime").css("display", "none");
        $("#startTime,#endTime").val("");
        $("#uploadTable").bootstrapTable('destroy');
    });

    // 发布选择区域
    $("#regionSelect").selectpicker({
        noneSelectedText: "请选择区域",
        title: "请选择区域"
    });

    // 发布站牌选择
    $('#sedShelter').selectpicker({
        actionsBox: true,
        liveSearch: true,
        noneSelectedText: "请选择站牌",
        title: "请选择站牌",
        selectAllText: "全选",
        deselectAllText: '全不选'
    });

    $('#regionSelect').on('changed.bs.select', function (e) {
        var selectVal = $("#regionSelect").find("option:selected").attr('class');
        $.subareaNum = "";
        if (selectVal == "subareaNum1") {
            $.subareaNum = 1;
        } else if (selectVal == "subareaNum2") {
            $.subareaNum = 2;
        } else if (selectVal == "subareaNum3") {
            $.subareaNum = 3;
        }

        // $.subareaNum = $('#regionSelect').selectpicker('val');
        if ($.subareaNum != "") {
            $('#sedShelter').html('');
            $.ajax({
                url: "/cms/chooseShelter/" + $.subareaNum,
                success: function (data) {
                    $('#sedShelter').selectpicker('refresh');
                    if (data.data.length == 0) {
                        $('#sedShelter').prop('disabled', true);
                        swal({
                            title: "该区域下暂无站牌选择！",
                            text: "",
                            type: "warning",
                            allowOutsideClick: false
                        });
                        $("body").attr("style", "padding-right: 0!important;");
                    } else {
                        var result = data.data;
                        for (var i = 0; i < result.length; i++) {
                            $('#sedShelter').append("<option value=" + result[i].iccid + ">" + result[i].shelterName + "(" + result[i].shelterDirection + ")" + "</option>");
                        }
                    }
                    $('#sedShelter').selectpicker('refresh');
                }
            })
        }
    });

    $('#regionSelect').on('hidden.bs.select', function (e) {
        if ($('#regionSelect').selectpicker('val') != "") {
            $('#sedShelter').prop('disabled', false);
            $('#sedShelter').selectpicker('refresh');
            // $('input[name="sedBox"],input[name="sedUpload"]').removeAttr("disabled");
            $('input[name="sedBox"],#fodderUpload').removeAttr("disabled");
        }
    });

    // 发布播放方式--轮播、插播
    $("#routineaInput,#videoSedInput").click(function () {
        $("#sedTime").css("display", "none");
    });

    // 发布播放方式--预约播
    $("#preInput").click(function () {
        $("#sedTime").css("display", "block");
    });

    // 时间插件
    $("#startTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        startDate: new Date(),
        autoclose: true
    }).on('changeDate', function () {
        var startTime = $("#startTime").val();
        $("#endTime").datetimepicker('setStartDate', startTime);
    });

    $("#endTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        startDate: new Date(),
        autoclose: true
    }).on('changeDate', function () {
        var endTime = $("#endTime").val();
        $("#startTime").datetimepicker('setEndDate', endTime);
    });

    // 选择图片上传
    // $("#imageSedUpload").click(function () {
    //     $("#imageUpload").removeAttr("disabled");
    //     $("#videoUpload").attr("disabled", "disabled");
    //     $("#wordUpload").attr("disabled", "disabled");
    // });

    // 选择视频上传
    // $("#videoSedUpload").click(function () {
    //     $("#videoUpload").removeAttr("disabled");
    //     $("#imageUpload").attr("disabled", "disabled");
    //     $("#wordUpload").attr("disabled", "disabled");
    // });

    // 选择文字上传
    // $("#wordSedUpload").click(function () {
    //     $("#wordUpload").removeAttr("disabled");
    //     $("#imageUpload").attr("disabled", "disabled");
    //     $("#videoUpload").attr("disabled", "disabled");
    // });

    // 素材选择
    // $.uploadtype = "";
    // if ($('#imageSedUpload').is(":checked")) {
    //     $.uploadtype = 1;
    // }else if($('#videoSedUpload').is(":checked")){
    //     $.uploadtype = 2;
    // }else if($('#wordSedUpload').is(":checked")){
    //     $.uploadtype = 3;
    // }

    // $("#imageUpload").click(function () {
    //     uploadTable($.uploadtype);
    // });
    //
    // // 视频发布
    // $("#videoUpload").click(function () {
    //     uploadTable($.type);
    // });
    //
    // // 文字发布
    // $("#wordUpload").click(function () {
    //     uploadTable($.type);
    // });

    // 素材选择
    $("#fodderUpload").click(function () {
        uploadTable();
    });

    function uploadTable() {
        $("#uploadTable").bootstrapTable('destroy');
        $("#uploadTable").bootstrapTable({
            url: "/cms/selectFodderByAuditStatus/1", // 获取表格数据的url
            cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
            striped: true, //表格显示条纹，默认为false
            pagination: true, // 在表格底部显示分页组件，默认false
            pageSize: 5, // 页面数据条数
            pageList: [5],
            //search: true,     //搜索
            clickToSelect: true, //点击行选择
            pageNumber: 1, // 首页页码
            sortOrder: "desc", // 排序规则
            sortName: "createTime", // 要排序的列。
            columns: [{
                radio: true, // 显示一个勾选框
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
                field: "fodderId", // 返回json数据中的name
                title: "素材id", // 表格表头显示文字
                align: "center", // 左右居中
                valign: "middle", // 上下居中
                width: 200,
                visible: false
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
                field: "fodderContext",
                title: "文件内容",
                align: "center",
                width: 100,
                valign: "middle",
                visible: false
            }],
        });
    }

    // 素材上传确定
    $("#uploadOK").click(function () {
        var row = $("#uploadTable").bootstrapTable('getSelections');
        if (row.length == 0) {
            swal({
                title: "请选择要上传的素材",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
        } else {
            var fodderId = row[0].fodderId;
            $.fodderId = fodderId;
            $("#uploadModal").modal('hide');
        }
    });

    // 发布确认
    $("#confirmation").click(function () {
        var tableUrl = "/cms/selectFodderManage/&1/&0/0/88",
            row,
            iccids = $('#sedShelter').selectpicker('val'),
            fodderName,
            publishType,
            fodderArea = $('#regionSelect').selectpicker('val'),
            fodderContent,
            content,
            fodderFormat,
            publishType,
            videoBox = $('[name="sedBox"]'),
            beginTime = $('#startTime').val(),
            endTime = $('#endTime').val(),
            data;

        beginTime = beginTime.substring(0, 19);
        beginTime = beginTime.replace(/-/g, '/');
        var fodderBeginTime = new Date(beginTime).getTime();

        endTime = endTime.substring(0, 19);
        endTime = endTime.replace(/-/g, '/');
        var fodderEndTime = new Date(endTime).getTime();

        if ($.fodderId != "") {
            row = $("#uploadTable").bootstrapTable('getSelections');
            fodderName = row[0].fodderName;
            publishType = row[0].fodderType;
            fodderContent = row[0].fodderContext;
            if (row[0].fodderType == 3) {
                fodderFormat = "";
            } else {
                content = row[0].fodderContext.split(".");
                fodderFormat = content[content.length - 1];
            }

        }

        for (var i in videoBox) {
            if (videoBox[i].checked) {
                publishType = videoBox[i].value;
            }
        }

        if (fodderArea == "") {
            swal({
                title: "请选择区域",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
            return;
        }
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
        if (publishType == undefined) {
            swal({
                title: "请选择播放方式",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
            return;
        }
        if ($.fodderId == "") {
            swal({
                title: "请选择素材",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
            return;
        }
        if ($('#preInput').is(":checked")) {
            if (beginTime == "") {
                swal({
                    title: "请选择开始时间",
                    text: "",
                    type: "warning",
                    allowOutsideClick: false
                });
                $("body").attr("style", "padding-right: 0!important;");
                return;
            }
            if (endTime == "") {
                swal({
                    title: "请选择结束时间",
                    text: "",
                    type: "warning",
                    allowOutsideClick: false
                });
                $("body").attr("style", "padding-right: 0!important;");
                return;
            }
        }

        if (publishType == 2) {
            data = {
                fodderId: $.fodderId,
                iccids: iccids,
                fodderName: fodderName,
                publishType: publishType,
                fodderArea: fodderArea,
                fodderContent: fodderContent,
                fodderFormat: fodderFormat,
                shelterMonitor: 4,
                fodderBeginTime: fodderBeginTime,
                fodderEndTime: fodderEndTime
            }
        } else {
            data = {
                fodderId: $.fodderId,
                iccids: iccids,
                fodderName: fodderName,
                publishType: publishType,
                fodderArea: fodderArea,
                fodderContent: fodderContent,
                fodderFormat: fodderFormat,
                shelterMonitor: 4
            }
        }

        var jsonMsg = {
            msgType: "cmsFodderPublish",
            cmsFodderPublishMsg: data
        };

        parent.ws.send(JSON.stringify(jsonMsg));

        swal({
            title: "已发布",
            text: "",
            type: "success",
            allowOutsideClick: false
        }).then((result) => {
            if (result.value) {
                $("#table").bootstrapTable('destroy');
                initTable(tableUrl);
                $("#sedModal").modal('hide');
                $("body").attr("style", "padding-right: 0!important;");
            }
        });
    });
})
;