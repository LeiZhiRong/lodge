package com.shgs.lodge.dto;

import java.util.ArrayList;
import java.util.List;

public class TreeJson {
  private String iconCls;
  private String id;
  private String text;
  private String pid;
  private String state;
  private Object arg;
  private Object arg1;
  private boolean checked;
  private List<TreeJson> children = new ArrayList<TreeJson>();

  public TreeJson() {
    super();
  }

  public String getIconCls() {
    return iconCls;
  }

  public void setIconCls(String iconCls) {
    this.iconCls = iconCls;
  }

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

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  public List<TreeJson> getChildren() {
    return children;
  }

  public void setChildren(List<TreeJson> children) {
    this.children = children;
  }

  public Object getArg() {
    return arg;
  }

  public void setArg(Object arg) {
    this.arg = arg;
  }

  public Object getArg1() {
    return arg1;
  }

  public void setArg1(Object arg1) {
    this.arg1 = arg1;
  }

  public TreeJson(String id, String text, String pid) {
    this.id = id;
    this.text = text;
    this.pid = pid;
  }

  public TreeJson(String id, String text, String pid, boolean checked) {
    this.id = id;
    this.text = text;
    this.pid = pid;
    this.checked = checked;
  }

  public TreeJson(String id, String text, String pid, Object arg, Object arg1) {
    this.id = id;
    this.text = text;
    this.pid = pid;
    this.arg = arg;
    this.arg1 = arg1;
  }

  public TreeJson(String id, String text, String pid, List<TreeJson> children) {
    this.id = id;
    this.text = text;
    this.pid = pid;
    this.children = children;
  }

  public TreeJson(String iconCls, String id, String text, String pid, Object arg) {
    this.iconCls = iconCls;
    this.id = id;
    this.text = text;
    this.pid = pid;
    this.arg = arg;
  }



  public final static List<TreeJson> getfatherNode(List<TreeJson> treeDataList) {
    List<TreeJson> newTreeDataList = new ArrayList<TreeJson>();
    for (TreeJson jsonTreeData : treeDataList) {
      if (jsonTreeData.getPid() == null || jsonTreeData.getPid().isEmpty()) {
        jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(), treeDataList));
        newTreeDataList.add(jsonTreeData);
      }
    }
    return newTreeDataList;
  }

  public final static List<TreeJson> getfatherNode( String pid,List<TreeJson> treeDataList) {
    List<TreeJson> newTreeDataList = new ArrayList<TreeJson>();
    for (TreeJson jsonTreeData : treeDataList) {
      if (jsonTreeData.getId() == pid) {
        jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(), treeDataList));
        newTreeDataList.add(jsonTreeData);
      }
    }
    return newTreeDataList;
  }

  private final static List<TreeJson> getChildrenNode(String pid, List<TreeJson> treeDataList) {
    List<TreeJson> newTreeDataList = new ArrayList<TreeJson>();
    for (TreeJson jsonTreeData : treeDataList) {
      if (jsonTreeData.getPid() == null || jsonTreeData.getPid().isEmpty()) continue;
      //这是一个子节点
      if (jsonTreeData.getPid().equals(pid)) {
        //递归获取子节点下的子节点
        jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(), treeDataList));
        newTreeDataList.add(jsonTreeData);
      }
    }
    return newTreeDataList;
  }

}

