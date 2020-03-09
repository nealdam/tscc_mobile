package capstone.spring20.tscc_mobile;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import capstone.spring20.tscc_mobile.Entity.TrashRequest;
import capstone.spring20.tscc_mobile.constant.TrashSizeConstant;
import capstone.spring20.tscc_mobile.constant.TrashTypeConstant;
import capstone.spring20.tscc_mobile.constant.TrashWidthConstant;

public class RequestActivity extends AppCompatActivity {

    Spinner mType, mWidth, mSize;
    Button mSubmit, mGallery;
    FusedLocationProviderClient mFusedLocationClient;
    double myLatitude;
    double myLongitude;
    Bitmap image;
    String imageEncoded;
    List<String> imageStringList = new ArrayList<>();
    List<Bitmap> imageList = new ArrayList<>();
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
            imageList.add(image);
        }

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
                String trashType = mType.getSelectedItem().toString();
                String trashSize = mSize.getSelectedItem().toString();
                String trashWidth = mWidth.getSelectedItem().toString();
                if (!imageList.isEmpty()) {
                    for (Bitmap img : imageList) {
                        imageEncoded = convertBitmapToString(img);
                        imageStringList.add(imageEncoded);
                    }
                }

                TrashRequest trashRequest = new TrashRequest(TrashTypeConstant.getTrashTypeId(trashType),
                        TrashSizeConstant.getTrashSizeId(trashSize),
                        TrashWidthConstant.getTrashWidthId(trashWidth),
                        myLatitude, myLongitude,
                        imageStringList);
                //then send request to api

            }
        });

        mGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK && null != data && null != data.getClipData()) {
                ClipData mClipData = data.getClipData();

                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    Uri uri = mClipData.getItemAt(i).getUri();
                    Bitmap img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageList.add(img);
                }
            } else {
                Toast.makeText(this, "You haven't picked any Image",
                        Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "st wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void setupBasic(){
        mType = findViewById(R.id.spType);
        mSize = findViewById(R.id.spSize);
        mWidth = findViewById(R.id.spWidth);
        mSubmit = findViewById(R.id.btnSendRequest);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mGallery = findViewById(R.id.btnGallery);
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
