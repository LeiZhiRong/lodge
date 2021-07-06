/*<![CDATA[*/
$(function () {
    $('#tcode').textbox().textbox('addClearBtn', 'icon-clear');
    $('#wcode').textbox().textbox('addClearBtn', 'icon-clear');
    $('#ycode').textbox().textbox('addClearBtn', 'icon-clear');
    $('#dl1').datalist({
        loadMsg: false,
        header: '#wfqx',
        method: 'post',
        valueField: 'id',
        textField: 'name',
        lines: false,
        checkbox: true,
        singleSelect: false
    });
    $('#dl2').datalist({
        loadMsg: false,
        header: '#yfqx',
        method: 'post',
        valueField: 'id',
        textField: 'name',
        lines: false,
        checkbox: true,
        singleSelect: false
    });

    $("#tlist").datalist({
        loadMsg: false,
        url: "list",
        toolbar: '#Ttools',
        lines: false,
        border: false,
        fit: true,
        textField: 'name',
        valueField: 'id',
        onClickRow: function (index, row) {
            $("#dl1").datalist({url: 'getRoleInfo', queryParams: {user_id: row.id}});
            $("#dl2").datalist({url: 'getUserGrant', queryParams: {user_id: row.id}});
        },
        onLoadSuccess: function () {
            $('#wcode').textbox("setValue","");
            $('#ycode').textbox("setValue","");
            $('#dl1').datalist('loadData', {rows: []});
            $('#dl2').datalist('loadData', {rows: []});
        }
    });
    //移动
    $('#dl_add').bind('click', function () {
        var user_row = $("#tlist").datalist("getSelected");
        var rows = $("#dl1").datalist("getSelections");
        var ids = $(rows).map(function () {
            return this.id;
        }).get().join(",");
        if (empty(ids) || empty(user_row))
            return false;
        LG.ajax({
            url: 'savaUserGrant',
            data: {
                role_ids: ids,
                user_id: user_row.id
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
        var user_row = $("#tlist").datalist("getSelected");
        var data = $("#dl1").datalist("getData");
        var rows = data.rows;
        var ids = $(rows).map(function () {
            return this.id;
        }).get().join(",");
        if (empty(ids) || empty(user_row))
            return false;
        LG.ajax({
            url: 'savaUserGrant',
            data: {
                role_ids: ids,
                user_id: user_row.id
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
        var user_row = $("#tlist").datalist("getSelected");
        var rows = $("#dl2").datalist("getSelections");
        var ids = $(rows).map(function () {
            return this.id;
        }).get().join(",");
        if (empty(ids) || empty(user_row))
            return false;
        LG.ajax({
            url: 'delUserGrant',
            data: {
                role_ids: ids,
                user_id: user_row.id
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
        var user_row = $("#tlist").datalist("getSelected");
        var data = $("#dl2").datalist("getData");
        var rows = data.rows;
        var ids = $(rows).map(function () {
            return this.id;
        }).get().join(",");
        if (empty(ids) || empty(user_row))
            return false;
        LG.ajax({
            url: 'delUserGrant',
            data: {
                role_ids: ids,
                user_id: user_row.id
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
            user_id: row.id
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
            user_id: row.id
        }
    });
}
/*]]>*/