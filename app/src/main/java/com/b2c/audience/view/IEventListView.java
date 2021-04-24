package com.b2c.audience.view;


import com.b2c.audience.model.CategoryListResponse;
import com.b2c.audience.model.EventListResponse;

public interface IEventListView extends IView {

    void onSuccess(EventListResponse body);


}
