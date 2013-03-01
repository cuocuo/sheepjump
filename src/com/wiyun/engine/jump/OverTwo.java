package com.wiyun.engine.jump;


import java.util.Map;

import android.app.Activity;

import android.view.KeyEvent;


import com.wiyun.engine.jump.R;
import com.wiyun.engine.nodes.BitmapFontLabel;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Layer;
import com.wiyun.engine.nodes.Menu;
import com.wiyun.engine.nodes.MenuItemSprite;
import com.wiyun.engine.nodes.Sprite;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.transitions.TransitionScene;
import com.wiyun.engine.types.WYColor3B;
import com.wiyun.engine.types.WYSize;
import com.wiyun.engine.utils.BitmapFont;
import com.wiyun.engine.utils.ResolutionIndependent;
//游戏结束
public class OverTwo extends Layer 
{
	WYSize s;
	int max2=0;
	BitmapFontLabel label1;
	BitmapFontLabel	label2;
	BitmapFont font2;
	private PreferencesService service;
	OverTwo()
	{

		Activity act = (Activity) Director.getInstance().getContext();
		service=new PreferencesService(act);
		Map<String, String> params=service.getout();
		s=Director.getInstance().getWindowSize();
		bc1();
		me1();
		BitmapFont font = BitmapFont.loadFont(
				R.raw.one);
		label1=BitmapFontLabel.make(font," your score");
		label1.setText(String.format("your score: %d",Shengcun.scores));
		label1.setColor(WYColor3B.make(0, 0, 0));
		label1.setPosition(s.width/2, s.height-DP(210));
		label1.autoRelease();
		addChild(label1);
		
		
		font2 = BitmapFont.loadFont(
				R.raw.one);
		if (Integer.valueOf(params.get("highest2"))<Shengcun.scores)
		{
			max2=Shengcun .scores;
			label2=BitmapFontLabel.make(font2," highest score "+Shengcun.scores);
			save2();
		}else {
			label2=BitmapFontLabel.make(font2," highest score "+params.get("highest2"));
		}
		label2.setColor(WYColor3B.make(0, 0, 0));
		label2.setPosition(s.width/2, s.height-DP(235));
		label2.autoRelease();
		addChild(label2);

	}
	public void save2()
	{
		service.save2(max2);
		
	}
	/*private float SP(float v) {
		return ResolutionIndependent.resolveSp(v);
	}*/
	//菜单设置
	public void me1()
	{
		Texture2D tex= Texture2D.makePNG(R.drawable.playagain);
		Texture2D tex2=Texture2D.makePNG(R.drawable.playagain1);
		Sprite spriteNormal=Sprite.make(tex);
		spriteNormal.autoRelease();
		Sprite spriteDisable=Sprite.make(tex2);
		spriteDisable.autoRelease();
		tex.autoRelease();
		tex2.autoRelease();
		MenuItemSprite playagain= MenuItemSprite.make(spriteNormal, spriteDisable,spriteDisable,this,"playagain");
		
		tex= Texture2D.makePNG(R.drawable.fenshu);
		tex2= Texture2D.makePNG(R.drawable.fenshu1);
		spriteNormal=Sprite.make(tex);
		spriteNormal.autoRelease();
		spriteDisable=Sprite.make(tex2);
		spriteDisable.autoRelease();
		tex.autoRelease();
		tex2.autoRelease();
		MenuItemSprite shangchuan1= MenuItemSprite.make(spriteNormal,spriteDisable,spriteDisable,this,"shangchuan");
		
		tex= Texture2D.makePNG(R.drawable.menu);
		tex2= Texture2D.makePNG(R.drawable.menu1);
		spriteNormal=Sprite.make(tex);
		spriteNormal.autoRelease();
		spriteDisable=Sprite.make(tex2);
		spriteDisable.autoRelease();
		tex.autoRelease();
		tex2.autoRelease();
		MenuItemSprite menuItemSprite= MenuItemSprite.make(spriteNormal,spriteDisable,spriteDisable,this,"menu");		
		
		Menu menu = Menu.make(playagain,shangchuan1,menuItemSprite);
		menu.alignItemsVertically(DP(40));
		menu.setPosition(s.width/2,s.height*2/3-DP(160));
		addChild(menu);
		
	}
	//游戏结束背景图片
	public void bc1()
	{
		Texture2D bc=Texture2D.makePNG(R.drawable.bc31);
		bc.setAntiAlias(false);
		Sprite bc2=Sprite.make(bc);
		bc.autoRelease();
		bc2.autoRelease();
		bc2.setPosition(s.width/2,s.height/2);
		bc2.setContentSize(s.width, s.height);
		bc2.setAutoFit(true);
		addChild(bc2);
		
	}
	private float DP(float v) {
		return ResolutionIndependent.resolveDp(v);
	}

	public void playagain()
	{
		Skeleton.skeleton.survivalchange();
		
	}
	public void menu()
	{
		MEnu aEnu = new MEnu();
		Director.getInstance().replaceScene(TransitionScene.make(1,aEnu.mScene));
	}
	public void shangchuan()
	{
		Skeleton.skeleton.chuanfen2();
	}
	@Override
	public boolean wyKeyUp(KeyEvent event)
	{
		if (event.getKeyCode()==KeyEvent.KEYCODE_BACK)
		{
			
			MEnu aEnu = new MEnu();
			Director.getInstance().replaceScene(TransitionScene.make(1,aEnu.mScene));
			return true;
		}
		return super.wyKeyUp(event);
	}
		
}

