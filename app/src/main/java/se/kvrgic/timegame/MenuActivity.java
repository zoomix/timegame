package se.kvrgic.timegame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class MenuActivity extends Activity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mediaPlayer = MediaPlayer.create(this, R.raw.komiku_ending);
        mediaPlayer.setLooping(true);
        boolean isMusicEnabled = isMusicEnabled();
        if (isMusicEnabled) {
            mediaPlayer.start();
        }
        ((Switch)findViewById(R.id.menu_music)).setChecked(isMusicEnabled);
    }

    public void onStop() {
        super.onStop();
        mediaPlayer.pause();
    }

    public void onRestart() {
        super.onRestart();
        if (isMusicEnabled()) {
            mediaPlayer.start();
        }
    }

    public void doEasyGame(View view) {
        startActivity(new Intent(this, Game1Activity.class));
    }

    public void doHardGame(View view) {

    }

    public void doInfo(View view) {
        startActivity(new Intent(this, InfoActivity.class));
    }

    public void doMusicToggled(View view) {
        boolean isMusicEnabled = ((Switch)view).isChecked();
        setMusicEnabled(isMusicEnabled);

        if (isMusicEnabled) {
            mediaPlayer.start();
        } else {
            mediaPlayer.pause();
        }
    }


    private boolean isMusicEnabled() {
        return getSharedPreferences("mainmenu", MODE_PRIVATE).getBoolean("musicenabled", true);
    }

    private void setMusicEnabled(boolean isMusicEnabled) {
        SharedPreferences.Editor mainmenuEditor = getSharedPreferences("mainmenu", MODE_PRIVATE).edit();
        mainmenuEditor.putBoolean("musicenabled", isMusicEnabled);
        mainmenuEditor.apply();
    }
}
