package com.matrixdev.dremergency.Helpers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.matrixdev.dremergency.Controllers.BaseActivity;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.Utils.ImageUtils;
import com.takusemba.cropme.CropView;
import com.takusemba.cropme.OnCropListener;


public class CropImage extends BaseActivity {

    private CropView cropView;
    private ImageView cropBtn;
    private ImageView cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

        init();
        listener();
    }

    private void listener() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropView.crop(new OnCropListener() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {

                        Intent i = new Intent();
                        i.putExtra(Constants.INTENT_CROP_IMAGE, ImageUtils.bitmapToFile(bitmap));
                        setResult(Constants.SUCCESS_CODE, i);
//                        i.putExtra(Constants.INTENT_USER_DATA, getLoginData());
//                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure() {
                    }
                });
            }
        });
    }

    private void init() {
        cropBtn = findViewById(R.id.crop_btn);
        cancelBtn = findViewById(R.id.cancel_btn);
        cropView = findViewById(R.id.crop_view);


        if(getIntent().hasExtra(Constants.IMAGE_URL_INTENT)) {
            cropView.setUri((Uri) getIntent().getExtras().get(Constants.IMAGE_URL_INTENT));
        }

    }
}
