package com.oliva2.models;

import java.io.Serializable;
import java.util.List;

public class TaxDataModel extends StatusResponse implements Serializable {
    private List<TaxModel> data;

    public List<TaxModel> getData() {
        return data;
    }
}
