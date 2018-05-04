package mobwebtech2.com.bcu.kristian.discogsapp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import mobwebtech2.com.bcu.kristian.discogsapp.Models.Video;
import mobwebtech2.com.bcu.kristian.discogsapp.R;

public class VideoView extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView youTubeVideo;
    private TextView videoTitle;
    private static final int RECOVERY_REQUEST = 1;
    private static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);

        youTubeVideo = findViewById(R.id.youtubePlayer);
        videoTitle = findViewById(R.id.videoTitle);
        youTubeVideo.initialize("AIzaSyAmZHfH-xv-vAe-sNw7hBS_mF1J6oadVcg",this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {

        Bundle bundle = getIntent().getExtras();
        final Video video = bundle.getParcelable("video details");

        videoTitle.setText(video.getTitle());

        if (!wasRestored) {

            String uri = video.getUri();
            String videoURL = uri.replace("https://www.youtube.com/watch?v=", "");

            player.cueVideo(videoURL);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {

        if (errorReason.isUserRecoverableError()) {

            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {

            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Log.d(TAG, error);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {

            getYouTubePlayerProvider().initialize("AIzaSyAmZHfH-xv-vAe-sNw7hBS_mF1J6oadVcg", this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeVideo;
    }
}
