/*<![CDATA[*/
var basepath = $("#basepath").val();
$(function () {
    $("#tableHeaderGrid").datagrid({
        singleSelect: true,
        fit: true,
        fitColumns: true,
        rownumbers: false,
        maximized: true,
        border: false,
        url: basepath + '/table/getColumnsInfo',
        queryParams: {action: $("#action").val()},
        onClickRow: onClickRow,
        columns: [[{
            field: 'title',
            title: '列名',
            width: 150
        }, {
            field: 'name',
            title: '显示名称',
            width: 150,
            editor: {
                type: 'textbox',
                height: 27
            }
        }, {
            field: 'hidden',
            title: '显示',
            width: 80,
            formatter: function (value, row) {
                return value == 0 ? "<i class='fa fa-check fa-fw'></i>" : "<i class='fa fa-close fa-fw'></i>";
            },
            editor: {
                type: 'checkbox',
                options: {
                    on: '0',
                    off: '1'
                }
            }
        }
        ]],
        onLoadSuccess: function () {
            $("#tableHeaderGrid").datagrid('enableDnd');
        },
        onDrop: function (dRow, sRow, point) {
            enableHeaderDnd(dRow, sRow, point);
        }
    }).datagrid('columnMoving').datagrid('getPanel').addClass("lines-no");

});

var editIndex = undefined;

function endEditing() {
    if (editIndex == undefined) {
        return true
    }
    if ($('#tableHeaderGrid').datagrid('validateRow', editIndex)) {
        $('#tableHeaderGrid').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

function onClickRow(index) {
    if (editIndex != index) {
        if (endEditing()) {
            $('#tableHeaderGrid').datagrid('beginEdit', index);
            editIndex = index;
            var ed = $('#tableHeaderGrid').datagrid('getEditor', {
                index: editIndex,
                field: "name"
            });
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
        }
    }
}

function accept() {
    if (endEditing()) {
        $('#tableHeaderGrid').datagrid('acceptChanges');
    }
}

function reject() {
    $('#tableHeaderGrid').datagrid('rejectChanges');
    editIndex = undefined;
}

//getChanges
function getChanges(checkbox) {
    var _g = "#" + $("#action").val();
    if (endEditing()) {
        var rows = $('#tableHeaderGrid').datagrid('getChanges');
        if (rows.length > 0) {
            $.messager.confirm({
                top: '20%',
                left:'30%',
                msg: "是否保存列配置！保存后将重新刷新当前页面！",
                title: "提示",
                fn: function (r) {
                    if (r) {
                        LG.ajax({
                            url: basepath + '/table/saveTableHeader',
                            data: {
                                rows: base64_encode(JSON.stringify(rows))
                            },
                            success: function (data) {
                                top.reloadTab();
                            },
                            error: function (message) {
                                LG.alert("error", message);
                            }
                        });
                    }
                }
            });
        }
    }
    $('#tableHeaderGrid').datagrid('clearSelections');
}

//reset
function restHeaderSetup(checkbox) {
    var _g = $("#action").val();
    $.messager.confirm({
        top: '20%',
        left:'30%',
        msg: "是否重置列配置？重置后将重新刷新该页面",
        title: "提示",
        fn: function (r) {
            if (r) {
                LG.ajax({
                    url: basepath + '/table/resetTableHeader',
                    data: {
                        action: _g
                    },
                    success: function (data) {
                        top.reloadTab();
                    },
                    error: function (message) {
                        LG.alert("error", message);
                    }
                });
            }
        }
    });
}

//getChanges
function getHtmlChanges(checkbox) {
    var _g = "#" + $("#action").val();
    if (endEditing()) {
        var rows = $('#tableHeaderGrid').datagrid('getChanges');
        var _rows = $('#tableHeaderGrid').datagrid('getRows');
        if (_rows != null && _rows.length > 0) {
            var html = [];
            if (checkbox) {
                html.push({
                    "field": 'id',
                    "checkbox": true
                });
            }
            $.each(_rows, function (i, item) {
                var sortable = item.sortable == 1 ? true : false;
                var hidden = item.hidden == 1 ? true : false;
                html.push({
                    "field": item.field,
                    "width": item.width,
                    "sortable": sortable,
                    "hidden": hidden,
                    "title": item.name
                });
            });
            $(_g).datagrid({columns: [html]}).datagrid("resize");
        }
        if (rows.length > 0) {
            $.messager.confirm({
                top: '20%',
                left:'30%',
                msg: "是否保存列配置！",
                title: "提示",
                fn: function (r) {
                    if (r) {
                        LG.ajax({
                            url: basepath + '/table/saveTableHeader',
                            data: {
                                rows: base64_encode(JSON.stringify(rows))
                            },
                            error: function (message) {
                                LG.alert("error", message);
                            }
                        });
                    }
                }
            });
        }
    }
    $('#tableHeaderGrid').datagrid('clearSelections');
}

//enableHeaderDnd
function enableHeaderDnd(dRow, sRow, point) {
    var rows = $('#tableHeaderGrid').datagrid('getRows');
    var ids = $(rows).map(function () {
        return this.id;
    }).get().join(",");
    var data = {
        ids: ids
    };
    LG.ajax({
        url: basepath + '/table/upordersHeader',
        data: data,
        success: function () {
            $('#tableHeaderGrid').datagrid('reload');
        },
        error: function (message) {
            LG.tip("error", message);
            return false;
        }
    });
}

/*]]>*/