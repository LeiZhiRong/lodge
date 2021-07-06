/*<![CDATA[*/
$(function () {
    let eu = $("#dg").datagrid({
        url: 'list',
        border: true,
        sortName: 'orders',
        sortOrder: 'asc',
        pagination: true,
        singleSelect: true,
        rownumbers: true,
        pageSize: 20,
        striped: true,
        header: '#top-toolbar',
        fit: true,
        columns: [[
            {field: 'deptID', title: '部门编号', width: 100},
            {field: 'deptName', title: '部门名称', width: 250},
            {field: 'parent_name', title: '所属部门', width: 250},
            {field: 'status2str', title: '状态', width: 80, align: 'center'},
            {
                field: 'action',
                title: '操作',
                width: 125,
                align: 'center',
                formatter: function (value, row) {
                    var a = '<a href="javascript:void(0)" class="easyui-linkbutton " data-options="plain:false" onclick="mergeRow(&quot;' + row.id + '&quot;)"><i class="fa fa-edit fa-lg"></i>编辑</a>&nbsp;&nbsp;';
                    var b = '<span class="datagrid-btn-separator"  style="vertical-align:middle;display:inline-block;float:none;"></span>';
                    var c = '&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton " data-options="plain:false" onclick="deleteRow(&quot;' + row.id + '&quot;)"><i class="fa fa-trash fa-lg"></i>删除</a>';
                    return a + b + c;
                }
            }
        ]],
        onLoadSuccess: function () {
            $(this).datagrid('enableDnd');
        },
        onDrop: function (dRow, sRow, point) {
            enableDnd(dRow, sRow, point);
        }
    }).datagrid('columnMoving');

    $('#searchbox').textbox().textbox('addClearBtn', 'icon-clear');

})

function deleteRow(id) {
    $.messager.confirm({
        msg: '是否删除该条记录！',
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
                            if (data.data != null) {
                                findDeptInfo(data.data);
                            } else {
                                $('#dg').datagrid('reload');
                            }
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

var data_pid = null;

function mergeRow(id) {
    var Parent = $('#content').parent();
    var Own = $('#content').clone();
    Own.hide();
    $('#content').dialog({
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: "努力加载中...",
        title: '部门信息',
        modal: true,
        height: 300,
        width: 450,
        top:'10%',
        left:'30%',
        inline:true,
        border:'thin',
        cls:'c8',
        constrain: true,
        href: 'dialog',
        queryParams: {
            id: id,
            pid: data_pid
        },
        onClose: function () {
            Own.appendTo(Parent);
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            text: '保存',
            iconCls: 'e-icon2 fa fa-floppy-o red',
            handler: function () {
                if (!$("#mergeFrom").form('enableValidation').form('validate'))
                    return false;
                var mergeFrom = $("#mergeFrom");
                LG.ajaxSubmitForm(mergeFrom, function (data) {
                    if (data.code == 1) {
                        if (data.data != null) {
                            findDeptInfo(data.data);
                        } else {
                            $('#dg').datagrid('reload');
                        }
                        $('#content').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'e-icon2 fa fa-reply red',
            text: '取消',
            handler: function () {
                $('#content').dialog('close');
            }
        }]
    })
}

// 模块目录跳转
function findDeptInfo(pid) {
    data_pid = pid;
    var queryparams = {
        pid: pid
    };
    $('#dg').datagrid('options').queryParams = queryparams;
    $('#dg').datagrid('reload');
}

// 拖动排序
function enableDnd(dRow, sRow, point) {
    var rows = $('#dg').datagrid('getRows');
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
            $('#dg').datagrid('reload');
        },
        error: function (message) {
            LG.tip("error", message);
            return false;
        }
    });
}

function f_search(value) {
    var queryparams = {
        value: value
    };
    $('#dg').datagrid('options').queryParams = queryparams;
    $('#dg').datagrid('reload');
}

/*]]>*/