/*<![CDATA[*/
$(function () {
    //初始化往来客户表
    $("#corpGrid").datagrid({
        loadMsg: false,
        url: 'list',
        singleSelect: true,
        checkOnSelect: false,
        selectOnCheck: false,
        collapsible: true,
        fit: true,
        striped: true,
        maximized: true,
        rownumbers: true,
        sortName: 'corpBM',
        sortOrder: 'asc',
        border: true,
        pagination: true,
        pageSize: 20,
        onLoadSuccess: function () {
            $('#corpGrid').datagrid('doCellTip', {
                onlyShowInterrupt: true,
                position: 'bottom',
                maxWidth: '450px',
                // tipStyler: {
                //     backgroundColor: '#FFFFCC',
                //     color: '#333',
                //     boxShadow: '1px 1px 3px #eee',
                //     wordBreak: 'break-all'
                // }
            });
            getChecked();
        },
        onCheck: function (index, row) {
            getChecked();
        },
        onUncheck: function (index, row) {
            getChecked();
        },
        onCheckAll: function (rows) {
            getChecked();
        },
        onUncheckAll: function (row) {
            getChecked();
        },
        toolbar: '#Ptools',
        footer: '#buttons',
    });
    $('#pcode').textbox().textbox('addClearBtn', 'icon-clear');
})

//添加
function addCorp() {
    altCorpDialog(null);
}

//编辑
function editRow(id) {
    altCorpDialog(id);
}

//查询
function _searchCorpInfo(value) {
    var queryparams = {
        keyword: value,
        corpType: $("#corpCode").combobox("getValue")
    };
    $('#corpGrid').datagrid('options').queryParams = queryparams;
    $('#corpGrid').datagrid('reload');

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
                    url: 'deleteCorpInfo',
                    data: {
                        id: id
                    },
                    success: function (data) {
                        if (data.code == 1) {
                            $('#corpGrid').datagrid('reload');
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

//导出
function exportDown() {
    $.messager.confirm({
        msg: '是否将客商信息导出为Excel！',
        title: '提示',
        top: '10%',
        fn: function (r) {
            if (r) {
                var corpType=$("#corpCode").combobox("getValue");
                var keyword = $("#pcode").searchbox('getValue');
                window.location.href = "exportDown?keyword=" + keyword+"&corpType="+corpType;
            }
        }
    });

}

//Dialog
function altCorpDialog(id) {
    var title = empty(id) ? "增加【客商信息】" : "编辑【客商信息】";
    $("body").append("<div id='corpContent' Style='display: none;padding: 5px;'></div>");
    $('#corpContent').dialog({
        title: title,
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 500,
        width: 760,
        top: '5%',
        left: '20%',
        inline: true,
        border: 'thin',
        cls: 'c8',
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
                if (!$("#corpForm").form('enableValidation').form('validate'))
                    return false;
                var rows = $('#corpBankGrid').datagrid('getRows');
                var _corpId = $("#id").val();
                if (rows.length > 0 && empty(_corpId)) {
                    $("#bankAccount").val(base64_encode(JSON.stringify(rows)));
                } else {
                    $("#bankAccount").val("");
                }
                var actiform = $("#corpForm");
                LG.ajaxSubmitForm(actiform, function (data) {
                    if (data.code == 1) {
                        $('#corpGrid').datagrid('reload');
                        $('#corpContent').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }
                })
            }
        }, {
            iconCls: 'icon1 fa fa-reply blue',
            text: '取消',
            handler: function () {
                $('#corpContent').dialog('close');
            }
        }]
    });
}

//grid check
var data_ids;

function getChecked() {
    var ss = [];
    var rows = $('#corpGrid').datagrid('getChecked');
    if (rows.length > 0) {
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            ss.push(row.id);
        }
        $('#batchDeleteBtn').linkbutton('enable');
        data_ids = ss.join(',');
    } else {
        $('#batchDeleteBtn').linkbutton('disable');
        data_ids = null;
    }
}

//批量删除
function batchdeleteCorp() {
    if (empty(data_ids)) {
        LG.alert("error", "请选择客商信息，然后重试！");
        return false;
    }
    $.messager.confirm({
        msg: '是否删除所选记录！',
        title: '提示',
        top: '10%',
        fn: function (r) {
            if (r) {
                LG.ajax({
                    url: 'batchDeleteCorp',
                    data: {
                        ids: data_ids
                    },
                    success: function (data) {
                        if (data.code == 1) {
                            $('#corpGrid').datagrid('reload');
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
        inline: true,
        border: true,
        cls: 'c4',
        constrain: true,
        href: basepath + '/table/getColumns',
        queryParams: {
            action: action
        },
        onClose: function () {
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            iconCls: 'fa fa-check blue',
            text: '应用',
            handler: function () {
                getHtmlChanges(true);
            }
        }, {
            iconCls: 'fa fa-repeat blue',
            text: '重置',
            handler: function () {
                restHeaderSetup();
            }
        }, {
            iconCls: 'fa fa-reply blue',
            text: '关闭',
            handler: function () {
                $('#headerContent').dialog('close');
            }
        }]
    });
}

//模板导入
function importUpload() {
    $("body").append("<div id='importContent' Style='display: none;'></div>");
    $('#importContent').dialog({
        title: '客商信息导入',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: '90%',
        width: '95%',
        top: '2%',
        left: '2%',
        cls: 'c4',
        inline: true,
        border: true,
        constrain: true,
        href: 'importUpload',
        onClose: function () {
            $('#corpGrid').datagrid('reload');
            $(this).dialog("destroy").remove();
        },
    });
}

/*]]>*/