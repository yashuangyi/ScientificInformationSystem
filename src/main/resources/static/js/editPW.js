'use strict'

layui.config({
    base: '/js' //静态资源所在路径
}).use(['form'], function () {
    var form = layui.form;

    //监听修改按钮
    $("#submit").click(function () {
        if ($('#newPw').val() != $('#againPw').val()) {
            layer.msg("两次新密码不一致，请重新输入!");
            return false;
        }
        if (sessionStorage.power === "admin") {
            $.ajax({
                url: "/admin/update",
                dataType: "json",
                type: "post",
                data: {
                    password: $('#newPw').val(),
                    id: sessionStorage.id,
                },
                success: function (res) {
                    if (res.code === 200) {
                        layer.alert("修改成功!", function (index) {
                            window.location.reload();
                        });
                    }
                    else if (res.code === 402) {
                        layer.alert("修改失败!");
                    }
                },
            });
        }
        else if (sessionStorage.power === "user") {
            $.ajax({
                url: "/user/update",
                dataType: "json",
                type: "post",
                data: {
                    password: $('#newPw').val(),
                    id: sessionStorage.id,
                },
                success: function (res) {
                    if (res.code === 200) {
                        layer.alert("修改成功!", function (index) {
                            window.location.reload();
                        });
                    }
                    else {
                        layer.alert("修改失败!");
                    }
                },
            });
        }
    });
});