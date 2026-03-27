package com.example.appbar_spinner_radio_checkbox_switch_seekbar_picker_listview_contextmenu_popupmenu;

import java.io.Serializable;

public class Registration implements Serializable {
    private String category;
    private String ticketType;
    private String slot;
    private int guestCount;
    private String addOns;

    public Registration(String category, String ticketType, String slot, int guestCount, String addOns) {
        this.category = category;
        this.ticketType = ticketType;
        this.slot = slot;
        this.guestCount = guestCount;
        this.addOns = addOns;
    }

    public String getCategory() { return category; }
    public String getTicketType() { return ticketType; }
    public String getSlot() { return slot; }
    public int getGuestCount() { return guestCount; }
    public String getAddOns() { return addOns; }

    @Override
    public String toString() {
        return category + " | " + ticketType + " | " + slot + " | Guests: " + guestCount + "\nAdd-ons: " + addOns;
    }
}
