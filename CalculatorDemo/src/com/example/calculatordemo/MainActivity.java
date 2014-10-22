package com.example.calculatordemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity implements OnClickListener{
	Button btn_0;
	Button btn_1;
	Button btn_2;
	Button btn_3;
	Button btn_4;
	Button btn_5;
	Button btn_6;
	Button btn_7;
	Button btn_8;
	Button btn_9;
	Button btn_point;
	Button btn_plus;
	Button btn_clear;
	Button btn_del;
	Button btn_minus;
	Button btn_multiply;
	Button btn_divide;
	Button btn_equal;
	Button btn_pow;
	Button btn_lftbracket;
	Button btn_rhtbracket;
	EditText et_input;           
	boolean clear_flag = false;             //declaration
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);                 //Hide the bar
		setContentView(R.layout.activity_main);
		btn_0=(Button)findViewById(R.id.btn_0);
		btn_1=(Button)findViewById(R.id.btn_1);
		btn_2=(Button)findViewById(R.id.btn_2);
		btn_3=(Button)findViewById(R.id.btn_3);
		btn_4=(Button)findViewById(R.id.btn_4);
		btn_5=(Button)findViewById(R.id.btn_5);
		btn_6=(Button)findViewById(R.id.btn_6);
		btn_7=(Button)findViewById(R.id.btn_7);
		btn_8=(Button)findViewById(R.id.btn_8);
		btn_9=(Button)findViewById(R.id.btn_9);
		btn_point=(Button)findViewById(R.id.btn_point);
		btn_plus=(Button)findViewById(R.id.btn_plus);
		btn_minus=(Button)findViewById(R.id.btn_minus);
		btn_divide=(Button)findViewById(R.id.btn_divide);
		btn_multiply=(Button)findViewById(R.id.btn_multiply);
		btn_clear=(Button)findViewById(R.id.btn_clear);
		btn_del=(Button)findViewById(R.id.btn_del);
		btn_lftbracket=(Button)findViewById(R.id.btn_lftbracket);
		btn_rhtbracket=(Button)findViewById(R.id.btn_rhtbracket);
		btn_pow=(Button)findViewById(R.id.btn_pow);
		btn_equal=(Button)findViewById(R.id.btn_equal);     
		et_input=(EditText)findViewById(R.id.et_input);      //Itil
		
		btn_0.setOnClickListener(this);
		btn_1.setOnClickListener(this);
		btn_2.setOnClickListener(this);
		btn_3.setOnClickListener(this);
		btn_4.setOnClickListener(this);
		btn_5.setOnClickListener(this);
		btn_6.setOnClickListener(this);
		btn_7.setOnClickListener(this);
		btn_8.setOnClickListener(this);
		btn_9.setOnClickListener(this);
		btn_point.setOnClickListener(this);
		btn_equal.setOnClickListener(this);
		btn_lftbracket.setOnClickListener(this);
		btn_rhtbracket.setOnClickListener(this);
		btn_divide.setOnClickListener(this);
		btn_plus.setOnClickListener(this);
		btn_minus.setOnClickListener(this);
		btn_multiply.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		btn_del.setOnClickListener(this);
		btn_pow.setOnClickListener(this);    //ClickEvent
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String str=et_input.getText().toString();
		Calculator calculator=new Calculator(str);
		if((v.getId()!=R.id.btn_equal)&&(v.getId()!=R.id.btn_del)&&(v.getId()!=R.id.btn_clear))
		{
			if(clear_flag){
				clear_flag=false;
				str="";
				et_input.setText(str);
			}
			et_input.setText(str+((Button)v).getText());      //Print the string
		}
		else if(v.getId()==R.id.btn_equal)
		{
			if(clear_flag){
				clear_flag=false;
				return;
			}
			clear_flag=true;
			et_input.setText(String.valueOf(calculator.output()));   //Output
			
			
		}else if(v.getId()==R.id.btn_del)
		{
			if(clear_flag){
				clear_flag=false;
				str="";
				et_input.setText(str);
			}else if(str!=""){
				if(str.length()<=0)
				{
					et_input.setText("");
				}
				else{
					et_input.setText(str.substring(0,str.length()-1));
				}														//Del one char OR del all after calculating
			}
		}
		else if(v.getId()==R.id.btn_clear)
		{
			clear_flag=false;
			str="";
			et_input.setText(str);                                   //Clear all
		}
	}
}
