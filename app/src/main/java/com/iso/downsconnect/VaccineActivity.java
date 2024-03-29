package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriPermission;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Image;
import com.iso.downsconnect.helpers.ImageHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
//Activity to keep track of vaccine history for a child
public class VaccineActivity extends AppCompatActivity {
    private Button back, choosePic, takePic, upload, delete;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int TAKE_PHOTO = 2;
//    private ImageView vaccineImage;
    private String path;
    private ContentResolver resolver;
    private List<UriPermission> permissions = new ArrayList<>();
    private ImageHelper imageHelper = new ImageHelper();
    //private Image image = new Image();
    private DBHelper dbHelper;
    private LinearLayout v_layout;
    private ArrayList<Image> images = new ArrayList<>();
    private ArrayList<Image> addImages = new ArrayList<>();
    private LinearLayout.LayoutParams imParams;
    private LinkedList<ImageView> currentImages = new LinkedList<>();
    private int childID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);

        //get current childID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        childID = sharedPreferences.getInt("name", 1);

        //initialize variables
        back = findViewById(R.id.backButton);
        choosePic = findViewById(R.id.choosePicture);
//        takePic = findViewById(R.id.takePicture);
        //vaccineImage = findViewById(R.id.vaccineImage);
        upload = findViewById(R.id.saveButton);
        resolver = getApplicationContext().getContentResolver();
        dbHelper = new DBHelper(this);
        v_layout = findViewById(R.id.imageLayout);
        delete = findViewById(R.id.deleteHistoryButton);

        //define parameters for an object
        imParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //get all the current vaccine info for the child from the db
        images = dbHelper.getChildVaccines(childID);
        int i = 0;
        //display images if there images to display
        if(images.size() > 0) {
            upload.setEnabled(false);
            for (Image image : images) {
                //create imageview to display each image
                ImageView v_image = new ImageView(this);
                currentImages.add(v_image);
                //convert byte array to bitmap using helper class and set bitmap as the image for the imageview
                Bitmap b = imageHelper.getImage(image.getImage());
                v_image.setVisibility(View.VISIBLE);
                v_image.setImageBitmap(b);
                //add image to layout
                v_layout.addView(v_image, imParams);
                i++;
            }
        }
        //button to navigate back to medical page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VaccineActivity.this, MedicalActivity.class);
                startActivity(intent);
            }
        });

        //opens up camera roll to allow user to select an image from their phone
        choosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_OPEN_DOCUMENT);
                i.putExtra("Uri", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(i, "Select Photo"), RESULT_LOAD_IMAGE);
            }
        });

        //button to delete the vaccine history for a child from the db
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload.setEnabled(true);
                new AlertDialog.Builder(VaccineActivity.this)
                        //dialog to ask user if they want to delete the vaccine history
                        .setTitle("Delete records")
                        .setMessage("Are you sure you want to delete your child's current vaccine records?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //if yes if clicked, delete each image with the child ID of the child that currently selected
                                for(Image image: images) {
                                    dbHelper.deleteEntry(image.getImageID(), "Image");
                                }
                                //remove teh corresponding imageviews/layout from the screen
                                for(ImageView image: currentImages){
                                    v_layout.removeView(image);
                                }
                                //display success messagae
                                Toast.makeText(VaccineActivity.this, "Successfully delete records", Toast.LENGTH_SHORT);
                            }
                        })
                        .setNegativeButton("no", null).show();
            }
        });

        //button to upload the vaccine history to the db
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(VaccineActivity.this)
                        //dialog box to ask if user wants to add image to vaccine history
                        .setTitle("Upload history")
                        .setMessage("Are you sure you want to upload these pictures for your child?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (addImages.size() > 0) {
                                    //if yes is clicked, adds each image to the db
                                    for (Image im : addImages) {
                                        dbHelper.addImage(im);
                                    }
                                    Intent intent = new Intent(VaccineActivity.this, MedicalActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("no", null).show();
            }
        });
    }

    @Override
    //returns with the information the user selected from their photos
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView v_image = new ImageView(this);
        //check if the request codes are correct and the image was returned
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            //create bitmap and store the image as the bitmap
            Bitmap b;
            try {
                //stores the image in the bitmap
                b = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                //determines if you have the necessary version and stores the path of the image
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    path = Objects.requireNonNull(data.getData()).getPath();
                //display the image in the imageview
                v_image.setImageBitmap(b);
                v_image.setVisibility(View.VISIBLE);

                //save image info to the image object
                Image newImg = new Image();
                newImg.setImage(imageHelper.getBytes(b));
                newImg.setChildID(childID);

                //add the new image to the image array and add the new image imageview to the layout
                addImages.add(newImg);
                v_layout.addView(v_image, imParams);

                //display image correctly depending on their x and y values
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                if(b.getWidth() == size.x && b.getHeight() == size.y){
                    v_image.setRotation(0);
                }else{
                    v_image.setRotation(90);
                }
            } catch(IOException e) {
                e.printStackTrace();
            } catch(NullPointerException npe) {
                npe.printStackTrace();
            }
        }
    }

    //unused methods
    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private boolean hasDefaultCameraApp() {
        final PackageManager packageManager = getPackageManager();
        final Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"vaccine.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}