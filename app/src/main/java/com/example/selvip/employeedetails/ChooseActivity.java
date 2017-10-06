package com.example.selvip.employeedetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity {

    private Button mEmployeeBtn, mAdminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        mEmployeeBtn = (Button) findViewById(R.id.employeeBtn);
        mAdminBtn = (Button) findViewById(R.id.adminBtn);


        mEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        mAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivity.this, EnterPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
