package edu.brynmawr.cmsc353.elevateproject.models;

public class CommentModel {
    private String userName;
    private String commentID;
    private String content;
    private String date;

    public CommentModel(String userName, String commentID, String content, String date) {
        this.userName = userName;
        this.commentID = commentID;
        this.content = content;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
