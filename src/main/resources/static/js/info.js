'use strict'

// 初始化信息
window.onload = function () {
    if (sessionStorage.power === "admin") {
        $.ajax({
            type: 'get',
            url: '/admin/getAccount',
            dataType: 'json',
            data: { userId: sessionStorage.id },
            success: function (res) {
                if (res.code === 200) {
                    $('#userId').val(sessionStorage.id);
                    $('#name').val($("#userName",parent.document).text());
                    $('#account').val(res.data["account"]);
                    $('#power').val(sessionStorage.power);
                }
            }
        });
    }
    else if (sessionStorage.power === "user"){
        $.ajax({
            type: 'get',
            url: '/user/getAccount',
            dataType: 'json',
            data: { userId: sessionStorage.id },
            success: function (res) {
                if (res.code === 200) {
                    $('#userId').val(sessionStorage.id);
                    $('#name').val($("#userName",parent.document).text());
                    $('#account').val(res.data["account"]);
                    $('#power').val(sessionStorage.power);
                }
            }
        });
    }
}

layui.config({
    base: '/js' //静态资源所在路径
}).use(['form'], function () {
    var form = layui.form;

    //监听修改按钮
    $("#submit").click(function () {
        if ($('#name').val() == null) {
            layer.msg("昵称不可为空!");
            return false;
        }
        if (sessionStorage.power === "admin") {
            $.ajax({
                url: "/admin/update",
                dataType: "json",
                type: "post",
                data: {
                    name: $('#name').val(),
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
                    name: $('#name').val(),
                    id: sessionStorage.id,
                },
                success: function (res) {
                    if (res.code === 200) {
                        layer.alert("修改成功!", function (index) {
                            // window.location.reload();
                            parent.window.location.reload();
                        });
                    }
                    else if (res.code === 402) {
                        layer.alert("修改失败!");
                    }
                },
            });
        }
    });
});