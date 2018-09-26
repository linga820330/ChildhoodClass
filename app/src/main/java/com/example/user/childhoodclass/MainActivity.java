package com.example.user.childhoodclass;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  {
    private String[] id = {"一年級", "三年級", "三年級", "一年級", "三年級", "二年級", "三年級", "一年級", "一年級", "一年級", "一年級"};
    private String[] name = {"黃米希", "廖盈睿", "林睫霓", "黃實至", "黃誠恩", "劉妍娜", "任謙", "蕭晉邦", "張亮晟", "黃喬駿", "楊颢"};
    private int[] image = {R.drawable.p01, R.drawable.p02, R.drawable.p03, R.drawable.p04, R.drawable.p05, R.drawable.p06, R.drawable.p07, R.drawable.p08, R.drawable.p09, R.drawable.p10, R.drawable.p11};
    private DrawerLayout drawerLayout;
   // private NavigationView navigation_view;
    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gvmember = (GridView) findViewById(R.id.gvmember);
        Memberadapter memberadapter = new Memberadapter();
        gvmember.setAdapter(memberadapter);
        gvmember.setNumColumns(2);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
       // navigation_view=(NavigationView)findViewById(R.id.navigation_view);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

      //  navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      //      @Override
       //     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
     //           drawerLayout.closeDrawer(GravityCompat.START);
       //         int Nid=item.getItemId();
      //          if (Nid==R.id.member) {
      //              Toast.makeText(MainActivity.this, "小朋友圖鑑", Toast.LENGTH_SHORT).show();
     //               return true;
       //         }
      //          else if(Nid==R.id.drstory){
      //              Intent stintent =new Intent(MainActivity.this,storyActivity.class);
     //               startActivity(stintent);
      //              return true;
     //           }
     //           else if (Nid==R.id.drkb){
       //             Intent kbintent=new Intent(MainActivity.this,kbActivity.class);
     //               startActivity(kbintent);
      //              return true;
      //          }

     //           return false;
      //      }
    //    });

        setSupportActionBar(toolbar);
     //   ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
     //           drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
     //   drawerLayout.addDrawerListener(actionBarDrawerToggle);
     //   actionBarDrawerToggle.syncState();


    }



    private class Memberadapter extends BaseAdapter{


        @Override
        public int getCount() {
            return image.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                LayoutInflater layoutInflater= LayoutInflater.from(getApplicationContext());
                convertView = layoutInflater.inflate(R.layout.viewitem,parent,false);
            }
        TextView ivid=(TextView) convertView.findViewById(R.id.ivid);
            ivid.setText(id[position]);

            ImageView ivimage=(ImageView) convertView.findViewById(R.id.ivimage);
            ivimage.setImageResource(image[position]);

            TextView ivname =(TextView)convertView.findViewById(R.id.ivname);
            ivname.setText(name[position]);


            return convertView;
        }
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.kb:
                Intent kbintent = new Intent(this,kbActivity.class);
               startActivity(kbintent);
                break;
            case R.id.story:
                Intent stintent =new Intent(this,storyActivity.class);
                startActivity(stintent);
               break;
       }

        return super.onOptionsItemSelected(item);
    }
}