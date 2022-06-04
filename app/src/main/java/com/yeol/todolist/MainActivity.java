package com.yeol.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";//오류났을때 쉽게 디버깅 할수있도록 다 붙여주자
    Fragment MainFragment;
    Button btnAdd;
    private View inputToDo;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment).commit();

        //findViewByid를 onCreate 안에 써야한다.. 이거땜에 앱 실행안된거였음
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTodo();
                Toast.makeText(getApplicationContext(),"일정이 추가 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //NoteDatabase에서 정의한 open close 활용
    //아래코드로 noteDb 초기화 시켜줌
    public static NoteDatabase noteDatabase = null;

    public void openDatabase() {

        if(noteDatabase != null){
            noteDatabase.close();
            noteDatabase = null;
        }

        noteDatabase = NoteDatabase.getInstance(this);
        boolean isOpen = noteDatabase.open();
        if (isOpen){
            Log.d(TAG, "Note database is open.");
        }
        else {
            Log.d(TAG, "Note database is not open.");
        }
    }

    protected void onDestroy() {
        super.onDestroy();

        if(noteDatabase != null) {
            noteDatabase.close();
            noteDatabase = null;
        }
    }

    //EditText에 적힌 글을 String_todo로 가져와 테이블에 값을 추가하는 sql문을 만들고 execSQL을 통해 실행해줌
    private void saveTodo(){

        inputToDo = findViewById(R.id.inputText);

        String todo = inputToDo.getText().toString();

        String sqlSave = "insert into" + NoteDatabase.TABLE_NOTE + " (TODO) values (" + "'" + todo + "')";

        NoteDatabase database = NoteDatabase.getInstance(context);
        database.execSQL((sqlSave));

        inputToDo.setText("");

    }

}
