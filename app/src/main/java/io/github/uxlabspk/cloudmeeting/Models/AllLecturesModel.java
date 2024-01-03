package io.github.uxlabspk.cloudmeeting.Models;

import android.widget.ImageView;
import android.widget.TextView;

public class AllLecturesModel {
    private String class_name_lectures;
    private String class_lectures_url;

    public AllLecturesModel() {
        // empty constructor.
    }

    public AllLecturesModel(String class_name_lectures, String clas_lectures_url) {
        this.class_name_lectures = class_name_lectures;
        this.class_lectures_url = clas_lectures_url;
    }

    public String getClass_name_lectures() {
        return class_name_lectures;
    }

    public void setClass_name_lectures(String class_name_lectures) {
        this.class_name_lectures = class_name_lectures;
    }

    public String getClass_lectures_url() {
        return class_lectures_url;
    }

    public void setClass_lectures_url(String class_lectures_url) {
        this.class_lectures_url = class_lectures_url;
    }
}
