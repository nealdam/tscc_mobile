package capstone.spring20.tscc_mobile.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrashRequest {

    @SerializedName("trashTypeId")
    @Expose
    private String trashTypeId;
    @SerializedName("trashSizeId")
    @Expose
    private String trashSizeId;
    @SerializedName("trashWidthId")
    @Expose
    private String trashWidthId;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("imageDirectories")
    @Expose
    public List<String> imageDirectories = new ArrayList<>();

    public String getTrashTypeId() {
        return trashTypeId;
    }



    public void setTrashTypeId(String trashTypeId) {
        this.trashTypeId = trashTypeId;
    }

    public String getTrashSizeId() {
        return trashSizeId;
    }

    public void setTrashSizeId(String trashSizeId) {
        this.trashSizeId = trashSizeId;
    }

    public String getTrashWidthId() {
        return trashWidthId;
    }

    public void setTrashWidthId(String trashWidthId) {
        this.trashWidthId = trashWidthId;
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

    public TrashRequest(String trashTypeId, String trashSizeId, String trashWidthId, double latitude, double longitude, List<String> imageDirectories) {
        this.trashTypeId = trashTypeId;
        this.trashSizeId = trashSizeId;
        this.trashWidthId = trashWidthId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageDirectories = imageDirectories;
    }

    public List<String> getImageDirectories() {
        return imageDirectories;
    }

    public void setImageDirectories(List<String> imageDirectories) {
        this.imageDirectories = imageDirectories;
    }
}
