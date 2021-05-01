package edu.brynmawr.cmsc353.elevateproject.models;

import android.graphics.Bitmap;

public class QuestionModel {
    private String userName;
    private String title;
    private String content;
    private String date;
    private int numLikes;
    private int numComments;
    private int[] commentID;
    private Bitmap image;

    public QuestionModel(String userName, String title, String content, int numLikes, int numComments, int[] commentID, String date, Bitmap image) {
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.commentID = commentID;
        this.date = date;
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public int[] getCommentID() {
        return commentID;
    }

    public void setCommentID(int[] commentID) {
        this.commentID = commentID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
