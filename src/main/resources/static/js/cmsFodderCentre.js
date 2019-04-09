$(function () {
    $("body").attr("style", "padding-right: 0!important;");
    $.baseUrl = parent.baseUrl;

    //在iframe子页面中查找父页面元素
    // alert($('#default', window.parent.document).html());
    //在iframe中调用父页面中定义的变量
    // alert(parent.value);
    //在iframe中调用父页面中定义的方法
    // parent.sayhello();

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
    $("#fodderName").blur(function () {
        var regexp = /^[\u4e00-\u9fa5|\w]+$/,
            str = $("#fodderName").val();
        if (regexp.test(str) == false && str != "") {
            swal({
                title: "请填写中文、英文或数字",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("#fodderName").val("");
        }
        $("body").attr("style", "padding-right: 0!important;");
    });

    // 模态框隐藏事件
    $(".modal").on('hide.bs.modal', function () {
        $("body").attr("style", "padding-right: 0!important;");
    });

    // 选项框
    $("#typeSelect").selectpicker({
        noneSelectedText: "请选择类型",
        title: "请选择类型"
    });

    var type = $('#typeSelect').selectpicker("val"),
        fodderName = $("#fodderName").val(),
        tableUrl;

    if (fodderName == "") {
        fodderName = "&0";
    }

    tableUrl = "/cms/selectFodder/" + type + "/" + fodderName;

    // 表格
    function initTable(url) {
        $("#table").bootstrapTable({
            url: url, // 获取表格数据的url
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
            // height: $(window).height() -300,
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
            }, {
                field: "fileSize",
                title: "文件大小",
                align: "center",
                width: 100,
                valign: "middle",
                formatter: function (value, row, index) {
                    if (value == null) {
                        return "无";
                    } else {
                        var num = parseFloat(value / 1024 / 1024);
                        var size = num.toFixed(2) + "M";
                        return size;
                    }
                }
            }, {
                field: "fileTime",
                title: "持续时间",
                align: "center",
                width: 200,
                valign: "middle",
                visible: false
            }, {
                field: "fileName",
                title: "原文件名",
                align: "center",
                width: 250,
                valign: "middle",
                formatter: function (value, row, index) {
                    if (row.fodderType == 3 || value == "" || value == null) {
                        return "无";
                    } else {
                        return value;
                    }
                }
            }, {
                field: "auditStatus",
                title: "审核状态",
                align: "center",
                width: 100,
                valign: "middle",
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return "审核中";
                    } else if (value == 1) {
                        return "审核成功";
                    } else if (value == 2) {
                        return "审核失败";
                    } else if (value == 3) {
                        return "待人工复审";
                    } else if (value == 4) {
                        return "审核异常，请联系管理员";
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
                width: 200,
                valign: "middle"
                //--修改--获取日期列的值进行转换
                // formatter: function (value, row, index) {
                // return changeDateFormat(value);
                // }
            }, {
                field: "updateTime",
                title: "修改时间",
                align: "center",
                width: 200,
                valign: "middle"
                // visible: false,
                //--修改--获取日期列的值进行转换
                // formatter: function (value, row, index) {
                //     // return changeDateFormat(value);
                // }
            }, {
                field: "operation",
                title: "操作",
                align: "center",
                valign: "middle",
                width: 120,
                events: {
                    // 预览
                    "click #fodderPreview": function (e, value, row, index) {
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
                    },
                    // 下载
                    "click #fodderDownload": function (e, value, row, index) {
                        $(function () {
                            window.location.href = $.baseUrl + "/fodder/" + row.fodderId;
                        });
                    },
                    // 修改
                    "click #fodderEdit": function (e, value, row, index) {
                        $(function () {
                            $.edtiId = row.fodderId;
                            $.editStatu = row.auditStatus;
                            $("#editModal").modal();
                            $("#editName").val(row.fodderName);
                            $('[name="editBox"]').prop("checked", false);

                            if (row.auditStatus == 3) {
                                $(".editAuditLabel, .editAudit").css("display", "block");
                            } else {
                                $(".editAuditLabel, .editAudit").css("display", "none");
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

        if (row.auditStatus == 3) {
            return [
                '<div class="dropdown">\n' +
                '    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">操作\n' +
                '    </button>\n' +
                '    <div class="dropdown-menu">\n' +
                '      <a class="dropdown-item" href="javascript:void(0);" id="fodderPreview">预览</a>\n' +
                '      <div class="dropdown-divider"></div>\n' +
                '      <a class="dropdown-item" href="javascript:void(0);" id="fodderEdit">修改</a>\n' +
                '    </div>\n' +
                '  </div>'
            ].join("");
        } else if (row.auditStatus == 0) {
            return [
                '<div class="dropdown">\n' +
                '    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">操作\n' +
                '    </button>\n' +
                '    <div class="dropdown-menu">\n' +
                '      <a class="dropdown-item" href="javascript:void(0);" id="fodderPreview">预览</a>\n' +
                '    </div>\n' +
                '  </div>'
            ].join("");
        } else if (row.fodderType == 3) {
            return [
                '<div class="dropdown">\n' +
                '    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">操作\n' +
                '    </button>\n' +
                '    <div class="dropdown-menu">\n' +
                '      <a class="dropdown-item" href="javascript:void(0);" id="fodderPreview">预览</a>\n' +
                '      <div class="dropdown-divider"></div>\n' +
                '      <a class="dropdown-item" href="javascript:void(0);" id="fodderEdit">修改</a>\n' +
                '    </div>\n' +
                '  </div>'
            ].join("");
        } else {
            return [
                '<div class="dropdown">\n' +
                '    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" >操作\n' +
                '    </button>\n' +
                '    <div class="dropdown-menu" >\n' +
                '      <a class="dropdown-item" href="javascript:void(0);" id="fodderPreview">预览</a>\n' +
                '      <div class="dropdown-divider"></div>\n' +
                '      <a class="dropdown-item" href="javascript:void(0);" id="fodderDownload">下载</a>\n' +
                '      <div class="dropdown-divider"></div>\n' +
                '      <a class="dropdown-item" href="javascript:void(0);" id="fodderEdit">修改</a>\n' +
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
            return row.fodderId;
        });
    }

    // 预览隐藏清空内容
    $('#previewModal').on('hidden.bs.modal', function () {
        $("#videoPlay").attr("src", "");
        $("#picture").attr("src", "");
        $("#wordHtml").html("");
    });

    // 修改确认
    $("#editChecked").click(function () {
        var type = $('#typeSelect').selectpicker("val"),
            fodderName = $("#fodderName").val(),
            tableUrl;

        if (fodderName == "") {
            fodderName = "&0";
        }

        tableUrl = "/cms/selectFodder/" + type + "/" + fodderName;

        var fodderName = $("#editName").val(),
            editRadio;

        if (fodderName == "") {
            swal({
                title: "请填写素材名称",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
            return;
        }

        if ($.editStatu == 3) {
            editRadio = $('input:radio[name="editBox"]:checked').val();
        } else {
            editRadio = $.editStatu;
        }

        if (editRadio == undefined) {
            swal({
                title: "请选择审核状态",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
            return;
        }

        $.ajax({
            url: '/cms/updateFodder',
            type: "post",
            data: {
                fodderName: fodderName,
                fodderId: $.edtiId,
                auditStatus: editRadio
            },
            beforeSend: function () {
                // 禁用按钮防止重复提交
                $("#editChecked").attr('disabled', true);
            },
            success: function (result) {
                if (result.status == 200) {
                    swal({
                        title: "修改成功",
                        text: "",
                        type: "success",
                        allowOutsideClick: false
                    }).then((result) => {
                        if (result.value) {
                            $("#editModal").modal('hide');
                            $("#table").bootstrapTable('destroy');
                            initTable(tableUrl);
                            $("#editChecked").attr('disabled', false);
                            $("body").attr("style", "padding-right: 0!important;");
                        }
                    });
                } else if (result.status == 201) {
                    Swal.fire(
                        '修改失败',
                        '' + result.msg + '',
                        'error'
                    );
                    $("body").attr("style", "padding-right: 0!important;");
                }
            },
            error: function () {
                swal({
                    title: "系统错误，修改失败",
                    text: "",
                    type: "error",
                    allowOutsideClick: false
                });
                $("#editChecked").attr('disabled', false);
            },
            complete: function () {
                $("#editChecked").attr('disabled', false);
            }
        });
    });

    // 查询
    $("#fodderInquire").click(function () {
        var type = $('#typeSelect').selectpicker("val"),
            fodderName = $("#fodderName").val(),
            tableUrl;

        if (fodderName == "") {
            fodderName = "&0";
        }

        tableUrl = "/cms/selectFodder/" + type + "/" + fodderName;

        $.ajax({
            url: tableUrl,
            type: "post",
            // data: data,
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
    $("#fodderDelete").click(function () {
        var row = $("#table").bootstrapTable("getSelections"),
            auditArr = [];
        for (var i in row) {
            if (row[i].auditStatus == 0) {
                auditArr.push(row[i].auditStatus);
            }
        }

        var type = $('#typeSelect').selectpicker("val"),
            fodderName = $("#fodderName").val(),
            tableUrl;

        if (fodderName == "") {
            fodderName = "&0";
        }

        tableUrl = "/cms/selectFodder/" + type + "/" + fodderName;

        var fodderIds = getIdSelections();
        if (fodderIds.length == 0) {
            swal({
                title: "请选择要删除的素材",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
        } else if (auditArr.length != 0) {
            swal({
                title: "状态错误",
                text: "审核中不允许删除",
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
                        url: "/cms/deleteFodder",
                        type: "post",
                        data: {
                            fodderIds: fodderIds
                        },
                        // dataType: "json",
                        // contentType: "application/json;charset=UTF-8",
                        success: function (data) {
                            if (data.status == 200) {
                                Swal.fire(
                                    '删除!',
                                    '您的素材已被删除',
                                    'success'
                                ).then((result) => {
                                    if (result.value) {
                                        $("#table").bootstrapTable('destroy');
                                        initTable(tableUrl);
                                    }
                                });
                                $("body").attr("style", "padding-right: 0!important;");
                            } else if (data.status == 201) {
                                Swal.fire(
                                    '删除失败',
                                    '' + data.msg + '',
                                    'error'
                                );
                                $("body").attr("style", "padding-right: 0!important;");
                            } else if (data.status == 300) {
                                Swal.fire(
                                    '删除失败',
                                    '' + data.msg + '',
                                    'error'
                                );
                                $("body").attr("style", "padding-right: 0!important;");
                            }
                        },
                        error: function () {
                            swal({
                                title: "系统错误，删除失败",
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

    // 新增
    $("#fodderAdd").click(function () {
        $('input[name="sedUpload"]').prop("checked", false);
        $("#picName,#interCutName,#wordContent,#wordName").val("");
        $("#picName,#picUpload,#interCutName,#interCutUpload,#wordContent,#wordName").attr("disabled", "disabled");
    });

    // 选择图片上传
    $("#picSedUpload").click(function () {
        $("#picName,#picUpload").removeAttr("disabled");
        videoEmpty();
        wordEmpty();
    });

    // 选择视频上传
    $("#videoSedUpload").click(function () {
        $("#interCutName,#interCutUpload").removeAttr("disabled");
        picEmpty();
        wordEmpty();
    });

    // 选择文字上传
    $("#wordUpload").click(function () {
        $("#wordContent,#wordName").removeAttr("disabled");
        picEmpty();
        videoEmpty();
    });

    // 图片清空禁用
    function picEmpty() {
        $("#picName,#picUpload").attr("disabled", "disabled");
        $("#picName").val("");
    }

    // 视频清空禁用
    function videoEmpty() {
        $("#interCutName,#interCutUpload").attr("disabled", "disabled");
        $("#interCutName").val("");
    }

    // 文字清空禁用
    function wordEmpty() {
        $("#wordContent,#wordName").attr("disabled", "disabled");
        $("#wordContent,#wordName").val("");
    }

    // 图片上传按钮
    $("#picUpload").click(function () {
    });

    // 截图
    function imgCropper() {
        $.image = $('#imgContainer');
        var $dataX = $('#dataX'),
            $dataY = $('#dataY'),
            options = {
                aspectRatio: 9 / 16, //裁剪框的宽高比
                viewMode: 1, //视图模式,默认为0:无限制,1:截取框不能移到图片外部,2:不全部铺满背景,3:全部铺满背景
                preview: '.img-preview',
                crop: function (data) {
                    $dataX.val(Math.round(data.x));
                    $dataY.val(Math.round(data.y));
                }
            };
        var $inputImage = $('#inputImage');
        $.URL = window.URL || window.webkitURL;
        $.blobURL;
        $.originalImageURL = $.image.attr('src');
        $.uploadedImageName = '';

        $.image.on().cropper(options);

        // 图片上传按钮
        $("#picModal").on('click', '[data-method]', function () {
            var $this = $(this);
            var data = $this.data();
            var cropper = $.image.data('cropper');
            $.result;

            if (cropper && data.method) {
                data = $.extend({}, data); // Clone a new one
                $.result = $.image.cropper(data.method, data.option, data.secondOption);

                switch (data.method) {
                    case 'getCroppedCanvas':
                        if ($.result) {
                            $('.img-preview').html($.result);
                        }
                        break;
                    case 'destroy':
                        if ($.blobURL) {
                            $.URL.revokeObjectURL($.blobURL);
                            $.blobURL = '';
                            $.image.attr('src', $.originalImageURL);
                        }
                        break;
                }
            }
        });

        // 图片上传模态框确认
        $('.modal-footer').on('click', '[data-method]', function () {
            $("#picModal").modal('hide');
        });

        // Import image
        if ($.URL) {
            $inputImage.change(function () {
                var files = this.files;
                $.file;

                if (!$.image.data('cropper')) {
                    return;
                }

                if (files && files.length) {
                    $.file = files[0];
                    if (/^image\/\w+$/.test($.file.type)) {

                        $.uploadedImageName = $.file.name;
                        $.uploadedImageType = $.file.type;

                        $.fileSize = $.file.size;

                        if ($.blobURL) {
                            $.URL.revokeObjectURL($.blobURL);
                        }

                        $.blobURL = $.URL.createObjectURL($.file);
                        $.image.one('built.cropper', function () {
                            $.URL.revokeObjectURL($.blobURL); // Revoke when load complete
                        }).cropper('reset', true).cropper('replace', $.blobURL);

                        // $.image.cropper('destroy').attr('src', $.blobURL).cropper(options);

                        $inputImage.val('');

                    } else {
                        swal({
                            title: "请选择图片上传",
                            text: "",
                            type: "warning",
                            allowOutsideClick: false
                        });
                        $("body").attr("style", "padding-right: 0!important;");
                    }
                }
            });
        } else {
            $inputImage.parent().remove();
        }
    }

    imgCropper();

    //  上传图片
    function uploadImg(formData) {
        var tableUrl = "/cms/selectFodder/0/" + "&0";
        $.fileName = $.file.name.split(".");
        $.fileFormat = $.fileName[$.fileName.length - 1];

        $.ajax({
            url: '/cms/fodder/fileUpload',
            type: "post",
            data: formData,
            contentType: false,
            processData: false,
            beforeSend: function () {
                // 禁用按钮防止重复提交
                $("#addConfirm").attr('disabled', true);
            },
            success: function (data) {
                if (data.status == 200) {
                    $.fodderContext = data.data.currentUrl;

                    var fodderData = {
                        fodderContext: $.fodderContext,
                        fodderName: $.adsTitle,
                        fodderType: 1,
                        fileSize: $.fileSize,
                        fileName: $.uploadedImageName,
                        fileFormat: $.fileFormat
                    };

                    $.ajax({
                        url: '/cms/insertFodder',
                        type: "post",
                        data: fodderData,
                        success: function (result) {
                            swal({
                                title: "已上传",
                                text: "文件审核中",
                                type: "success",
                                allowOutsideClick: false
                            });
                            $("#addModal").modal('hide');
                            $("#table").bootstrapTable('destroy');
                            initTable(tableUrl);
                            $("#addConfirm").attr('disabled', false);
                            $("body").attr("style", "padding-right: 0!important;");
                        },
                        error: function () {

                        }
                    });

                } else if (data.status == 201) {
                    Swal.fire(
                        '删除失败',
                        '' + data.msg + '',
                        'error'
                    );
                    $("body").attr("style", "padding-right: 0!important;");
                }
            },
            error: function () {
                swal({
                    title: "系统失败，上传失败",
                    text: "系统错误，请联系管理员",
                    type: "error",
                    allowOutsideClick: false
                });
                $("#addConfirm").attr('disabled', false);
            },
            complete: function () {
                $("#addConfirm").attr('disabled', false);
            }
        });
    }

    //添加转化为二进制方法
    function processData(dataUrl) {
        var binaryString = window.atob(dataUrl.split(',')[1]);
        var arrayBuffer = new ArrayBuffer(binaryString.length);
        var intArray = new Uint8Array(arrayBuffer);
        for (var i = 0, j = binaryString.length; i < j; i++) {
            intArray[i] = binaryString.charCodeAt(i);
        }
        var data = [intArray],
            blob;
        try {
            blob = new Blob(data);
        } catch (e) {
            window.BlobBuilder = window.BlobBuilder ||
                window.WebKitBlobBuilder ||
                window.MozBlobBuilder ||
                window.MSBlobBuilder;
            if (e.name === 'TypeError' && window.BlobBuilder) {
                var builder = new BlobBuilder();
                builder.append(arrayBuffer);
                blob = builder.getBlob($.uploadedImageType); // imgType为上传文件类型，即 file.type
            } else {
                swal({
                    title: "版本过低，不支持上传图片",
                    text: "",
                    type: "warning",
                    allowOutsideClick: false
                });
                $("body").attr("style", "padding-right: 0!important;");
            }
        }
        return blob;
    }

    // 视频上传按钮
    $("#interCutUpload").click(function () {
        $("#interCutModal").modal();
        $(".fileinput-remove-button").click();
        initUpload("interCutFile", "/cms/fodder/fileUpload");
    });

    function initUpload(ctrlName, uploadUrl) {
        var ctrlNames = "#" + ctrlName;
        $(ctrlNames).fileinput({
            language: "zh", //设置语言
            uploadUrl: uploadUrl, //上传的地址
            uploadAsync: true, //默认异步上传
            showCaption: true, //是否显示标题
            showUpload: false, //是否显示上传按钮
            showCancel: false, //是否显示取消按钮
            browseClass: "btn btn-primary", //按钮样式
            allowedFileExtensions: ["AVI", "MP4"], //接收的文件后缀
            enctype: 'multipart/form-data',
            maxFileSize: 204800,
            maxFileCount: 1, //最大上传文件数限制
            autoReplace: true,
            previewFileIcon: '<i class="glyphicon glyphcion-file"></i>',
            showPreview: true, //是否显示预览
            dropZoneTitle: "… 拖拽文件到这里 …<br>不支持多文件同时上传<br>选择多个文件系统默认上传最后一个", //拖拽区域内容设置
            fileActionSettings: {
                showRemove: false,
                showUpload: false,
                showZoom: false
            }
        }).on("fileuploaded", function (event, data, previewId, index) {
            $.adsTitle = $("#interCutName").val();
            $.adsContent = data.response.data.downloadUrl;
            var adsFormatArr = $.adsContent.split(".");
            $.adsFormat = adsFormatArr[adsFormatArr.length - 1];
            $.fileSize = data.files[0].size;

            var tableUrl = "/cms/selectFodder/0/" + "&0";

            var fodderData = {
                fodderContext: $.adsContent,
                fodderName: $.adsTitle,
                fodderType: 2,
                fileSize: $.fileSize,
                fileName: data.files[0].name,
                fileFormat: $.adsFormat
            };

            $.ajax({
                url: '/cms/insertFodder',
                data: fodderData,
                type: "POST",
                beforeSend: function () { // 禁用按钮防止重复提交
                    $("#addConfirm").attr('disabled', true);
                },
                success: function (result) {
                    swal({
                        title: "已上传",
                        text: "文件审核中",
                        type: "success",
                        allowOutsideClick: false
                    });
                    $("#table").bootstrapTable('destroy');
                    initTable(tableUrl);
                    $("#addConfirm").attr('disabled', false);
                    $("#addModal").modal('hide');
                    $("body").attr("style", "padding-right: 0!important;");
                },
                error: function () {
                    swal({
                        title: "系统错误，提交失败",
                        text: '',
                        type: "error",
                        allowOutsideClick: false
                    });
                    $("#addConfirm").attr('disabled', false);
                    $("body").attr("style", "padding-right: 0!important;");
                },
                complete: function () {
                    $("#addConfirm").attr('disabled', false);
                }
            });
        });
    }

    // 获取视频文件名
    $("#interCutChecked").click(function () {
        var warringText = $(".kv-fileinput-error").find("li").text();
        if (warringText == "") {
            $("#interCutModal").modal('hide');
            $.interCutUrl = $(".file-caption-info").html();
            return $.interCutUrl;
        } else {
            swal({
                title: "视频格式错误或大小超出限制，请重新选择",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
        }
    });

    // 新增确认
    $("#addConfirm").click(function () {
        var tableUrl = "/cms/selectFodder/0/" + "&0";

        // 图片上传
        if ($('#picSedUpload').is(":checked")) {
            var cas = $.result;
            $.adsTitle = $("#picName").val();
            if ($.adsTitle == "") {
                swal({
                    title: "请填写图片名称",
                    text: "",
                    type: "warning",
                    allowOutsideClick: false
                });
                $("body").attr("style", "padding-right: 0!important;");
                return false;
            } else if ($('.img-preview').html() == "") {
                swal({
                    title: "请选择图片",
                    text: "",
                    type: "warning",
                    allowOutsideClick: false
                });
                $("body").attr("style", "padding-right: 0!important;");
                return false;
            } else {
                var blob = processData(cas.toDataURL());
                var formData = new FormData();
                formData.append('file', blob);
                formData.append('fileName', $.uploadedImageName);

                var imgHeight = $('#imgContainer').cropper('getData', true).height;
                var imgWidth = $('#imgContainer').cropper('getData', true).width;

                if (imgHeight > 4096 || imgWidth > 4096) {
                    swal({
                        title: "图片尺寸超出限制，请重新选择！",
                        text: "图片长宽分别不能超过4096像素",
                        type: "error",
                        allowOutsideClick: false
                    });
                    return;
                }

                if ($.fileSize > 4194304) {
                    swal({
                        title: "图片大小超出限制，请重新选择！",
                        text: "图片大小要小于4M",
                        type: "error",
                        allowOutsideClick: false
                    });
                    return;
                }

                swal({
                    title: "审核中",
                    text: "文件正在审核，请确认",
                    confirmButtonText: '确认',
                    showLoaderOnConfirm: true,
                    preConfirm: function () {
                        return new Promise(function (resolve) {
                            uploadImg(formData);
                        })
                    }
                });
            }
        } else if ($('#videoSedUpload').is(":checked")) {
            $.adsTitle = $("#interCutName").val();
            if ($.adsTitle == "") {
                swal({
                    title: "请填写视频名称",
                    text: "",
                    type: "warning",
                    allowOutsideClick: false
                });
                $("body").attr("style", "padding-right: 0!important;");
                return false;
            } else if (typeof ($.interCutUrl) == "undefined") {
                swal({
                    title: "请上传一个视频",
                    text: "",
                    type: "warning",
                    allowOutsideClick: false
                });
                $("body").attr("style", "padding-right: 0!important;");
                return false;
            }

            swal({
                title: "正在发往审核",
                text: "视频正在上传，审核通过将自动为您发布，请确认",
                confirmButtonText: '确认',
                showLoaderOnConfirm: true,
                preConfirm: function () {
                    return new Promise(function (resolve) {
                        $('#interCutFile').fileinput('upload');
                    })
                }
            });
        } else if ($('#wordUpload').is(":checked")) {
            var wordName = $("#wordName").val();
            var wordContent = $("#wordContent").val();
            if (wordName == "") {
                swal({
                    title: "请填写文字标题",
                    text: "",
                    type: "warning",
                    allowOutsideClick: false
                });
                $("body").attr("style", "padding-right: 0!important;");
                return;
            } else if (wordContent == "") {
                swal({
                    title: "请填写文字内容",
                    text: "",
                    type: "warning",
                    allowOutsideClick: false
                });
                $("body").attr("style", "padding-right: 0!important;");
                return;
            }
            $.ajax({
                url: '/cms/insertFodder',
                data: {
                    fodderName: wordName,
                    fodderType: 3,
                    fodderContext: wordContent,
                    fileSize: "",
                    fileName: "",
                    fileFormat: ""
                },
                type: "POST",
                // dataType: "json",
                // contentType: "application/json;charset=UTF-8",
                beforeSend: function () { // 禁用按钮防止重复提交
                    $("#addConfirm").attr('disabled', true);
                },
                success: function (result) {
                    swal({
                        title: "已上传",
                        text: "文件审核中",
                        type: "success",
                        allowOutsideClick: false
                    });
                    $("#table").bootstrapTable('destroy');
                    initTable(tableUrl);
                    $("#addConfirm").attr('disabled', false);
                    $("#addModal").modal('hide');
                    $("body").attr("style", "padding-right: 0!important;");
                },
                error: function () {
                    swal({
                        title: "系统错误",
                        text: '',
                        type: "error",
                        allowOutsideClick: false
                    });
                    $("#addConfirm").attr('disabled', false);
                    $("body").attr("style", "padding-right: 0!important;");
                },
                complete: function () {
                    $("#addConfirm").attr('disabled', false);
                }
            });
        } else {
            swal({
                title: "请选择上传内容类型",
                text: "",
                type: "warning",
                allowOutsideClick: false
            });
            $("body").attr("style", "padding-right: 0!important;");
        }
    });
});