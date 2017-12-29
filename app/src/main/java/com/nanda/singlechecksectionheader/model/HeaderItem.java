package com.nanda.singlechecksectionheader.model;

import java.util.List;

public class HeaderItem {

    private String title;
    private List<ChildItem> childItemList;

    private boolean isMultiSelect = false;

    public HeaderItem(String title, List<ChildItem> childItemList) {
        this.title = title;
        this.childItemList = childItemList;
    }

    public boolean isMultiSelect() {
        return isMultiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        isMultiSelect = multiSelect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildItem> getChildItemList() {
        return childItemList;
    }

    public void setChildItemList(List<ChildItem> childItemList) {
        this.childItemList = childItemList;
    }
}
