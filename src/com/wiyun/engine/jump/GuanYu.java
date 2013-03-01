package com.wiyun.engine.jump;


import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Label;
import com.wiyun.engine.nodes.Scene;
import com.wiyun.engine.types.WYSize;


public class GuanYu extends Scene
{
	GuanYu(){
		WYSize size=Director.getInstance().getWindowSize();
		Label abouTest= Label.make("www.cmd100.com 提供帮助", 20);
		abouTest.setPosition(size.width/2, size.height/2);
		Label abouTest2=Label.make("开发者ID:cuocuozai",20);
		abouTest2.setPosition(size.width/2, size.height/2-30);
		abouTest2.autoRelease();
		
		abouTest.autoRelease();
		addChild(abouTest);
		addChild(abouTest2);
	}

	
}
