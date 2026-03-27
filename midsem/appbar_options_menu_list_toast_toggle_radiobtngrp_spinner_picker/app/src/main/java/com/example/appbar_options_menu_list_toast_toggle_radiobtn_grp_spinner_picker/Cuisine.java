package com.example.appbar_options_menu_list_toast_toggle_radiobtn_grp_spinner_picker;

public class Cuisine {
    private String name;
    private int imageResId;

    public Cuisine(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}