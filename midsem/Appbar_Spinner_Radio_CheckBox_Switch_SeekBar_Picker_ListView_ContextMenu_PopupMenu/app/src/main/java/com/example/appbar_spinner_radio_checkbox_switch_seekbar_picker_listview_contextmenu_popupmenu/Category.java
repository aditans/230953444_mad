package com.example.appbar_spinner_radio_checkbox_switch_seekbar_picker_listview_contextmenu_popupmenu;

public class Category {
    private String name;
    private int iconResId;

    public Category(String name, int iconResId) {
        this.name = name;
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }

    @Override
    public String toString() {
        return name;
    }
}
