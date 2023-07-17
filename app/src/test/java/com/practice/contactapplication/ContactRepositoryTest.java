package com.practice.contactapplication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;

import com.practice.contactapplication.database.ContactDao;
import com.practice.contactapplication.database.ContactEntity;
import com.practice.contactapplication.models.Contact;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Completable;

public class ContactRepositoryTest {
    @Mock
    private ContactDao mockContactDao;

    private ContactRepository contactRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        contactRepository = new ContactRepository(mockContactDao);
    }

    @Test
    public void getAllContacts_returnsLiveData() {

        // Mock the contactDao's getAllContacts method to return the expectedContacts
        when(mockContactDao.getAllContacts()).thenReturn(Mockito.mock(LiveData.class));

        // Call the method under test
        LiveData<List<ContactEntity>> result = contactRepository.getAllContacts();

        // Verify that the contactDao's getAllContacts method is called
        Mockito.verify(mockContactDao).getAllContacts();

    }

    @Test
    public void addContact_completesSuccessfully() {
        Contact contact = new Contact("John", "67786877"," ","" ,"");

        // Mocking a method that returns void
        Mockito.doNothing().when(mockContactDao).insert(any());

        // Call the method under test
        Completable result = contactRepository.addContact(contact);

        // Verify that the result completes successfully
        result.test().awaitDone(5, TimeUnit.SECONDS).assertComplete();
    }

    @Test
    public void updateContact_completesSuccessfully() {
        Contact contact = new Contact("John", "67786877","jnjk","" ,"");
        Mockito.doNothing().when(mockContactDao).update(any());

        // Call the method under test
        Completable result = contactRepository.updateContact(contact);

        // Verify that the result completes successfully
        result.test().awaitDone(5, TimeUnit.SECONDS).assertComplete();
    }

    @Test
    public void deleteContact_completesSuccessfully() {
        Contact contact = new Contact("John", "67786877","jnjk","" ,"");
        Mockito.doNothing().when(mockContactDao).delete(any());

        // Call the method under test
        Completable result = contactRepository.deleteContact(contact);

        // Verify that the result completes successfully
        result.test().awaitDone(5, TimeUnit.SECONDS).assertComplete();
    }
}

