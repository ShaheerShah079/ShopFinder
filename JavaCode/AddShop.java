package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddShop extends AppCompatActivity {
     private EditText shopName,streetNum,shopNum,city,shopArea,shopType,vPlace;
    String id,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        id=bundle.getString("idShopkeepers","none");
        name=bundle.getString("nameGreeting","none");
        shopName=findViewById(R.id.addShopShopName);
        streetNum=findViewById(R.id.addShopStreetNumber);
        shopNum=findViewById(R.id.addShopShopNumber);
        city=findViewById(R.id.addShopShopCity);
        shopArea=findViewById(R.id.addShopShopArea);
        shopType=findViewById(R.id.addShopShopType);
        vPlace=findViewById(R.id.addShopVisitingPlace);
    }
    public void addShopSave (View view) {
        try {
            DBHandler hn = new DBHandler(this);
            // check not any field is empty
            if (!(shopName.getText().toString().equals("") || shopNum.getText().toString().equals("")
                    || streetNum.getText().toString().equals("") || city.getText().toString().equals("")
                    || shopArea.getText().toString().equals("") || shopType.getText().toString().equals("")
                    ||vPlace.getText().toString().equals("")) ){
                //Get info of visiting palace that shopkeeper want to add there Shop
                Cursor crsVisitingPlace=hn.getVisitingPlaceRecord(vPlace.getText().toString());
                //check if the place exists where shop kepper want to add shop if yes then add shop if no then inform her
                if(crsVisitingPlace.moveToFirst()){
                    hn.insertIntoShops(shopName.getText().toString(), shopNum.getText().toString(), streetNum.getText().toString()
                            , city.getText().toString(), shopArea.getText().toString(), shopType.getText().toString(),
                            Integer.parseInt(id), Integer.parseInt(crsVisitingPlace.getString(0)));
                    Toast.makeText(this, "Shop Added", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this,ShopKeepers.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("nameGreeting",name);
                    bundle.putString("idShopkeepers", id);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "No Visiting Palace Found Tell Owner to Add this Palace", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(this, "Fill All Fields", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e){
            Toast.makeText(this, e.getMessage()+" ", Toast.LENGTH_LONG).show();
        }
    }
}