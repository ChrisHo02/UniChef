package com.example.unichef.ui.uploadRecipe;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.unichef.MainActivity;
import com.example.unichef.R;
import com.example.unichef.UploadRecipeActivity;
import com.example.unichef.database.Equipment;
import com.example.unichef.database.Ingredient;
import com.example.unichef.database.Instruction;
import com.example.unichef.database.Recipe;
import com.example.unichef.database.Tag;
import com.example.unichef.database.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadTitleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadTitleFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 200;
    Button next;
    NavController navController;
    ImageView recipePhoto;
    ImageButton uploadPhoto;
    String photoPath;
    Context mContext;
    Activity mActivity;

    List<String> imagesFilesPaths = new ArrayList<>();

    public UploadTitleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment uploadRecipe0.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadTitleFragment newInstance(String param1, String param2) {
        UploadTitleFragment fragment = new UploadTitleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        navController = NavHostFragment.findNavController(this);
        View view = inflater.inflate(R.layout.fragment_upload_title,
                container, false);

        this.mContext = getContext();
        this.mActivity = getActivity();
        checkPermission();

        recipePhoto = view.findViewById(R.id.imageView);

        uploadPhoto = view.findViewById(R.id.imageButton);
        uploadPhoto.setOnClickListener(this);

        next = view.findViewById(R.id.button);
        next.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton:

                selectImage();
                break;
            case R.id.button:
                EditText recipeTextView = getView().findViewById(R.id.nameOfRecipe);
                EditText recipeDescTextView = getView().findViewById(R.id.description);
                String title = recipeTextView.getText().toString();
                String description = recipeDescTextView.getText().toString();

                ArrayList<Ingredient> ingredients = new ArrayList<>();
                ArrayList<Instruction> instructions = new ArrayList<>();
                ArrayList<Equipment> equipment = new ArrayList<>();
                ArrayList<Tag> tags = new ArrayList<>();

                Recipe recipe = new Recipe();
                recipe.setCreatorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                recipe.setTitle(title);
                recipe.setDescription(description);
                recipe.setImageUrl("IDK");
                recipe.setIngredients(ingredients);
                recipe.setInstructions(instructions);
                recipe.setEquipment(equipment);
                recipe.setTags(tags);
                recipe.setImageUrl(photoPath);

                UploadTitleFragmentDirections.ActionUploadTitleFragmentToUploadInfoFragment action = UploadTitleFragmentDirections.actionUploadTitleFragmentToUploadInfoFragment();
                action.setRecipeArg(recipe);
                Navigation.findNavController(view).navigate(action);
                //navController.navigate(new ActionOnlyNavDirections(R.id.action_uploadTitleFragment_to_uploadInfoFragment));
                break;
            default:
                break;
        }
    }

    protected void checkPermission(){
        if(ContextCompat.checkSelfPermission(mActivity,Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                mActivity,Manifest.permission.READ_CONTACTS)
                + ContextCompat.checkSelfPermission(
                mActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.READ_CONTACTS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage("Need stuff");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                mActivity,
                                new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_CONTACTS,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        mActivity,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }else {
            // Do something, when permissions are already granted
            Toast.makeText(mContext,"Permissions already granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:{
                // When request is cancelled, the results array are empty
                if(
                        (grantResults.length >0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                ){
                    // Permissions are granted
                    Toast.makeText(mContext,"Permissions granted.",Toast.LENGTH_SHORT).show();
                }else {
                    // Permissions are denied
                    Toast.makeText(mContext,"Permissions denied.",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

//    public void checkPermission(String permission, int requestCode)
//    {
//        if (ContextCompat.checkSelfPermission(this.getContext(), permission)
//                == PackageManager.PERMISSION_DENIED) {
//
//            // Requesting the permission
//            ActivityCompat.requestPermissions(this.getActivity(),
//                    new String[] { permission },
//                    requestCode);
//        }
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode,
//                        permissions,
//                        grantResults);
//    }

        private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (options[i].toString()) {
                    case "Take Photo":
//                        checkPermission(
//                                Manifest.permission.CAMERA,
//                                CAMERA_PERMISSION_CODE);

                        uploadPhotoFromCamera();
                        break;
                    case "Choose from Gallery":
//                        checkPermission(
//                                Manifest.permission.READ_EXTERNAL_STORAGE,
//                                STORAGE_PERMISSION_CODE);

                        uploadPhotoFromGallery();

                        break;
                    case "Cancel":
                        dialogInterface.dismiss();
                        break;
                }
            }
        });
        builder.show();
    }

    private void uploadPhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        "com.mydomain.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, 1);
            }
        }
    }

    private void uploadPhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {


                String tempImageFilePath = imagesFilesPaths.get(imagesFilesPaths.size()-1);
                Uri tempImageURI = Uri.fromFile(new File( tempImageFilePath ));
                resizeThenLoadImage(tempImageFilePath, tempImageURI);
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String picturePath = getPath( getActivity( ).getApplicationContext( ), selectedImage );

                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    try {
                        copyFile(new File(picturePath), photoFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                recipePhoto.setImageURI(selectedImage);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagesFilesPaths.add(image.getAbsolutePath());
        photoPath = image.getAbsolutePath();
        return image;
    }

    private void resizeThenLoadImage(String tempImageFilePath, Uri tempImageURI){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), tempImageURI);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bitmap == null)return;

        int sizeDivisor = 1;

        double imageSize = bitmap.getWidth();
        if(bitmap.getHeight() > bitmap.getWidth())imageSize = bitmap.getHeight();
        if(sizeDivisor == 0)sizeDivisor = 1;


        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, (bitmap.getWidth()/sizeDivisor), (bitmap.getHeight()/sizeDivisor), true);

        //storeBitmapInFile(bitmapScaled, tempImageFilePath);

        recipePhoto.setImageURI(tempImageURI);
    }

    public static String getPath(Context context, Uri uri ) {
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
        if (result == null) {
            result = "Not found";
        }
        return result;
    }


    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

}