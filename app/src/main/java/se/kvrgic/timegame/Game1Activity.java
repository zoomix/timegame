package se.kvrgic.timegame;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Game1Activity extends Activity {

    public static final String TAG = "Game1Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
    }

    public void doAcceptAnswer(View view) {
        Log.d(TAG, "doAcceptAnswer: ");
    }
}
