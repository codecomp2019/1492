package com.example.memeder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {


    private Button third_Btn;
    private ImageView hImageViewSemafor;
    private Button four_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        hImageViewSemafor = (ImageView) findViewById(R.id.idImageViewSemafor);
        third_Btn = (Button) findViewById(R.id.third_Btn);
        third_Btn.setOnClickListener(mButtonChangeImageListener);

        hImageViewSemafor = (ImageView) findViewById(R.id.idImageViewSemafor);
        four_Btn = (Button) findViewById(R.id.fourBtn);
        four_Btn.setOnClickListener(mButtonChangeBack);

    }

    View.OnClickListener mButtonChangeImageListener = new View.OnClickListener() {
        public void onClick(View v) {
            hImageViewSemafor.setImageResource(R.drawable.example);
        }
    };
        View.OnClickListener mButtonChangeBack = new View.OnClickListener() {
            public void onClick(View v) {
                hImageViewSemafor.setImageResource(R.drawable.catmeme);
            }
        };

}


