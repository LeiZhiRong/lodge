/*<![CDATA[*/
$(function () {
    $("#bookSetGrid").datagrid({
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
function addAccoun() {
    altAccounDialog(null);
};

//编辑按钮Click事件
function editRow(id) {
    altAccounDialog(id);
}

//Dialog
function altAccounDialog(id) {
    $("body").append("<div id='accounContent' Style='display: none;'></div>");
    $('#accounContent').dialog({
        title: '账套信息',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 280,
        width: 450,
        top:'10%',
        left:'30%',
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
                if (!$("#bookSetForm").form('enableValidation').form('validate'))
                    return false;
                var actiform = $("#bookSetForm");
                LG.ajaxSubmitForm(actiform, function (data) {
                    if (data.code == 1) {
                        $('#bookSetGrid').datagrid('reload');
                        $('#accounContent').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'icon1 fa fa-reply red',
            text: '取消',
            handler: function () {
                $('#accounContent').dialog('close');
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
                            $('#bookSetGrid').datagrid('reload');
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
//列调整
function headerSetup(action) {
    $("body").append("<div id='headerContent' Style='display: none;'></div>");
    $('#headerContent').dialog({
        title: '列配置',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 400,
        width: 600,
        top: '10%',
        left: '20%',
        border: true,
        constrain: true,
        href: basepath + '/table/getColumns',
        queryParams: {
            action: action
        },
        onClose: function () {
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            iconCls: 'fa fa-check red',
            text: '应用',
            handler: function () {
                getHtmlChanges(false);
            }
        }, {
            iconCls: 'fa fa-reply red',
            text: '关闭',
            handler: function () {
                $('#headerContent').dialog('close');
            }
        }]
    });
}
function f_search(value) {
    var queryparams = {
        value: value
    };
    $('#bookSetGrid').datagrid('options').queryParams = queryparams;
    $('#bookSetGrid').datagrid('reload');
}
/*]]>*/