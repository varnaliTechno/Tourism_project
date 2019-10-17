package com.Tourism.tourism_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.Tourism.tourism_app.RoomDB.Category_Tbl;
import com.Tourism.tourism_app.RoomDB.Package_Tbl;

import java.util.ArrayList;
import java.util.List;

import static com.Tourism.tourism_app.MainActivity.mydb;

public class PackageAct extends AppCompatActivity {

    private Package_Adpt pack_adpt;
    private RecyclerView rv_list;
    private ArrayList<Package_Tbl> cat_list = new ArrayList<>();
    private Category_Tbl cat_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        cat_model = (Category_Tbl)getIntent().getSerializableExtra("cat");


        getSupportActionBar().setTitle(cat_model.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setHasFixedSize(true);
        rv_list.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(mLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());


    }

    private void setCategoryList() {

        cat_list.clear();

        //MyDatabase db = new MyDatabase(PackageAct.this);
        //cat_list = db.getPackageList(cat_model.getId());

        List<Package_Tbl> plist = mydb.dao_package().getPackage(cat_model.getId());

        cat_list.addAll(plist);

        pack_adpt = new Package_Adpt(this, cat_list, new Package_Adpt.OnItemClickListener() {
            @Override
            public void OnItemClick(Package_Tbl model) {

                Intent intent = new Intent(PackageAct.this , PackageDetailAct.class);
                intent.putExtra("cat",model);
                startActivity(intent);

            }
        });

        rv_list.setAdapter(pack_adpt);

    }


    @Override
    protected void onResume() {
        super.onResume();
        setCategoryList();
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
            Intent intent = new Intent(PackageAct.this , Add_Package.class);
            intent.putExtra("cid" , cat_model.getId());
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

}
