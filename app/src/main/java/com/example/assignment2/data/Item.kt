package com.example.assignment2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true) var itemId: Long?,
    @ColumnInfo(name = "category") var category: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "estPrice") var estPrice: String,
    @ColumnInfo(name = "status") var status: Boolean
) : Serializable