package com.example.admin.umbrella.inject.MainInject;


import com.example.admin.umbrella.view.MainActivity.MainActivity;

import dagger.Component;


@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);


}
