package com.example.unichef.ui.uploadRecipe;

import android.os.Bundle;

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

import com.example.unichef.R;
import com.example.unichef.adapters.UploadEquipmentAdapter;
import com.example.unichef.adapters.UploadIngredientsAdapter;
import com.example.unichef.database.Equipment;
import com.example.unichef.database.Ingredient;
import com.example.unichef.database.Recipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadEquipmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadEquipmentFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static  String[] EQUIPMENT = new String[]{
            "Apple", "Avocado", "Banana", "Carrot", "Duck", "Egg", "Garlic", "Ginger", "Hot sauce", "Red onion", "Onion", "Red pepper", "Yellow pepper", "Green pepper",
            "Pancetta", "Parmesan", "Egg", "Salted butter", "Unsalted butter", "Butter", "Salt", "Pepper", "Beef mince", "Pork mince", "Lamb mince", "Chicken breast",
            "Chicken thigh", "Chicken wing", "Chicken drumstick", "Red chilli", "Smoked paprika", "Ground coriander", "Ground cumin", "Olive oil", "Lime", "Lemon", "Tabasco",
            "Tortilla", "Oregano", "Tomato", "Spaghetti", "Tinned tomatoes", "Curry sauce", "Sugar", "Caster sugar", "Granulated sugar", "Vegetable oil", "Sweet potato",
            "Potato", "Black beans", "Kidney beans", "Tomato purée", "Chilli powder", "Celery", "Lasagne sheets", "Cheddar cheese"
    };
    NavController navController;
    Button addEquipment;
    Button next;
    Recipe recipe;
    ArrayList<Equipment> equipment;
    RecyclerView recyclerView;
    ArrayAdapter<String> chooseAdapter;
    UploadEquipmentAdapter uploadEquipmentAdapter;

    public UploadEquipmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadEquipment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadEquipmentFragment newInstance(String param1, String param2) {
        UploadEquipmentFragment fragment = new UploadEquipmentFragment();
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
        View view = inflater.inflate(R.layout.fragment_upload_equipment,
                container, false);

        assert getArguments() != null;
        this.recipe = UploadEquipmentFragmentArgs.fromBundle(getArguments()).getRecipeArg();
        this.equipment = recipe.getEquipment();


        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.equipment_autoCompleteTextView);
        this.chooseAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, EQUIPMENT);
        autoCompleteTextView.setAdapter(chooseAdapter);

        recyclerView = view.findViewById(R.id.recyclerView);
        this.uploadEquipmentAdapter = new UploadEquipmentAdapter(this.getContext(), equipment);
        recyclerView.setAdapter(uploadEquipmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        uploadEquipmentAdapter.setOnItemClickListener(new UploadEquipmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                return;
            }

            @Override
            public void onDeleteClick(int position) {
                equipment.remove(position);
                uploadEquipmentAdapter.notifyDataSetChanged();
            }
        });

        navController = NavHostFragment.findNavController(this);


        addEquipment = (Button) view.findViewById(R.id.addEquipment_button);
        addEquipment.setOnClickListener(this);

        next = (Button) view.findViewById(R.id.button);
        next.setOnClickListener(this);

        return view;
        //return inflater.inflate(R.layout.fragment_upload_equipment, container, false);
    }

    @Override
    public void onClick(View view) {
        EditText equipmentTextView = (EditText) getView().findViewById(R.id.equipment_autoCompleteTextView);
        switch (view.getId()) {
            case R.id.addEquipment_button:

                String equipmentString = equipmentTextView.getText().toString();

                if (equipmentString.trim().length() == 0) {
                    equipmentTextView.setError("Please enter an equipment");
                } else {
                    recipe.addEquipment(new Equipment(equipmentString));
                    equipmentTextView.getText().clear();
                    this.uploadEquipmentAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.button:
                if (uploadEquipmentAdapter.getItemCount() == 0) {
                    equipmentTextView.setError("Please add an equipment");
                } else {
                    UploadEquipmentFragmentDirections.ActionUploadEquipmentFragmentToUploadInstructionsFragment action2 = UploadEquipmentFragmentDirections.actionUploadEquipmentFragmentToUploadInstructionsFragment();
                    action2.setRecipeArg(recipe);
                    Navigation.findNavController(view).navigate(action2);
                    //navController.navigate(new ActionOnlyNavDirections(R.id.action_navigation_uploadIngredients_to_navigation_uploadInstructions));
                }
                break;
            default:
                break;
        }
    }
}
