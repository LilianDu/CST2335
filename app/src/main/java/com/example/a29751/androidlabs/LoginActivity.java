package com.example.a29751.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    protected static final String ACTIVE_NAME = "LoginActivity";
    private SharedPreferences sharedPref;
    private EditText emailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVE_NAME,"In onCreat()");

        Button myButton = (Button)findViewById(R.id.login);
        emailText= (EditText)findViewById(R.id.emailEdit);

        sharedPref = this.getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String login_name = sharedPref.getString("DefaultEmail","email@domain.com");
        emailText.setText(login_name);
        myButton.setOnClickListener(btnListener);

    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("DefaultEmail",emailText.getText().toString());
            editor.commit();

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVE_NAME,"In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVE_NAME,"In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVE_NAME,"In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVE_NAME,"In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVE_NAME,"In onDestroy()");
    }
}
