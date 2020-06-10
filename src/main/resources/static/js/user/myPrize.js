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
        url: '/prize/pageByUserParams', //数据接口
        where: { userId: sessionStorage.id },
        cols: [[
            { field: "name", title: "奖项" },
            { field: "projectName", title: "项目名称" },
        ]],
        page: true,
        start:{curr:0},//重新从第一页开始
        request:{
            pageName:'start'
        }
    });

});