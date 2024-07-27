package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewPassword extends AppCompatActivity {
     private EditText password;
    private EditText confirmPassword;
    String id,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        password =findViewById(R.id.newPasswordNewPassword);
        confirmPassword =findViewById(R.id.newPasswordConfirmNewPassword);
        Intent intent=getIntent();
        Bundle b = intent.getExtras();
        id=b.getString("fPId","none");
        type=b.getString("fPtype","none");
    }
    public void newPasswordsave (View view) {
        // when both password and confirm password match then procced otherwise show them to enter
        //Same password
       if(password.getText().toString().equals(confirmPassword.getText().toString())){
           DBHandler hn = new DBHandler(this);

           if(type.equals("User")){
               hn.changeUserPassword(Integer.parseInt(id), password.getText().toString());
           }
           else if (type.equals("ShopKeeper")){
               hn.changeShopKeeperPassword(Integer.parseInt(id), password.getText().toString());
           }
           // if any one userof shopkeeper password change inform password change and go to main screen
           Toast.makeText(this,"Your Password Change!!!",Toast.LENGTH_LONG).show();
           startActivity(new Intent(this,MainActivity.class));
       }
       else{
           Toast.makeText(this,"Password cant match!!!",Toast.LENGTH_LONG).show();
       }

    }
}