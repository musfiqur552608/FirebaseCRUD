package com.example.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, password, conPassword;
    private TextView toLoginBtn;
    private Button registerBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.userEtxt);
        password = findViewById(R.id.passEtxt);
        conPassword = findViewById(R.id.conPassEtxt);
        toLoginBtn = findViewById(R.id.toLoginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        progressBar = findViewById(R.id.progessBar);

        mAuth = FirebaseAuth.getInstance();

        toLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String userName = username.getText().toString();
                String passWord = password.getText().toString();
                String conPassWord = conPassword.getText().toString();

                if(userName.isEmpty() && passWord.isEmpty() && conPassWord.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please, Fill up all the information", Toast.LENGTH_SHORT).show();
                }else{
                    if(!passWord.equals(conPassWord)){
                        Toast.makeText(RegisterActivity.this, "Confirm password Didn't matched", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        mAuth.createUserWithEmailAndPassword(userName, conPassWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this, "Register Succesfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    finish();
                                }else{
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

            }
        });



    }
}