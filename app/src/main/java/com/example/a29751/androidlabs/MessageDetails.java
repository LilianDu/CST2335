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
    TextView messageText;
    TextView idText;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                // Get result from the result intent.
                String mes = data.getStringExtra("message");
                String id = data.getStringExtra("ID");
                messageText.setText(mes);
                idText.setText(id);
                // Do something with result...
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        MessageFragment newFragment = new MessageFragment(null);
        newFragment.setArguments(savedInstanceState);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(newFragment,"222");

        transaction.addToBackStack(null);
        messageText=(TextView)findViewById(R.id.textMessage) ;
        idText=(TextView)findViewById(R.id.textID) ;


        transaction.commit();
        Button delBtn = (Button) findViewById(R.id.delButton);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   getActivity().setResult(int resultCode, Intent data)

            }
        });
    }
}
