package com.b2c.audience.view;


import com.b2c.audience.model.LoginRegisterResponse;

public interface IViewProfileView extends IView {

    void onViewProfile(LoginRegisterResponse body);


}
