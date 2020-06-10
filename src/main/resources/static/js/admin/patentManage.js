'use strict';

layui.config({
    base: '/js/' //静态资源所在路径
}).use(['form', 'table'], function () {
    var form = layui.form
        , table = layui.table;

    //申报项目列表数据表格
    table.render({
        elem: '#table_patent',
        height: 600,
        width: 1200,
        url: '/patent/pageByParams', //数据接口
        cols: [[
            { field: "flag", title: "专利号" },
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
    table.on('tool(table_patent)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('确认删除该专利吗？', function () {
                $.ajax({
                    type: 'delete',
                    url: '/patent/delete',
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
        table.reload('table_patent', {
            where: { projectName: $('#input').val() }
        });
    });
});