/*<![CDATA[*/
$(function () {
    $("#tlist").datalist({
        loadMsg: false,
        url: "list",
        toolbar: '#Ttools',
        footer: '#Ftools',
        lines: false,
        border: false,
        fit: true,
        textField: 'roleName',
        valueField: 'id',
        onClickRow: function (index, row) {
            $("#dl1").datalist({url: 'getMenuInfo', queryParams: {role_id: row.id}});
            $("#dl2").datalist({url: 'getPermInfo', queryParams: {role_id: row.id}});
        },
        onLoadSuccess: function () {
            $('#editRole').linkbutton('disable');
            $('#delRole').linkbutton('disable');
            $('#wcode').textbox("setValue","");
            $('#ycode').textbox("setValue","");
            $('#dl1').datalist('loadData', {rows: []});
            $('#dl2').datalist('loadData', {rows: []});
        },
        onSelect: function () {
            $('#editRole').linkbutton('enable');
            $('#delRole').linkbutton('enable');
        },
    });
    $('#tcode').textbox().textbox('addClearBtn', 'icon-clear');
    $('#wcode').textbox().textbox('addClearBtn', 'icon-clear');
    $('#ycode').textbox().textbox('addClearBtn', 'icon-clear');
    $('#dl1').datalist({
        loadMsg: false,
        header: '#wfqx',
        method: 'post',
        valueField: 'id',
        textField: 'text',
        groupField: 'group',
        lines: false,
        checkbox: true,
        singleSelect: false
    });
    $('#dl2').datalist({
        loadMsg: false,
        header: '#yfqx',
        method: 'post',
        valueField: 'id',
        textField: 'text',
        groupField: 'group',
        lines: false,
        checkbox: true,
        singleSelect: false
    });
    //移动
    $('#dl_add').bind('click', function () {
        var role_row = $("#tlist").datalist("getSelected");
        var rows = $("#dl1").datalist("getSelections");
        var ids = $(rows).map(function () {
            return this.id;
        }).get().join(",");
        if (empty(ids) || empty(role_row))
            return false;
        LG.ajax({
            url: 'savaPermInfo',
            data: {
                menu_ids: ids,
                role_id: role_row.id
            },
            success: function (data) {
                $('#dl1').datalist('reload');
                $('#dl2').datalist('reload');
            },
            error: function (message) {
                LG.alert("error", message);
            }
        });
    });
    //移动所有
    $('#dl_add_all').bind('click', function () {
        var role_row = $("#tlist").datalist("getSelected");
        var data = $("#dl1").datalist("getData");
        var rows = data.rows;
        var ids = $(rows).map(function () {
            return this.id;
        }).get().join(",");
        if (empty(ids) || empty(role_row))
            return false;
        LG.ajax({
            url: 'savaPermInfo',
            data: {
                menu_ids: ids,
                role_id: role_row.id
            },
            success: function (data) {
                $('#dl1').datalist('reload');
                $('#dl2').datalist('reload');
            },
            error: function (message) {
                LG.alert("error", message);
            }
        });

    });
    //移除
    $('#dl_remove').bind('click', function () {
        var role_row = $("#tlist").datalist("getSelected");
        var rows = $("#dl2").datalist("getSelections");
        var ids = $(rows).map(function () {
            return this.id;
        }).get().join(",");
        if (empty(ids) || empty(role_row))
            return false;
        LG.ajax({
            url: 'delPermInfo',
            data: {
                menu_ids: ids,
                role_id: role_row.id
            },
            success: function (data) {
                $('#dl1').datalist('reload');
                $('#dl2').datalist('reload');
            },
            error: function (message) {
                LG.alert("error", message);
            }
        });

    });
    //移除所有
    $('#dl_remove_all').bind('click', function () {
        var role_row = $("#tlist").datalist("getSelected");
        var data = $("#dl2").datalist("getData");
        var rows = data.rows;
        var ids = $(rows).map(function () {
            return this.id;
        }).get().join(",");
        if (empty(ids) || empty(role_row))
            return false;
        LG.ajax({
            url: 'delPermInfo',
            data: {
                menu_ids: ids,
                role_id: role_row.id
            },
            success: function (data) {
                $('#dl1').datalist('reload');
                $('#dl2').datalist('reload');
            },
            error: function (message) {
                LG.alert("error", message);
            }
        });
    });

})

function _searchRole(value) {
    $("#tlist").datalist({queryParams: {keyword: value}});
}

function _searchMenu(value) {
    var row = $('#tlist').datalist('getSelected');
    if (empty(row))
        return false;
    $("#dl1").datalist({
        queryParams: {
            keyword: value,
            role_id: row.id
        }
    });
}

function _searchPerm(value) {
    var row = $('#tlist').datalist('getSelected');
    if (empty(row))
        return false;
    $("#dl2").datalist({
        queryParams: {
            keyword: value,
            role_id: row.id
        }
    });
}

function alteration(id) {
    var dialogParent = $('#roleContent').parent();
    var dialogOwn = $('#roleContent').clone();
    dialogOwn.hide();
    $('#roleContent').dialog({
        title: '角色信息',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 250,
        width: 380,
        top:'10%',
        left:'30%',
        border:'thin',
        cls:'c8',
        constrain: true,
        href: 'roleDialog',
        queryParams: {
            id: id
        },
        onClose: function () {
            dialogOwn.appendTo(dialogParent);
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
                        $('#tlist').datalist('reload');
                        $('#roleContent').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'e-icon2 fa fa-reply red',
            text: '取消',
            handler: function () {
                $('#roleContent').dialog('close');
            }
        }]
    });
}

function editRole() {
    var row = $('#tlist').datalist('getSelected');
    if (row != null) {
        alteration(row.id);
    } else {
        LG.alert("info", "请选择角色，然后继续操作！");
    }
}

function addRole() {
    alteration(null);
};

function delRole() {
    var row = $('#tlist').datalist('getSelected');
    if (row != null) {
        $.messager.confirm({
            msg: "是否删除所选角色！",
            title: "提示",
            top: '10%',
            fn: function (r) {
                if (r) {
                    LG.ajax({
                        url: 'deleRole',
                        data: {
                            id: row.id
                        },
                        success: function (data) {
                            $('#tlist').datalist('reload');
                        },
                        error: function (message) {
                            LG.tip("error", message);
                        }
                    });
                }
            }
        });
    } else {
        LG.alert("info", "请选择角色，然后重试！");
    }
}

/*]]>*/