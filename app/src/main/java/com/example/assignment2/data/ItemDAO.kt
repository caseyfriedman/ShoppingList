package com.example.assignment2.data

import androidx.room.*

@Dao

interface ItemDAO {

    @Query("SELECT * FROM item")
    fun getAllItems(): List<Item>


    @Insert
    fun addItem(vararg item: Item)

    @Delete
    fun deleteItem(item: Item)

    @Update
    fun updateItem(item: Item)

    @Query("DELETE FROM item")
    fun deleteAllItems()


}