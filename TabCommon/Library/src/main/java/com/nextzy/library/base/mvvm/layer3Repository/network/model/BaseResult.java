package com.nextzy.library.base.mvvm.layer3Repository.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TheKhaeng on 8/8/2016.
 */
public abstract class BaseResult<D extends BaseData> extends BaseResultNoData {
    public static final String SUCCESS = "20000";

    protected D data;
    @SerializedName(value = "dataList", alternate = {"datalist"})
    protected List<D> dataList;

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public List<D> getDataList() {
        return dataList;
    }

    public void setDataList(List<D> dataList) {
        this.dataList = dataList;
    }

    public boolean hasData() {
        return data != null;
    }

    public boolean hasDataList() {
        return dataList != null && dataList.size() > 0 && dataList.get(0) != null;
    }

    public boolean isSuccess() {
        return SUCCESS.equalsIgnoreCase(getResultCode());
    }

}
