package com.paradise.malariastressfighter.Health;

/**
 * Created by St. Luke's Chapel on 15-Feb-18.
 */

public class Blog {
    private String Title;
    private String Desc;
    private String Image;

    public Blog(String title, String desc, String image) {
        Title = title;
        Desc = desc;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
