package capstone.spring20.tscc_mobile.Entity;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrashRequest {

    @SerializedName("trashType")
    @Expose
    private String trashType;
    @SerializedName("trashSize")
    @Expose
    private String trashSize;
    @SerializedName("trashWidth")
    @Expose
    private String trashWidth;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("imageList")
    @Expose
    public List<String> imageList = new ArrayList<>();

    public String getTrashType() {
        return trashType;
    }



    public void setTrashType(String trashType) {
        this.trashType = trashType;
    }

    public String getTrashSize() {
        return trashSize;
    }

    public void setTrashSize(String trashSize) {
        this.trashSize = trashSize;
    }

    public String getTrashWidth() {
        return trashWidth;
    }

    public void setTrashWidth(String trashWidth) {
        this.trashWidth = trashWidth;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public TrashRequest(String trashType, String trashSize, String trashWidth, double latitude, double longitude, List<String> imageList) {
        this.trashType = trashType;
        this.trashSize = trashSize;
        this.trashWidth = trashWidth;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageList = imageList;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
