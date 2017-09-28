package com.example.admin.umbrella;

/**
 * Created by Admin on 9/2/2017.
 */

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);
    void removeView();


}
