package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.request.target.BitmapThumbnailImageViewTarget;
import com.example.myapplication.views.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class feedbackpage extends AppCompatActivity {

    EditText feedbacktext;
    DatabaseReference db;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackpage);
        feedbacktext=findViewById(R.id.feedbacktext);
        submit=findViewById(R.id.submit);
        db= FirebaseDatabase.getInstance().getReference().child("info");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=feedbacktext.getText().toString();
                db.child(FirebaseAuth.getInstance().getUid()).child("Feedback").setValue(text);
                Toast.makeText(feedbackpage.this, "Feedback Sent Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(feedbackpage.this, MainActivity.class));
                finish();
            }
        });

    }
}