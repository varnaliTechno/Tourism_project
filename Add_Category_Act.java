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

import com.Tourism.tourism_app.RoomDB.Category_Tbl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.Tourism.tourism_app.MainActivity.mydb;

public class Add_Category_Act extends AppCompatActivity {

    private EditText title,desc;
    private ImageView image;
    private Button submit,cancel;
    private String TAG = Add_Category_Act.class.getName();

    //public MyAppDatabase mydb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__category);

       // mydb = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"tourism_db").build();


        getSupportActionBar().setTitle("Add Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        image = findViewById(R.id.image);

        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open_gallary();

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s1 = title.getText().toString().trim();
                String s2 = desc.getText().toString().trim();

                image.setDrawingCacheEnabled(true);
                image.buildDrawingCache();
                Bitmap bitmap = image.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();



                if (s1.equals(""))
                {
                    title.setError("Please Enter");
                }
                else
                {
                    Category_Tbl model = new Category_Tbl();
                    model.setTitle(s1);
                    model.setDesc(s2);
                    model.setImage(data);

                    if (mydb.dao_category().insertCategory(model) > 0)
                    {
                        Toast.makeText(Add_Category_Act.this , "Data Saved..",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Add_Category_Act.this , "Something Wrong..",Toast.LENGTH_LONG).show();
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

                //  set Progress Bar
                //  setProgressBar();
                //  set profile picture form gallery

                image.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }



    }


    private void open_gallary() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

    }


}
