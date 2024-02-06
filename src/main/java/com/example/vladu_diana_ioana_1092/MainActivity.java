package com.example.vladu_diana_ioana_1092;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIMER = 3000;

    Animation topAnim, bottomAnim;
    ImageView imgView;
    TextView nameTV, grupaTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Asta ascunde banda cu bateria si ceasul = "status bar"
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Asta ascunde "action bar"
        getSupportActionBar().hide();

        //Animations - am gasit un tutorial in care se implementau pentru splash screen si animatii si mi s-au parut dragute
        //si am zis sa incerc sa implementez si eu.
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        imgView = findViewById(R.id.vladu_diana_ioana_cute_lama_imgView);
        nameTV = findViewById(R.id.vladu_diana_ioana_tv_MyName);
        grupaTV = findViewById(R.id.vladu_diana_ioana_tv_MyGrupa);

        imgView.setAnimation(topAnim);
        nameTV.setAnimation(bottomAnim);
        grupaTV.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, AnagramaActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_TIMER);
    }
}