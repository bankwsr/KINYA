package com.example.kinya;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    static final int GOOGLE_SIGN_IN = 123;
    FirebaseAuth firebaseAuth;

    //Toolbar toolbar;
    ProgressBar progressBar;
    TextView text;
    //ImageView imageGoogle;
    EditText userEmail, userPassword;
    Button userSignin, userSignup, userGoogle;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar = findViewById(R.id.toolbar2);
        //toolbar.setTitle("Login");
        progressBar = findViewById(R.id.progressBar);
        userEmail = findViewById(R.id.etUserEmail);
        userPassword = findViewById(R.id.etUserPassword);
        userSignin = findViewById(R.id.btnUserSignin);
        userSignup = findViewById(R.id.btnSignup);
        userGoogle = findViewById(R.id.btnGoogle);
        //imageGoogle = findViewById(R.id.profile);

        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        userGoogle.setOnClickListener(v -> SignInGoogle());

        if(firebaseAuth.getCurrentUser() != null){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            //updateUI(user);
        }

        userSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "ยินดีต้อนรับ", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                } else {
                                    Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        userSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisActivity.class));
            }
        });
    }

    public void SignInGoogle() {
        progressBar.setVisibility(View.VISIBLE);
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN_IN);
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG","firebaseAuthWithGoogle: " + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("TAG","Sign in Success");
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //updateUI(user);
            }else{
                progressBar.setVisibility(View.INVISIBLE);
                Log.w("TAG","Sign in Failure", task.getException());
                Toast.makeText(this,"SignIn Failed!", Toast.LENGTH_LONG);
                //updateUI(null);
            }
        });
    }
    
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
            }catch (ApiException e){
                Log.w("TAG", "Google Sign in Failed",e);
            }
    }
}
    /*private void updateUI(FirebaseUser user) {
        if(user != null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());

            text.append("Info : \n");
            text.append(name + "\n");
            text.append(email);

            Picasso.get().load(photo).into(imageGoogle);
        }else{

        }
    }*/
}