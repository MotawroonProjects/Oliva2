package com.oliva2.local_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.oliva2.models.BrandModel;
import com.oliva2.models.CategoryModel;
import com.oliva2.models.ProductModel;
import com.oliva2.tags.Tags;

@Database(version = 1,entities = {CategoryModel.class,ProductModel.class,ProductModel.FirstStock.class,ProductModel.Tax.class,ProductModel.Unit.class,ProductModel.OfferProducts.class, BrandModel.class},exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    public static volatile LocalDatabase instance = null;

    public abstract DAOInterface daoInterface();


    public static LocalDatabase newInstance(Context context){
        if (instance==null){
            synchronized (LocalDatabase.class){
                instance = Room.databaseBuilder(context.getApplicationContext(),LocalDatabase.class, Tags.DATABASE_NAME)
                        .build();
            }
        }
        return instance;
    }
}
