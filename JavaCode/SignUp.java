package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SignUp extends AppCompatActivity {

    private EditText emailAddress;
    private EditText password;
    private EditText phone;
    private EditText name;
    private CheckBox userCheckBox,shopKepeercheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailAddress = findViewById(R.id.signUpEmailAddress);
        password = findViewById(R.id.signUpPassword);
        phone = findViewById(R.id.signUpPhone);
        name = findViewById(R.id.signUpUserName);
        userCheckBox= findViewById(R.id.signUpUserCheckBox);
        shopKepeercheckBox= findViewById(R.id.signUpShopKeepercheckBox);

    }
    public void signUpSignUp(View view){
        // Check if they dont fill any one feild inform her to fill
        if(emailAddress.getText().toString().equals("")||password.getText().toString().equals("")||
                phone.getText().toString().equals("")){
            Toast.makeText(this, "Fill All the Mandatory Feild", Toast.LENGTH_LONG).show();
        }
        // Check if they checked both check box inform her to fcheked only one
        else if(userCheckBox.isChecked()&&shopKepeercheckBox.isChecked()){
            Toast.makeText(this, "Check only one either ShopKeeper or User", Toast.LENGTH_LONG).show();
        }
        // if user checked store in user data base
        else if(userCheckBox.isChecked()) {
            try {
                DBHandler handler = new DBHandler(this);
                // check provided email in user and shopkeeper that if account already creted with that email
                if (handler.getNumberofAcountsShopKeeper(emailAddress.getText().toString()) != 0||handler.getNumberofAcountsUser(emailAddress.getText().toString()) != 0)
                    Toast.makeText(SignUp.this, "Account already created try another Email ", Toast.LENGTH_LONG).show();
                // when account not created before store info in database and go the Main screen
                else{
                    boolean res = handler.insertIntoUsers(name.getText().toString(), phone.getText().toString(), emailAddress.getText().toString(), password.getText().toString());
                    if(res){
                        Toast.makeText(SignUp.this, "User Account created", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(SignUp.this, "Account cant Created", Toast.LENGTH_LONG).show();
                    }

                }
                } catch(Exception e){
                    Toast.makeText(SignUp.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                }

        }
        // if shopkeeper checked store in shopkeeper data base
        else if (shopKepeercheckBox.isChecked()){
                try {
                    DBHandler handler = new DBHandler(this);
                    if (handler.getNumberofAcountsUser(emailAddress.getText().toString()) != 0 || handler.getNumberofAcountsShopKeeper(emailAddress.getText().toString()) != 0)
                        Toast.makeText(SignUp.this, "Account already created try another Email ", Toast.LENGTH_LONG).show();
                    else {
                        boolean res = handler.insertIntoShopkeeper(name.getText().toString(), phone.getText().toString(), emailAddress.getText().toString(), password.getText().toString());
                        if(res){
                            Toast.makeText(SignUp.this, "ShopKeeper Acount created ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SignUp.this, "Account cant Created", Toast.LENGTH_LONG).show();
                        }

                    }
                }
                catch(Exception e){
                    Toast.makeText(SignUp.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                }
        }
        // the no check box is checked then inform her to check atleast one
        else{
            Toast.makeText(this, "Please check one check box ShopKeeper or User", Toast.LENGTH_LONG).show();
        }
    }
}