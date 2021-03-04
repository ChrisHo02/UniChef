package com.example.unichef.ui.uploadRecipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.unichef.MainActivity;
import com.example.unichef.R;
import com.example.unichef.adapters.IngredientAdapter;
import com.example.unichef.adapters.UploadIngredientsAdapter;
import com.example.unichef.adapters.UploadTagsAdapter;
import com.example.unichef.database.Ingredient;
import com.example.unichef.database.Recipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadIngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadIngredientsFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    NavController navController;
    Button addIngredient;
    Button next;
    Recipe recipe;
    ArrayList<Ingredient> ingredients;
    RecyclerView recyclerView;



    public UploadIngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment uploadRecipe2.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadIngredientsFragment newInstance(String param1, String param2) {
        UploadIngredientsFragment fragment = new UploadIngredientsFragment();
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

        View view = inflater.inflate(R.layout.fragment_upload_ingredients,
                container, false);


        this.recipe = UploadIngredientsFragmentArgs.fromBundle(getArguments()).getRecipeArg();


        recyclerView = view.findViewById(R.id.recyclerView);
        ingredients = recipe.getIngredients();
        UploadIngredientsAdapter adapter = new UploadIngredientsAdapter(this.getContext(), ingredients);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        navController = NavHostFragment.findNavController(this);


        addIngredient = (Button) view.findViewById(R.id.addIngredient_button);
        addIngredient.setOnClickListener(this);

        next = (Button) view.findViewById(R.id.button);
        next.setOnClickListener(this);

        return view;
        //return inflater.inflate(R.layout.fragment_upload_recipe2, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addIngredient_button:
                UploadIngredientsFragmentDirections.ActionNavigationUploadIngredientsToNavigationChooseIngredient action = UploadIngredientsFragmentDirections.actionNavigationUploadIngredientsToNavigationChooseIngredient();
                action.setRecipeArg(recipe);
                Navigation.findNavController(view).navigate(action);
                //navController.navigate(new ActionOnlyNavDirections(R.id.action_navigation_uploadIngredient_to_navigation_chooseIngredient));
                break;
            case R.id.button:
                UploadIngredientsFragmentDirections.ActionNavigationUploadIngredientsToNavigationUploadInstructions action2 = UploadIngredientsFragmentDirections.actionNavigationUploadIngredientsToNavigationUploadInstructions();
                action2.setRecipeArg(recipe);
                Navigation.findNavController(view).navigate(action2);
                //navController.navigate(new ActionOnlyNavDirections(R.id.action_navigation_uploadIngredients_to_navigation_uploadInstructions));
                break;
            default:
                break;
        }
    }
}