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
	private Bitmap comp = null;//Bitmap绘图
	private float[] allValue;//传感器数值

	public ArrowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		super.setBackgroundColor(Color.WHITE); // 底色为白色
		this.comp = BitmapFactory.decodeResource(super.getResources(),
				R.drawable.arrow);//取得资源图片
		SensorManager manager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE); // 取得传感器服务
		manager.registerListener(this,
				manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),//磁场传感器
				SensorManager.SENSOR_DELAY_GAME); // 适合于游戏更新速率
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}//传感器监听方法

	@Override
	public void onSensorChanged(SensorEvent event) { // 传感器方位改变
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) { // 现在是磁场传感器
			float value[] = event.values; // 取得三个轴的值
			ArrowView.this.allValue = value; //保存值
			super.postInvalidate(); // 主线程重绘
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {  // 根据传感器的数值来改变图片的方向
		super.onDraw(canvas);
		Paint p = new Paint(); //绘图对象
		if (this.allValue != null) {
			float x = this.allValue[0] ;//获取X轴坐标
			float y = this.allValue[1] ;//获取Y轴坐标
			canvas.restore(); // 重置绘图对象
			// 设置以屏幕中心点作为旋转中心
			canvas.translate(super.getWidth() / 2, super.getHeight() / 2) ;
			// 判断y轴是否为0的旋转角度
			if (y == 0 && x > 0) {
				canvas.rotate(90) ;	// 旋转角度为90度
			} else if (y == 0 && x < 0) {
				canvas.rotate(270) ;	// 旋转角度为270度
			} else {	// 根据x和y的值计算旋转角度，而这个角度就是依靠tan()值来计算
				if(y >= 0) {
					canvas.rotate((float) Math.tanh(x / y) * 90);
				} else {
					canvas.rotate(180 + (float) Math.tanh(x / y) * 90);
				}
			}
		}
		canvas.drawBitmap(this.comp, -this.comp.getWidth() / 2, -this.comp.getHeight() / 2, p);//绘制
	}

}
