package com.Tourism.tourism_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.Tourism.tourism_app.RoomDB.Pack_Item_Tbl;
import com.Tourism.tourism_app.RoomDB.Package_Tbl;

import java.util.ArrayList;
import java.util.List;

import static com.Tourism.tourism_app.MainActivity.mydb;

public class PackageDetailAct extends AppCompatActivity {

    private ArrayList<Pack_Item_Tbl> item_list = new ArrayList<>();
    private Package_Tbl pack_model;
    private RecyclerView rv_list;
    private Package_Item_Adpt pack_adpt;
    private ImageView image_bg,image;
    private TextView title,price,pack,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);

        pack_model = (Package_Tbl) getIntent().getSerializableExtra("cat");

        getSupportActionBar().setTitle("Package Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //--------------


        image_bg = findViewById(R.id.image_bg);
        image = findViewById(R.id.image);

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        pack = findViewById(R.id.pack);
        desc = findViewById(R.id.desc);

        title.setText(pack_model.getTitle());
        price.setText("Price : $ "+pack_model.getPrice() + "/-" );
        pack.setText("Package : "+pack_model.getPack());

        desc.setText(Html.fromHtml(pack_model.getDesc()));


        byte[] img = pack_model.getImage();

        if (img != null)
        {
            Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
            image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200,
                    200, false));
        }



        byte[] img2 = pack_model.getImage2();

        if (img2 != null) {
            Bitmap bmp2 = BitmapFactory.decodeByteArray(img2, 0, img2.length);
            image_bg.setImageBitmap(Bitmap.createScaledBitmap(bmp2, 200,
                    200, false));
        }


        //-------------------------

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setHasFixedSize(true);
        rv_list.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(mLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());




        image_bg.requestFocus();
    }

    private void setDetail() {

        item_list.clear();

        //MyDatabase db = new MyDatabase(PackageDetailAct.this);
        //item_list = db.getPackageItemList(pack_model.getId());

        List<Pack_Item_Tbl> pi_list = mydb.dao_pack_item().getPackItem(pack_model.getId());
        item_list.addAll(pi_list);

        pack_adpt = new Package_Item_Adpt(this, item_list, new Package_Item_Adpt.OnItemClickListener() {
            @Override
            public void OnItemClick(Pack_Item_Tbl model) {
            }
        });

        rv_list.setAdapter(pack_adpt);


    }

    @Override
    protected void onResume() {
        super.onResume();

        setDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.cat_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }

        if (item.getItemId() == R.id.add_menu)
        {
            Intent intent = new Intent(PackageDetailAct.this , Add_Pack_Item.class);
            intent.putExtra("pid",pack_model.getId());
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }
}
