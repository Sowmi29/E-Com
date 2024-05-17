package com.example.e_com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class SignUp extends AppCompatActivity {
   Button subtn;
   TextView loginBtn;
   EditText email,pwd;
   String emailStr,pwdStr;
    FirebaseAuth mAuth;
  /* @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i=new Intent(getApplicationContext(), Homescreen.class);
            startActivity(i);
            finish();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loginBtn=findViewById(R.id.signupBtn);
        subtn=findViewById(R.id.loginbtn);
        email=findViewById(R.id.mailet);
        pwd=findViewById(R.id.pwdet);
        mAuth=FirebaseAuth.getInstance();

       loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(getApplicationContext(), Login.class);
               startActivity(i);
               finish();
           }
       });

        subtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailStr=email.getText().toString();
                pwdStr=pwd.getText().toString();


                if(TextUtils.isEmpty(emailStr)){
                    return;
                }
                if(TextUtils.isEmpty(pwdStr)){
                    return;
                }


                mAuth.createUserWithEmailAndPassword(emailStr, pwdStr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignUp.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();
                                    // Opens GetUserDetails Activity
                                    Intent i=new Intent(SignUp.this,GetUserDetails.class);
                                    i.putExtra("EXTRA_KEY", emailStr);
                                    startActivity(i);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });



            }
        });



    }

}