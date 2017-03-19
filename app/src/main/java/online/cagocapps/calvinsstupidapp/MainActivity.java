package online.cagocapps.calvinsstupidapp;

import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;


public class MainActivity extends AppCompatActivity {
    private VideoView videoView;
    private int i = 1; //(int)(Math.random() *5+1);
    private String path;
    private Button button;
    private Long length;
    private Handler h;
    private Runnable r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (VideoView) findViewById(R.id.videoView);
        button = (Button) findViewById(R.id.button);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (videoView.isPlaying()) goBack();
                h.removeCallbacks(r);
                return true;
            }
        }
    );
    }

    public void playVideo(View view) {
        int l = (int) (Math.random()* 5 +1);
        if ( l == 5 ) i = l;
        if (!videoView.isPlaying()) {
            if (i == 1) {
                path = "android.resource://" + getPackageName() + "/" + R.raw.donteatme;
                length = (long) 1750;
            } else if (i == 2) {
                path = "android.resource://" + getPackageName() + "/" + R.raw.imalady;
                length = (long) 4000;
            } else if (i == 3) {
                path = "android.resource://" + getPackageName() + "/" + R.raw.peeingmyself;
                length = (long) 1750;
            } else if (i == 4) {
                path = "android.resource://" + getPackageName() + "/" + R.raw.scream;
                length = (long) 4000;
            } else if (i == 5) {
                path = "android.resource://" + getPackageName() + "/" + R.raw.silentnight;
                length = (long) 115000;
            }
            MediaController mc = new MediaController(videoView.getContext());
            mc.setAnchorView(videoView);
            mc.setMediaPlayer(videoView);
            videoView.setVisibility(View.VISIBLE);
            button.setVisibility(View.INVISIBLE);

            videoView.setMediaController(mc);
            videoView.setVideoURI(Uri.parse(path));
            videoView.start();
            r = new Runnable() {
                @Override
                public void run() {
                    goBack();
                }
            };
            h = new Handler();
            h.postDelayed(r, length);
        } else goBack();
    }

    private void goBack(){
        videoView.stopPlayback();
        videoView.setVisibility(View.GONE);
        i++;
        if (i >= 5) i = 1;
        //i = (int)(Math.random() *5+1);

    }
}
