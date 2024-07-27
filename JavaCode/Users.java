package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Users extends AppCompatActivity {
    private String userId,userName;
    private TextView txt;
    private LinearLayout layout;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        txt = findViewById(R.id.UserGreetingText);
        layout=findViewById(R.id.UserLayoutIncluded);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getString("userId", "none");
        userName=bundle.getString("userName","none");
        txt.setText("Hello " + userName);

        DBHandler hn = new DBHandler(this);
        // create a view in function getUserIncludedShops() and and get all the shop that user included in Included data base
        try{
            Cursor crs = hn.getUserIncludedShops(Integer.parseInt(userId));
            crs.moveToFirst();

            // Add the Record in screen by creating button and add it in linear layout
            for (int i=0;i<crs.getCount();i++){
                btn=new Button(this);
                btn.setText(crs.getString(1)+"   "+crs.getString(4)+"   "+crs.getString(6)+" "+hn.getVisitingPlaceName(Integer.parseInt(crs.getString(9))));
                //set the id of the button(shop id) that use when user click on any button set
                // shop id when create button and retrive it when user click on it and send to next screen
                btn.setId(Integer.parseInt(crs.getString(0)));
                layout.addView(btn);
                // set the onclick listener on button go to next User Search shop screen when button is clicked
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in= userIncludedShops(v);
                        startActivity(in);
                    }
                });
                crs.moveToNext();
            }
        }
        catch (Exception e){
            // if error then view already created soo drop the view
//            hn.dropUserIncludedShopsView();
        }
    }

    private Intent userIncludedShops(View v){
        // When user click on any button go to next screen with user and shop id shop id is set when button is created
        Intent in = new Intent(this, UserSavedShopDetail.class);
        Bundle bn = new Bundle();
        bn.putString("userId",userId);
        bn.putString("userName",userName);
        bn.putString("User_ShopId", Integer.toString(v.getId()));
        in.putExtras(bn);
        return in;
    }
    public void searchShops(View view){
        // when user click on search shop go to that screen
        Intent in= new Intent(this,UserSearchShops.class);
        Bundle bn = new Bundle();
        bn.putString("userId",userId);
        bn.putString("userName",userName);

        in.putExtras(bn);
        startActivity(in);
    }

    @Override
    public void onBackPressed() {
        // when screen start include shop of user show by an view so when use go back to main screen drop the view
        Log.d("MyTag","Back");
        DBHandler hn = new DBHandler(this);
        hn.dropUserIncludedShopsView();
        startActivity(new Intent(this,MainActivity.class));
    }
    public void userLogOut(View view) {
        DBHandler hn = new DBHandler(this);
        hn.deleteSession();
        Toast.makeText(this, "Log Out", Toast.LENGTH_LONG).show();
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
    }
}