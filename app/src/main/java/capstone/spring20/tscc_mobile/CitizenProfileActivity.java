package capstone.spring20.tscc_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import capstone.spring20.tscc_mobile.Api.ApiController;
import capstone.spring20.tscc_mobile.Api.TSCCClient;
import capstone.spring20.tscc_mobile.Entity.Citizen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitizenProfileActivity extends AppCompatActivity {

    TextView mName, mRole, mEmail, mPhoneNumber;
    ImageView mAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
/*
        mName = findViewById(R.id.txtName);
        mRole = findViewById(R.id.txtRole);
        mEmail = findViewById(R.id.txtEmail);
        mPhoneNumber = findViewById(R.id.txtPhoneNumber);
        mAvatar = findViewById(R.id.ivAvatar);
        //get user id
//        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String id = "845e77ed-565f-4249-9868-8f015c4853ff";


        //call api
        TSCCClient client = ApiController.getTsccClient();
        Call<Citizen> call = client.getCitizenById(id);
        call.enqueue(new Callback<Citizen>() {
            @Override
            public void onResponse(Call<Citizen> call, Response<Citizen> response) {
                //check id có tồn tại
                if (response.isSuccessful() && response.body() != null) {
                    Citizen citizen = response.body();
                    mName.setText(citizen.getFullName());
                    mEmail.setText(citizen.getEmail());
                    mPhoneNumber.setText(citizen.getPhoneNumber());
                }
            }

            @Override
            public void onFailure(Call<Citizen> call, Throwable t) {
            }
        });
*/
    }

}
