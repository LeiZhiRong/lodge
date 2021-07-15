/*<![CDATA[*/
$(function () {
    $("#manage_point_grid").datagrid({
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
function addRow() {
    altDialog(null);
}

//编辑按钮Click事件
function editRow(id) {
     if(empty(id)){
        LG.alert("error", "id-参数错误");
        return false;
    }else {
        altDialog(id);
    }
}

//Dialog
function altDialog(id) {
    $("body").append("<div id='dialog_Content' Style='display: none;'></div>");
    let title = empty(id) ? "新增【管理处信息】" : "修改【管理处信息】";
    $('#dialog_Content').dialog({
        title: title,
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 240,
        width: 400,
        top:'10%',
        left:'35%',
        inline:true,
        border:'thin',
        cls:'c6',
        constrain: true,
        href: 'dialog',
        queryParams: {
            id: id
        },
        onClose: function () {
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            iconCls: 'e-icon1 fa fa-floppy-o red',
            text: '保存',
            handler: function () {
                if (!$('#manage_point_form').form('enableValidation').form('validate'))
                    return false;
                let actiform = $("#manage_point_form");
                LG.ajaxSubmitForm(actiform, function (data) {
                    if (data.code === 1) {
                        $('#manage_point_grid').datagrid('reload');
                        $('#dialog_Content').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'icon1 fa fa-reply red',
            text: '取消',
            handler: function () {
                $('#dialog_Content').dialog('close');
            }
        }]
    });
}

//删除
function deleteRow(id) {
     if(empty(id)){
        LG.alert("error", "id-参数错误");
        return false;
    }
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
                        if (data.code === 1) {
                            $('#manage_point_grid').datagrid('reload');
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
    let action = $('#manage_point_grid');
    action.datagrid('options').queryParams = {
        value: value
    };
    action.datagrid('reload');
}
/*]]>*/