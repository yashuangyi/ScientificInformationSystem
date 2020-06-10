'use strict';

layui.config({
    base: '/js/' //静态资源所在路径
}).use(['form', 'table'], function () {
    var form = layui.form
        , table = layui.table;

    //申报项目列表数据表格
    table.render({
        elem: '#table_prize',
        height: 600,
        width: 1200,
        url: '/prize/pageByParams', //数据接口
        cols: [[
            { field: "name", title: "奖项" },
            { field: "projectName", title: "项目名称" },
            { field: "userName", title: "申报人" },
            { fixed: 'right', align: 'center', toolbar: '#toolbar', width: 320 }
        ]],
        page: true,
        start:{curr:0},//重新从第一页开始
        request:{
            pageName:'start'
        }
    });

    //监听表格工具栏
    table.on('tool(table_prize)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('确认删除该奖项吗？', function () {
                $.ajax({
                    type: 'delete',
                    url: '/prize/delete',
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
        } else if(obj.event === 'edit'){
            layer.prompt({
                formType: 0,
                title: '请修改奖项',
                area: ['800px', '350px'] //自定义文本域宽高
            }, function (value, index, elem) {
                $.ajax({
                    type: 'post',
                    url: '/prize/update',
                    dataType: 'json',
                    data: { projectId: data.projectId, name: value },
                    success: function (res) {
                        if (res.code === 200) {
                            layer.alert("修改成功!", function success() {
                                window.location.reload();
                            });
                        }
                        else {
                            layer.alert("修改失败，请联系系统管理员!");
                        }
                    }
                });
            });
        }
    });

    //监听查询按钮
    $("#search").click(function () {
        table.reload('table_prize', {
            where: { projectName: $('#input').val() }
        });
    });
});