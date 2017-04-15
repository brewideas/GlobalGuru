package in.co.thingsdata.gurukul.ui.AppStart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import in.co.thingsdata.gurukul.R;

public class MyProfile extends AppCompatActivity {

    ImageButton proImg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        proImg = (ImageButton)findViewById(R.id.profileBtn);
        showImageForBackround();
      }

    private void showImageForBackround(){
        Log.v("editCampaign", "showImageCampaign");
        try {
            FileInputStream in = openFileInput(MainActivity.STUDENT_PROFILE_CROPED_NAME);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            proImg.setImageDrawable(new BitmapDrawable(getResources(), bitmap));


        }catch (FileNotFoundException e){
            Log.v("MyProfile","Profile file not found");
        }

    }




    public void finishProfile(View view) {
        finish();
    }
}
