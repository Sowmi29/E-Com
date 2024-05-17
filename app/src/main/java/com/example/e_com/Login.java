package com.example.e_com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText mail,pwd;
    String mailstr,pwdstr;
    Button login;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent i = new Intent(getApplicationContext(), Homescreen.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail=findViewById(R.id.mailet);
        pwd=findViewById(R.id.pwdet);
        login=findViewById(R.id.loginbtn);
        mAuth=FirebaseAuth.getInstance();


        // On clicking sign up textView , signup activity will be opened
        TextView textView = findViewById(R.id.signupBtn);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });


        // Login process
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailstr=mail.getText().toString();
                pwdstr=pwd.getText().toString();

                if(TextUtils.isEmpty(mailstr)){
                    return;
                }
                if(TextUtils.isEmpty(pwdstr)){
                    return;
                }
                mAuth.signInWithEmailAndPassword(mailstr, pwdstr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Authentication Success", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Login.this,Homescreen.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
    }
}