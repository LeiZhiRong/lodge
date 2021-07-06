/*<![CDATA[*/
$(function () {
    // 初始化参数类型
    $("#tlist").tree({
        loadMsg: false,
        url: "listProceedType",
        lines: false,
        border: false,
        fit: true,
        onClick: function (node) {
            $('#addReveExpeItemBtn').linkbutton('enable');
            $('#pcode').textbox("setValue", "");
            $("#reveExpeItemGrid").datagrid({url: 'findReveExpeItem', queryParams: {proceedTypeId: node.id}});
        },
        onLoadSuccess: function () {
            $('#addReveExpeItemBtn').linkbutton('disable');
            $('#editProceedType').linkbutton('disable');
            $('#delProceedType').linkbutton('disable');
            $('#reveExpeItemGrid').datagrid('loadData', {total: 0, rows: []});
        },
        onSelect: function (node) {
            if (is_eq("all", node.id)) {
                $('#editProceedType').linkbutton('disable');
                $('#delProceedType').linkbutton('disable');
            } else {
                $('#editProceedType').linkbutton('enable');
                $('#delProceedType').linkbutton('enable');
            }
        },
    });
    $('#tcode').textbox().textbox('addClearBtn', 'icon-clear');
    $('#pcode').textbox().textbox('addClearBtn', 'icon-clear');

})

//收支费项编辑按钮Click事件
function editProceedType() {
    var row = $('#tlist').tree('getSelected');
    if (row != null) {
        proceedTypeDialog(row.id,row.pid);
    } else {
        LG.alert("info", "请选择费项，然后继续操作！");
    }
}

// 收支费项添加按钮Click事件
function addProceedType() {
    proceedTypeDialog(null,null);
}

// 收支费项删除按钮Click事件
function delProceedType() {
    var row = $('#tlist').tree('getSelected');
    if (row != null) {
        $.messager.confirm({
            msg: "是否删除所选费项？",
            title: "提示",
            top: '10%',
            fn: function (r) {
                if (r) {
                    LG.ajax({
                        url: 'deleteProceedType',
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
        LG.alert("info", "请选择费项，然后重试！");
    }
};

//收支费项查询处理
function _searchProceedType(value) {
    $("#tlist").tree("search", value);
}

//收支费项Dialog
function proceedTypeDialog(id,pid) {
    var title = empty(id) ? "新增【费项】" : "编辑【费项】";
    var dialogTypeParent = $('#proceedTypeContent').parent();
    var dialogTypeOwn = $('#proceedTypeContent').clone();
    dialogTypeOwn.hide();
    $('#proceedTypeContent').dialog({
        title: title,
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 300,
        width: 450,
        top: '5%',
        left: '30%',
        border: 'thin',
        cls: 'c8',
        constrain: true,
        href: 'proceedTypeDialog',
        queryParams: {
            id: id,
            pid:pid
        },
        onClose: function () {
            dialogTypeOwn.appendTo(dialogTypeParent);
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            iconCls: 'e-icon2 fa fa-floppy-o blue',
            text: '保存',
            handler: function () {
                if (!$("#proceedTypeForm").form('enableValidation').form('validate'))
                    return false;
                var actiform = $("#proceedTypeForm");
                LG.ajaxSubmitForm(actiform, function (data) {
                    if (data.code == 1) {
                        $('#tlist').tree('reload');
                        $('#proceedTypeContent').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'e-icon2 fa fa-reply blue',
            text: '取消',
            handler: function () {
                $('#proceedTypeContent').dialog('close');
            }
        }]
    });
}

$(function () {
    $("#reveExpeItemGrid").datagrid({
        loadMsg: false,
        singleSelect: true,
        collapsible: true,
        fit: true,
        maximized: true,
        rownumbers: true,
        border: false,
        onLoadSuccess: function () {
            $('#editCusParamBtn').linkbutton('disable');
            $('#deleteCusParamBtn').linkbutton('disable');
        },
        onSelect: function () {
            $('#editCusParamBtn').linkbutton('enable');
            $('#deleteCusParamBtn').linkbutton('enable');
        },
        toolbar: '#Ptools'
    })
})

// 添加入账规则按钮Click事件
function addReveExpeItem() {
    altReveExpeItemDialog(null,"add");
};

//编辑入账规则按钮Click事件
function editRow(id) {
    altReveExpeItemDialog(id,"edit");
}

function copyRow(id) {
    altReveExpeItemDialog(id,"copy");
}

//Dialog
function altReveExpeItemDialog(id,action) {
    var title = is_eq("edit",action) ? "编辑【入账规则】" : "新增【入账规则】";
    var row = $('#tlist').tree('getSelected');
    var proceedTypeId = row.id;
    $("body").append("<div id='reveExpeItemContent' Style='display: none;padding: 10px;'></div>");
    $('#reveExpeItemContent').dialog({
        title: title,
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: '85%',
        width: 800,
        top: '5%',
        inline: true,
        border: 'thin',
        cls: 'c8',
        constrain: true,
        href: 'reveExpeItemDialog',
        queryParams: {
            id: id,
            proceedTypeId: proceedTypeId,
            action:action
        },
        onClose: function () {
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            iconCls: 'e-icon1 fa fa-floppy-o blue',
            text: '保存',
            handler: function () {
                if (!$("#reveExpeItemForm").form('enableValidation').form('validate'))
                    return false;
                var actiform = $("#reveExpeItemForm");
                LG.ajaxSubmitForm(actiform, function (data) {
                    if (data.code == 1) {
                        $('#reveExpeItemGrid').datagrid('reload');
                        $('#reveExpeItemContent').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'icon1 fa fa-reply blue',
            text: '取消',
            handler: function () {
                $('#reveExpeItemContent').dialog('close');
            }
        }]
    });
}

///列调整
function headerSetup(action) {
    $("body").append("<div id='headerContent' Style='display: none;'></div>");
    $('#headerContent').dialog({
        title: '列配置',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 400,
        width: 600,
        top: '5%',
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
                getHtmlChanges(false);
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

//删除入账规则
function deleteRow(id) {
    $.messager.confirm({
        msg: '是否删除该条记录！',
        title: '提示',
        top: '10%',
        fn: function (r) {
            if (r) {
                LG.ajax({
                    url: 'deleteReveExpeItem',
                    data: {
                        id: id
                    },
                    success: function (data) {
                        if (data.code == 1) {
                            $('#reveExpeItemGrid').datagrid('reload');
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

/*]]>*/