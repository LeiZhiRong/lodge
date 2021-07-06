/*<![CDATA[*/
$(function () {
    //初始化开票信息表
    $("#applyGrid").datagrid({
        loadMsg: false,
        url: 'list',
        singleSelect: true,
        checkOnSelect: false,
        selectOnCheck: false,
        collapsible: true,
        fit: true,
        striped: true,
        maximized: true,
        rownumbers: true,
        sortName: 'applyBH',
        sortOrder: 'asc',
        border: true,
        pagination: true,
        pageSize: 20,
        onLoadSuccess: function () {
            var hj=0;
            var rows=$("#applyGrid").datagrid("getRows");
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                   hj=hj+(row.billMoney);
                }
            }
            $('#hj').text("￥"+hj);
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
        toolbar: '#Ptools',
        footer: '#buttons'
    });

    //初始化开票历史信息表
    $("#applyHistoryGrid").datagrid({
        loadMsg: false,
        singleSelect: true,
        checkOnSelect: false,
        selectOnCheck: false,
        collapsible: true,
        fit: true,
        striped: true,
        maximized: true,
        rownumbers: true,
        sortName: 'applyBH',
        sortOrder: 'asc',
        border: true,
        pagination: true,
        pageSize: 20,
        onLoadSuccess: function () {
            var thj=0;
            var rows=$("#applyHistoryGrid").datagrid("getRows");
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    thj=thj+(row.billMoney);
                }
            }
            $('#thj').text("￥"+thj);
        },
        onCheck: function (index, row) {

        },
        onUncheck: function (index, row) {

        },
        onCheckAll: function (rows) {

        },
        onUncheckAll: function (row) {

        },
        toolbar: '#Htools',
        footer: '#Hbuttons'
    });

    //实现复选框
    $("#Hztbz").combobox({
        multiple: true,
        separator:",",
        prompt:'请选择...',
        formatter: function (row) {
            var opts = $(this).combobox('options');
            return '<input type="checkbox" class="combobox-checkbox">&nbsp;' + row[opts.textField];

        },
        onLoadSuccess: function () {
            var opts = $(this).combobox('options');
            var target = this;
            var values = $(target).combobox('getValues');
            $.map(values, function (value) {
                var el = opts.finder.getEl(target, value);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            })
            $(this).combobox("panel").parent("div").append("<div style='background: #eeeeee;height: 35px;padding: 5px;text-align: center'><button  style='border: 0;width:80px;height: 25px' id='Hztbz_select'>正选</button><button  style='border: 0;width:80px;height: 25px' id='Hztbz_close'>确定</button></div>");
        },
        onSelect: function (row) {
            var opts = $(this).combobox('options');
            var el = opts.finder.getEl(this, row[opts.valueField]);
            el.find('input.combobox-checkbox')._propAttr('checked', true);
        },
        onUnselect: function (row) {
            var opts = $(this).combobox('options');
            var el = opts.finder.getEl(this, row[opts.valueField]);
            el.find('input.combobox-checkbox')._propAttr('checked', false);
        }
    })
    //全选（反选）
    $("#Hztbz_select").linkbutton({
        onClick: function () {
            var str = $("#Hztbz_select").linkbutton("options").text;
            var valueField = $("#Hztbz").combobox("options").valueField;
            var data = $("#Hztbz").combobox("getData");
            var selectVaues = [];
            if (is_eq("正选", str)) {
                $('#Hztbz_select').linkbutton({text: '反选'});
                data.reduce(function (prev, current, index, array) {
                    selectVaues.push(current[valueField]);
                }, selectVaues);

            } else {
                selectVaues.push(null);
                $('#Hztbz_select').linkbutton({text: '正选'});
            }
            $("#Hztbz").combobox('setValues', selectVaues);

        }
    })
    // 关闭下拉框
    $("#Hztbz_close").linkbutton({
        onClick: function () {
            $('#Hztbz').combobox('hidePanel');

        }
    })

    //实现复选框
    $("#deptBh").combobox({
        multiple: true,
        separator:",",
        prompt:'请选择...',
        formatter: function (row) {
            var opts = $(this).combobox('options');
            return '<input type="checkbox" class="combobox-checkbox">&nbsp;' + row[opts.textField];

        },
        onLoadSuccess: function () {
            var opts = $(this).combobox('options');
            var target = this;
            var values = $(target).combobox('getValues');
            $.map(values, function (value) {
                var el = opts.finder.getEl(target, value);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            })
            $(this).combobox("panel").parent("div").append("<div style='background: #eeeeee;height: 35px;padding: 5px;text-align: center'><button  style='border: 0;width:80px;height: 25px' id='dept_select'>正选</button><button  style='border: 0;width:80px;height: 25px' id='dept_close'>确定</button></div>");
        },
        onSelect: function (row) {
            var opts = $(this).combobox('options');
            var el = opts.finder.getEl(this, row[opts.valueField]);
            el.find('input.combobox-checkbox')._propAttr('checked', true);
        },
        onUnselect: function (row) {
            var opts = $(this).combobox('options');
            var el = opts.finder.getEl(this, row[opts.valueField]);
            el.find('input.combobox-checkbox')._propAttr('checked', false);
        }
    })
    //全选（反选）
    $("#dept_select").linkbutton({
        onClick: function () {
            var str = $("#dept_select").linkbutton("options").text;
            var valueField = $("#deptBh").combobox("options").valueField;
            var data = $("#deptBh").combobox("getData");
            var selectVaues = [];
            if (is_eq("正选", str)) {
                $('#dept_select').linkbutton({text: '反选'});
                data.reduce(function (prev, current, index, array) {
                    selectVaues.push(current[valueField]);
                }, selectVaues);

            } else {
                selectVaues.push(null);
                $('#dept_select').linkbutton({text: '正选'});
            }
            $("#deptBh").combobox('setValues', selectVaues);

        }
    })
    // 关闭下拉框
    $("#dept_close").linkbutton({
        onClick: function () {
            $('#deptBh').combobox('hidePanel');

        }
    })


})

