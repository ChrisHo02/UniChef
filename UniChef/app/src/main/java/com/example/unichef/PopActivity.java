package com.example.unichef;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.unichef.database.Recipe;
import com.example.unichef.ui.tips.TipsFragment;
import com.example.unichef.ui.tips.TipsViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PopActivity extends Activity {
    ArrayList<String> tipsTitle = new ArrayList<>();
    ArrayList<String> tipsDesc = new ArrayList<>();

    ArrayList<String> redact = new ArrayList(Arrays.asList("to", "be", "the", "or", "heat", "medium", "small", "large", "cook", "a", "an", "so", "that", "before", "after"));

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.s_tips);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        getWindow().setLayout((int) (width * 0.6), (int) (height * 0.5));

        getTips();

        if(tipsDesc.size() == 0){
            tipsDesc.add("We couldn't find any tips for this recipe. Sorry! :`(");
        }

        ListView listView = findViewById(R.id.stipsList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PopActivity.this, android.R.layout.simple_list_item_1, tipsDesc);
        listView.setAdapter(adapter);

        /**
        setContentView(R.layout.s_tips);
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        View view = this.findViewById(android.R.id.content);
        View root = inflater.inflate(R.layout.s_tips, (ViewGroup)view, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        tipsListView = root.findViewById(R.id.stipsList);

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        getWindow().setLayout((int) (width * 0.6), (int) (height * 0.5));

        PopActivity.specificTipsAdapter adapter = new PopActivity.specificTipsAdapter(this, tipTitle, tipDesc);
        tipsListView.setAdapter(adapter);
         */


    }

    public void getTips(){
        HashMap<Integer, String> hashMap = new HashMap<>();
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> ingredients = bundle.getStringArrayList("ingredients");
        ArrayList<String> equipment = bundle.getStringArrayList("equipment");
        for(int i =0;i<ingredients.size();i++){
            String[] words = ingredients.get(i).split(" ");
            for(String word: words){
                hashMap.put(hash(word.toLowerCase()), word.toLowerCase());
            }
        }
        for(int i =0;i<equipment.size();i++){
            String[] words = equipment.get(i).split(" ");
            for(String word: words){
                hashMap.put(hash(word.toLowerCase()), word.toLowerCase());
            }
        }
        String[] tipArray = getResources().getStringArray(R.array.tipsArray);
        for(int i =0; i<tipArray.length;i++){
            String[] words = tipArray[i].split(" ");
            for(String word : words){
                if(hashMap.containsKey(hash(word.toLowerCase()))){
                    if(hashMap.get(hash(word.toLowerCase())).equals(word.toLowerCase()) && !redact.contains(word.toLowerCase())){
                        tipsDesc.add(tipArray[i]);
                    }
                    break;
                }
            }
        }
    }

    public int hash(String string){
        return string.hashCode();
    }

    /**
    class specificTipsAdapter extends ArrayAdapter<String> {
        Context context;
        String[] m_TipTitle;
        String[] m_TipDesc;

        specificTipsAdapter(Context c,  String[] title, String[] desc) {
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
    }*/
}
