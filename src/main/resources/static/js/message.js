'use strict';

layui.config({
    base: '/js/' //静态资源所在路径
}).use(['form', 'table'], function () {
    var form = layui.form
        , table = layui.table;

    //消息列表数据表格
    table.render({
        elem: '#table_message',
        height: 600,
        width: 1200,
        url: '/message/pageByParams', //数据接口
        where: { userId: sessionStorage.id, power: sessionStorage.power, sort: "time,DESC" },
        cols: [[
            { field: "time", title: "时间",sort:true },
            { field: "content", title: "内容" }
        ]],
        page: true,
        start:{curr:0},//重新从第一页开始
        request:{
            pageName:'start'
        }
    });

    //监听查询按钮
    $("#search").click(function () {
        table.reload('table_message', {
            where: { content: $('#input').val() }
        });
    });
});