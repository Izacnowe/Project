package com.paradise.malariastressfighter.Health;

/**
 * Created by St. Luke's Chapel on 15-Feb-18.
 */

public class Person {

    String email;
    String desc;
    String imageUri;
    String name;

    public Person(String name, String email,String desc, String imageUri) {
        this.name = name;
        this.email = email;

        this.imageUri = imageUri;
    }

    public Person() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
