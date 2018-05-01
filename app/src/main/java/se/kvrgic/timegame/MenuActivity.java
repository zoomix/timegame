package se.kvrgic.timegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Switch;

import java.util.Arrays;

import se.kvrgic.timegame.data.GameState;

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

        doLoadTopScores(R.id.layoutEasyScore, "bestscore_game1");
        doLoadTopScores(R.id.layoutHardScore, "bestscore_game2");

        doAnimateView(R.id.layoutEasy, 0);
        doAnimateView(R.id.layoutHard, 150);
        doAnimateView(R.id.layoutInfo, 300);
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
        startActivity(new Intent(this, Game2Activity.class));
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

    public void doShowHelp(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.menu_help_header)
                .setMessage(R.string.menu_help)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {} )
                .create()
                .show();
    }



    private void doLoadTopScores(int layoutId, String sharedPrefName) {
        ConstraintLayout scoreLayout = findViewById(layoutId);

        GameState gameState = GameState.getStoredState(getSharedPreferences(sharedPrefName, MODE_PRIVATE));
        Arrays.asList(R.id.duckOk, R.id.duckFail, R.id.catFail, R.id.catOk, R.id.bananaOk, R.id.bananaFail, R.id.cowOk, R.id.cowFail).forEach((id) -> {
            scoreLayout.findViewById(id).setVisibility(View.INVISIBLE);
        });

        if (gameState.isRoundPlayed(1)) {
            scoreLayout.findViewById(gameState.isRoundWon(1) ? R.id.duckOk : R.id.duckFail).setVisibility(View.VISIBLE);
        }
        if (gameState.isRoundPlayed(2)) {
            scoreLayout.findViewById(gameState.isRoundWon(2) ? R.id.catOk : R.id.catFail).setVisibility(View.VISIBLE);
        }
        if (gameState.isRoundPlayed(3)) {
            scoreLayout.findViewById(gameState.isRoundWon(3) ? R.id.bananaOk : R.id.bananaFail).setVisibility(View.VISIBLE);
        }
        if (gameState.isRoundPlayed(4)) {
            scoreLayout.findViewById(gameState.isRoundWon(4) ? R.id.cowOk : R.id.cowFail).setVisibility(View.VISIBLE);
        }
        scoreLayout.removeView(scoreLayout.findViewById(R.id.correct));
        scoreLayout.removeView(scoreLayout.findViewById(R.id.incorrect));
    }

    private boolean isMusicEnabled() {
        return getSharedPreferences("mainmenu", MODE_PRIVATE).getBoolean("musicenabled", true);
    }

    private void setMusicEnabled(boolean isMusicEnabled) {
        SharedPreferences.Editor mainmenuEditor = getSharedPreferences("mainmenu", MODE_PRIVATE).edit();
        mainmenuEditor.putBoolean("musicenabled", isMusicEnabled);
        mainmenuEditor.apply();
    }

    private void doAnimateView(int viewId, int offset) {
        Animation mainMenu = AnimationUtils.loadAnimation(this, R.anim.mainmenu_anim);
        mainMenu.setStartOffset(offset);
        findViewById(viewId).startAnimation(mainMenu);
    }
}
