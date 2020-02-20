package capstone.spring20.tscc_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CitizenProfileActivity extends AppCompatActivity {

    TextView mName, mRole, mEmail, mPhoneNumber;
    ImageView mAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mName = findViewById(R.id.txtName);
        mRole = findViewById(R.id.txtRole);
        mEmail = findViewById(R.id.txtEmail);
        mPhoneNumber = findViewById(R.id.txtPhoneNumber);
        mAvatar = findViewById(R.id.ivAvatar);
        //load profile data from sharedpreference
        loadProfileData();
    }

    private void loadProfileData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("profileData", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String name = sharedPreferences.getString("name", "");
            String role = sharedPreferences.getString("role", "");
            String email = sharedPreferences.getString("email", "");
            String phoneNumber = sharedPreferences.getString("phoneNumber", "");

            mName.setText(name);
            mRole.setText(role);
            mEmail.setText(email);
            mPhoneNumber.setText(phoneNumber);
        }
    }
}
