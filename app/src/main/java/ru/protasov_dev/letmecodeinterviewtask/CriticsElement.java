package ru.protasov_dev.letmecodeinterviewtask;

import android.graphics.Bitmap;

public class CriticsElement {
    private String name;
    private String status;
    private String urlImg;

    public CriticsElement(String name, String status, String urlImg){
        this.name = name;
        this.status = status;
        this.urlImg = urlImg;
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
        ImageManager iM = new ImageManager();
        return iM.fetchImage(urlImg);
    }
}
