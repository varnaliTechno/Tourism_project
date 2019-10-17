package com.Tourism.tourism_app.RoomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Category_Tbl.class,Package_Tbl.class,Pack_Item_Tbl.class},version = 1,exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase
{
    public abstract DaoAccess dao_category();
    public abstract DaoAccess_Pack dao_package();
    public abstract DaoAccess_Pack_Item dao_pack_item();
}
