/**
 * 
 */
package com.yeetrack.websitetool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * @author xuemeng
 *
 */
public class AboutActivity extends Activity
{

	private ImageButton backButton;
	private ImageButton saveButton;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_websiteabout);
	    
	    backButton = (ImageButton)findViewById(R.id.aboutBackButton);
	    saveButton = (ImageButton)findViewById(R.id.aboutSaveButton);
	    
	  //定义按钮监听匿名类
        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    //点击了后退按钮
                    case R.id.aboutBackButton:
                        AboutActivity.this.finish();
                        break;
                    //点击了保存按钮
                    case R.id.aboutSaveButton:
                    	if(Utils.saveShot(System.currentTimeMillis()+"-spider", AboutActivity.this))
                    		Toast.makeText(AboutActivity.this, "截图保存成功", Toast.LENGTH_SHORT).show();
                    	else 
                    		Toast.makeText(AboutActivity.this, "截图失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        backButton.setOnClickListener(onClickListener);
        saveButton.setOnClickListener(onClickListener);
	    
    }
	
}
