package com.example.unichef.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.unichef.R;
import com.example.unichef.database.Comment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private final ArrayList<Comment> comments;
    private DatabaseReference mDatabase;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
        this.comments = comments;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        Comment comment = comments.get(position);

        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.comment_item, parent, false);
        }

        TextView name = v.findViewById(R.id.nameText);
        TextView date = v.findViewById(R.id.dateText);
        TextView commentText = v.findViewById(R.id.commentText);
        ImageView image = v.findViewById(R.id.profileImage);

        mDatabase = FirebaseDatabase.getInstance("https://unichef-f6056-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        mDatabase.child(comment.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("username").getValue().toString());
                Picasso.get().load(snapshot.child("profileImage").getValue().toString()).into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Date time = new Date(comment.getDate());
        date.setText(new SimpleDateFormat("dd/MM/yy").format(time));
        commentText.setText(comment.getComment());
        return v;
    }
}
