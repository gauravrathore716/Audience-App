package com.b2c.audience.view;


import com.b2c.audience.model.CategoryListInfo;
import com.b2c.audience.model.CategoryListResponse;

public interface ICategoryView extends IView {

    void onSuccess(CategoryListResponse body);


}
