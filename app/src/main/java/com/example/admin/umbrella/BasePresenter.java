package com.example.admin.umbrella;

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);
    void removeView();


}
