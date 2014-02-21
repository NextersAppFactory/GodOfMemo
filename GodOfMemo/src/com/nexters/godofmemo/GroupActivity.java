package com.nexters.godofmemo;


import com.nexters.godofmemo.object.Group;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

@SuppressLint("NewApi")
public class GroupActivity extends ActionBarActivity implements
		OnClickListener, OnSeekBarChangeListener {
	Intent intent;
	EditText input_group_et;
	
	private SeekBar bar;
	private TextView seekBarAction;
	private int width, height;
	RelativeLayout group; //ImageView는 안된다 
	
	private String groupId;
	private String groupTitle;
	private int groupColor;
	
	private final int BACK = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Load the layout
		setContentView(R.layout.activity_group);
		
		
		input_group_et = (EditText) findViewById(R.id.group_name_text);
		
		// 커스텀 액션바
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.actionbar_group);

		//화면 넓이와 높이 구하기 
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		
		
		//그룹이미지를 위치한다 
		group = (RelativeLayout) findViewById(R.id.group_img);
		//group.setX(width/10);
		//group.setY(height/10);
		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width/10, height/10);
		layoutParams.addRule(RelativeLayout.BELOW, R.id.seekBar);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.seekBarAction);
		//layoutParams.addRule(RelativeLayout.m, anchor)
		//layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, R.id.seekBarAction);
		group.setLayoutParams(layoutParams);
		
		bar = (SeekBar) findViewById(R.id.seekBar); // make seekbar object
		bar.setOnSeekBarChangeListener(this); // set seekbar listener.
		// since we are using this class as the listener the class is "this"

		// make text label for seekBarAction value
		seekBarAction = (TextView) findViewById(R.id.seekBarAction);

		findViewById(R.id.btn_back).setOnClickListener(this);
		findViewById(R.id.btn_finish).setOnClickListener(this);
		ImageView trash_can = (ImageView) findViewById(R.id.trash_can);
		trash_can.setOnClickListener(this);
		
		// get Intent
		intent = getIntent();
		// When you update the group's status.
		//TODO Handling tab event that user select Group!
		groupId = intent.getStringExtra("selectedGroupId");
		groupTitle = intent.getStringExtra("selectedGroupTitle");
		groupColor = intent.getIntExtra("selectedGroupColor", Group.DEFAULT_GROUP_COLOR);
			
		if(groupTitle==null){
			trash_can.setVisibility(View.GONE);
		}else{
			input_group_et.setText(groupTitle);
			//TODO set groupColor
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_finish:
			createGroup();
			break;
		case R.id.btn_back:
			moveToBack();
			break;
		case R.id.trash_can:
			deleteGroup();
			break;
		}
	}
	
	private void createGroup(){
		
		// case group create
		String input_group_title_text = "";
		if(input_group_et == null){
			System.out.println("Didn't get text");
		}else{
			input_group_title_text = input_group_et.getText().toString();
		}
		intent.putExtra("newGroupTitle", input_group_title_text);
		
		String updateGroupTitle = input_group_title_text;
				//case status update
		intent.putExtra("selectedGroupId", groupId);
		//TODO You must write code selecting color. 
		intent.putExtra("selectedGroupColor", groupColor);
		intent.putExtra("selectedGroupTitle", updateGroupTitle);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	private void deleteGroup(){
		intent.putExtra("selectedGroupId", groupId);
		intent.putExtra("delete", true);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	private void moveToBack(){
		intent.putExtra("checkBack", BACK);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
		seekBarAction.setText(progress + "% 움직였습니다." + width);
		// TODO int progress 받아와서 그룹이미지 크기 조정
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				100 + (progress * 10), 100 + (progress * 10)); //parameter(x, y)
		layoutParams.addRule(RelativeLayout.BELOW, R.id.seekBar);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.seekBarAction);
		group.setLayoutParams(layoutParams);
	}

	// 지금 필요없음
	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
	}
}
