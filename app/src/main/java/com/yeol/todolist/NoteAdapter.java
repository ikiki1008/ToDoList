package com.yeol.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


//RecyclerView.Adapter 뷰를 상속하고 NoteAdapter의 태그를 만들어준다
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private static final String TAG = "NoteAdapter";
    ArrayList<Note> items = new ArrayList<Note>(); //item에 들어갈 배열 정의

    static class ViewHolder extends RecyclerView.ViewHolder {

        //todo_item xml 에서 변수 가져오기
        LinearLayout layoutTodo;
        CheckBox chkTodo;
        Button btnRemove;
        public ArrayList<Note> items;

        //ViewHolder 클래스 안에서 선언하고 아이디값 찾기
        public ViewHolder(View itemView){
            super(itemView);

            layoutTodo = itemView.findViewById(R.id.layoutToDo);
            chkTodo = itemView.findViewById(R.id.chkToDo);
            btnRemove = itemView.findViewById(R.id.btnRemove);

            //삭제 버튼 클릭시 text와 같은 db값을 삭제
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String TODO = (String) chkTodo.getText();
                    removeToDo(TODO);
                    Toast.makeText(view.getContext(),"일정이 삭제 되었습니다", Toast.LENGTH_SHORT).show();
                }

                private void removeToDo(String TODO){

                }

            });

        }
        public void setItem(Note item){
            chkTodo.setText(item.getTodo());
        }

        public void setLayout(){
            layoutTodo.setVisibility(View.VISIBLE);
        }

        public void setItems(ArrayList<Note> items){
            this.items = items;
        }

        //리스트 아이템으로 만들어지는 파트가 뷰홀더이기에 각각의 버튼을 구별하기 위해서는 뷰홀더 내에서 만들때 버튼 이벤트도 같이 만들어야 한다.
        Context context;
        private void removeToDo(String TODO) {
            String deleteSql = "delete from " + NoteDatabase.TABLE_NOTE + " where " + " TODO = '" + TODO + "'";
            NoteDatabase database = NoteDatabase.getInstance(context);
            database.execSQL(deleteSql);
        }

        //임마는 뭐하는 앤지 모르겠다
        public void notifyDataSetChanged() {
        }

    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {

        Note item = items.get(position);
        holder.setItem(item);
        holder.setLayout();

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

