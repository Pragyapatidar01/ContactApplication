package com.practice.contactapplication;

import androidx.lifecycle.LiveData;

import com.practice.contactapplication.database.ContactDao;
import com.practice.contactapplication.database.ContactEntity;
import com.practice.contactapplication.models.Contact;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ContactRepository implements ContactContract.Repository {
    private final ContactDao contactDao;

    @Inject
    public ContactRepository(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    public LiveData<List<ContactEntity>> getAllContacts() {
        return contactDao.getAllContacts();
    }

    @Override
    public Completable addContact(Contact contact) {
        ContactEntity contactEntity = new ContactEntity(contact);
        return Completable.fromAction(() -> contactDao.insert(contactEntity));
    }

    @Override
    public Completable updateContact(Contact contact) {
        ContactEntity contactEntity = new ContactEntity(contact);
        return Completable.fromAction(() -> contactDao.update(contactEntity))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteContact(Contact contact) {
        ContactEntity contactEntity = new ContactEntity(contact);
        return Completable.fromAction(() -> contactDao.delete(contactEntity))
                .subscribeOn(Schedulers.io());
    }
}
