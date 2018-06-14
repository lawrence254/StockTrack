
package com.lawrence254.stocktrack.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StocksModel {

    @SerializedName("AAPL")
    @Expose
    private AAPL aAPL;
    @SerializedName("FB")
    @Expose
    private FB fB;

    public AAPL getAAPL() {
        return aAPL;
    }

    public void setAAPL(AAPL aAPL) {
        this.aAPL = aAPL;
    }

    public FB getFB() {
        return fB;
    }

    public void setFB(FB fB) {
        this.fB = fB;
    }

}
