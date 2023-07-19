package com.practice.contactapplication.View.Controllers;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.practice.contactapplication.View.UpdateContactCallback;
import com.practice.contactapplication.databinding.AddContactBinding;
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

    Contact contact = new Contact();

    AddContactBinding addContactBinding;
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
        addContactBinding = AddContactBinding.inflate(inflater, container, false);
        View rootView = addContactBinding.getRoot();

        EditText fullNameEditText = addContactBinding.fullNameEdittext1;
        EditText phoneNumberEditText = addContactBinding.phoneNumberEdittext1;
        EditText emailEditText = addContactBinding.emailEdittext1;
        EditText companyEditText = addContactBinding.companyEdittext1;
        Button selectImageButton = addContactBinding.selectImageBtn;
        Button addButton = addContactBinding.addButton;


        selectImageButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        addButton.setOnClickListener(v -> {

            boolean isAllFieldsChecked = checkAllFields(fullNameEditText, phoneNumberEditText);
            if(isAllFieldsChecked){

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            ImageView imageView = addContactBinding.contactImageview1;
            imageView.setImageURI(selectedImageUri);

            String imageUrl = selectedImageUri.toString();
            contact.setImageUri(imageUrl);
        }
    }

    private static final int PICK_IMAGE_REQUEST = 1;
}