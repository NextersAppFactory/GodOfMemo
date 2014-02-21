package com.nexters.godofmemo;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.nexters.godofmemo.dao.MemoDAO;
import com.nexters.godofmemo.object.Memo;
import com.nexters.godofmemo.util.Font;
import com.nexters.godofmemo.view.MemoGLView;

public class MainActivity extends ActionBarActivity implements OnClickListener{
	/**
	 * Hold a reference to our GLSurfaceView
	 */
	private MemoGLView glSurfaceView;
	private boolean rendererSet = false;
	public static final int CREATE_RESULT= 0;
	public static final int UPDATE_RESULT= 1;
	public static final int CREATE_GROUP= 2;
	private String memoContent;
	private String memoId;
	private MemoDAO memoDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//폰트 초기화
		Font.setTf(Typeface.createFromAsset(getAssets(), "fonts/nanum.ttf"));
		
		//커스톰 액션바 구현.
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getSupportActionBar().setCustomView(R.layout.actionbar_memoboard);
		
		//쓰기버튼에 클릭이벤트를 등록한다 
		findViewById(R.id.action_write).setOnClickListener(this);
		findViewById(R.id.action_group).setOnClickListener(this);


		// Check if the system supports OpenGL ES 2.0.
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();

		/*
		 * final boolean supportsEs2 = configurationInfo.reqGlEsVersion >=
		 * 0x20000;
		 */

		// Even though the latest emulator supports OpenGL ES 2.0,
		// it has a bug where it doesn't set the reqGlEsVersion so
		// the above check doesn't work. The below will detect if the
		// app is running on an emulator, and assume that it supports
		// OpenGL ES 2.0.
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000
				|| (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 && (Build.FINGERPRINT
						.startsWith("generic")
						|| Build.FINGERPRINT.startsWith("unknown")
						|| Build.MODEL.contains("google_sdk")
						|| Build.MODEL.contains("Emulator") || Build.MODEL
							.contains("Android SDK built for x86")));

		if (supportsEs2) {
			glSurfaceView = new MemoGLView(this);
			rendererSet = true;
		} else {
			/*
			 * This is where you could create an OpenGL ES 1.x compatible
			 * renderer if you wanted to support both ES 1 and ES 2. Since we're
			 * not doing anything, the app will crash if the device doesn't
			 * support OpenGL ES 2.0. If we publish on the market, we should
			 * also add the following to AndroidManifest.xml:
			 * 
			 * <uses-feature android:glEsVersion="0x00020000"
			 * android:required="true" />
			 * 
			 * This hides our app from those devices which don't support OpenGL
			 * ES 2.0.
			 */
			Toast.makeText(this, "This device does not support OpenGL ES 2.0.",
					Toast.LENGTH_LONG).show();
			return;
		}

		setContentView(glSurfaceView);

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (rendererSet) {
			glSurfaceView.onPause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (rendererSet) {
			glSurfaceView.onResume();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		memoDao = new MemoDAO(getApplicationContext());
		//비정상종료면?
		if(resultCode != Activity.RESULT_OK) return;
		
		// 또 다른 액티비티를 사용하고 나서 결과 값으로 생성화면과 수정화면을 구분한다.
		switch(requestCode){
		case CREATE_RESULT:
			//뒤로가기 버튼을 눌렀는지 체크
			if(data.getIntExtra("checkBack",0)!=0) return;
			memoContent = data.getStringExtra("short_txt");
			
			//TODO 새 메모 체크하기 
			//메모를 저장한다.
			Memo newMemo = new Memo(getApplicationContext(), memoContent, glSurfaceView);
			//지금 시간을 구한다
			long curr = System.currentTimeMillis();
			//setter
			newMemo.setProdTime(curr);
			
			//새로 생성한 메모에 아이디를 설정. 
			long memoIdL = memoDao.insertMemo(newMemo);
			memoId = String.valueOf(memoIdL);
			newMemo.setMemoId(memoId);
			
			//새 메모가 생겼을때 토스트
			String newText = "새 메모!";
			createToast(newText);
			
			//화면에 그릴 목록에 추가
			glSurfaceView.mr.memoList.add(newMemo);
			break;
			
		case UPDATE_RESULT:
			if(data.getIntExtra("checkBack",0)!=0) return;
			
			memoContent = data.getStringExtra("short_txt");
			memoId = data.getStringExtra("selectedMemoId");
			
			
			// 휴지통 버튼을 눌렀는지 체크
			if(data.getBooleanExtra("delete", false)){
				Memo deleteMemo =  memoDao.getMemoInfo(memoId);
				memoDao.delMemo(deleteMemo);
				createToast("메모 삭제");
				return;
			}
			
			// 수정된 메모 정보를 갱신한다.
			Memo updateMemo = memoDao.getMemoInfo(memoId);
			updateMemo.setProdTime(System.currentTimeMillis());
			updateMemo.setMemoContent(memoContent);
			memoDao.updateMemo(updateMemo);
			
			//새로 그리기 위해.
			removeMemo(updateMemo);
			glSurfaceView.mr.memoList.add(updateMemo);

			break;
			
		case CREATE_GROUP:
			System.out.println("Group activity");
			break;
		}
		
	}
	
	/**
	 * renderer의 memoList안에 있는 memo를 지우는 로직을 메서드화.
	 */
	private void removeMemo(Memo updateMemo){
		glSurfaceView.mr.memoList.remove(updateMemo);
	}
	
	/**
	 * 토스트를 굽는다.
	 * @param text
	 */
	private void createToast(String text){
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(this, text, duration);
		toast.show();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_write:
			Intent intent = new Intent(this, MemoActivity.class);
			startActivityForResult(intent, CREATE_RESULT);
			break;
			
		case R.id.action_group:
			Intent groupIntent = new Intent(this, GroupActivity.class);
			startActivityForResult(groupIntent, CREATE_GROUP);
			break;
		}
		
	}
}
