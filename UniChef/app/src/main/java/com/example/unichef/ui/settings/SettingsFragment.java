package com.example.unichef.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.unichef.LoginActivity;
import com.example.unichef.R;
import com.example.unichef.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private SettingsViewModel settingsViewModel;

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

        root.findViewById(R.id.settings_delete_account).setOnClickListener(this);

        return root;
    }

    //delete account onClick
    public void deleteAccount(View view){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_delete_account:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }
}
