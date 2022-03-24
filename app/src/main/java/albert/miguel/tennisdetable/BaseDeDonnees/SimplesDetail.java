package albert.miguel.tennisdetable.BaseDeDonnees;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import albert.miguel.tennisdetable.R;

public class SimplesDetail extends AppCompatActivity implements android.view.View.OnClickListener{

    Button btnDelete;
    Button btnClose;
    LinearLayout set1, set2, set3, set4, set5, set6, set7;
    TextView tvNomJoueurUn,tvNomJoueurDeux,tvTotalSetsJ1,tvTotalSetsJ2,tvTitreNbSets;
    TextView tvDate,tvScoreSet1J1,tvScoreSet1J2,tvScoreSet2J1,tvScoreSet2J2,tvScoreSet3J1,tvScoreSet3J2,tvScoreSet4J1,tvScoreSet4J2,tvScoreSet5J1,tvScoreSet5J2,tvScoreSet6J1,tvScoreSet6J2,tvScoreSet7J1,tvScoreSet7J2;
    private int _Simple_Id=0;
    String nombreDeSets;
    String sharebody;
    String date;
    ImageButton imageButtonShare;
    String corpMessageShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_detail);

        tvDate =(TextView) findViewById(R.id.tvDate);
        tvNomJoueurUn = (TextView) findViewById(R.id.tvNomJoueurUn);
        tvNomJoueurDeux = (TextView) findViewById(R.id.tvNomJoueurDeux);

        tvTotalSetsJ1 = (TextView) findViewById(R.id.tvTotalSetsJ1);
        tvTotalSetsJ2 = (TextView) findViewById(R.id.tvTotalSetsJ2);
        tvTitreNbSets = (TextView) findViewById(R.id.tvTitreNbSets);

        tvScoreSet1J1 =(TextView) findViewById(R.id.tvScoreSet1J1);
        tvScoreSet1J2 =(TextView) findViewById(R.id.tvScoreSet1J2);
        tvScoreSet2J1 =(TextView) findViewById(R.id.tvScoreSet2J1);
        tvScoreSet2J2 =(TextView) findViewById(R.id.tvScoreSet2J2);
        tvScoreSet3J1 =(TextView) findViewById(R.id.tvScoreSet3J1);
        tvScoreSet3J2 =(TextView) findViewById(R.id.tvScoreSet3J2);
        tvScoreSet4J1 =(TextView) findViewById(R.id.tvScoreSet4J1);
        tvScoreSet4J2 =(TextView) findViewById(R.id.tvScoreSet4J2);
        tvScoreSet5J1 =(TextView) findViewById(R.id.tvScoreSet5J1);
        tvScoreSet5J2 =(TextView) findViewById(R.id.tvScoreSet5J2);
        tvScoreSet6J1 =(TextView) findViewById(R.id.tvScoreSet6J1);
        tvScoreSet6J2 =(TextView) findViewById(R.id.tvScoreSet6J2);
        tvScoreSet7J1 =(TextView) findViewById(R.id.tvScoreSet7J1);
        tvScoreSet7J2 =(TextView) findViewById(R.id.tvScoreSet7J2);

        set1 = (LinearLayout) findViewById(R.id.set1);
        set2 = (LinearLayout) findViewById(R.id.set2);
        set3 = (LinearLayout) findViewById(R.id.set3);
        set4 = (LinearLayout) findViewById(R.id.set4);
        set5 = (LinearLayout) findViewById(R.id.set5);
        set6 = (LinearLayout) findViewById(R.id.set6);
        set7 = (LinearLayout) findViewById(R.id.set7);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);
        imageButtonShare = (ImageButton) findViewById(R.id.imageButtonShare);

        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        imageButtonShare.setOnClickListener(this);

        Intent intent = getIntent();
        _Simple_Id =intent.getIntExtra("match_Id", 0);
        SimplesRepo repo = new SimplesRepo(this);
        Simples matchSimple = new Simples();
        matchSimple = repo.getSimpleById(_Simple_Id);


        tvNomJoueurUn.setText(String.valueOf(matchSimple.namej1));
        tvNomJoueurDeux.setText(String.valueOf(matchSimple.namej2));
        tvDate.setText("Date du match : " + matchSimple.date);
        date = matchSimple.date;
        tvTotalSetsJ1.setText(String.valueOf(matchSimple.scoreSetj1));
        tvTotalSetsJ2.setText(String.valueOf(matchSimple.scoreSetj2));
        tvScoreSet1J1.setText(String.valueOf(matchSimple.score_set1_j1));
        tvScoreSet1J2.setText(String.valueOf(matchSimple.score_set1_j2));
        tvScoreSet2J1.setText(String.valueOf(matchSimple.score_set2_j1));
        tvScoreSet2J2.setText(String.valueOf(matchSimple.score_set2_j2));
        tvScoreSet3J1.setText(String.valueOf(matchSimple.score_set3_j1));
        tvScoreSet3J2.setText(String.valueOf(matchSimple.score_set3_j2));
        tvScoreSet4J1.setText(String.valueOf(matchSimple.score_set4_j1));
        tvScoreSet4J2.setText(String.valueOf(matchSimple.score_set4_j2));
        tvScoreSet5J1.setText(String.valueOf(matchSimple.score_set5_j1));
        tvScoreSet5J2.setText(String.valueOf(matchSimple.score_set5_j2));
        tvScoreSet6J1.setText(String.valueOf(matchSimple.score_set6_j1));
        tvScoreSet6J2.setText(String.valueOf(matchSimple.score_set6_j2));
        tvScoreSet7J1.setText(String.valueOf(matchSimple.score_set7_j1));
        tvScoreSet7J2.setText(String.valueOf(matchSimple.score_set7_j2));
        nombreDeSets = String.valueOf(matchSimple.nbsets);
        if (nombreDeSets== "1") {
            tvTitreNbSets.setText("Match en simple - 1 set");
        } else {
            tvTitreNbSets.setText("Match en simple - " + matchSimple.nbsets + " sets");
        }
        afficheTableau();
    }

    private void afficheTableau() {
        switch (nombreDeSets) {
            case "1":
                set1.setVisibility(View.VISIBLE);
                set2.setVisibility(View.GONE);
                set3.setVisibility(View.GONE);
                set4.setVisibility(View.GONE);
                set5.setVisibility(View.GONE);
                set6.setVisibility(View.GONE);
                set7.setVisibility(View.GONE);
                break;
            case "3":
                set1.setVisibility(View.VISIBLE);
                set2.setVisibility(View.VISIBLE);
                set3.setVisibility(View.VISIBLE);
                set4.setVisibility(View.GONE);
                set5.setVisibility(View.GONE);
                set6.setVisibility(View.GONE);
                set7.setVisibility(View.GONE);
                break;
            case "5":
                set1.setVisibility(View.VISIBLE);
                set2.setVisibility(View.VISIBLE);
                set3.setVisibility(View.VISIBLE);
                set4.setVisibility(View.VISIBLE);
                set5.setVisibility(View.VISIBLE);
                set6.setVisibility(View.GONE);
                set7.setVisibility(View.GONE);
                break;
            case "7":
                set1.setVisibility(View.VISIBLE);
                set2.setVisibility(View.VISIBLE);
                set3.setVisibility(View.VISIBLE);
                set4.setVisibility(View.VISIBLE);
                set5.setVisibility(View.VISIBLE);
                set6.setVisibility(View.VISIBLE);
                set7.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void onClick(View view) {
        if (view== findViewById(R.id.btnDelete)){
            SimplesRepo repo = new SimplesRepo(this);
            repo.delete(_Simple_Id);
            Toast.makeText(this, "Le match a été supprimé", Toast.LENGTH_SHORT);
            finish();
        }else if (view== findViewById(R.id.btnClose)){
            finish();
        } else if (view== findViewById(R.id.imageButtonShare)){
            ConstruireCorpMessage();
            Intent intentShare = new Intent(Intent.ACTION_SEND);
            intentShare.setType("text/plain");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                sharebody = String.valueOf((Html.fromHtml(corpMessageShare,Html.FROM_HTML_MODE_LEGACY)));
            } else {
                sharebody = String.valueOf(Html.fromHtml(corpMessageShare));
            }
            String sharesub = date + " " + tvNomJoueurUn.getText().toString()+" vs "+ tvNomJoueurDeux.getText().toString();
            intentShare.putExtra(Intent.EXTRA_SUBJECT,sharesub);
            intentShare.putExtra(Intent.EXTRA_TEXT,sharebody);
            startActivity(Intent.createChooser(intentShare,"Partager avec :"));
        }
    }

    private void ConstruireCorpMessage() {
        switch (nombreDeSets) {
            case "1":
                corpMessageShare =
                        tvDate.getText().toString()
                                + "<br>"
                                + tvNomJoueurUn.getText().toString() + " vs " + tvNomJoueurDeux.getText().toString()
                                + "<br>"
                                + " Score : "
                                + tvTotalSetsJ1.getText().toString() + " - " + tvTotalSetsJ2.getText().toString()
                                + "<br>"
                                + "Set 1 : "
                                + tvScoreSet1J1.getText().toString() + " - " + tvScoreSet1J2.getText().toString();
                break;
            case "3":
                corpMessageShare =
                        tvDate.getText().toString()
                                + "<br>"
                                + tvNomJoueurUn.getText().toString() + " vs " + tvNomJoueurDeux.getText().toString()
                                + "<br>"
                                + " Score : "
                                + tvTotalSetsJ1.getText().toString() + " - " + tvTotalSetsJ2.getText().toString()
                                + "<br>"
                                + "Set 1 : "
                                + tvScoreSet1J1.getText().toString() + " - " + tvScoreSet1J2.getText().toString()
                                + "<br>"
                                + "Set 2 : "
                                + tvScoreSet2J1.getText().toString() + " - " + tvScoreSet2J2.getText().toString()
                                + "<br>"
                                + "Set 3 : "
                                + tvScoreSet3J1.getText().toString() + " - " + tvScoreSet3J2.getText().toString();
                break;
            case "5":
                corpMessageShare =
                        tvDate.getText().toString()
                                + "<br>"
                                + tvNomJoueurUn.getText().toString() + " vs " + tvNomJoueurDeux.getText().toString()
                                + "<br>"
                                + " Score : "
                                + tvTotalSetsJ1.getText().toString() + " - " + tvTotalSetsJ2.getText().toString()
                                + "<br>"
                                + "Set 1 : "
                                + tvScoreSet1J1.getText().toString() + " - " + tvScoreSet1J2.getText().toString()
                                + "<br>"
                                + "Set 2 : "
                                + tvScoreSet2J1.getText().toString() + " - " + tvScoreSet2J2.getText().toString()
                                + "<br>"
                                + "Set 3 : "
                                + tvScoreSet3J1.getText().toString() + " - " + tvScoreSet3J2.getText().toString()
                                + "<br>"
                                + "Set 4 : "
                                + tvScoreSet4J1.getText().toString() + " - " + tvScoreSet4J2.getText().toString()
                                + "<br>"
                                + "Set 5 : "
                                + tvScoreSet5J1.getText().toString() + " - " + tvScoreSet5J2.getText().toString();
                break;
            case "7":
                corpMessageShare =
                        tvDate.getText().toString()
                                + "<br>"
                                + tvNomJoueurUn.getText().toString() + " vs " + tvNomJoueurDeux.getText().toString()
                                + "<br>"
                                + " Score : "
                                + tvTotalSetsJ1.getText().toString() + " - " + tvTotalSetsJ2.getText().toString()
                                + "<br>"
                                + "Set 1 : "
                                + tvScoreSet1J1.getText().toString() + " - " + tvScoreSet1J2.getText().toString()
                                + "<br>"
                                + "Set 2 : "
                                + tvScoreSet2J1.getText().toString() + " - " + tvScoreSet2J2.getText().toString()
                                + "<br>"
                                + "Set 3 : "
                                + tvScoreSet3J1.getText().toString() + " - " + tvScoreSet3J2.getText().toString()
                                + "<br>"
                                + "Set 4 : "
                                + tvScoreSet4J1.getText().toString() + " - " + tvScoreSet4J2.getText().toString()
                                + "<br>"
                                + "Set 5 : "
                                + tvScoreSet5J1.getText().toString() + " - " + tvScoreSet5J2.getText().toString()
                                + "<br>"
                                + "Set 6 : "
                                + tvScoreSet6J1.getText().toString() + " - " + tvScoreSet6J2.getText().toString()
                                + "<br>"
                                + "Set 7 : "
                                + tvScoreSet7J1.getText().toString() + " - " + tvScoreSet7J2.getText().toString();
                break;
        }
    }
}
