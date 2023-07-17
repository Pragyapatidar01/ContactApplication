package com.practice.contactapplication.di;

import com.practice.contactapplication.View.Controllers.AddContactController;
import com.practice.contactapplication.View.Controllers.ContactDetailsController;
import com.practice.contactapplication.View.Controllers.MainActivityController;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContactModule.class})
public interface ContactComponent {

    void inject(MainActivityController controller);
    void inject(AddContactController addContactController);
    void inject(ContactDetailsController contactDetailsController);
}
