package in.co.thingsdata.gurukul.ui.Gallery;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.GetGalleryImageData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetGalleryImageRequest;

import static in.co.thingsdata.gurukul.services.helper.CommonRequest.ResponseCode.COMMON_RES_FAILED_TO_CONNECT;
import static in.co.thingsdata.gurukul.services.helper.CommonRequest.ResponseCode.COMMON_RES_IMAGE_NOT_FOUND;
import static in.co.thingsdata.gurukul.services.helper.CommonRequest.ResponseCode.COMMON_RES_INTERNAL_ERROR;
import static in.co.thingsdata.gurukul.services.helper.CommonRequest.ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE;

public class schoolGallery extends AppCompatActivity implements GetGalleryImageRequest.GetGalleryCallback {

    private static GetGalleryImageData mImageData = null;
    private GridView mGridView = null;
    private GalleryAdapter mAdapter = null;
    static  final String TAG = "schoolGallery";
    private static final int IMAGE_FROM_GALLERY_REQUEST_CODE = 0;
    private Uri mFileUri;
    private schoolGallery mActivity;
    private static boolean mNeedRefresh;
    TextView mCentralString;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNeedRefresh == true){
            GetGalleryImageData data = new GetGalleryImageData();
            GetGalleryImageRequest request = new GetGalleryImageRequest(this, data, this);
            request.executeRequest();

            mCentralString.setVisibility(View.VISIBLE);
            mGridView.setVisibility(View.INVISIBLE);
        }
    }

    public static void setRefreshGalleryFlag(boolean flag){mNeedRefresh = flag;}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = schoolGallery.this;
        setContentView(R.layout.school_gallery);

        if (!UserData.getUserType().contentEquals(CommonDetails.USER_TYPE_PRINCIPAL))
        {
            FloatingActionButton btn = (FloatingActionButton)findViewById(R.id.addImageFloatingButton);
            btn.setVisibility(View.INVISIBLE);
        }

        mCentralString = (TextView) findViewById(R.id.rcSchoolGalleryCenterString);
        mGridView = (GridView) findViewById(R.id.rcSchoolGallery);

        mNeedRefresh = true;

        //theimage = prepareData();
    }

    public static GetGalleryImageData getImageData(){return mImageData;}

    @Override
    public void onGalleryResponse(CommonRequest.ResponseCode res, GetGalleryImageData data) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            mImageData = data;
            mCentralString.setVisibility(View.INVISIBLE);
            mGridView.setVisibility(View.VISIBLE);

            mAdapter = new GalleryAdapter(this, mImageData);
            mGridView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Intent i = new Intent(schoolGallery.this, GalleryImageViewActivity.class);
                    i.putExtra("Index", position);
                    startActivity(i);
                }
            });
        }
        else
        {
            mCentralString = (TextView) findViewById(R.id.rcSchoolGalleryCenterString);
            String error;
            if (res == COMMON_RES_FAILED_TO_CONNECT){
                error = "Fail to connect, please check internet connection";
            }
            else if (res == COMMON_RES_INTERNAL_ERROR){
                error = "Something went wrong, please try again";
            }
            else if (res == COMMON_RES_SERVER_ERROR_WITH_MESSAGE){
                error = data.getErrorMessage();
            }
            else if (res == COMMON_RES_IMAGE_NOT_FOUND){
                error = "No Image !!!";
            }
            else
            {
                error = "Something went wrong, please try again";
            }

            mCentralString.setText(error);
        }
        mNeedRefresh = false;
    }

    public void uploadImageButtonClick (View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
                {
                    // Explain to the user why we need to read the contacts
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return;
            }
            else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_FROM_GALLERY_REQUEST_CODE);
            }
        }
        else{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_FROM_GALLERY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_FROM_GALLERY_REQUEST_CODE);

            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        switch (requestCode) {
            case IMAGE_FROM_GALLERY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        mFileUri = data.getData();
                        launchUploadActivity();
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(this, "Invalid selection", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void launchUploadActivity(){
        Intent i = new Intent(schoolGallery.this, UploadPreview.class);
        i.putExtra("filePath", getRealPathFromURI(mFileUri));
        i.putExtra("isImage", true);
        startActivity(i);
    }

    private String getRealPathFromURI(Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            Cursor cursor = mActivity.getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();
            cursor = mActivity.getContentResolver().query(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);

            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            cursor.close();

            return path;
        }
        else
        {
            return uri.getPath();
        }
    }
}
