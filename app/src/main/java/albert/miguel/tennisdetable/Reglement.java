package albert.miguel.tennisdetable;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Reglement extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglement);
        pdfView = (PDFView) findViewById(R.id.pdfview);
        pdfView.fromAsset("pdf/reglements-sportifs-2016-correctif-mars-2017-1733.pdf").load();

    }
}
