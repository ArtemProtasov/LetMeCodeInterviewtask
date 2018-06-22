package ru.protasov_dev.letmecodeinterviewtask;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ReviewesElement {
    private String title;
    private String summaryShort;
    private String date;
    private String byline;
    private String urlImg;
    private ImageLoader imageLoader;

    public ReviewesElement(String title, String summaryShort, String date, String byline, String urlImg, ImageLoader imageLoader){
        this.title = title;
        this.summaryShort = summaryShort;
        this.date = date;
        this.byline = byline;
        this.urlImg = urlImg;
        this.imageLoader = imageLoader;
    }

    public String getTitle() {
        return title;
    }

    public String getSummaryShort() {
        return summaryShort;
    }

    public String getDate() {
        return date;
    }

    public String getByline() {
        return byline;
    }

    public Bitmap getImg() {
//        ImageManager iM = new ImageManager();
//        return iM.fetchImage(urlImg);
        return imageLoader.loadImageSync(urlImg);
    }
}
