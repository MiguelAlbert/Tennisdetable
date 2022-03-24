package albert.miguel.tennisdetable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import albert.miguel.tennisdetable.BaseDeDonnees.Doubles;
import albert.miguel.tennisdetable.BaseDeDonnees.DoublesRepo;


public class MatchdoubleActivity extends AppCompatActivity {

    int pointSetGauche = 0;
    int pointSetDroite = 0;
    int nbSetsGauche = 0;
    int nbSetsDroite = 0;
    int intServiceGauche;
    int intServiceDroite;
    int intNbSetsAJouer;
    int positionJoueur;
    int chiffreDuSetEnCours = 1;
    int changementJeuxDecisif = 0;
    String nbSetsAJouer;
    String messageAenvoyer;
    Boolean serviceCoteGauche;
    LinearLayout set1,set2,set3,set4,set5,set6,set7;
    LinearLayout layoutEnr,layoutPlusMoins,layoutService;
    TextView tvTotalSetsJ1,tvTotalSetsJ2,tvScoreSetGauche,tvScoreSetDroite,tvTitreNbSets,tvNomJoueurUn,tvNomJoueurDeux,tvPositionJoueur1,tvNomCourtJoueurGauche,tvNomCourtJoueurDroite,tvScoreNbSetsGauche,tvScoreNbSetsDroite;
    TextView tvScoreSet1J1,tvScoreSet1J2,tvScoreSet2J1,tvScoreSet2J2,tvScoreSet3J1,tvScoreSet3J2,tvScoreSet4J1,tvScoreSet4J2,tvScoreSet5J1,tvScoreSet5J2,tvScoreSet6J1,tvScoreSet6J2,tvScoreSet7J1,tvScoreSet7J2;
    Button btnPlusDroite,btnPlusGauche,btnMoinsDroite,btnMoinsGauche;
    ImageView imageViewBalDroite1,imageViewBalDroite2,imageViewBalGauche1,imageViewBalGauche2;
    ImageButton imageButtonSave;
    private MediaPlayer mPlayer = null;
    private int _Double_Id = 0;

    //le BLUETOOTH
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private AdView mAdView;

    private String connectedDeviceName = null;
    private ArrayAdapter<String> chatArrayAdapter;

