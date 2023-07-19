package com.practice.contactapplication.View.Controllers;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.practice.contactapplication.ContactContract;
import com.practice.contactapplication.ContactPresenter;
import com.practice.contactapplication.ContactRepository;
import com.practice.contactapplication.R;
import com.practice.contactapplication.View.ContactListAdapter;
import com.practice.contactapplication.View.UpdateContactCallback;
import com.practice.contactapplication.database.ContactDao;
import com.practice.contactapplication.database.ContactDatabase;
import com.practice.contactapplication.database.ContactEntity;
import com.practice.contactapplication.databinding.ActivityMainBinding;
import com.practice.contactapplication.databinding.ContactDetailsBinding;
import com.practice.contactapplication.di.ContactComponent;
import com.practice.contactapplication.di.ContactModule;
import com.practice.contactapplication.di.DaggerContactComponent;
import com.practice.contactapplication.models.Contact;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class MainActivityController extends Controller implements ContactContract.View {

    @Inject
    ContactPresenter presenter;

    @Inject
    ContactRepository repository;

    private ContactListAdapter contactListAdapter;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onContextAvailable(@NonNull Context context) {
        super.onContextAvailable(context);
        DaggerContactComponent.builder()
                .contactModule(new ContactModule(this, context))
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedViewState) {

        activityMainBinding = ActivityMainBinding.inflate(inflater, container, false);
        View rootView = activityMainBinding.getRoot();

        ListView contactListView = activityMainBinding.contactList;

        contactListAdapter = new ContactListAdapter(inflater.getContext(), this.getActivity());
        contactListView.setAdapter(contactListAdapter);
        contactListView.setOnItemClickListener((parent, view, position, id) -> {
            Contact contact = (Contact) contactListView.getItemAtPosition(position);
            presenter.onContactClicked(contact);
        });

        Button addButton = activityMainBinding.addButton;
        addButton.setOnClickListener(v -> presenter.onAddButtonClicked());

        return rootView;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        ContactComponent contactComponent = DaggerContactComponent.builder()
                .contactModule(new ContactModule(this, getApplicationContext()))
                .build();
        contactComponent.inject(this);

        loadContacts();
    }

    @Override
    public void showContactDetails(Contact contact) {
        // Open the contact details page and pass the contact data
        ContactDetailsController detailsController = new ContactDetailsController(contact, new AddContactController(), new UpdateContactCallback() {
            @Override
            public void updateContactCallback(List<Contact> contacts) {
                showContacts(contacts);
            }
        });

        Router router = getRouter();
        router.pushController(RouterTransaction.with(detailsController)
                .pushChangeHandler(new FadeChangeHandler())
                .popChangeHandler(new FadeChangeHandler()));

    }

    public void showContacts(List<Contact> contacts) {
        Log.d(TAG, "showContacts: ");
        contactListAdapter.setContacts(contacts);
    }

    @Override
    public void showAddContactScreen() {
        // Open the add contact screen
        AddContactController addController = new AddContactController(new UpdateContactCallback() {
            @Override
            public void updateContactCallback(List<Contact> contacts) {
                showContacts(contacts);
            }
        });
        Router router = getRouter();
        router.pushController(RouterTransaction.with(addController)
                .pushChangeHandler(new FadeChangeHandler())
                .popChangeHandler(new FadeChangeHandler()));
    }

    @Override
    public void showContactUpdateSuccess() {
        Toast.makeText(getApplicationContext(), "Contact updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showContactDeleteSuccess() {
        Toast.makeText(getApplicationContext(), "Contact deleted successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    public void loadContacts() {
        repository.getAllContacts().observe((LifecycleOwner) Objects.requireNonNull(this.getActivity()), new Observer<List<ContactEntity>>() {
            @Override
            public void onChanged(List<ContactEntity> contacts) {
                Log.d(TAG, contacts.size() + "");
                List<Contact> contactList = Contact.mapToContactList(contacts);
                showContacts(contactList);
            }
        });
    }
}