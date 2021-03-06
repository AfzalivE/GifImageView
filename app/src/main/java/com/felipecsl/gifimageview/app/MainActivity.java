package com.felipecsl.gifimageview.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.felipecsl.gifimageview.library.GifImageView;
import com.felipecsl.gifimageview.library.PausableGifView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private PausableGifView mPausableGifView;
    private GifImageView gifImageView;
    private Button btnToggle;
    private Button btnBlur;

    private boolean shouldBlur = false;
    Blur blur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPausableGifView = (PausableGifView) findViewById(R.id.gifImageView);
        gifImageView = mPausableGifView.getGifImageView();

        btnToggle = (Button) findViewById(R.id.btnToggle);
        btnBlur = (Button) findViewById(R.id.btnBlur);
        final Button btnClear = (Button) findViewById(R.id.btnClear);

        blur = Blur.newInstance(this);


        gifImageView.setOnFrameAvailable(new GifImageView.OnFrameAvailable() {
            @Override
            public Bitmap onFrameAvailable(Bitmap bitmap) {
                if (shouldBlur)
                    return blur.blur(bitmap);
                return bitmap;
            }
        });

//        gifImageView.setOnAnimationStop(new OnAnimationStop() {
//            @Override
//            public void onStop() {
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MainActivity.this, "Animation ended", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

        btnToggle.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnBlur.setOnClickListener(this);

        mPausableGifView.setGif("chop.gif");
        mPausableGifView.setThumbnail(R.drawable.chop);

//        new GifDataDownloader() {
//            @Override
//            protected void onPostExecute(final byte[] bytes) {
//                gifImageView.setBytes(bytes);
//                gifImageView.startAnimation();
//                Log.d(TAG, "GIF width is " + gifImageView.getGifWidth());
//                Log.d(TAG, "GIF height is " + gifImageView.getGifHeight());
//            }
//        }.execute("http://gifs.joelglovier.com/aha/aha.gif");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(final View v) {
        if (v.equals(btnToggle)) {
            if (gifImageView.isAnimating())
                gifImageView.stopAnimation();
            else
                gifImageView.startAnimation();
        } else if (v.equals(btnBlur)) {
            shouldBlur = !shouldBlur;
        } else {
            gifImageView.clear();
        }
    }
}
