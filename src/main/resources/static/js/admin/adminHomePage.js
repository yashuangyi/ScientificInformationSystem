'use strict';

window.onload = function () {
    $.ajax({
        type: 'get',
        url: '/project/getAdminCount',
        dataType: 'json',
        success: function (res) {
            if (res.code === 200) {
                $("#num_shouldGrant").text(res.data["shouldGrant"]);
                $("#num_shouldPass").text(res.data["shouldPass"]);
                $("#num_hasDeclare").text(res.data["hasDeclare"]);
            }
        }
    });
};