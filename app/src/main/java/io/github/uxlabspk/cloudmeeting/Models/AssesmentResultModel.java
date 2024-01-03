package io.github.uxlabspk.cloudmeeting.Models;

public class AssesmentResultModel {

    private String title;
    private String studentName;
    private String studentMarks;

    public AssesmentResultModel() {
        // empty constructor
    }

    public AssesmentResultModel(String title, String studentName, String studentMarks) {
        this.title = title;
        this.studentName = studentName;
        this.studentMarks = studentMarks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentMarks() {
        return studentMarks;
    }

    public void setStudentMarks(String studentMarks) {
        this.studentMarks = studentMarks;
    }
}
