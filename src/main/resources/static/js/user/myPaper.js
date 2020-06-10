'use strict';

layui.config({
    base: '/js/' //静态资源所在路径
}).use(['form', 'table', 'upload'], function () {
    var form = layui.form
        , upload = layui.upload
        , table = layui.table;

    //列表数据表格
    table.render({
        elem: '#table_paper',
        height: 600,
        width: 800,
        url: '/paper/pageByUserParams', //数据接口
        where: { userId: sessionStorage.id },
        cols: [[
            { field: "projectName", title: "项目名称" },
            { fixed: 'right', align: 'center', toolbar: '#toolbar', width: 320 }
        ]],
        page: true,
        start:{curr:0},//重新从第一页开始
        request:{
            pageName:'start'
        }
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

    //监听表格工具栏
    table.on('tool(table_paper)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            $("#projectId2").val(data.projectId);
            layer.open({
                type: 1, //页面层
                title: "修改论文",
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
                            url: '/paper/update',
                            dataType: 'json',
                            data: {projectId:$("#projectId2").val(), path:$("#paperPath").val()},
                            success: function (res) {
                                if (res.code === 200) {
                                    layer.alert("修改成功!", function (index) {
                                        window.location.reload();
                                    });
                                }
                                else {
                                    layer.alert("您已提交过了!");
                                }
                            }
                        });
                        return false;
                    });
                }
            });
        } else if (obj.event === 'del') {
            layer.confirm('确认删除该论文吗？', function () {
                $.ajax({
                    type: 'delete',
                    url: '/paper/delete',
                    dataType: 'json',
                    data: { id: data.id },
                    success: function (res) {
                        if (res.code === 200) {
                            layer.alert("删除成功!", function (index) {
                                window.location.reload();
                            });
                        }
                    }
                });
            });
        } else if (obj.event === 'download') {
            if(data.paperPath === null){
                layer.alert("该项目尚未上传论文!");
                return false;
            }
            //window.open("C:\\Users\\chenz\\Desktop\\外包\\流程.txt");
            window.open(data.paperPath);
        }
    });

});