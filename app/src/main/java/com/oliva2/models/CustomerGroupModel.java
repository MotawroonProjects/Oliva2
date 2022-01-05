package com.oliva2.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.oliva2.tags.Tags;

import java.io.Serializable;

@Entity(tableName = Tags.table_customer_group,indices = @Index(value = {"id"}, unique = true)
//        foreignKeys = {
//                @ForeignKey(entity = CategoryModel.class, parentColumns = "id", childColumns = "category_id", onDelete = CASCADE)
//
//        }

)
public class CustomerGroupModel implements Serializable {
    @PrimaryKey
    private int id;
    private String name;
    @Ignore
    private String percentage;
    @Ignore
    private int is_active;
    @Ignore
    private String created_at;
    @Ignore
    private String updated_at;

    public CustomerGroupModel(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPercentage() {
        return percentage;
    }

    public int getIs_active() {
        return is_active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
