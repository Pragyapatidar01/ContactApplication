package com.practice.contactapplication;

import com.practice.contactapplication.View.Controllers.MainActivityController;
import com.practice.contactapplication.models.Contact;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ContactPresenter implements ContactContract.Presenter {
    private final ContactContract.View view;
    private final ContactContract.Repository repository;

    @Inject
    Contact contact;

    final private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public ContactPresenter(ContactContract.View view, ContactContract.Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void addContact(Contact contact) {
        disposable.add(repository.addContact(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::showContactUpdateSuccess,
                        error -> view.showError(error.getMessage())
                ));
    }

    @Override
    public void updateContact(Contact contact) {
        disposable.add(repository.updateContact(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::showContactUpdateSuccess,
                        error -> view.showError(error.getMessage())
                ));
    }

    @Override
    public void deleteContact(Contact contact) {
        disposable.add(repository.deleteContact(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::showContactDeleteSuccess,
                        error -> view.showError(error.getMessage())
                ));
    }

    @Override
    public void onContactClicked(Contact contact) {
        view.showContactDetails(contact);
    }

    @Override
    public void onAddButtonClicked() {
        view.showAddContactScreen();
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
    }
}