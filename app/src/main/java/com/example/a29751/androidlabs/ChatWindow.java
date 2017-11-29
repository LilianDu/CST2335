package com.example.a29751.androidlabs;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



public class ChatWindow extends AppCompatActivity {

    private String ACTIVITY_NAME = "ChatWindow";

    private ArrayList<String> sendList = new ArrayList<>();
    public ChatDatabaseHelper cdbHelper;
    public SQLiteDatabase db;
    public Cursor cur;
    public boolean isFrameExist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        ListView listView = (ListView)findViewById(R.id.chatView);
        final EditText edText = (EditText)findViewById(R.id.textSend);
        Button btSend = (Button)findViewById(R.id.sendButton);
       //Lab7
        isFrameExist = (FrameLayout)findViewById(R.id.frameL)!=null;

        if(isFrameExist){//tablet layout-at least 600 pixels

        }else{//phone layout

        }



        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
//database processing...
        Log.i("ChatWindow","newHelper");
        cdbHelper = new ChatDatabaseHelper(this);
        db = cdbHelper.getWritableDatabase();
        Log.i(ACTIVITY_NAME,"SQLiteDatabase");

        cur = db.rawQuery("select * from " + cdbHelper.TABLE_NAME,null);
        int column = cur.getCount();
        cur.moveToFirst();
        while(!cur.isAfterLast()){
            String message = cur.getString(cur.getColumnIndex(ChatDatabaseHelper.MESSAGE_HEADER));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + message);
            sendList.add(message);
            messageAdapter.notifyDataSetChanged();
            cur.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cur.getColumnCount());

        for(int i = 0; i <cur.getColumnCount();i++){
            System.out.println(cur.getColumnName(i)+ "-----");
        }

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendText = edText.getText().toString();

                sendList.add(sendText);
                messageAdapter.notifyDataSetChanged();

                ContentValues newData = new ContentValues();
                newData.put(cdbHelper.MESSAGE_HEADER, sendText);
                //Then insert
                db.insert(cdbHelper.TABLE_NAME,null , newData);

                edText.setText("");
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isFrameExist){//tablet layout-at least 600 pixels
                    Fragment newFragment = new Fragment();
                    Bundle args = new Bundle();
                    args.putString("message","newFragment");
                    args.putLong("Database id",id);
                    newFragment.setArguments(args);

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    //transaction.add(R.id.frameL,newFragment);
                    transaction.replace(R.id.frameL, newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                }else{//phone layout
                    startActivity(new Intent(ChatWindow.this,MessageDetails.class));
                }
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        cdbHelper.close();

    }

    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx){
            super(ctx,0);
        }

        public int getCount(){
            return sendList.size();
        }

        public String getItem(int position){
            return sendList.get(position);
        }

        public View getView(int position, View converView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            TextView message;
            if(position%2==0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                message = (TextView)result.findViewById(R.id.message_text2);
                result.setBackgroundResource(R.color.colorPrimary);
            }
            else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
                message = (TextView)result.findViewById(R.id.message_text);
                result.setBackgroundResource(R.color.colorAccent);
            }

            message.setText(getItem(position));

            return result;
        }

        public long getItemId(int position){
            cur.moveToPosition(position);
            return cur.getLong(cur.getColumnIndex(ChatDatabaseHelper.ID_HEADER));
        }
    }

}

