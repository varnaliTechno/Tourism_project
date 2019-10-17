package com.Tourism.tourism_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Tourism.tourism_app.RoomDB.Package_Tbl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.Tourism.tourism_app.MainActivity.mydb;

public class Add_Package extends AppCompatActivity {

    private EditText title,price,pack,desc;
    private ImageView image1,image2;
    private Button submit,cancel;
    private int cid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__package);

        cid = getIntent().getIntExtra("cid",0);


        getSupportActionBar().setTitle("Add Package");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        pack = findViewById(R.id.pack);
        desc = findViewById(R.id.desc);


        image1 = findViewById(R.id.image_bg);
        image2 = findViewById(R.id.image_prof);

        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s1 = title.getText().toString().trim();
                String s2 = price.getText().toString().trim();
                String s3 = pack.getText().toString().trim();
                String s4 = desc.getText().toString().trim();


                image1.setDrawingCacheEnabled(true);
                image1.buildDrawingCache();
                Bitmap bitmap = image1.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();


                image2.setDrawingCacheEnabled(true);
                image2.buildDrawingCache();
                Bitmap bitmap2 = image2.getDrawingCache();
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos2);
                byte[] data2 = baos2.toByteArray();


                if (s1.equals(""))
                {
                    title.setError("Please Enter");
                }
                else if (s2.equals(""))
                {
                    price.setError("Please Enter");
                }
                else if (s3.equals(""))
                {
                    pack.setError("Please Enter");
                }
                else if (s4.equals(""))
                {
                    desc.setError("Please Enter");
                }
                else
                {

                    Package_Tbl model = new Package_Tbl();

                    model.setCat_id(cid);
                    model.setTitle(s1);
                    model.setPrice(Double.parseDouble(s2));
                    model.setPack(s3);
                    model.setDesc(s4);
                    model.setImage(data);
                    model.setImage2(data2);

                    if (mydb.dao_package().insertPackage(model) > 0)
                    {
                        Toast.makeText(Add_Package.this , "Data Saved..",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Add_Package.this , "Something Wrong..",Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                image1.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                image2.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }




}
