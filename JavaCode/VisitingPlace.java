package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class VisitingPlace extends AppCompatActivity {
    private EditText name,location,city,about,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visiting_place);
        name=findViewById(R.id.VisitingPlacePlaceName);
        location=findViewById(R.id.VisitingPlacePlaceLocation);
        city =findViewById(R.id.VisitingPlacePlaceCity);
        about =findViewById(R.id.VisitingPlaceAboutPlace);
        type =findViewById(R.id.VisitingPlacePlaceType);

    }
    public void VisitingPlaceSave(View view) {
        // Check if they dont fill the mandatory field inform her to fill
        if (name.getText().toString().equals("") || location.getText().toString().equals("") || type.getText().toString().equals("")) {
            Toast.makeText(this, "Fill All the Mandatory Feild", Toast.LENGTH_LONG).show();

        }
        // if mandatory field is filled then put in visiting place database
        else {
            DBHandler hn = new DBHandler(this);
            boolean res = hn.insertIntoVisitingPlaces(name.getText().toString(), location.getText().toString(), city.getText().toString(), about.getText().toString(),
                    type.getText().toString());
            if(res) {
                Toast.makeText(this, "Visiting Place Added", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "Place Cant added", Toast.LENGTH_LONG).show();
            }
            // if Vp added or not but reset all the field
            name.setText("");
            location.setText("");
            type.setText("");
            about.setText("");
            city.setText("");
        }
    }
}