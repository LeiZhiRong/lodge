package com.shgs.lodge.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页快捷导航菜单
 */
public class MenuShortcut {
    private String id;
    private String iconCls;
    private String text;
    private Object url;
    private List<MenuShortcut> list= new ArrayList<>();

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public MenuShortcut() {
        super();
    }

    public List<MenuShortcut> getList() {
        return list;
    }

    public void setList(List<MenuShortcut> list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
