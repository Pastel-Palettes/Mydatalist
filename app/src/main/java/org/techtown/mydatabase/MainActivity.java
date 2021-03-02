package org.techtown.mydatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ListView myListView;
    DBHelper mydb;
    ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllMovies();
        //모든 항목의 이름이 담겨있는 ArrayList

        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        //mAdapter에 담아서 리스트뷰에 전달
        myListView = (ListView)findViewById(R.id.listView1);
        myListView.setAdapter(mAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //어떤 아이템이 클릭됐을 때 정보를 가져오는 함수
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg4) {
                String item = (String)((ListView)parent).getItemAtPosition(position);
                String[] strArray = item.split(" ");
                int id = Integer.parseInt(strArray[0]);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id); //클릭한 정보의 id 정보
                Intent intent = new Intent(getApplicationContext(), DisplayMovie.class);
                intent.putExtras(dataBundle); //액티비티 간의 데이터 전송
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clear();
        mAdapter.addAll(mydb.getAllMovies());
        mAdapter.notifyDataSetChanged();
        //데이터의 추가, 변경이 되었을 때 리스트뷰를 업데이트
    }

    public void onClick(View view) {
        //추가하기 버튼을 누르면 DisplayMovie 액티비티로 전환
        Bundle bundle = new Bundle();
        bundle.putInt("id", 0);
        Intent intent = new Intent(getApplicationContext(), DisplayMovie.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}