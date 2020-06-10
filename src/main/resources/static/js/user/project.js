'use strict';

layui.config({
    base: '/js/' //静态资源所在路径
}).use(['form', 'table', 'upload'], function () {
    var form = layui.form
        , upload = layui.upload
        , table = layui.table;

    //申报项目列表数据表格
    table.render({
        elem: '#table_declare',
        height: 600,
        width: 1200,
        url: '/project/pageByParams', //数据接口
        where: { userId: sessionStorage.id, sort: "startTime,DESC" },
        cols: [[
            { field: "name", title: "项目名称" },
            { field: "description", title: "简述" },
            { field: "startTime", title: "申报时间",sort:true },
            { field: "endTime", title: "完成时间" ,sort:true},
            { field: "status", title: "状态", templet: '#statusbar' ,sort:true},
            { fixed: 'right', align: 'center', toolbar: '#toolbar', width: 200 }
        ]],
        page: true,
        start:{curr:0},//重新从第一页开始
        request:{
          pageName:'start'
        }
    });

    //上传成果功能
    upload.render({
        elem: '#btn_selectResult',
        url: '/project/submit',
        auto: false,//不自动上传
        data: {dict:"results/"},
        accept: 'file',
        exts:"zip|rar|7z",
        bindAction: '#btn_uploadResult', //触发上传的按钮
        before: function () {
            layer.load();
        },
        done: function (res) {
            layer.closeAll('loading');
            $('#btn_selectResult').html("<i class=''layui-icon layui-icon-upload-drag'></i>重新选择");
            if (res.code === 200) {
                layer.msg(res.msg);
                $('#result').html("<i class='layui-icon layui-icon-file'></i> <a href='" + res.data["path"] + "'>" + res.data["name"] + "<a/>");
                $('#resultPath').val(res.data["path"]);
            } else {
                layer.msg(res.msg);
                $('#result').html("");
                $('#resultPath').val("");
            }
        },
    });

    //上传论文功能
    upload.render({
        elem: '#btn_selectPaper',
        url: '/project/submit',
        auto: false,//不自动上传
        accept: 'file',
        data: {dict:"papers/"},
        acceptMime: 'file/pdf,file/docx',
        exts: 'pdf|docx',
        bindAction: '#btn_uploadPaper', //触发上传的按钮
        before: function () {
            layer.load();
        },
        done: function (res) {
            layer.closeAll('loading');
            $('#btn_selectPaper').html("<i class=''layui-icon layui-icon-upload-drag'></i>重新选择");
            if (res.code === 200) {
                layer.msg(res.msg);
                $('#paper').html("<i class='layui-icon layui-icon-file'></i> <a href='" + res.data["path"] + "'>" + res.data["name"] + "<a/>");
                $('#paperPath').val(res.data["path"]);
            } else {
                layer.msg(res.msg);
                $('#paper').html("");
                $('#paperPath').val("");
            }
        },
    });

    //监听"申报项目"按钮
    window.btn_addDeclare = function () {
        $("#userId").val(sessionStorage.id);
        layer.open({
            type: 1, //页面层
            title: "申报登记",
            area: ['600px', '300px'],
            btn: ['保存', '取消'],
            btnAlign: 'c', //按钮居中
            content: $('#div_addDeclare'),
            success: function (layero, index) {// 弹出layer后的回调函数,参数分别为当前层DOM对象以及当前层索引
                // 解决按回车键重复弹窗问题
                $(':focus').blur();
                // 为当前DOM对象添加form标志
                layero.addClass('layui-form');
                // 将保存按钮赋予submit属性
                layero.find('.layui-layer-btn0').attr({
                    'lay-filter': 'btn_saveDeclareAdd',
                    'lay-submit': ''
                });
                // 表单验证
                form.verify();
                // 刷新渲染(否则开关按钮不会显示)
                form.render();
            },
            yes: function (index, layero) {// 确认按钮回调函数,参数分别为当前层索引以及当前层DOM对象
                form.on('submit(btn_saveDeclareAdd)', function (data) {//data按name获取
                    $.ajax({
                        type: 'post',
                        url: '/project/add',
                        dataType: 'json',
                        data: data.field,
                        success: function (res) {
                            if (res.code === 200) {
                                layer.alert("申报成功!待管理员审核通过后即可开发", function (index) {
                                    window.location.reload();
                                });
                            }
                            else {
                                layer.alert("申报失败!");
                            }
                        }
                    });
                    return false;
                });
            }
        });
    };

    //监听表格工具栏
    table.on('tool(table_declare)', function (obj) {
        var data = obj.data;
        if (obj.event === 'submit') {
            $("#projectId").val(data.id);
            layer.open({
                type: 1, //页面层
                title: "提交成果",
                area: ['600px', '300px'],
                btn: ['保存', '取消'],
                btnAlign: 'c', //按钮居中
                content: $('#div_submitResult'),
                success: function (layero, index) {// 弹出layer后的回调函数,参数分别为当前层DOM对象以及当前层索引
                    // 解决按回车键重复弹窗问题
                    $(':focus').blur();
                    // 为当前DOM对象添加form标志
                    layero.addClass('layui-form');
                    // 将保存按钮赋予submit属性
                    layero.find('.layui-layer-btn0').attr({
                        'lay-filter': 'btn_saveResultAdd',
                        'lay-submit': ''
                    });
                    // 表单验证
                    form.verify();
                    // 刷新渲染(否则开关按钮不会显示)
                    form.render();
                },
                yes: function (index, layero) {// 确认按钮回调函数,参数分别为当前层索引以及当前层DOM对象
                    form.on('submit(btn_saveResultAdd)', function (data) {//data按name获取
                        if ($('#resultPath').val() == "" || $('#resultPath').val() == null) {
                            layer.alert("请上传成果！");
                            return false;
                        }
                        $.ajax({
                            type: 'post',
                            url: '/project/submitResult',
                            dataType: 'json',
                            data: data.field,
                            success: function (res) {
                                if (res.code === 200) {
                                    layer.alert("提交成功!请耐心等待管理员依次审批", function (index) {
                                        window.location.reload();
                                    });
                                }
                                else {
                                    layer.alert("提交失败!");
                                }
                            }
                        });
                        return false;
                    });
                }
            });
        }
        else if (obj.event === 'paper') {
            $("#projectId2").val(data.id);
            layer.open({
                type: 1, //页面层
                title: "提交论文",
                area: ['600px', '300px'],
                btn: ['保存', '取消'],
                btnAlign: 'c', //按钮居中
                content: $('#div_submitPaper'),
                success: function (layero, index) {// 弹出layer后的回调函数,参数分别为当前层DOM对象以及当前层索引
                    // 解决按回车键重复弹窗问题
                    $(':focus').blur();
                    // 为当前DOM对象添加form标志
                    layero.addClass('layui-form');
                    // 将保存按钮赋予submit属性
                    layero.find('.layui-layer-btn0').attr({
                        'lay-filter': 'btn_savePaperAdd',
                        'lay-submit': ''
                    });
                    // 表单验证
                    form.verify();
                    // 刷新渲染(否则开关按钮不会显示)
                    form.render();
                },
                yes: function (index, layero) {// 确认按钮回调函数,参数分别为当前层索引以及当前层DOM对象
                    form.on('submit(btn_savePaperAdd)', function (data) {//data按name获取
                        if ($('#paperPath').val() == "" || $('#paperPath').val() == null) {
                            layer.alert("请上传论文！");
                            return false;
                        }
                        $.ajax({
                            type: 'post',
                            url: '/paper/add',
                            dataType: 'json',
                            data: {projectId:$("#projectId2").val(), path:$("#paperPath").val()},
                            success: function (res) {
                                if (res.code === 200) {
                                    layer.alert("提交成功!", function (index) {
                                        window.location.reload();
                                    });
                                }
                                else {
                                    layer.alert("您已提交过了，请勿重复操作!");
                                }
                            }
                        });
                        return false;
                    });
                }
            });
        }
    });

    //监听查询按钮
    $("#search").click(function () {
        table.reload('table_declare', {
            where: { name: $('#input').val() }
        });
    });
});