package com.Tourism.tourism_app.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoAccess_Pack_Item
{
    @Insert
    Long insertPackItem(Pack_Item_Tbl pack);

    @Query("SELECT * FROM pack_item WHERE pack_id =:pid")
    List<Pack_Item_Tbl> getPackItem(int pid);

}
