package com.wiyun.engine.jump;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesService
{
	private Context context;
	public  PreferencesService(Context context)
	{
		this.context=context;
	}
	public void save(Integer max )
	{
		SharedPreferences shares=context.getSharedPreferences("gaofen", Context.MODE_PRIVATE);
		Editor editor = shares.edit();
		editor.putInt("highest", max);
		editor.commit();
	}
	public void save2(Integer max2 )
	{
		SharedPreferences shares2=context.getSharedPreferences("gaofen2", Context.MODE_PRIVATE);
		Editor editor = shares2.edit();
		editor.putInt("highest2", max2);
		editor.commit();
	}
	public Map<String, String> getout()
	{
		Map<String,String> params=new HashMap<String, String>();
		SharedPreferences shares=context.getSharedPreferences("gaofen", Context.MODE_PRIVATE);
		SharedPreferences shares2=context.getSharedPreferences("gaofen2", Context.MODE_PRIVATE);
		params.put("highest", String.valueOf(shares.getInt("highest", 0)));
		params.put("highest2", String.valueOf(shares2.getInt("highest2", 0)));
		return params;
	}
}
