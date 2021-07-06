/*<![CDATA[*/
$(function () {
    $("#periodDG").datagrid({
        url: 'list',
        border: true,
        sortName: 'month',
        sortOrder: 'asc',
        pagination: true,
        singleSelect: true,
        pageSize: 20,
        striped: true,
        header: '#top-toolbar',
        fit: true       
        
    })
})

function deleteRow(id) {
    $.messager.confirm({
        msg: '是否删除该条记录！',
        title: '提示',
        top: '10%',
        left:'35%',
        fn: function (r) {
            if (r) {
                LG.ajax({
                    url: 'delete',
                    data: {
                        id: id
                    },
                    success: function (data) {
                        if (data.code == 1) {
                            $('#periodDG').datagrid('reload');
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

function mergeRow(id) {
    var title = empty(id) ? "新增【会计期间】" : "编辑【会计期间】";
    var Parent = $('#period_content').parent();
    var Own = $('#period_content').clone();
    Own.hide();
    $('#period_content').dialog({
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: "努力加载中...",
        title: title,
        modal: true,
        height: 300,
        width: 450,
        top: '10%',
        left: '30%',
        inline: true,
        border: 'thin',
        cls: 'c8',
        constrain: true,
        href: 'dialog',
        queryParams: {
            id: id
        },
        onClose: function () {
            Own.appendTo(Parent);
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            text: '保存',
            iconCls: 'e-icon2 fa fa-floppy-o blue',
            handler: function () {
                if (!$("#periodFrom").form('enableValidation').form('validate'))
                    return false;
                var mergeFrom = $("#periodFrom");
                LG.ajaxSubmitForm(mergeFrom, function (data) {
                    if (data.code == 1) {
                        $('#periodDG').datagrid('reload');
                        $('#period_content').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'e-icon2 fa fa-reply blue',
            text: '取消',
            handler: function () {
                $('#period_content').dialog('close');
            }
        }]
    })
}
//编辑
function editRow(id) {
    mergeRow(id);
}
/*]]>*/