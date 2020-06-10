'use strict';

layui.config({
    base: '/js' //静态资源所在路径
}).use(['form', 'table'], function () {
    var form = layui.form
        , table = layui.table;

    //用户列表数据表格
    table.render({
        elem: '#table_user',
        height: 600,
        width: 900,
        startByZero: true, //分页查询从page=0页开始
        url: '/user/pageByParams', //数据接口
        cols: [[
            //{ field: "id", title: "编号", sort: true},
            { field: "account", title: "账号" },
            { field: "password", title: "密码" },
            { field: "name", title: "姓名" },
            { fixed: 'right', align: 'center', toolbar: '#toolbar', width: 200 }
        ]],
        page: true,
        start:{curr:0},//重新从第一页开始
        request:{
            pageName:'start'
        }
    });

    //监听"注册"按钮
    window.btn_addUser = function () {
        $('#userAccount').attr("readonly", false);
        layer.open({
            type: 1, //页面层
            title: "注册",
            area: ['500px', '400px'],
            btn: ['保存', '取消'],
            btnAlign: 'c', //按钮居中
            content: $('#div_addUser'),
            success: function (layero, index) {// 弹出layer后的回调函数,参数分别为当前层DOM对象以及当前层索引
                // 解决按回车键重复弹窗问题
                $(':focus').blur();
                // 为当前DOM对象添加form标志
                layero.addClass('layui-form');
                // 将保存按钮赋予submit属性
                layero.find('.layui-layer-btn0').attr({
                    'lay-filter': 'btn_saveUserAdd',
                    'lay-submit': ''
                });
                // 表单验证
                form.verify();
                // 刷新渲染(否则开关按钮不会显示)
                form.render();
            },
            yes: function (index, layero) {// 确认按钮回调函数,参数分别为当前层索引以及当前层DOM对象
                form.on('submit(btn_saveUserAdd)', function (data) {//data按name获取
                    if (data.field.password != data.field.pwAgain) {
                        layer.msg("两次密码不一致，请重新输入!");
                        return false;
                    }
                    $.ajax({
                        type: 'post',
                        url: "/user/add",
                        dataType: 'json',
                        data: data.field,
                        success: function (res) {
                            if (res.code === 200) {
                                layer.alert("注册成功!", function (index) {
                                    window.location.reload();
                                });
                            }
                            else if (res.code === 507) {
                                layer.alert("已存在该账号!");
                            }
                        }
                    });
                    return false;
                });
            }
        });
    };

    //监听表格工具栏
    table.on('tool(table_user)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            $('#id').val(data.id);
            $('#userName').val(data.name);
            $('#userPw').val(data.password);
            $('#userPwAgain').val(data.password);
            $('#userAccount').val(data.account);
            $('#userAccount').attr("readonly", true);
            layer.open({
                type: 1, //页面层
                title: "修改用户信息",
                area: ['600px', '400px'],
                btn: ['保存', '取消'],
                btnAlin: 'c', //按钮居中
                content: $('#div_addUser'),
                success: function (layero, index) {// 弹出layer后的回调函数,参数分别为当前层DOM对象以及当前层索引
                    // 解决按回车键重复弹窗问题
                    $('focus').blur();
                    // 为当前DOM对象添加form标志
                    layero.addClass('layui-form');
                    // 将保存按钮赋予submit属性
                    layero.find('.layui-layer-btn0').attr({
                        'lay-filter': 'btn_saveUserEdit',
                        'lay-submit': ''
                    });
                    // 表单验证
                    form.verify();
                    // 刷新渲染(否则开关按钮不会显示)
                    form.render();
                },
                yes: function (index, layero) {// 确认按钮回调函数,参数分别为当前层索引以及当前层DOM对象
                    form.on('submit(btn_saveUserEdit)', function (data) {//data按name获取
                        if (data.field.password != data.field.pwAgain) {
                            layer.msg("两次密码不一致，请重新输入!");
                            return false;
                        }
                        $.ajax({
                            type: 'post',
                            url: '/user/update',
                            dataType: 'json',
                            data: data.field,
                            success: function (res) {
                                if (res.code === 200) {
                                    layer.alert("修改成功!", function (index) {
                                        window.location.reload();
                                    });
                                }
                            }
                        });
                    });
                }
            });
        }
        else if (obj.event === 'del') {
            layer.confirm('确认删除该账户?该操作将导致该用户所拥有项目一并删除！', function () {
                $.ajax({
                    type: 'delete',
                    url: '/user/delete',
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
        }
    });

    //监听查询按钮
    $("#search").click(function () {
        table.reload('table_user', {
            where: { name: $('#input').val() }
        });
    });

});