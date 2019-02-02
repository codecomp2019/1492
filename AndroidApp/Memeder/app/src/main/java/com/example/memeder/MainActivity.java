package com.example.memeder;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String meme_description = "This is the description of the Meme";
    TextToSpeech tts;
    String image_save = "Imaged is now saved.";

    private Button first_Btn;
    private Button second_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button first_Btn = findViewById(R.id.first_Btn);
        final Button second_Btn = findViewById(R.id.second_Btn);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });

        first_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConvertTextToSpeech1();
            }

        });
    }



    public void ConvertTextToSpeech1() {
        Toast.makeText(getApplicationContext(), image_save, Toast.LENGTH_SHORT).show();
        tts.speak(image_save, TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override
    public void onPause(){
        if (tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();

    }
}
