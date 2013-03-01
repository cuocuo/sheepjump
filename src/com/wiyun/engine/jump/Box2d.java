package com.wiyun.engine.jump;

import java.util.Random;

import android.graphics.Typeface;
import android.view.KeyEvent;

import com.wiyun.engine.box2d.Box2D;
import com.wiyun.engine.box2d.Box2DRender;
import com.wiyun.engine.box2d.collision.PolygonShape;
import com.wiyun.engine.box2d.dynamics.Body;
import com.wiyun.engine.box2d.dynamics.BodyDef;
import com.wiyun.engine.box2d.dynamics.Fixture;
import com.wiyun.engine.box2d.dynamics.World;
import com.wiyun.engine.box2d.dynamics.World.IContactListener;
import com.wiyun.engine.box2d.dynamics.contacts.Contact;
import com.wiyun.engine.jump.R;
import com.wiyun.engine.nodes.ColorLayer;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Label;
import com.wiyun.engine.nodes.Scene;
import com.wiyun.engine.nodes.Scheduler;
import com.wiyun.engine.nodes.Sprite;
import com.wiyun.engine.nodes.Timer;
import com.wiyun.engine.particle.ParticleSystem;
import com.wiyun.engine.particle.QuadParticleSystem;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.transitions.BottomPushInTransition;
import com.wiyun.engine.types.WYPoint;
import com.wiyun.engine.types.WYSize;
import com.wiyun.engine.utils.ResolutionIndependent;
import com.wiyun.engine.utils.TargetSelector;

