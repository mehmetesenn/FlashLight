package com.mehmetesen.flashlight;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.health.PackageHealthStats;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

      
public class MainActivity extends AppCompatActivity {
    ImageButton imageButton;
    boolean state;
    int camera_request_code=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButton= findViewById(R.id.flashlight);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                permission();
            }
        });

        }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public void permission(){
          if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
              if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
                  requestPermissions(new String[] {Manifest.permission.CAMERA},camera_request_code);

              }else{
                  runFlashlight();

              }
          }else{
              runFlashlight();
          }

        }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==camera_request_code && resultCode==RESULT_OK){
            runFlashlight();
        }else{
            Toast.makeText(this, "permission needed", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void runFlashlight() {


                    CameraManager cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
                    try{
                       String cameraId= cameraManager.getCameraIdList()[0];
                       cameraManager.setTorchMode(cameraId,true);
                       state=true;
                       imageButton.setImageResource(R.drawable.ic_baseline_flashlight_on_24);


                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void stopFlashLight(){
        CameraManager cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try{
            String cameraId= cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId,true);
            state=true;
            imageButton.setImageResource(R.drawable.ic_baseline_flashlight_off_24);


        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }
}