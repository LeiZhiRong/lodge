/*<![CDATA[*/
$(function () {
    $('#wu-tabs').tabs({
        border: false,
        onSelect:function () {
        }
    })
})

//退出系统
function logout() {
    $.messager.confirm({
        msg: '是否要退出系统?',
        title: '提示',
        top: '20%',
        fn: function (r) {
            if (r) {
                LG.ajax({
                    url: basepath + '/login/logout',
                    success: function () {
                        window.location.href = basepath + '/login/index';
                    },
                    error: function (message) {
                        LG.alert("error", message);
                    }
                });
            }
        }
    });
}

//重置密码
function f_updateUserPwd() {
    $("body").append("<div id='passwordContent' Style='display: none;'></div>");
    $('#passwordContent').dialog({
        title: '密码修改',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 250,
        width: 400,
        top: '15%',
        inline:true,
        border:'thin',
        cls:'c6',
        constrain: true,
        href: basepath + '/home/password',
        onClose: function () {
            $(this).dialog("destroy").remove();
        },
        buttons: [{
            iconCls: 'e-icon1 fa fa-floppy-o red',
            text: '保存',
            handler: function () {
                if (!$("#pswForm").form('enableValidation').form('validate'))
                    return false;
                var pwd = $("#password").passwordbox("getValue");
                var newPwd = $("#newPwd").passwordbox("getValue");
                var confirmPwd = $("#confirmPwd").passwordbox("getValue");
                var enc_pwd, enc_confirmPwd, enc_newPwd, enc_id, error = "";
                var user_id = $("#user_id").val();
                if (empty(pwd))
                    error = error + "原始密码不能为空<br>";
                if (empty(newPwd))
                    error = error + "重设密码不能为空<br>";
                if (!is_eq(newPwd, confirmPwd))
                    error = error + "确认密码不正确<br>";
                if (!empty(error)) {
                    LG.alert("error", error);
                    return false;
                }
                $.ajax({
                    url: basepath + '/home/getKey',
                    type: "post",
                    success: function (data) {
                        if (empty(data)) {
                            LG.alert("error", "<p>获取公钥失败</p>");
                            return false;
                        }
                        var Key = new RSAUtils.getKeyPair(data.exponent, "", data.modulus);
                        if (!empty(pwd)) {
                            pwd = encodeURIComponent(pwd);
                            pwd = pwd.split("").reverse().join("");
                            enc_pwd = RSAUtils.encryptedString(Key, pwd);
                        }
                        if (!empty(newPwd)) {
                            newPwd = encodeURIComponent(newPwd);
                            newPwd = newPwd.split("").reverse().join("");
                            enc_newPwd = RSAUtils.encryptedString(Key, newPwd);
                        }
                        if (!empty(confirmPwd)) {
                            confirmPwd = encodeURIComponent(confirmPwd);
                            confirmPwd = confirmPwd.split("").reverse().join("");
                            enc_confirmPwd = RSAUtils.encryptedString(Key, confirmPwd);
                        }
                        if (!empty(user_id)) {
                            user_id = user_id.split("").reverse().join("");
                            enc_id = RSAUtils.encryptedString(Key, user_id);
                        }
                        LG.ajax({
                            url: basepath + '/home/saveUserPwd',
                            type: "post",
                            data: {
                                id: enc_id,
                                newPwd: enc_newPwd,
                                password: enc_pwd,
                                confirmPwd: enc_confirmPwd
                            },
                            success: function (data, info) {
                                $('#passwordContent').dialog('close');
                                return false;
                            },
                            error: function (info) {
                                LG.alert("error", info);
                                return false;
                            }
                        });
                    },
                    error: function (xhr) {
                        LG.alert("error", "错误代码：" + xhr.status);
                        return false;
                    }
                })
            }
        }, {
            iconCls: 'icon1 fa fa-reply red',
            text: '取消',
            handler: function () {
                $('#passwordContent').dialog('close');
            }
        }]
    });
}

function f_getUserInfo() {
    $("body").append("<div id='getUserInfoContent' Style='display: none;'></div>");
    $('#getUserInfoContent').dialog({
        title: '用户信息',
        iconCls: 'fa fa-ellipsis-v',
        loadingMessage: '数据加载中,请稍等...',
        modal: true,
        height: 450,
        width: 860,
        top: '15%',
        inline:true,
        border:'thin',
        cls:'c6',
        constrain: true,
        href: basepath + '/home/getUserInfo',
        onClose: function () {
            $(this).dialog("destroy").remove();
        }
    })
}

//add tab
function addTab(title, href) {
    var tabPanel = $('#wu-tabs');
    if (tabPanel.tabs('exists', title)) {
        tabPanel.tabs('select', title);
         return;
    }
    var allTabs = $("#wu-tabs").tabs('tabs');
     var coun = allTabs.length;
    if (coun > 9) {
     $('#wu-tabs').tabs('close', 1);
    }
    tabPanel.tabs('add', {
        title: title,
        content: 'url:' + href,
        useiframe: true,
        closable: true
    });
}

/**
 * reload tab
 */
function reloadTab() {
    var tab = $('#wu-tabs').tabs('getSelected');
    var index = $('#wu-tabs').tabs('getTabIndex',tab);
    var panel = $('#wu-tabs').tabs('getTab', index);
    var useiframe = panel.panel('options').useiframe;
    if(useiframe){
        $('iframe', panel.panel('body')).each(function(){
            this.contentWindow.location.reload();
        });
    }else{
        panel.panel('refresh');
    }
}

//close tab
function closeTab() {
    var currTab = $('#wu-tabs').tabs('getSelected');
    var currTitle = currTab.panel('options').title;
    if (!is_eq("首页", currTitle))
        $('#wu-tabs').tabs('close', currTitle);
}

//close other tab
function closeOtherTab() {
    var currTab = $('#wu-tabs').tabs('getSelected');
    var currTitle = currTab.panel('options').title;
    $(".tabs li").each(function (i, n) {
        var title = $(n).text();
        if (!is_eq(currTitle, title)) {
            if (!is_eq("首页", title))
                $('#wu-tabs').tabs('close', title);
        }
    });
}

//close all tab
function closeAllTab() {
    $(".tabs li").each(function (i, n) {
        var title = $(n).text();
        if (!is_eq("首页", title))
            $('#wu-tabs').tabs('close', title);
    });

}

/*]]>*/