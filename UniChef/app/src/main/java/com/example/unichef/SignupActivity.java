package com.example.unichef;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicBoolean;

public class SignupActivity extends AppCompatActivity {
    public Button register;
    public EditText email;
    public EditText username;
    public EditText password;
    public EditText confirmPassword;
    public Button hideshow;
    public boolean shown = false;

    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        register = findViewById(R.id.create_button);
        email = findViewById(R.id.editTextEmail);
        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        hideshow = findViewById(R.id.hideshow_button);

        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://unichef-f6056-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");

        register.setOnClickListener(v -> {
            if (checkValidData()){
                mDatabase.orderByChild("username").equalTo(username.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()){
                            registerUser();
                        }else{
                            username.setError("Username already exists");
                            Toast.makeText(getApplicationContext(), "Check your info.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }else{
                Toast.makeText(this, "Check your info.", Toast.LENGTH_SHORT).show();
            }
        });

        hideshow.setOnClickListener(v -> {
            if(shown) {
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                hideshow.setText("ヘ(- _ -ヘ)");
                shown = false;
            } else {
                confirmPassword.setTransformationMethod(SingleLineTransformationMethod.getInstance());
                password.setTransformationMethod(SingleLineTransformationMethod.getInstance());
                hideshow.setText("\\(◕。◕\\)");
                shown = true;
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

    private void registerUser(){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()){
                updateDatabase();
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                finish();
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
    }

    private void updateDatabase(){
        String newUserID = mAuth.getCurrentUser().getUid();
        mDatabase.child(newUserID).child("username").setValue(username.getText().toString());
        mDatabase.child(newUserID).child("profileImage").setValue("https://firebasestorage.googleapis.com/v0/b/unichef-f6056.appspot.com/o/profile%2Fchef_icon.png?alt=media&token=52a0acb8-2222-415c-acc1-549c2af8c38f");
    }
}