package com.wiyun.engine.jump;

import com.wiyun.engine.box2d.collision.PolygonShape;
import com.wiyun.engine.box2d.dynamics.Body;
import com.wiyun.engine.box2d.dynamics.BodyDef;
import com.wiyun.engine.box2d.dynamics.Fixture;
import com.wiyun.engine.box2d.dynamics.World;

import com.wiyun.engine.jump.R;
import com.wiyun.engine.nodes.Layer;
import com.wiyun.engine.opengl.Texture2D;

public class Yun extends Layer
{
	Fixture myun;
	Yun(World mWorld)
	{
		BodyDef bd=BodyDef.make();
		bd.setType(Body.TYPE_KINEMATIC);
		Body body= mWorld.createBody(bd);
		body.setSleepingAllowed(true);
		bd.destroy();
		PolygonShape shape = PolygonShape.make();
		shape.setAsBox(2f,0.8f);
		myun=body.createFixture(shape,0.0f);
		
		Texture2D texture2d=Texture2D.makePNG(R.drawable.yun);
		Box2d.box2dRender.bindTexture(myun, texture2d);
		texture2d.autoRelease();
	}
} 
