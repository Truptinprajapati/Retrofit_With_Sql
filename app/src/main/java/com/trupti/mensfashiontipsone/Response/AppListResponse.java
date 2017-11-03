package com.trupti.mensfashiontipsone.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vaksys-android-52 on 25/7/17.
 */

public class AppListResponse extends CommonResponse {
    @SerializedName("row")
    private List<CategoryEntity> row;

    public List<CategoryEntity> getRow() {
        return row;
    }

    public class CategoryEntity {
        @SerializedName("id")
        private int id;

        @SerializedName("category")
        private String category;

        @SerializedName("description")
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
