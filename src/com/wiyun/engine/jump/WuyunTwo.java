package com.wiyun.engine.jump;

import com.wiyun.engine.box2d.collision.PolygonShape;
import com.wiyun.engine.box2d.dynamics.Body;
import com.wiyun.engine.box2d.dynamics.BodyDef;
import com.wiyun.engine.box2d.dynamics.Fixture;
import com.wiyun.engine.box2d.dynamics.World;
import com.wiyun.engine.jump.R;
import com.wiyun.engine.nodes.Layer;
import com.wiyun.engine.opengl.Texture2D;

public class WuyunTwo extends Layer
{
	Fixture mwuyun;
	WuyunTwo(World mWorld){
		BodyDef bd=BodyDef.make();
		bd.setType(Body.TYPE_KINEMATIC);
		Body taijieBody= mWorld.createBody(bd);
		bd.destroy();
		PolygonShape shape = PolygonShape.make();
		shape.setAsBox(2f,0.8f);
		mwuyun=taijieBody.createFixture(shape,0.0f);
		mwuyun.setSensor(true);
		Texture2D texture2d=Texture2D.makePNG(R.drawable.wuyun);
		Shengcun.box2dRender.bindTexture(mwuyun, texture2d);
		texture2d.autoRelease();
		
	}
}
