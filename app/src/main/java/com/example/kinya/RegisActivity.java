package com.example.kinya;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisActivity extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progressBar;
    EditText email, password;
    Button signup, signin;

    TextView textView;
    Handler handler = new Handler();
    private int progressStatus = 0;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_regis);

        //toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);

        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        signup = findViewById(R.id.btnSignup);
        signin = findViewById(R.id.btnSignin);

        //toolbar.setTitle(R.string.app_name);

        firebaseAuth = firebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                new Thread(new Runnable() {
                                    public void run() {
                                        while (progressStatus < 100) {
                                            progressStatus += 1;
                                            // Update the progress bar and display the
                                            //current value in the text view
                                            handler.post(new Runnable() {
                                                public void run() {
                                                    progressBar.setProgress(progressStatus);
                                                    textView.setText(progressStatus+"/"+progressBar.getMax());
                                                }
                                            });
                                            try {
                                                // Sleep for 200 milliseconds.
                                                Thread.sleep(200);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }).start();


                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisActivity.this, "Registered!!", Toast.LENGTH_LONG).show();
                                    email.setText("");
                                    password.setText("");
                                } else {
                                    Toast.makeText(RegisActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisActivity.this, MainActivity.class));
            }
        });
    }
}