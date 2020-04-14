package capstone.spring20.tscc_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    Button mCamera, mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBasic();

        getJWTAndSavetoSharedPreference();

        saveCurrentLocation();
    }

    private void saveCurrentLocation() {
        SharedPreferences shared = this.getSharedPreferences("Location", MODE_PRIVATE);
        final SharedPreferences.Editor editor = shared.edit();

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                                                                         @Override
                                                                         public void onComplete(@NonNull Task<Location> task) {
                                                                             Location location = task.getResult();
                                                                             if (location != null) {
                                                                                 editor.putFloat("latitude", (float) location.getLatitude());
                                                                                 editor.putFloat("longitude", (float) location.getLongitude());
                                                                                 editor.apply();
                                                                             }
                                                                         }
                                                                     }
        );
    }

    private void setupBasic() {
        mCamera = findViewById(R.id.btnCamera);
        mLogout = findViewById(R.id.btnLogout);

        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CaptureImagesActivity.class);
                startActivity(intent);
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            }
        });
    }

    private void getJWTAndSavetoSharedPreference() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences sharedPreferences = this.getSharedPreferences("JWT", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if (user != null) {
            user.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String token = Objects.requireNonNull(task.getResult()).getToken();
                                token = "Bearer " + token;
                                editor.putString("token", token);
                                editor.apply();
                            }
                        }
                    });
        }
    }


}
