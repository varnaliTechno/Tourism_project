package com.Tourism.tourism_app.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoAccess
{

    @Insert
    Long insertCategory(Category_Tbl category);

    @Query("SELECT * FROM category ORDER BY id desc")
    List<Category_Tbl> getCategory();

}
