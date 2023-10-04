package io.github.uxlabspk.cloudmeeting.Models;

public class AllClassesModel {
    private String class_name;
    private String class_shedule;
    private String class_start_time;

    public AllClassesModel() {
    }

    public AllClassesModel(String class_name, String class_shedule, String class_start_time) {
        this.class_name = class_name;
        this.class_shedule = class_shedule;
        this.class_start_time = class_start_time;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_shedule() {
        return class_shedule;
    }

    public void setClass_shedule(String class_shedule) {
        this.class_shedule = class_shedule;
    }

    public String getClass_start_time() {
        return class_start_time;
    }

    public void setClass_start_time(String class_start_time) {
        this.class_start_time = class_start_time;
    }
}
