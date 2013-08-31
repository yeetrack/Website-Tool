package com.yeetrack.websitetool;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.*;

public class MainActivity extends Activity
{
    String currentPath;
	/**
	 * 域名输入框
	 */
	private EditText domainEditText;
	
	/**
	 * 综合查询按钮
	 */
	private Button integratedButton;
	
	/**
	 * 友情链接按钮
	 */
	private Button friendLinkButton;
	
	/**
	 * 网站测速按钮
	 */
	private Button websiteFastButton;
	
	/**
	 * Ping检测按钮
	 */
	private Button pingButton;
	
	/**
	 * 路由追踪按钮
	 */
	private Button routeButton;
	
	/**
	 * DNS检测按钮
	 */
	private Button dnsButton;
	
	/**
	 * 页面检测按钮
	 */
	private Button pageButton;
	
	/**
	 * PR值检测按钮
	 */
	private Button prButton;
	
	/**
	 * 死链检测按钮
	 */
	private Button deadLinkButton;
	
	/**
	 * Alexa排名按钮
	 */
	private Button alexaButton;
	
	/**
	 * 综合查询按钮第二个
	 */
	private Button integrated2Button;
	
	/**
	 * 蜘蛛模拟按钮
	 */
	private Button spiderButton;
	
	/**
	 * whois 按钮
	 */
	private Button whoisButton;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        currentPath = getApplicationContext().getFilesDir().getAbsolutePath();
		domainEditText = (EditText)findViewById(R.id.domainInput);
        //默认读取files目录下的domain.txt文件中的域名
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(currentPath+"/domain.txt");
            BufferedReader br = new BufferedReader(fileReader);
            String  name = br.readLine();
            domainEditText.setText(name);
            fileReader.close();
        } catch (FileNotFoundException e) {
            //没有该文件，使用默认的yeetrack.com
        } catch (IOException e) {
        }
        integratedButton = (Button)findViewById(R.id.domainSubmitButton);
		friendLinkButton = (Button)findViewById(R.id.friendLinkBtn);
		websiteFastButton = (Button)findViewById(R.id.websiteFastBtn);
		pingButton = (Button)findViewById(R.id.websitePingBtn);
		routeButton = (Button)findViewById(R.id.routeBtn);
		dnsButton = (Button)findViewById(R.id.dnsBtn);
		pageButton = (Button)findViewById(R.id.pageBtn);
		prButton = (Button)findViewById(R.id.prBtn);
		deadLinkButton = (Button)findViewById(R.id.deadLinkBtn);
		alexaButton = (Button)findViewById(R.id.alexaBtn);
		integrated2Button = (Button)findViewById(R.id.visitBtn);
		spiderButton = (Button)findViewById(R.id.spiderBtn);
		whoisButton = (Button)findViewById(R.id.whoisBtn);
		
		Toast.makeText(this, "注意本工具直接从互联网抓取数据，可能会消耗大量流量，尽量在wifi环境下使用", Toast.LENGTH_LONG).show();		
		//为输入框添加点击监听，点击后清空默认
		domainEditText.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				if("yeetrack.com".equals(domainEditText.getText().toString()))
					domainEditText.setText("");
			}
		});
		
		//为综合查询按钮添加监听
		integratedButton.setOnClickListener( new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				// 需要启动一个页面，来展示结果
				if(checkDomain())
				{
                    //将域名写入文件
                    writeDomain2Disk();

					Bundle detailBundle = new Bundle();
					String domainString = domainEditText.getText().toString();
					detailBundle.putString("domain", domainString);
					Intent detailIntent = new Intent(MainActivity.this, DomainDetailActivity.class);
					detailIntent.putExtra("data", detailBundle);
					startActivity(detailIntent);
				}
			}
		});
		
		/**
		 * 为友情链接按钮，添加监听
		 */
		friendLinkButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(checkDomain())
				{
					Bundle detailBundle = new Bundle();
					String domainString = domainEditText.getText().toString();
					detailBundle.putString("domain", domainString);
					Intent detailIntent = new Intent(MainActivity.this, FriendLinkActivity.class);
					detailIntent.putExtra("data", detailBundle);
					startActivity(detailIntent);
				}
			}
		});
		
		/**
		 * 为网站测速按钮，添加监听
		 */
		websiteFastButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(checkDomain())
				{
					Bundle detailBundle = new Bundle();
					String domainString = domainEditText.getText().toString();
					detailBundle.putString("domain", domainString);
					Intent detailIntent = new Intent(MainActivity.this, SpeedActivity.class);
					detailIntent.putExtra("data", detailBundle);
					startActivity(detailIntent);
				}
			}
		});
		
		/**
		 * 为Ping检测按钮，添加监听
		 */
		pingButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(checkDomain())
				{
					Bundle detailBundle = new Bundle();
					String domainString = domainEditText.getText().toString();
					detailBundle.putString("domain", domainString);
					Intent detailIntent = new Intent(MainActivity.this, PingActivity.class);
					detailIntent.putExtra("data", detailBundle);
					startActivity(detailIntent);
				}
			}
		});
		
		/**
		 * 为路由追踪按钮，添加监听
		 */
		routeButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(checkDomain())
				{
					Bundle detailBundle = new Bundle();
					String domainString = domainEditText.getText().toString();
					detailBundle.putString("domain", domainString);
					Intent detailIntent = new Intent(MainActivity.this, RouteActivity.class);
					detailIntent.putExtra("data", detailBundle);
					startActivity(detailIntent);
				}
			}
		});
		
		/**
		 * 为DNS检测按钮，添加监听
		 */
		dnsButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(checkDomain())
				{
					Bundle detailBundle = new Bundle();
					String domainString = domainEditText.getText().toString();
					detailBundle.putString("domain", domainString);
					Intent detailIntent = new Intent(MainActivity.this, DNSActivity.class);
					detailIntent.putExtra("data", detailBundle);
					startActivity(detailIntent);
				}
			}
		});
		
		/**
		 * 为页面监测按钮，添加监听
		 */
		pageButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(checkDomain())
				{
					Bundle detailBundle = new Bundle();
					String domainString = domainEditText.getText().toString();
					detailBundle.putString("domain", domainString);
					Intent detailIntent = new Intent(MainActivity.this, PageActivity.class);
					detailIntent.putExtra("data", detailBundle);
					startActivity(detailIntent);
				}
			}
		});
		
		/**
		 * 为PR值查询按钮，添加监听
		 */
		prButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(checkDomain())
				{
					Bundle detailBundle = new Bundle();
					String domainString = domainEditText.getText().toString();
					detailBundle.putString("domain", domainString);
					Intent detailIntent = new Intent(MainActivity.this, GooglePageActivity.class);
					detailIntent.putExtra("data", detailBundle);
					startActivity(detailIntent);
				}
			}
		});
		
		/**
		 * 为死链检测按钮，添加监听
		 */
		deadLinkButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(checkDomain())
				{
					Bundle detailBundle = new Bundle();
					String domainString = domainEditText.getText().toString();
					detailBundle.putString("domain", domainString);
					Intent detailIntent = new Intent(MainActivity.this, DeadLinkActivity.class);
					detailIntent.putExtra("data", detailBundle);
					startActivity(detailIntent);
				}
			}
		});
		
		/**
		 * 为Alexa排名按钮，添加监听
		 */
		alexaButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(checkDomain())
				{
					Bundle detailBundle = new Bundle();
					String domainString = domainEditText.getText().toString();
					detailBundle.putString("domain", domainString);
					Intent detailIntent = new Intent(MainActivity.this, AlexaActivity.class);
					detailIntent.putExtra("data", detailBundle);
					startActivity(detailIntent);
				}
			}
		});
		
		/**
		 * 为蜘蛛模拟按钮，添加监听
		 */
		spiderButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(checkDomain())
				{
					Bundle detailBundle = new Bundle();
					String domainString = domainEditText.getText().toString();
					detailBundle.putString("domain", domainString);
					Intent detailIntent = new Intent(MainActivity.this, SpiderActivity.class);
					detailIntent.putExtra("data", detailBundle);
					startActivity(detailIntent);
				}
			}
		});
		
		/**
		 * 为Whois查询按钮，添加监听
		 */
		whoisButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(checkDomain())
				{
					Bundle detailBundle = new Bundle();
					String domainString = domainEditText.getText().toString();
					detailBundle.putString("domain", domainString);
					Intent detailIntent = new Intent(MainActivity.this, WhoisActivity.class);
					detailIntent.putExtra("data", detailBundle);
					startActivity(detailIntent);
				}
			}
		});
		
		//为综合查询按钮添加监听
		integrated2Button.setOnClickListener( new OnClickListener()
				{
					
					@Override
					public void onClick(View arg0)
					{
						// 需要启动一个页面，来展示结果
						if(checkDomain())
						{
							Intent intent = new Intent();  
				            intent.setAction(Intent.ACTION_VIEW);  
				            intent.addCategory(Intent.CATEGORY_BROWSABLE);  
				            intent.setData(Uri.parse("http://"+domainEditText.getText().toString()));  
				            startActivity(intent);  
						}
					}
				});

	}

    /**
     * 将用户搜索的域名保存到本地，下次方便从本地读取
     */
    void writeDomain2Disk()
    {
        try {
            FileWriter fileWriter = new FileWriter(currentPath+"/domain.txt");
            fileWriter.write(domainEditText.getText().toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
		switch (item.getItemId())
        {		
		
		case R.id.action_quit:
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
		case R.id.action_about:
			Intent intent = new Intent(MainActivity.this, AboutActivity.class);
			startActivity(intent);
			break;
		default:
			break;
        }
	    return super.onOptionsItemSelected(item);
	    
    }

	/**
	 * 检测域名输入是否合法
	 */
	public boolean checkDomain()
	{
		String domain = domainEditText.getText().toString();
		Toast toast = Toast.makeText(this, "提示", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		if(domain == null || "".equals(domain))
		{
			toast.setText("域名不能为空");
			toast.show();
			return false;
		}
		else if(! domain.matches("(([a-z0-9](w|-){0,61}?[a-z0-9]|[a-z0-9]).){1,}(aero|arpa|asia|biz|cat|com|coop|co|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])(.[a-z][a-z]){0,1}"))
		{
			toast.setText("域名格式有误");
			toast.show();
			return false;
		}
		return true;		
	}

}
