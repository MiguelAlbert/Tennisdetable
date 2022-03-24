package albert.miguel.tennisdetable;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(4000)
                .withBackgroundColor(Color.parseColor("#ffcc80"))
                .withLogo(R.drawable.joueurs)
                .withHeaderText("Bienvenue")
                .withFooterText("Version 1.0")
                .withBeforeLogoText("Score Tennis de table")
                .withAfterLogoText("Miguel ALBERT");

        //Text Color
        config.getHeaderTextView().setTextColor(Color.BLACK);
        config.getHeaderTextView().setTextSize(40);
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextSize(15);
        config.getBeforeLogoTextView().setTextColor(Color.BLACK);
        config.getBeforeLogoTextView().setTextSize(30);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextSize(20);

        // mettre dans la vue
        View view = config.create();

        //Set view to contentView

        setContentView(view);
    }
}
