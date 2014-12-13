package com.example.deadline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartView extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_view);
		
		 //�ӳ������ִ��run�����е�ҳ����ת  
        new Handler().postDelayed(new Runnable() {  
  
            @Override  
            public void run() {  
            	Intent i = new Intent(StartView.this, MainActivity.class);    
                //ͨ��Intent������������������Main���Activity
            	StartView.this.startActivity(i);    //����Main����
            	StartView.this.finish();    //�ر��Լ����������
            }  
        }, 2000);  
	}
	
}
