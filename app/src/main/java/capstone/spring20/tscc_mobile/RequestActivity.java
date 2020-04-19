package capstone.spring20.tscc_mobile;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import capstone.spring20.tscc_mobile.Api.ApiController;
import capstone.spring20.tscc_mobile.Api.TSCCClient;
import capstone.spring20.tscc_mobile.Entity.TrashRequest;
import capstone.spring20.tscc_mobile.adapter.ImageAdapter;
import capstone.spring20.tscc_mobile.constant.TrashSizeConstant;
import capstone.spring20.tscc_mobile.constant.TrashTypeConstant;
import capstone.spring20.tscc_mobile.constant.TrashWidthConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestActivity extends AppCompatActivity {
    String TAG = "RequestActivity";

    Spinner mType, mWidth, mSize;
    Button mSubmit, mGallery, btnBack;
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
            imageNum = imageList.size (); //show số lượng image
            mImageNum.setText("image number:" + imageNum);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {


            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                // When an Image is picked
                if(data.getData()!=null){
                    imageNum += 1;
                    mImageNum.setText("image number: " + imageNum);
                    Uri mImageUri=data.getData();
                    imageEncoded = mImageUri.getPath();

                    //BitmapFactory.Options options = new BitmapFactory.Options ();
                    //Bitmap img = BitmapFactory.decodeFile ( imageEncoded, options );

                    Bitmap img = MediaStore.Images.Media.getBitmap ( this.getContentResolver () ,mImageUri );
                    imageList.add ( img );
                    gridView = findViewById(R.id.gridview);
                    gridView.setAdapter ( new ImageAdapter ( this, imageList ) );
                } else {
                    // multi images
                    if (null != data.getClipData()) {
                        ClipData mClipData = data.getClipData();
                        //update image number
                        imageNum += mClipData.getItemCount();
                        mImageNum.setText("image number: " + imageNum);
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            Uri uri = mClipData.getItemAt(i).getUri();
                            Bitmap img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            imageList.add(img);
                        }
                        gridView = findViewById(R.id.gridview);
                        gridView.setAdapter ( new ImageAdapter ( this, imageList ) );
                    } else {
                        Toast.makeText(this, "You haven't picked any Image",
                                Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
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
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestActivity.this.onBackPressed();
            }
        });

        //set img array
        gridView = findViewById(R.id.gridview);
        gridView.setAdapter ( new ImageAdapter ( this, imageList ) );

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
//        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    @Override
    public void onBackPressed() {
        // đặt resultCode là Activity.RESULT_CANCELED thể hiện
        // đã thất bại khi người dùng click vào nút Back.
        setResult( Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}
