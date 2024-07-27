package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {

    private EditText phone;
    private TextView txt;
    String email,cell,id,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        phone = findViewById(R.id.forgetPasswordPhone);
        txt= findViewById(R.id.forgetPasswordtemptext);
        Intent intent=getIntent();
        Bundle b = intent.getExtras();
        email=b.getString("Email","none");
        cell =b.getString("Cell","none");
        id = b.getString("Id","none");
        type=b.getString("Type","none");
    }

    public void forgetPasswordsearchAccount (View view) {
            // if number field is empty inform them to fill Number
            if(phone.getText().toString().equals(""))
                Toast.makeText(this,"Enter Your Phone Number",Toast.LENGTH_LONG).show();
//                txt.setText("Enter Your Phone Number");
            // if number match with actual phone number(actual number come from previous screen)
            // in the data base then go to newPassword screen to update password
            else if(phone.getText().toString().equals(cell)){
                Toast.makeText(this, "Change Your Password", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,NewPassword.class);
                Bundle bundle = new Bundle();
                bundle.putString("fPId", id);
                bundle.putString("fPtype",type);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                    Toast.makeText(this,"Wrong number",Toast.LENGTH_LONG).show();
            }
        }
    }