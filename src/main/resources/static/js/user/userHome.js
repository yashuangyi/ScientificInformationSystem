'use strict';

window.onload = function () {
    if(sessionStorage.power !== "user"){
        layer.alert("权限不足！");
        location.href = "/logout";
    }

    $.ajax({
        type: 'get',
        url: '/user/getName',
        dataType: 'json',
        data: {userId:sessionStorage.id},
        success: function (res) {
            if (res.code === 200) {
                $("#userName").text(res.data["name"]);
            }
        }
    });
};