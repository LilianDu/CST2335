package com.example.a29751.androidlabs;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.nio.channels.Channel;

/**
 * Created by 29751 on 2017-11-28.
 */

public class MessageFragment extends Fragment {

   /* public MessageFragment(ChatWindow cw) {
        if(cw!= null){
            //tablet
        }else{
            //phone
        }
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_message_details, container, false);
    }
}
