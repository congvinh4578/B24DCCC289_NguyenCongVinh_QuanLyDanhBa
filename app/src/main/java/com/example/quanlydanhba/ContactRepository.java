package com.example.quanlydanhba;

import java.util.ArrayList;

/**
 * Singleton that owns the one shared ArrayList<Contact>.
 *
 * Every Activity calls ContactRepository.getInstance() and therefore sees the
 * same list. The list lives as long as the app process, so state is kept while
 * the app runs (it is reset when the process is killed - no database needed).
 */
public class ContactRepository {

    private static ContactRepository instance;

    private final ArrayList<Contact> contacts = new ArrayList<>();

    private ContactRepository() {
        // Initial sample data
        contacts.add(new Contact("Nguyễn Văn An", "0901234567", "Bạn bè", "Nam", false, ""));
        contacts.add(new Contact("Trần Thị Bình", "0912345678", "Gia đình", "Nữ", true, ""));
        contacts.add(new Contact("Lê Minh Châu", "0987654321", "Công việc", "Nữ", false, ""));
    }

    public static synchronized ContactRepository getInstance() {
        if (instance == null) {
            instance = new ContactRepository();
        }
        return instance;
    }

    /** The live list. The ListView adapter holds a reference to this same object. */
    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void add(Contact contact) {
        contacts.add(contact);
    }

    public boolean remove(Contact contact) {
        return contacts.remove(contact);
    }

    public boolean phoneExists(String phone) {
        return findByPhone(phone) != null;
    }

    /** The phone number is unique, so it identifies a contact. */
    public Contact findByPhone(String phone) {
        for (Contact c : contacts) {
            if (c.getPhone().equals(phone)) {
                return c;
            }
        }
        return null;
    }

    public int getTotalCount() {
        return contacts.size();
    }

    public int getFavoriteCount() {
        int count = 0;
        for (Contact c : contacts) {
            if (c.isFavorite()) {
                count++;
            }
        }
        return count;
    }
}
