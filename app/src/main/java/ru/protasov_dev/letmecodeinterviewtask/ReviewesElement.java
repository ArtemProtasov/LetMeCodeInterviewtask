package ru.protasov_dev.letmecodeinterviewtask;

import android.content.Context;

public class ReviewesElement {
    private String title;
    private String summaryShort;
    private String date;
    private String byline;
    private String urlImg;
    private Context context;

    public ReviewesElement(String title, String summaryShort, String date, String byline, String urlImg, Context context){
        this.title = title;
        this.summaryShort = summaryShort;
        this.date = date;
        this.byline = byline;
        this.urlImg = urlImg;
        this.context = context;
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

//    public Bitmap getImg() {
//        return imageLoader.loadImageSync(urlImg);
//    }


    public String getUrlImg() {
        return urlImg;
    }

    public Context getContext() {
        return context;
    }
}
