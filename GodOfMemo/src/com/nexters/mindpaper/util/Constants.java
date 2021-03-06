package com.nexters.mindpaper.util;

public class Constants {
	public static final int POSITION_COMPONENT_COUNT = 2; // 점들의 위치정보. x,y 총 2개.
	public static final int BYTES_PER_FLOAT = 4;
	public static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2; // 텍스쳐 위치
																		// x,y 총
																		// 2개.
																		// 텍스트에
																		// 사용.
	public static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT)
			* BYTES_PER_FLOAT; // 한 점당 필요한 변수 개수는 2+2=4개. * 1float에 필요한 byte수
								// 4바이트.

	public static final int COLOR_COMPONENT_COUNT = 4; // color. 알파체널도 포함.
														// a,r,g,b. 4개.
	public static final int COLOR_STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT)
			* BYTES_PER_FLOAT; // 한 점당 필요한 변수. 2+4 = 6개.

	public static final int FLOATS_PER_VERTEX = 6;

	// 배경관련 변수
	public static final int DOT_SIZE = 100;
	public static final int DOT_BACKGROUND_SIZE = Constants.DOT_SIZE / 20;
	public static final int SCREEN_SIZE = 16;

	//화면설정
	public static final float ZOOM_MIN= 1f;	//줌 최대
	public static final float ZOOM_MAX = 12f;	//줌 최소
	public static final float moveLimit = 10f;	//움직임여부 감도

	// 메모관련변수.
	public static final int MEMO_MAX_LINE = 7;

	// 뒤로가기 확인.
	public final static int BACK = 3;

	// 상태
	public static final int CREATE_MEMO_RESULT = 0;
	public static final int UPDATE_MEMO_RESULT = 1;
	public static final int CREATE_GROUP_RESULT = 2;
	public static final int UPDATE_GROUP_RESULT = 3;

	// 그룹관련 변수
	public static final float GROUP_DEFAULT_SIZE = 0.8f;
	// Circle 형태로 그릴 때 필요한 변수들.
	public static final int numPoints = 70;
	// 그룹 영역 크기
	public static final int dHeight = 0;
	// 그룹 중심 위치
	public static final int centerPosition = 50;
	// 그룹 최소 크기(%)
	public static final int minGroupSize = 20;
	// 그룹 최대 크기(%)
	public static final int maxGroupSize = 80;

}