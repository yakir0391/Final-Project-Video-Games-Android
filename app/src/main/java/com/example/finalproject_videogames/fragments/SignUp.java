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
import com.example.finalproject_videogames.models.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends Fragment {

    private FirebaseAuth mAuth;

    public SignUp() {}

    public static SignUp newInstance(String param1, String param2) {
        SignUp fragment = new SignUp();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    public void addData(View view) {

        EditText phoneT = view.findViewById(R.id.registerPhoneNumber);
        EditText emailT = view.findViewById(R.id.registerUserEmailAddress);

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        ((MainActivity)getActivity()).uid = uid;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(uid);

        Person p = new Person(phoneT.getText().toString(), emailT.getText().toString());
        myRef.setValue(p);
    }

    public void signUp(View view) {
        EditText emailT = view.findViewById( R.id.registerUserMail);
        EditText passT = view.findViewById(R.id.registerUserPassword);
        String email = emailT.getText().toString();
        String password = passT.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getContext() , "Sign up ok" , Toast.LENGTH_LONG).show();
                            addData(view);
                            Navigation.findNavController(view).navigate(R.id.action_signUp_to_game_list);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext() , "Sign up failed" , Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Button signupBtn = (Button) fragView.findViewById(R.id.registerSignUp);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(fragView);
            }
        });

        return fragView;
    }
}