package albert.miguel.tennisdetable.BaseDeDonnees;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper  extends SQLiteOpenHelper {
        //version number to upgrade database version
        //each time if you Add, Edit table, you need to change the
        //version number.
        private static final int DATABASE_VERSION = 1;

        // Database Name
        private static final String DATABASE_NAME = "scoret2t.db";

        public DBHelper(Context context ) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //All necessary tables you like to create will create here

            String CREATE_TABLE_SIMPLES = "CREATE TABLE " + Simples.TABLE  + "("
                    + Simples.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + Simples.KEY_name_joueur_un + " TEXT, "
                    + Simples.KEY_name_joueur_deux + " TEXT, "
                    + Simples.KEY_date + " TEXT, "
                    + Simples.KEY_nb_sets + " INTEGER, "
                    + Simples.KEY_score_set_j1 + " INTEGER, "
                    + Simples.KEY_score_set_j2 + " INTEGER, "
                    + Simples.KEY_set1_j1 + " INTEGER, "
                    + Simples.KEY_set1_j2 + " INTEGER, "
                    + Simples.KEY_set2_j1 + " INTEGER, "
                    + Simples.KEY_set2_j2 + " INTEGER, "
                    + Simples.KEY_set3_j1 + " INTEGER, "
                    + Simples.KEY_set3_j2 + " INTEGER, "
                    + Simples.KEY_set4_j1 + " INTEGER, "
                    + Simples.KEY_set4_j2 + " INTEGER, "
                    + Simples.KEY_set5_j1 + " INTEGER, "
                    + Simples.KEY_set5_j2 + " INTEGER, "
                    + Simples.KEY_set6_j1 + " INTEGER, "
                    + Simples.KEY_set6_j2 + " INTEGER, "
                    + Simples.KEY_set7_j1 + " INTEGER, "
                    + Simples.KEY_set7_j2 + " INTEGER )";


            String CREATE_TABLE_DOUBLES = "CREATE TABLE " + Doubles.TABLE  + "("
                    + Doubles.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + Doubles.KEY_name_joueur_un_a + " TEXT, "
                    + Doubles.KEY_name_joueur_un_b + " TEXT, "
                    + Doubles.KEY_name_joueur_deux_a + " TEXT, "
                    + Doubles.KEY_name_joueur_deux_b + " TEXT, "
                    + Doubles.KEY_date + " TEXT, "
                    + Doubles.KEY_nb_sets + " INTEGER, "
                    + Doubles.KEY_score_set_j1 + " INTEGER, "
                    + Doubles.KEY_score_set_j2 + " INTEGER, "
                    + Doubles.KEY_set1_j1 + " INTEGER, "
                    + Doubles.KEY_set1_j2 + " INTEGER, "
                    + Doubles.KEY_set2_j1 + " INTEGER, "
                    + Doubles.KEY_set2_j2 + " INTEGER, "
                    + Doubles.KEY_set3_j1 + " INTEGER, "
                    + Doubles.KEY_set3_j2 + " INTEGER, "
                    + Doubles.KEY_set4_j1 + " INTEGER, "
                    + Doubles.KEY_set4_j2 + " INTEGER, "
                    + Doubles.KEY_set5_j1 + " INTEGER, "
                    + Doubles.KEY_set5_j2 + " INTEGER, "
                    + Doubles.KEY_set6_j1 + " INTEGER, "
                    + Doubles.KEY_set6_j2 + " INTEGER, "
                    + Doubles.KEY_set7_j1 + " INTEGER, "
                    + Doubles.KEY_set7_j2 + " INTEGER )";

            db.execSQL(CREATE_TABLE_DOUBLES);
            db.execSQL(CREATE_TABLE_SIMPLES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed, all data will be gone!!!
            db.execSQL("DROP TABLE IF EXISTS " + Simples.TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + Doubles.TABLE);

            // Create tables again
            onCreate(db);
        }

}
