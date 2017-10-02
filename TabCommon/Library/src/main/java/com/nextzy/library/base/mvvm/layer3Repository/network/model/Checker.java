package com.nextzy.library.base.mvvm.layer3Repository.network.model;

import java.util.List;

/**
 * Created by TheKhaeng on 10/7/2016.
 */

public class Checker {
    public static <Result extends BaseResult> boolean dataListNotNull(Result result) {
        return result != null &&
                result.getDataList() != null &&
                result.getDataList().size() > 0 && //empty datalist
                result.getDataList().get(0) != null; //doesn't contain anobject with a null keyword
    }

    public static <Result extends BaseResult> boolean dataNotNull(Result result) {
        return result != null && result.getData() != null;
    }

    public static <Result extends BaseResultNoData> boolean descriptionNotNull(Result result) {
        return result != null && result.getResultDescription() != null;
    }

    public static<OBJECT extends Object> boolean listNotNull(List<OBJECT> dataList) {
        return dataList != null && dataList.size() > 0 && dataList.get(0) != null;
    }
}
