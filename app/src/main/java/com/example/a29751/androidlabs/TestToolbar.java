package com.example.a29751.androidlabs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    private String snackText = "Option 1 is selected";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, snackText, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu,m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){


        int id = mi.getItemId();
        switch(id){
            case R.id.action_one:

                Snackbar.make(findViewById(R.id.action_one), snackText, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Log.d("Toolbar",snackText);
                break;
            case R.id.action_two:
                snackText = "Option 2 is selected";
                //start an activity
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.dialogTitle)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();

                break;
            case R.id.action_three:

                AlertDialog.Builder obuilder = new AlertDialog.Builder(this);

                LayoutInflater li= getLayoutInflater();
                LinearLayout root = (LinearLayout)li.inflate(R.layout.customer_layout, null);
                final EditText et = (EditText)root.findViewById(R.id.customText);
                obuilder.setView(root);
                obuilder.setIcon(R.drawable.female);


                obuilder.setTitle(R.string.graTitle)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                snackText = getString(R.string.graTitle) + " "+ et.getText().toString();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                obuilder.create().show();
                break;
            case R.id.action_about:
                Toast toast = Toast.makeText(TestToolbar.this,"Version 1.0, by "+"Liangliang", Toast.LENGTH_SHORT);
                toast.show();
                break;
            default:
                break;
        }
        return true;
    }
}
