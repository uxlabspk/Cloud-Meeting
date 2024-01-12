package io.github.uxlabspk.cloudmeeting.Models;

public class AssesmentDetailsModel {
    private String assesmentTitle;
    private String assesmentDetail;
    private String assesmentDeadline;
    private String totalMarks;
    private String className;
    private String attachmentUrl;

    public AssesmentDetailsModel() {
        // empty constructor
    }

    public AssesmentDetailsModel(String assesmentTitle, String assesmentDetail, String assesmentDeadline, String totalMarks, String attachmentUrl, String className) {
        this.assesmentTitle = assesmentTitle;
        this.assesmentDetail = assesmentDetail;
        this.assesmentDeadline = assesmentDeadline;
        this.totalMarks = totalMarks;
        this.attachmentUrl = attachmentUrl;
        this.className = className;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public AssesmentDetailsModel(String assesmentTitle, String assesmentDetail, String assesmentDeadline, String totalMarks) {
        this.assesmentTitle = assesmentTitle;
        this.assesmentDetail = assesmentDetail;
        this.assesmentDeadline = assesmentDeadline;
        this.totalMarks = totalMarks;
    }

    public String getAssesmentTitle() {
        return assesmentTitle;
    }

    public void setAssesmentTitle(String assesmentTitle) {
        this.assesmentTitle = assesmentTitle;
    }

    public String getAssesmentDetail() {
        return assesmentDetail;
    }

    public void setAssesmentDetail(String assesmentDetail) {
        this.assesmentDetail = assesmentDetail;
    }

    public String getAssesmentDeadline() {
        return assesmentDeadline;
    }

    public void setAssesmentDeadline(String assesmentDeadline) {
        this.assesmentDeadline = assesmentDeadline;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }
}
