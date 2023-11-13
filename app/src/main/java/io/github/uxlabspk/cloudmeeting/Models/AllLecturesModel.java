package io.github.uxlabspk.cloudmeeting.Models;

import android.widget.ImageView;
import android.widget.TextView;

public class AllLecturesModel {
    private String class_name_lectures;
    private int class_lectures_files_count;

    public AllLecturesModel() {
        // empty constructor.
    }

    public AllLecturesModel(String class_name_lectures, int class_lectures_files_count) {
        this.class_name_lectures = class_name_lectures;
        this.class_lectures_files_count = class_lectures_files_count;
    }

    public String getClass_name_lectures() {
        return class_name_lectures;
    }

    public void setClass_name_lectures(String class_name_lectures) {
        this.class_name_lectures = class_name_lectures;
    }

    public int getClass_lectures_files_count() {
        return class_lectures_files_count;
    }

    public void setClass_lectures_files_count(int class_lectures_files_count) {
        this.class_lectures_files_count = class_lectures_files_count;
    }

}
