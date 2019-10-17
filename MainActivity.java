package com.Tourism.tourism_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.Tourism.tourism_app.RoomDB.Category_Tbl;
import com.Tourism.tourism_app.RoomDB.MyAppDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Category_Adpt cat_adpt;
    private RecyclerView rv_list;
    private ArrayList<Category_Tbl> cat_list = new ArrayList<>();
    private String TAG = MainActivity.class.getName();
    private static final int MY_PERMISSIONS_Write_External_Storage = 1;
    private static final int MY_PERMISSIONS_Read_External_Storage = 2;

    public static MyAppDatabase mydb ;
    public static String room_db = "tourism_db" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,room_db).allowMainThreadQueries().build();


       // isReadStoragePermissionGranted();
      //  isWriteStoragePermissionGranted();

        getSupportActionBar().setTitle("Tourism");

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setHasFixedSize(true);
        rv_list.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(mLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());

    }

    private void setCategoryList() {

        cat_list.clear();
        /*MyDatabase db = new MyDatabase(MainActivity.this);
        cat_list = db.getCategory();
*/
        List<Category_Tbl> catlist = mydb.dao_category().getCategory();

        cat_list.addAll(catlist);


        cat_adpt = new Category_Adpt(this, cat_list, new Category_Adpt.OnItemClickListener() {
            @Override
            public void OnItemClick(Category_Tbl model) {

                Intent intent = new Intent(MainActivity.this , PackageAct.class);
                intent.putExtra("cat",model);
                startActivity(intent);

            }
        });

        rv_list.setAdapter(cat_adpt);

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

        if (item.getItemId() == R.id.add_menu)
        {
            Intent intent = new Intent(MainActivity.this , Add_Category_Act.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    public  boolean isWriteStoragePermissionGranted()
    {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");

                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_Write_External_Storage);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }

    public  boolean isReadStoragePermissionGranted()
    {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");

                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_Read_External_Storage);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_Write_External_Storage:
                Log.d(TAG, "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission

                }else
                {
                    //progress.dismiss();
                }
                break;

            case MY_PERMISSIONS_Read_External_Storage:

                Log.d(TAG, "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission

                }
                else
                {
                    //progress.dismiss();
                }
                break;
        }

    }


}
