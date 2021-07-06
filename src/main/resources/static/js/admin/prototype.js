/*<![CDATA[*/
$(function () {
    // 初始化参数类型
    $("#tlist").datalist({
        loadMsg: false,
        url: "listProjectsType",
        lines: false,
        border: false,
        fit: true,
        textField: 'name',
        valueField: 'id',
        onClickRow: function (index, row) {
            t_id = row.id;
            $('#addProjects').linkbutton('enable');
            $('#searchbox').textbox("setValue", "");
            $("#prototypeDG").datagrid({url: 'list', queryParams: {t_id: t_id}});
        },
        onLoadSuccess: function () {
            $('#addProjects').linkbutton('disable');
            $('#prototypeDG').datagrid('loadData', {total: 0, rows: []});
        }
    });
    let eu = $("#prototypeDG").datagrid({
        border: false,
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
            {field: 'projectsBh', title: '项目编号', width: 150},
            {field: 'projectsName', title: '项目名称', width: 250},
            {field: 'parent_name', title: '上级项目', width: 250},
            {field: 'ztbz', title: '状态', width: 80, align: 'center'},
            {
                field: 'action',
                title: '操作',
                width: 125,
                align: 'center',
                formatter: function (value, row) {
                    var a = '<a href="javascript:void(0)" class="easyui-linkbutton " data-options="plain:false" onclick="mergeRow(&quot;' + row.id + '&quot;)"><i class="fa fa-edit fa-lg"></i>编辑</a>&nbsp;&nbsp;';
                    var c = '<a href="javascript:void(0)" class="easyui-linkbutton " data-options="plain:false" onclick="deleteRow(&quot;' + row.id + '&quot;)"><i class="fa fa-trash fa-lg"></i>删除</a>';
                    return a + c;
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
                                findList(data.data);
                            } else {
                                $('#prototypeDG').datagrid('reload');
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
var t_id = null;

//添加
function addProjects() {
    var typeRow = $("#tlist").datalist("getSelected");
    if (empty(typeRow)) {
        LG.alert("error", "请选择项目分类！");
        return false;
    }
    mergeRow(null);
}

function mergeRow(id) {
    var title = empty(id) ? "新增项目" : "编辑项目";
    var Parent = $('#prototype_content').parent();
    var Own = $('#prototype_content').clone();
    Own.hide();
    $('#prototype_content').dialog({
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: "努力加载中...",
        title: title,
        modal: true,
        height: 260,
        width: 450,
        top: '10%',
        left: '20%',
        inline: true,
        border: 'thin',
        cls: 'c8',
        constrain: true,
        href: 'dialog',
        queryParams: {
            id: id,
            pid: data_pid,
            t_id: t_id
        },
        onClose: function () {
            Own.appendTo(Parent);
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            text: '保存',
            iconCls: 'e-icon2 fa fa-floppy-o red',
            handler: function () {
                if (!$("#prototypeFrom").form('enableValidation').form('validate'))
                    return false;
                var mergeFrom = $("#prototypeFrom");
                LG.ajaxSubmitForm(mergeFrom, function (data) {
                    if (data.code == 1) {
                        if (data.data != null) {
                            findList(data.data);
                        } else {
                            $('#prototypeDG').datagrid('reload');
                        }
                        $('#prototype_content').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'e-icon2 fa fa-reply red',
            text: '取消',
            handler: function () {
                $('#prototype_content').dialog('close');
            }
        }]
    })
}

// 模块目录跳转
function findAncillaryProjects(pid) {
    data_pid = pid;
    var queryparams = {
        pid: pid,
        t_id:t_id
    };
    $('#prototypeDG').datagrid('options').queryParams = queryparams;
    $('#prototypeDG').datagrid('reload');
}

// 拖动排序
function enableDnd(dRow, sRow, point) {
    var rows = $('#prototypeDG').datagrid('getRows');
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
            $('#prototypeDG').datagrid('reload');
        },
        error: function (message) {
            LG.tip("error", message);
            return false;
        }
    });
}

function f_search(value) {
    if (empty(t_id))
        return false;
    var queryparams = {
        value: value,
        t_id: t_id
    };
    $('#prototypeDG').datagrid('options').queryParams = queryparams;
    $('#prototypeDG').datagrid('reload');
}

// 模块目录跳转
function findList(pid) {
    data_pid = pid;
    var queryparams = {
        pid: pid,
        t_id: t_id
    };
    $('#prototypeDG').datagrid('options').queryParams = queryparams;
    $('#prototypeDG').datagrid('reload');
}

/*]]>*/