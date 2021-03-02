package org.techtown.mydatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMovie extends AppCompatActivity {

    private DBHelper mydb;
    TextView name;
    TextView director;
    TextView year;
    TextView nation;
    TextView rating;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);
        name = (TextView)findViewById(R.id.edittext_name);
        director = (TextView)findViewById(R.id.edittext_directer);
        year = (TextView)findViewById(R.id.edittext_year);
        nation = (TextView)findViewById(R.id.edittext_nation);
        rating = (TextView)findViewById(R.id.edittext_score);

        mydb = new DBHelper(this);

        //데이터는 인텐트의 번들 안에 있다
        Bundle extras = getIntent().getExtras();
        //인텐트로부터 저장된 값을 불러와 Bundle에 입력한다
        if (extras != null){
            int Value = extras.getInt("id");
            if (Value > 0) {
                Cursor rs = mydb.getData(Value);
                id = Value;
                rs.moveToFirst(); //cursor를 첫 번째 행으로 이동, 현재 cursor가 위치한 행의 값들을 다룬다
                //해당하는 rs.getColumnIndex 컬럼에 있는 데이터를 가져온다.
                String n = rs.getString(rs.getColumnIndex(DBHelper.MOVIES_COLUMN_NAME));
                String d = rs.getString(rs.getColumnIndex(DBHelper.MOVIES_COLUMN_DIRECTOR));
                String y = rs.getString(rs.getColumnIndex(DBHelper.MOVIES_COLUMN_YEAR));
                String na = rs.getString(rs.getColumnIndex(DBHelper.MOVIES_COLUMN_NATION));
                String r = rs.getString(rs.getColumnIndex(DBHelper.MOVIES_COLUMN_RATING));

                if(!rs.isClosed()){
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.save);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence) n);
                director.setText((CharSequence) d);
                year.setText((CharSequence) y);
                nation.setText((CharSequence) na);
                rating.setText((CharSequence) r);
            }
        }
    }

    public void saveClick(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                //아이템이 존재할 경우 업데이트
                //DBHelper의 함수를 불러온다
                if (mydb.updateMovie(id, name.getText().toString(), director.getText().toString(), year.getText().toString(), nation.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "업데이트 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "업데이트 실패", Toast.LENGTH_SHORT).show();
                }
            } else {
                //처음 생성할 경우
                if (mydb.insertMovie(name.getText().toString(), director.getText().toString(), year.getText().toString(), nation.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "INSERT", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "NOT INSERT", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    }

    public void updateClick(View view) { //DBHelper의 updatemovie 함수
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("id");
            if (value > 0) {
                if (mydb.updateMovie(id, name.getText().toString(), director.getText().toString(), year.getText().toString(), nation.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "UPDATE", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "NOT UPDATE", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void deleteClick(View view) { //DBHelper의 deletemovie 함수
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                mydb.deleteMovie(id);
                Toast.makeText(getApplicationContext(), "DELETE", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "NOT DELETE", Toast.LENGTH_SHORT).show();
            }
        }
    }
}