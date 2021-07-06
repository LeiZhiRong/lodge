var pfields = undefined;
var pgrid = undefined;
$.extend($.fn.datagrid.methods, {
    editCell: function (jq, param) {
        return jq.each(function () {
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields', true).concat($(this).datagrid('getColumnFields'));
            var arr = new Array();
            for (var i = 0; i < fields.length; i++) {
                var col = $(this).datagrid('getColumnOption', fields[i]);
                if (col.editor != undefined) {
                    arr.push(fields[i]);
                }
                col.editor1 = col.editor;
                if (fields[i] != param.field) {
                    col.editor = null;
                }
            }
            pfields = arr;
            $(this).datagrid('beginEdit', param.index);
            var ed = $(this).datagrid('getEditor', param);
            if (ed) {
                if ($(ed.target).hasClass('textbox-f')) {
                    $(ed.target).textbox('textbox').focus();
                } else {
                    $(ed.target).focus();
                }
            }
            for (var i = 0; i < fields.length; i++) {
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});
var editIndex = undefined;
var pfield = "";

function endEditing() {
    if (editIndex == undefined) {
        return true
    }
    if (pgrid.datagrid('validateRow', editIndex)) {
        pgrid.datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

function onClickCell(index, field) {
    if (endEditing()) {
        $(this).datagrid('selectRow', index)
            .datagrid('editCell', {index: index, field: field});
        editIndex = index;
        pfield = field;
    }
    pgrid = $(this);
}

$(document).keydown(function (event) {
    var s = pgrid.datagrid('getRows').length - 1;
    //判断单元格编辑状态
    if (event.keyCode == 13 || event.keyCode == 40) { //enter 和向下方向键
        pgrid.datagrid('endEdit', editIndex);
        editIndex = editIndex + 1;
        if (editIndex == pgrid.datagrid('getData').rows.length) { //当到最后一行时 换列
            editIndex = 0;
            for (var j = 0; j < pfields.length; j++) {
                if (pfield == pfields[j]) {
                    if (j == pfields.length - 1) {
                        pfield = pfields[0];
                        break;
                    }
                    pfield = pfields[j + 1];
                    break;
                }
            }
        }
        setTimeout("pgrid.datagrid('selectRow', editIndex).datagrid('editCell', {index:editIndex,field:pfield});", 50)//防止快捷键冲突设置延迟,机智如我
    } else if (event.keyCode == 38) { //向上方向键
        var e = event
        pgrid.datagrid('endEdit', editIndex);
        editIndex = editIndex - 1;
        if (editIndex == -1) { //最前一行时换列
            editIndex = pgrid.datagrid('getData').rows.length - 1;
            for (var j = 0; j < pfields.length; j++) {
                if (pfield == pfields[j]) {
                    if (j == 0) {
                        pfield = pfields[pfields.length - 1];
                        break;
                    }
                    pfield = pfields[j - 1];
                    break;
                }
            }
        }
        setTimeout("pgrid.datagrid('selectRow', editIndex).datagrid('editCell', {index:editIndex,field:pfield});", 50)
    } else if ((event.keyCode == 9 || event.keyCode == 39) && (!event.shiftKey)) { //tab 和 向右方向键
        var e = event
        if (e.preventDefault) { //屏蔽浏览器快捷键
            e.preventDefault();
            e.stopPropagation()
        } else {
            e.returnValue = false;
            e.cancelBubble = true
        }
        pgrid.datagrid('endEdit', editIndex);
        for (var j = 0; j < pfields.length; j++) {
            if (pfield == pfields[j]) {
                if (j == pfields.length - 1) {//最后一列时换行
                    if (editIndex < s) {
                        editIndex = editIndex + 1;
                        pfield = pfields[0];
                        break;
                    } else {
                        editIndex = 0;
                        pfield = pfields[0];
                        break;
                    }
                }
                pfield = pfields[j + 1];
                break;
            }
        }
        setTimeout("pgrid.datagrid('selectRow', editIndex).datagrid('editCell', {index:editIndex,field:pfield});", 100)
    } else if ((event.keyCode == 9 || event.keyCode == 37) && !event.shiftKey) { //shift tab 向左移动
        var e = event
        if (e.preventDefault) {
            e.preventDefault();
            e.stopPropagation()
        } else {
            e.returnValue = false;
            e.cancelBubble = true
        }
        pgrid.datagrid('endEdit', editIndex);
        for (var j = 0; j < pfields.length; j++) {
            if (pfield == pfields[j]) {
                if (j == 0) {
                    if (editIndex == 0) {
                        editIndex = 0;
                        pfield = pfields[0];
                        break;
                    } else {
                        editIndex = editIndex - 1;
                        pfield = pfields[pfields.length - 1];
                        break;
                    }
                }
                pfield = pfields[j - 1];
                break;
            }
        }
        setTimeout("pgrid.datagrid('selectRow', editIndex).datagrid('editCell', {index:editIndex,field:pfield});", 100)
    }
});