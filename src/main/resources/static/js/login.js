'use strict'

layui.config({
    base: '/js/'
}).use(['layer', 'form'], function () {
    var form = layui.form;

    //监听登录按钮
    $("#login").click(function () {
        $.ajax({
            url: "./login",
            dataType: "json",
            contentType:'application/json;charset=utf-8',
            type: "post",
            data: JSON.stringify({
                account: $('#account').val(),
                password: $('#password').val(),
            }),
            success: function (res) {
                if (res.code === 200) {
                    layer.open({
                        title: '欢迎用户~'
                        , content: '登录成功!'
                        , end: function () {
                            sessionStorage.power = "user";
                            sessionStorage.id = res.data;
                            location.href = "/userHome";
                        }
                    });
                }
                else if (res.code === 201) {
                    layer.open({
                        title: '欢迎管理员~'
                        , content: '登录成功!'
                        , end: function () {
                            sessionStorage.power = "admin";
                            sessionStorage.id = res.data;
                            location.href = "/adminHome";
                        }
                    });
                }
                else if (res.code === 401) {
                    layer.open({
                        title: 'Fail'
                        , content: '请输入账号及密码!'
                    });
                }
                else if (res.code === 404) {
                    layer.open({
                        title: 'Fail'
                        , content: '账号或密码错误，请重新输入!'
                    });
                    $('#password').val("");
                }
            },
        });
    });

    //监听"注册"按钮
    window.btn_addUser = function () {
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

    //监听“回车键”
    $(document).keyup(function (event) {
        if (event.keyCode === 13) {
            $("#login").click();
        }
    });
});