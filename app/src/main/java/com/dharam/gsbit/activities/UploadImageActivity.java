package com.dharam.gsbit.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dharam.gsbit.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class UploadImageActivity extends AppCompatActivity {

    ImageView userImage;
    Button cameraButton;
    Button galleryButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMAGE = 2;
    static final int PERMISSION_CAMERA_REQUEST_CODE = 100;
    static final int PERMISSION_GALLERY_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        cameraButton = (Button) findViewById(R.id.uploadImageFromCamera);
        galleryButton = (Button) findViewById(R.id.uploadImagefromGallery);
        userImage = (ImageView) findViewById(R.id.uploadedImage);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isPermissionGrantedForCamera())
                {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        requestPermissionForCamera();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No permission to access camera!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    capturePicture();
                }
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isPermissionGrantedForGallery())
                {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        requestPermissionForGallery();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No permission to access camera!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    pickImageFromGallery();
                }
            }
        });
    }

    private boolean isPermissionGrantedForCamera()
    {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isPermissionGrantedForGallery()
    {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionForCamera()
    {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST_CODE);
    }

    private void requestPermissionForGallery()
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_GALLERY_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch(requestCode) {

            case PERMISSION_CAMERA_REQUEST_CODE:

                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        capturePicture();
                    } else {
                        Toast.makeText(getApplicationContext(), "Camera could not be opened.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case PERMISSION_GALLERY_REQUEST_CODE:

                if(grantResults.length > 0)
                {
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {
                        pickImageFromGallery();
                    }
                    else
                    {
                        Toast.makeText(this, "Gallery could not be opened", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void capturePicture()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickImageFromGallery()
    {
        Intent selectPictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (selectPictureIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(selectPictureIntent, REQUEST_LOAD_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            userImage.setImageBitmap(imageBitmap);
        }
        else if(requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            userImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.logOutOption)
        {
            logOutUser();
        }

        return true;
    }

    private void logOutUser()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
