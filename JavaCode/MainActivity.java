package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import android.widget.EditText;

import android.database.Cursor;

public class MainActivity extends AppCompatActivity {
    private EditText EmailAddress,Password;
    private String adminEmail ="shopfinder12@gmail.com", adminPassword ="test1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmailAddress=findViewById(R.id.mainEmailAddress);
        Password=findViewById(R.id.mainPassword);
        EmailAddress.setText("");
        Password.setText("");
        DBHandler hn=new DBHandler(this);
//        hn.dropIncludedShops();
//        hn.dropCommentsView();
//       Cursor crs=hn.test();
//        Log.e(TAG, crs.isNull(0)+" test  ");
        Cursor crsSessionExists=hn.isSessionExist();
        if(crsSessionExists.moveToFirst()){
            Log.d("MyTag",crsSessionExists.getString(2)+" type");
            if(crsSessionExists.getString(2).equals("User")){
                Log.d("MyTag","1");
                Cursor userEmailPasswordInDB = hn.getUserLoginEmailPass(crsSessionExists.getString(0),crsSessionExists.getString(1));
                Log.d("MyTag","2");
                Intent intent = new Intent(this, Users.class);
                Log.d("MyTag","3");
                Bundle bundle = new Bundle();
                Log.d("MyTag","4");
                bundle.putString("userName", userEmailPasswordInDB.getString(1));
                Log.d("MyTag","5");
                bundle.putString("userId", userEmailPasswordInDB.getString(0));
                Log.d("MyTag","6");
                intent.putExtras(bundle);
                Log.d("MyTag","7");
                startActivity(intent);
            }
            else if(crsSessionExists.getString(2).equals("ShopKeeper")){
                Cursor shopkeeperEmailPasswordInDB = hn.getShopKeeperLoginEmailPass(crsSessionExists.getString(0),crsSessionExists.getString(1));
                Intent in=new Intent(this, ShopKeepers.class);
                Bundle bundle = new Bundle();
                bundle.putString("nameGreeting", shopkeeperEmailPasswordInDB.getString(1));
                bundle.putString("idShopkeepers", shopkeeperEmailPasswordInDB.getString(0));
                in.putExtras(bundle);
                startActivity(in);
            }
        }
    }
