package in.co.thingsdata.gurukul.ui.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.ui.AppStart.MainActivity;

public class ImageCropped extends Activity implements CropImageView.OnGetCroppedImageCompleteListener,  CropImageView.OnSetImageUriCompleteListener{

    private String TAG = "cropedImage";
    private CropImageView mCropImageView;
    private View mProgressView;

    private Uri mCropImageUri;

    private TextView mProgressViewText;
    @Override
    public void onGetCroppedImageComplete(CropImageView view, Bitmap bitmap, Exception error) {
        mProgressView.setVisibility(View.INVISIBLE);
        if (error == null) {
            Log.e("Crop", "Success to crop image", error);
            if (bitmap != null) {
                // mCropImageView.setImageBitmap(bitmap);
                Intent editCampaignIntent;
                createImagefromBitmap(bitmap, MainActivity.STUDENT_PROFILE_CROPED_NAME);
                finish();
            }else{
                Toast.makeText(this, "Please Try again", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e("Crop", "Failed to crop image", error);
            Toast.makeText(this,  "Failed to crop image ,Please Try again", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {

        mProgressView.setVisibility(View.INVISIBLE);
        if (error != null) {
            Log.e("Crop", "Failed to load image for cropping", error);
            Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_LONG).show();
        }else{
            Log.e("Crop", "Success to load image for cropping", error);
            mCropImageUri = uri;
        }
    }

    final int REQUEST_GALLERY_IMAGE_SELECTOR = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.croped_image);
        if(mCropImageView == null) {
            mCropImageView = (CropImageView) findViewById(R.id.CropImageView);
            mProgressView = findViewById(R.id.ProgressView);
            mProgressViewText = (TextView) findViewById(R.id.ProgressViewText);

            Intent intent = new Intent();
            // Show only images, no videos or anything else
            intent.setType("image/*"); //MediaStore.ACTION_IMAGE_CAPTURE
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            // Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY_IMAGE_SELECTOR);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");

            mCropImageView.setOnSetImageUriCompleteListener(this);
            mCropImageView.setOnGetCroppedImageCompleteListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
        mCropImageView.setOnSetImageUriCompleteListener(null);
        mCropImageView.setOnGetCroppedImageCompleteListener(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  Log.v(TAG, "onDestroy" + galleryStarted);
        // galleryStarted =false;
    }

    public void onCropImageClick(View view) {
        mCropImageView.getCroppedImageAsync(500, 500);
        mProgressViewText.setText("Cropping...");
        mProgressView.setVisibility(View.VISIBLE);


    }

    public void createImagefromBitmap(Bitmap bitmap,String fileName){

        Log.v("createImagefromBitmap", "start");
        try{

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            Log.v("createImagefromBitmap", "try");
            fo.write(bytes.toByteArray());
            fo.close();
        }catch (Exception e){
            e.printStackTrace();
            fileName=null;
            Log.v("createImagefromBitmap","catch");
        }
    }


    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(this);
    }

    @SuppressLint("NewApi")
    public void onSelectImageClick(View view) {
        if (CropImage.isExplicitCameraPermissionRequired(this)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
        } else {
            CropImage.startPickImageActivity(this);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int  requestCode, int resultCode, Intent data) {

        Uri imageUri = null;
        if (resultCode == Activity.RESULT_OK) {


            imageUri = CropImage.getPickImageResultUri(this, data);


            // For API >= 23 we need to check specifically that we have permissions to read external storage,
            // but we don't know if we need to for the URI so the simplest is to try open the stream and see if we get error.
            boolean requirePermissions = false;
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {

                // request permissions and handle the result in onRequestPermissionsResult()
                requirePermissions = true;
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            }

            if (!requirePermissions) {
                mCropImageView.setImageUriAsync(imageUri);
                mProgressViewText.setText("Loading...");
                mProgressView.setVisibility(View.VISIBLE);

            }
        }
        else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri outputFileUri = CropImage.getCaptureImageOutputUri(this);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE);
                }

            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mCropImageView.setImageUriAsync(mCropImageUri);
                mProgressViewText.setText("Loading...");
                mProgressView.setVisibility(View.VISIBLE);

            } else {
                Toast.makeText(this, "Required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void onCancelImageClick(View view) {
        finish();
    }
}
