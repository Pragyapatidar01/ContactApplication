package com.practice.contactapplication;

import androidx.lifecycle.LiveData;

import com.practice.contactapplication.database.ContactEntity;
import com.practice.contactapplication.models.Contact;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public interface ContactContract {
    interface View {
        void showContactDetails(Contact contact);
        void showAddContactScreen();
        void showContactUpdateSuccess();
        void showContactDeleteSuccess();
        void showError(String message);
    }

    interface Presenter {
        void addContact(Contact contact);
        void updateContact(Contact contact);
        void deleteContact(Contact contact);
        void onContactClicked(Contact contact);
        void onAddButtonClicked();
        void onDestroy();
    }

    interface Repository {
        LiveData<List<ContactEntity>> getAllContacts();
        Completable addContact(Contact contact);
        Completable updateContact(Contact contact);
        Completable deleteContact(Contact contact);
    }
}
