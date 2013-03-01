package com.wiyun.engine.jump;

import com.wiyun.engine.jump.R;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Layer;
import com.wiyun.engine.nodes.Menu;
import com.wiyun.engine.nodes.MenuItemSprite;
import com.wiyun.engine.nodes.Scene;
import com.wiyun.engine.nodes.Sprite;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.types.WYSize;
import com.wiyun.engine.utils.ResolutionIndependent;

public class MEnu extends Layer
{
	Scene mScene;//申明场景
	WYSize s;//获得屏幕尺寸
	MEnu()
	{
		s=Director.getInstance().getWindowSize();
		mScene=Scene.make();
		mScene.autoRelease(true);
		background();//背景
		me();//菜单
	
	}
	float DP(float v) {
		return ResolutionIndependent.resolveDp(v);
	}
	float SP(float v) {
		return ResolutionIndependent.resolveSp(v);
	}
	public void me()
	{
		Texture2D tex= Texture2D.makePNG(R.drawable.classic);
		Texture2D tex2=Texture2D.makePNG(R.drawable.classic1);
		Sprite spriteNormal=Sprite.make(tex);
		spriteNormal.autoRelease();
		Sprite spriteDisable=Sprite.make(tex2);
		spriteDisable.autoRelease();
		tex.autoRelease();
		tex2.autoRelease();
		MenuItemSprite classic= MenuItemSprite.make(spriteNormal, spriteDisable, spriteDisable,this,"classic");
		
		tex= Texture2D.makePNG(R.drawable.survival);
		tex2= Texture2D.makePNG(R.drawable.survival1);
		spriteNormal=Sprite.make(tex);
		spriteNormal.autoRelease();
		spriteDisable=Sprite.make(tex2);
		spriteDisable.autoRelease();
		tex.autoRelease();
		tex2.autoRelease();
		MenuItemSprite survival= MenuItemSprite.make(spriteNormal,spriteDisable,spriteDisable,this,"survival");		
		
		
		tex= Texture2D.makePNG(R.drawable.scores);
		tex2= Texture2D.makePNG(R.drawable.scores1);
		spriteNormal=Sprite.make(tex);
		spriteNormal.autoRelease();
		spriteDisable=Sprite.make(tex2);
		spriteDisable.autoRelease();
		tex.autoRelease();
		tex2.autoRelease();
		MenuItemSprite scores= MenuItemSprite.make(spriteNormal,spriteDisable,spriteDisable,this,"scores");
		

		
		Menu menu = Menu.make(classic,survival,scores);
		menu.alignItemsVertically(15);
		menu.setPosition(s.width/2,s.height*2/3-90);
		mScene.addChild(menu);
	}

		
	public void background()
	{
		Texture2D bc=Texture2D.makePNG(R.drawable.gameback);
		Sprite bc2=Sprite.make(bc);
		bc.autoRelease();
		bc2.autoRelease();
		bc2.setPosition(s.width/2,s.height/2);
		bc2.setContentSize(s.width, s.height);
		bc2.setAutoFit(true);
		mScene.addChild(bc2);
		
	}
	
    public void survival() {  
    	Skeleton.skeleton.survivalchange();
    }  
  
    public void classic() {  
    	Skeleton.skeleton.classicchange();
    }
    public void scores()
	{
		Skeleton.skeleton.paihangbang();
	}
   
    
}
