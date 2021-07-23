package com.example.downsconnect;

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
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.downsconnect.objects.Image;
import com.example.downsconnect.objects.ImageHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        childID = sharedPreferences.getInt("name", 1);

        //image.setChildID(childID);
        back = findViewById(R.id.backButton);
        choosePic = findViewById(R.id.choosePicture);
//        takePic = findViewById(R.id.takePicture);
        //vaccineImage = findViewById(R.id.vaccineImage);
        upload = findViewById(R.id.saveButton);
        resolver = getApplicationContext().getContentResolver();
        dbHelper = new DBHelper(this);
        v_layout = findViewById(R.id.imageLayout);
        delete = findViewById(R.id.deleteHistoryButton);

        imParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        images = dbHelper.getChildVaccines(childID);
        Log.i("thesize", String.valueOf(images.size()));
        int i = 0;
        if(images.size() > 0) {
            upload.setEnabled(false);
            for (Image image : images) {
                ImageView v_image = new ImageView(this);
                currentImages.add(v_image);
                Bitmap b = imageHelper.getImage(image.getImage());
                v_image.setVisibility(View.VISIBLE);
                v_image.setImageBitmap(b);
                v_layout.addView(v_image, imParams);
//                Log.i("anop", "Put " +  String.valueOf(i) + " in");
                i++;
            }
        }

//        UriPermission uri = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            permissions = getApplicationContext().getContentResolver().getPersistedUriPermissions();
//            if(permissions.size() > 0) {
//                uri = permissions.get(0);
//                vaccineImage.setImageURI(uri.getUri());
//            }
//        }

//        Bitmap bip = null;
//        try {
//            bip = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse("content://com.android.providers.media.documents/document/image%3A74"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        vaccineImage.setImageBitmap(bip);
//        File sd = Environment.getExternalStorageDirectory();
//        File img = new File( "image/document/image:71");
//        Bitmap bip = BitmapFactory.decodeFile(img.getAbsolutePath());
//        vaccineImage.setImageBitmap(bip);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VaccineActivity.this, MedicalActivity.class);
                startActivity(intent);
            }
        });

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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload.setEnabled(true);
                new AlertDialog.Builder(VaccineActivity.this)
                        .setTitle("Sign Out")
                        .setMessage("Are you sure you want to delete your child's current vaccine records?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for(Image image: images) {
                                    dbHelper.deleteEntry(image.getImageID(), "Image");
                                }
                                for(ImageView image: currentImages){
                                    v_layout.removeView(image);
                                }
                                Toast.makeText(VaccineActivity.this, "Successfully delete records", Toast.LENGTH_SHORT);
                            }
                        })
                        .setNegativeButton("no", null).show();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(VaccineActivity.this)
                        .setTitle("Upload history")
                        .setMessage("Are you sure you want to upload these pictures for your child?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (addImages.size() > 0) {
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
//        if(hasCamera() && hasDefaultCameraApp()) {
//            takePic.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(i, TAKE_PHOTO);
//                }
//            });
//        } else {
//            takePic.setEnabled(false);
//            takePic.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView v_image = new ImageView(this);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Bitmap b;
            try {
                b = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    path = Objects.requireNonNull(data.getData()).getPath();
                Log.i("somethings", path);
                v_image.setImageBitmap(b);
                v_image.setVisibility(View.VISIBLE);
//                vaccineImage.setVisibility(View.VISIBLE);
//                vaccineImage.setImageBitmap(b);
                Image newImg = new Image();
                newImg.setImage(imageHelper.getBytes(b));
                newImg.setChildID(childID);
                addImages.add(newImg);
                v_layout.addView(v_image, imParams);

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

//        } else if(requestCode == TAKE_PHOTO && resultCode == RESULT_OK && data != null) {
//            if(data.getExtras() != null) {
//                Bitmap b = (Bitmap) data.getExtras().get("data");
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                if(b != null) {
//                    //b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                    String dir = getFilesDir().getAbsolutePath();
//                    path = dir + System.currentTimeMillis() + ".jpg";
//                    File newFile = new File(path);
//                    try {
//                        //noinspection ResultOfMethodCallIgnored
//                        newFile.createNewFile();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    FileOutputStream ofile;
//                    try {
//                        ofile = new FileOutputStream(newFile);
//                        ofile.write(bytes.toByteArray());
//                        ofile.close();
//                    } catch (FileNotFoundException fnfe) {
//                        fnfe.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                    vaccineImage.setVisibility(View.VISIBLE);
////                    vaccineImage.setImageBitmap(b);
//                    v_image.setImageBitmap(b);
//                    v_image.setVisibility(View.VISIBLE);
//                    image.setImage(imageHelper.getBytes(b));
//                    addImages.add(image);
//                    v_layout.addView(v_image, imParams);
//
//
//                    //circleImageView.setImageBitmap(b);
//                }
//            }
        }
    }

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