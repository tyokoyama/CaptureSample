package com.example.capturesample;

import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	File destination;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // フルサイズ写真の出力先（外部ストレージへ）
        destination = new File(Environment.getExternalStorageDirectory(), "image.jpg");
        
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
				startActivityForResult(intent, 1);
			}
		});
    }


    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
    		Bitmap bitmap = null;
    		Matrix matrix = new Matrix();
    		matrix.postRotate(90.0f);
    		try {
    			FileInputStream in = new FileInputStream(destination);
    			BitmapFactory.Options options = new BitmapFactory.Options();
    			options.inSampleSize = 10;
    			
    			bitmap = BitmapFactory.decodeStream(in, null, options);
    			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    			
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
//    		Bitmap bitmap = (Bitmap)data.getExtras().get("data");
    		ImageView iv = (ImageView)findViewById(R.id.imageView1);
    		iv.setImageBitmap(bitmap);
    	}
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
