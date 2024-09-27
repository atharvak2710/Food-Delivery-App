package com.example.myapplication.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapters.HomeListAdapter;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.login.loginpage;
import com.example.myapplication.models.Product;
import com.example.myapplication.repositories.HomeRepo;
import com.example.myapplication.viewmodels.HomeViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class HomeFragment extends Fragment implements HomeListAdapter.HomeInterface {

    private HomeListAdapter homeListAdapter;
    private NavController navController;
    EditText search;
    private static final String TAG = "HomeFragment";
    ImageView imageView;
    DatabaseReference refname;
    ImageButton pizza;
    TextView tw,open;
    FirebaseAuth sauth=FirebaseAuth.getInstance();




    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @SuppressLint("SuspiciousIndentation")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView=view.findViewById(R.id.profile);
        homeListAdapter=new HomeListAdapter(this);
        search=view.findViewById(R.id.searchbar);
        open=view.findViewById(R.id.textView4);
        pizza=view.findViewById(R.id.pizza);
        tw=view.findViewById(R.id.textView);
        loadImage();
        binding.recyclerview.setAdapter(homeListAdapter);
        homeViewModel =new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                homeListAdapter.submitList(products);
            }
        });

        navController= Navigation.findNavController(view);

        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                try {
                    navController.navigate(R.id.action_HomeFragment_to_searchFragment);

                }catch (Exception e)
                {

                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    navController.navigate(R.id.action_HomeFragment_to_orderFragment);

                }catch (Exception e)
                {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    navController.navigate(R.id.action_HomeFragment_to_pizzaFragment);

                }catch (Exception e)
                {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    navController.navigate(R.id.action_HomeFragment_to_searchFragment);

                }catch (Exception e)
                {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });








        refname = FirebaseDatabase.getInstance().getReference().child("info").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Name");
        refname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String data = snapshot.getValue().toString();
                    tw.setText("Hi! " + data);
                }
                else {
                    startActivity(new Intent(requireContext(), loginpage.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void addItem(Product product) {

        boolean isAdded =homeViewModel.addItemToCart(product);
        if(isAdded)
        {
            Snackbar.make(requireView(),product.getName()+" added to Cart",Snackbar.LENGTH_LONG).setAction("Checkout", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navController.navigate(R.id.action_HomeFragment_to_cartFragment);
                }
            }).show();
        }
        else {
            Snackbar.make(requireView(), "Already Have The Max Quantity In Cart", Snackbar.LENGTH_LONG).setAction("Checkout", new View.OnClickListener()
            {
                public void onClick(View view) {
                    navController.navigate(R.id.action_HomeFragment_to_cartFragment);
                }
            }).show();
        }

    }

    @Override
    public void onItemClick(Product product) {
        Log.d(TAG,"onItemClick: "+product.toString());
        homeViewModel.setProduct(product);
        navController.navigate(R.id.action_HomeFragment_to_DetailsFragment);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadImage() {
        StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("images/" +sauth.getUid());


        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getActivity())
                        .load(uri.toString())
                        .into(imageView);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Firebase", "Error retrieving the image", e);
            }
        });
    }
}