package com.example.selvip.employeedetails;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SentActivity extends AppCompatActivity {

    private ListView mSentMessages;

    private ArrayList<String> mMessages = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);
        mSentMessages = (ListView) findViewById(R.id.sentMessages);

        arrayAdapter = new ArrayAdapter<String>(SentActivity.this, android.R.layout.simple_list_item_1, mMessages);
        mSentMessages.setAdapter(arrayAdapter);

        rootRef = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String UserId = user.getUid();

                rootRef.child("User Info").child("Data").child(UserId).child("Messages").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        String value = dataSnapshot.getValue(String.class);
                        mMessages.add(value);
                        arrayAdapter.insert(value, 0);
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
    }
}
