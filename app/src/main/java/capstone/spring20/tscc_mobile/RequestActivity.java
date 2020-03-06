package capstone.spring20.tscc_mobile;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import capstone.spring20.tscc_mobile.Api.ApiController;
import capstone.spring20.tscc_mobile.Api.TSCCClient;
import capstone.spring20.tscc_mobile.Entity.TrashRequest;
import capstone.spring20.tscc_mobile.constant.TrashSizeConstant;
import capstone.spring20.tscc_mobile.constant.TrashTypeConstant;
import capstone.spring20.tscc_mobile.constant.TrashWidthConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestActivity extends AppCompatActivity {

    Spinner mType, mWidth, mSize;
    Button mSubmit;
    FusedLocationProviderClient mFusedLocationClient;
    double myLatitude;
    double myLongitude;
    Bitmap image;
    String imageEncoded;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        setupBasic();
        setupSpinner();

        //get image from previous activity
        Bundle data = getIntent().getExtras();
        if (data != null) {
            image = (Bitmap) data.get("image");
            imageEncoded = convertBitmapToString(image);
        }



        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
                String trashType = mType.getSelectedItem().toString();
                String trashSize = mSize.getSelectedItem().toString();
                String trashWidth = mWidth.getSelectedItem().toString();
                List<String> imageList = new ArrayList<>();
                if (image != null) {
                    imageEncoded = convertBitmapToString(image);
                    imageList.add(imageEncoded);
                }

                TrashRequest trashRequest = new TrashRequest(TrashTypeConstant.getTrashTypeId(trashType),
                        TrashSizeConstant.getTrashSizeId(trashSize),
                        TrashWidthConstant.getTrashWidthId(trashWidth),
                        myLatitude, myLongitude,
                        imageList);
                //then send request to api
                postTrashRequest(trashRequest);
            }
        });

    }

    private void postTrashRequest(TrashRequest trashRequest) {
        TSCCClient client = ApiController.getTsccClient();
        Call<TrashRequest> call = client.sendTrashRequest(trashRequest);
        call.enqueue(new Callback<TrashRequest>() {
            @Override
            public void onResponse(Call<TrashRequest> call, Response<TrashRequest> response) {

            }

            @Override
            public void onFailure(Call<TrashRequest> call, Throwable t) {
            }
        });
    }

    public void setupBasic(){
        mType = findViewById(R.id.spType);
        mSize = findViewById(R.id.spSize);
        mWidth = findViewById(R.id.spWidth);
        mSubmit = findViewById(R.id.btnSendRequest);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    public void setupSpinner() {
        // Create an ArraytrashTypeAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> trashTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.trashType, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> trashWidthAdapter = ArrayAdapter.createFromResource(this,
                R.array.trashWidth, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> trashSizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.trashSize, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        trashTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trashSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trashWidthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the trashTypeAdapter to the spinner
        mType.setAdapter(trashTypeAdapter);
        mSize.setAdapter(trashSizeAdapter);
        mWidth.setAdapter(trashWidthAdapter);
    }

    private void getLastLocation() {
        mFusedLocationClient.getLastLocation().addOnCompleteListener( new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        Toast.makeText(RequestActivity.this, location.toString(), Toast.LENGTH_LONG).show();
                        if (location != null) {
                           myLatitude = location.getLatitude();
                           myLongitude = location.getLongitude();
                        }
                    }
                }
        );
    }

    public String convertBitmapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }
}
