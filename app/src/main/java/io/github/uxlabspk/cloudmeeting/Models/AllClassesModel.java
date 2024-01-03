package io.github.uxlabspk.cloudmeeting.Models;

public class AllClassesModel {
    private String class_name;
    private String teacher_id;
    private String class_start_time;
    private String lectures_url;
    private String assesments_url;

    private String sectionName;

    public AllClassesModel() {
    }

    public AllClassesModel(String sectionName, String class_name, String teacher_id, String class_start_time, String lectures_url, String assesments_url) {
        this.sectionName = sectionName;
        this.class_name = class_name;
        this.teacher_id = teacher_id;
        this.class_start_time = class_start_time;
        this.lectures_url = lectures_url;
        this.assesments_url = assesments_url;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }


    public String getLectures_url() {
        return lectures_url;
    }

    public void setLectures_url(String lectures_url) {
        this.lectures_url = lectures_url;
    }

    public String getAssesments_url() {
        return assesments_url;
    }

    public void setAssesments_url(String assesments_url) {
        this.assesments_url = assesments_url;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }


    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getClass_start_time() {
        return class_start_time;
    }

    public void setClass_start_time(String class_start_time) {
        this.class_start_time = class_start_time;
    }
}
