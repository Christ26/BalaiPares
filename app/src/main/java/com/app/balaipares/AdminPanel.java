package com.app.balaipares;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class AdminPanel extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<FinalOrderModel> flist;

    private MainAdapter adapter;

    private FirebaseDatabase root;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        root = FirebaseDatabase.getInstance();
        reference = root.getReference("admin/OrdersToDeliver");

        recyclerView = findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);


        flist = new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (snapshot.hasChildren()){
                        FinalOrderModel finalOrderModel = new FinalOrderModel();

                        finalOrderModel.setFoodOrders(Objects.requireNonNull(dataSnapshot.child("foodOrders").getValue()).toString());
                        finalOrderModel.setAddOns(Objects.requireNonNull(dataSnapshot.child("addOns").getValue()).toString());
                        finalOrderModel.setFoodQuantity(Objects.requireNonNull(dataSnapshot.child("foodQuantity").getValue()).toString());
                        finalOrderModel.setDrinksOrders(Objects.requireNonNull(dataSnapshot.child("drinksOrders").getValue()).toString());
                        finalOrderModel.setDrinksQuantity(Objects.requireNonNull(dataSnapshot.child("drinksQuantity").getValue()).toString());
                        finalOrderModel.setTotalPayment(Objects.requireNonNull(dataSnapshot.child("totalPayment").getValue()).toString());
                        finalOrderModel.setNameOfReceiver(Objects.requireNonNull(dataSnapshot.child("nameOfReceiver").getValue()).toString());
                        finalOrderModel.setMobileNumber(Objects.requireNonNull(dataSnapshot.child("mobileNumber").getValue()).toString());
                        finalOrderModel.setAddress(Objects.requireNonNull(dataSnapshot.child("address").getValue()).toString());
                        finalOrderModel.setKey(Objects.requireNonNull(dataSnapshot.child("key").getValue()).toString());

                        flist.add(finalOrderModel);

                    }else{
                        Toast.makeText(AdminPanel.this, "No Customer", Toast.LENGTH_SHORT).show();
                    }

                }
                adapter = new MainAdapter(AdminPanel.this,flist);
                recyclerView.setAdapter(adapter);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminPanel.this, "DB ERROR", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void ExitAdmin(View view) {
        FirebaseAuth.getInstance().signOut();
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        super.onBackPressed();
    }
}