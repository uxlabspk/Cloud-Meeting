package io.github.uxlabspk.cloudmeeting.Models;

public class SubmissionModel {
    private String submissionUrl;
    private String studentID;
    private String studentMarks;
    private boolean isSubmited;
    private String studentRemarks;

    public SubmissionModel() {
        // empty constructor
    }

    public SubmissionModel(String submissionUrl, String studentID, String studentMarks, boolean isSubmited, String studentRemarks) {
        this.submissionUrl = submissionUrl;
        this.studentID = studentID;
        this.studentMarks = studentMarks;
        this.isSubmited = isSubmited;
        this.studentRemarks = studentRemarks;
    }

    public String getSubmissionUrl() {
        return submissionUrl;
    }

    public void setSubmissionUrl(String submissionUrl) {
        this.submissionUrl = submissionUrl;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentMarks() {
        return studentMarks;
    }

    public void setStudentMarks(String studentMarks) {
        this.studentMarks = studentMarks;
    }

    public boolean isSubmited() {
        return isSubmited;
    }

    public void setSubmited(boolean submited) {
        isSubmited = submited;
    }

    public String getStudentRemarks() {
        return studentRemarks;
    }

    public void setStudentRemarks(String studentRemarks) {
        this.studentRemarks = studentRemarks;
    }
}
