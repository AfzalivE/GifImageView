package com.felipecsl.gifimageview.library;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.felipecsl.gifimageview.library.GifImageView.OnAnimationStop;

/**
 * Created by afzal on 15-07-09.
 */
public class PausableGifView extends FrameLayout {
    private final GifImageView mGifImageView;
    private final ImageView mThumbnailView;
    private final ImageButton mPlayButton;

    public PausableGifView(Context context) {
        this(context, null);
    }

    public PausableGifView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.pausable_gif_view, this, true);

        mGifImageView = (GifImageView) getChildAt(0);
        mThumbnailView = (ImageView) getChildAt(1);

        mPlayButton = (ImageButton) getChildAt(2);

        mGifImageView.setOnAnimationStop(new OnAnimationStop() {
            @Override
            public void onStop() {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        mPlayButton.setVisibility(View.VISIBLE);
                        mThumbnailView.setVisibility(View.VISIBLE);
                    }
                };
                mainHandler.post(runnable);
            }
        });

        mGifImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mGifImageView.stopAnimation();
            }
        });

        mPlayButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mThumbnailView.setVisibility(View.INVISIBLE);
                mPlayButton.setVisibility(View.INVISIBLE);
                mGifImageView.startAnimation();
            }
        });
    }

    public void setThumbnail(int thumbnailRes) {
        mThumbnailView.setImageResource(thumbnailRes);
    }

    public void setGif(String gifSrc) {
        try {
            InputStream buf = getContext().getAssets().open(gifSrc);
            byte[] bytes = new byte[buf.available()];
            buf.read(bytes);
            mGifImageView.setBytes(bytes);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid gif asset specified");
        }

    }

    public GifImageView getGifImageView() {
        return mGifImageView;
    }
}
