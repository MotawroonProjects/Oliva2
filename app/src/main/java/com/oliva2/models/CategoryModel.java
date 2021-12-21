package com.oliva2.models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.oliva2.tags.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

@Entity(tableName = Tags.table_category,indices = {@Index(value = {"id"},unique = true)})
public class CategoryModel implements Serializable {
    @NonNull
    @PrimaryKey
    private int id;
    private String name;
    @Ignore
    private String image;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] imageBitmap;

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImageBitmap(byte[] imageBitmap) {
        this.imageBitmap = imageBitmap;
    }


    public byte[] getImageBitmap() {
        return imageBitmap;
    }
}


