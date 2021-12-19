package com.oliva2.models;

import java.io.Serializable;
import java.util.List;

public class CustomerGroupDataModel extends StatusResponse implements Serializable {
    private List<CustomerGroupModel> data;

    public List<CustomerGroupModel> getData() {
        return data;
    }
}
