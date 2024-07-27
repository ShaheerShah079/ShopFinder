package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditShopDetail extends AppCompatActivity {
     private EditText name,num,street,area,type,vsPlace,city;
     DBHandler hn;
     public int shopid,shopkeeperId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop_detail);

        name =findViewById(R.id.EditShopDetailShopName);
        num =findViewById(R.id.EditShopDetailShopNumber);
        street =findViewById(R.id.EditShopDetailShopStreet);
        area =findViewById(R.id.EditShopDetailShopArea);
        type =findViewById(R.id.EditShopDetailShopType);
        vsPlace =findViewById(R.id.EditShopDetailVistingPlace);
        city =findViewById(R.id.EditShopDetailShopCity);

        Intent in = getIntent();
        Bundle bn = in.getExtras();
        shopid=Integer.parseInt(bn.getString("EditShopDetailShopId","none"));
        shopkeeperId=Integer.parseInt(bn.getString("EditShopDetailShopKeeperId","none"));
        hn= new DBHandler(this);
    }
    public void editName(View view){
        Toast.makeText(this,"Shop Name Changed",Toast.LENGTH_LONG).show();
        hn.changeShopName(shopid,name.getText().toString());
    }
    public void editNumber(View view){
        Toast.makeText(this,"Shop Number Changed",Toast.LENGTH_LONG).show();
        hn.changeShopNumber(shopid,num.getText().toString());
    }
    public void editStreet(View view){
        Toast.makeText(this,"Shop Street Changed",Toast.LENGTH_LONG).show();
        hn.changeShopStreet(shopid,street.getText().toString());
    }
    public void editArea(View view){
        Toast.makeText(this,"Shop Area Changed",Toast.LENGTH_LONG).show();
        hn.changeShopArea(shopid,area.getText().toString());
    }
    public void editType(View view){
        Toast.makeText(this,"Shop Type Changed",Toast.LENGTH_LONG).show();
        hn.changeShopType(shopid,type.getText().toString());
    }
    public void editCity(View view){
        Toast.makeText(this,"Shop City Changed",Toast.LENGTH_LONG).show();
        hn.changeShopCity(shopid,city.getText().toString());
    }
    public void editVsPlace(View view){
        Toast.makeText(this,"Visiting Place Changed",Toast.LENGTH_LONG).show();
        hn.changeShopvsPlace(shopid,vsPlace.getText().toString());
    }
    public void onBackPressed(){
        Intent in = new Intent(this,ShopDetail.class);
        Bundle bn = new Bundle();
        bn.putString("ShopKeeperShopId",Integer.toString(shopid));
        bn.putString("ShopKeeperShopKeeperId",Integer.toString(shopkeeperId));
        in.putExtras(bn);
        startActivity(in);

    }
}