package org.techtown.mydatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyMovies.db";
    public static final String MOVIES_TABLE_NAME = "movies";
    public static final String MOVIES_COLUMN_ID = "id";
    public static final String MOVIES_COLUMN_NAME = "name";
    public static final String MOVIES_COLUMN_DIRECTOR = "director";
    public static final String MOVIES_COLUMN_YEAR = "year";
    public static final String MOVIES_COLUMN_NATION = "nation";
    public static final String MOVIES_COLUMN_RATING = "rating";

    //생성자, DB 이름과 버전 정보를 받는다
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //DB 생성할 때 호출
    @Override
    public void onCreate(SQLiteDatabase db) {
        //테이블 생성
        //name, director, year, nation, rating 애트리뷰트로 구성된 테이블
        db.execSQL("create table movies" + "(id integer primary key, name text, director text, year text, nation text, rating text)"
        );
    }

    //버전이 업그레이드 될 때
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int vewVersion) {
        db.execSQL("DROP TABLE IF EXISTS movies");
        //기존의 테이블 삭제 후 다시 생성한다.
        onCreate(db);
    }

    public boolean insertMovie(String name, String director, String year, String nation, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        //읽기,쓰기 모드가 가능한 데이터베이스 오픈
        ContentValues contentValues = new ContentValues();
        //입력한 데이터값을 불러와 contentValue 객체에 입력한다.

        contentValues.put("name", name);
        contentValues.put("director", director);
        contentValues.put("year", year);
        contentValues.put("nation", nation);
        contentValues.put("rating", rating);

        db.insert("movies", null, contentValues);
        //insert() 메소드를 이용하여 데이터를 데이터베이스에 삽입한다.
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movies where id=" + id + "", null);
        return res;
    }

    public boolean updateMovie(Integer id, String name, String director, String year, String nation, String rating){
        //데이터 값이 변경될 때 호출
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("director", director);
        contentValues.put("year", year);
        contentValues.put("nation", nation);
        contentValues.put("rating", rating);
        db.update("movies", contentValues, "id = ?", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteMovie(Integer id){
        //데이터 값이 삭제될 때 호출
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("movies", "id = ?", new String[]{Integer.toString(id)});
    }

    public ArrayList getAllMovies() {
        //ArrayList를 만들어서 Query로 데이터베이스에 저장된 모든 항목을 불러오는 함수
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movies", null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(MOVIES_COLUMN_ID))+" "+res.getString(res.getColumnIndex(MOVIES_COLUMN_NAME)));
            res.moveToNext();
       }
        return array_list;
    }
}
