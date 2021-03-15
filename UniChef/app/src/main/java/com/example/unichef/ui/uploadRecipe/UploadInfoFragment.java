package com.example.unichef.ui.uploadRecipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.example.unichef.R;

import com.example.unichef.database.Ingredient;
import com.example.unichef.database.Recipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadInfoFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button next;
    private NavController navController;
    Recipe recipe;

    public UploadInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment uploadRecipe1.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadInfoFragment newInstance(String param1, String param2) {
        UploadInfoFragment fragment = new UploadInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_upload_info,
                container, false);

        assert getArguments() != null;
        this.recipe = UploadInfoFragmentArgs.fromBundle(getArguments()).getRecipeArg();

        Spinner mspin=(Spinner) view.findViewById(R.id.spinner);
        Integer[] items = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this.getContext(), android.R.layout.simple_spinner_item, items);
        mspin.setAdapter(adapter);

        navController = NavHostFragment.findNavController(this);
        next = (Button) view.findViewById(R.id.button);
        next.setOnClickListener(this);


        return view;
        //return inflater.inflate(R.layout.fragment_upload_recipe2, container, false);
    }

    @Override
    public void onClick(View view) {

        Spinner recipePortionSpinner = (Spinner) getView().findViewById(R.id.spinner);
        Integer portion = Integer.parseInt(recipePortionSpinner.getSelectedItem().toString());

        RatingBar recipeDifficultyRateBar = (RatingBar) getView().findViewById(R.id.ratingBar);
        Integer difficulty = recipeDifficultyRateBar.getNumStars();


        EditText recipeTimeTextView = (EditText) getView().findViewById(R.id.cookingTime);
        Integer cookingTime = 0;
        if (!recipeTimeTextView.getText().toString().equals("")) {
            cookingTime = Integer.parseInt(recipeTimeTextView.getText().toString());
        }

        recipe.setPortions(portion);
        recipe.setDifficulty(difficulty);
        recipe.setTime(cookingTime);

//        ArrayList<Ingredient> ingredients = new ArrayList<>();
//        ingredients.add(new Ingredient("apple"));
//        ingredients.add(new Ingredient("banana"));


        UploadInfoFragmentDirections.ActionUploadInfoFragmentToUploadIngredientsFragment action = UploadInfoFragmentDirections.actionUploadInfoFragmentToUploadIngredientsFragment();
        action.setRecipeArg(recipe);
        Navigation.findNavController(view).navigate(action);
//        navController.navigate(new ActionOnlyNavDirections(R.id.action_navigation_UploadInfo_to_navigation_uploadIngredient));
    }
}