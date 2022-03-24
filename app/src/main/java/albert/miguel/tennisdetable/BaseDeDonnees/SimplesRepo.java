package albert.miguel.tennisdetable.BaseDeDonnees;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class SimplesRepo {
    private DBHelper dbHelper;

    public SimplesRepo (Context context) {
            dbHelper = new DBHelper(context);
        }

    public int insert(Simples simple) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Simples.KEY_name_joueur_un, simple.namej1);
        values.put(Simples.KEY_name_joueur_deux, simple.namej2);
        values.put(Simples.KEY_date, simple.date);
        values.put(Simples.KEY_score_set_j1, simple.scoreSetj1);
        values.put(Simples.KEY_score_set_j2, simple.scoreSetj2);
        values.put(Simples.KEY_nb_sets, simple.nbsets);
        values.put(Simples.KEY_set1_j1, simple.score_set1_j1);
        values.put(Simples.KEY_set1_j2, simple.score_set1_j2);
        values.put(Simples.KEY_set2_j1, simple.score_set2_j1);
        values.put(Simples.KEY_set2_j2, simple.score_set2_j2);
        values.put(Simples.KEY_set3_j1, simple.score_set3_j1);
        values.put(Simples.KEY_set3_j2, simple.score_set3_j2);
        values.put(Simples.KEY_set4_j1, simple.score_set4_j1);
        values.put(Simples.KEY_set4_j2, simple.score_set4_j2);
        values.put(Simples.KEY_set5_j1, simple.score_set5_j1);
        values.put(Simples.KEY_set5_j2, simple.score_set5_j2);
        values.put(Simples.KEY_set6_j1, simple.score_set6_j1);
        values.put(Simples.KEY_set6_j2, simple.score_set6_j2);
        values.put(Simples.KEY_set7_j1, simple.score_set7_j1);
        values.put(Simples.KEY_set7_j2, simple.score_set7_j2);

        // Inserting Row
        long simple_Id = db.insert(Simples.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) simple_Id;
    }

    public void delete(int simple_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Simples.TABLE, Simples.KEY_ID + "= ?", new String[] { String.valueOf(simple_Id) });
        db.close(); // Closing database connection
    }

    /*public void update(Simples simple) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Simples.KEY_score_set_j1, simple.scoreSetj1);
        values.put(Simples.KEY_score_set_j2,simple.scoreSetj2);
        values.put(Simples.KEY_name_joueur_un, simple.namej1);
        values.put(Simples.KEY_name_joueur_deux, simple.namej2);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Simples.TABLE, values, Simples.KEY_ID + "= ?", new String[] { String.valueOf(simple.simple_ID) });
        db.close(); // Closing database connection
    }*/

    public ArrayList<HashMap<String, String>> getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Simples.KEY_ID + "," +
                Simples.KEY_name_joueur_un + "," +
                Simples.KEY_name_joueur_deux + "," +
                Simples.KEY_date + "," +
                Simples.KEY_score_set_j1 + "," +
                Simples.KEY_score_set_j2 +
                " FROM " + Simples.TABLE;

        ArrayList<HashMap<String, String>> simpleList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> simple = new HashMap<String, String>();
                simple.put("id", cursor.getString(cursor.getColumnIndex(Simples.KEY_ID)));
                simple.put("date", cursor.getString(cursor.getColumnIndex(Simples.KEY_date)));
                simple.put("name1", cursor.getString(cursor.getColumnIndex(Simples.KEY_name_joueur_un)));
                simple.put("name2", cursor.getString(cursor.getColumnIndex(Simples.KEY_name_joueur_deux)));
                simple.put("nbSetJ1", cursor.getString(cursor.getColumnIndex(Simples.KEY_score_set_j1)));
                simple.put("nbSetJ2", cursor.getString(cursor.getColumnIndex(Simples.KEY_score_set_j2)));
                simpleList.add(simple);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return simpleList;

    }

    public Simples getSimpleById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Simples.KEY_ID + "," +
                Simples.KEY_name_joueur_un + "," +
                Simples.KEY_name_joueur_deux + "," +
                Simples.KEY_date + "," +
                Simples.KEY_score_set_j1 + "," +
                Simples.KEY_score_set_j2 + "," +
                Simples.KEY_nb_sets + "," +
                Simples.KEY_set1_j1 + "," +
                Simples.KEY_set1_j2 + "," +
                Simples.KEY_set2_j1 + "," +
                Simples.KEY_set2_j2 + "," +
                Simples.KEY_set3_j1 + "," +
                Simples.KEY_set3_j2 + "," +
                Simples.KEY_set4_j1 + "," +
                Simples.KEY_set4_j2 + "," +
                Simples.KEY_set5_j1 + "," +
                Simples.KEY_set5_j2 + "," +
                Simples.KEY_set6_j1 + "," +
                Simples.KEY_set6_j2 + "," +
                Simples.KEY_set7_j1 + "," +
                Simples.KEY_set7_j2 +
                " FROM " + Simples.TABLE
                + " WHERE " +
                Simples.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Simples simple = new Simples();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                simple.simple_ID =cursor.getInt(cursor.getColumnIndex(Simples.KEY_ID));
                simple.namej1 =cursor.getString(cursor.getColumnIndex(Simples.KEY_name_joueur_un));
                simple.namej2  =cursor.getString(cursor.getColumnIndex(Simples.KEY_name_joueur_deux));
                simple.date =cursor.getString(cursor.getColumnIndex(Simples.KEY_date));
                simple.scoreSetj1 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_score_set_j1));
                simple.scoreSetj2 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_score_set_j2));
                simple.nbsets = cursor.getInt(cursor.getColumnIndex(Simples.KEY_nb_sets));
                simple.score_set1_j1 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set1_j1));
                simple.score_set1_j2 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set1_j2));
                simple.score_set2_j1 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set2_j1));
                simple.score_set2_j2 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set2_j2));
                simple.score_set3_j1 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set3_j1));
                simple.score_set3_j2 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set3_j2));
                simple.score_set4_j1 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set4_j1));
                simple.score_set4_j2 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set4_j2));
                simple.score_set5_j1 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set5_j1));
                simple.score_set5_j2 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set5_j2));
                simple.score_set6_j1 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set6_j1));
                simple.score_set6_j2 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set6_j2));
                simple.score_set7_j1 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set7_j1));
                simple.score_set7_j2 =cursor.getInt(cursor.getColumnIndex(Simples.KEY_set7_j2));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return simple;
    }
}
