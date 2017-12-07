package com.example.a29751.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.nio.channels.Channel;

/**
 * Created by 29751 on 2017-11-28.
 */

public class MessageFragment extends Fragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      //  super.onViewCreated(view, savedInstanceState);

        Bundle info = getArguments();
        String message = info.getString("message");
        final long id = info.getLong("ID");
        final boolean isTablet = info.getBoolean("Boolean");
        TextView  messageText=(TextView)view.findViewById(R.id.textMessage) ;
        TextView idText=(TextView)view.findViewById(R.id.textID) ;

        ChatDatabaseHelper cdbHelper = new ChatDatabaseHelper(getActivity());
        final SQLiteDatabase db = cdbHelper.getWritableDatabase();

        messageText.setText(messageText.getText().toString()+ message);
        idText.setText(idText.getText().toString()+id);
        Button delBtn = (Button) view.findViewById(R.id.delButton);
        delBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isTablet){
                    db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.ID_HEADER + "=" + id, null);
                    getActivity().finish();
                    Intent intent = getActivity().getIntent();
                    startActivity(intent);
                }
                else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("Delete", id);
                    getActivity().setResult(Activity.RESULT_OK, resultIntent);
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_layout, container, false);




    }
}
