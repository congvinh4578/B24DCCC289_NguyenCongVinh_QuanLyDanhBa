package com.example.quanlydanhba;

import java.io.Serializable;
import java.util.Locale;

/**
 * Model class representing one contact.
 * Implements Serializable so a Contact can be passed between Activities
 * with Intent.putExtra(...).
 */
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fullName;
    private String phone;
    private String group;   // "Gia đình", "Bạn bè", "Công việc", "Khác"
    private String gender;  // "Nam", "Nữ", "Khác"
    private boolean isFavorite;
    private String notes;

    public Contact(String fullName, String phone, String group, String gender,
                   boolean isFavorite, String notes) {
        this.fullName = fullName;
        this.phone = phone;
        this.group = group;
        this.gender = gender;
        this.isFavorite = isFavorite;
        this.notes = notes;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /** First letter of the name in upper case, used as the avatar text. */
    public String getInitial() {
        if (fullName == null) {
            return "?";
        }
        String name = fullName.trim();
        if (name.isEmpty()) {
            return "?";
        }
        int end = name.offsetByCodePoints(0, 1);
        return name.substring(0, end).toUpperCase(Locale.ROOT);
    }
}
