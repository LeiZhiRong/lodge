/*<![CDATA[*/
$(function () {
    // 初始化参数类型
    $("#tlist").tree({
        loadMsg: false,
        url: "listCustomType",
        lines: false,
        border: false,
        fit: true,
        onClick: function (node) {
            if (empty(node.pid)) {
                $('#addCusParamBtn').linkbutton('disable');
                $('#paramGrid').datagrid('loadData', {total: 0, rows: []});
                return false;
            } else {
                $('#addCusParamBtn').linkbutton('enable');
                $('#pcode').textbox("setValue", "");
                $("#paramGrid").datagrid({url: 'getCusParam', queryParams: {type_id: node.id}});
            }
        },
        onLoadSuccess: function () {
            $('#addCusParamBtn').linkbutton('disable');
            $('#editCusType').linkbutton('disable');
            $('#delCusType').linkbutton('disable');
            $('#paramGrid').datagrid('loadData', {total: 0, rows: []});
        },
        onSelect: function () {
            $('#editCusType').linkbutton('enable');
            $('#delCusType').linkbutton('enable');
        },
    });
    //参数类型查询框增加删除键
    $('#tcode').textbox().textbox('addClearBtn', 'icon-clear');
    $('#pcode').textbox().textbox('addClearBtn', 'icon-clear');

})

//字典类型编辑按钮Click事件
function editCusType() {
    var row = $('#tlist').tree('getSelected');
    if (row != null) {
        cusTypeDialog(row.id);
    } else {
        LG.alert("info", "字典类型，然后继续操作！");
    }
}

// 字典类型添加按钮Click事件
function addCusType() {
    cusTypeDialog(null);
}

// 字典类型添加删除Click事件
function delCusType() {
    var row = $('#tlist').tree('getSelected');
    if (row != null) {
        $.messager.confirm({
            msg: "是否删除所选类型，继续操作将同时删除该类型所有字典信息，是否继续？",
            title: "提示",
            top: '10%',
            fn: function (r) {
                if (r) {
                    LG.ajax({
                        url: 'deleteCusType',
                        data: {
                            id: row.id
                        },
                        success: function (data) {
                            $('#tlist').tree('reload');
                        },
                        error: function (message) {
                            LG.alert("error", message);
                        }
                    });
                }
            }
        });
    } else {
        LG.alert("info", "请选择字典类型，然后重试！");
    }
}

function _searchCusParam(value) {
    var row = $('#tlist').datalist('getSelected');
    if (empty(row))
        return false;
    $("#paramGrid").datagrid({queryParams: {keyword: value, type_id: row.id}});
}

