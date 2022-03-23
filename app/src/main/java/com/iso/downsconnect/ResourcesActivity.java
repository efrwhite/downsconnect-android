package com.iso.downsconnect;

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

import androidx.appcompat.app.AppCompatActivity;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Resource;

import java.util.ArrayList;

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

        final Button back = findViewById(R.id.backButton);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        ndss = findViewById(R.id.ndssText);
        ndsc = findViewById(R.id.ndscText);
        dsdn = findViewById(R.id.dsdnText);
        add = findViewById(R.id.addResouceBtn);
        helper = new DBHelper(this);
        resources = helper.getResources();
        resourceLayout = findViewById(R.id.resourceLayout);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResourcesActivity.this, AddResourceActivity.class);
                startActivity(intent);
            }
        });

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


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResourcesActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        for (final Resource resource: resources) {
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textParams.setMargins(0, 30, 0, 30);

            TextView resName = new TextView(this);
            resName.setText(resource.getName());
            resName.setTextSize(15);
            resName.setTextColor(Color.parseColor("#0645AD"));
            resName.setWidth(450);
            resName.setLayoutParams(textParams);
            resName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(resource.getURL()));
                    startActivity(intent);
                }
            });
            resourceLayout.addView(resName);
        }
    }
}
