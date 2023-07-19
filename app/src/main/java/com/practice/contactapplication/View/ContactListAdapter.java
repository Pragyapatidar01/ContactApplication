package com.practice.contactapplication.View;


import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.practice.contactapplication.R;
import com.practice.contactapplication.models.Contact;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContactListAdapter extends BaseAdapter {

    private final Context context;
    private List<Contact> contacts;

    private final MainActivity mainActivity;

    public ContactListAdapter(Context context, Activity activity) {
        this.context = context;
        this.contacts = new ArrayList<>();
        mainActivity = (MainActivity) activity;
    }

    public void setContacts(List<Contact> contacts){
        this.contacts = contacts;
        Log.d(TAG, "setContacts: here in setContacts");
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.contacts_list_item, parent, false);
            holder = new ViewHolder();
            holder.fullNameTextView = convertView.findViewById(R.id.full_name);
            holder.phoneNumberTextView = convertView.findViewById(R.id.phone_number);
            holder.imageView = convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = contacts.get(position);
        holder.fullNameTextView.setText(contact.getFullName());
        holder.phoneNumberTextView.setText(contact.getPhoneNumber());
        Log.d(TAG, contact.getImageUri()+ "");


        Glide.with(Objects.requireNonNull(mainActivity))
                .load(contact.getImageUri())
                .into(holder.imageView);

        return convertView;
    }

    static class ViewHolder {
        TextView fullNameTextView;
        TextView phoneNumberTextView;
        ImageView imageView;
    }

}