/*<![CDATA[*/
$(function () {
    //初始化开票信息表
    $("#confirmGrid").datagrid({
        loadMsg: false,
        singleSelect: true,
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
            var hj = 0;
            var rows = $("#confirmGrid").datagrid("getRows");
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    hj = hj + (row.billMoney);
                }
            }
            $('#hj').text("【合计金额】￥" + hj);
        },
        toolbar: '#Ptools',
        footer: '#buttons'
    });

    //实现复选框
    $("#applyDeptBH").combobox({
        multiple: true,
        separator: ';',
        prompt: '请选择...',
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
            $(this).combobox('setValues', "");
            $(this).combobox("panel").parent("div").append("<div style='background: #eeeeee;height: 35px;padding: 5px;text-align: center'><button  style='border: 0;width: 80px;height: 25px' id='apply_select'>正选</button><button  style='border: 0;width: 80px;height: 25px' id='apply_close'>确定</button></div>");
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
    $("#apply_select").linkbutton({
        onClick: function () {
            var str = $("#apply_select").linkbutton("options").text;
            var valueField = $("#applyDeptBH").combobox("options").valueField;
            var data = $("#applyDeptBH").combobox("getData");
            var selectVaues = [];
            if (is_eq("正选", str)) {
                $('#apply_select').linkbutton({text: '反选'});
                data.reduce(function (prev, current, index, array) {
                    selectVaues.push(current[valueField]);
                }, selectVaues);

            } else {
                selectVaues.push(null);
                $('#apply_select').linkbutton({text: '正选'});
            }
            $("#applyDeptBH").combobox('setValues', selectVaues);

        }
    })
    // 关闭下拉框
    $("#apply_close").linkbutton({
        onClick: function () {
            $('#applyDeptBH').combobox('hidePanel');

        }
    })

})

//查询
function confirmInfoSearch(value, name) {
    var applyDeptBH = $("#applyDeptBH").combobox("getValues").join(",");
    var starDate = $("#starDate").datebox("getValue");
    var endDate = $("#endDate").datebox("getValue");
    if (!isDate(starDate))
        return false;
    if (!isDate(endDate))
        return false;
    var queryparams = {
        value: value,
        field: name,
        starDate: starDate,
        endDate: endDate,
        applyDeptBH: applyDeptBH,
        saleCorpID: $("#saleCorpID").val(),
        buyerCorpID: $("#buyerCorpID").val()
    };
    $('#confirmGrid').datagrid('options').queryParams = queryparams;
    $('#confirmGrid').datagrid('options').url = "list";
    $('#confirmGrid').datagrid('reload');

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
        border: true,
        constrain: true,
        href: basepath + '/table/getColumns',
        queryParams: {
            action: action
        },
        onClose: function () {
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            iconCls: 'fa fa-check red',
            text: '应用',
            handler: function () {
                getHtmlChanges(false);
            }
        }, {
            iconCls: 'fa fa-reply red',
            text: '关闭',
            handler: function () {
                $('#headerContent').dialog('close');
            }
        }]
    });
}

//查询销售方
function _searchSaleCorp(value) {
    var corpParent = $('#corpContent').parent();
    var corpOwn = $('#corpContent').clone();
    corpOwn.hide();
    $('#corpContent').dialog({
        title: "请选择销售方...",
        loadingMessage: false,
        modal: true,
        height: 450,
        width: 350,
        top: '5%',
        left: '30%',
        border: true,
        constrain: true,
        href: 'getCorpInfo',
        queryParams: {
            keyword: value,
            corpType: 0
        },
        onClose: function () {
            corpOwn.appendTo(corpParent);
            $(this).dialog("destroy").remove();
        }
    });
}

//查询购买方
function _searchBuyerCorp(value) {
    var corpParent = $('#corpContent').parent();
    var corpOwn = $('#corpContent').clone();
    corpOwn.hide();
    $('#corpContent').dialog({
        title: "请选择购买方...",
        loadingMessage: false,
        modal: true,
        height: 450,
        width: 350,
        top: '5%',
        left: '30%',
        border: true,
        constrain: true,
        href: 'getCorpInfo',
        queryParams: {
            keyword: value,
            corpType: 1
        },
        onClose: function () {
            corpOwn.appendTo(corpParent);
            $(this).dialog("destroy").remove();
        }
    });
}

//驳回开票申请
function rejectRow(id) {
    $.messager.confirm({
        msg: '是否驳回开票申请？',
        title: '提示',
        top: '10%',
        fn: function (r) {
            if (r) {
                LG.ajax({
                    url: 'rejectApply',
                    data: {
                        ids: id
                    },
                    success: function (data) {
                        if (data.code == 1) {
                            $('#confirmGrid').datagrid('reload');
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

//开票确认
function confirmRow(id) {
    $("body").append("<div id='confirmContent' Style='display: none;'></div>");
    $('#confirmContent').dialog({
        title: '开票信息',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        inline: true,
        border: 'thin',
        cls: 'c6',
        height: 450,
        width: 760,
        top: '5%',
        left: '20%',
        constrain: true,
        href: 'dialog',
        queryParams: {
            id: id
        },
        onClose: function () {
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            iconCls: 'e-icon1 fa fa-floppy-o red',
            text: '确认',
            handler: function () {
                if (!$("#confirmForm").form('enableValidation').form('validate'))
                    return false;
                var actiform = $("#confirmForm");
                LG.ajaxSubmitForm(actiform, function (data) {
                    if (data.code == 1) {
                        $('#confirmGrid').datagrid('reload');
                        $('#confirmContent').dialog('destroy').remove();
                    } else {
                        LG.alert("error", data.message);
                    }

                })
            }
        }, {
            iconCls: 'icon1 fa fa-reply red',
            text: '取消',
            handler: function () {
                $('#confirmContent').dialog('destroy').remove();
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
        height: '80%',
        width: '80%',
        top: '5%',
        left: '5%',
        border: true,
        constrain: true,
        href: 'importUpload',
        onClose: function () {
            $('#applyGrid').datagrid('confirmGrid');
            $(this).dialog("destroy").remove();
        },
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
                var applyDeptBH = $("#applyDeptBH").combobox("getValues").join(",");
                var starDate = $("#starDate").datebox("getValue");
                var endDate = $("#endDate").datebox("getValue");
                var saleCorpID = $("#saleCorpID").val();
                var buyerCorpID = $("#buyerCorpID").val();
                if (!isDate(starDate))
                    return false;
                if (!isDate(endDate))
                    return false;
                var value = $("#ss").searchbox('getValue');
                var name = $("#ss").searchbox('getName');
                window.location.href = "exportDown?value=" + value + "&field=" + name +"&starDate=" + starDate +"&endDate=" + endDate +"&applyDeptBH=" + applyDeptBH +"&buyerCorpID=" + buyerCorpID + "&ztbz=T";
            }
        }
    });

}

/*]]>*/