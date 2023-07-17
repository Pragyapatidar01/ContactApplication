package com.practice.contactapplication.di;

import android.content.Context;

import com.practice.contactapplication.ContactContract;
import com.practice.contactapplication.ContactPresenter;
import com.practice.contactapplication.ContactRepository;
import com.practice.contactapplication.database.ContactDao;
import com.practice.contactapplication.database.ContactDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactModule {
    private final ContactContract.View view;
    private final Context context;

    public ContactModule(ContactContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Provides
    ContactContract.View provideView() {
        return view;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    ContactContract.Presenter providePresenter(ContactContract.View view, ContactContract.Repository repository) {
        return new ContactPresenter(view, repository);
    }

    @Singleton
    @Provides
    ContactContract.Repository provideRepository(ContactDao contactDao) {
        return new ContactRepository(contactDao);
    }

    @Provides
    @Singleton
    public ContactDao provideContactDao(ContactDatabase contactDatabase) {
        return contactDatabase.contactDao();
    }

    @Provides
    @Singleton
    public ContactDatabase provideContactDatabase(Context context) {
        return ContactDatabase.getInstance(context);
    }
}