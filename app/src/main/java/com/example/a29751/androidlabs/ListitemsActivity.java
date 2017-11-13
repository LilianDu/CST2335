package com.example.a29751.androidlabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListitemsActivity extends AppCompatActivity {

    protected static final String ACTIVE_NAME = "ListitemsActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton iButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitems);
        Log.i(ACTIVE_NAME,"In onCreat()");

        iButton = (ImageButton) findViewById(R.id.imageButton);
        Switch switchB = (Switch) findViewById(R.id.switch1) ;
        CheckBox cBox = (CheckBox) findViewById(R.id.checkBox) ;

        iButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
              if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        switchB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    CharSequence text = getString(R.string.switch_on);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(ListitemsActivity.this,text,duration);
                    toast.show();
                }
                else{
                    CharSequence text = getString(R.string.switch_off);
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(ListitemsActivity.this,text,duration);
                    toast.show();
                }

            }
        });

        cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListitemsActivity.this);
                    builder.setMessage(R.string.checkbox_question)
                            .setTitle(R.string.checkbox_title)
                            .setPositiveButton(R.string.checkbox_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("Response", "Here is my response");
                                    setResult(ListitemsActivity.RESULT_OK, resultIntent);
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.checkbox_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
            }
        });

    }


@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            iButton.setImageBitmap(imageBitmap);
        }
    }

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
