package com.oliva2.models;

import static androidx.room.ForeignKey.CASCADE;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.oliva2.tags.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

@Entity(tableName = Tags.table_products
//        foreignKeys = {
//                @ForeignKey(entity = CategoryModel.class, parentColumns = "id", childColumns = "category_id", onDelete = CASCADE)
//
//        }

)
public  class ProductModel implements Serializable {
    @NonNull
    @PrimaryKey
    private int id;
    private String name;
    private double price;
    private int brand_id;
    private int featured;
    @ColumnInfo(name = "image" ,typeAffinity = ColumnInfo.BLOB)
    private byte[] imageBitmap;

    private int count;
    @Ignore
    private String image;
    private int can_make;
    private String code;
    @Ignore

    private Unit unit;

    private int category_id;
    @Ignore

    private FirstStock first_stock;
    @Ignore

    private Tax tax;
    @Ignore

    private List<OfferProducts> offer_products;

    public void setCount(int count) {
        this.count = count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageBitmap(byte[] imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setFirst_stock(FirstStock first_stock) {
        this.first_stock = first_stock;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public void setOffer_products(List<OfferProducts> offer_products) {
        this.offer_products = offer_products;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public int getCan_make() {
        return can_make;
    }

    public void setCan_make(int can_make) {
        this.can_make = can_make;
    }

    public void setImageBitmap() {
        Picasso.get().load(Tags.Product_IMAGE_URL + image).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                bitmap.recycle();
                imageBitmap = byteArray; // I don't want to set it to an Image view here
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    public byte[] getImageBitmap() {
        return imageBitmap;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public String getImage() {
        return image;
    }

    public String getCode() {
        return code;
    }

    public FirstStock getFirst_stock() {
        return first_stock;
    }

    public Tax getTax() {
        return tax;
    }

    public Unit getUnit() {
        return unit;
    }

    public int getCategory_id() {
        return category_id;
    }

    public List<OfferProducts> getOffer_products() {
        return offer_products;
    }

    @Entity(tableName = Tags.table_first_stock
//            foreignKeys = {
//                    @ForeignKey(entity = ProductModel.class, parentColumns = "id", childColumns = "product_id", onDelete = CASCADE)
//
//            }

    )
    public static class FirstStock implements Serializable {
        @PrimaryKey(autoGenerate = true)
        private int localid;
        private int id;
        private double qty;
        private int product_id;

        public int getId() {
            return id;
        }

        public double getQty() {
            return qty;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setQty(double qty) {
            this.qty = qty;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getLocalid() {
            return localid;
        }

        public void setLocalid(int localid) {
            this.localid = localid;
        }
    }

    @Entity(tableName = Tags.table_tax
//            foreignKeys = {
//                    @ForeignKey(entity = ProductModel.class, parentColumns = "id", childColumns = "product_id", onDelete = CASCADE)
//
//            }

    )
    public static class Tax implements Serializable {
        @PrimaryKey(autoGenerate = true)
        private int localid;
        private int id;
        private String name;
        private int rate;
        private int product_id;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getRate() {
            return rate;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getLocalid() {
            return localid;
        }

        public void setLocalid(int localid) {
            this.localid = localid;
        }
    }

    @Entity(tableName = Tags.table_unit
//            foreignKeys = {
//                    @ForeignKey(entity = ProductModel.class, parentColumns = "id", childColumns = "product_id", onDelete = CASCADE)
//
//            }

    )
    public static class Unit implements Serializable {
        @PrimaryKey(autoGenerate = true)
        private int localid;
        private int id;
        private String unit_name;
        private int product_id;

        public int getId() {
            return id;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getLocalid() {
            return localid;
        }

        public void setLocalid(int localid) {
            this.localid = localid;
        }
    }

    @Entity(tableName = Tags.table_offer
//            foreignKeys = {
//                    @ForeignKey(entity = ProductModel.class, parentColumns = "id", childColumns = "product_id", onDelete = CASCADE)
//
//            }


    )
    public static class OfferProducts implements Serializable {
        @PrimaryKey(autoGenerate = true)
        private int localid;
        private int product_id;

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getLocalid() {
            return localid;
        }

        public void setLocalid(int localid) {
            this.localid = localid;
        }
    }
}

