package com.example.user.childhoodclass;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class storyActivity extends AppCompatActivity {
    private MySQLiteOpenHelper sqlitehelper;
    private StoryAdapter storyAdapter;
    private RecyclerView recycleview;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        if(sqlitehelper==null){
            sqlitehelper=new MySQLiteOpenHelper(this);
        }

        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stintent =new Intent(storyActivity.this,insertActivity.class);
                startActivity(stintent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Story> storyList = getStoryList();
        if(storyList.size()<=0){
            Toast.makeText(this, "沒有故事", Toast.LENGTH_SHORT).show();
        }
        if(storyAdapter==null){
           storyAdapter=new StoryAdapter(this,storyList);
            recycleview.setAdapter(storyAdapter);
        }
        else {
            storyAdapter.setStoryList(storyList);
            storyAdapter.notifyDataSetChanged();
        }
    }

    public List<Story> getStoryList(){
        return sqlitehelper.getallstory();
    }

    private class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.myviewholder>{

        Context context;
        List<Story> storyList;

        void setStoryList(List<Story> storyList){
            this.storyList=storyList;
        }
        StoryAdapter(Context context,List<Story> storyList){
            this.context=context;
            this.storyList=storyList;
        }

        class myviewholder extends RecyclerView.ViewHolder{
            ImageView stimage;
            TextView sttext;
            Button stedit;
            Button stdelet;

            public myviewholder(View itemView){
                super(itemView);
                stimage =(ImageView) itemView.findViewById(R.id.stimage);
                sttext =(TextView) itemView.findViewById(R.id.sttext);
                stedit =(Button)itemView.findViewById(R.id.stedit);
                stdelet =(Button)itemView.findViewById(R.id.stdelet);
            }
        }

        @Override
        public void onBindViewHolder( myviewholder holder, int position) {
           final Story story=storyList.get(position);
            if(story.getImage()==null) {
                holder.stimage.setImageResource(R.drawable.button);
            }
            else{
                Bitmap bitmap = BitmapFactory.decodeByteArray(story.getImage(),0,story.getImage().length);
                holder.stimage.setImageBitmap(bitmap);
            }
            holder.sttext.setText(story.getContent());

            holder.stedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(context,EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID",story.getId());
                intent.putExtras(bundle);
                startActivity(intent);
                }
            });

            holder.stdelet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(storyActivity.this);
                    builder.setTitle("刪除");
                    builder.setMessage("確定要刪除?");
                    builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int count = sqlitehelper.deletebyid(story.getId());
                            storyList =sqlitehelper.getallstory();
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    builder.show();
                }
            });
        }

       // @NonNull
        @Override
        public myviewholder onCreateViewHolder( ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater =LayoutInflater.from(context);
            View itemView = layoutInflater.inflate(R.layout.storyitem,parent,false);
            return new myviewholder(itemView);
        }

        @Override
        public int getItemCount() {
            return storyList.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.storymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addst:
                Intent stintent =new Intent(this,insertActivity.class);
               startActivity(stintent);
               break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sqlitehelper!=null){
            sqlitehelper.close();
        }
    }

}
