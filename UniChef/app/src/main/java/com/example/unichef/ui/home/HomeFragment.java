package com.example.unichef.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.unichef.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listView;
    String recipeTitle[] = {"Spaghetti", "Lasagne", "Turds with Cream"};
    String recipeDescription[] = {"Noodles", "Pasta", "Cream isn't fresh. It isn't cream either."};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //This was default code written when I loaded up the project.
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        listView = root.findViewById(R.id.listView);

        MyAdapter adapter  = new MyAdapter(getActivity(), recipeTitle, recipeDescription);
        listView.setAdapter(adapter);

        return root;
    }


    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String m_recipeTitle[];
        String m_recipeDescription[];

        MyAdapter (Context c, String title[], String description[]){
            super(c, R.layout.med_recipe_item, R.id.textView1, title);
            this.context = c;
            this.m_recipeTitle = title;
            this.m_recipeDescription = description;
        }

        @Nullable
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.med_recipe_item, parent, false);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);

            myTitle.setText(recipeTitle[position]);
            myDescription.setText(recipeDescription[position]);

            return row;
        }
    }
}