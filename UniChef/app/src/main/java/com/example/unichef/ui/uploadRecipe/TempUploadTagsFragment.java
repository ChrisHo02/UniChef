package com.example.unichef.ui.uploadRecipe;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.unichef.R;
import com.example.unichef.adapters.TempUploadTagsAdapter;
import com.example.unichef.adapters.UploadEquipmentAdapter;
import com.example.unichef.database.Equipment;
import com.example.unichef.database.FirebaseHelper;
import com.example.unichef.database.Recipe;
import com.example.unichef.database.Tag;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TempUploadTagsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TempUploadTagsFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static  String[] TAGS = new String[]{
            "Apple", "Avocado", "Banana", "Carrot", "Duck", "Egg", "Garlic", "Ginger", "Hot sauce", "Red onion", "Onion", "Red pepper", "Yellow pepper", "Green pepper",
            "Pancetta", "Parmesan", "Egg", "Salted butter", "Unsalted butter", "Butter", "Salt", "Pepper", "Beef mince", "Pork mince", "Lamb mince", "Chicken breast",
            "Chicken thigh", "Chicken wing", "Chicken drumstick", "Red chilli", "Smoked paprika", "Ground coriander", "Ground cumin", "Olive oil", "Lime", "Lemon", "Tabasco",
            "Tortilla", "Oregano", "Tomato", "Spaghetti", "Tinned tomatoes", "Curry sauce", "Sugar", "Caster sugar", "Granulated sugar", "Vegetable oil", "Sweet potato",
            "Potato", "Black beans", "Kidney beans", "Tomato purée", "Chilli powder", "Celery", "Lasagne sheets", "Cheddar cheese"
    };
    NavController navController;
    Button addTag;
    Button next;
    Recipe recipe;
    ArrayList<Tag> tags;
    RecyclerView recyclerView;
    ArrayAdapter<String> chooseAdapter;
    TempUploadTagsAdapter uploadTagsAdapter;
    String photopath;

    public TempUploadTagsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TempUploadTagsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TempUploadTagsFragment newInstance(String param1, String param2) {
        TempUploadTagsFragment fragment = new TempUploadTagsFragment();
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
        View view = inflater.inflate(R.layout.fragment_temp_upload_tags,
                container, false);

        assert getArguments() != null;
        this.recipe = TempUploadTagsFragmentArgs.fromBundle(getArguments()).getRecipeArg();
        this.tags = recipe.getTags();
        this.photopath = recipe.getImageUrl();


        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.tags_autoCompleteTextView);
        this.chooseAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, TAGS);
        autoCompleteTextView.setAdapter(chooseAdapter);

        recyclerView = view.findViewById(R.id.recyclerView);
        this.uploadTagsAdapter = new TempUploadTagsAdapter(this.getContext(), tags);
        recyclerView.setAdapter(uploadTagsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        navController = NavHostFragment.findNavController(this);


        addTag = (Button) view.findViewById(R.id.addTag_button);
        addTag.setOnClickListener(this);

        next = (Button) view.findViewById(R.id.button);
        next.setOnClickListener(this);

        return view;
        //return inflater.inflate(R.layout.fragment_temp_upload_tags, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTag_button:
                EditText tagTextView = (EditText) getView().findViewById(R.id.tags_autoCompleteTextView);
                String tagString = tagTextView.getText().toString();
                recipe.addTag(new Tag(tagString));
                tagTextView.getText().clear();
                this.uploadTagsAdapter.notifyDataSetChanged();
                break;
            case R.id.button:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("Select your answer.");
                builder.setMessage("Upload recipe?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recipe.setDateAdded(new Date().getTime());
                        FirebaseHelper helper = new FirebaseHelper();
                        helper.uploadRecipe(recipe);

                        File file = new File(photopath);
                        file.delete();
                        getActivity().finish();
                        Toast.makeText(getContext(),
                                "Recipe uploaded",Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            default:
                break;
        }
    }
}