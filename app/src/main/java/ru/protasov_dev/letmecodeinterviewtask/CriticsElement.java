package ru.protasov_dev.letmecodeinterviewtask;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.ImageLoader;

public class CriticsElement {
    private String name;
    private String status;
    private String urlImg;
    private ImageLoader imageLoader;
    private String bio;

    public CriticsElement(String name, String status, String urlImg, ImageLoader imageLoader, String bio){
        this.name = name;
        this.status = status;
        this.urlImg = urlImg;
        this.imageLoader = imageLoader;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bitmap getImg() {
        //ImageManager iM = new ImageManager();
        //return iM.fetchImage(urlImg);
        return imageLoader.loadImageSync(urlImg);
    }

    public String getBio() {
        return bio;
    }
}
