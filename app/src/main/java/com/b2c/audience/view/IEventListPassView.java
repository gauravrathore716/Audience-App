package com.b2c.audience.view;


import com.b2c.audience.model.DefaultResponse;
import com.b2c.audience.model.EventListResponse;

public interface IEventListPassView extends IView {

    void onSuccess(EventListResponse data);

}
