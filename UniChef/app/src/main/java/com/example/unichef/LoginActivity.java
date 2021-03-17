package com.example.unichef;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    public Button login;
    public Button register;
    public EditText email;
    public EditText password;
    public Button hideshow;
    public Boolean shown = false;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editPassword);
        hideshow = findViewById(R.id.hideshow_button);

        login = findViewById(R.id.login_button);
        login.setOnClickListener(v -> {
            if (checkValidData()){
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Failed to sign in!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        register = findViewById(R.id.signup_button);
        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        hideshow.setOnClickListener(v -> {
            if(shown) {
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                hideshow.setText("ヘ(- _ -ヘ)");
                shown = false;
            } else {
                password.setTransformationMethod(SingleLineTransformationMethod.getInstance());
                hideshow.setText("\\(◕。◕\\)");
                shown = true;
            }
        });
    }

    private boolean checkValidData(){
        boolean valid = true;
        if (!isEmail(email)){
            email.setError("Enter valid email! (e.g. johnsmith123@gmail.com)");
            valid = false;
        }
        if (isShort(password)){
            password.setError("Password must be 6+ characters!");
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
}
