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

import com.Tourism.tourism_app.RoomDB.Pack_Item_Tbl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.Tourism.tourism_app.MainActivity.mydb;

public class Add_Pack_Item extends AppCompatActivity {

    private EditText title,desc;
    private ImageView image1;
    private Button submit,cancel;
    private int pid = 0;
    private int img_flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__pack__item);

        pid = getIntent().getIntExtra("pid",0);


        getSupportActionBar().setTitle("Add Package Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);


        image1 = findViewById(R.id.image);

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s1 = title.getText().toString().trim();
                String s4 = desc.getText().toString().trim();


                image1.setDrawingCacheEnabled(true);
                image1.buildDrawingCache();
                Bitmap bitmap = image1.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();


                if (s1.equals(""))
                {
                    title.setError("Please Enter");
                }
                else if (s4.equals(""))
                {
                    desc.setError("Please Enter");
                }
                else
                {

                    Pack_Item_Tbl model = new Pack_Item_Tbl();
                    model.setPack_id(pid);
                    model.setTitle(s1);
                    model.setDesc(s4);

                    if (img_flag == 1)
                    {
                        model.setImage(data);
                    }
                    else {
                        model.setImage(null);
                    }

                    if (mydb.dao_pack_item().insertPackItem(model) > 0)
                    {
                        Toast.makeText(Add_Pack_Item.this , "Data Saved..",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Add_Pack_Item.this , "Something Wrong..",Toast.LENGTH_LONG).show();
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

                img_flag = 1;

                image1.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
