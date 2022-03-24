package albert.miguel.tennisdetable;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import albert.miguel.tennisdetable.BaseDeDonnees.SimplesDetail;
import albert.miguel.tennisdetable.BaseDeDonnees.SimplesRepo;

public class EnrSimples extends ListActivity implements android.view.View.OnClickListener{

    TextView match_Id;

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enr_simples);
        listeLesParties();
    }
    @Override
    protected void onResume() {
        super.onResume();
        listeLesParties();
    }

    public void listeLesParties() {
        SimplesRepo repo = new SimplesRepo(this);
        ArrayList<HashMap<String, String>> studentList =  repo.getStudentList();
        if(studentList.size()!=0) {
            ListView lv = getListView();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    match_Id = (TextView) view.findViewById(R.id.student_Id);
                    String studentId = match_Id.getText().toString();
                    Intent objIntent = new Intent(getApplicationContext(),SimplesDetail.class);
                    objIntent.putExtra("match_Id", Integer.parseInt( studentId));
                    startActivity(objIntent);
                }
            });
            ListAdapter adapter = new SimpleAdapter( EnrSimples.this,studentList, R.layout.simple_list, new String[] { "id","date","name1","name2","nbSetJ1","nbSetJ2"}, new int[] {R.id.student_Id, R.id.date,R.id.name_j1,R.id.name_j2,R.id.setj1,R.id.setj2 });
            setListAdapter(adapter);
        }else{
            Toast.makeText(this,"Aucune partie enregistrée",Toast.LENGTH_SHORT).show();
        }
    }


}