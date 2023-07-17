package com.practice.contactapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contacts ORDER BY fullName ASC")
    LiveData<List<ContactEntity>> getAllContacts();

    @Upsert
    void insert(ContactEntity contactEntity);

    @Update
    void update(ContactEntity contactEntity);

    @Delete
    void delete(ContactEntity contactEntity);

}

