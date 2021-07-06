package com.shgs.lodge.dto;

public class ListGroup {
    String id;
    String text;
    String group;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ListGroup() {
        super();
    }

    public ListGroup(String id, String text, String group) {
        this.id = id;
        this.text = text;
        this.group = group;
    }
}
