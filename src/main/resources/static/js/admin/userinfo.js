/*<![CDATA[*/
$(function () {
    //初始化用户表
    $("#userGrid").datagrid({
        loadMsg: true,
        url: 'getUserInfo',
        singleSelect: true,
        checkOnSelect: false,
        selectOnCheck: false,
        collapsible: true,
        fit: true,
        maximized: true,
        striped:true,
        rownumbers: true,
        sortName: 'loginAccount',
        sortOrder: 'asc',
        border: true,
        pagination: true,
        pageSize: 20,
        onLoadSuccess: function () {
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
        toolbar: '#Ptools'
    });
    $('#tcode').textbox().textbox('addClearBtn', 'icon-clear');
    $('#pcode').textbox().textbox('addClearBtn', 'icon-clear');
})

//用户信息查询click事件
function _searchUserInfo(value) {
    $("#userGrid").datagrid({queryParams: {keyword: value}});
}

// 添加用户信息按钮Click事件
function addUser() {
    altUserDialog(null);
};

//编辑用户信息按钮Click事件
function editRow(id) {
    altUserDialog(id);
}

//批量删除
function batchdeleteUser() {
    if (empty(data_ids)) {
        LG.alert("error", "请选择用户信息，然后重试！");
        return false;
    }
    $.messager.confirm({
        msg: '是否删除所选记录！',
        title: '提示',
        top: '10%',
        fn: function (r) {
            if (r) {
                LG.ajax({
                    url: 'batchDeleteUser',
                    data: {
                        ids: data_ids
                    },
                    success: function (data) {
                        if (data.code == 1) {
                            $('#userGrid').datagrid('reload');
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

//删除用户信息
function deleteRow(id) {
    $.messager.confirm({
        msg: '是否删除所选记录！',
        title: '提示',
        top: '10%',
        fn: function (r) {
            if (r) {
                LG.ajax({
                    url: 'deleteUser',
                    data: {
                        id: id
                    },
                    success: function (data) {
                        if (data.code == 1) {
                            $('#userGrid').datagrid('reload');
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

//模板导入
function importUser() {
    $("body").append("<div id='importContent' Style='display: none;'></div>");
    $('#importContent').dialog({
        title: '用户信息导入',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: '80%',
        width: '85%',
        top:'5%',
        left:'5%',
        inline: true,
        border: true,
        cls: 'c4',
        constrain: true,
        href: 'importUser',
        onClose: function () {
            $('#userGrid').datagrid('reload');
            $(this).dialog("destroy").remove();
        },
    });
}

//Dialog
function altUserDialog(id) {
    $("body").append("<div id='userContent' Style='display: none;'></div>");
    $('#userContent').dialog({
        title: '用户信息',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 420,
        width: 580,
        top: '10%',
        left: '25%',
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
                if (!$("#userForm").form('enableValidation').form('validate'))
                    return false;
                var actiform = $("#userForm");
                LG.ajaxSubmitForm(actiform, function (data) {
                    if (data.code == 1) {
                        $('#userGrid').datagrid('reload');
                        $('#userContent').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'icon1 fa fa-reply blue',
            text: '取消',
            handler: function () {
                $('#userContent').dialog('close');
            }
        }]
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
        top:'10%',
        left:'25%',
        inline: true,
        border: true,
        cls: 'c4',
        constrain: true,
        href: basepath+'/table/getColumns',
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

//grid check
var data_ids;

function getChecked() {
    var ss = [];
    var rows = $('#userGrid').datagrid('getChecked');
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
/*]]>*/