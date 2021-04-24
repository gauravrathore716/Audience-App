package com.b2c.audience.view;


import com.b2c.audience.model.LoginRegisterResponse;

public interface IEditProfileView extends IView {

    void onEditProfile(LoginRegisterResponse body);


}
