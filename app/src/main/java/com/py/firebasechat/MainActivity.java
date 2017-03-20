package com.py.firebasechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.format.DateFormat;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static int SIGN_IN_REQUEST = 1;
    public FirebaseListAdapter<Message> adapter;

    //Views
    @BindView(R.id.activity_main)
    ConstraintLayout activity_main;
    @BindView(R.id.button_send)
    Button buttonSend;
    @BindView(R.id.editText)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get from editText, send to Firebase
                String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                FirebaseDatabase.getInstance().getReference().push()
                        .setValue(new Message(editText.getText().toString(), userEmail));
                //set empty
                editText.setText("");
            }
        });

        //authorization
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST);
        } else {
            //to logged users
            displayChat();
        }
    }

    public void displayChat(){

        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.item, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView message, author, time;
                message = (TextView) v.findViewById(R.id.tvMessage);
                author = (TextView) v.findViewById(R.id.tvUser);
                time = (TextView) v.findViewById(R.id.tvTime);

                message.setText(model.getMessageText());
                author.setText(model.getMessageAuthor());
                //import android.text.format.DateFormat !
                time.setText(DateFormat.format("dd-MM-yyyy (HH:mm)", model.getMessageTime()));
            }
        };

        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST){
            if(resultCode == RESULT_OK){
                Snackbar.make(activity_main, "Log in success", Snackbar.LENGTH_SHORT).show();
                displayChat();
            } else {
                Snackbar.make(activity_main, "Log in failed", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_logout){
            //log out current user
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Snackbar.make(activity_main, "Log out success", Snackbar.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }
        return true;
    }
}
