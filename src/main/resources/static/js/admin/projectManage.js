'use strict';

layui.config({
    base: '/js/' //静态资源所在路径
}).use(['form', 'table'], function () {
    var form = layui.form
        , table = layui.table;

    //申报项目列表数据表格
    table.render({
        elem: '#table_project',
        height: 600,
        width: 1400,
        url: '/project/pageUserAndProjectByParams', //数据接口
        where: { sort: "startTime,DESC" },
        cols: [[
            { field: "name", title: "项目名称" },
            { field: "description", title: "简述" },
            { field: "userName", title: "申报人" },
            { field: "startTime", title: "申报时间",sort:true, width: 200},
            { field: "endTime", title: "完成时间" ,sort:true, width: 200},
            { field: "status", title: "状态", templet: '#statusbar',sort:true },
            { fixed: 'right', align: 'center', toolbar: '#toolbar', width: 320 }
        ]],
        page: true,
        start:{curr:0},//重新从第一页开始
        request:{
            pageName:'start'
        }
    });

    //监听表格工具栏
    table.on('tool(table_project)', function (obj) {
        var data = obj.data;
        if (obj.event === 'license') {
            layer.confirm('确认批准其开发此科研项目吗？', function () {
                $.ajax({
                    type: 'post',
                    url: '/project/license',
                    dataType: 'json',
                    data: { id: data.id },
                    success: function (res) {
                        if (res.code === 200) {
                            layer.alert("批准成功!", function (index) {
                                window.location.reload();
                            });
                        }
                    }
                });
            });
        }
        else if (obj.event === 'no-license'){
            layer.confirm('确认不批准吗？', function () {
                $.ajax({
                    type: 'post',
                    url: '/project/no-license',
                    dataType: 'json',
                    data: { id: data.id },
                    success: function (res) {
                        if (res.code === 200) {
                            layer.alert("操作成功!", function (index) {
                                window.location.reload();
                            });
                        }
                    }
                });
            });
        }
        else if (obj.event === 'result'){
            layer.confirm('确认下载该项目的成果吗？', function () {
                window.open(data.resultPath);
                window.location.reload();
            });
        }
        else if (obj.event === 'pass'){
            layer.confirm('确认审批其通过吗？', function () {
                $.ajax({
                    type: 'post',
                    url: '/project/pass',
                    dataType: 'json',
                    data: { projectId: data.id, adminId: sessionStorage.id },
                    success: function (res) {
                        if (res.code === 200) {
                            layer.alert("审批通过成功!", function (index) {
                                window.location.reload();
                            });
                        }else{
                            layer.alert("您已经审批过该项目了，请勿重复操作!", function (index) {
                                window.location.reload();
                            });
                        }
                    }
                });
            });
        }
        else if (obj.event === 'no-pass'){
            layer.confirm('确认审批其不通过吗？', function () {
                $.ajax({
                    type: 'post',
                    url: '/project/no-pass',
                    dataType: 'json',
                    data: { projectId: data.id, adminId: sessionStorage.id },
                    success: function (res) {
                        if (res.code === 200) {
                            layer.alert("操作成功!", function (index) {
                                window.location.reload();
                            });
                        }else{
                            layer.alert("您已经审批过该项目了，请勿重复操作!", function (index) {
                                window.location.reload();
                            });
                        }
                    }
                });
            });
        }
        else if (obj.event === 'prize'){
            layer.prompt({
                formType: 0,
                title: '请填写奖项名',
                area: ['800px', '350px'] //自定义文本域宽高
            }, function (value, index, elem) {
                $.ajax({
                    type: 'post',
                    url: '/prize/add',
                    dataType: 'json',
                    data: { projectId: data.id, name: value },
                    success: function (res) {
                        if (res.code === 200) {
                            layer.alert("颁奖成功!", function success() {
                                window.location.reload();
                            });
                        }
                        else {
                            layer.alert("您已为该项目颁发过奖项，请勿重复操作!");
                        }
                    }
                });
            });
        }
        else if (obj.event === 'patent'){
            layer.confirm('确认为该项目申请专利号吗？', function () {
                $.ajax({
                    type: 'post',
                    url: '/patent/add',
                    dataType: 'json',
                    data: { projectId: data.id },
                    success: function (res) {
                        if (res.code === 200) {
                            layer.alert("申请成功!", function (index) {
                                window.location.reload();
                            });
                        }
                        else {
                            layer.alert("您已为该项目申请过专利，请勿重复操作!");
                        }
                    }
                });
            });
        }
        else if (obj.event === 'del') {
            layer.confirm('确认删除该项目？该操作将导致该项目所含奖项、专利及论文一并删除！', function () {
                $.ajax({
                    type: 'delete',
                    url: '/project/delete',
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
        table.reload('table_project', {
            where: { name: $('#input').val() }
        });
    });
});