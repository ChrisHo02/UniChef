package com.example.unichef;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.concurrent.atomic.AtomicBoolean;

public class SignupActivity extends AppCompatActivity {
    public Button register;
    public EditText email;
    public EditText username;
    public EditText password;
    public EditText confirmPassword;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        register = findViewById(R.id.create_button);
        email = findViewById(R.id.editTextEmail);
        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);
        confirmPassword = findViewById(R.id.confirmPassword);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(v -> {
            if (checkValidData()){
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        try{
                            throw task.getException();
                        }catch (FirebaseAuthUserCollisionException e){
                            email.setError("Email already in use!");
                        }catch (Exception e){
                            Toast.makeText(SignupActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(this, "Check your info.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkValidData(){
        boolean valid = true;
        if (!isEmail(email)){
            email.setError("Enter valid email!");
            valid = false;
        }
        if (isShort(username)){
            username.setError("Username is too short!");
            valid = false;
        }
        if(usernameExists(username)){
            username.setError("Username already exists!");
            valid = false;
        }
        if (isShort(password)){
            password.setError("Password is too short!");
            valid = false;
        }
        if(isShort(confirmPassword) || confirmPassword.getText().equals(password.getText())){
            confirmPassword.setError("Passwords do not match");
            valid = false;
        }
        return valid;
    }

    private boolean isEmail(EditText text){
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isShort(EditText text){
        int textLength = text.getText().toString().length();
        return textLength < 6;
    }

    private boolean usernameExists(EditText text){
        return false;
    }
}