//字典类型Dialog
function cusTypeDialog(id) {
    var dialogTypeParent = $('#cusTypeContent').parent();
    var dialogTypeOwn = $('#cusTypeContent').clone();
    dialogTypeOwn.hide();
    $('#cusTypeContent').dialog({
        title: '自定义类型',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 250,
        width: 380,
        top: '10%',
        left: '30%',
        border: 'thin',
        cls: 'c6',
        constrain: true,
        href: 'cusTypeDialog',
        queryParams: {
            id: id
        },
        onClose: function () {
            dialogTypeOwn.appendTo(dialogTypeParent);
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            iconCls: 'e-icon2 fa fa-floppy-o red',
            text: '保存',
            handler: function () {
                if (!$("#roleForm").form('enableValidation').form('validate'))
                    return false;
                var actiform = $("#roleForm");
                LG.ajaxSubmitForm(actiform, function (data) {
                    if (data.code == 1) {
                        $('#tlist').tree('reload');
                        $('#cusTypeContent').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'e-icon2 fa fa-reply red',
            text: '取消',
            handler: function () {
                $('#cusTypeContent').dialog('close');
            }
        }]
    });
}

$(function () {
    $("#paramGrid").datagrid({
        loadMsg: false,
        singleSelect: true,
        collapsible: true,
        fit: true,
        maximized: true,
        rownumbers: true,
        border: false,
        sortName: 'orders',
        sortOrder: 'asc',
        columns: [[{
            field: 'parameName',
            title: '名称',
            width: 150
        }, {
            field: 'parameCode',
            title: '编码',
            width: 150
        }, {
            field: 'describe',
            title: '描述',
            width: 200
        }, {
            field: 'status',
            title: '状态',
            width: 80,
            formatter: function (value, row) {
                return value == 1 ? "<i class='fa fa-check fa-fw'></i>" : "<i class='fa fa-close fa-fw'></i>";
            }
        }
        ]],
        onLoadSuccess: function () {
            $(this).datagrid('enableDnd');
            $('#editCusParamBtn').linkbutton('disable');
            $('#deleteCusParamBtn').linkbutton('disable');
        },
        onSelect: function () {
            $('#editCusParamBtn').linkbutton('enable');
            $('#deleteCusParamBtn').linkbutton('enable');
        },
        onDrop: function (dRow, sRow, point) {
            enableDnd(dRow, sRow, point);
        },
        toolbar: '#Ptools'
    }).datagrid('columnMoving')
})

// 编辑参数按钮Click事件
function editCusParam() {
    var row = $('#paramGrid').datagrid('getSelected');
    if (row != null) {
        altParamDialog(row.id);
    } else {
        LG.alert("info", "请选择参数，然后继续操作！");
    }
}


// 添加参数按钮Click事件
function addCusParam() {
    altParamDialog(null);
};

//删除参数按钮Click事件
function deleteCusParam() {
    var row = $('#paramGrid').datagrid('getSelected');
    if (row != null) {
        $.messager.confirm({
            msg: "是否删除所选记录！",
            title: "提示",
            top: '10%',
            fn: function (r) {
                if (r) {
                    LG.ajax({
                        url: 'deleteCusParam',
                        data: {
                            id: row.id
                        },
                        success: function (data) {
                            $('#paramGrid').datagrid('reload');
                        },
                        error: function (message) {
                            LG.alert("error", message);
                        }
                    });
                }
            }
        });
    } else {
        LG.alert("info", "请选择参数，然后继续操作！");
    }
}

//增加(编辑)自定义参数
function altParamDialog(id) {
    var row = $('#tlist').tree('getSelected');
    if (empty(row)) {
        LG.alert("info", "请选择类型，然后继续操作！");
    }
    var parameterParent = $('#paramContent').parent();
    var parameterOwn = $('#paramContent').clone();
    parameterOwn.hide();
    $('#paramContent').dialog({
        title: '自定义参数',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 260,
        width: 380,
        top: '10%',
        left: '30%',
        inline: true,
        border: 'thin',
        cls: 'c6',
        constrain: true,
        href: 'cusParamDialog',
        queryParams: {
            id: id,
            type_id: row.id
        },
        onClose: function () {
            parameterOwn.appendTo(parameterParent);
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            iconCls: 'fa fa-floppy-o red',
            text: '保存',
            handler: function () {
                if (!$("#paramForm").form('enableValidation').form('validate'))
                    return false;
                var actiform = $("#paramForm");
                LG.ajaxSubmitForm(actiform, function (data) {
                    if (data.code == 1) {
                        $('#paramGrid').datagrid('reload');
                        $('#paramContent').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'fa fa-reply red',
            text: '取消',
            handler: function () {
                $('#paramContent').dialog('close');
            }
        }]
    });
}

//参数拖动触发事件
function enableDnd(dRow, sRow, point) {
    var rows = $('#paramGrid').datagrid('getRows');
    var ids = $(rows).map(function () {
        return this.id;
    }).get().join(",");
    var data = {
        ids: ids
    };
    LG.ajax({
        url: 'uporders',
        data: data,
        success: function () {
            $('#paramGrid').datagrid('reload');
        },
        error: function (message) {
            LG.tip("error", message);
            return false;
        }
    });
}

function _searchCustomType(value) {
    $("#tlist").tree("search", value);
}

/*]]>*/