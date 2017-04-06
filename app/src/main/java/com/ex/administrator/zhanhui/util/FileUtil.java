package com.ex.administrator.zhanhui.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public class FileUtil {
    //uri转换成url
    public static File getRealPathFromURI(Uri contentUri, Context context) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return new File(res);
    }

    //把bitmap转换成file对象
    public static File saveFile(Bitmap bm, String fileName) {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), fileName + ".png");
            FileOutputStream fileOutStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream); // 把位图输出到指定的文件中
            fileOutStream.flush();
            fileOutStream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
