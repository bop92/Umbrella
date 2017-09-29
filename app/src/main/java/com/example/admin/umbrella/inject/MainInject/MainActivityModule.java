package com.example.admin.umbrella.inject.MainInject;

import com.example.admin.umbrella.view.MainActivity.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule{

    @Provides
    MainActivityPresenter provideMainActivityPresenter(){

        return new MainActivityPresenter();
    }

}

