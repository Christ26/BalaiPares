package com.app.balaipares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeliverAddressActivity extends AppCompatActivity {
    EditText fullName,mobileNum,state,city,street;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_address);
    }

    public void doneBtn(View view) {

        rootNode = FirebaseDatabase.getInstance();


        fAuth = FirebaseAuth.getInstance();
        String userId = fAuth.getCurrentUser().getUid();
        reference = rootNode.getReference("admin/OrdersToDeliver");

        fullName = findViewById(R.id.fullname);
        mobileNum = findViewById(R.id.mobilenumber);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);

        String fName = fullName.getText().toString();
        String mNumber = mobileNum.getText().toString();
        String mState = state.getText().toString();
        String mCity = city.getText().toString();
        String mStreet = street.getText().toString();

        if (TextUtils.isEmpty(fName)){
            fullName.setError("Required");
        }
        if (TextUtils.isEmpty(mNumber)){
            mobileNum.setError("Required");
        }
        if (TextUtils.isEmpty(mState)){
            state.setError("Required");
        }
        if (TextUtils.isEmpty(mCity)){
            city.setError("Required");
        }
        if (TextUtils.isEmpty(mStreet)){
            street.setError("Required");
        }
        if (!TextUtils.isEmpty(fName) && !TextUtils.isEmpty(mNumber) && !TextUtils.isEmpty(mState) && !TextUtils.isEmpty(mCity) && !TextUtils.isEmpty(mStreet)){
            Orders.NameOfReceiver = fName;
            Orders.mobileNumber = mNumber;
            Orders.address = mState+","+mCity+","+mStreet;
            Long tsLong = System.currentTimeMillis()/10000;
            String key = tsLong.toString();

            FinalOrderModel finalOrderModel = new FinalOrderModel(Orders.Orders,Orders.AddOns,Orders.Drinks,Orders.fQuantity,Orders.dQuantity,Orders.totalPayement,Orders.NameOfReceiver,Orders.mobileNumber,Orders.address,key);
            reference.child(key).setValue(finalOrderModel);

            Intent intent = new Intent(DeliverAddressActivity.this,SuccessActivity.class);
            Orders.Orders = "";
            Orders.address ="";
            Orders.mobileNumber ="";
            Orders.totalPayement ="";
            Orders.Drinks = "";
            Orders.AddOns = "";
            Orders.NameOfReceiver = "";
            Orders.dQuantity = "";
            Orders.fQuantity = "";

            ParesOrder.Order = "";
            ParesOrder.Quantity = "";
            ParesOrder.AddOns = "";
            ParesOrder.ParesTotalPay = "";

            MamiOrder.Order = "";
            MamiOrder.Quantity = "";
            MamiOrder.AddOns = "";
            MamiOrder.MamiTotalPay = "";


            DrinksOrder.Drinks = "";
            DrinksOrder.Quantity = "";
            DrinksOrder.payTotal = "";
            startActivity(intent);
            finish();

        }

    }
}