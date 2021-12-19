package com.oliva2.models;

import java.io.Serializable;

public class SingleOrderDataModel extends StatusResponse implements Serializable {

    private OrderModel data;

    public OrderModel getData() {
        return data;
    }

}
