package org.campass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;

public class ArrowView extends View implements SensorEventListener {
	private Bitmap comp = null;
	private float[] allValue;

	public ArrowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		super.setBackgroundColor(Color.WHITE); // ��ɫΪ��ɫ
		this.comp = BitmapFactory.decodeResource(super.getResources(),
				R.drawable.arrow);
		SensorManager manager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE); // ����ֻ���ҵ���һ��������������û�ж�������
		manager.registerListener(this,
				manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_GAME); // ������һ���ʺ�����Ϸ�����Ĵų�������
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) { // ��������λ�ı�
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) { // �����Ƿ�λ������
			float value[] = event.values; // ȡ�����е�ƫ������
			ArrowView.this.allValue = value; // ȡ���������ֵ
			super.postInvalidate(); // ���̵߳���ʵ��Ҫ�ػ�
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint p = new Paint(); // ���ݴ���������ֵ���ı�����ٶ�
		if (this.allValue != null) { // �Ѿ�ȡ��������
			float x = this.allValue[0] ;
			float y = this.allValue[1] ;
			canvas.restore(); // ���û�ͼ����
			// ��������Ļ���ĵ���Ϊ��ת����
			canvas.translate(super.getWidth() / 2, super.getHeight() / 2) ;
			// �ж�y���Ƿ�Ϊ0����ת�Ƕ�
			if (y == 0 && x > 0) {
				canvas.rotate(90) ;	// ��ת�Ƕ�Ϊ90��
			} else if (y == 0 && x < 0) {
				canvas.rotate(270) ;	// ��ת�Ƕ�Ϊ270��
			} else {	// ����x��y��ֵ������ת�Ƕȣ�������ǶȾ�������tan()ֵ������
				if(y >= 0) {
					canvas.rotate((float) Math.tanh(x / y) * 90);
				} else {
					canvas.rotate(180 + (float) Math.tanh(x / y) * 90);
				}
			}
		}
		canvas.drawBitmap(this.comp, -this.comp.getWidth() / 2,
				-this.comp.getHeight() / 2, p);
	}

}
