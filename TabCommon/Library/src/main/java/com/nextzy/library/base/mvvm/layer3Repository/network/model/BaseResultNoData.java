package com.nextzy.library.base.mvvm.layer3Repository.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TheKhaeng on 8/8/2016.
 */
public abstract class BaseResultNoData {
    protected String resultCode;
    @SerializedName("resultDesc")
    protected String resultDescription;
    protected String developerMessage;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public boolean isSuccess() {
        return ResponseCode.SUCCESS.equalsIgnoreCase(getResultCode());
    }

}
