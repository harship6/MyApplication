package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends YouTubeBaseActivity {

    YouTubePlayerView mYoutubePlayerView;

    Intent i;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mYoutubePlayerView = findViewById(R.id.youtubePlay);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (b){Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();}
                else{
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                //single video
                i = getIntent();
                String url = i.getStringExtra("loadUrl");
                youTubePlayer.loadVideo(url);
                //multiple videos
                //List<String> videoList = new ArrayList<>();
                //videoList.add("");
                //videoList.add("");
                //youTubePlayer.loadVideos(videoList);

                //playlist
                //youTubePlayer.loadPlaylist("");


            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        };
        mYoutubePlayerView.initialize(YotubePlayer.getApiKey(),mOnInitializedListener);
    }
}