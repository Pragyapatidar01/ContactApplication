package com.practice.contactapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.practice.contactapplication.ContactPresenter;
import com.practice.contactapplication.R;
import com.practice.contactapplication.View.Controllers.MainActivityController;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private Router router;
    @Inject
    ContactPresenter contactPresenter;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        router = Conductor.attachRouter(this, findViewById(R.id.controller_container), savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new MainActivityController()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactPresenter.onDestroy();
    }
}