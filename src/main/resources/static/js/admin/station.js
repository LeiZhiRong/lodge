/*<![CDATA[*/
$(function () {
    $("#stationGrid").datagrid({
        loadMsg: true,
        url: 'list',
        singleSelect: true,
        collapsible: true,
        fit: true,
        maximized: true,
        striped: true,
        rownumbers: true,
        sortName: 'rVTime',
        sortOrder: 'asc',
        border: true,
        pagination: true,
        pageSize: 20,
        toolbar: '#Ptools'
    });
})
// 添加按钮Click事件
function addStation() {
    altStationDialog(null);
};

//编辑按钮Click事件
function editRow(id) {
    altStationDialog(id);
}

//Dialog
function altStationDialog(id) {
    var title=empty(id)?"新增【岗位信息】":"编辑【岗位信息】";
    $("body").append("<div id='StationContent' Style='display: none;'></div>");
    $('#StationContent').dialog({
        title: title,
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 200,
        width: 450,
        top:'10%',
        left:'30%',
        inline:true,
        border:'thin',
        cls:'c8',
        constrain: true,
        href: 'dialog',
        queryParams: {
            id: id
        },
        onClose: function () {
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            iconCls: 'e-icon1 fa fa-floppy-o blue',
            text: '保存',
            handler: function () {
                if (!$("#stationForm").form('enableValidation').form('validate'))
                    return false;
                var actiform = $("#stationForm");
                LG.ajaxSubmitForm(actiform, function (data) {
                    if (data.code == 1) {
                        $('#stationGrid').datagrid('reload');
                        $('#StationContent').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'icon1 fa fa-reply blue',
            text: '取消',
            handler: function () {
                $('#StationContent').dialog('close');
            }
        }]
    });
}

//删除
function deleteRow(id) {
    $.messager.confirm({
        msg: '是否删除所选记录！',
        title: '提示',
        top: '10%',
        fn: function (r) {
            if (r) {
                LG.ajax({
                    url: 'delete',
                    data: {
                        id: id
                    },
                    success: function (data) {
                        if (data.code == 1) {
                            $('#stationGrid').datagrid('reload');
                        } else {
                            LG.alert("error", data.message);
                        }
                    },
                    error: function (message) {
                        LG.tip('error', message);
                    }
                });
            }
        }
    });
}

function f_search(value) {
    var queryparams = {
        value: value
    };
    $('#stationGrid').datagrid('options').queryParams = queryparams;
    $('#stationGrid').datagrid('reload');
}
/*]]>*/