//添加
function addApply() {
    altApplyDialog(null,"add");
}

//复制
function copyRow(id) {
    altApplyDialog(id,"copy");
}
//编辑
function editRow(id) {
    altApplyDialog(id,"edit");
}

//查询
function applyInfoSearch(value, name) {
    var queryparams = {
        value: value,
        field: name
    };
    $('#applyGrid').datagrid('options').queryParams = queryparams;
    $('#applyGrid').datagrid('reload');

}

//删除
function deleteRow(id) {
    $.messager.confirm({
        msg: '是否删除所选记录！',
        title: '提示',
        top: '10%',
        fn: function (r) {
            if (r) {
                LG.ajax({
                    url: 'deleteApplyInfo',
                    data: {
                        id: id
                    },
                    success: function (data) {
                        if (data.code == 1) {
                            $('#applyGrid').datagrid('reload');
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

//导出
function exportDown() {
    $.messager.confirm({
        msg: '是否将开票信息导出为Excel！',
        title: '提示',
        top: '10%',
        fn: function (r) {
            if (r) {
                var value = $("#ss").searchbox('getValue');
                var name = $("#ss").searchbox('getName');
                window.location.href = "exportDown?value=" + value+"&field="+name+"&ztbz=DF";
            }
        }
    });

}

//Dialog
function altApplyDialog(id,cation) {
    var title=empty(id)?"新增【开票信息】":"编辑【开票信息】";
    $("body").append("<div id='applyContent' Style='display: none; '></div>");
    $('#applyContent').dialog({
        title: title,
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        inline:true,
        border:'thin',
        cls:'c8',
        height: 480,
        width: 800,
        top: '5%',
        constrain: true,
        href: 'dialog',
        queryParams: {
            id: id,
            cation:cation
        },
        onClose: function () {
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            iconCls: 'e-icon1 fa fa-floppy-o blue',
            text: '保存',
            handler: function () {
                if (!$("#applyForm").form('enableValidation').form('validate'))
                    return false;
                var actiform = $("#applyForm");
                LG.ajaxSubmitForm(actiform, function (data) {
                    if (data.code == 1) {
                        $('#applyGrid').datagrid('reload');
                        $('#applyContent').dialog('close');
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'icon1 fa fa-reply blue',
            text: '取消',
            handler: function () {
                $('#applyContent').dialog('close');
            }
        }]
    });
}

//grid check
var data_ids;

function getChecked() {
    var ss = [];
    var rows = $('#applyGrid').datagrid('getChecked');
    if (rows.length > 0) {
        var hj1=0.00;
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            hj1=hj1+(row.billMoney);
            ss.push(row.id);
        }
        $("#hj1").text("选择金额：￥"+hj1);
        $('#batchDeleteBtn').linkbutton('enable');
        $('#batchExamineBtn').linkbutton('enable');
        data_ids = ss.join(',');
    } else {
        $('#batchDeleteBtn').linkbutton('disable');
        $('#batchExamineBtn').linkbutton('disable');
        data_ids = null;
        $("#hj1").text("");
    }
}

//批量删除
function batchdeleteApply() {
    if (empty(data_ids)) {
        LG.alert("error", "请选择开票信息，然后重试！");
        return false;
    }
    $.messager.confirm({
        msg: '是否删除所选记录！',
        title: '提示',
        top: '10%',
        fn: function (r) {
            if (r) {
                LG.ajax({
                    url: 'batchDeleteApply',
                    data: {
                        ids: data_ids
                    },
                    success: function (data) {
                        if (data.code == 1) {
                            $('#applyGrid').datagrid('reload');
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
        top: '10%',
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

//列调整
function theaderSetup(action) {
    $("body").append("<div id='headerContent' Style='display: none;'></div>");
    $('#headerContent').dialog({
        title: '列配置',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 400,
        width: 600,
        top: '10%',
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
        },{
            iconCls: 'fa fa-repeat blue',
            text: '重置',
            handler: function () {
                restHeaderSetup();
            }
        },  {
            iconCls: 'fa fa-reply blue',
            text: '关闭',
            handler: function () {
                $('#headerContent').dialog('close');
            }
        }]
    });
}

//模板导入
function importUpload() {
    $("body").append("<div id='importContent' Style='display: none;'></div>");
    $('#importContent').dialog({
        title: '开票信息导入',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: '90%',
        width: '95%',
        top: '2%',
        left: '2%',
        cls: 'c4',
        inline: true,
        border: true,
        constrain: true,
        href: 'importUpload',
        onClose: function () {
            $('#applyGrid').datagrid('reload');
            $(this).dialog("destroy").remove();
        },
    });
}
//提交审核
function batchExamine() {
    if (empty(data_ids)) {
        LG.alert("error", "请选择开票信息，然后重试！");
        return false;
    }
    $.messager.confirm({
        msg: '提交审核后开票信息将锁定，是否继续？',
        title: '提示',
        top: '10%',
        fn: function (r) {
            if (r) {
                LG.ajax({
                    url: 'batchExamine',
                    data: {
                        ids: data_ids
                    },
                    success: function (data) {
                        if (data.code == 1) {
                            $('#applyGrid').datagrid('reload');
                            $('#applyHistoryGrid').datagrid('reload');
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

//历史票据
function applyHistoryInfoSearch(value, name) {
    var _ztbz=$("#Hztbz").combobox("getValues").join(",");
    var starDate=$("#starDate").datebox("getValue");
    var endDate=$("#endDate").datebox("getValue");
    if(!isDate(starDate))
        return false;
    if(!isDate(endDate))
        return false;
    var queryparams = {
        value: value,
        field: name,
        ztbz:_ztbz,
        starDate:starDate,
        endDate:endDate
    };
    $('#applyHistoryGrid').datagrid('options').queryParams = queryparams;
    $('#applyHistoryGrid').datagrid('options').url="listHistoryInfo";
    $('#applyHistoryGrid').datagrid('reload');

}
//导出历史数据
function exportHistory() {
    var _ztbz=$("#Hztbz").combobox("getValues").join(",");
    var starDate=$("#starDate").datebox("getValue");
    var endDate=$("#endDate").datebox("getValue");
    if(!isDate(starDate))
        return false;
    if(!isDate(endDate))
        return false;
    $.messager.confirm({
        msg: '是否将开票信息导出为Excel！',
        title: '提示',
        top: '10%',
        fn: function (r) {
            if (r) {
                var value = $("#hh").searchbox('getValue');
                var name = $("#hh").searchbox('getName');
                var ztbz=$("#Hztbz").combobox("getValue");
                window.location.href = "exportDown?value=" + value+"&field="+name+"&ztbz="+_ztbz+"&starDate="+starDate+"&endDate="+endDate;
            }
        }
    });

}

/*]]>*/