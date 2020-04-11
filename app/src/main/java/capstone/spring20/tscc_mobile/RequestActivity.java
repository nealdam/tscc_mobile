package capstone.spring20.tscc_mobile;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
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

import capstone.spring20.tscc_mobile.Api.ApiController;
import capstone.spring20.tscc_mobile.Api.TSCCClient;
import capstone.spring20.tscc_mobile.Entity.TrashRequest;
import capstone.spring20.tscc_mobile.constant.TrashSizeConstant;
import capstone.spring20.tscc_mobile.constant.TrashTypeConstant;
import capstone.spring20.tscc_mobile.constant.TrashWidthConstant;
import capstone.spring20.tscc_mobile.util.CustomGridAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestActivity extends AppCompatActivity {
    String TAG = "RequestActivity";

    Spinner mType, mWidth, mSize;
    Button mSubmit, mGallery;
    FusedLocationProviderClient mFusedLocationClient;
    double myLatitude;
    double myLongitude;
    Bitmap image;
    String imageEncoded;
    List<String> imageStringList = new ArrayList<>();
    List<Bitmap> imageList = new ArrayList<>();
    String token;
    TextView mImageNum;
    int imageNum = 0;
    GridView gridView;
//    ImageView mImageView1;

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
            imageNum = 1; //show số lượng image
            mImageNum.setText("image number:" + imageNum);
//            mImageView1.setImageBitmap(image);
            updateImageUI(imageList); //update gridview show ảnh
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK && null != data && null != data.getClipData()) {
                ClipData mClipData = data.getClipData();
                //update image number
                imageNum += mClipData.getItemCount();
                mImageNum.setText("image number: " + imageNum);
                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    Uri uri = mClipData.getItemAt(i).getUri();
                    Bitmap img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageList.add(img);
                }
                updateImageUI(imageList);
            } else {
                Toast.makeText(this, "You haven't picked any Image",
                        Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "st wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void postTrashRequest(TrashRequest trashRequest) {
        TSCCClient client = ApiController.getTsccClient();
        Call<TrashRequest> call = client.sendTrashRequest(token, trashRequest);
        call.enqueue(new Callback<TrashRequest>() {
            @Override
            public void onResponse(Call<TrashRequest> call, Response<TrashRequest> response) {
                Toast.makeText(RequestActivity.this, "Send request success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RequestActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onFailure(Call<TrashRequest> call, Throwable t) {
                Toast.makeText(RequestActivity.this, "Send request fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupBasic(){
        mType = findViewById(R.id.spType);
        mSize = findViewById(R.id.spSize);
        mWidth = findViewById(R.id.spWidth);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mImageNum = findViewById(R.id.txtImageNum);
        gridView = findViewById(R.id.gridview);
        //get jwt token
        SharedPreferences jwtSharedPreferences = this.getSharedPreferences("JWT", MODE_PRIVATE);
        token = jwtSharedPreferences.getString("token", "");
        //get current location
        SharedPreferences locationSharedPreferences = this.getSharedPreferences("Location", MODE_PRIVATE);
        myLatitude = locationSharedPreferences.getFloat("latitude", 0);
        myLongitude = locationSharedPreferences.getFloat("longitude", 0);

        mSubmit = findViewById(R.id.btnSendRequest);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                postTrashRequest(trashRequest);
            }
        });

        mGallery = findViewById(R.id.btnLibrary);
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
//        mImageView1 = findViewById(R.id.imageView1);
    }

    public void setupSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
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
        // Apply the Adapter to the spinner
        mType.setAdapter(trashTypeAdapter);
        mSize.setAdapter(trashSizeAdapter);
        mWidth.setAdapter(trashWidthAdapter);
    }

    public String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public void updateImageUI(List<Bitmap> list) {
        gridView.setAdapter(new CustomGridAdapter(this, list));
    }
}
