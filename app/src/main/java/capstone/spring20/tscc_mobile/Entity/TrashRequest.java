package capstone.spring20.tscc_mobile.Entity;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrashRequest {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("requestDate")
    @Expose
    private String requestDate;
    @SerializedName("finishedDate")
    @Expose
    private String finishedDate;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("imageLocation")
    @Expose
    private Object imageLocation;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("trashType")
    @Expose
    private String trashType;
    @SerializedName("trashSize")
    @Expose
    private String trashSize;
    @SerializedName("trashWidth")
    @Expose
    private String trashWidth;
    @SerializedName("trashRequestStatus")
    @Expose
    private String trashRequestStatus;
    @SerializedName("citizen")
    @Expose
    private Object citizen;

    @SerializedName("ImageList")
    @Expose
    public List<Bitmap> ImageList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(String finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Object getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(Object imageLocation) {
        this.imageLocation = imageLocation;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

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

    public String getTrashRequestStatus() {
        return trashRequestStatus;
    }

    public void setTrashRequestStatus(String trashRequestStatus) {
        this.trashRequestStatus = trashRequestStatus;
    }

    public Object getCitizen() {
        return citizen;
    }

    public void setCitizen(Object citizen) {
        this.citizen = citizen;
    }
}
