package com.wiyun.engine.jump;




import com.wiyun.engine.jump.R;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Label;
import com.wiyun.engine.nodes.Layer;
import com.wiyun.engine.opengl.Texture2D;

import com.wiyun.engine.transitions.TransitionScene;
import com.wiyun.engine.types.WYSize;

public class Load extends Layer 
{	
	
	WYSize s;
	boolean run;
	Load()
	{

		s=Director.getInstance().getWindowSize();
		Label label=Label.make("load.....", 20);
		label.setPosition(s.width/2, s.height/2);
		label.autoRelease();
		addChild(label);
		if (run)
			return;
		run = true;
		new Thread(){
			public void run()
			{
				Texture2D.makePNG(R.drawable.gameback).loadTexture();
				Texture2D.makePNG(R.drawable.zhujiao1).loadTexture();
				Texture2D.makePNG(R.drawable.zhujiao2).loadTexture();
				Texture2D.makePNG(R.drawable.bc3).loadTexture();
				Texture2D.makePNG(R.drawable.bc31).loadTexture();
				Texture2D.makePNG(R.drawable.menu1).loadTexture();
				Texture2D.makePNG(R.drawable.menu).loadTexture();
				Texture2D.makePNG(R.drawable.playagain1).loadTexture();
				Texture2D.makePNG(R.drawable.playagain).loadTexture();
				Texture2D.makePNG(R.drawable.zhujiao1).loadTexture();
				Texture2D.makePNG(R.drawable.snow).loadTexture();
				Texture2D.makePNG(R.drawable.taijie).loadTexture();
				Texture2D.makePNG(R.drawable.cao).loadTexture();
				Texture2D.makePNG(R.drawable.classic).loadTexture();
				Texture2D.makePNG(R.drawable.classic1).loadTexture();
				Texture2D.makePNG(R.drawable.scbc).loadTexture();
				Texture2D.makePNG(R.drawable.survival).loadTexture();
				Texture2D.makePNG(R.drawable.survival).loadTexture();
				Texture2D.makePNG(R.drawable.scores).loadTexture();
				Texture2D.makePNG(R.drawable.scores1).loadTexture();
				Texture2D.makePNG(R.drawable.wuyun).loadTexture();
				Texture2D.makePNG(R.drawable.fenshu).loadTexture();
				Texture2D.makePNG(R.drawable.fenshu1).loadTexture();

				
				Director.getInstance().runOnGLThread(new Runnable() {
					@Override
					public void run() {
						
						MEnu aEnu = new MEnu();
						
						Director.getInstance().replaceScene(
								TransitionScene.make(1f, aEnu.mScene));
						
						run = false;
					}
				});
			}
		}.start();
		
	}

}
