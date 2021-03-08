package com.example.unichef.ui.settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.unichef.LoginActivity;
import com.example.unichef.MainActivity;
import com.example.unichef.R;
import com.example.unichef.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private SettingsViewModel settingsViewModel;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView textView = root.findViewById(R.id.text_settings);
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://unichef-f6056-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");

        Button deleteButton = root.findViewById(R.id.settings_delete_account);
        deleteButton.setOnClickListener(this);

        Button helpButton = root.findViewById(R.id.settings_help_button);
        helpButton.setOnClickListener(this);

        return root;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_delete_account:
                deleteAccount();
                break;
            case R.id.settings_help_button:
                signOut();
                break;
        }
    }

    private void deleteAccount(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mDatabase.child(currentUser.getUid()).removeValue();
        currentUser.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(getContext(), "Successfully deleted Account!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }else{
                Toast.makeText(getContext(), "Failed to deleted Account", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signOut(){
        mAuth.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
}
