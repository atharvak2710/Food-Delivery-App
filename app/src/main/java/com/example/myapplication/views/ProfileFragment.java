package com.example.myapplication.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.feedbackpage;
import com.example.myapplication.login.loginpage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ProfileFragment extends Fragment {

    Button logout;
    FirebaseAuth sauth;
    EditText email;
    EditText name,address;
    EditText phno;
    TextView feedback,add;
    Uri imagePath;

    DatabaseReference db;
    DatabaseReference refphno,refname,refadd;
    ImageView profile;
    FloatingActionButton chooseprofile;







    public ProfileFragment() {

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logout= view.findViewById(R.id.logout);
        email=view.findViewById(R.id.iemail);
        name=view.findViewById(R.id.iname);
        feedback=view.findViewById(R.id.feedback);
        phno=view.findViewById(R.id.iphno);

        sauth=FirebaseAuth.getInstance();
        chooseprofile=view.findViewById(R.id.fab);
        profile=view.findViewById(R.id.getuserimageinimageview);
        address=view.findViewById(R.id.address);
        db=FirebaseDatabase.getInstance().getReference().child("info");
        refphno = FirebaseDatabase.getInstance().getReference().child("info").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Number");
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


        loadImage();
        refphno.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data=snapshot.getValue().toString();
                    phno.setText(data);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        refname = FirebaseDatabase.getInstance().getReference().child("info").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Name");


        refname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data=snapshot.getValue().toString();
                name.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        refadd = FirebaseDatabase.getInstance().getReference().child("info").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Address");


        refadd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data=snapshot.getValue().toString();
                address.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

                builder.setMessage("Are you Sure You Want to Logout?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences= getActivity().getSharedPreferences(loginpage.PREFS_NAME,0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putBoolean("hasLoggedIn",false);
                        editor.commit();
                        startActivity(new Intent(getActivity(), loginpage.class));
                        getActivity().finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        chooseprofile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                    chooseImage();

            }
        });

feedback.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), feedbackpage.class));
    }
});

    }
    public void chooseImage()
    {
        try {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,1);
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), "Failed to Choose Image", Toast.LENGTH_SHORT).show();
        }
    }
    public void onActivityResult(int requestcode ,int resultcode,Intent data)
    {
        try{
            super.onActivityResult(requestcode,resultcode,data);
            if(requestcode==1)
            {
                imagePath = data.getData();
                uploadImageToFirebase(imagePath);


            }
        }
        catch(Exception e)
        {


        }

    }

    private void uploadImageToFirebase(Uri imageUri) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" +sauth.getUid());
        storageRef.putFile(imageUri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadUrl = uri.toString();
                    // Use this URL for displaying the image using Glide
                    loadImageWithGlide(downloadUrl);
                    Toast.makeText(getActivity(), "Image Saved Successfully", Toast.LENGTH_SHORT).show();


                });
            } else {
                Toast.makeText(getActivity(), "Upload Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
  private void loadImageWithGlide(String url) {
        Glide.with(this)
                .load(url)
                .into(profile);
    }
    private void loadImage() {
        StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("images/" +sauth.getUid());


        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getActivity())
                        .load(uri.toString())
                        .into(profile);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Firebase", "Error retrieving the image", e);
            }
        });
    }
}

