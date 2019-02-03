package com.example.memeder;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    String meme_description = "This is the description of the Meme";
    TextToSpeech tts;
    String image_save = "Meme is now saved.";
    String app_instructions = "To operate our application, swipe down for desrciption, up to save, right for next, left for previous and shake to share. Double tap for instructions to repeat.  Thank you and enjoy.";
    String previous_swipe = "Previous Meme";
    String next_swipe = "Next Meme";
    String share_shake = "Sharing Meme";

    Meme curr = new Meme();

    private GestureDetector detector;

    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;

    private static final int SHAKE_THRESHOLD = 800;
    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    //Tag for log messages
    private static final String API = "API_Execute";
    private static final String OBJ = "Object";
    //URL to be used
    private final String MEME = "http://172.28.170.170:3000/meme/";

    //int used for HTTP request
    int memeID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.detector = new GestureDetector(this, this);
        detector.setOnDoubleTapListener(this);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                    ConvertTextToSpeech5();
                }
            }
        });
        new APIRequest().execute();
    }

    /////////////Begin Gestures////////////////////////
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        ConvertTextToSpeech5();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        ConvertTextToSpeech5();
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        new APIRequest().execute();
        memeID++;
        if(memeID > 4){
            memeID = 0;
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
               /* if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH){
                    return false;
                }
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH){
                    return false;
                }*/
            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                onUpSwipe();
            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                onDownSwipe();
            } else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                onLeftSwipe();
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                onRightSwipe();
            }

        } catch (Exception e) {
        }
        return false;
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    //////////////////End of Gestures///////////////////////////

    public void onUpSwipe(){
        ConvertTextToSpeech1();
    }

    public void onDownSwipe(){
        ConvertTextToSpeech2();
    }

    public void onLeftSwipe(){
        ConvertTextToSpeech3();

    }

    public void onRightSwipe(){
        ConvertTextToSpeech4();
    }

    ///////////////////////Begin ConvertTextToSpeech Methods////////////////////////////////
    public void ConvertTextToSpeech1() {
        Toast.makeText(getApplicationContext(), image_save, Toast.LENGTH_SHORT).show();
        tts.speak(image_save, TextToSpeech.QUEUE_FLUSH, null);

    }

    public void ConvertTextToSpeech2 (){
        Toast.makeText(getApplicationContext(), meme_description, Toast.LENGTH_SHORT).show();
        tts.speak(curr.getDescription(), TextToSpeech.QUEUE_FLUSH, null);
    }

    public void ConvertTextToSpeech3(){
        Toast.makeText(getApplicationContext(), previous_swipe, Toast.LENGTH_SHORT).show();
        tts.speak(previous_swipe, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void ConvertTextToSpeech4 (){
        Toast.makeText(getApplicationContext(), next_swipe, Toast.LENGTH_SHORT).show();
        tts.speak(next_swipe, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void ConvertTextToSpeech5 (){
        Toast.makeText(getApplicationContext(), app_instructions, Toast.LENGTH_SHORT).show();
        tts.speak(app_instructions, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void ConvertTextToSpeech6(){
        Toast.makeText(getApplicationContext(), share_shake, Toast.LENGTH_SHORT).show();
        tts.speak(share_shake, TextToSpeech.QUEUE_FLUSH, null);
    }
    //////////////////////////End ConvertTextToSpeech Methods/////////////////////////////////////
    @Override
    public void onPause(){
        if (tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();

    }

    ///////////////////////////Begin Trying to connect to server////////////////////////////////////
    private class APIRequest extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String response;
            try {
                // HTTP GET
                WebHandler example = new WebHandler();
                // response = url[meme id/index]
                // log it
                response = example.run(MEME+memeID);
                //response contains JSON string
                //this needs to be converted from JSON to Meme class
                Gson g = new Gson();
                Meme m = g.fromJson(response, Meme.class);
                curr = m;
                Log.i(OBJ, m.toString());
            } catch (IOException e) {
                response = e.toString();
            }
            Log.i(API, response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // do something...
        }
    }

    /////////////////////////////End Trying to connect to server////////////////////////////////////
}

