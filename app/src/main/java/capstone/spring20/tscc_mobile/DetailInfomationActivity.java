package capstone.spring20.tscc_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import capstone.spring20.tscc_mobile.Api.ApiController;
import capstone.spring20.tscc_mobile.Api.TSCCClient;
import capstone.spring20.tscc_mobile.Entity.Citizen;
import capstone.spring20.tscc_mobile.Entity.CitizenToPost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailInfomationActivity extends AppCompatActivity {

    Button mSubmit;
    EditText mName, mPhoneNumber;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_infomation);
/*
        mSubmit = findViewById(R.id.btnSubmit);
        mName = findViewById(R.id.etxtName);
        mPhoneNumber = findViewById(R.id.etxtPhoneNumber);
        user = FirebaseAuth.getInstance().getCurrentUser();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create citizen object
                CitizenToPost citizen = new CitizenToPost();
                citizen.setFullName(mName.getText().toString().trim());
                citizen.setEmail(user.getEmail());
                citizen.setUserName(mName.getText().toString().trim());
                citizen.setPassword("123123");
                citizen.setIdentityCode(user.getUid());
                citizen.setPhoneNumber(mPhoneNumber.getText().toString().trim());
                citizen.setAvatar("string");
                //post to api
                TSCCClient client = ApiController.getTsccClient();
                Call<CitizenToPost> call = client.CreateCitizen(citizen);
                call.enqueue(new Callback<CitizenToPost>() {
                    @Override
                    public void onResponse(Call<CitizenToPost> call, Response<CitizenToPost> response) {

                    }

                    @Override
                    public void onFailure(Call<CitizenToPost> call, Throwable t) {

                    }
                });
                //go to next activity
                Intent intent = new Intent(DetailInfomationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
*/
    }


}
