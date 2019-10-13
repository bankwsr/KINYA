package com.example.kinya;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;

public class ProfileFragment extends Fragment {

    private TextView mTextMessage;
    TextView text;
    Button userLogout;
    ImageView image;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    GoogleSignInClient mGoogleSignInClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView mTextMessage = (TextView) v.findViewById(R.id.userEmail);
        TextView userEmail = (TextView) v.findViewById(R.id.userEmail);
        Button userLogout = (Button) v.findViewById(R.id.userLogout);
        ImageView profile_pic = (ImageView) v.findViewById(R.id.imageProfile);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        userEmail.setText(firebaseUser.getDisplayName());
        profile_pic.setImageURI(firebaseUser.getPhotoUrl().);
        userLogout.setOnClickListener(view ->Logout());

        return v;
    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        //mGoogleSignInClient.signOut().addOnCompleteListener((Executor) this, task -> updateUI(null));
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl().toString());

            text.append("Info : \n");
            text.append(name + "\n");
            text.append(email);
            //Picasso.with(ProfileFragment.this).load(photo).into(image);
        } else {
            text.setText("Firebase Login \n");
            //Picasso.with(ProfileFragment.this).load(R.drawable.ic_account_circle_black_24dp).into(image);
            //btn_logout.setVisibility(View.INVISIBLE);
            //btn_login.setVisibility(View.VISIBLE);
        }
    }
}