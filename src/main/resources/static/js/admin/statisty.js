//echarts饼图，需要注意先让DOM元素加载出来
$(document).ready(function () {
    var chart = echarts.init($('#echarts_pie').get(0));
    var data1 = [];
    var data2 = [];

    var option = {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            left: 10,
            data: data1
        },
        series: [
            {
                name: '项目各状态数量',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '30',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: data2
            }
        ]
    };
    $.get("/statisty/getEchartsPie", null,
        function (res) {
            if (res.code === 200) {
                console.log(res);
                for (let i = 0; i < 6; i++) {
                    data1.push(res.data[i].name)
                    data2.push(res.data[i]);
                }
                chart.setOption(option);
            }
        },
        "json"
    );
});