//private Intent newShopkeeperScreen(Cursor shopkeeperEmailPasswordInDB){
//    Intent intent =
//    return intent;
//}
    public void mainLogIn (View view) {
//        if(LogIn.login==true){
//            DBHandler hn = new DBHandler(this);
//            Cursor resultShopKeeper = hn.getShopKeeperLoginEmailPass(LogIn.email, LogIn.pass);
//            Intent intent = new Intent(this, ShopKeepers.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("nameGreeting", resultShopKeeper.getString(1));
//            bundle.putString("idShopkeepers", resultShopKeeper.getString(0));
//
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
//        else
        // if both email and paswword text box is empty
        if (EmailAddress.getText().toString().equals("") && Password.getText().toString().equals("")) {
            Toast.makeText(this, "Please Enter Email Password", Toast.LENGTH_LONG).show();
        }
        // if only any one of email or paswword text box is empty
        else if (!EmailAddress.getText().toString().equals("") && Password.getText().toString().equals("")||
                EmailAddress.getText().toString().equals("") && !Password.getText().toString().equals("")) {
            EmailAddress.setText("");
            Password.setText("");
            Toast.makeText(this, "Log in Again With All Input Feilds", Toast.LENGTH_LONG).show();
        }
        // if admin login the application admin only add the Visiting place
        else if(EmailAddress.getText().toString().equals(adminEmail)&&
                Password.getText().toString().equals(adminPassword)){
            Intent i = new Intent(this,VisitingPlace.class);
            startActivity(i);
        }
        else {
            LogInAsUserOrShopKeeper();
        }
    }
    private void LogInAsUserOrShopKeeper(){
        DBHandler hn = new DBHandler(this);
        //check result of email password first both email password exists as user or shopkeeper if not
        //then check if only email exist as user or shopkeeper
        Cursor userEmailPasswordInDB = hn.getUserLoginEmailPass(EmailAddress.getText().toString(), Password.getText().toString());
        Cursor shopkeeperEmailPasswordInDB = hn.getShopKeeperLoginEmailPass(EmailAddress.getText().toString(), Password.getText().toString());
        Cursor userEmailInDB = hn.getUserLoginEmail(EmailAddress.getText().toString());
        Cursor shopKepperEmailInDB = hn.getShopKeeperLoginEmail(EmailAddress.getText().toString());
//      check if the email and password exists in user data base if so go to user screen
        if (userEmailPasswordInDB.moveToFirst()&&EmailAddress.getText().toString().equals(userEmailPasswordInDB.getString(3))
                && Password.getText().toString().equals(userEmailPasswordInDB.getString(4))) {
            // save the session in table so user dont need to log in again
            boolean isSessionSave=hn.saveSession(EmailAddress.getText().toString(),Password.getText().toString(),"User");
            if(isSessionSave){
                Toast.makeText(this, "Session saved User Log In ", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "User Log In", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(this, Users.class);
            Bundle bundle = new Bundle();
            bundle.putString("userName", userEmailPasswordInDB.getString(1));
            bundle.putString("userId", userEmailPasswordInDB.getString(0));
            intent.putExtras(bundle);

            startActivity(intent);
        }
//      check if the email and password exists in shopKeeper data base if so go to shopkeeper screen
        else if(shopkeeperEmailPasswordInDB.moveToFirst()&&EmailAddress.getText().toString().equals(shopkeeperEmailPasswordInDB.getString(3))
                && Password.getText().toString().equals(shopkeeperEmailPasswordInDB.getString(4))) {

//             LogIn.login=true;
//             LogIn.email=EmailAddress.getText().toString();
//             LogIn.pass=Password.getText().toString();
            // save the session in table so user dont need to log in again
            boolean isSessionSave=hn.saveSession(EmailAddress.getText().toString(),Password.getText().toString(),"ShopKeeper");
            if(isSessionSave){
                Toast.makeText(this, "Session saved Shopkeer Log In", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "Shopkeer Log In", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(this, ShopKeepers.class);
            Bundle bundle = new Bundle();
            bundle.putString("nameGreeting", shopkeeperEmailPasswordInDB.getString(1));
            bundle.putString("idShopkeepers", shopkeeperEmailPasswordInDB.getString(0));

            intent.putExtras(bundle);
            startActivity(intent);
        }
//      if email and password dont exists for user and shopKeeper data base then check for
//      only email in user database if so then go to forget password with some detail that check
//      in that screen to update pasword
        else if (userEmailInDB.moveToFirst()&&EmailAddress.getText().toString().equals(userEmailInDB.getString(3))) {
            Intent intent = new Intent(this, ForgetPassword.class);
            Bundle b = new Bundle();
            b.putString("Email", EmailAddress.getText().toString());
            b.putString("Cell", userEmailInDB.getString(2));
            b.putString("Id", userEmailInDB.getString(0));
            b.putString("Type","User");
            intent.putExtras(b);
            startActivity(intent);
        }
//      if email and password dont exists for user and shopKeeper data base then check for
//      only email in shopkeeper database if so then go to forget password with some detail that check
//      in that screen to update pasword
        else if (shopKepperEmailInDB.moveToFirst()&&EmailAddress.getText().toString().equals(shopKepperEmailInDB.getString(3))) {
            Intent intent = new Intent(this, ForgetPassword.class);
            Toast.makeText(this, "fp "+ shopKepperEmailInDB.getString(0), Toast.LENGTH_LONG).show();
            Bundle b = new Bundle();
            b.putString("Email", EmailAddress.getText().toString());
            b.putString("Cell", shopKepperEmailInDB.getString(2));
            b.putString("Id", shopKepperEmailInDB.getString(0));
            b.putString("Type","ShopKeeper");
            intent.putExtras(b);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Cant find your Account Enter Again", Toast.LENGTH_LONG).show();
            EmailAddress.setText("");
            Password.setText("");
        }
    }
    // Go to Sgn up Screen
    public void mainSignUp (View view) {
        new DBHandler(this);
        Toast.makeText(this,"Sign Up",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }
}

// main , sign up ,visiting palace, forget pass , new pass , user,user save shop detail , user search shop , user search shop detail
// shopkeeper , shop detail m edit shop detail , add shop