package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.MenuInfo;
import com.shgs.lodge.util.RoleType;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class MenuInfoDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 菜单名称
     */
    @NotEmpty(message = "模块名称不能为空")
    private String name;

    /**
     * 菜单图标
     */
    private String icons;

    /**
     * 排序号
     */
    private Integer orders;

    private String parent_id;

    private String parent_name;

    /**
     * 访问路径
     */
    private String pathUrl;

    /**
     * 是否启用
     */
    private String status2str;

    private int status;

    private RoleType type;

    /**
     * 首页显示
     */
    private String hompPage;

    /**
     * 是否下发
     */
    private String modelGrant;

    /**
     * 目录显示
     */
    private String contents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getStatus2str() {
        return status2str;
    }

    public void setStatus2str(String status2str) {
        this.status2str = status2str;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    public MenuInfoDto() {
        super();
    }

    public String getHompPage() {
        return hompPage;
    }

    public void setHompPage(String hompPage) {
        this.hompPage = hompPage;
    }

    public String getModelGrant() {
        return modelGrant;
    }

    public void setModelGrant(String modelGrant) {
        this.modelGrant = modelGrant;
    }

    public MenuInfoDto(MenuInfo menuInfo) {
        this.setId(menuInfo.getId());
        this.setStatus2str(menuInfo.getStatus() == 1 ? "<i class='fa fa-check  fa-lg  '></i>" : "<i class='fa fa-close fa-lg '></i>");
        this.setContents(menuInfo.getContents());
        if ("T".equals(menuInfo.getContents())) {
            this.setName("<a href='javascript:void(0)' onclick='findMenuInfo(&quot;" + menuInfo.getId() + "&quot;);'><i class='fa fa-folder-open-o fa-lg'></i> " + menuInfo.getName() + "</a>");
        } else {
            this.setName("<i class='fa fa-clone  fa-lg '></i> " + menuInfo.getName());
        }
        this.setIcons(menuInfo.getIcons() != null ? "<i class='" + menuInfo.getIcons() + "'></i>" : "");
        this.setOrders(menuInfo.getOrders());
        this.setPathUrl(menuInfo.getPathUrl());
        this.setHompPage(menuInfo.getHompPage());
        this.setModelGrant(menuInfo.getModelGrant());
        if (menuInfo.getParent() != null) {
            MenuInfo parent = menuInfo.getParent();
            this.setParent_id(parent.getId());
            if (parent.getParent() != null) {
                this.setParent_name("<a href='javascript:void(0)' onclick='findMenuInfo(&quot;" + parent.getParent().getId() + "&quot;);'><i class='fa  fa-folder-open-o  fa-lg'></i> " + parent.getName() + "</a>");
            } else {
                this.setParent_name("<a href='javascript:void(0)' onclick='findMenuInfo(null);'><i class='fa fa-folder-open-o fa-lg'></i> " + parent.getName() + "</a>");
            }
        } else {
            this.setParent_id(null);
            this.setParent_name("");
        }
    }

    public List<MenuInfoDto> listMenuInfoDto(List<MenuInfo> menuInfoList) {
        List<MenuInfoDto> list = new ArrayList<>();
        if (menuInfoList.size() > 0) {
            for (MenuInfo mast : menuInfoList) {
                list.add(new MenuInfoDto(mast));
            }
        }
        return list;
    }

    public MenuInfo getMenuInfo(MenuInfoDto dto) {
        MenuInfo menuInfo = new MenuInfo();
        if (dto != null) {
            menuInfo.setId(dto.getId() != null && !dto.getId().isEmpty() ? dto.getId() : null);
            menuInfo.setOrders(dto.getOrders());
            menuInfo.setStatus(dto.getStatus());
            menuInfo.setContents(dto.getContents());
            menuInfo.setPathUrl(dto.getPathUrl());
            menuInfo.setName(dto.getName());
            menuInfo.setType(dto.getType());
            menuInfo.setIcons(dto.getIcons());
            menuInfo.setHompPage(dto.getHompPage());
            menuInfo.setModelGrant(dto.getModelGrant());
        }
        return menuInfo;

    }
}
