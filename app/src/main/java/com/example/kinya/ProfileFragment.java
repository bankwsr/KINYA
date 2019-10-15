package com.example.kinya;

import android.content.Intent;
import android.net.Uri;
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
    TextView text,userName,userEmail;
    Button userLogout;
    ImageView image,profile_pic;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    GoogleSignInClient mGoogleSignInClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView mTextMessage = v.findViewById(R.id.userEmail);
        TextView userEmail = v.findViewById(R.id.userEmail);
        TextView userName = v.findViewById(R.id.userName);
        Button userLogout = v.findViewById(R.id.userLogout);
        ImageView profile_pic = v.findViewById(R.id.imageProfile);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        userName.setText(firebaseUser.getDisplayName());
        userEmail.setText(firebaseUser.getEmail());
        Picasso.with(getActivity()).load(firebaseUser.getPhotoUrl()).into(profile_pic);
        userLogout.setOnClickListener(view ->Logout());

        return v;
    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        firebaseAuth.signOut();
        //mGoogleSignInClient.signOut().addOnCompleteListener((Executor) this, task -> updateUI(null));
    }
}