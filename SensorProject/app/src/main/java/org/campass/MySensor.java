package org.campass;

import android.app.Activity;
import android.os.Bundle;

public class MySensor extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//读取布局文件
    }
}