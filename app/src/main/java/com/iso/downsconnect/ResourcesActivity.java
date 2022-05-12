package com.iso.downsconnect;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Resource;

import java.util.ArrayList;
//activity that displays info about existing resources in the db
public class ResourcesActivity extends AppCompatActivity {
    private TextView ndss, ndsc, dsdn;
    private Button add;
    private LinearLayout resourceLayout;
    private ArrayList<Resource> resources = new ArrayList<>();
    private DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);


//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        final int childID = sharedPreferences.getInt("name", 1);

        //initialize variables
        final Button back = findViewById(R.id.backButton);
        ndss = findViewById(R.id.ndssText);
        ndsc = findViewById(R.id.ndscText);
        dsdn = findViewById(R.id.dsdnText);
        add = findViewById(R.id.addResouceBtn);
        helper = new DBHelper(this);
        resourceLayout = findViewById(R.id.resourceLayout);

        //get all the resources in the db
        resources = helper.getResources();

        //button that navigates to page for adding new resources
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResourcesActivity.this, AddResourceActivity.class);
                startActivity(intent);
            }
        });

        //base resources; when user clicks on one it navigates to the website on the internet
        ndss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.ndss.org/"));
                startActivity(intent);
            }
        });

        ndsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.ndsccenter.org/"));
                startActivity(intent);
            }
        });

        dsdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.dsdiagnosisnetwork.org/"));
                startActivity(intent);
            }
        });


        //button for navigting back to the home screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResourcesActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        //displays the information for each resource in the db
        for (final Resource resource: resources) {
            //create layout parameters for each linear layout
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            // set margins for parameters
            layoutParams.setMargins(0, 0, 0, 30);
            textParams.setMargins(0, 30, 0, 50);
            marginLayoutParams.setMargins(20, 0, 50,10);

            //create horizontal layout for holding objects
            final LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setLayoutParams(marginLayoutParams);
            horizontalLayout.setId(resource.getResourceID());

            //textview for displaying the resource name
            TextView resName = new TextView(this);
            resName.setText(resource.getName());
            resName.setTextSize(15);
            resName.setTextColor(Color.parseColor("#0645AD"));
            resName.setWidth(450);
            resName.setLayoutParams(textParams);
//            resName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                    intent.setData(Uri.parse(resource.getURL()));
//                    startActivity(intent);
//                }
//            });

            //button that navigates to the website on the internet
            Button visit = new Button(getApplicationContext());
            visit.setText("Visit");
            visit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(resource.getURL()));
                    startActivity(intent);
                }
            });

            //button to delete resources from the db
            Button delete = new Button(getApplicationContext());
            delete.setText("Delete");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //displays dialog to asks user if they want to continue with deleting the resource
                    new AlertDialog.Builder(ResourcesActivity.this)
                            .setTitle("Delete Resource")
                            .setMessage("Are you sure you want to delete this resource?")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //if yes is clicked, the resource is deleted as well as it's corresponding layout
                                    helper.deleteEntry(resource.getResourceID(), "Resource");
                                    resourceLayout.removeView(findViewById(resource.getResourceID()));
                                    Toast.makeText(getApplicationContext(), "Deleted a resource", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("no", null).show();
                }
            });
            visit.setWidth(1);
            visit.setLayoutParams(layoutParams);

            //add the elements to the horizontal layout and add horizontal layout to main linear layout
            horizontalLayout.addView(resName);
            horizontalLayout.addView(visit);
            horizontalLayout.addView(delete);
            resourceLayout.addView(horizontalLayout);
        }
    }
}
