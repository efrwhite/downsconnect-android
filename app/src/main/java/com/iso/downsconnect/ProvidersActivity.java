package com.iso.downsconnect;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.iso.downsconnect.objects.Provider;

public class ProvidersActivity extends AppCompatActivity {
    private EditText name, practice, number, email, website, fax, address_one, address_two, state, city, zip;
    private Spinner specialty;
    private Button save;
    private DBHelper helper;
    private Provider provider = new Provider();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        final int p_name = getIntent().getIntExtra("p_name", -1);

        final Button back = findViewById(R.id.backButton);
        name = findViewById(R.id.providerName);
        specialty = findViewById(R.id.specialtySpinner);
        number = findViewById(R.id.numberEdit);
        fax = findViewById(R.id.faxEdit);
        email = findViewById(R.id.emailEdit);
        website = findViewById(R.id.websiteEdit);
        address_one = findViewById(R.id.addressOneEdit);
        state = findViewById(R.id.stateEdit);
        address_two = findViewById(R.id.address_twoEdit);
        city = findViewById(R.id.cityEdit);
        zip = findViewById(R.id.zipEdit);
        practice = findViewById(R.id.practiceEdit);
        save = findViewById(R.id.saveButton);
        helper = new DBHelper(this);

        //set information for exisitng provider if one is provided
        if(p_name != -1) {
            provider = helper.getProvider(p_name);
            if (provider != null) {
                name.setText(provider.getName());
                //calls private method getIndex
                specialty.setSelection(getIndex(specialty, provider.getSpecialty()));
                number.setText(provider.getPhone());
                fax.setText(provider.getFax());
                email.setText(provider.getEmail());
                website.setText(provider.getWebsite());
                state.setText(provider.getState());
                city.setText(provider.getCity());
                zip.setText(provider.getZip());
                practice.setText(provider.getPrac_name());
                if (provider.getAddress().contains(";")) {
                    String address = provider.getAddress();
                    address_one.setText(provider.getAddress().substring(0, address.indexOf(";")));
                    address_two.setText(provider.getAddress().substring(address.indexOf(";")));
                } else {
                    address_one.setText(provider.getAddress());
                }
            }
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProvidersActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals("") && name.getText().toString().contains(" ") && !practice.getText().toString().equals("")
                        && !specialty.getSelectedItem().toString().equals("Select") && !number.getText().toString().equals("")
                        && !email.getText().toString().equals("") && !address_one.getText().toString().equals("")
                        && !state.getText().toString().equals("") && !city.getText().toString().equals("") && !zip.getText().toString().equals("")){
                    if(!address_two.getText().toString().equals("")){
                        provider.setAddress(name.getText().toString() + ";" + address_two.getText().toString());
                    }
                    else{
                        provider.setAddress(address_one.getText().toString());
                    }
                    provider.setName(name.getText().toString());
                    provider.setPrac_name(practice.getText().toString());
                    provider.setSpecialty(specialty.getSelectedItem().toString());
                    provider.setPhone(number.getText().toString());
                    if(!fax.getText().toString().equals("")){
                        provider.setFax(fax.getText().toString());
                    }
                    provider.setEmail(email.getText().toString());
                    if(!website.getText().toString().equals("")){
                        provider.setWebsite(website.getText().toString());
                    }
                    provider.setState(state.getText().toString());
                    provider.setCity(city.getText().toString());
                    provider.setZip(zip.getText().toString());

                    if(p_name != -1){
                        helper.updateProvider(provider);
                    }
                    else {
                        boolean num = helper.addProvider(provider);
                    }
                    Intent intent = new Intent(ProvidersActivity.this, ActivityContainer.class);
                    startActivity(intent);
                }
                else{
                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });
    }

    //get index of selection in a spinner
    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;

    }
}
