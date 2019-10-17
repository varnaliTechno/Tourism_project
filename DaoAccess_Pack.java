package com.Tourism.tourism_app.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoAccess_Pack
{
    @Insert
    Long insertPackage(Package_Tbl pack);

    @Query("SELECT * FROM Package_Tbl WHERE cat_id =:cid ORDER BY id desc")
    List<Package_Tbl> getPackage(int cid);

}
