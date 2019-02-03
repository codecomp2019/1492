package com.example.memeder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    String meme_description = "This is the description of the Meme";
    TextToSpeech tts;
    String image_save = "Meme is now saved.";
    String app_instructions = "To operate our application.";
    String previous_swipe = "Previous Meme";
    String next_swipe = "Next Meme";
    String share_shake = "Sharing Meme";
    private GestureDetector detector;
    boolean requestingImage = false;
    Meme curr = new Meme();

    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    int[] arrayOfPics = {
            R.drawable.badluckfire,
            R.drawable.firstworldproblem,
            R.drawable.philosoraptor,
            R.drawable.scumbagsteve,
            R.drawable.successkid,
    };
    private ImageView imageView;

    //Tag for log messages
    private static final String API = "API_Execute";
    private static final String OBJ = "Object";
    //URL to be used
    private final String MEME = "http://172.28.170.170:3000/meme/";
    private final String ROOT = "http://172.28.170.170:3000/memes/";
    //int used for HTTP request
    int memeID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.detector = new GestureDetector(this, this);
        imageView = (ImageView) findViewById(R.id.imageView3);
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
        requestImage();
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
        if (memeID > 4) {
            memeID = 0;
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {

            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                onUpSwipe();
            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                onDownSwipe();
            } else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                onLeftSwipe();
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
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

    public void onUpSwipe() {
        ConvertTextToSpeech1();
    }

    public void onDownSwipe() {
        ConvertTextToSpeech2();
    }

    public void onLeftSwipe() {
        if (memeID - 1 >= 0) {
            memeID--;
            requestImage();
            ConvertTextToSpeech3();
        } else {
            tts.speak("No More images", TextToSpeech.QUEUE_FLUSH, null);
        }

    }


    public void onRightSwipe() {
        if (memeID + 1 < arrayOfPics.length) {
            memeID++;
            requestImage();
            ConvertTextToSpeech4();
        } else {
            tts.speak("No More images", TextToSpeech.QUEUE_FLUSH, null);
        }


    }

    private void requestImage(){
        if(requestingImage){
            return;
        }
        new APIRequest().execute();
        requestingImage = true;
    }

    private void updateMemeImage(Bitmap bitmap) {
        //Toast.makeText(getApplicationContext(), currentmemeindex, Toast.LENGTH_SHORT).show();
            imageView.setImageBitmap(bitmap);
            requestingImage = false;
    }

    ///////////////////////Begin ConvertTextToSpeech Methods////////////////////////////////
    public void ConvertTextToSpeech1() {
        Toast.makeText(getApplicationContext(), image_save, Toast.LENGTH_SHORT).show();
        tts.speak(image_save, TextToSpeech.QUEUE_FLUSH, null);

    }

    public void ConvertTextToSpeech2() {
        Toast.makeText(getApplicationContext(), meme_description, Toast.LENGTH_SHORT).show();
        tts.speak(curr.getDescription(), TextToSpeech.QUEUE_FLUSH, null);
    }


    public void ConvertTextToSpeech3() {
        Toast.makeText(getApplicationContext(), previous_swipe, Toast.LENGTH_SHORT).show();
        tts.speak(previous_swipe, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void ConvertTextToSpeech4() {
        Toast.makeText(getApplicationContext(), next_swipe, Toast.LENGTH_SHORT).show();
        tts.speak(next_swipe, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void ConvertTextToSpeech5() {
        Toast.makeText(getApplicationContext(), app_instructions, Toast.LENGTH_SHORT).show();
        tts.speak(app_instructions, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void ConvertTextToSpeech6() {
        Toast.makeText(getApplicationContext(), share_shake, Toast.LENGTH_SHORT).show();
        tts.speak(share_shake, TextToSpeech.QUEUE_FLUSH, null);
    }

    //////////////////////////End ConvertTextToSpeech Methods/////////////////////////////////////
    @Override
    public void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();

    }

    ///////////////////////////Begin Trying to connect to server////////////////////////////////////
    private class APIRequest extends AsyncTask<Void, Void, String> {

        private Bitmap bitmap;

        @Override
        protected String doInBackground(Void... voids) {
            String response;
            try {
                // HTTP GET
                WebHandler example = new WebHandler();
                // response = url[meme id/index]
                // log it
                response = example.run(MEME + memeID);
                //response contains JSON string
                //this needs to be converted from JSON to Meme class
                Gson g = new Gson();
                Meme m = g.fromJson(response, Meme.class);
                curr = m;
                try {
                    String spec = ROOT + curr.getFile();

                    bitmap = BitmapFactory.decodeStream((InputStream)new URL(spec).getContent());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            updateMemeImage(bitmap);
        }
    }
}

    /////////////////////////////End Trying to connect to server////////////////////////////////////
