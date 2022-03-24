package albert.miguel.tennisdetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button buttonSimple1Set,buttonSimple3Set,buttonSimple5Set,buttonSimple7Set;
    Button buttonDouble1Set,buttonDouble3Set,buttonDouble5Set,buttonDouble7Set;
    Button btnAffichageSecondaire;
    Button buttonEnrSimples,buttonEnrDoubles,btnClassJoueurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonSimple1Set = (Button) findViewById(R.id.buttonSimple1Set);
        buttonSimple3Set = (Button) findViewById(R.id.buttonSimple3Set);
        buttonSimple5Set = (Button) findViewById(R.id.buttonSimple5Set);
        buttonSimple7Set = (Button) findViewById(R.id.buttonSimple7Set);
        buttonDouble1Set = (Button) findViewById(R.id.buttonDouble1Set);
        buttonDouble3Set = (Button) findViewById(R.id.buttonDouble3Set);
        buttonDouble5Set = (Button) findViewById(R.id.buttonDouble5Set);
        buttonDouble7Set = (Button) findViewById(R.id.buttonDouble7Set);

        buttonEnrSimples = (Button) findViewById(R.id.buttonEnrSimples);
        buttonEnrSimples.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnrSimples.class);
                startActivity(intent);
            }
        });

        buttonEnrDoubles = (Button) findViewById(R.id.buttonEnrDoubles);
        buttonEnrDoubles.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnrDoubles.class);
                startActivity(intent);
            }
        });

        btnAffichageSecondaire = (Button)findViewById(R.id.btnAffichageSecondaire);
        btnAffichageSecondaire.setOnClickListener(new ouvre_page_affichage_secondaire());

        btnClassJoueurs = (Button) findViewById(R.id.btnClassJoueurs);
        btnClassJoueurs.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Classementdesjoueurs.class);
                startActivity(intent);
            }
        });
    }


    public void buttonPress(View v) {
        switch (v.getId()) {
            case R.id.buttonSimple1Set:
                Intent intent = new Intent(MainActivity.this, NomDesJoueursSimple.class);
                intent.putExtra("nbSet","1");
                startActivity(intent);
                break;
            case R.id.buttonSimple3Set:
                Intent intent1 = new Intent(MainActivity.this, NomDesJoueursSimple.class);
                intent1.putExtra("nbSet","3");
                startActivity(intent1);
                break;
            case R.id.buttonSimple5Set:
                Intent intent2 = new Intent(MainActivity.this, NomDesJoueursSimple.class);
                intent2.putExtra("nbSet","5");
                startActivity(intent2);
                break;
            case R.id.buttonSimple7Set:
                Intent intent3 = new Intent(MainActivity.this, NomDesJoueursSimple.class);
                intent3.putExtra("nbSet","7");
                startActivity(intent3);
                break;
            case R.id.buttonDouble1Set:
                Intent intent4 = new Intent(MainActivity.this, NomDesJoueursDouble.class);
                intent4.putExtra("nbSet","1");
                startActivity(intent4);
                break;
            case R.id.buttonDouble3Set:
                Intent intent5 = new Intent(MainActivity.this, NomDesJoueursDouble.class);
                intent5.putExtra("nbSet","3");
                startActivity(intent5);
                break;
            case R.id.buttonDouble5Set:
                Intent intent6 = new Intent(MainActivity.this, NomDesJoueursDouble.class);
                intent6.putExtra("nbSet","5");
                startActivity(intent6);
                break;
            case R.id.buttonDouble7Set:
                Intent intent7 = new Intent(MainActivity.this, NomDesJoueursDouble.class);
                intent7.putExtra("nbSet","7");
                startActivity(intent7);
                break;
        }
    }

    class ouvre_page_affichage_secondaire implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AffichageSecondaire.class);
            startActivity(intent);
        }
    }
}

