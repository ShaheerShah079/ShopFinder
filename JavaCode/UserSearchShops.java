package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class UserSearchShops extends AppCompatActivity {
private LinearLayout layout;
private Button btn;
private String userId,userName;
private EditText vsPlace,shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_shops);
        vsPlace = findViewById(R.id.userSearchShopVsPlace);
        shop = findViewById(R.id.userSearchShopShops);
        layout=findViewById(R.id.UserSearchLinearLayout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getString("userId", "none");
        userName = bundle.getString("userName", "none");
//        DBHandler hn = new DBHandler(this);
//        Cursor crs = hn.searchShops();
//        crs.moveToFirst();
    }
    public void userSearchShopSearchShops(View view){
        layout.removeAllViews();
        DBHandler hn = new DBHandler(this);
        Cursor crs=hn.selectNull();
        //when user fill only Vising palace field then then search shops at the name of that visiting palace
        if (!vsPlace.getText().toString().equals("") && shop.getText().toString().equals("")) {
            crs = hn.userSearchShopByVsPlace(vsPlace.getText().toString());
        }
        //when user fill only shop name field then then search all shops of that name
        else if (vsPlace.getText().toString().equals("") && !shop.getText().toString().equals("")) {
            crs = hn.userSearchShopByShop(shop.getText().toString());
        }
        // when user fill both field then search the shop of that name exists on the given visiting palace
        else if (!vsPlace.getText().toString().equals("") && !shop.getText().toString().equals("")) {
            crs = hn.userSearchShopByVsPlaceAndShop(vsPlace.getText().toString(), shop.getText().toString());
        }
        else{
            Toast.makeText(this, "Select Atleast one field", Toast.LENGTH_LONG).show();
        }
        // check if first three if - else not execute then cursor contain null and after so dont show the button of searched shops
        if ((!crs.getColumnName(0).equals("null"))) {
            for (int i = 0; i < crs.getCount(); i++) {
                // Add Search record in screen by creating button and add it in linear layout
                btn = new Button(this);
                btn.setText(crs.getString(1) + "  " + crs.getString(4) + "  " + crs.getString(6) + "  " + hn.getVisitingPlaceName(Integer.parseInt(crs.getString(9))) + "  ");
                //set the id of the button(shop id) that use when user click on any button set
                // shop id when create button and retrive it when user click on it and send to next screen
                btn.setId(Integer.parseInt(crs.getString(0)));
                layout.addView(btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = newScreenShopDetail(v);
                        startActivity(in);
                    }
                });
                crs.moveToNext();
            }
        }
        else{
            Toast.makeText(this, "No Search Shop Found", Toast.LENGTH_LONG).show();
        }
        vsPlace.setText("");
        shop.setText("");
    }
    private Intent newScreenShopDetail(View v){
        Intent in = new Intent(this,UserSearchShopDetail.class);
        Bundle bn = new Bundle();
        bn.putString("userId", userId);
        bn.putString("UserSearchShopDetailShopId", Integer.toString(v.getId()));
        bn.putString("userName",userName);
        in.putExtras(bn);

        return in;
    }

    public void onBackPressed(){
        DBHandler hn = new DBHandler(this);
        hn.dropUserIncludedShopsView();

        // onBack press start the previous screen so that oncreate function call and user save shops appear in main user menu
        Intent intent = new Intent(this, Users.class);
        Bundle bundle = new Bundle();
        bundle.putString("userName", userName);
        bundle.putString("userId", userId);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}