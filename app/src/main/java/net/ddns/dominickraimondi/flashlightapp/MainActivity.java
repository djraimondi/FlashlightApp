package net.ddns.dominickraimondi.flashlightapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    //Starting variables
    private Camera camera;
    private boolean appNoCrash = false;

    //private CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

    ImageButton flashlightSwitchImg;
    private boolean isFlashlightOn;
    Camera.Parameters params;

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // flashlight on off Image
        flashlightSwitchImg = (ImageButton) findViewById(R.id.flashlightSwitch);

        boolean isCameraFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isCameraFlash) {
            showNoCameraAlert();
        } else {
            camera = Camera.open();
            params = camera.getParameters();
        }

        flashlightSwitchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashlightOn) {
                    setFlashlightOff();
                } else {
                    setFlashlightOn();
                }
            }
        });
    }

    private void showNoCameraAlert(){
        new AlertDialog.Builder(this)
                .setTitle("Error: No Camera Flash!")
                .setMessage("Camera flashlight not available in this Android device!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish(); // close the Android app
                        appNoCrash = true;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return;
    }
    private void setFlashlightOn() {

        if (appNoCrash == false) {
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
        }

        isFlashlightOn = true;
        flashlightSwitchImg.setImageResource(R.drawable.light_on);

    }

    private void setFlashlightOff() {
        if (appNoCrash == false) {
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
        }
        isFlashlightOn = false;
        flashlightSwitchImg.setImageResource(R.drawable.light_off);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
            camera = null;
        }

    }
}
