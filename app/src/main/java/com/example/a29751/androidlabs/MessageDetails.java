package com.example.a29751.androidlabs;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle info = getIntent().getExtras();

        MessageFragment newFragment = new MessageFragment();
        newFragment.setArguments(info);
        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        transaction.add(R.id.phone_frameLayout,newFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }
}