    private StringBuffer outStringBuffer;
    private BluetoothAdapter bluetoothAdapter = null;
    private BTService btService = null;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BTService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to,
                                    connectedDeviceName));
                            chatArrayAdapter.clear();
                            prepareMessage();
                            sendMessage(messageAenvoyer);
                            break;
                        case BTService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BTService.STATE_LISTEN:
                        case BTService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;

                    String writeMessage = new String(writeBuf);

                    chatArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;

                    String readMessage = new String(readBuf, 0, msg.arg1);
                    chatArrayAdapter.add(connectedDeviceName + ":  " + readMessage);
                    break;
                case MESSAGE_DEVICE_NAME:

                    connectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(),
                            "Connected to  " + connectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(),
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchdouble);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// Garde l'écran toujours Allumé

        //Pub
        //MobileAds.initialize(this, "ca-app-pub-6506972643290681~6421531562");
        //mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);
        //Pub
        layoutService = (LinearLayout)findViewById(R.id.layoutService);
        layoutPlusMoins = (LinearLayout) findViewById(R.id.layoutPlusMoins);
        layoutEnr = (LinearLayout) findViewById(R.id.layoutEnr);
        layoutEnr.setVisibility(View.GONE);
        nbSetsAJouer = getIntent().getExtras().getString("nbSets");
        intNbSetsAJouer = Integer.parseInt(nbSetsAJouer);// transforme String en Int
        positionJoueur = getIntent().getExtras().getInt("PositionJoueurs");
        tvScoreSetGauche = (TextView) findViewById(R.id.tvScoreSetGauche);
        tvScoreSetDroite = (TextView) findViewById(R.id.tvScoreSetDroite);
        tvTitreNbSets = (TextView) findViewById(R.id.tvTitreNbSets);
        if (nbSetsAJouer == "1") {
            tvTitreNbSets.setText("Match en double - 1 set");
        } else {
            tvTitreNbSets.setText("Match en double - " + nbSetsAJouer + " sets");
        }
        set1 = (LinearLayout) findViewById(R.id.set1);
        set2 = (LinearLayout) findViewById(R.id.set2);
        set3 = (LinearLayout) findViewById(R.id.set3);
        set4 = (LinearLayout) findViewById(R.id.set4);
        set5 = (LinearLayout) findViewById(R.id.set5);
        set6 = (LinearLayout) findViewById(R.id.set6);
        set7 = (LinearLayout) findViewById(R.id.set7);
        tvScoreSet1J1 = (TextView) findViewById(R.id.tvScoreSet1J1);
        tvScoreSet1J2 = (TextView) findViewById(R.id.tvScoreSet1J2);
        tvScoreSet2J1 = (TextView) findViewById(R.id.tvScoreSet2J1);
        tvScoreSet2J2 = (TextView) findViewById(R.id.tvScoreSet2J2);
        tvScoreSet3J1 = (TextView) findViewById(R.id.tvScoreSet3J1);
        tvScoreSet3J2 = (TextView) findViewById(R.id.tvScoreSet3J2);
        tvScoreSet4J1 = (TextView) findViewById(R.id.tvScoreSet4J1);
        tvScoreSet4J2 = (TextView) findViewById(R.id.tvScoreSet4J2);
        tvScoreSet5J1 = (TextView) findViewById(R.id.tvScoreSet5J1);
        tvScoreSet5J2 = (TextView) findViewById(R.id.tvScoreSet5J2);
        tvScoreSet6J1 = (TextView) findViewById(R.id.tvScoreSet6J1);
        tvScoreSet6J2 = (TextView) findViewById(R.id.tvScoreSet6J2);
        tvScoreSet7J1 = (TextView) findViewById(R.id.tvScoreSet7J1);
        tvScoreSet7J2 = (TextView) findViewById(R.id.tvScoreSet7J2);
        tvTotalSetsJ1= (TextView) findViewById(R.id.tvTotalSetsJ1);
        tvTotalSetsJ2= (TextView) findViewById(R.id.tvTotalSetsJ2);
        afficheTableau();

        _Double_Id = 0;
        Intent intent = getIntent();
        _Double_Id = intent.getIntExtra("match_Id", 0);
        DoublesRepo repo = new DoublesRepo(this);
        Doubles matchDouble = new Doubles();
        matchDouble = repo.getSimpleById(_Double_Id);


        tvNomJoueurUn = (TextView) findViewById(R.id.tvNomJoueurUn);
        String nomJoueurCompletJ1 = getIntent().getExtras().getString("nomJoueurCompletJ1A")+"-"+getIntent().getExtras().getString("nomJoueurCompletJ1B");
        tvNomJoueurUn.setText(nomJoueurCompletJ1);

        tvNomJoueurDeux = (TextView) findViewById(R.id.tvNomJoueurDeux);
        String nomJoueurCompletJ2 = getIntent().getExtras().getString("nomJoueurCompletJ2A")+"-"+getIntent().getExtras().getString("nomJoueurCompletJ2B");
        tvNomJoueurDeux.setText(nomJoueurCompletJ2);

        String NomCourtJoueurGauche = getIntent().getExtras().getString("nomJoueurPositionGauche");
        tvNomCourtJoueurGauche = (TextView) findViewById(R.id.tvNomCourtJoueurGauche);
        tvNomCourtJoueurGauche.setText(NomCourtJoueurGauche);

        String NomCourtJoueurDroite = getIntent().getExtras().getString("nomJoueurPositionDroite");
        tvNomCourtJoueurDroite = (TextView) findViewById(R.id.tvNomCourtJoueurDroite);
        tvNomCourtJoueurDroite.setText(NomCourtJoueurDroite);

        imageButtonSave = (ImageButton) findViewById(R.id.imageButtonSave);
        imageButtonSave.setOnClickListener(new MatchdoubleActivity.stockage());

        imageViewBalDroite1 = (ImageView) findViewById(R.id.imageViewBalDroite1);
        imageViewBalDroite2 = (ImageView) findViewById(R.id.imageViewBalDroite2);
        imageViewBalGauche1 = (ImageView) findViewById(R.id.imageViewBalGauche1);
        imageViewBalGauche2 = (ImageView) findViewById(R.id.imageViewBalGauche2);

        Boolean serviceGauche = getIntent().getExtras().getBoolean("serviceGauche");
        serviceCoteGauche = serviceGauche;
        if (serviceGauche) {
            intServiceGauche = 2;
            intServiceDroite = 0;
            imageViewBalGauche1.setVisibility(View.VISIBLE);
            imageViewBalGauche2.setVisibility(View.VISIBLE);
            imageViewBalDroite1.setVisibility(View.INVISIBLE);
            imageViewBalDroite2.setVisibility(View.INVISIBLE);
        } else {
            intServiceGauche = 0;
            intServiceDroite = 2;
            imageViewBalGauche1.setVisibility(View.INVISIBLE);
            imageViewBalGauche2.setVisibility(View.INVISIBLE);
            imageViewBalDroite1.setVisibility(View.VISIBLE);
            imageViewBalDroite2.setVisibility(View.VISIBLE);
        }
        btnPlusGauche = (Button) findViewById(R.id.btnPlusGauche);
        btnPlusGauche.setOnClickListener(new ajoutePointGauche());
        btnPlusDroite = (Button) findViewById(R.id.btnPlusDroite);
        btnPlusDroite.setOnClickListener(new ajoutePointDroite());
        tvScoreNbSetsGauche = (TextView) findViewById(R.id.tvScoreNbSetsGauche);
        tvScoreNbSetsDroite = (TextView) findViewById(R.id.tvScoreNbSetsDroite);
        btnMoinsDroite = (Button) findViewById(R.id.btnMoinsDroite);
        btnMoinsDroite.setOnClickListener(new retirePointDroite());
        btnMoinsGauche = (Button) findViewById(R.id.btnMoinsGauche);
        btnMoinsGauche.setOnClickListener(new retirePointGauche());

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "La connection Bluetooth n'est pas disponible sur cet appareil.",
                    Toast.LENGTH_LONG).show();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    setupChat();
                } else {
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        String address = data.getExtras().getString(
                DeviceListActivity.DEVICE_ADDRESS);
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        btService.connect(device, secure);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent serverIntent = null;
        switch (item.getItemId()) {
            case R.id.secure_connect_scan:
                serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;

            case R.id.discoverable:
                ensureDiscoverable();
                return true;
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    private void ensureDiscoverable() {
        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(
                    BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    private void sendMessage(String message) {
        if (btService.getState() != BTService.STATE_CONNECTED) {
            //Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
            //       .show();
            return;
        }
        if (message.length() > 0) {
            byte[] send = message.getBytes();
            btService.write(send);
            outStringBuffer.setLength(0);
        }
    }

    private TextView.OnEditorActionListener mWriteListener = new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView view, int actionId,
                                      KeyEvent event) {
            if (actionId == EditorInfo.IME_NULL
                    && event.getAction() == KeyEvent.ACTION_UP) {
                String message = view.getText().toString();
                sendMessage(message);
            }
            return true;
        }
    };

    private void setStatus(int resId) {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(resId);
    }

    private void setStatus(CharSequence subTitle) {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(subTitle);
    }

    private void setupChat() {
        chatArrayAdapter = new ArrayAdapter<String>(this, R.layout.message2);
        btService = new BTService(this, handler);
        outStringBuffer = new StringBuffer("");
    }


    private void afficheTableau() {
        switch (nbSetsAJouer) {
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

    @Override
    protected void onResume() {
        super.onResume();
        if (btService != null) {
            if (btService.getState() == BTService.STATE_NONE) {
                btService.start();
            }
        }
    }

    //debut bluetooth
    @Override
    public void onStart() {
        super.onStart();

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            if (btService == null)
                setupChat();
        }
    }
    //fin bluetooth
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Quitter le match")
                .setMessage("Voulez-vous quitter ce match ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MatchdoubleActivity.this.finish();
                    }
                })
                .setNegativeButton("Non", null)//Do nothing on no
                .show();
    }
    @Override
    protected void onPause() {
        super.onPause();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Bluetooth
        //if (btService != null)
            btService.stop();
        //fin Bluetooth
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// supprime l'écran toujours Allumé
    }

    @Override
    protected void onSaveInstanceState(Bundle statutOut) {
        statutOut.putString("StoreScoreJoueurGauche", tvScoreSetGauche.getText().toString());
        statutOut.putString("StoreScoreJoueurDroite", tvScoreSetDroite.getText().toString());
        statutOut.putString("StoreScoreSetJoueurGauche", tvScoreNbSetsGauche.getText().toString());
        statutOut.putString("StoreScoreSetJoueurDroite", tvScoreNbSetsDroite.getText().toString());
        statutOut.putString("StoreNomCourtJoueurGauche", tvNomCourtJoueurGauche.getText().toString());
        statutOut.putString("StoreNomCourtJoueurDroite", tvNomCourtJoueurDroite.getText().toString());

        statutOut.putInt("StoreIntpointSetGauche", pointSetGauche);
        statutOut.putInt("StoreIntpointSetDroite", pointSetDroite);

        statutOut.putString("StoreSet1J1", tvScoreSet1J1.getText().toString());
        statutOut.putString("StoreSet1J2", tvScoreSet1J2.getText().toString());
        statutOut.putString("StoreSet2J1", tvScoreSet2J1.getText().toString());
        statutOut.putString("StoreSet2J2", tvScoreSet2J2.getText().toString());
        statutOut.putString("StoreSet3J1", tvScoreSet3J1.getText().toString());
        statutOut.putString("StoreSet3J2", tvScoreSet3J2.getText().toString());
        statutOut.putString("StoreSet4J1", tvScoreSet4J1.getText().toString());
        statutOut.putString("StoreSet4J2", tvScoreSet4J2.getText().toString());
        statutOut.putString("StoreSet5J1", tvScoreSet5J1.getText().toString());
        statutOut.putString("StoreSet5J2", tvScoreSet5J2.getText().toString());
        statutOut.putString("StoreSet6J1", tvScoreSet6J1.getText().toString());
        statutOut.putString("StoreSet6J2", tvScoreSet6J2.getText().toString());
        statutOut.putString("StoreSet7J1", tvScoreSet7J1.getText().toString());
        statutOut.putString("StoreSet7J2", tvScoreSet7J2.getText().toString());
        super.onSaveInstanceState(statutOut);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        tvScoreSetGauche.setText(savedInstanceState.getString("StoreScoreJoueurGauche"));
        tvScoreSetDroite.setText(savedInstanceState.getString("StoreScoreJoueurDroite"));
        tvScoreNbSetsGauche.setText(savedInstanceState.getString("StoreScoreSetJoueurGauche"));
        tvScoreNbSetsDroite.setText(savedInstanceState.getString("StoreScoreSetJoueurDroite"));
        tvNomCourtJoueurGauche.setText(savedInstanceState.getString("StoreNomCourtJoueurGauche"));
        tvNomCourtJoueurDroite.setText(savedInstanceState.getString("StoreNomCourtJoueurDroite"));

        pointSetGauche = (savedInstanceState.getInt("StoreIntpointSetGauche"));
        pointSetDroite = (savedInstanceState.getInt("StoreIntpointSetDroite"));

        tvScoreSet1J1.setText(savedInstanceState.getString("StoreSet1J1"));
        tvScoreSet1J2.setText(savedInstanceState.getString("StoreSet1J2"));
        tvScoreSet2J1.setText(savedInstanceState.getString("StoreSet2J1"));
        tvScoreSet2J2.setText(savedInstanceState.getString("StoreSet2J2"));
        tvScoreSet3J1.setText(savedInstanceState.getString("StoreSet3J1"));
        tvScoreSet3J2.setText(savedInstanceState.getString("StoreSet3J2"));
        tvScoreSet4J1.setText(savedInstanceState.getString("StoreSet4J1"));
        tvScoreSet4J2.setText(savedInstanceState.getString("StoreSet4J2"));
        tvScoreSet5J1.setText(savedInstanceState.getString("StoreSet5J1"));
        tvScoreSet5J2.setText(savedInstanceState.getString("StoreSet5J2"));
        tvScoreSet6J1.setText(savedInstanceState.getString("StoreSet6J1"));
        tvScoreSet6J2.setText(savedInstanceState.getString("StoreSet6J2"));
        tvScoreSet7J1.setText(savedInstanceState.getString("StoreSet7J1"));
        tvScoreSet7J2.setText(savedInstanceState.getString("StoreSet7J2"));
    }

    private class ajoutePointGauche implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            pointSetGauche ++;
            if(pointSetGauche<10){
                tvScoreSetGauche.setText("0"+String.valueOf(pointSetGauche));
            } else{
                tvScoreSetGauche.setText(String.valueOf(pointSetGauche));
            }
            service();
            setDecisif();
            prepareMessage();
            sendMessage(messageAenvoyer);
            finDeSet();
        }
    }
    private class ajoutePointDroite implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            pointSetDroite ++;
            if(pointSetDroite<10){
                tvScoreSetDroite.setText("0"+String.valueOf(pointSetDroite));
            }else{
                tvScoreSetDroite.setText(String.valueOf(pointSetDroite));
            }
            service();
            setDecisif();
            prepareMessage();
            sendMessage(messageAenvoyer);
            finDeSet();
        }
    }

    private void setDecisif() {
        if(changementJeuxDecisif ==0){
            if((intNbSetsAJouer == 3)||(intNbSetsAJouer == 5)||(intNbSetsAJouer == 7)){
                if((nbSetsGauche+nbSetsDroite)==(intNbSetsAJouer - 1)){
                    if((pointSetGauche == 5) ||(pointSetDroite ==5)){

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder
                                .setTitle("Set décisif")
                                .setMessage("Les joueurs doivent changer de côté")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        changementJeuxDecisif = 1;
                                        String nomJoueurEnAttente = tvNomCourtJoueurDroite.getText().toString();
                                        tvNomCourtJoueurDroite.setText(tvNomCourtJoueurGauche.getText().toString());
                                        tvNomCourtJoueurGauche.setText(nomJoueurEnAttente);
                                        int pointdroiteencours = pointSetDroite;
                                        pointSetDroite = pointSetGauche;
                                        pointSetGauche = pointdroiteencours;
                                        String pointsEnAttente = tvScoreSetDroite.getText().toString();
                                        tvScoreSetDroite.setText(tvScoreSetGauche.getText().toString());
                                        tvScoreSetGauche.setText(pointsEnAttente);
                                        if (positionJoueur == 1) {
                                            positionJoueur = 2;
                                        } else {
                                            positionJoueur = 1;
                                        }
                                        if (serviceCoteGauche == true) {
                                            serviceCoteGauche = false;
                                        } else {
                                            serviceCoteGauche = true;
                                        }
                                        service();
                                        prepareMessage();
                                        sendMessage(messageAenvoyer);
                                    }
                                })
                                .show();
                    }
                }
            }
        }
    }

    private void retourSetDecisif() {
        if(changementJeuxDecisif == 1){
            if((intNbSetsAJouer == 3)||(intNbSetsAJouer == 5)||(intNbSetsAJouer == 7)){
                if((nbSetsGauche+nbSetsDroite)==(intNbSetsAJouer - 1)){
                    if((pointSetGauche == 4) ||(pointSetDroite ==4)){
                        Toast.makeText(MatchdoubleActivity.this, "Les joueurs doivent revenir à leur place", Toast.LENGTH_SHORT).show();
                        changementJeuxDecisif = 0;
                        String nomJoueurEnAttente = tvNomCourtJoueurDroite.getText().toString();
                        tvNomCourtJoueurDroite.setText(tvNomCourtJoueurGauche.getText().toString());
                        tvNomCourtJoueurGauche.setText(nomJoueurEnAttente);
                        int pointdroiteencours = pointSetDroite;
                        pointSetDroite = pointSetGauche;
                        pointSetGauche = pointdroiteencours;
                        String pointsEnAttente = tvScoreSetDroite.getText().toString();
                        tvScoreSetDroite.setText(tvScoreSetGauche.getText().toString());
                        tvScoreSetGauche.setText(pointsEnAttente);
                        if (positionJoueur == 1) {
                            positionJoueur = 2;
                        } else {
                            positionJoueur = 1;
                        }
                        if (serviceCoteGauche == true) {
                            serviceCoteGauche = false;
                        } else {
                            serviceCoteGauche = true;
                        }
                        service();
                        prepareMessage();
                        sendMessage(messageAenvoyer);
                    }
                }
            }
        }
    }

    private class retirePointGauche implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(pointSetGauche>0){
                pointSetGauche--;
                vibrate(200);
                service();
                if(pointSetGauche<10){
                    tvScoreSetGauche.setText("0"+String.valueOf(pointSetGauche));
                }else {
                    tvScoreSetGauche.setText(String.valueOf(pointSetGauche));
                }
                retourSetDecisif();
                prepareMessage();
                sendMessage(messageAenvoyer);
            }
        }
    }

    private class retirePointDroite implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(pointSetDroite>0){
                pointSetDroite--;
                vibrate(200);
                service();
                if(pointSetDroite<10){
                    tvScoreSetDroite.setText("0"+String.valueOf(pointSetDroite));
                }else{
                    tvScoreSetDroite.setText(String.valueOf(pointSetDroite));
                }
                retourSetDecisif();
                prepareMessage();
                sendMessage(messageAenvoyer);
            }
        }
    }

    private void prepareMessage() {
        messageAenvoyer = (tvNomCourtJoueurGauche.getText().toString()
                +"#"
                +tvNomCourtJoueurDroite.getText().toString()
                +"#"
                +tvScoreNbSetsGauche.getText().toString()
                +"#"
                +tvScoreNbSetsDroite.getText().toString()
                +"#"
                +tvScoreSetGauche.getText().toString()
                +"#"
                +tvScoreSetDroite.getText().toString()
                +"#"
                +String.valueOf(intServiceGauche)
                +"#"
                +String.valueOf(intServiceDroite)
        );
    }

    private void saisirFinDeSetTableau() {
        if(positionJoueur ==1){
            switch(chiffreDuSetEnCours){
                case 1:
                    tvScoreSet1J1.setText(String.valueOf(pointSetGauche));
                    tvScoreSet1J2.setText(String.valueOf(pointSetDroite));
                    if (pointSetGauche>pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }
                    changeDeSet();
                    break;
                case 2:
                    tvScoreSet2J1.setText(String.valueOf(pointSetGauche));
                    tvScoreSet2J2.setText(String.valueOf(pointSetDroite));
                    if (pointSetGauche>pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }
                    changeDeSet();
                    break;
                case 3:
                    tvScoreSet3J1.setText(String.valueOf(pointSetGauche));
                    tvScoreSet3J2.setText(String.valueOf(pointSetDroite));
                    if (pointSetGauche>pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }
                    changeDeSet();
                    break;
                case 4:
                    tvScoreSet4J1.setText(String.valueOf(pointSetGauche));
                    tvScoreSet4J2.setText(String.valueOf(pointSetDroite));
                    if (pointSetGauche>pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }
                    changeDeSet();
                    break;
                case 5:
                    tvScoreSet5J1.setText(String.valueOf(pointSetGauche));
                    tvScoreSet5J2.setText(String.valueOf(pointSetDroite));
                    if (pointSetGauche>pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }
                    changeDeSet();
                    break;
                case 6:
                    tvScoreSet6J1.setText(String.valueOf(pointSetGauche));
                    tvScoreSet6J2.setText(String.valueOf(pointSetDroite));
                    if (pointSetGauche>pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }
                    changeDeSet();
                    break;
                case 7:
                    tvScoreSet7J1.setText(String.valueOf(pointSetGauche));
                    tvScoreSet7J2.setText(String.valueOf(pointSetDroite));
                    if (pointSetGauche>pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }
                    break;
            }
        }else{
            switch(chiffreDuSetEnCours){
                case 1:
                    tvScoreSet1J1.setText(String.valueOf(pointSetDroite));
                    tvScoreSet1J2.setText(String.valueOf(pointSetGauche));
                    if (pointSetGauche<pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }
                    changeDeSet();
                    break;
                case 2:
                    tvScoreSet2J1.setText(String.valueOf(pointSetDroite));
                    tvScoreSet2J2.setText(String.valueOf(pointSetGauche));
                    if (pointSetGauche<pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }
                    changeDeSet();
                    break;
                case 3:
                    tvScoreSet3J1.setText(String.valueOf(pointSetDroite));
                    tvScoreSet3J2.setText(String.valueOf(pointSetGauche));
                    if (pointSetGauche<pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }
                    changeDeSet();
                    break;
                case 4:
                    tvScoreSet4J1.setText(String.valueOf(pointSetDroite));
                    tvScoreSet4J2.setText(String.valueOf(pointSetGauche));
                    if (pointSetGauche<pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }
                    changeDeSet();
                    break;
                case 5:
                    tvScoreSet5J1.setText(String.valueOf(pointSetDroite));
                    tvScoreSet5J2.setText(String.valueOf(pointSetGauche));
                    if (pointSetGauche<pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }
                    changeDeSet();
                    break;
                case 6:
                    tvScoreSet6J1.setText(String.valueOf(pointSetDroite));
                    tvScoreSet6J2.setText(String.valueOf(pointSetGauche));
                    if (pointSetGauche<pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }
                    changeDeSet();
                    break;
                case 7:
                    tvScoreSet7J1.setText(String.valueOf(pointSetDroite));
                    tvScoreSet7J2.setText(String.valueOf(pointSetGauche));
                    if (pointSetGauche<pointSetDroite){
                        nbSetsGauche++;
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                    }else{
                        nbSetsDroite++;
                        tvTotalSetsJ1.setText(String.valueOf(nbSetsGauche));
                        tvTotalSetsJ2.setText(String.valueOf(nbSetsDroite));
                    }
                    changeDeSet();
                    break;
            }
        }
    }
    private void changeDeSet() {
        chiffreDuSetEnCours++;
        pointSetGauche = 0;
        tvScoreSetGauche.setText("00");
        pointSetDroite = 0;
        tvScoreSetDroite.setText("00");
        if (serviceCoteGauche) {
            intServiceGauche = 2;
            intServiceDroite = 0;
            imageViewBalGauche1.setVisibility(View.VISIBLE);
            imageViewBalGauche2.setVisibility(View.VISIBLE);
            imageViewBalDroite1.setVisibility(View.INVISIBLE);
            imageViewBalDroite2.setVisibility(View.INVISIBLE);
        } else {
            intServiceGauche = 0;
            intServiceDroite = 2;
            imageViewBalGauche1.setVisibility(View.INVISIBLE);
            imageViewBalGauche2.setVisibility(View.INVISIBLE);
            imageViewBalDroite1.setVisibility(View.VISIBLE);
            imageViewBalDroite2.setVisibility(View.VISIBLE);
        }
        if (positionJoueur ==1){
            positionJoueur = 2;
            String nomJoueurEnAttente = tvNomCourtJoueurDroite.getText().toString();
            tvNomCourtJoueurDroite.setText(tvNomCourtJoueurGauche.getText().toString());
            tvNomCourtJoueurGauche.setText(nomJoueurEnAttente);
            tvScoreNbSetsGauche.setText(String.valueOf(nbSetsDroite));
            tvScoreNbSetsDroite.setText(String.valueOf(nbSetsGauche));
        } else{
            positionJoueur = 1;
            String nomJoueurEnAttente = tvNomCourtJoueurDroite.getText().toString();
            tvNomCourtJoueurDroite.setText(tvNomCourtJoueurGauche.getText().toString());
            tvNomCourtJoueurGauche.setText(nomJoueurEnAttente);
            tvScoreNbSetsGauche.setText(String.valueOf(nbSetsGauche));
            tvScoreNbSetsDroite.setText(String.valueOf(nbSetsDroite));
        }
        prepareMessage();
        sendMessage(messageAenvoyer);
    }

    @SuppressLint("MissingPermission")
    public void vibrate(int temps){
        Vibrator vibs = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(temps);
    }

    public void service() {
        int tableauService[][] = { {2,1,0,0,2,1,0,0,2,1,0,0,2,1,0,0,2,1,0,0,1,0,1,0},{0,0,2,1,0,0,2,1,0,0,2,1,0,0,2,1,0,0,2,1,0,1,0,1} };
        int sommePoints = pointSetGauche+pointSetDroite;
        if(sommePoints<=22){
            if (serviceCoteGauche==true){
                intServiceGauche = tableauService[0][sommePoints];
                intServiceDroite = tableauService[1][sommePoints];
            } else {
                intServiceDroite = tableauService[0][sommePoints];
                intServiceGauche = tableauService[1][sommePoints];
            }
            if (intServiceGauche == 2) {
                imageViewBalGauche1.setVisibility(View.VISIBLE);
                imageViewBalGauche2.setVisibility(View.VISIBLE);
            }
            if (intServiceGauche == 1) {
                imageViewBalGauche1.setVisibility(View.INVISIBLE);
                imageViewBalGauche2.setVisibility(View.VISIBLE);
            }
            if (intServiceGauche == 0) {
                imageViewBalGauche1.setVisibility(View.INVISIBLE);
                imageViewBalGauche2.setVisibility(View.INVISIBLE);
            }
            if (intServiceDroite == 2) {
                imageViewBalDroite1.setVisibility(View.VISIBLE);
                imageViewBalDroite2.setVisibility(View.VISIBLE);
            }
            if (intServiceDroite == 1) {
                imageViewBalDroite1.setVisibility(View.VISIBLE);
                imageViewBalDroite2.setVisibility(View.INVISIBLE);
            }
            if (intServiceDroite == 0) {
                imageViewBalDroite1.setVisibility(View.INVISIBLE);
                imageViewBalDroite2.setVisibility(View.INVISIBLE);
            }
        } else{
            if ((serviceCoteGauche) && (sommePoints % 2 == 0)){
                intServiceGauche = 1;
                intServiceDroite = 0;
                imageViewBalGauche1.setVisibility(View.INVISIBLE);
                imageViewBalGauche2.setVisibility(View.VISIBLE);
                imageViewBalDroite1.setVisibility(View.INVISIBLE);
                imageViewBalDroite2.setVisibility(View.INVISIBLE);
            } else {
                if ((serviceCoteGauche==false) && (sommePoints % 2 == 0)){
                    intServiceGauche = 0;
                    intServiceDroite = 1;
                    imageViewBalGauche1.setVisibility(View.INVISIBLE);
                    imageViewBalGauche2.setVisibility(View.INVISIBLE);
                    imageViewBalDroite1.setVisibility(View.VISIBLE);
                    imageViewBalDroite2.setVisibility(View.INVISIBLE);
                }else {
                    if ((serviceCoteGauche) && (sommePoints % 2 != 0)) {
                        intServiceGauche = 0;
                        intServiceDroite = 1;
                        imageViewBalGauche1.setVisibility(View.INVISIBLE);
                        imageViewBalGauche2.setVisibility(View.INVISIBLE);
                        imageViewBalDroite1.setVisibility(View.VISIBLE);
                        imageViewBalDroite2.setVisibility(View.INVISIBLE);
                    } else {
                        if ((serviceCoteGauche == false) && (sommePoints % 2 != 0)) {
                            intServiceGauche = 1;
                            intServiceDroite = 0;
                            imageViewBalGauche1.setVisibility(View.INVISIBLE);
                            imageViewBalGauche2.setVisibility(View.VISIBLE);
                            imageViewBalDroite1.setVisibility(View.INVISIBLE);
                            imageViewBalDroite2.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        }
        /*Animation startFadeOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_fade_out);
        imageViewBalGauche2.startAnimation(startFadeOutAnimation);
        Animation startFadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_fade_in);
        imageViewBalDroite1.startAnimation(startFadeInAnimation);*/
    }
    public void finDeSet(){
        if (((pointSetDroite >= 11) && (pointSetDroite-pointSetGauche>=2))||((pointSetGauche >= 11) && (pointSetGauche-pointSetDroite>=2))){
            messageFinDeSet();
        }
    }
    public void messageFinDeSet(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Fin de set")
                .setMessage("Validez-vous la fin de ce set ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Yes button clicked, do something
                        Toast.makeText(MatchdoubleActivity.this, "Vous avez validé la fin du set",Toast.LENGTH_SHORT).show();
                        saisirFinDeSetTableau();
                        finDeMatch();
                    }
                })
                .setNegativeButton("Non", null)//Do nothing on no
                .show();
    }
    public void finDeMatch(){
        if((nbSetsGauche==((intNbSetsAJouer+1)/2))||(nbSetsDroite==((intNbSetsAJouer+1)/2))){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("Fin du match")
                    .setMessage("Validez-vous la fin de ce match ?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Yes button clicked, do something
                            Toast.makeText(MatchdoubleActivity.this, "Vous avez validé la fin du match",Toast.LENGTH_SHORT).show();
                            layoutPlusMoins.setVisibility(View.GONE);
                            layoutService.setVisibility(View.GONE);
                            layoutEnr.setVisibility(View.VISIBLE);
                            playSound(R.raw.applaudissements);
                        }
                    })
                    .setNegativeButton("Non", null)//Do nothing on no
                    .show();
        }
    }
    private class stockage implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            DoublesRepo repo = new DoublesRepo(MatchdoubleActivity.this);
            Doubles matchDouble = new Doubles();
            matchDouble.double_ID = _Double_Id;

            matchDouble.namej1a = getIntent().getExtras().getString("nomJoueurCompletJ1A");
            matchDouble.namej1b = getIntent().getExtras().getString("nomJoueurCompletJ1B");
            matchDouble.namej2a = getIntent().getExtras().getString("nomJoueurCompletJ2A");
            matchDouble.namej2b = getIntent().getExtras().getString("nomJoueurCompletJ2B");

            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
            String today = formatter.format(date);

            matchDouble.date = today;
            matchDouble.nbsets = Integer.parseInt(String.valueOf(intNbSetsAJouer));
            matchDouble.scoreSetj1 = Integer.parseInt(tvTotalSetsJ1.getText().toString());
            matchDouble.scoreSetj2 = Integer.parseInt(tvTotalSetsJ2.getText().toString());
            matchDouble.score_set1_j1 = Integer.parseInt(tvScoreSet1J1.getText().toString());
            matchDouble.score_set1_j2 = Integer.parseInt(tvScoreSet1J2.getText().toString());
            matchDouble.score_set2_j1 = Integer.parseInt(tvScoreSet2J1.getText().toString());
            matchDouble.score_set2_j2 = Integer.parseInt(tvScoreSet2J2.getText().toString());
            matchDouble.score_set3_j1 = Integer.parseInt(tvScoreSet3J1.getText().toString());
            matchDouble.score_set3_j2 = Integer.parseInt(tvScoreSet3J2.getText().toString());
            matchDouble.score_set4_j1 = Integer.parseInt(tvScoreSet4J1.getText().toString());
            matchDouble.score_set4_j2 = Integer.parseInt(tvScoreSet4J2.getText().toString());
            matchDouble.score_set5_j1 = Integer.parseInt(tvScoreSet5J1.getText().toString());
            matchDouble.score_set5_j2 = Integer.parseInt(tvScoreSet5J2.getText().toString());
            matchDouble.score_set6_j1 = Integer.parseInt(tvScoreSet6J1.getText().toString());
            matchDouble.score_set6_j2 = Integer.parseInt(tvScoreSet6J2.getText().toString());
            matchDouble.score_set7_j1 = Integer.parseInt(tvScoreSet7J1.getText().toString());
            matchDouble.score_set7_j2 = Integer.parseInt(tvScoreSet7J2.getText().toString());

            if (_Double_Id == 0) {
                _Double_Id = repo.insert(matchDouble);
                Toast.makeText(MatchdoubleActivity.this, "Match double enregistré", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void playSound(int resId) {
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this, resId);
        mPlayer.start();
    }
}
