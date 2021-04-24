package com.b2c.audience.view;


import com.b2c.audience.model.LoginRegisterResponse;

public interface ILogoutView extends IView {

    void onSuccess(LoginRegisterResponse body);

}
