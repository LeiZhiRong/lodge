package com.shgs.lodge.dto;


import com.shgs.lodge.primary.entity.TableHeader;

import java.util.ArrayList;
import java.util.List;

public class HeaderColumns {

    private String field;
    private String title;
    private int width;
    private boolean sortable;
    private boolean hidden;
    private int status;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }


    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public HeaderColumns() {
        super();
    }

    public HeaderColumns(String field, String title, int width, boolean sortable, boolean hidden, int status) {
        this.field = field;
        this.title = title;
        this.width = width;
        this.sortable = sortable;
        this.hidden = hidden;
        this.status = status;
    }


    public HeaderColumns(TableHeader dto) {
        this.setField(dto.getField());
        this.setTitle(dto.getName());
        this.setWidth(dto.getWidth());
        this.setSortable(dto.getSortable() == 1);
        if (dto.getStatus() == 1) {
            this.setHidden(dto.getHidden() == 1);
        } else {
            this.setHidden(true);
        }
           this.setStatus(dto.getStatus());
    }

    public List<HeaderColumns> listHeaderColumns(List<TableHeader> dto) {
        List<HeaderColumns> list = new ArrayList<>();
        if (dto.size() > 0) {
            for (TableHeader mast : dto) {
                list.add(new HeaderColumns(mast));
            }
        }
        return list;
    }


}

