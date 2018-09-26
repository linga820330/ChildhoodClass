package com.example.user.childhoodclass;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

public class HomeActivity extends AppCompatActivity {
private FirebaseAnalytics mFirebaseAnalytics;
    private DrawerLayout drawerLayout;
   // private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mFirebaseAnalytics =FirebaseAnalytics.getInstance(this);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
    //navigationView=(NavigationView)findViewById(R.id.navigation_view);
    toolbar=(Toolbar)findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

   // navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
    //    @Override
     //   public boolean onNavigationItemSelected(@NonNull MenuItem item) {
     //       drawerLayout.closeDrawer(GravityCompat.START);
     //       int Nid=item.getItemId();
     //       if (Nid==R.id.member) {
     //           Toast.makeText(HomeActivity.this, "小朋友圖鑑", Toast.LENGTH_SHORT).show();
     //           return true;
      //      }
      //      else if(Nid==R.id.drstory){
      //          Intent stintent =new Intent(HomeActivity.this,storyActivity.class);
      //          startActivity(stintent);
       //         return true;
      //      }
      //      else if (Nid==R.id.drkb){
      //          Intent kbintent=new Intent(HomeActivity.this,kbActivity.class);
       //         startActivity(kbintent);
       //         return true;
      //      }
      //      return false;
      //  }
   // });

      //  ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
      //          drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
      //  drawerLayout.addDrawerListener(actionBarDrawerToggle);
      //  actionBarDrawerToggle.syncState();
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

            case R.id.member:
                Intent mbintent = new Intent(this,MainActivity.class);
                startActivity(mbintent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
