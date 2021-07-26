/*<![CDATA[*/
$(function () {
    $("#dg").datagrid({
        url: 'list',
        border: true,
        sortName: 'bookSet',
        sortOrder: 'asc',
        pagination: true,
        singleSelect: true,
        rownumbers: true,
        pageSize: 20,
        striped: true,
        header: '#top-toolbar',
        fit: true,
        columns: [[
            {field: 'name', title: '坐落位置', width: 250},
            {field: 'ztBz', title: '状态', width: 80, align: 'center',formatter: function (value, row) {
                    return is_eq("T",value) ? "<i class='fa fa-check fa-fw green '></i>" : "<i class='fa fa-close fa-fw red'></i>";
                }},
            {
                field: 'action',
                title: '操作',
                width: 125,
                align: 'center',
                formatter: function (value, row) {
                    var a = '<a href="javascript:void(0)" class="easyui-linkbutton " data-options="plain:false" onclick="mergeRow(&quot;' + row.id + '&quot;)"><i class="fa fa-edit fa-fw"></i>编辑</a>&nbsp;&nbsp;';
                    var c = '<a href="javascript:void(0)" class="easyui-linkbutton " data-options="plain:false" onclick="deleteRow(&quot;' + row.id + '&quot;)"><i class="fa fa-trash fa-fw"></i>删除</a>';
                    return a + c;
                }
            }
        ]]
    });
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
                            $('#dg').datagrid('reload');
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
    var Parent = $('#content').parent();
    var Own = $('#content').clone();
    var title=empty(id)?"新增【坐落位置信息】":"编辑【坐落位置信息】";
    Own.hide();
    $('#content').dialog({
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: "努力加载中...",
        title: title,
        modal: true,
        height: 250,
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
            point_id: $("#point_id").combobox("getValue")
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
                        $('#dg').datagrid('reload');
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

function f_search(value) {
    var queryparams = {
        value: value,
        point_id: $("#point_id").combobox("getValue")
    };
    $('#dg').datagrid('options').queryParams = queryparams;
    $('#dg').datagrid('reload');
}

/*]]>*/