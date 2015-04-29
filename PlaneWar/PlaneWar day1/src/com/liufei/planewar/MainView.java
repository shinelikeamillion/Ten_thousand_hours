package com.liufei.planewar;

import com.liufei.planewar.util.PublicData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {
	private boolean isRunning = true;
	private static boolean isPause = false;
	private static boolean isPlanePressed;
	private float touchX;
	private float touchY;
	private int gameSpeed = 10;
	private long gameCount = 0;

	private float screenWidth; // ��Ļ����
	private float screenHeight; // ��Ļ�߶�
	private float initPlaneX; // ��ʼ������λ��
	private float initPlaneY;

	private float bgY1; // ����ͼƬ��y����
	private float bgY2;

	private float scalex; // ���ű���
	private float scaley;

	private Bitmap backGround1; // ����ͼ
	private Bitmap backGround2; // ����ͼ
	private Bitmap myPlaneImg; // �ҵķɻ�ͼƬ
	private MyPlane myPlaneSprite; // �ҵķɻ�����
	private Bitmap bulletImg1; // �ӵ�ͼ

	private SurfaceHolder holder;
	private Paint paint;
	private Canvas canvas;
	private Thread thread;

	private Context context;

	public MainView(Context context) {
		super(context);
		this.context = context;
		holder = this.getHolder();
		holder.addCallback(this);
		paint = new Paint();
		thread = new Thread(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		init();
		thread.start();
	}

	/**
	 * ��ȡͼƬ
	 */
	private Bitmap getImgById(int resId) {
		return BitmapFactory.decodeResource(getResources(), resId);
	}

	// ��ʼ��
	private void init() {
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		backGround1 = getImgById(R.drawable.bg_01);
		backGround2 = getImgById(R.drawable.bg_02);

		scalex = screenWidth / backGround1.getWidth(); // ��������ŵı���
		scaley = screenHeight / backGround2.getHeight();

		bgY2 = -backGround1.getHeight(); // ���ڶ���ͼƬ��ʼ���ڵ�һ��ͼƬ���Ϸ�

		// �ҵķɻ�ͼƬ
		myPlaneImg = getImgById(R.drawable.myplane);
		myPlaneSprite = new MyPlane(myPlaneImg, myPlaneImg.getWidth() / 2,
				myPlaneImg.getHeight());
		// �ҵķɻ�λ��
		initPlaneX = (PublicData.screenWidth - myPlaneSprite.getWidth()) / 2;
		initPlaneY = (PublicData.screenHeight - myPlaneSprite.getHeight());
		myPlaneSprite.setXY(initPlaneX, initPlaneY);

		// �ӵ�ͼ
		bulletImg1 = getImgById(R.drawable.bullet);
		myPlaneSprite.setBulletImg(bulletImg1);

		// ��ʼ���ҵķɻ�
		myPlaneSprite.init();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchX = event.getX();
			touchY = event.getY();
			if (myPlaneSprite.isCollWith(touchX, touchY)) {
				isPlanePressed = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isPlanePressed) {
				float pX1 = event.getX();
				float pY1 = event.getY();
				float pX0 = pX1 - touchX;
				float pY0 = pY1 -touchY;
				
				myPlaneSprite.move(pX0, pY0);
				
				touchX = pX1;
				touchY = pY1;
			}
			break;
		case MotionEvent.ACTION_UP:
			isPlanePressed = false;
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * ���ƽ���
	 */
	private void draw() {
		try {
			canvas = holder.lockCanvas();
			canvas.drawColor(Color.BLACK);
			canvas.save();
			// ���㱳��ͼƬ����Ļ�ı�����Ȼ����Ʊ���
			canvas.scale(scalex, scaley);
			canvas.drawBitmap(backGround1, 0, bgY1, paint);
			canvas.drawBitmap(backGround2, 0, bgY2, paint);
			canvas.restore();
			// ���Ʒɻ�
			myPlaneSprite.draw(canvas, paint);

			canvas.restore();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	/**
	 * �����߼�
	 */
	private void logic() {
		// ʵ�ֹ���Ч��
		bgY1 += 5;
		bgY2 += 5;
		if (bgY1 >= backGround1.getHeight()) {
			bgY1 = -backGround1.getHeight();
		}
		if (bgY2 >= backGround2.getHeight()) {
			// ����ͼƬ�Ĵ�С��һ������˭��������ν��
			bgY2 = -backGround2.getHeight();
		}

		// �ҵķɻ�
		if (gameCount % 1 == 0) {
			myPlaneSprite.logic();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		release();
	}

	/**
	 * �ͷ���Դ
	 */
	private void release() {
		isRunning = false;
	}

	@Override
	public void run() {
		while (isRunning) {
			gameCount++;
			long startTime = System.currentTimeMillis();
			draw();

			if (!isPause) {
				logic();
			}
			long endTime = System.currentTimeMillis();

			if (endTime - startTime < gameSpeed) {
				try {
					Thread.sleep(gameSpeed - (endTime - startTime));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}