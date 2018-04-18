package se.kvrgic.timegame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mediaPlayer = MediaPlayer.create(this, R.raw.komiku_ending);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }


    public void onStop() {
        super.onStop();
        mediaPlayer.pause();
    }

    public void onRestart() {
        super.onRestart();
        mediaPlayer.start();
    }

    public void doEasyGame(View view) {
        startActivity(new Intent(this, Game1Activity.class));
    }

    public void doHardGame(View view) {

    }

    public void doInfo(View view) {
        startActivity(new Intent(this, InfoActivity.class));
    }
}
