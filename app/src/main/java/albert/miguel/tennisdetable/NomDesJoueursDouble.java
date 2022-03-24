package albert.miguel.tennisdetable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class NomDesJoueursDouble extends AppCompatActivity {
    TextView textViewprompt, tvTitre, tvJoueur1a, tvJoueur1b, tvJoueur2a, tvJoueur2b, textViewJoueur1, textViewJoueur2;
    String nbSets;
    RadioButton radioButton, radioButton2;
    ImageButton btnEcrireJoueur1a, btnEcrireJoueur1b,btnEcrireJoueur2a,btnEcrireJoueur2b, imagebuttonInverserMatch, imagebuttonDemarrer, imageButtonQrCode1a, imageButtonQrCode1b,imageButtonQrCode2a,imageButtonQrCode2b;
    int joueur, cotechangejoueur;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nom_des_joueurs_double);

        tvTitre = (TextView) findViewById(R.id.tvTitre);
        tvJoueur1a = (TextView) findViewById(R.id.tvJoueur1a);
        tvJoueur1b = (TextView) findViewById(R.id.tvJoueur1b);
        tvJoueur2a= (TextView) findViewById(R.id.tvJoueur2a);
        tvJoueur2b= (TextView) findViewById(R.id.tvJoueur2b);
        textViewJoueur1 = (TextView) findViewById(R.id.textViewJoueur1);
        textViewJoueur2 = (TextView) findViewById(R.id.textViewJoueur2);

        btnEcrireJoueur1a = (ImageButton) findViewById(R.id.btnEcrireJoueur1a);
        btnEcrireJoueur1a.setOnClickListener(new ouvrePromptJ1a());

        btnEcrireJoueur1b = (ImageButton) findViewById(R.id.btnEcrireJoueur1b);
        btnEcrireJoueur1b.setOnClickListener(new ouvrePromptJ1b());

        btnEcrireJoueur2a = (ImageButton) findViewById(R.id.btnEcrireJoueur2a);
        btnEcrireJoueur2a.setOnClickListener(new ouvrePromptJ2a());

        btnEcrireJoueur2b = (ImageButton) findViewById(R.id.btnEcrireJoueur2b);
        btnEcrireJoueur2b.setOnClickListener(new ouvrePromptJ2b());

        imagebuttonInverserMatch = (ImageButton) findViewById(R.id.imagebuttonInverserMatch);
        imagebuttonInverserMatch.setOnClickListener(new echangeNomsJoueurs());
        imagebuttonDemarrer = (ImageButton) findViewById(R.id.imagebuttonDemarrer);
        imagebuttonDemarrer.setOnClickListener(new ouvre_page_match_doubles());
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);

        imageButtonQrCode1a = (ImageButton) findViewById(R.id.imageButtonQrCode1a);
        imageButtonQrCode1a.setOnClickListener(new ouvreScanJ1a());
        imageButtonQrCode1b = (ImageButton) findViewById(R.id.imageButtonQrCode1b);
        imageButtonQrCode1b.setOnClickListener(new ouvreScanJ1b());

        imageButtonQrCode2a = (ImageButton) findViewById(R.id.imageButtonQrCode2a);
        imageButtonQrCode2a.setOnClickListener(new ouvreScanJ2a());
        imageButtonQrCode2b = (ImageButton) findViewById(R.id.imageButtonQrCode2b);
        imageButtonQrCode2b.setOnClickListener(new ouvreScanJ2b());

        nbSets = getIntent().getExtras().getString("nbSet");
        if (nbSets.equals("1")) {
            tvTitre.setText("Match en double - 1 set");
        } else {
            tvTitre.setText("Match en double - " + nbSets + " sets");
        }
        joueur = 1;
        cotechangejoueur = 1;

        //permission android 6
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CAMERA)) {
                //Cela signifie que la permission à déjà était
                //demandé et l'utilisateur l'a refusé
                //Vous pouvez aussi expliquer à l'utilisateur pourquoi
                //cette permission est nécessaire et la redemander
            } else {
                //Sinon demander la permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // La permission est garantie
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Vous ne pourrez pas utiliser le lecteur de QR Code", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle statutOut) {
        statutOut.putString("StoreNomEquipe1", textViewJoueur1.getText().toString());
        statutOut.putString("StoreNomEquipe2", textViewJoueur2.getText().toString());
        statutOut.putString("StoreTvNomJoueur1a", tvJoueur1a.getText().toString());
        statutOut.putString("StoreTvNomJoueur1b", tvJoueur1b.getText().toString());
        statutOut.putString("StoreTvNomJoueur2a", tvJoueur2a.getText().toString());
        statutOut.putString("StoreTvNomJoueur2b", tvJoueur2b.getText().toString());
        statutOut.putInt("StoreJoueur", joueur);
        statutOut.putInt("Storecotechangejoueur", cotechangejoueur);
        super.onSaveInstanceState(statutOut);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        textViewJoueur1.setText(savedInstanceState.getString("StoreNomEquipe1"));
        textViewJoueur2.setText(savedInstanceState.getString("StoreNomEquipe2"));
        tvJoueur1a.setText(savedInstanceState.getString("StoreTvNomJoueur1a"));
        tvJoueur1b.setText(savedInstanceState.getString("StoreTvNomJoueur1b"));
        tvJoueur2a.setText(savedInstanceState.getString("StoreTvNomJoueur2a"));
        tvJoueur2b.setText(savedInstanceState.getString("StoreTvNomJoueur2b"));
        joueur = (savedInstanceState.getInt("StoreJoueur"));
        cotechangejoueur = (savedInstanceState.getInt("Storecotechangejoueur"));
    }
    private class ouvreScanJ1a implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            joueur = 1;
            new IntentIntegrator(NomDesJoueursDouble.this).initiateScan();
        }
    }

    private class ouvreScanJ1b implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            joueur = 2;
            new IntentIntegrator(NomDesJoueursDouble.this).initiateScan();
        }
    }
    private class ouvreScanJ2a implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            joueur = 3;
            new IntentIntegrator(NomDesJoueursDouble.this).initiateScan();
        }
    }
    private class ouvreScanJ2b implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            joueur = 4;
            new IntentIntegrator(NomDesJoueursDouble.this).initiateScan();
        }
    }

    private class ouvrePromptJ1a implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            joueur = 1;
            LayoutInflater li = LayoutInflater.from(NomDesJoueursDouble.this);
            View promptView = li.inflate(R.layout.prompt, null);
            textViewprompt = (TextView) promptView.findViewById(R.id.textView1);
            textViewprompt.setText("Saisir le nom du joueur A de l'équipe 1 (maxi 15 caractères)");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NomDesJoueursDouble.this);

            // set prompt.xml to alertdialog builder
            alertDialogBuilder.setView(promptView);
            final EditText userInput = (EditText) promptView
                    .findViewById(R.id.editTextDialogUserInput);
            userInput.setHint(tvJoueur1a.getText().toString());
            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                    String nom = userInput.getText().toString();
                                    saisirDansLesTextViewsHaut(nom);
                                    saisirDansLesTextViewsBas(nom);
                                }
                            })
                    .setNegativeButton("Annuler",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    }
    private class ouvrePromptJ1b implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            joueur = 2;
            LayoutInflater li = LayoutInflater.from(NomDesJoueursDouble.this);
            View promptView = li.inflate(R.layout.prompt, null);
            textViewprompt = (TextView) promptView.findViewById(R.id.textView1);
            textViewprompt.setText("Saisir le nom du joueur B de l'équipe 1 (maxi 15 caractères)");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NomDesJoueursDouble.this);
            // set prompt.xml to alertdialog builder
            alertDialogBuilder.setView(promptView);
            final EditText userInput = (EditText) promptView
                    .findViewById(R.id.editTextDialogUserInput);
            userInput.setHint(tvJoueur1b.getText().toString());
            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                    String nom = userInput.getText().toString();
                                    saisirDansLesTextViewsHaut(nom);
                                    saisirDansLesTextViewsBas(nom);
                                }
                            })
                    .setNegativeButton("Annuler",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    }
    private class ouvrePromptJ2a implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            joueur = 3;
            LayoutInflater li = LayoutInflater.from(NomDesJoueursDouble.this);
            View promptView = li.inflate(R.layout.prompt, null);
            textViewprompt = (TextView) promptView.findViewById(R.id.textView1);
            textViewprompt.setText("Saisir le nom du joueur A de l'équipe 2 (maxi 15 caractères)");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NomDesJoueursDouble.this);

            // set prompt.xml to alertdialog builder
            alertDialogBuilder.setView(promptView);
            final EditText userInput = (EditText) promptView
                    .findViewById(R.id.editTextDialogUserInput);
            userInput.setHint(tvJoueur2a.getText().toString());
            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                    String nom = userInput.getText().toString();
                                    saisirDansLesTextViewsHaut(nom);
                                    saisirDansLesTextViewsBas(nom);
                                }
                            })
                    .setNegativeButton("Annuler",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    }
    private class ouvrePromptJ2b implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            joueur = 4;
            LayoutInflater li = LayoutInflater.from(NomDesJoueursDouble.this);
            View promptView = li.inflate(R.layout.prompt, null);
            textViewprompt = (TextView) promptView.findViewById(R.id.textView1);
            textViewprompt.setText("Saisir le nom du joueur B de l'équipe 2 (maxi 15 caractères)");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NomDesJoueursDouble.this);
            // set prompt.xml to alertdialog builder
            alertDialogBuilder.setView(promptView);
            final EditText userInput = (EditText) promptView
                    .findViewById(R.id.editTextDialogUserInput);
            userInput.setHint(tvJoueur2b.getText().toString());
            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                    String nom = userInput.getText().toString();
                                    saisirDansLesTextViewsHaut(nom);
                                    saisirDansLesTextViewsBas(nom);
                                }
                            })
                    .setNegativeButton("Annuler",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    }
    private class echangeNomsJoueurs implements View.OnClickListener {
        public void onClick(View v) {
            String nomEnAttente = textViewJoueur1.getText().toString();
            textViewJoueur1.setText(textViewJoueur2.getText());
            textViewJoueur2.setText(nomEnAttente);
            if (cotechangejoueur == 1) {
                cotechangejoueur = 2;
            } else {
                cotechangejoueur = 1;
            }
        }
    }
    public void saisirDansLesTextViewsHaut(String valeur) {
        if (valeur.length() > 15) {
            valeur = valeur.substring(0, 15);
        }
        switch (joueur){
            case 1 :
                tvJoueur1a.setText(valeur);
                break;
            case 2 :
                tvJoueur1b.setText(valeur);
                break;
            case 3 :
                tvJoueur2a.setText(valeur);
                break;
            case 4 :
                tvJoueur2b.setText(valeur);
                break;
        }
    }
    public void saisirDansLesTextViewsBas(String valeur) {
        valeur = valeur.toUpperCase();
        if (valeur.length() > 15) {
            valeur = valeur.substring(0, 15);
        }
        if (cotechangejoueur == 1) {
            switch (joueur){
                case 1 :
                    textViewJoueur1.setText(valeur + "\n"+ (tvJoueur1b.getText().toString().toUpperCase()));
                    break;
                case 2 :
                    textViewJoueur1.setText((tvJoueur1a.getText().toString().toUpperCase()) + "\n" + valeur);
                    break;
                case 3 :
                    textViewJoueur2.setText(valeur + "\n"+ (tvJoueur2b.getText().toString().toUpperCase()));
                    break;
                case 4 :
                    textViewJoueur2.setText((tvJoueur2a.getText().toString().toUpperCase()) + "\n" + valeur);
                    break;
            }
        } else {
            switch (joueur){
                case 1 :
                    textViewJoueur2.setText(valeur + "\n"+ (tvJoueur1b.getText().toString().toUpperCase()));
                    break;
                case 2 :
                    textViewJoueur2.setText((tvJoueur1a.getText().toString().toUpperCase()) + "\n" + valeur);
                    break;
                case 3 :
                    textViewJoueur1.setText(valeur + "\n"+ (tvJoueur2b.getText().toString().toUpperCase()));
                    break;
                case 4 :
                    textViewJoueur1.setText((tvJoueur2a.getText().toString().toUpperCase()) + "\n" + valeur);
                    break;
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // nous utilisons la classe IntentIntegrator et sa fonction parseActivityResult pour parser le résultat du scan
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String scanContent = scanningResult.getContents();
        if (scanContent == null) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Aucune donnée reçu!", Toast.LENGTH_SHORT);
            toast.show();
        }
        if (scanContent != null) {
            // nous récupérons le contenu du code barre
            saisirDansLesTextViewsHaut(scanContent);
            saisirDansLesTextViewsBas(scanContent);
        }
    }

    private class ouvre_page_match_doubles implements View.OnClickListener {
        public void onClick(View v) {
            String nomCompletJ1A = tvJoueur1a.getText().toString();
            String nomCompletJ1B = tvJoueur1b.getText().toString();
            String nomCompletJ2A = tvJoueur2a.getText().toString();
            String nomCompletJ2B = tvJoueur2b.getText().toString();
            String nomCourtJoueurPositionGauche = textViewJoueur1.getText().toString();
            String nomCourtJoueurPositionDroite = textViewJoueur2.getText().toString();
            Boolean serviceGauche = radioButton.isChecked();
            if (nomCourtJoueurPositionGauche.matches("") || nomCourtJoueurPositionDroite.matches("")) {
                Toast.makeText(getApplicationContext(), "Veuillez saisir le nom des joueurs", Toast.LENGTH_LONG).show();
            } else if (nomCourtJoueurPositionDroite.equals(nomCourtJoueurPositionGauche)) {
                Toast.makeText(getApplicationContext(), "Les noms des joueurs sont identiques", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(NomDesJoueursDouble.this, MatchdoubleActivity.class);
                intent.putExtra("nbSets", nbSets);
                intent.putExtra("PositionJoueurs", cotechangejoueur);
                intent.putExtra("nomJoueurPositionGauche", nomCourtJoueurPositionGauche);
                intent.putExtra("nomJoueurPositionDroite", nomCourtJoueurPositionDroite);
                intent.putExtra("nomJoueurCompletJ1A", nomCompletJ1A);
                intent.putExtra("nomJoueurCompletJ1B", nomCompletJ1B);
                intent.putExtra("nomJoueurCompletJ2A", nomCompletJ2A);
                intent.putExtra("nomJoueurCompletJ2B", nomCompletJ2B);
                intent.putExtra("serviceGauche", serviceGauche);
                startActivity(intent);
            }
        }
    }
}
