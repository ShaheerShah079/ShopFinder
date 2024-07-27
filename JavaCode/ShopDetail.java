package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShopDetail extends AppCompatActivity {
    private String shopDetailShopId,ShopDetailShopKeeperId,shopKepperName;
    private LinearLayout layout;
    private TextView txtComments,txtRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        layout=findViewById(R.id.shopDetailLinearLayout);
        txtRating=findViewById(R.id.shopDetailTextRating);

        Intent in = getIntent();
        Bundle bn = in.getExtras();
        // get data from previous screen
        shopDetailShopId=bn.getString("ShopKeeperShopId","none");
        ShopDetailShopKeeperId=bn.getString("ShopKeeperShopKeeperId","none");
        shopKepperName=bn.getString("nameGreeting", "none");
        DBHandler hn=new DBHandler(this);
        // get rating from data base shop table and put on screen
        float shopRating =hn.getShopRating(Integer.parseInt(shopDetailShopId));
        txtRating.setText(txtRating.getText().toString().concat("   "+shopRating));
        // get the comments on the shops and put it on text view and then add on layout
        Cursor cr=hn.getShopComments(Integer.parseInt(shopDetailShopId));
        cr.moveToFirst();
         for (int i=0;i<cr.getCount();i++){
             txtComments=new TextView(this);
             txtComments.setText(cr.getString(0)+"  "+cr.getString(1)+"  "+cr.getString(2));
             layout.addView(txtComments);
             cr.moveToNext();
         }
    }
    public void shopDetailEditShopDetail(View view){
        //goto edit shop detail
        Intent in = new Intent(this,EditShopDetail.class);
        Bundle bn = new Bundle();
        bn.putString("EditShopDetailShopId",shopDetailShopId);
        bn.putString("EditShopDetailShopKeeperId",ShopDetailShopKeeperId);
        in.putExtras(bn);
        startActivity(in);
    }
    public void onBackPressed(){
        // drop the comments view of shop and go to Shopkeeper main menu
        DBHandler hn = new DBHandler(this);
        hn.dropCommentsView();
        Intent in = new Intent(this,ShopKeepers.class);
        Bundle bn = new Bundle();
        bn.putString("nameGreeting",shopKepperName);
        bn.putString("idShopkeepers",ShopDetailShopKeeperId);
        in.putExtras(bn);
        startActivity(in);

    }
}
