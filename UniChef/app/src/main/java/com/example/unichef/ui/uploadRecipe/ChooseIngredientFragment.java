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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.unichef.R;
import com.example.unichef.database.Ingredient;
import com.example.unichef.database.Recipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseIngredientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseIngredientFragment extends Fragment implements  View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String[] INGREDIENTS = new String[]{
            "Apple", "Banana", "Carrot", "Duck", "Egg", "Fire", "Garlic", "Hot sauce", "Igloo"
    };
    private Button button;
    private NavController navController;
    Recipe recipe;


    public ChooseIngredientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseRecipe.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseIngredientFragment newInstance(String param1, String param2) {
        ChooseIngredientFragment fragment = new ChooseIngredientFragment();
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
        View view = inflater.inflate(R.layout.fragment_choose_ingredient,
                container, false);

        this.recipe = ChooseIngredientFragmentArgs.fromBundle(getArguments()).getRecipeArg();


        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.ingredient_autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, INGREDIENTS);
        autoCompleteTextView.setAdapter(adapter);

        button = (Button) view.findViewById(R.id.button2);
        button.setOnClickListener(this);

        return view;
        //return inflater.inflate(R.layout.fragment_choose_ingredient, container, false);
    }

    @Override
    public void onClick(View view) {
        EditText ingredientTextView = (EditText) getView().findViewById(R.id.ingredient_autoCompleteTextView);
        String ingredientString = ingredientTextView.getText().toString();
        recipe.addIngredient(new Ingredient(ingredientString));

        ChooseIngredientFragmentDirections.ActionChooseIngredientFragmentToUploadIngredientsFragment action = ChooseIngredientFragmentDirections.actionChooseIngredientFragmentToUploadIngredientsFragment();
        action.setRecipeArg(recipe);
        Navigation.findNavController(view).navigate(action);
        //navController.navigate(new ActionOnlyNavDirections(R.id.action_navigation_chooseIngredient_to_navigation_uploadIngredients));
    }
}