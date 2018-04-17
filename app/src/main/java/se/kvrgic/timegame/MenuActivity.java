package se.kvrgic.timegame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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
