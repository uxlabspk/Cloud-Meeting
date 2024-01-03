package io.github.uxlabspk.cloudmeeting.Models;

public class AllAssesmentsModel {
    private String assesmentTitle;
    private String assesmentDedline;

    public AllAssesmentsModel() {
        // empty constructor
    }

    public AllAssesmentsModel(String assesmentTitle, String assesmentDedline) {
        this.assesmentTitle = assesmentTitle;
        this.assesmentDedline = assesmentDedline;
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
}
