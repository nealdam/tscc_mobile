package capstone.spring20.tscc_mobile.Api;

import capstone.spring20.tscc_mobile.Entity.Citizen;
import capstone.spring20.tscc_mobile.Entity.CitizenRequest;
import capstone.spring20.tscc_mobile.Entity.TrashRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TSCCClient {


    @POST("/api/trash-forms")
    Call<TrashRequest> sendTrashRequest(@Header("Authorization") String authorization,
            @Body TrashRequest trashRequest);

    @POST("/api/citizens/register")
    Call<Citizen> register(@Body CitizenRequest citizenRequest);

}
