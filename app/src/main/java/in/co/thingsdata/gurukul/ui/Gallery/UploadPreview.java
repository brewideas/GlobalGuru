package in.co.thingsdata.gurukul.ui.Gallery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import in.co.thingsdata.gurukul.NoticeficationPanel.ShowNotificationActivity;
import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.GetNotificationStatsData;
import in.co.thingsdata.gurukul.data.UpdateImageRequestData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetNotificationStatsRequest;
import in.co.thingsdata.gurukul.services.request.UploadImageRequest;

import static in.co.thingsdata.gurukul.ui.Gallery.schoolGallery.setRefreshGalleryFlag;

public class UploadPreview extends Activity {

    String mFilePath = null;
    File mFile;
    private ImageView mImgPreview;
    private Button mButtonUpload;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_preview);

        mImgPreview = (ImageView)findViewById(R.id.ImageUploadImgPreview);

        Bundle extras = getIntent().getExtras();

        // image or video path that is captured in previous activity
        mFilePath = extras.getString("filePath");
        mFile = new File(mFilePath);

        if (mFilePath != null) {
            // Displaying the image or video on the screen
            previewMedia(true);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }
    }

    public void upload (View v){
        mProgressDialog = new ProgressDialog(UploadPreview.this, android.R.style.Theme_DeviceDefault_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("");
        mProgressDialog.show();
        new UploadFileToServer().execute();
    }


    /**
     * Displaying captured image/video on the screen
     * */
    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (true) {
            mImgPreview.setVisibility(View.VISIBLE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap myBitmap = BitmapFactory.decodeFile(mFile.getAbsolutePath());

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            mImgPreview.setImageBitmap(myBitmap);
        }
    }


    private class UploadFileToServer extends AsyncTask<Void, Integer, Boolean> implements UploadImageRequest.UpdateImageResponseCallback {

        @Override
        protected Boolean doInBackground(Void... params) {
            return uploadFile();
        }

        private boolean uploadFile() {
            UpdateImageRequestData data = new UpdateImageRequestData(mFile);
            UploadImageRequest req = new UploadImageRequest(UploadPreview.this, data, this);
            req.executeRequest();
            return true;
        }

        @Override
        public void onUpdateImageResponse(CommonRequest.ResponseCode res, UpdateImageRequestData data) {
            mProgressDialog.dismiss();
            String result;
            int dlg_type = SweetAlertDialog.SUCCESS_TYPE;

            if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
                result = "Image has been uploaded successfully";
                setRefreshGalleryFlag(true);
            }
            else
            {
                if (res == CommonRequest.ResponseCode.COMMON_RES_FAILED_TO_CONNECT){
                    result = "Image has been uploaded successfully";
                }
                else if(res == CommonRequest.ResponseCode.COMMON_RES_CONNECTION_TIMEOUT){
                    result = "Failed, Please check internet connection and try later";
                }else{
                    result = "Something went wrong, please try again";
                }
                dlg_type = SweetAlertDialog.ERROR_TYPE;
            }
            new SweetAlertDialog(UploadPreview.this, dlg_type)
                    .setTitleText("Image Upload")
                    .setContentText(result)
                    .setConfirmText("OK")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.cancel();
                            finish();
                        }
                    })
                    .show();
        }
    }
/*
    public String getPath(Uri uri, CommonFileUpload.FileType type)
    {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            Cursor cursor = mActivity.getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();
            cursor = mActivity.getApplicationContext().getContentResolver().query(
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
    }*/

    public String getImagePathFromURI(Uri contentURI)
    {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
