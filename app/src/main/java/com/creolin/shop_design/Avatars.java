package com.creolin.shop_design;

public class Avatars {

    String imageURL;
    String imageName;
    boolean bought;
    boolean DP;

    public Avatars(String imageURL, String imageName, boolean bought, boolean DP) {
        this.imageURL = imageURL;
        this.imageName = imageName;
        this.bought = bought;
        this.DP = DP;
    }

    public Avatars(){}

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public boolean isDP() {
        return DP;
    }

    public void setDP(boolean DP) {
        this.DP = DP;
    }

}
