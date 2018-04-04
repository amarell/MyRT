package com.dark.amarel.myrt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

public class ProfilRt extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 101;
    ImageView IvPicture;
    EditText EtDesa,EtKetua,EtRt;
    Uri uriProfileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_rt);

        EtDesa = (EditText) findViewById(R.id.EtDesa);
        EtKetua =(EditText) findViewById(R.id.EtKetua);
        EtRt = (EditText) findViewById(R.id.EtRt);

        IvPicture = (ImageView) findViewById(R.id.IvPicture);

        IvPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

        findViewById(R.id.BtnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && requestCode == RESULT_OK && data != null && data.getData() != null ){
            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                IvPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Foto Profil"), CHOOSE_IMAGE);
    }
}
