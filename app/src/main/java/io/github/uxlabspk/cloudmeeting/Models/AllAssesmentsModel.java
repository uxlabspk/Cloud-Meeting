package io.github.uxlabspk.cloudmeeting.Models;

public class AllAssesmentsModel {
    private String assesmentTitle;
    private String assesmentDedline;
    private long assesmentPublishTime;

    public AllAssesmentsModel(String assesmentTitle, String assesmentDedline, long assesmentPublishTime) {
        this.assesmentTitle = assesmentTitle;
        this.assesmentDedline = assesmentDedline;
        this.assesmentPublishTime = assesmentPublishTime;
    }

    public String getAssesmentTitle() {
        return assesmentTitle;
    }

    public void setAssesmentTitle(String assesmentTitle) {
        this.assesmentTitle = assesmentTitle;
    }

    public String getAssesmentDedline() {
        return assesmentDedline;
    }

    public void setAssesmentDedline(String assesmentDedline) {
        this.assesmentDedline = assesmentDedline;
    }

    public long getAssesmentPublishTime() {
        return assesmentPublishTime;
    }

    public void setAssesmentPublishTime(long assesmentPublishTime) {
        this.assesmentPublishTime = assesmentPublishTime;
    }
}
