package com.mailapp.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;


import com.mailapp.R;
import com.mailapp.sourceop.HttpStreamOP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ImageCacheOP {
    private Context context;

    public ImageCacheOP(Context context) {
        this.context = context;
    }

    public Bitmap getBitmapFromURL(String path){
        InputStream in = null;
        File file = new File(context.getCacheDir(), Base64.encodeToString(path.getBytes(), Base64.DEFAULT));
        if(file.exists()&&file.length()>0){
            System.out.println("----------------cache----------------");
        }else{
            try {
                in = new HttpStreamOP().getInputStream(path);
                if(in!=null){
                    writeImageToCache(in,file);
                }else{
                    return BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round);
                }
                System.out.println("------------------server-------------------");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }
    private void writeImageToCache(InputStream in, File file) {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            int len = -1;
            byte[] buffer = new byte[1024];
            while((len = in.read(buffer))!=-1){

                fos.write(buffer,0,len);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
