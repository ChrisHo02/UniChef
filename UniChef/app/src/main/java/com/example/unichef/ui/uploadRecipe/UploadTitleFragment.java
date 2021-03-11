package com.example.unichef.ui.uploadRecipe;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
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
    Button next;
    NavController navController;
    ImageView recipePhoto;
    ImageButton uploadPhoto;
    String photoPath;

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

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent;
                switch (options[i].toString()) {
                    case "Take Photo":
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                        break;
                    case "Choose from Gallery":
                        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                        break;
                    case "Cancel":
                        dialogInterface.dismiss();
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
//                Bundle extras = data.getExtras();
//                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                recipePhoto.setImageBitmap(imageBitmap);


                String tempImageFilePath = imagesFilesPaths.get(imagesFilesPaths.size()-1);
                Uri tempImageURI = Uri.fromFile(new File( tempImageFilePath ));
                resizeThanLoadImage(tempImageFilePath, tempImageURI);
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//                Cursor cursor = requireContext().getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String picturePath = cursor.getString(columnIndex);
//                cursor.close();
//
//                recipePhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
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

    private void resizeThanLoadImage(String tempImageFilePath, Uri tempImageURI){
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

}