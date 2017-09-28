package com.example.admin.umbrella.model.Option;

/**
 * Created by Admin on 9/27/2017.
 */

public class Option {
    String option_key;
    String name;
    String value;

    public Option(String option_key, String name, String value) {
        this.option_key = option_key;
        this.name = name;
        this.value = value;
    }

    public String getOption_key() {
        return option_key;
    }

    public void setOption_key(String option_key) {
        this.option_key = option_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
