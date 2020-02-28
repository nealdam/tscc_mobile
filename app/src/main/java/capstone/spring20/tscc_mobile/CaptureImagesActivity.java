package capstone.spring20.tscc_mobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import capstone.spring20.tscc_mobile.Entity.TrashRequest;

public class CaptureImagesActivity extends AppCompatActivity {

    TrashRequest trashRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_images);

        trashRequest = new TrashRequest();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            Bitmap image = (Bitmap) data.getExtras().get("data");
            trashRequest.ImageList.add(image);

            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1);
        }
    }
}
