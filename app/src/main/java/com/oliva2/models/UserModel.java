package com.oliva2.models;

import java.io.Serializable;

public class UserModel extends StatusResponse{
    private User data;

    public User getUser() {
        return data;
    }



    public static class User implements Serializable {
        private int id;
        private int branch_id;
        private String name;
        private String email;
        private String phone;
        private String company_name;
        private int role_id;
        private int biller_id;
        private int warehouse_id;
        private int is_active;
        private int is_deleted;
        private int pos_id;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getCompany_name() {
            return company_name;
        }

        public int getRole_id() {
            return role_id;
        }

        public int getBiller_id() {
            return biller_id;
        }

        public int getWarehouse_id() {
            return warehouse_id;
        }

        public int getIs_active() {
            return is_active;
        }

        public int getIs_deleted() {
            return is_deleted;
        }

        public int getPos_id() {
            return pos_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }


}
