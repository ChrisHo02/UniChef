package com.example.unichef;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unichef.database.DBHelper;
import com.example.unichef.database.Equipment;
import com.example.unichef.database.Ingredient;
import com.example.unichef.database.Instruction;
import com.example.unichef.database.Recipe;
import com.example.unichef.database.RecipeGenerator;
import com.example.unichef.database.Tag;
import com.example.unichef.database.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    public Button button1;
    public Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //testing
        DBHelper db = new DBHelper(this, null, null, 1);
        User user = new User("chris", "email.com","chris","pass");
        db.addUser(db.getWritableDatabase(), user);

        new RecipeGenerator().generateRecipes(db, user);
        //end of testing

        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        button1 = (Button) findViewById(R.id.signup_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        button2 = (Button) findViewById(R.id.login_button);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editPassword = findViewById(R.id.editPassword);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                if(db.findUser(db.getReadableDatabase(),editTextEmail.getText().toString(), editPassword.getText().toString())){
                    db.close();
                    //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //startActivity(intent);
                }
            }
        });
    }

}
