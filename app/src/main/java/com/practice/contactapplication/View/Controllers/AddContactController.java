package com.practice.contactapplication.View.Controllers;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class AddContactController extends Controller implements ContactContract.View {
    private ContactContract.View view;
    @Inject
    ContactPresenter presenter;
    @Inject
    ContactRepository repository;
    UpdateContactCallback callback;

    public AddContactController() {
        // Required empty public constructor
    }

    public AddContactController(UpdateContactCallback callback) {
        this.callback = callback;
    }

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
        View rootView = inflater.inflate(R.layout.add_contact, container, false);

        EditText fullNameEditText = rootView.findViewById(R.id.full_name_edittext1);
        EditText phoneNumberEditText = rootView.findViewById(R.id.phone_number_edittext1);
        EditText emailEditText = rootView.findViewById(R.id.email_edittext1);
        EditText companyEditText = rootView.findViewById(R.id.company_edittext1);
        /*ImageView imageView = rootView.findViewById(R.id.contact_imageview1);*/
        Button addButton = rootView.findViewById(R.id.add_button);

        addButton.setOnClickListener(v -> {

            boolean isAllFieldsChecked = checkAllFields(fullNameEditText, phoneNumberEditText);
            if(isAllFieldsChecked){
                Contact contact = new Contact();
                contact.setFullName(fullNameEditText.getText().toString());
                contact.setPhoneNumber(phoneNumberEditText.getText().toString());
                contact.setEmail(emailEditText.getText().toString());
                contact.setCompany(companyEditText.getText().toString());

                // Call the presenter or repository method to add the contact
                presenter.addContact(contact);
                getRouter().popCurrentController();
            }
        });

        return rootView;
    }

    public boolean checkAllFields(EditText fullName, EditText phoneNumber) {
        if (fullName.getText().length() == 0) {
            fullName.setError("This field is required");
            return false;
        }
        if (phoneNumber.getText().length() == 0) {
            phoneNumber.setError("This field is required");
            return false;
        }
        if(phoneNumber.getText().length() < 8 || phoneNumber.getText().length() > 13) {
            phoneNumber.setError("Invalid phoneNumber");
            return false;
        }
        return true;
    }

    @Override
    public void showContactDetails(Contact contact) {}

    @Override
    public void showAddContactScreen() {}

    @Override
    public void showContactUpdateSuccess() {
        Toast.makeText(getApplicationContext(), "Contact updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showContactDeleteSuccess() {}

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}