package com.example.unichef.ui.account;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.unichef.MainActivity;
import com.example.unichef.R;
import com.example.unichef.SignupActivity;
import com.example.unichef.ViewSavedRecipesActivity;
import com.example.unichef.ViewYourRecipesActivity;
import com.example.unichef.database.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;

    private TextView usernameTextView;
    private ImageView profilePicture;
    private Button viewYourRecipes;
    private Button viewSavedRecipes;

    private FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        usernameTextView = root.findViewById(R.id.textView);
        profilePicture = root.findViewById(R.id.imageView);
        viewYourRecipes = root.findViewById(R.id.view_your_recipes);
        viewSavedRecipes = root.findViewById(R.id.view_saved_recipes_button);
        initializeView();
        return root;
    }

    private void initializeView(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://unichef-f6056-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mDatabase.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AccountUser account = snapshot.getValue(AccountUser.class);
                usernameTextView.setText(account.getUsername());
                Picasso.get().load(account.getProfileImage()).into(profilePicture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profilePicture.setOnLongClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            }
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
            return false;
        });

        viewYourRecipes.setOnClickListener(v -> startActivity(new Intent(getActivity(), ViewYourRecipesActivity.class)));

        viewSavedRecipes.setOnClickListener(v -> startActivity(new Intent(getActivity(), ViewSavedRecipesActivity.class)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),"Storage Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),"Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1 && data != null) {
                String filePath = getPath(getActivity().getApplicationContext(), data.getData());
                if (filePath != null){
                    FirebaseHelper helper = new FirebaseHelper();
                    helper.uploadProfilePicture(filePath, user.getUid());
                }
            }
        }
    }

    public static String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        return result;
    }

    public static class AccountUser{
        private String username;
        private String profileImage;

        public AccountUser(){}

        public AccountUser(String username, String profileImage){
            this.username = username;
            this.profileImage = profileImage;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }
    }
}