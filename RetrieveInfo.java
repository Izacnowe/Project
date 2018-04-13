package com.paradise.malariastressfighter.Health;

/**
 * Created by Dell on 2/15/2018.
 */

public class RetrieveInfo {
    private String imageView,Email,Name;
    public RetrieveInfo(){

    }
    public RetrieveInfo(String imageView, String email, String  name ) {
        this.imageView = imageView;
        this.Email = email;
        this.Name = name;



    }

    public String getImageView() {
        return imageView;
    }
    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }


    public void setImageView() {
        this.imageView = imageView;
    }
}

