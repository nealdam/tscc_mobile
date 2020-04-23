package capstone.spring20.tscc_mobile.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Citizen {

    @SerializedName("id")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
