package se.kvrgic.timegame;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Game1Activity extends Activity {

    public static final String TAG = "Game1Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        doSetupGame();
    }

    public void doAcceptAnswer(View view) {
        Log.d(TAG, "doAcceptAnswer: ");
    }



    private void doSetupGame() {
        long hour = Math.round(Math.random() * 12);
        float hourAngle = hour * 360f / 12;
        View hour_hand = findViewById(R.id.game1_hour);
        hour_hand.setRotation(hourAngle);
        Toast.makeText(this, "Hour: " + hour + ", angle: " + hourAngle, Toast.LENGTH_SHORT).show();
    }

}