public class Box2d extends ColorLayer implements IContactListener 
{
	static World mWorld;
	boolean running;
	int pengzhuang;
	static Box2D mBox2d;
	static float biansu=1f, shandian1=1f;
	int n,m,r,q=0;;
	int w;
	Taijie[] taijie;//台阶类
	Yun[] yun;
	Wuyun[] wuyun;
	WYPoint robotpos ,taijiepos,sudu, boxpos;
	static Box2DRender box2dRender;
	float x;
	float y=37f;
	float boxy;
	float top;
	Fixture mrobot;
	Fixture	mcao;
	Fixture	mxuehua;
	Fixture	mxiaoniao;
	Fixture	mshandian;
	float py,px;
	Body robotBody;
	Body bcbody;
	Sprite bc2;
	int fenshu;
	static int scores;
	Label mLabel2;
	TargetSelector mSelector2;
	Contact contact;
	ParticleSystem emitter;
	
	
	Box2d()
	{
		biansu=1f;
		shandian1=1f;
		boxy=0;
		WYSize s=Director.getInstance().getWindowSize();
		mBox2d =Box2D.make();
		mBox2d.setDebugDraw(false);
		mBox2d.setPosition(s.width/2, 0);
		mBox2d.setMeterPixels(32);
		mWorld=mBox2d.getWorld();
		box2dRender=Box2DRender.make();
		mBox2d.setBox2DRender(box2dRender);
		//背景
		Texture2D bc=Texture2D.makePNG(R.drawable.bc3);
		bc.setAntiAlias(false);
		Sprite bc2=Sprite.make(bc);
		bc.autoRelease();
		bc2.autoRelease();	
		bc2.setPosition(s.width/2,s.height/2);
		bc2.setContentSize(s.width, s.height);
		bc2.setAutoFit(true);
		addChild(bc2);
		setKeyEnabled(true);

		//下雪
		emitter=new ParticleSnow();
		emitter.setPosition(s.width/2,s.height);
		emitter.autoRelease();
		addChild(emitter);
		//踩一下就消失的白云
		/*yun=new Yun[7];
		for (int i2 = 0; i2 < yun.length; i2++)
		{
			yun[i2]=new Yun(mWorld);

			yun[0].myun.getBody().setTransform(2f-s.width/2/32,
					(21000)/32,0);
			yun[i2].myun.getBody().setTransform((2f-s.width/2/32)+1.5f*(b.nextInt(7)),
						(b.nextInt(100)),0);

		}*/
		//移动的乌云
		wuyun = new Wuyun[6];
		Random b = new Random();
		{
			for (int i3= 0; i3 < wuyun.length; i3++)
			{
				wuyun[i3]=new Wuyun(mWorld);
				wuyun[i3].mwuyun.getBody().setTransform(b.nextInt((int)s.width/32-1)-s.width/2/32+1.3f,b.nextInt(200), 0);
			}
			
		}
		//雪花（减速效果）
		{
			BodyDef bd=BodyDef.make();
			bd.setType(Body.TYPE_STATIC);
			bd.setPosition(-3f,150f);
			Body body=mWorld.createBody(bd);
			bd.destroy();
			PolygonShape shape=PolygonShape.make();
			shape.setAsBox(0.9f,0.9f);
			mxuehua = body.createFixture(shape,0);
			Texture2D tex=Texture2D.makePNG(R.drawable.xuehua);
			box2dRender.bindTexture(mxuehua, tex);
			tex.autoRelease();
		}
		//草（增加弹力）
		{
			BodyDef bd=BodyDef.make();
			bd.setType(Body.TYPE_STATIC);
			bd.setPosition(-2f, 100f);
			Body body=mWorld.createBody(bd);
			bd.destroy();
			PolygonShape shape=PolygonShape.make();
			shape.setAsBox(0.6f,1.0f);
			mcao = body.createFixture(shape,0);
			Texture2D tex=Texture2D.makePNG(R.drawable.cao);
			box2dRender.bindTexture(mcao, tex);
			tex.autoRelease();
		}
		//小鸟（飞速前进）
		{
			BodyDef bd=BodyDef.make();
			bd.setType(Body.TYPE_STATIC);
			bd.setPosition(5f, 200f);
			Body body=mWorld.createBody(bd);
			bd.destroy();
			PolygonShape shape=PolygonShape.make();
			shape.setAsBox(0.8f,0.8f);
			mxiaoniao = body.createFixture(shape,0);
			Texture2D tex=Texture2D.makePNG(R.drawable.niao);
			box2dRender.bindTexture(mxiaoniao, tex);
			tex.autoRelease();
		}
		//闪电（左右颠倒）
		{
			BodyDef bd=BodyDef.make();
			bd.setType(Body.TYPE_STATIC);
			bd.setPosition(3,250f);
			Body body=mWorld.createBody(bd);
			bd.destroy();
			PolygonShape shape=PolygonShape.make();
			shape.setAsBox(0.7f,0.9f);
			mshandian = body.createFixture(shape,0);
			Texture2D tex=Texture2D.makePNG(R.drawable.shandian);
			box2dRender.bindTexture(mshandian, tex);
			tex.autoRelease();
		}
		//主角
		{
			
			BodyDef bd=BodyDef.make();
			bd.setType(Body.TYPE_DYNAMIC);
			bd.setPosition(0,3f);
			robotBody=mWorld.createBody(bd);
			bd.destroy();
			PolygonShape shape=PolygonShape.make();
			shape.setAsBox(1.1f,1.0f);
			mrobot=robotBody.createFixture(shape,10f);
			robotBody.setLinearVelocity(WYPoint.make(0, 40));
			Texture2D tex=Texture2D.makePNG(R.drawable.zhujiao1);
			box2dRender.bindTexture(mrobot, tex);
			tex.autoRelease();
		}
		
		
		//台阶
		Random a=new Random();
		taijie=new Taijie[10];
		{
			for (int i = 0; i <taijie.length; i++)
			{
				
				taijie[i] =new Taijie(mWorld);
			}
			taijie[0].mtaijie.getBody().setTransform(1.4f-s.width/2/32
					,3f, 0);
			taijie[1].mtaijie.getBody().setTransform(taijie[0].mtaijie.getBody().getPosition().x+(a.nextInt(12))
					,taijie[0].mtaijie.getBody().getPosition().y+(2+a.nextInt(4)), 0);
			taijie[2].mtaijie.getBody().setTransform(taijie[0].mtaijie.getBody().getPosition().x+(a.nextInt(12))
					,taijie[1].mtaijie.getBody().getPosition().y+(2+a.nextInt(4)), 0);
			taijie[3].mtaijie.getBody().setTransform(taijie[0].mtaijie.getBody().getPosition().x+(a.nextInt(12))
					,taijie[2].mtaijie.getBody().getPosition().y+(2+a.nextInt(4)), 0);
			taijie[4].mtaijie.getBody().setTransform(taijie[0].mtaijie.getBody().getPosition().x+(a.nextInt(12))
					,taijie[3].mtaijie.getBody().getPosition().y+(2+a.nextInt(4)), 0);
			taijie[5].mtaijie.getBody().setTransform(taijie[0].mtaijie.getBody().getPosition().x+(a.nextInt(12))
					,taijie[4].mtaijie.getBody().getPosition().y+(2+a.nextInt(4)), 0);
			taijie[6].mtaijie.getBody().setTransform(taijie[0].mtaijie.getBody().getPosition().x+(a.nextInt(12))
					,taijie[5].mtaijie.getBody().getPosition().y+(2+a.nextInt(4)), 0);
			taijie[7].mtaijie.getBody().setTransform(taijie[0].mtaijie.getBody().getPosition().x+(a.nextInt(12))
					,taijie[6].mtaijie.getBody().getPosition().y+(2+a.nextInt(4)), 0);
			taijie[8].mtaijie.getBody().setTransform(taijie[0].mtaijie.getBody().getPosition().x+(a.nextInt(12))
					,taijie[7].mtaijie.getBody().getPosition().y+(2+a.nextInt(4)), 0);
			taijie[9].mtaijie.getBody().setTransform(taijie[0].mtaijie.getBody().getPosition().x+(a.nextInt(12))
					,taijie[8].mtaijie.getBody().getPosition().y+(2+a.nextInt(4)), 0);
		}
		//分数
		mLabel2 = Label.make(" 0",SP(20),Typeface.BOLD_ITALIC);
		mLabel2.setPosition(DP(50), s.height-DP(60));
		addChild(mLabel2);
		mLabel2.autoRelease();


		addChild(mBox2d);
		mWorld.setGravity(0,-60);//重力
		mWorld.setContactListener(this);//碰撞监听
		setAccelerometerEnabled(true);//加速度
		schedule(new TargetSelector(this, "update(float)",new Object[]{0f}));
		// 每10帧调用一次updateLabel2，来更新第二个label
				mSelector2 = new TargetSelector(this, "updateLabel2(float,int)",
						new Object[] { 0f, 1 });
				Timer t = new Timer(mSelector2, 1);
				Scheduler.getInstance().schedule(t);

	}

	
	public void update(float delta)
	{
		
		mWorld.step(1.f/60.f, 10, 10);
		mWorld.clearForces();
		WYSize s =Director.getInstance().getWindowSize();
		robotpos=robotBody.getPosition();
		py = mBox2d.meter2Pixel(robotpos.y);
		sudu=mrobot.getBody().getLinearVelocity();
		Random c=new Random();
		int len=taijie.length;
		
		if (py>s.height/2-boxy&&sudu.y>0)
		{
			mBox2d.setPosition(WYPoint.make(s.width/2,s.height/2-py));
			boxy=mBox2d.getPositionY();
		}
		if (py<-boxy-0.8*32)//游戏结束条件
		{
			mrobot.setSensor(true);
			scores=fenshu;
			over();
		}
		for (int i = 0; i < taijie.length; i++)
		{
			float max=taijie[0].mtaijie.getBody().getPosition().y;
			for (int j = 1; j < len; j++)
			{
				
				if (taijie[j].mtaijie.getBody().getPosition().y>max)
				{
					max=taijie[j].mtaijie.getBody().getPosition().y;
				}
			}
			if (taijie[i].mtaijie.getBody().getPosition().y<-boxy/32&&fenshu<=10000)
			{
				taijie[i].mtaijie.getBody().setTransform(c.nextInt((int)s.width/32-1)-s.width/2/32+1.3f,
						max+(3+c.nextInt(4)), 0);
				taijie[4].mtaijie.getBody().setLinearVelocity(WYPoint.make(2.5f, 0));
			}
			if (taijie[i].mtaijie.getBody().getPosition().y<-boxy/32&&fenshu>10000&&fenshu<=30000)
			{
				taijie[i].mtaijie.getBody().setTransform(c.nextInt((int)s.width/32-1)-s.width/2/32+1.3f,
						max+(3+c.nextInt(5)), 0);
				taijie[4].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (3.2+fenshu/50000), 0));
				taijie[6].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (-3+fenshu/50000), 0));
			}
			if (taijie[i].mtaijie.getBody().getPosition().y<-boxy/32&&fenshu>30000&&fenshu<=50000)
			{
				taijie[i].mtaijie.getBody().setTransform(c.nextInt((int)s.width/32-1)-s.width/2/32+1.3f,
						max+(3+c.nextInt(6)), 0);
				taijie[2].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (3.6+fenshu/100000), 0));
				taijie[6].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (-3.6+fenshu/100000), 0));
				taijie[4].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (3.1+fenshu/100000),0));
			}
			if (taijie[i].mtaijie.getBody().getPosition().y<-boxy/32&&fenshu>50000&&fenshu<=100000)
			{
				taijie[i].mtaijie.getBody().setTransform(c.nextInt((int)s.width/32-1)-s.width/2/32+1.3f,
						max+(3+c.nextInt(7)), 0);
				taijie[3].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (4+fenshu/200000), 0));
				taijie[4].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (-4+fenshu/200000), 0));
				taijie[5].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (4+fenshu/200000), 0));
				taijie[9].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (-3.5+fenshu/200000),0));
			}
			if (taijie[i].mtaijie.getBody().getPosition().y<-boxy/32&&fenshu>100000)
			{
				taijie[i].mtaijie.getBody().setTransform(c.nextInt((int)s.width/32-1)-s.width/2/32+1.3f,
						max+(3+c.nextInt(8)), 0);
				taijie[1].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (4.5+fenshu/2000000), 0));
				taijie[2].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (-4.1+fenshu/2000000), 0));
				taijie[3].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (4.2+fenshu/2000000), 0));
				taijie[4].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (4.6+fenshu/2000000), 0));
				taijie[5].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (-4.5+fenshu/2000000),0 ));
				taijie[10].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (4.3+fenshu/2000000), 0));
				taijie[7].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (4.6+fenshu/2000000), 0));
				taijie[8].mtaijie.getBody().setLinearVelocity(WYPoint.make(0,(float) (4+fenshu/2000000)));
				taijie[9].mtaijie.getBody().setLinearVelocity(WYPoint.make((float) (-4+fenshu/2000000), 0));
			}
			if (taijie[i].mtaijie.getBody().getPosition().x<-1.0f-s.width/2/32)
			{
				float a=taijie[i].mtaijie.getBody().getPosition().y;
				taijie[i].mtaijie.getBody().setTransform(s.width/32/2+1.0f, a, 0);
			}
			if (taijie[i].mtaijie.getBody().getPosition().x>1.0f+s.width/2/32)
			{
				float a=taijie[i].mtaijie.getBody().getPosition().y;
				taijie[i].mtaijie.getBody().setTransform(-s.width/32/2-1.0f, a, 0);
			}
			
		}
		for (int j = 0; j < wuyun.length; j++)
		{
			float max1=wuyun[0].mwuyun.getBody().getPosition().y;
			int changdu=wuyun.length;
			for (int x = 1; x < changdu; x++)
			{
				
				if (wuyun[x].mwuyun.getBody().getPosition().y>max1)
				{
					max1=wuyun[x].mwuyun.getBody().getPosition().y;
				}
			}
			if (wuyun[j].mwuyun.getBody().getPosition().y<-boxy/32&&fenshu<10000)
			{
				wuyun[j].mwuyun.getBody().setTransform(c.nextInt((int)s.width/32-1)-s.width/2/32+1.3f,
						max1+(3+c.nextInt(60)), 0);
			}
			if (wuyun[j].mwuyun.getBody().getPosition().y<-boxy/32&&fenshu>10000)
			{
				wuyun[j].mwuyun.getBody().setTransform(c.nextInt((int)s.width/32-1)-s.width/2/32+1.3f,
						max1+(3+c.nextInt(50)), 0);
				wuyun[1].mwuyun.getBody().setLinearVelocity(WYPoint.make((float) (3+fenshu/200000), 0));
				wuyun[3].mwuyun.getBody().setLinearVelocity(WYPoint.make((float) (-3+fenshu/200000), 0));
				wuyun[5].mwuyun.getBody().setLinearVelocity(WYPoint.make((float) (3+fenshu/200000), 0));
			}
			if (wuyun[j].mwuyun.getBody().getPosition().x<-1.2f-s.width/2/32)
			{
				float a=wuyun[j].mwuyun.getBody().getPosition().y;
				wuyun[j].mwuyun.getBody().setTransform(s.width/32/2+1.2f, a, 0);
			}
			if (wuyun[j].mwuyun.getBody().getPosition().x>1.2f+s.width/2/32)
			{
				float a=wuyun[j].mwuyun.getBody().getPosition().y;
				wuyun[j].mwuyun.getBody().setTransform(-s.width/32/2-1.2f, a, 0);
			}
		}
		
		//道具的处理
		Random f = new Random();
		if (mcao.getBody().getPosition().y*32<-boxy)
		{
			mcao.getBody().setTransform((f.nextInt(9)+3)-s.width/2/32, mcao.getBody().getPosition().y*(f.nextInt(2)+7)/5+s.height/32, 0);
		}
		if (mxuehua.getBody().getPosition().y*32<-boxy)
		{
			mxuehua.getBody().setTransform((f.nextInt(9)+3)-s.width/2/32, mxuehua.getBody().getPosition().y*(f.nextInt(2)+7)/5+s.height/32, 0);
		}
		if (mxiaoniao.getBody().getPosition().y*32<-boxy)
		{
			mxiaoniao.getBody().setTransform((f.nextInt(9)+3)-s.width/2/32, mxiaoniao.getBody().getPosition().y*(f.nextInt(2)+7)/5+s.height/32, 0);
		}
		if (mshandian.getBody().getPosition().y*32<-boxy)
		{
			mshandian.getBody().setTransform((f.nextInt(9)+3)-s.width/2/32, mshandian.getBody().getPosition().y*(f.nextInt(2)+7)/5+s.height/32, 0);
		}
		
		

		//主角超过左右边界的处理
		if (robotpos.x<-0.65f-s.width/2/32)
		{
			float a=robotpos.y;
			mrobot.getBody().setTransform(s.width/32/2+0.65f, a, 0);
		}
		if (robotpos.x>s.width/32/2+0.65f)
		{
			float a=robotpos.y;
			mrobot.getBody().setTransform(-0.65f-s.width/2/32, a, 0);
		}
			
	}
	public void updateLabel2(float delta, int v) {
		mLabel2.setText(String.format(" %d",v));
		fenshu=-(int)boxy/2;
		mSelector2.setArgument(1,fenshu);
	}
	private float SP(float v) {
		return ResolutionIndependent.resolveSp(v);
	}
	//游戏结束
	public void over()
	{
		if (running)
			return;
		running = true;
				Director.getInstance().runOnGLThread(new Runnable() {
					@Override
					public void run() {
						Skeleton.skeleton.play7();
						GameOver aGameOver=new GameOver();
						Scene mScene=Scene.make();
						mScene.addChild(aGameOver);
						mScene.autoRelease();
						Director.getInstance().replaceScene(BottomPushInTransition.make(1f, mScene));
						
					}
				});
			
	}
	//重写返回
	@Override
	public boolean wyKeyUp(KeyEvent event)
	{
		if (event.getKeyCode()==KeyEvent.KEYCODE_BACK)
		{
			
			Skeleton.skeleton.tomenu();
			return true;
		}
		return super.wyKeyUp(event);
	}


	//设置加速度
	@Override
	public void wyAccelerometerChanged(float accelX, float accelY,
			float accelZ)
	{
		
		x=accelX*shandian1;
		mrobot.getBody().setLinearVelocity(WYPoint.make(8f*x+sudu.x*0.8f, sudu.y));
		if (accelX<0)
		{
			Texture2D tex=Texture2D.makePNG(R.drawable.zhujiao2);
			box2dRender.bindTexture(mrobot, tex);
			tex.autoRelease();
		}else {
			Texture2D tex=Texture2D.makePNG(R.drawable.zhujiao1);
			box2dRender.bindTexture(mrobot, tex);
			tex.autoRelease(); 
		}
		
	}
	float DP(float v) {
		return ResolutionIndependent.resolveDp(v);
	}
	@Override
	public void beginContact(int arg0)
	{
		
	}

	@Override
	public void endContact(int arg0)
	{
		
		robotBody.setAngularVelocity(0);
		
	}
	@Override
	public void postSolve(int arg0, int arg1)
	{

	}
	//碰撞分析
	@Override
	public void preSolve(int contactPointer, int oldManifoldPointer)
	{

		contact=Contact.from(contactPointer);
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		int i=0;
		pengzhuang =0;
		if (fixtureB.equals(mrobot))
		{
			
			if (fixtureA.equals(mcao))
			{
				
				biansu=1.5f;
				Director.getInstance().runOnGLThread(new Runnable()
				{
					
					@Override
					public void run()
					{
						Skeleton.skeleton.play6();
						
					}
				});
				scheduleOnce(new TargetSelector(this, "cao1(float)",new Object[]{0f}));
				scheduleOnce(new TargetSelector(this, "cao(float)",new Object[]{0f}),3f);
				
				pengzhuang=0;
				w=1;
			}
			else if (fixtureA.equals(mxuehua))
			{
				Director.getInstance().runOnGLThread(new Runnable()
				{
					
					@Override
					public void run()
					{
						Skeleton.skeleton.play5();
						
					}
				});
				biansu =0.8f;
				scheduleOnce(new TargetSelector(this, "xuehua1(float)",new Object[]{0f}));
				scheduleOnce(new TargetSelector(this, "xuehua(float)",new Object[]{0f}),3f);
				pengzhuang=0;
				w=2;
			}
			else if (fixtureA.equals(mxiaoniao))
			{
				Skeleton.skeleton.play2();
				mWorld.setGravity(0, 0);
				mrobot.getBody().setLinearVelocity(WYPoint.make(sudu.x,35f));
				scheduleOnce(new TargetSelector(this, "xiaoniao1(float)",new Object[]{0f}));
				scheduleOnce(new TargetSelector(this, "xiaoniao(float)",new Object[]{0f}),3f);
				pengzhuang=0;
			
			}
			else if (fixtureA.equals(mshandian))
			{
				Skeleton.skeleton.play3();
				shandian1=-1f;
				scheduleOnce(new TargetSelector(this, "shandian1(float)",new Object[]{0f}));
				scheduleOnce(new TargetSelector(this, "shandian(float)",new Object[]{0f}),3f);
				pengzhuang=0;
			}
			while (!fixtureA.equals(taijie[i].mtaijie)&&i<taijie.length-1)
			{
				i++;
			}
			pengzhuang=1;


		}else if (fixtureA.equals(mrobot)) {
			
			while (!fixtureB.equals(taijie[i].mtaijie)&&i<taijie.length-1)
			{
				i++;
				
			}
			pengzhuang=1;
		}
		if (pengzhuang==1)
		{
			if ((mrobot.getBody().getLinearVelocity().y<0&&
					mrobot.getBody().getPosition().y>taijie[i].mtaijie.getBody().getPosition().y+1.3f))
			{
				
				contact.setEnabled(true);
				mrobot.getBody().setLinearVelocity(WYPoint.make(sudu.x, y*biansu));
				robotBody.setAngularVelocity(0);
				Director.getInstance().runOnGLThread(new Runnable()
				{
					
					@Override
					public void run()
					{
						if (w==0)
						{
							Skeleton.skeleton.play1();
						}else if (w==1) {
							Skeleton.skeleton.play6();
						}else if (w==2) {
							Skeleton.skeleton.play5();
						}
						
						
					}
				});
				
			}else {
				contact.setEnabled(false);
			}
		}
		
		if (pengzhuang ==0)
		{
			contact.setEnabled(false);
		}
	}
	public void cao(float delta)
	{
		biansu=1f;
		w=0;
	}
	public void cao1(float delta)
	{
		WYSize s= Director.getInstance().getWindowSize();
		Random f= new Random();
		mcao.getBody().setTransform((f.nextInt(9)+5)-s.width/2/32, mcao.getBody().getPosition().y*(f.nextInt(2)+7)/6+s.height/32,0);
	}
	public void xuehua(float delta)
	{

		biansu=1f;
		w=0;

	}
	public void xuehua1(float delta)
	{
		WYSize s= Director.getInstance().getWindowSize();
		Random f= new Random();
		mxuehua.getBody().setTransform((f.nextInt(9)+5)-s.width/2/32, mxuehua.getBody().getPosition().y*(f.nextInt(2)+7)/6+s.height/32,0);
	}
	public void xiaoniao(float delta)
	{

		mWorld.setGravity(0, -60);

	}
	public void xiaoniao1(float delta)
	{
		WYSize s= Director.getInstance().getWindowSize();
		Random f= new Random();
		mxiaoniao.getBody().setTransform((f.nextInt(9)+5)-s.width/2/32, mxiaoniao.getBody().getPosition().y*(f.nextInt(2)+8)/6+s.height/32,0);
	}
	public void shandian(float delta)
	{
	
		shandian1=1f;

		
		
	}
	public void shandian1(float delta)
	{
		WYSize s= Director.getInstance().getWindowSize();
		Random f= new Random();
		mshandian.getBody().setTransform((f.nextInt(9)+5)-s.width/2/32, mshandian.getBody().getPosition().y*(f.nextInt(2)+7)/6+s.height/32,0);
	}
	
	
	
	public  class ParticleSnow extends QuadParticleSystem {
		
		protected ParticleSnow() {
			this(60);
		}

		protected ParticleSnow(int p) {
			super(p);

			// duration
			setDuration(PARTICLE_DURATION_INFINITY);

			// gravity
			setParticleGravity(0, -10);

			// angle
			setDirectionAngleVariance(-90, 5);

			// speed of particles
			setSpeedVariance(130, 30);

			// radial
			setRadialAccelerationVariance(0,5);

			// tangential
			setTangentialAccelerationVariance(0, 1);

			// emitter position
			setParticlePositionVariance(0, 0, Director.getInstance()
					.getWindowSize().width / 2, 0);

			// life of particles
			setLifeVariance(8, 1);

			// size, in pixels
			setStartSizeVariance(15, 8);

			// emits per second
			setEmissionRate(getMaxParticleCount() / getLife());

			// color of particles
			setStartColorVariance(1f, 1f, 1f, 1f, 0f, 0f, 0.1f, 0f);
			setEndColorVariance(0.9f, 0.9f, 0.9f, 1f, 0f, 0f, 0.1f, 0f);

			setTexture(Texture2D.makePNG(R.drawable.snow));

			// additive
			setBlendAdditive(false);
		}
	}


/*
	@Override
	public void jDraw()
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void jOnEnter()
	{
		 final Activity act = (Activity) Director.getInstance().getContext();
			act.runOnUiThread(new Runnable() {
			    @Override
			    public void run() {
				Activity act = (Activity) Director.getInstance().getContext();
				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,                                
		                          FrameLayout.LayoutParams.WRAP_CONTENT);
				lp.gravity = Gravity.TOP;
				lp.topMargin = 1;
			    ad = new AdView(act);
				ad.setResId("38df7cb257627120");
				ad.setGoneIfFail(true);
				act.addContentView(ad, lp);
				ad.setRefreshInterval(31);
				ad.requestAd();
				//ad.setTestMode(true);
		            }
			});
		
	}


	@Override
	public void jOnEnterTransitionDidFinish()
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void jOnExit()
	{
		final Activity act = (Activity) Director.getInstance().getContext();
		act.runOnUiThread(new Runnable() {
	 	    @Override
		    public void run() {
			((ViewGroup)ad.getParent()).removeView(ad);
		    }
		});
		
	}*/

}
