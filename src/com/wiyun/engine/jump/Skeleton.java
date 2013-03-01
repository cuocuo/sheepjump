package com.wiyun.engine.jump;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;


import com.adview.AdViewLayout;
import com.wiyun.engine.jump.R;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Director.IDirectorLifecycleListener;
import com.wiyun.engine.nodes.Scene;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.opengl.WYGLSurfaceView;
import com.wiyun.engine.transitions.TransitionScene;
import com.wiyun.game.WiGame;



public class Skeleton extends Activity implements IDirectorLifecycleListener{
	static {
		/*
		 * 这里您可以去掉你不要的库, 但是wiskia, xml2和wiengine是一定要保留的
		 */
		System.loadLibrary("wiskia");
		System.loadLibrary("xml2");
		System.loadLibrary("wiengine");
		System.loadLibrary("box2d");
		
	}
	
	protected WYGLSurfaceView mGLView;
	protected Scene mScene;
	static Skeleton skeleton;
	private SoundPool soundPool;
	private boolean start;
	private HashMap<Integer, Integer> soundPoolMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setVolumeControlStream(android.media.AudioManager.STREAM_MUSIC);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mGLView = new WYGLSurfaceView(this);
        skeleton=this;
        setContentView(mGLView);
        WiGame.init(this, "1336e7ca63176403", "KWM4rubtqY3nzPUfJmmx5TGRq8Cvu2VQ", "1.0",false);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // add lifecycle listener
        Director.getInstance().addLifecycleListener(this);
       /**
        * 聚合广告
        */
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
        		FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.TOP;
        AdViewLayout layout =new AdViewLayout(this,"SDK201211121104227x5o6lv718cyd8b");
        //AdViewTargeting.setRunMode(RunMode.TEST);
        //AdViewTargeting.setUpdateMode(UpdateMode.EVERYTIME);
        addContentView(layout, lp);
        
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);  
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(1, soundPool.load(this, R.raw.tiao, 1));
        soundPoolMap.put(2, soundPool.load(this, R.raw.niao, 1));
        soundPoolMap.put(3, soundPool.load(this, R.raw.dian, 1));
        soundPoolMap.put(4, soundPool.load(this, R.raw.shui, 1));
        soundPoolMap.put(5, soundPool.load(this, R.raw.yun, 1));
        soundPoolMap.put(6, soundPool.load(this, R.raw.xuehua, 1));
        soundPoolMap.put(7, soundPool.load(this, R.raw.over, 1));
    }
  
	@Override
    public void onPause() {
        super.onPause();

        Director.getInstance().pause();
    }

    @Override
    public void onResume() {
        super.onResume();

        Director.getInstance().resume();
    }
    public void play1()
	{
    	 AudioManager mgr = (AudioManager) this.getSystemService(    
    	            Context.AUDIO_SERVICE);    
    	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);     
    	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//设置最大音量         
    	    float volume = streamVolumeCurrent/streamVolumeMax ;  //设备的音量 
    	    soundPool.play(soundPoolMap.get(1), volume/6, volume/6, 0 , 0 ,2.0f);
	}
    public void play2()
   	{
       	 AudioManager mgr = (AudioManager) this.getSystemService(    
       	            Context.AUDIO_SERVICE);    
       	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);     
       	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//设置最大音量         
       	    float volume = streamVolumeCurrent/streamVolumeMax ;  //设备的音量 
       	    soundPool.play(soundPoolMap.get(2), volume*2/3, volume*2/3, 0 , 0 ,1.5f);
   	}
    public void play3()
   	{
       	 AudioManager mgr = (AudioManager) this.getSystemService(    
       	            Context.AUDIO_SERVICE);    
       	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);     
       	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//设置最大音量         
       	    float volume = streamVolumeCurrent/streamVolumeMax ;  //设备的音量 
       	    soundPool.play(soundPoolMap.get(3), volume/2, volume/2, 0 , 0 ,1.5f);
   	}
    public void play4()
   	{
       	 AudioManager mgr = (AudioManager) this.getSystemService(    
       	            Context.AUDIO_SERVICE);    
       	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);     
       	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//设置最大音量         
       	    float volume = streamVolumeCurrent/streamVolumeMax ;  //设备的音量 
       	    soundPool.play(soundPoolMap.get(4), volume, volume, 0 , 0 ,2.0f);
   	}
    public void play5()
   	{
       	 AudioManager mgr = (AudioManager) this.getSystemService(    
       	            Context.AUDIO_SERVICE);    
       	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);     
       	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//设置最大音量         
       	    float volume = streamVolumeCurrent/streamVolumeMax ;  //设备的音量 
       	    soundPool.play(soundPoolMap.get(5), volume, volume, 0 , 0 ,1f);
   	}
    public void play6()
   	{
       	 AudioManager mgr = (AudioManager) this.getSystemService(    
       	            Context.AUDIO_SERVICE);    
       	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);     
       	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//设置最大音量         
       	    float volume = streamVolumeCurrent/streamVolumeMax ;  //设备的音量 
       	    soundPool.play(soundPoolMap.get(6), volume*3/2, volume*3/2, 0 , 0 ,1f);
   	}
    public void play7()
   	{
       	 AudioManager mgr = (AudioManager) this.getSystemService(    
       	            Context.AUDIO_SERVICE);    
       	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);     
       	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//设置最大音量         
       	    float volume = streamVolumeCurrent/streamVolumeMax ;  //设备的音量 
       	    soundPool.play(soundPoolMap.get(7), volume, volume, 0 , 0 ,1f);
   	}
 
    

    @Override
    public void onDestroy() {
    	Director.getInstance().end();
    	
        super.onDestroy();
    }
    
	@Override
	public void onDirectorEnded() {
	}

	@Override
	public void onDirectorPaused() {
	}

	@Override
	public void onDirectorResumed() {
	}

	@Override
	public void onDirectorScreenCaptured(String path) {
	}

	@Override
	public void onSurfaceChanged(int arg0, int arg1) {
    	
		/*
    	 * 把起始代码放在这里, 可以避免一些屏幕尺寸问题. 因为一开始WiEngine并不知道
    	 * SurfaceView的尺寸, 所以是假设为全屏大小. 在之后的onSurfaceChanged事件中, 
    	 * 正确的大小会被设置. 但是往往在onSurfaceChanged之前, 场景就已经被创建了, 
    	 * 所以如果因为某种原因不是全屏大小, 那么之前创建的场景位置就有误了.
    	 * 
    	 * 由于onSurfaceChanged有可能被调用多次, 用一个标志来防止多次运行. 而且这可能
    	 * 还不够, 有些情况下onSurfaceChanged可能连续被调用两次, 第一次传入的尺寸不对, 
    	 * 而第二次是对的. 这种情况下, 还需要做更多的检查, 可以通过尺寸大小和屏幕朝向来检查.
    	 * 比如游戏如果是横屏的, 则w必须要大于h才认为是可以的, 反之则必须要h大于w才可以.
    	 */
		if (!start)
		{
			start=true;
			Scene scene =Scene.make();
			Load a = new Load();
			scene.addChild(a);
			scene.autoRelease();
			Director.getInstance().runWithScene(scene);
		}

    		
	}

	@Override
	public void onSurfaceCreated(){
	}

	@Override
	public void onSurfaceDestroyed() {
	}

	public void classicchange()
	{
		
		new Thread(){
			public void run()
			{
				
				Texture2D.makePNG(R.drawable.zhujiao1).loadTexture();
				Texture2D.makePNG(R.drawable.zhujiao2).loadTexture();
				Texture2D.makePNG(R.drawable.bc3).loadTexture();
				Texture2D.makePNG(R.drawable.wuyun).loadTexture();
				Texture2D.makePNG(R.drawable.niao).loadTexture();
				Texture2D.makePNG(R.drawable.shandian).loadTexture();
				Texture2D.makePNG(R.drawable.xuehua).loadTexture();
				Texture2D.makePNG(R.drawable.yun).loadTexture();
				Texture2D.makePNG(R.drawable.snow).loadTexture();
				Texture2D.makePNG(R.drawable.taijie).loadTexture();
				
				Director.getInstance().runOnGLThread(new Runnable() {
					@Override
					public void run() {

						 Scene scene=Scene.make();
				         Box2d aBox2d= new Box2d();
				         scene.addChild(aBox2d,0);
				         scene.autoRelease();
				         Director.getInstance().replaceScene(TransitionScene.make(1,scene));
					}
				});
			}
		}.start();
        	
	}
	public void tomenu()
	{
		Director.getInstance().pauseUI();
		runOnUiThread(new Runnable() {
			public void run() { 
		AlertDialog.Builder builder = new Builder(Skeleton.this);
		builder.setMessage("Return to main ?")
				.setIcon(R.drawable.icon)
				.setTitle("Game Pause")
				.setCancelable(false)
				.setNegativeButton("No", new OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						if (Director.getInstance().isUIPaused())
						{
							Director.getInstance().resumeUI();
						}
						
					}
				})
				.setPositiveButton("Yes", new OnClickListener() {
					public void onClick(DialogInterface dialog,int which)
					{
						Director.getInstance().resumeUI();								
						MEnu aEnu =new MEnu();
						Director.getInstance().replaceScene(aEnu.mScene);
						
					}
				}).show();
			}});
	}
	public void survivalchange()
	{
		new Thread(){
			public void run()
			{
				
				Texture2D.makePNG(R.drawable.zhujiao1).loadTexture();
				Texture2D.makePNG(R.drawable.zhujiao2).loadTexture();
				Texture2D.makePNG(R.drawable.bc3).loadTexture();
				Texture2D.makePNG(R.drawable.wuyun).loadTexture();
				Texture2D.makePNG(R.drawable.niao).loadTexture();
				Texture2D.makePNG(R.drawable.shandian).loadTexture();
				Texture2D.makePNG(R.drawable.xuehua).loadTexture();
				Texture2D.makePNG(R.drawable.yun).loadTexture();
				Texture2D.makePNG(R.drawable.snow).loadTexture();
				Texture2D.makePNG(R.drawable.taijie).loadTexture();
				
				Director.getInstance().runOnGLThread(new Runnable() {
					@Override
					public void run() {

						 Scene scene=Scene.make();
						 Shengcun shengcun= new Shengcun();
				         scene.addChild(shengcun,0);
				         scene.autoRelease();
				         Director.getInstance().replaceScene(TransitionScene.make(1,scene));
					}
				});
			}
		}.start();
	}
	public void paihangbang()
	{
		WiGame.startUI();
	}
	public void chuanfen1()
	{
		WiGame.submitScore("258678c62a0eefd6", Box2d.scores, null, false);
	}
	public void chuanfen2()
	{
		WiGame.submitScore("7c8039795cafd6d5", Shengcun.scores, null, false);
	}
	public void tuijian()
	{
		//AppConnect.getInstance(this).showOffers(this);
	}

}
