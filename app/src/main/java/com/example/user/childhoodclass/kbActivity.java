package com.example.user.childhoodclass;
import java.io.IOException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.util.Log;

public class kbActivity extends AppCompatActivity {
   private ImageView imageViev;
private static final int REQUEST_ADD=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kb);
        imageViev =(ImageView) findViewById(R.id.imageView);
    }
public void add(View view){
  // Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
   // startActivityForResult(intent,REQUEST_ADD);
    Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);

    getAlbum.setType("image/*");

    startActivityForResult(getAlbum, REQUEST_ADD);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
       // if (resultCode==RESULT_OK && requestCode==REQUEST_ADD){
           // int newsize=512;
           // Uri uri =intent.getData();
           //String[] columns= {MediaStore.Images.Media.DATA};
            //Cursor cursor = getContentResolver().query(uri,columns,null,null,null);


          // if(cursor != null ){
               //cursor.moveToFirst();
              // String imagepath = cursor.getString(0);


               // cursor.close();
               // Bitmap srcimage = BitmapFactory.decodeFile(imagepath);
               // Bitmap downsizedimage = Common.downSize(srcimage,newsize);
                //Matrix matrix = new Matrix();

                //int width = srcimage.getWidth();
                //int height = srcimage.getHeight();

//想要的大小
                //int newWidth = 360;
                //int newHeight = 360;

//計算比例
                //float scaleWidth = ((float)newWidth) / width;
                //float scaleHeight = ((float) newHeight) / height;

// 設定 Matrix 物件，設定 x,y 向的縮放比例
                //matrix.postScale(scaleWidth, scaleHeight);
                //Bitmap newsizebitmap = Bitmap.createBitmap(srcimage,0,0,width,height,matrix,true);

        if (resultCode != RESULT_OK) { //此處的 RESULT_OK 是系統自定義得一個常量

            Log.e("TAG->onresult","ActivityResult resultCode error");
            return;
        }
        Bitmap bm = null;
//外界的程式訪問ContentProvider所提供數據 可以通過ContentResolver介面
        ContentResolver resolver = getContentResolver();
        int newsize=512;
//此處的用於判斷接收的Activity是不是你想要的那個
        if (requestCode == REQUEST_ADD) {
            try {
                Uri originalUri = intent.getData(); //獲得圖片的uri

                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                Bitmap downsizedimage = Common.downSize(bm,newsize);
//顯得到bitmap圖片
                imageViev.setImageBitmap(downsizedimage);
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
            }catch (IOException e) {
                Log.e("TAG-->Error",e.toString());
            }
               // imageViev.setImageBitmap(srcimage);
            }
        }

    public void delet(View view){
        Bitmap nullbitmap=null;
        imageViev.setImageBitmap(nullbitmap);
    }
}
