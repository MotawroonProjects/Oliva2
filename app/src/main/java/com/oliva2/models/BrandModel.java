package com.oliva2.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.oliva2.tags.Tags;

import java.io.Serializable;

@Entity(tableName = Tags.table_brand
//        foreignKeys = {
//                @ForeignKey(entity = CategoryModel.class, parentColumns = "id", childColumns = "category_id", onDelete = CASCADE)
//
//        }

)
public class BrandModel implements Serializable {
    @PrimaryKey
    private int id;
    private String title;
    @Ignore
    private String image;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] imageBitmap;
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public byte[] getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(byte[] imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}


