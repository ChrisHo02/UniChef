package com.example.unichef.ui.tips;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.unichef.R;
import com.example.unichef.ui.home.HomeFragment;

public class TipsFragment extends Fragment {
    private TipsViewModel tipsViewModel;
    ListView tipsListView;
    String tipTitle[] = {"#1", "#2", "#3'", "#4", "#5", "#6", "#7", "#8"};
    String tipDesc[] = {"Al dente = the moment when pasta is JUST cooked!", "Always wash your produce and hands before cooking!", "You can't cook chicken to be medium rare! It's either poisonous, cooked or sand!", "Don't chop both veggies and meat on one chopping board! Cross-contamination!", "Let the knife do all the work!", "Ran out of things to say", "Uh...", "I hate AI CW?"};
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        tipsViewModel = new ViewModelProvider(this).get(TipsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tips, container, false);
        tipsListView = root.findViewById(R.id.tipsList);
        TipsFragment.TipsAdapter adapter = new TipsFragment.TipsAdapter(getActivity(), tipTitle, tipDesc);
        tipsListView.setAdapter(adapter);
        return root;
    }

    class TipsAdapter extends ArrayAdapter<String> {
        Context context;
        String m_TipTitle[];
        String m_TipDesc[];
        TipsAdapter(Context c, String title[], String desc[]){
            super(c, R.layout.tip_item, R.id.listtextview1, title);
            this.context = c;
            this.m_TipTitle = title;
            this.m_TipDesc = desc;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.tip_item, parent, false);
            TextView myTitle = row.findViewById(R.id.listtextview1);
            TextView myDescription = row.findViewById(R.id.listtextview2);

            myTitle.setText(tipTitle[position]);
            myDescription.setText(tipDesc[position]);

            return row;
            //return super.getView(position, convertView, parent);
        }
    }
}
