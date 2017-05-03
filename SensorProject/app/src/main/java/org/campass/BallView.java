package org.campass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;

public class BallView extends View implements SensorEventListener {
	private Bitmap ball = null;
	private float[] allValue;
	private Point point = new Point();
	private int xSpeed = 0;
	private int ySpeed = 0;

	public BallView(Context context, AttributeSet attrs) {
		super(context, attrs);
		super.setBackgroundColor(Color.WHITE); // ��ɫΪ��ɫ
		this.ball = BitmapFactory.decodeResource(super.getResources(),
				R.drawable.ball);
		SensorManager manager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE); // ����ֻ���ҵ���һ��������������û�ж�������
		manager.registerListener(this,
				manager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME); // ������һ���ʺ�����Ϸ�����ķ�λ������
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) { // ��������λ�ı�
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) { // �����Ƿ�λ������
			float value[] = event.values; // ȡ�����е�ƫ������
			BallView.this.allValue = value; // ȡ���������ֵ
			super.postInvalidate(); // ���̵߳���ʵ��Ҫ�ػ�
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint p = new Paint(); // ���ݴ���������ֵ���ı�����ٶ�
		if (this.allValue != null) { // �Ѿ�ȡ��������
			this.xSpeed = (int) -this.allValue[2]; // ����X���ٶ�
			this.ySpeed = (int) -this.allValue[1];
		}
		this.point.x += this.xSpeed;
		this.point.y += this.ySpeed;
		if (this.point.x < 0) {
			this.point.x = 0;
		}
		if (this.point.y < 0) {
			this.point.y = 0;
		}
		if (point.x > super.getWidth() - this.ball.getWidth()) { // X���Ѿ���ʾ����
			this.point.x = super.getWidth() - this.ball.getWidth();
		}
		if (point.y > super.getHeight() - this.ball.getHeight()) {
			this.point.y = super.getHeight() - this.ball.getWidth(); // ����Y ��ı߽�
		}
		canvas.drawBitmap(this.ball, this.point.x, this.point.y, p);
	}

}
