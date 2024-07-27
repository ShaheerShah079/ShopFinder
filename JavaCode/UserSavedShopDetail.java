package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserSavedShopDetail extends AppCompatActivity {
    private String ShopId,UserId,userName;
    private LinearLayout layout;
    private TextView txtComments,txtRating,txtAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_save_shop_detail);
        DBHandler hn = new DBHandler(this);
        Intent in = getIntent();
        Bundle bn = in.getExtras();
        // get the user id and shop id from previos screen
        ShopId = bn.getString("User_ShopId", "none");
        UserId = bn.getString("userId", "none");
        userName=bn.getString("userName", "none");
        // get the rating of shop by shop_id in data base shop table
        txtRating = findViewById(R.id.userShopDetailTextRating);
        float shopRating = hn.getShopRating(Integer.parseInt(ShopId));
        txtRating.setText(txtRating.getText().toString().concat("   " + shopRating));
        //// get the address of shop by shop_id in data base shop table
        txtAddress = findViewById(R.id.userShopAddress);
        String address = hn.getShopAddress(Integer.parseInt(ShopId));
        txtAddress.setText(txtAddress.getText() + " " + address);
        try {
            // get the cooments of the shop by shop id in viewing table
            // then create a textView and set comment data in it and add it in layout
            layout = findViewById(R.id.userShopCommentslinearLayout);
            Cursor crs = hn.getUserShopComments(Integer.parseInt(ShopId));
            crs.moveToFirst();
            for (int i = 0; i < crs.getCount(); i++) {
                txtComments = new TextView(this);
                txtComments.setText(crs.getString(0) + "  " + crs.getString(1) + "  " + crs.getString(2));
                layout.addView(txtComments);
                crs.moveToNext();
            }
        } catch (Exception e) {
            //error occur some time so drop the view
            hn.dropUserCommentsView();
        }
    }
    @Override
    public void onBackPressed() {
        // when screen start coment view created so when use go back to drop the view
        Log.d("MyTag","Back");
        DBHandler hn = new DBHandler(this);
        hn.dropUserIncludedShopsView();
        hn.dropUserCommentsView();
        Intent intent = new Intent(this, Users.class);
        Bundle bundle = new Bundle();
        bundle.putString("userName", userName);
        bundle.putString("userId", UserId);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
