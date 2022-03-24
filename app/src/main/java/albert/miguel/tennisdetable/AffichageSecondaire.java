package albert.miguel.tennisdetable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import static java.lang.String.valueOf;

public class AffichageSecondaire extends AppCompatActivity {
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

    private TextView tvNomJoueurGauche,tvNomJoueurDroite,tvPointsGauche,tvPointsDroite,tvSetsGauche,tvSetsDroite,tvServiceGauche,tvServiceDroite;

    ImageView imageViewBalDroite1,imageViewBalDroite2,imageViewBalGauche1,imageViewBalGauche2;
    String[] elements;

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

                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    repartitionDanslesCases(readMessage);
                    afficheLesBalles();
                    break;
                case MESSAGE_DEVICE_NAME:
                    connectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(),
                            "Connecté à " + connectedDeviceName,
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

    public void repartitionDanslesCases(String readMessage) {
        elements = readMessage.split("\\#");//separe le resultat après les #
        tvNomJoueurGauche.setText(elements[1]);
        tvNomJoueurDroite.setText(elements[0]);
        tvSetsGauche.setText(elements[3]);
        tvSetsDroite.setText(elements[2]);
        tvPointsGauche.setText(elements[5]);
        tvPointsDroite.setText(elements[4]);
        tvServiceGauche.setText(elements[7]);
        tvServiceDroite.setText(elements[6]);

    }

    public void afficheLesBalles() {
        imageViewBalDroite1 = (ImageView) findViewById(R.id.imageViewBalDroite1);
        imageViewBalDroite2 = (ImageView) findViewById(R.id.imageViewBalDroite2);
        imageViewBalGauche1 = (ImageView) findViewById(R.id.imageViewBalGauche1);
        imageViewBalGauche2 = (ImageView) findViewById(R.id.imageViewBalGauche2);
        if (valueOf(elements[6]).equals("0")) {
            imageViewBalDroite1.setVisibility(View.INVISIBLE);
            imageViewBalDroite2.setVisibility(View.INVISIBLE);
        }
        if (valueOf(elements[6]).equals("1")) {
            imageViewBalDroite1.setVisibility(View.VISIBLE);
            imageViewBalDroite2.setVisibility(View.INVISIBLE);
        }
        if (valueOf(elements[6]).equals("2")) {
            imageViewBalDroite1.setVisibility(View.VISIBLE);
            imageViewBalDroite2.setVisibility(View.VISIBLE);
        }
        if (valueOf(elements[7]).equals("0")) {
            imageViewBalGauche1.setVisibility(View.INVISIBLE);
            imageViewBalGauche2.setVisibility(View.INVISIBLE);
        }
        if (valueOf(elements[7]).equals("1")) {
            imageViewBalGauche1.setVisibility(View.VISIBLE);
            imageViewBalGauche2.setVisibility(View.INVISIBLE);
        }
        if (valueOf(elements[7]).equals("2")) {
            imageViewBalGauche1.setVisibility(View.VISIBLE);
            imageViewBalGauche2.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_secondaire);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// Garde l'écran toujours Allumé
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        tvNomJoueurGauche = (TextView) findViewById(R.id.tvNomJoueurGauche);
        tvNomJoueurDroite = (TextView) findViewById(R.id.tvNomJoueurDroite);
        tvPointsGauche = (TextView) findViewById(R.id.tvPointsGauche);
        tvPointsDroite = (TextView) findViewById(R.id.tvPointsDroite);
        tvSetsGauche = (TextView) findViewById(R.id.tvSetsGauche);
        tvSetsDroite = (TextView) findViewById(R.id.tvSetsDroite);
        tvServiceGauche = (TextView) findViewById(R.id.tvServiceGauche);
        tvServiceDroite = (TextView) findViewById(R.id.tvServiceDroite);

        imageViewBalDroite1 = (ImageView) findViewById(R.id.imageViewBalDroite1);
        imageViewBalDroite2 = (ImageView) findViewById(R.id.imageViewBalDroite2);
        imageViewBalGauche1 = (ImageView) findViewById(R.id.imageViewBalGauche1);
        imageViewBalGauche1 = (ImageView) findViewById(R.id.imageViewBalGauche1);


        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Le Bluetooth n'est pas disponible sur cet appareil",
                    Toast.LENGTH_LONG).show();
            return;
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

    private final void setStatus(int resId) {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(resId);
    }

    private final void setStatus(CharSequence subTitle) {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(subTitle);
    }

    private void setupChat() {
        chatArrayAdapter = new ArrayAdapter<String>(this, R.layout.message2);
        btService = new BTService(this, handler);
        outStringBuffer = new StringBuffer("");
    }

    @SuppressLint("MissingPermission")
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

    @Override
    public synchronized void onResume() {
        super.onResume();

        if (btService != null) {
            if (btService.getState() == BTService.STATE_NONE) {
                btService.start();
            }
        }
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //if (btService != null)
        //    btService.stop();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// supprime l'écran toujours Allumé
    }

}
