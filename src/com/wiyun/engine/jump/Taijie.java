package com.wiyun.engine.jump;

import com.wiyun.engine.box2d.collision.PolygonShape;
import com.wiyun.engine.box2d.dynamics.Body;
import com.wiyun.engine.box2d.dynamics.BodyDef;
import com.wiyun.engine.box2d.dynamics.Fixture;
import com.wiyun.engine.box2d.dynamics.World;

import com.wiyun.engine.jump.R;
import com.wiyun.engine.nodes.Layer;
import com.wiyun.engine.opengl.Texture2D;

public class Taijie extends Layer
{
	Fixture mtaijie;
	Body taijieBody;
	Taijie(World mWorld)
	{
		BodyDef bodyDef=BodyDef.make();
		bodyDef.setType(Body.TYPE_KINEMATIC);
		taijieBody= mWorld.createBody(bodyDef);
		taijieBody.setSleepingAllowed(true);
		bodyDef.destroy();
		PolygonShape shape = PolygonShape.make();
		shape.setAsBox(1.6f,0.4f);
		mtaijie=taijieBody.createFixture(shape,0.0f);
		
		Texture2D texture2d=Texture2D.makePNG(R.drawable.taijie);
		Box2d.box2dRender.bindTexture(mtaijie, texture2d);
		texture2d.autoRelease();
		
	}
	
} 
