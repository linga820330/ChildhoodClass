package com.example.user.childhoodclass;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditActivity extends AppCompatActivity {
    private ImageButton edimage;
    private EditText edtext;
    private MySQLiteOpenHelper sqliteHelper;
    private Story story;
    private static final int  EDITRESULT =2;
    private byte[] image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        if(sqliteHelper==null){
         sqliteHelper=new MySQLiteOpenHelper(this);
        }
        findView();
    }

    public void findView() {
        edimage = (ImageButton) findViewById(R.id.edimage);
        edtext = (EditText) findViewById(R.id.edtext);
        Bundle bundle = this.getIntent().getExtras();
        int id = bundle.getInt("ID");
        //  int id = getIntent().getExtras().getInt("ID");
        //   String string = String.valueOf(id);
        //   Toast.makeText(this, string,Toast.LENGTH_LONG).show();
        story = sqliteHelper.findbyid(id);
        Bitmap bitmap = BitmapFactory.decodeByteArray(story.getImage(), 0, story.getImage().length);
        edimage.setImageBitmap(bitmap);
        edtext.setText(story.getContent());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        image=baos.toByteArray();

        edimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                getAlbum.setType("image/*");
                startActivityForResult(getAlbum, EDITRESULT);
            }
        });

    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            super.onActivityResult(requestCode, resultCode, intent);
            if (resultCode != RESULT_OK) { //此處的 RESULT_OK 是系統自定義得一個常量

                Log.e("TAG->onresult","ActivityResult resultCode error");
                return;
            }
            Bitmap bm = null;
//外界的程式訪問ContentProvider所提供數據 可以通過ContentResolver介面
            ContentResolver resolver = getContentResolver();
            int newsize=650;
//此處的用於判斷接收的Activity是不是你想要的那個
            if (requestCode == EDITRESULT) {
                try {
                    Uri originalUri = intent.getData(); //獲得圖片的uri

                    bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                    Bitmap downsizedimage = Common.downSize(bm, newsize);
//顯得到bitmap圖片
                    edimage.setImageBitmap(downsizedimage);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    downsizedimage.compress(Bitmap.CompressFormat.PNG,100,baos);
                    image=baos.toByteArray();


// 這裏開始的第二部分，獲取圖片的路徑：
                    String[] proj = {MediaStore.Images.Media.DATA};

//好像是android多媒體數據庫的封裝介面，具體的看Android文檔
                    Cursor cursor = managedQuery(originalUri, proj, null, null, null);
//按我個人理解 這個是獲得用戶選擇的圖片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//將光標移至開頭 ，這個很重要，不小心很容易引起越界
                    cursor.moveToFirst();
//最後根據索引值獲取圖片路徑
                    String path = cursor.getString(column_index);
                    //.setText(path);
                } catch (IOException e) {
                    Log.e("TAG-->Error", e.toString());
                }
                // imageViev.setImageBitmap(srcimage);
            }
        }


public void editclick(View view){
        story.setImage(image);
        story.setContent(edtext.getText().toString());
        sqliteHelper.update(story);
        finish();
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(sqliteHelper!=null){
            sqliteHelper.close();
        }
    }
}
