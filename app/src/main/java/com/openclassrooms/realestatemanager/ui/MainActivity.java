package com.openclassrooms.realestatemanager.ui;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.utils.UseBlurLib;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MainActivity extends ConnectivityActivity {

    private TextView textViewMain;
    private TextView textViewQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main); // id of text view was incorrect
        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);

        this.configureTextViewMain();
        this.configureTextViewQuantity();

        this.textViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            this.blur();
        } else {
            BlurView blurView = this.findViewById(R.id.blur_layout);
            ViewGroup viewGroup = this.findViewById(R.id.image_view_wall_paper_layout);
            new UseBlurLib().blur(blurView, viewGroup);
        }
    }

    private void configureTextViewMain(){
        this.textViewMain.setTextSize(15);
        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
    }

    private void configureTextViewQuantity(){
        String quantity = String.valueOf(Utils.convertDollarToEuro(100)); // String instead of int
        this.textViewQuantity.setTextSize(20);
        this.textViewQuantity.setText(quantity);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void blur() {
        ImageView imageView = this.findViewById(R.id.image_view_wall_paper);

        RenderEffect renderEffect = RenderEffect.createBlurEffect(40, 40, Shader.TileMode.MIRROR);
        imageView.setRenderEffect(renderEffect);
    }



}
