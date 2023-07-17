package com.practice.contactapplication.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.practice.contactapplication.models.Contact;

@Entity(tableName = "contacts")
public class ContactEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String fullName;
    public String phoneNumber;
    public String email;
    public String company;
    public String imageUri;

    public ContactEntity(Contact contact) {
        this.id = contact.getId();
        this.fullName = contact.getFullName();
        this.phoneNumber = contact.getPhoneNumber();
        this.email = contact.getEmail();
        this.company = contact.getCompany();
        this.imageUri = contact.getImageUri();
    }
    public ContactEntity(String fullName, String phoneNumber, String email, String company, String imageUri) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.company = company;
        this.imageUri = imageUri;
    }
}
