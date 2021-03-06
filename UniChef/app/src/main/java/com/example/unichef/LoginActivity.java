package com.example.unichef;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        Button signupButton = findViewById(R.id.signup_button);
        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            EditText textEmail = findViewById(R.id.editTextEmail);
            EditText textPassword = findViewById(R.id.editPassword);
            String email = textEmail.getText().toString().trim();
            String password = textPassword.getText().toString().trim();

            //Need checks for length etc.

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, "Error signing in", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
