/**
 * 
 */
package com.yeetrack.websitetool;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author youthflies
 * 自定义listview适配器
 */
public class MyAdapter extends BaseAdapter
{
	private List<Map<String, Object>> dataList;
	
	private LayoutInflater layoutInflater;
	private Context context;
	
	public MyAdapter(Context context, List<Map<String, Object>> dataList)
    {
		this.context = context;
	    this.dataList = dataList;
	    layoutInflater = LayoutInflater.from(context);
    }
	
	@Override
    public int getCount()
    {
	    // TODO Auto-generated method stub
	    return dataList.size();
    }

	@Override
    public Object getItem(int position)
    {
	    // TODO Auto-generated method stub
	    return position;
    }

	@Override
    public long getItemId(int position)
    {
	    // TODO Auto-generated method stub
	    return position;
    }

	@Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
		ViewHolder viewHolder = new ViewHolder();
		if(convertView == null)
		{
			convertView = layoutInflater.inflate(R.layout.item_domain, null);
			viewHolder.textView = (TextView) convertView.findViewById(R.id.list_item_textview);
			viewHolder.button = (Button) convertView.findViewById(R.id.list_item_button);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final String result = (String) dataList.get(position).get("domainResult");
		//首先取出结果中的域名
		final int start = result.indexOf("|");
		int end = result.indexOf("|", start+1);
		final String domain = result.substring(start+1, end);
		//结果中含有210代表可以注册，211表示已经被注册
		if(result.contains("210"))
		{
			viewHolder.textView.setText(domain+"   可以注册");
			viewHolder.textView.setTextColor(Color.BLUE);
			viewHolder.button.setText("抢注");
			viewHolder.button.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					//http://www.xinnet.com/domain/check.do?method=domfloSchres&domainSuffixType=0&prefix=yeetrack&suffix=.com
					Intent intent = new Intent();  
		            intent.setAction(Intent.ACTION_VIEW);  
		            intent.addCategory(Intent.CATEGORY_BROWSABLE);
		            
		            String prefix = domain.substring(0, domain.indexOf("."));
		            String suffix = domain.replace(prefix, "");
		            intent.setData(Uri.parse("http://www.xinnet.com/domain/check.do?method=domfloSchres&domainSuffixType=0&prefix="+prefix+"&suffix="+suffix));  
		            context.startActivity(intent);
				}
			});
		}
		
		else
		{
			viewHolder.textView.setText(domain+"   已经被注册");
			viewHolder.textView.setTextColor(Color.RED);
			viewHolder.button.setText("详情");
			//设置监听器
			viewHolder.button.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					Intent intent = new Intent(context, WhoisActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("domain", domain);
					intent.putExtra("data", bundle);
					context.startActivity(intent);
				}
			});
		}
		
	    return convertView;
    }
	
	private class ViewHolder
	{
		TextView textView;
		Button button;
	}

}
