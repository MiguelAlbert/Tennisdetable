package albert.miguel.tennisdetable.BaseDeDonnees;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class DoublesRepo {
    private DBHelper dbHelper;

    public DoublesRepo(Context context) {
            dbHelper = new DBHelper(context);
        }

    public int insert(Doubles doubles) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Doubles.KEY_name_joueur_un_a, doubles.namej1a);
        values.put(Doubles.KEY_name_joueur_un_b, doubles.namej1b);
        values.put(Doubles.KEY_name_joueur_deux_a, doubles.namej2a);
        values.put(Doubles.KEY_name_joueur_deux_b, doubles.namej2b);
        values.put(Doubles.KEY_date, doubles.date);
        values.put(Doubles.KEY_score_set_j1, doubles.scoreSetj1);
        values.put(Doubles.KEY_score_set_j2, doubles.scoreSetj2);
        values.put(Doubles.KEY_nb_sets, doubles.nbsets);
        values.put(Doubles.KEY_set1_j1, doubles.score_set1_j1);
        values.put(Doubles.KEY_set1_j2, doubles.score_set1_j2);
        values.put(Doubles.KEY_set2_j1, doubles.score_set2_j1);
        values.put(Doubles.KEY_set2_j2, doubles.score_set2_j2);
        values.put(Doubles.KEY_set3_j1, doubles.score_set3_j1);
        values.put(Doubles.KEY_set3_j2, doubles.score_set3_j2);
        values.put(Doubles.KEY_set4_j1, doubles.score_set4_j1);
        values.put(Doubles.KEY_set4_j2, doubles.score_set4_j2);
        values.put(Doubles.KEY_set5_j1, doubles.score_set5_j1);
        values.put(Doubles.KEY_set5_j2, doubles.score_set5_j2);
        values.put(Doubles.KEY_set6_j1, doubles.score_set6_j1);
        values.put(Doubles.KEY_set6_j2, doubles.score_set6_j2);
        values.put(Doubles.KEY_set7_j1, doubles.score_set7_j1);
        values.put(Doubles.KEY_set7_j2, doubles.score_set7_j2);

        // Inserting Row
        long double_Id = db.insert(Doubles.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) double_Id;
    }

    public void delete(int double_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Doubles.TABLE, Doubles.KEY_ID + "= ?", new String[] { String.valueOf(double_Id) });
        db.close(); // Closing database connection
    }

    /*public void update(Doubles doubles) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Doubles.KEY_score_set_j1, doubles.scoreSetj1);
        values.put(Doubles.KEY_score_set_j2,doubles.scoreSetj2);
        values.put(Doubles.KEY_name_joueur_un_a, doubles.namej1a);
        values.put(Doubles.KEY_name_joueur_deux_a, doubles.namej2a);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Doubles.TABLE, values, Doubles.KEY_ID + "= ?", new String[] { String.valueOf(doubles.double_ID) });
        db.close(); // Closing database connection
    }*/

    public ArrayList<HashMap<String, String>> getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Doubles.KEY_ID + "," +
                Doubles.KEY_name_joueur_un_a + "," +
                Doubles.KEY_name_joueur_un_b + "," +
                Doubles.KEY_name_joueur_deux_a + "," +
                Doubles.KEY_name_joueur_deux_b + "," +
                Doubles.KEY_date + "," +
                Doubles.KEY_score_set_j1 + "," +
                Doubles.KEY_score_set_j2 +
                " FROM " + Doubles.TABLE;

        ArrayList<HashMap<String, String>> doubleList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> doubles = new HashMap<String, String>();
                doubles.put("id", cursor.getString(cursor.getColumnIndex(Doubles.KEY_ID)));
                doubles.put("date", cursor.getString(cursor.getColumnIndex(Doubles.KEY_date)));
                doubles.put("name1a", cursor.getString(cursor.getColumnIndex(Doubles.KEY_name_joueur_un_a)));
                doubles.put("name1b", cursor.getString(cursor.getColumnIndex(Doubles.KEY_name_joueur_un_b)));
                doubles.put("name2a", cursor.getString(cursor.getColumnIndex(Doubles.KEY_name_joueur_deux_a)));
                doubles.put("name2b", cursor.getString(cursor.getColumnIndex(Doubles.KEY_name_joueur_deux_b)));
                doubles.put("nbSetJ1", cursor.getString(cursor.getColumnIndex(Doubles.KEY_score_set_j1)));
                doubles.put("nbSetJ2", cursor.getString(cursor.getColumnIndex(Doubles.KEY_score_set_j2)));
                doubleList.add(doubles);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return doubleList;

    }

    public Doubles getSimpleById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Doubles.KEY_ID + "," +
                Doubles.KEY_name_joueur_un_a + "," +
                Doubles.KEY_name_joueur_un_b + "," +
                Doubles.KEY_name_joueur_deux_a + "," +
                Doubles.KEY_name_joueur_deux_b + "," +
                Doubles.KEY_date + "," +
                Doubles.KEY_score_set_j1 + "," +
                Doubles.KEY_score_set_j2 + "," +
                Doubles.KEY_nb_sets + "," +
                Doubles.KEY_set1_j1 + "," +
                Doubles.KEY_set1_j2 + "," +
                Doubles.KEY_set2_j1 + "," +
                Doubles.KEY_set2_j2 + "," +
                Doubles.KEY_set3_j1 + "," +
                Doubles.KEY_set3_j2 + "," +
                Doubles.KEY_set4_j1 + "," +
                Doubles.KEY_set4_j2 + "," +
                Doubles.KEY_set5_j1 + "," +
                Doubles.KEY_set5_j2 + "," +
                Doubles.KEY_set6_j1 + "," +
                Doubles.KEY_set6_j2 + "," +
                Doubles.KEY_set7_j1 + "," +
                Doubles.KEY_set7_j2 +
                " FROM " + Doubles.TABLE
                + " WHERE " +
                Doubles.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Doubles doubles = new Doubles();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                doubles.double_ID =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_ID));
                doubles.namej1a =cursor.getString(cursor.getColumnIndex(Doubles.KEY_name_joueur_un_a));
                doubles.namej1b =cursor.getString(cursor.getColumnIndex(Doubles.KEY_name_joueur_un_b));
                doubles.namej2a  =cursor.getString(cursor.getColumnIndex(Doubles.KEY_name_joueur_deux_a));
                doubles.namej2b  =cursor.getString(cursor.getColumnIndex(Doubles.KEY_name_joueur_deux_b));
                doubles.date =cursor.getString(cursor.getColumnIndex(Doubles.KEY_date));
                doubles.scoreSetj1 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_score_set_j1));
                doubles.scoreSetj2 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_score_set_j2));
                doubles.nbsets = cursor.getInt(cursor.getColumnIndex(Doubles.KEY_nb_sets));
                doubles.score_set1_j1 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set1_j1));
                doubles.score_set1_j2 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set1_j2));
                doubles.score_set2_j1 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set2_j1));
                doubles.score_set2_j2 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set2_j2));
                doubles.score_set3_j1 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set3_j1));
                doubles.score_set3_j2 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set3_j2));
                doubles.score_set4_j1 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set4_j1));
                doubles.score_set4_j2 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set4_j2));
                doubles.score_set5_j1 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set5_j1));
                doubles.score_set5_j2 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set5_j2));
                doubles.score_set6_j1 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set6_j1));
                doubles.score_set6_j2 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set6_j2));
                doubles.score_set7_j1 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set7_j1));
                doubles.score_set7_j2 =cursor.getInt(cursor.getColumnIndex(Doubles.KEY_set7_j2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return doubles;
    }
}
