package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopKeepers extends AppCompatActivity {

    private LinearLayout layout;
    private Button btn;
    String shopKepperId,shopKepperName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_keepers);
        TextView txt = findViewById(R.id.shopKeeperTexTempGreeting);
        layout = findViewById(R.id.shopKeeperLayout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //get data from previous screen
        shopKepperId = bundle.getString("idShopkeepers", "none");
        shopKepperName=bundle.getString("nameGreeting", "none");
        txt.setText("Hello " + shopKepperName);

        DBHandler hn = new DBHandler(this);
//      hn.dropCommentsView();
        Cursor crs = hn.getShopKeeperShop(Integer.parseInt(shopKepperId));
        crs.moveToFirst();
        for (int i = 0; i < crs.getCount(); i++) {
            // Add shopkeeper all shops record by creating button
            btn = new Button(this);
            btn.setText(crs.getString(1) + "  " + crs.getString(4) + "  " + crs.getString(6) + "  "+hn.getVisitingPlaceName(Integer.parseInt(crs.getString(9))) + "  ");
            btn.setId(Integer.parseInt(crs.getString(0)));
            layout.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in=newScreenShopDetail(v);
                    startActivity(in);
                }
            });
            crs.moveToNext();
        }


    }

    public void shopKeeperAddShop(View view) {
        // Shop keeper Add the shops by click on this button and they go to next screen Add shop
        Intent intent = new Intent(this, AddShop.class);
        Bundle bundle = new Bundle();
        bundle.putString("idShopkeepers", shopKepperId);
        bundle.putString("nameGreeting",shopKepperName);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    private Intent newScreenShopDetail(View v){

        Intent in = new Intent(this,ShopDetail.class);
        Bundle bn = new Bundle();
        bn.putString("ShopKeeperShopKeeperId", shopKepperId);
        bn.putString("nameGreeting",shopKepperName);
        bn.putString("ShopKeeperShopId", Integer.toString(v.getId()));
        in.putExtras(bn);
        return in;
    }

    public void shopKeeperLogOut(View view) {
        DBHandler hn = new DBHandler(this);
        hn.deleteSession();
        Toast.makeText(this, "Log Out", Toast.LENGTH_LONG).show();
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
    }
}