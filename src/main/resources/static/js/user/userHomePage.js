'use strict';

window.onload = function () {
    $.ajax({
        type: 'get',
        url: '/project/getUserCount',
        dataType: 'json',
        data: {userId:sessionStorage.id},
        success: function (res) {
            if (res.code === 200) {
                $("#num_declare").text(res.data["declare"]);
                $("#num_pass").text(res.data["pass"]);
                $("#num_prize").text(res.data["prize"]);
            }
        }
    });
};