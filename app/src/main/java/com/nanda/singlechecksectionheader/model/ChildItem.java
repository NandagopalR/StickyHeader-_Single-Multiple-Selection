package com.nanda.singlechecksectionheader.model;

public class ChildItem {

    private String message;
    private boolean isMultiSelect = false;

    public ChildItem(String message) {
        this.message = message;
    }

    public boolean isMultiSelect() {
        return isMultiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        isMultiSelect = multiSelect;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
