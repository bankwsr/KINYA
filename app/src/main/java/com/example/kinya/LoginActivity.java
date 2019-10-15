package com.example.kinya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText userEmail, userPassword;
    Button userSignin, userSignup, userGoogle;

    private FirebaseAuth mAuth;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
/*
        mAuth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.etUserEmail);
        userPassword = findViewById(R.id.etUserPassword);
        userSignin = findViewById(R.id.btnUserSignin);
        userSignup = findViewById(R.id.btnSignup);
        userGoogle = findViewById(R.id.btnGoogle);

        userSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    userEmail.setError("Invalid Email");
                    userEmail.setFocusable(true);
                }else{
                    loginUser(email,password);
                }
            }
        });
    }

    pd = new ProgressDialog(this);
    pd.setMessage("Loging In...");

    private void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            finish();
                        } else {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener()){
            @Override
            public void onFailure(@NonNull Exception e){
                Toast.makeText(LoginActivity.this,""+e.+getMessage(),
                        Toast.LENGTH_SHORT.show());
            }
        }
    }

    @Override
    public boolean onSupportnavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();*/
    }
}
