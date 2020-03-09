package capstone.spring20.tscc_mobile.Api;

import capstone.spring20.tscc_mobile.Entity.Citizen;
import capstone.spring20.tscc_mobile.Entity.CitizenToPost;
import capstone.spring20.tscc_mobile.Entity.TrashRequest;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface TSCCClient {


    @POST("/api/trash-forms")
    Call<TrashRequest> sendTrashRequest(@Body TrashRequest trashRequest);



    @GET("/citizen-management/managed-citizens/{id}")
    Call<Citizen> getCitizenById(@Path("id") String id);

    @POST("/citizen-management/managed-citizens")
    Call<CitizenToPost> CreateCitizen(@Body CitizenToPost citizen);

}
