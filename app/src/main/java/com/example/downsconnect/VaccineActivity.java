package com.example.downsconnect;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.UriPermission;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VaccineActivity extends AppCompatActivity {
    private Button back, choosePic, takePic, save;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int TAKE_PHOTO = 2;
    private ImageView vaccineImage;
    private String path;
    private ContentResolver resolver;
    private List<UriPermission> permissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);

        back = findViewById(R.id.backButton);
        choosePic = findViewById(R.id.choosePicture);
        takePic = findViewById(R.id.takePicture);
        vaccineImage = findViewById(R.id.vaccineImage);
        save = findViewById(R.id.saveButton);
        resolver = getApplicationContext().getContentResolver();

        UriPermission uri = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            permissions = getApplicationContext().getContentResolver().getPersistedUriPermissions();
            if(permissions.size() > 0) {
                uri = permissions.get(0);
                vaccineImage.setImageURI(uri.getUri());
            }
        }

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VaccineActivity.this, MedicalActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Bitmap b;
            try {
                b = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    path = Objects.requireNonNull(data.getData()).getPath();
                Log.i("somethings", path);
                vaccineImage.setVisibility(View.VISIBLE);
                vaccineImage.setImageBitmap(b);
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                if(b.getWidth() == size.x && b.getHeight() == size.y){
                    vaccineImage.setRotation(0);
                }else{
                    vaccineImage.setRotation(90);
                }
            } catch(IOException e) {
                e.printStackTrace();
            } catch(NullPointerException npe) {
                npe.printStackTrace();
            }

        } else if(requestCode == TAKE_PHOTO && resultCode == RESULT_OK && data != null) {
            if(data.getExtras() != null) {
                Bitmap b = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                if(b != null) {
                    b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String dir = getFilesDir().getAbsolutePath();
                    path = dir + System.currentTimeMillis() + ".jpg";
                    File newFile = new File(path);
                    try {
                        //noinspection ResultOfMethodCallIgnored
                        newFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    FileOutputStream ofile;
                    try {
                        ofile = new FileOutputStream(newFile);
                        ofile.write(bytes.toByteArray());
                        ofile.close();
                    } catch (FileNotFoundException fnfe) {
                        fnfe.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    vaccineImage.setVisibility(View.VISIBLE);
                    vaccineImage.setImageBitmap(b);

                    //circleImageView.setImageBitmap(b);
                }
            }
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