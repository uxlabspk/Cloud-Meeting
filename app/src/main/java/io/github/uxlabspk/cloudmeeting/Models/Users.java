package io.github.uxlabspk.cloudmeeting.Models;

public class Users {
    private String userID;
    private String userName;
    private String userEmail;
    private String userSchool;
    private String userClass;
    private String userImgUrl;
    private String userRole;
    private String userGender;

    public Users() {
        // empty constructor required here.
    }

    public Users(String userID, String userName, String userEmail, String userSchool, String userClass, String userImgUrl, String userRole, String userGender) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userSchool = userSchool;
        this.userClass = userClass;
        this.userImgUrl = userImgUrl;
        this.userRole = userRole;
        this.userGender = userGender;
    }

    // Getter and Setter Methods

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}
