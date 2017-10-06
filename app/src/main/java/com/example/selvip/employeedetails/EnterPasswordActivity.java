package com.example.selvip.employeedetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPasswordActivity extends AppCompatActivity {

    EditText mEtEnter2;
    Button mBtnConfirm2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);


        mEtEnter2 = (EditText) findViewById(R.id.etEnter2);
        mBtnConfirm2 = (Button) findViewById(R.id.btnConfirm2);

        mBtnConfirm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = mEtEnter2.getText().toString();

                if (text.equals("Kn0wledge@")) {
                    //enter the app
                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EnterPasswordActivity.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
