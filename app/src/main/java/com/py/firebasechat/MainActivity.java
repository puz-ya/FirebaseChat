package com.py.firebasechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseListAdapter;

public class MainActivity extends AppCompatActivity {

    public static int SIGN_IN_REQUEST = 1;
    public FirebaseListAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
