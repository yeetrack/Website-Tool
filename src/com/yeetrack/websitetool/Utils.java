package com.yeetrack.websitetool;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.Display;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: youthflies
 * Date: 13-8-16
 * Time: 上午11:02
 * 工具类
 **/
public class Utils
{
	/**
	 * 截屏方法
	 */
	@SuppressWarnings("deprecation")
    public static Bitmap shot(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		Display display = activity.getWindowManager().getDefaultDisplay();
		view.layout(0, 0, display.getWidth(), display.getHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
		return bitmap;
	}
	/**
	 * 将截图保存到本地
	 */
	public static boolean saveShot(String imageName, Activity activity)
	{
		
		String path = Environment.getExternalStorageDirectory().getPath()+"/yeetrack/websiteTool/";
		File pathFile = new File(path);
		if(!pathFile.exists())
			pathFile.mkdirs();
		
		File file = new File(path+imageName+".png");
		FileOutputStream fileOutputStream = null;
		try
        {
			file.createNewFile();
	        fileOutputStream = new FileOutputStream(file);
	        Bitmap bitmap = shot(activity);
	        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
	        fileOutputStream.flush();
	        fileOutputStream.close();
	        return true;
        } catch (Exception e)
        {
	        e.printStackTrace();
	        return false;
        }
	}
}