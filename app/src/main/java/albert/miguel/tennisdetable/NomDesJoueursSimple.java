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

public class NomDesJoueursSimple extends AppCompatActivity {

    TextView textViewprompt, tvTitre, textViewJoueur1, textViewJoueur2, tvJoueur1, tvJoueur2;
    String nbSets;
    RadioButton radioButton, radioButton2;
    ImageButton btnEcrireJoueur1, btnEcrireJoueur2, imagebuttonInverserMatch, imagebuttonDemarrer, imageButtonQrCode1, imageButtonQrCode2;
    int joueur, cotechangejoueur;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nom_des_joueurs_simple);

        tvTitre = (TextView) findViewById(R.id.tvTitre);
        textViewJoueur1 = (TextView) findViewById(R.id.textViewJoueur1);
        textViewJoueur2 = (TextView) findViewById(R.id.textViewJoueur2);
        tvJoueur1 = (TextView) findViewById(R.id.tvJoueur1);
        tvJoueur2 = (TextView) findViewById(R.id.tvJoueur2);
        btnEcrireJoueur1 = (ImageButton) findViewById(R.id.btnEcrireJoueur1);
        btnEcrireJoueur1.setOnClickListener(new ouvrePromptJ1());
        btnEcrireJoueur2 = (ImageButton) findViewById(R.id.btnEcrireJoueur2);
        btnEcrireJoueur2.setOnClickListener(new ouvrePromptJ2());
        imagebuttonInverserMatch = (ImageButton) findViewById(R.id.imagebuttonInverserMatch);
        imagebuttonInverserMatch.setOnClickListener(new echangeNomsJoueurs());
        imagebuttonDemarrer = (ImageButton) findViewById(R.id.imagebuttonDemarrer);
        imagebuttonDemarrer.setOnClickListener(new ouvre_page_match_simples());
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        imageButtonQrCode1 = (ImageButton) findViewById(R.id.imageButtonQrCode1);
        imageButtonQrCode1.setOnClickListener(new ouvreScanJ1());
        imageButtonQrCode2 = (ImageButton) findViewById(R.id.imageButtonQrCode2);
        imageButtonQrCode2.setOnClickListener(new ouvreScanJ2());

        nbSets = getIntent().getExtras().getString("nbSet");
        if (nbSets.equals("1")) {
            tvTitre.setText("Match en simple - 1 set");
        } else {
            tvTitre.setText("Match en simple - " + nbSets + " sets");
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
        statutOut.putString("StoreNomJoueur1", textViewJoueur1.getText().toString());
        statutOut.putString("StoreNomJoueur2", textViewJoueur2.getText().toString());
        statutOut.putString("StoreTvNomJoueur1", tvJoueur1.getText().toString());
        statutOut.putString("StoreTvNomJoueur2", tvJoueur2.getText().toString());
        statutOut.putInt("StoreJoueur", joueur);
        statutOut.putInt("Storecotechangejoueur", cotechangejoueur);
        super.onSaveInstanceState(statutOut);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        textViewJoueur1.setText(savedInstanceState.getString("StoreNomJoueur1"));
        textViewJoueur2.setText(savedInstanceState.getString("StoreNomJoueur2"));
        tvJoueur1.setText(savedInstanceState.getString("StoreTvNomJoueur1"));
        tvJoueur2.setText(savedInstanceState.getString("StoreTvNomJoueur2"));
        joueur = (savedInstanceState.getInt("StoreJoueur"));
        cotechangejoueur = (savedInstanceState.getInt("Storecotechangejoueur"));
    }

    private class ouvrePromptJ1 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            joueur = 1;
            LayoutInflater li = LayoutInflater.from(NomDesJoueursSimple.this);
            View promptView = li.inflate(R.layout.prompt, null);
            textViewprompt = (TextView) promptView.findViewById(R.id.textView1);
            textViewprompt.setText("Saisir le nom du joueur 1 (maxi 15 caractères)");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    NomDesJoueursSimple.this);
            // set prompt.xml to alertdialog builder
            alertDialogBuilder.setView(promptView);
            final EditText userInput = (EditText) promptView
                    .findViewById(R.id.editTextDialogUserInput);
            userInput.setHint(tvJoueur1.getText().toString());
            // set dialog message2
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
    private class ouvrePromptJ2 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            joueur = 2;
            LayoutInflater li = LayoutInflater.from(NomDesJoueursSimple.this);
            View promptView = li.inflate(R.layout.prompt, null);
            textViewprompt = (TextView) promptView.findViewById(R.id.textView1);
            textViewprompt.setText("Saisir le nom du joueur 2 (maxi 15 caractères)");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NomDesJoueursSimple.this);

            // set prompt.xml to alertdialog builder
            alertDialogBuilder.setView(promptView);
            final EditText userInput = (EditText) promptView
                    .findViewById(R.id.editTextDialogUserInput);
            userInput.setHint(tvJoueur2.getText().toString());
            // set dialog message2
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





    private class ouvreScanJ1 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            joueur = 1;
            new IntentIntegrator(NomDesJoueursSimple.this).initiateScan();
        }
    }

    private class ouvreScanJ2 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            joueur = 2;
            new IntentIntegrator(NomDesJoueursSimple.this).initiateScan();
        }
    }

    public void saisirDansLesTextViewsHaut(String valeur) {
        if (valeur.length() > 15) {
            valeur = valeur.substring(0, 15);
        }
        if (joueur == 1) {
            tvJoueur1.setText(valeur);
        } else {
            tvJoueur2.setText(valeur);
        }
    }

    public void saisirDansLesTextViewsBas(String valeur) {
        valeur = valeur.toUpperCase();
        if (valeur.length() > 15) {
            valeur = valeur.substring(0, 15);
        }
        if (cotechangejoueur == 1 && joueur == 1) {
            textViewJoueur1.setText(valeur);

        } else {
            if (cotechangejoueur == 2 && joueur == 1) {
                textViewJoueur2.setText(valeur);

            } else {
                if (cotechangejoueur == 1 && joueur == 2) {
                    textViewJoueur2.setText(valeur);

                } else {
                    if (cotechangejoueur == 2 && joueur == 2) {
                        textViewJoueur1.setText(valeur);

                    }
                }
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

    private class ouvre_page_match_simples implements View.OnClickListener {
        public void onClick(View v) {
            String nomCompletJ1 = tvJoueur1.getText().toString();
            String nomCompletJ2 = tvJoueur2.getText().toString();
            String nomCourtJoueurPositionGauche = textViewJoueur1.getText().toString();
            String nomCourtJoueurPositionDroite = textViewJoueur2.getText().toString();
            Boolean serviceGauche = radioButton.isChecked();
            if (nomCourtJoueurPositionGauche.matches("") || nomCourtJoueurPositionDroite.matches("")) {
                Toast.makeText(getApplicationContext(), "Veuillez saisir le nom des joueurs", Toast.LENGTH_LONG).show();
            } else if (nomCourtJoueurPositionDroite.equals(nomCourtJoueurPositionGauche)) {
                Toast.makeText(getApplicationContext(), "Les noms des joueurs sont identiques", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(NomDesJoueursSimple.this, MatchsimpleActivity.class);
                intent.putExtra("nbSets", nbSets);
                intent.putExtra("PositionJoueurs", cotechangejoueur);
                intent.putExtra("nomJoueurPositionGauche", nomCourtJoueurPositionGauche);
                intent.putExtra("nomJoueurPositionDroite", nomCourtJoueurPositionDroite);
                intent.putExtra("nomJoueurCompletJ1", nomCompletJ1);
                intent.putExtra("nomJoueurCompletJ2", nomCompletJ2);
                intent.putExtra("serviceGauche", serviceGauche);
                startActivity(intent);
            }
        }
    }
}
