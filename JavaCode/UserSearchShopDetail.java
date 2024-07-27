package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserSearchShopDetail extends AppCompatActivity {
    private TextView overAllRating,shopAddress;
    private EditText userRating,comments;
    private String userId,shopId,userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_shop_detail);
        DBHandler hn =new DBHandler(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getString("userId", "none");
        shopId = bundle.getString("UserSearchShopDetailShopId", "none");
        userName = bundle.getString("userName", "none");

        comments = findViewById(R.id.userSearchShopDetailShopComments);
        userRating = findViewById(R.id.userSearchShopDetailUserRating);
        // get the shop rating
        overAllRating=findViewById(R.id.userSearchShopDetailShopRating);
        float shopRating =hn.getShopRating(Integer.parseInt(shopId));
        overAllRating.setText(overAllRating.getText().toString().concat("   "+shopRating));
        // get the shop address
        shopAddress=findViewById(R.id.userSearchShopDetailShopAddress);
        String address=hn.getShopAddress(Integer.parseInt(shopId));
        shopAddress.setText(shopAddress.getText()+" "+address);
    }
    public void saveShopAsUser(View view){
        // Save the shop in Include Table so User Save it and its View in there Main Menu
        DBHandler hn = new DBHandler(this);
        if(hn.isRecordNotSaveInInclude(Integer.parseInt(userId),Integer.parseInt(shopId))){
            hn.insertIntoIncludes(Integer.parseInt(userId), Integer.parseInt(shopId));
            Toast.makeText(this, "Shop Save in Your List", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Shop Already Saved", Toast.LENGTH_LONG).show();
        }

    }
    public void onBackPressed(){
        DBHandler hn = new DBHandler(this);
        // when user go back check if user already view the shop if not then insert there mark comment and rating of the shop
        if(hn.userNotViewShopBefore(Integer.parseInt(userId),Integer.parseInt(shopId))){
            if(comments.getText().toString()!=""){
                hn.insertCommentIntoViewing(Integer.parseInt(shopId),Integer.parseInt(userId),comments.getText().toString());
            }
            if(userRating.getText().toString()!=""){
                hn.updateShopRating(Float.parseFloat(userRating.getText().toString()),Integer.parseInt(shopId));
            }
        }
        // if user already view shop update the comment but rating cant update as there issue of rating in the database
        else{
            if(comments.getText().toString()!=""){
                hn.updateCommentIntoViewing(Integer.parseInt(shopId),Integer.parseInt(userId),comments.getText().toString());
            }
        }
        // onBack press start the previous screen so that oncreate function call and user save shops appear in main user menu
        Intent in = new Intent(this,UserSearchShops.class);
        Bundle bn = new Bundle();
        bn.putString("userId",userId);
        bn.putString("userName",userName);
        in.putExtras(bn);
        startActivity(in);
    }
}