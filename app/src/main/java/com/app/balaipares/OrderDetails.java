package com.app.balaipares;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class OrderDetails extends AppCompatActivity {
    TextView fOrders,fAddOns,fQuant,dOrders,dQuant,name,address,phone,payment;

    Button deliveredBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        fOrders = findViewById(R.id.admin_food_order);
        fAddOns = findViewById(R.id.admin_food_addOns);
        fQuant = findViewById(R.id.admin_food_quant);
        dOrders = findViewById(R.id.admin_drinks_order);
        dQuant = findViewById(R.id.admin_drinks_quant);
        name = findViewById(R.id.admin_name_receiver);
        address = findViewById(R.id.admin_address_receiver);
        phone = findViewById(R.id.admin_phone_receiver);
        payment = findViewById(R.id.admin_total_pay);
        deliveredBtn = findViewById(R.id.delivered);

        firebaseDatabase = FirebaseDatabase.getInstance();


        String food = getIntent().getStringExtra("FOOD_ORDERS");
        String addons = getIntent().getStringExtra("FOOD_ADD_ONS");
        String foodQuant = getIntent().getStringExtra("FOOD_QUANTITY");
        String drinks = getIntent().getStringExtra("DRINKS_ORDER");
        String drinksQuant = getIntent().getStringExtra("DRINKS_QUANTITY");
        String sName = getIntent().getStringExtra("NAME_RECEIVER");
        String sAddress = getIntent().getStringExtra("RECEIVER_ADDRESS");
        String sPhone = getIntent().getStringExtra("RECEIVER_PHONE");
        String sPay = getIntent().getStringExtra("PAYMENT");
        String key = getIntent().getStringExtra("KEY");

        fOrders.setText(food);
        fAddOns.setText(addons);
        fQuant.setText(foodQuant);
        dOrders.setText(drinks);
        dQuant.setText(drinksQuant);
        name.setText(sName);
        address.setText(sAddress);
        phone.setText(sPhone);
        payment.setText(sPay);

        deliveredBtn.setOnClickListener(view -> {

            //alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetails.this);
            //set title
            builder.setTitle("Item Delivered");
            //set message
            builder.setMessage("Are you sure that this order has been received?");
            //positive yes
            builder.setPositiveButton("YES", (dialog, which) -> {
                //remove order
                databaseReference = firebaseDatabase.getReference("admin/OrdersToDeliver");
                databaseReference.child(key).removeValue();
                Intent intent = new Intent(OrderDetails.this,AdminPanel.class);
                startActivity(intent);
                finishAffinity();
            });
            //negative no
            builder.setNegativeButton("NO", (dialog, which) -> {
                //dismiss dialog
                dialog.dismiss();
            });
            builder.create();
            builder.show();
        });
    }

    public void Backadmin(View view) {
        super.onBackPressed();
    }
}