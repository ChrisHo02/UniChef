package com.example.unichef.ui.uploadRecipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.unichef.R;
import com.example.unichef.adapters.InstructionAdapter;
import com.example.unichef.adapters.UploadEquipmentAdapter;
import com.example.unichef.adapters.UploadIngredientsAdapter;
import com.example.unichef.adapters.UploadInstructionsAdapter;
import com.example.unichef.database.Instruction;
import com.example.unichef.database.Recipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadInstructionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadInstructionsFragment extends Fragment implements  View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button next;
    Button addInstruction;
    NavController navController;
    Recipe recipe;
    RecyclerView recyclerView;
    ArrayList<Instruction> instructions;
    UploadInstructionsAdapter uploadInstructionsAdapter;
    int step = 1;


    public UploadInstructionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment uploadReipce3.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadInstructionsFragment newInstance(String param1, String param2) {
        UploadInstructionsFragment fragment = new UploadInstructionsFragment();
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
        View view = inflater.inflate(R.layout.fragment_upload_instructions,
                container, false);

        this.recipe = UploadInstructionsFragmentArgs.fromBundle(getArguments()).getRecipeArg();
        assert recipe != null;
        this.instructions = recipe.getInstructions();



        recyclerView = view.findViewById(R.id.instructions_recycler_view);
        this.uploadInstructionsAdapter= new UploadInstructionsAdapter(this.getContext(),instructions);
        recyclerView.setAdapter(uploadInstructionsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        uploadInstructionsAdapter.setOnItemClickListener(new UploadInstructionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                return;
            }

            @Override
            public void onDeleteClick(int position) {
                instructions.remove(position);
                uploadInstructionsAdapter.notifyDataSetChanged();
            }
        });




        navController = NavHostFragment.findNavController(this);

        addInstruction = (Button) view.findViewById(R.id.addInstruction_button);
        addInstruction.setOnClickListener(this);
        next = (Button) view.findViewById(R.id.button);
        next.setOnClickListener(this);

        return view;
        //return inflater.inflate(R.layout.fragment_upload_recipe3, container, false);
    }

    @Override
    public void onClick(View view) {
        EditText instructionTextView = (EditText) getView().findViewById(R.id.instruction_editText);
        switch (view.getId()) {
            case R.id.addInstruction_button:

                String instructionStr = instructionTextView.getText().toString();
                if (instructionStr.trim().length() == 0) {
                    instructionTextView.setError("Please enter an instruction");
                } else {
                    this.instructions.add(new Instruction(step, instructionStr, 0));
                    instructionTextView.getText().clear();
                    this.uploadInstructionsAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.button:
                if (uploadInstructionsAdapter.getItemCount() == 0) {
                    instructionTextView.setError("Please add an instruction");
                } else {
                    recipe.setInstructions(instructions);
                    UploadInstructionsFragmentDirections.ActionUploadInstructionsFragmentToTempUploadTagsFragment action = UploadInstructionsFragmentDirections.actionUploadInstructionsFragmentToTempUploadTagsFragment();
                    action.setRecipeArg(recipe);
                    Navigation.findNavController(view).navigate(action);
                    //navController.navigate(new ActionOnlyNavDirections(R.id.action_navigation_uploadIngredients_to_navigation_uploadInstructions));
                }
                break;
            default:
                break;
        }
    }
}