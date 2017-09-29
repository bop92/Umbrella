package com.example.admin.umbrella.inject.SettingsInject;


import com.example.admin.umbrella.view.MainActivity.MainActivity;
import com.example.admin.umbrella.view.SettingsActivity.SettingsActivity;

import dagger.Component;


@Component(modules = SettingsActivityModule.class)
public interface SettingsActivityComponent {

    void inject(SettingsActivity settingsActivity);


}
