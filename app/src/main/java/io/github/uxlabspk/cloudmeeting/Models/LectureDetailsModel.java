package io.github.uxlabspk.cloudmeeting.Models;

public class LectureDetailsModel {
    private String LectureFileName;
    private String LectureDownloadUrl;

    public LectureDetailsModel() { /* Empty Constructor */}

    public LectureDetailsModel(String lectureFileName, String lectureDownloadUrl) {
        LectureFileName = lectureFileName;
        LectureDownloadUrl = lectureDownloadUrl;
    }

    public String getLectureFileName() {
        return LectureFileName;
    }

    public void setLectureFileName(String lectureFileName) {
        LectureFileName = lectureFileName;
    }

    public String getLectureDownloadUrl() {
        return LectureDownloadUrl;
    }

    public void setLectureDownloadUrl(String lectureDownloadUrl) {
        LectureDownloadUrl = lectureDownloadUrl;
    }
}
