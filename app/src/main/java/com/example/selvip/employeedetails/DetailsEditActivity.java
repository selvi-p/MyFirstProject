package com.example.selvip.employeedetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DetailsEditActivity extends AppCompatActivity {

    private EditText mAdminEmpPass;
    private EditText mAdminEmpEmail;
    private Button mProceed;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_edit);

        mAdminEmpEmail = (EditText) findViewById(R.id.empEmail);
        mAdminEmpPass = (EditText) findViewById(R.id.empPass);
        mProceed = (Button) findViewById(R.id.proceedEdit);
        mProgress = new ProgressDialog(DetailsEditActivity.this);

        mAuth = FirebaseAuth.getInstance();

        mProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mAdminEmpEmail.getText().toString();
                String pattern = "^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+$";
                String passwordLogin = mAdminEmpPass.getText().toString();

                if (mAdminEmpEmail.getText().length() == 0) {
                    mAdminEmpEmail.setError("Field cannot be empty");
                } else if (!email.matches(pattern)) {
                    Toast.makeText(DetailsEditActivity.this, "Email ID is invalid", Toast.LENGTH_SHORT).show();
                } else if (mAdminEmpPass.getText().length() == 0) {
                    mAdminEmpPass.setError("Field cannot be empty");
                } else {

                    mProgress.setMessage("Please wait...");
                    mProgress.setCancelable(false);
                    mProgress.setInverseBackgroundForced(false);
                    mProgress.show();

                    mAuth.signInWithEmailAndPassword(email, passwordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgress.dismiss();

                            if (task.isSuccessful()) {
                                Intent intent = new Intent(DetailsEditActivity.this, UserDetailEditActivity.class);
                                intent.putExtra("Email", mAuth.getCurrentUser().getEmail());
                                startActivity(intent);
                                finish();
                            }


                            if (!task.isSuccessful()) {
                                Toast.makeText(DetailsEditActivity.this.getApplicationContext(), "Authentication failed!", Toast.LENGTH_LONG).show();
                                mProgress.dismiss();

                            }
                        }

                    });
                }
            }

        });
    }

}
