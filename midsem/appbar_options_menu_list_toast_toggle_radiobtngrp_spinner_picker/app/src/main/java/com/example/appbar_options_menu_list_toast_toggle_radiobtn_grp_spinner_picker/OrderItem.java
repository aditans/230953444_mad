package com.example.appbar_options_menu_list_toast_toggle_radiobtn_grp_spinner_picker;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private String name;
    private String portion;
    private int spiceLevel;
    private String addOns;
    private boolean isPriority;

    public OrderItem(String name, String portion, int spiceLevel, String addOns, boolean isPriority) {
        this.name = name;
        this.portion = portion;
        this.spiceLevel = spiceLevel;
        this.addOns = addOns;
        this.isPriority = isPriority;
    }

    public String getName() { return name; }
    public String getPortion() { return portion; }
    public int getSpiceLevel() { return spiceLevel; }
    public String getAddOns() { return addOns; }
    public boolean isPriority() { return isPriority; }

    @Override
    public String toString() {
        String prefix = isPriority ? "⚡ " : "";
        return prefix + name + " (" + portion + "), Spice: " + spiceLevel + ", Add-ons: " + addOns;
    }
}