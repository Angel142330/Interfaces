package com.example.fragmentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Gif extends AppCompatActivity {


    private ImageView gif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);


        gif = findViewById(R.id.gif);

        //URL DEL GIFT Y IMPLEMENTACION CON LA CLASE GLIDE, DAR PERMISOS EN MANIFEST Y IMPLEMENTS EN GRADLE
        String url = "https://cdn.dribbble.com/users/614270/screenshots/13802801/media/b78451513f771e068ef7fc0c31c6aefe.gif";

        Glide.with(getApplicationContext()).load(url).into(gif);

        /*
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(

                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                        }
                    }
                }
        );*/

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        TranslateAnimation animation = new TranslateAnimation(width,0,0,0);
        animation.setDuration(4000);
        gif.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentLogin = new Intent(Gif.this, Login.class);
                startActivity(intentLogin);
                finish();
            }
        }, 8000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.gift, this.getTheme()));
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.gift, this.getTheme()));
        }

    }
}
