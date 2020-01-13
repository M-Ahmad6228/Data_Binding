package com.example.combined_task;

import android.actionsheet.demo.com.khoiron.actionsheetiosforandroid.ActionSheet;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView imageView;
    List<UserData> userList;
    TextView txt_email;
    TextView txt_fullName;
    ProgressBar progressBar;
    UserData userData = new UserData();
    private int PHOTO_PICK_CODE = 102;
    private int PHOTO_CLICK_CODE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        imageView = findViewById(R.id.profile_image);
        progressBar = findViewById(R.id.progressBar3);
        txt_fullName = findViewById(R.id.txt1);
        txt_email = findViewById(R.id.textView_email);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        userList = UserData.listAll(UserData.class);
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(userList);
        recyclerView.setAdapter(mAdapter);
        for(UserData data: userList){
            if(data.getEmailAddress().equals(PreferenceData.getLoggedInEmailUser(this))){
                userData = data;
                txt_fullName.setText(data.getFullName());
                txt_email.setText(data.getEmailAddress());
                if(data.getIsPictureSaved()) {
                    imageView.setImageURI(Uri.parse(data.getPicture()));
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_CLICK_CODE) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get(MediaStore.EXTRA_OUTPUT);
                imageView.setImageBitmap(thumbnail);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), thumbnail, "TEMP" , "");
                if(path != null) {
                    userData.setPicture(path);
                    userData.setIsPictureSaved(true);
                    userData.save();
                }
            } else if (requestCode == PHOTO_PICK_CODE) {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {

                    progressBar.setVisibility(View.GONE);
                    Uri path = data.getData();
                    imageView.setImageURI(path);
                    if(path.toString() != null) {
                        userData.setPicture(path.toString());
                        userData.setIsPictureSaved(true);
                        userData.save();
                    }

                }, 1500);
            }
        }
    }

    public void tapAvatar(View view){
        ArrayList<String> data = new ArrayList<>();

        data.add("Upload Picture");
        data.add("View Picture");
        data.add("Take Picture");
        data.add("Delete Picture");

        new ActionSheet(UserActivity.this,data)
                .setCancelTitle("Cancel")
                .setColorTitleCancel(getResources().getColor(R.color.colorRed))
                .setColorData(getResources().getColor(R.color.colorBlue))
                .create((data1, position) -> {
                    switch (position){
                        case 0:
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, PHOTO_PICK_CODE);
                            break;
                        case 1:
                            //bmp to byte[]
                           /* Bitmap bmp = intent.getExtras().get("data");
                              ByteArrayOutputStream stream = new ByteArrayOutputStream();
                              bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                              byte[] byteArray = stream.toByteArray();
                              bmp.recycle();*/
                           //byte[] to bmp
                            //Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                            break;
                        case 2:
                            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(i, PHOTO_CLICK_CODE);
                            break;
                        case 3:
                            progressBar.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(() -> {

                                progressBar.setVisibility(View.GONE);
                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no_avatar);
                                imageView.setImageBitmap(bitmap);
                                userData.setPicture("");
                                userData.setIsPictureSaved(false);
                                userData.save();

                            }, 1000);
                            break;
                    }
                });
    }

    public void logout(View view){
        PreferenceData.setUserLoggedInStatus(this, false);
        PreferenceData.setLoggedInUserEmail(this, "");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
