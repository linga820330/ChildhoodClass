package com.example.user.childhoodclass;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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

public class insertActivity extends AppCompatActivity {

    private ImageButton inimage;
    private EditText intext;
    private MySQLiteOpenHelper sqliteHelper;
    private static final int INSERT_ADD=1;
    private byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        if(sqliteHelper==null){
            sqliteHelper = new MySQLiteOpenHelper(this);
        }
        findViews();
    }

    public void findViews(){
        inimage=(ImageButton)findViewById(R.id.edimage);
        inimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);

                getAlbum.setType("image/*");

                startActivityForResult(getAlbum, INSERT_ADD);
            }
        });
        intext=(EditText) findViewById(R.id.intext);
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
        if (requestCode == INSERT_ADD) {
            try {
                Uri originalUri = intent.getData(); //獲得圖片的uri

                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                Bitmap downsizedimage = Common.downSize(bm, newsize);
//顯得到bitmap圖片
                inimage.setImageBitmap(downsizedimage);
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

    public void insertclick(View view){
        String content = intext.getText().toString().trim();
        if(content.length()<=0){
            Toast.makeText(this,"請輸入故事內容",Toast.LENGTH_LONG).show();
        return;
        }

        Story story = new Story(content,image);
        long rowID = sqliteHelper.insert(story);
        if(rowID!=-1){
            Toast.makeText(this,"新增成功",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"新增失敗",Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(sqliteHelper!=null)
        sqliteHelper.close();
    }
}
