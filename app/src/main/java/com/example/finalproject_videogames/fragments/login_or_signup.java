package com.example.finalproject_videogames.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject_videogames.R;
import com.example.finalproject_videogames.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class login_or_signup extends Fragment {

    private FirebaseAuth mAuth;

    public login_or_signup() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    public void login (View view) {

        EditText emailT = view.findViewById(R.id.userMail);
        EditText passT = view.findViewById(R.id.password);
        String email = emailT.getText().toString();
        String password = passT.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getContext() , "login ok" , Toast.LENGTH_LONG).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            ((MainActivity)getActivity()).uid = uid;

                            Navigation.findNavController(view).navigate(R.id.action_login_or_signup_to_game_list);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext() , "login fail" , Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_login_or_signup, container, false);
        Button loginBtn = (Button) fragView.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(fragView);
            }
        });

        Button signUpBtn = (Button) fragView.findViewById(R.id.signupBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(fragView).navigate(R.id.action_login_or_signup_to_signUp);
            }
        });

        return fragView;
    }
}