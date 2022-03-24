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

import albert.miguel.tennisdetable.BaseDeDonnees.DoublesDetail;
import albert.miguel.tennisdetable.BaseDeDonnees.DoublesRepo;

public class EnrDoubles extends ListActivity implements View.OnClickListener{

    TextView match_Id;

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enr_doubles);
        listeLesParties();
    }
    @Override
    protected void onResume() {
        super.onResume();
        listeLesParties();
    }

    public void listeLesParties() {
        DoublesRepo repo = new DoublesRepo(this);
        ArrayList<HashMap<String, String>> studentList =  repo.getStudentList();
        if(studentList.size()!=0) {
            ListView lv = getListView();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    match_Id = (TextView) view.findViewById(R.id.student_Id);
                    String studentId = match_Id.getText().toString();
                    Intent objIntent = new Intent(getApplicationContext(),DoublesDetail.class);
                    objIntent.putExtra("match_Id", Integer.parseInt( studentId));
                    startActivity(objIntent);
                }
            });
            ListAdapter adapter = new SimpleAdapter( EnrDoubles.this,studentList, R.layout.double_list, new String[] { "id","date","name1a","name1b","name2a","name2b","nbSetJ1","nbSetJ2"}, new int[] {R.id.student_Id, R.id.date,R.id.name_j1a,R.id.name_j1b,R.id.name_j2a,R.id.name_j2b,R.id.setj1,R.id.setj2 });
            setListAdapter(adapter);
        }else{
            Toast.makeText(this,"Aucune partie enregistrée",Toast.LENGTH_SHORT).show();
        }
    }


}