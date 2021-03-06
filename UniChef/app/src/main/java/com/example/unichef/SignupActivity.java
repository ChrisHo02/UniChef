package com.example.unichef;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Button signupButton = findViewById(R.id.create_button);
        signupButton.setOnClickListener(v -> {
            EditText textEmail = findViewById(R.id.editTextEmail);
            EditText textPassword = findViewById(R.id.editPassword);
            String email = textEmail.getText().toString().trim();
            String password = textPassword.getText().toString().trim();

            //Need checks for length etc.

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    //Store other information like username in database
                    Toast.makeText(SignupActivity.this, "Signed up", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(SignupActivity.this, "Error signing up", Toast.LENGTH_SHORT).show();
                }
            });
        });

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

}