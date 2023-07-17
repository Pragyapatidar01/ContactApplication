package com.practice.contactapplication.models;

import com.practice.contactapplication.database.ContactEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Contact {
    private int id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String company;
    private String imageUri;

    // Constructor, getters, and setters

    @Inject
    public Contact(){
    }

    public Contact(String fullName, String phoneNumber, String email, String company, String imageUri) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.company = company;
        this.imageUri = imageUri;
    }

    public Contact(ContactEntity contactEntity) {
        this.id = contactEntity.id;
        this.fullName = contactEntity.fullName;
        this.phoneNumber = contactEntity.phoneNumber;
        this.email = contactEntity.email;
        this.company = contactEntity.company;
        this.imageUri = contactEntity.imageUri;
    }

    public static List<Contact> mapToContactList(List<ContactEntity> contactList) {
        List<Contact> contacts = new ArrayList<>();
        for (ContactEntity contactEntity : contactList) {
            contacts.add(new Contact(contactEntity));
        }
        return contacts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}