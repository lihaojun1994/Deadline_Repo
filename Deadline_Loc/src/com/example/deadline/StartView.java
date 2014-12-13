package com.example.deadline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartView extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_view);
		
		 //延迟两秒后执行run方法中的页面跳转  
        new Handler().postDelayed(new Runnable() {  
  
            @Override  
            public void run() {  
            	Intent i = new Intent(StartView.this, MainActivity.class);    
                //通过Intent打开最终真正的主界面Main这个Activity
            	StartView.this.startActivity(i);    //启动Main界面
            	StartView.this.finish();    //关闭自己这个开场屏
            }  
        }, 2000);  
	}
	
}
