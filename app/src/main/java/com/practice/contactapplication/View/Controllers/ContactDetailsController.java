package com.practice.contactapplication.View.Controllers;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bluelinelabs.conductor.Controller;
import com.practice.contactapplication.ContactContract;
import com.practice.contactapplication.ContactPresenter;
import com.practice.contactapplication.ContactRepository;
import com.practice.contactapplication.R;
import com.practice.contactapplication.View.UpdateContactCallback;
import com.practice.contactapplication.di.ContactModule;
import com.practice.contactapplication.di.DaggerContactComponent;
import com.practice.contactapplication.models.Contact;

import javax.inject.Inject;

public class ContactDetailsController extends Controller implements ContactContract.View{
    private Contact contact;
    private AddContactController addContactController;
    @Inject
    ContactPresenter presenter;
    @Inject
    ContactRepository repository;

    UpdateContactCallback callback;

    public ContactDetailsController() {
        // Required empty public constructor
    }

    @Override
    protected void onContextAvailable(@NonNull Context context) {
        super.onContextAvailable(context);

        DaggerContactComponent.builder()
                .contactModule(new ContactModule(this, context))
                .build()
                .inject(this);
    }
    public ContactDetailsController(Contact contact, AddContactController addContactController, UpdateContactCallback callback) {
        this.addContactController = addContactController;
        this.callback = callback;
        this.contact = contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedViewState) {
        View rootView = inflater.inflate(R.layout.contact_details, container, false);

        EditText fullNameEditText = rootView.findViewById(R.id.full_name_edittext);
        EditText phoneNumberEditText = rootView.findViewById(R.id.phone_number_edittext);
        EditText emailEditText = rootView.findViewById(R.id.email_edittext);
        EditText companyEditText = rootView.findViewById(R.id.company_edittext);
        Button updateButton = rootView.findViewById(R.id.update_button);
        Button deleteButton = rootView.findViewById(R.id.delete_button);

        fullNameEditText.setText(contact.getFullName());
        phoneNumberEditText.setText(contact.getPhoneNumber());
        emailEditText.setText(contact.getEmail());
        companyEditText.setText(contact.getCompany());

        updateButton.setOnClickListener(v -> {

            boolean isAllFieldsChecked = addContactController.checkAllFields(fullNameEditText, phoneNumberEditText);
            if(isAllFieldsChecked){
                contact.setFullName(fullNameEditText.getText().toString());
                contact.setPhoneNumber(phoneNumberEditText.getText().toString());
                contact.setEmail(emailEditText.getText().toString());
                contact.setCompany(companyEditText.getText().toString());

                presenter.updateContact(contact);
                getRouter().popCurrentController();
            }
        });

        deleteButton.setOnClickListener(v-> {
            presenter.deleteContact(contact);
            getRouter().popCurrentController();
        });

        return rootView;
    }

    @Override
    public void showContactDetails(Contact contact) {}

    @Override
    public void showAddContactScreen() {}

    @Override
    public void showContactUpdateSuccess() {
        Toast.makeText(getApplicationContext(),"Contact updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showContactDeleteSuccess() {
        Toast.makeText(getApplicationContext(), "Contact deleted successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

}
