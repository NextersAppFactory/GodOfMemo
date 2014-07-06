package com.nexters.godofmemo.object.helper;

import com.nexters.godofmemo.data.VertexArray;
import com.nexters.godofmemo.util.BitmapHelper;
import com.nexters.godofmemo.util.Font;
import com.nexters.godofmemo.util.TextureHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.FloatMath;

public class GroupHelper {

	private int texture;
	private int textTexture;

	public GroupHelper() {
		// TODO Auto-generated constructor stub
	}

	public static float[] getGroupVertices(int numPoints,
			int FLOATS_PER_VERTEX, float x, float y, float red, float green,
			float blue, float radius) {

		int size = sizeOfCircleInVertices(numPoints);

		float[] VERTEX_DATA = new float[size * FLOATS_PER_VERTEX];
		// Order of coordinates: X, Y, texture's X, texture's Y
		// 일단은 texture 배제.

		int ai = 128;
		float a = ai / 255.0f;
		// rgb 253, 245, 229
		// rgb 140, 211, 156
		int offset = 0;

		// Center point of fan
		VERTEX_DATA[offset++] = x;
		VERTEX_DATA[offset++] = y;
		VERTEX_DATA[offset++] = red; // r
		VERTEX_DATA[offset++] = green; // g
		VERTEX_DATA[offset++] = blue; // b
		VERTEX_DATA[offset++] = a; // a

		// Fan around center point. <= is used because we want to generate
		// the point at the starting angle twice to complete the fan.
		for (int i = 0; i <= numPoints; i++) {

			// radian 계산하기. 360도에 대한 각도의 비율을 통해서 radian 을 구할 수 있다. l = r * 2PI *
			// (angle/360) = r * 2PI * (i/numPoints)
			float angleInRadians = ((float) i / (float) numPoints)
					* ((float) Math.PI * 2f);
			// center = (gx,gy) 이후에 center class 생성.
			VERTEX_DATA[offset++] = x + radius * FloatMath.cos(angleInRadians);
			VERTEX_DATA[offset++] = y + radius * FloatMath.sin(angleInRadians);
			VERTEX_DATA[offset++] = red; // r
			VERTEX_DATA[offset++] = green; // g
			VERTEX_DATA[offset++] = blue; // b
			VERTEX_DATA[offset++] = a; // a
		}

		return VERTEX_DATA;
	}

	public static float[] getTextVertices(float x, float y, float width,
			float height, float ratioW, float ratioH) {
		// System.out.println("setTextVertices");
		float[] VERTEX_DATA_TEXT = new float[24];

		width = width * ratioW;
		height = height * ratioH;

		// 중심.
		int s = 0;
		VERTEX_DATA_TEXT[0] = x; // x
		VERTEX_DATA_TEXT[1] = y; // y
		VERTEX_DATA_TEXT[2] = 0.5f * ratioW;// S
		VERTEX_DATA_TEXT[3] = 0.5f * ratioH; // T

		// 왼쪽 아래
		s++;
		VERTEX_DATA_TEXT[s * 4 + 0] = x - width / 2; // x
		VERTEX_DATA_TEXT[s * 4 + 1] = y - height / 2; // y
		VERTEX_DATA_TEXT[s * 4 + 2] = 0f * ratioW; // z
		VERTEX_DATA_TEXT[s * 4 + 3] = 1f * ratioH; // z

		// 오른쪽 아래
		s++;
		VERTEX_DATA_TEXT[s * 4 + 0] = x + width / 2; // x
		VERTEX_DATA_TEXT[s * 4 + 1] = y - height / 2; // y
		VERTEX_DATA_TEXT[s * 4 + 2] = 1f * ratioW; // z
		VERTEX_DATA_TEXT[s * 4 + 3] = 1f * ratioH; // z

		// 오른쪽 위에
		s++;
		VERTEX_DATA_TEXT[s * 4 + 0] = x + width / 2; // x
		VERTEX_DATA_TEXT[s * 4 + 1] = y + height / 2; // y
		VERTEX_DATA_TEXT[s * 4 + 2] = 1f * ratioW; // z
		VERTEX_DATA_TEXT[s * 4 + 3] = 0f * ratioH; // z

		// 왼쪽 위에
		s++;
		VERTEX_DATA_TEXT[s * 4 + 0] = x - width / 2; // x
		VERTEX_DATA_TEXT[s * 4 + 1] = y + height / 2; // y
		VERTEX_DATA_TEXT[s * 4 + 2] = 0f * ratioW; // z
		VERTEX_DATA_TEXT[s * 4 + 3] = 0f * ratioH; // z

		// 왼쪽 아래
		s++;
		VERTEX_DATA_TEXT[s * 4 + 0] = x - width / 2; // x
		VERTEX_DATA_TEXT[s * 4 + 1] = y - height / 2; // y
		VERTEX_DATA_TEXT[s * 4 + 2] = 0f * ratioW; // z
		VERTEX_DATA_TEXT[s * 4 + 3] = 1f * ratioH; // z

		return VERTEX_DATA_TEXT;
	}

	// Return size of a circle built out of a triangle fan
	public static int sizeOfCircleInVertices(int numPoints) {
		return 1 + (numPoints + 1);
	}
	

	public VertexArray setColorVertices(float x, float y, float red, float green, float blue, int width, int height) {

		int ai = 128;
		float a = ai / 255.0f;
		// rgb 253, 245, 229
		// rgb 140, 211, 156

		float[] VERTEX_DATA_COLOR = new float[36];

		// Order of coordinates: X, Y, R, G, B

		// point 1
		int s = 0;
		VERTEX_DATA_COLOR[s * 6 + 0] = x; // x
		VERTEX_DATA_COLOR[s * 6 + 1] = y; // y
		VERTEX_DATA_COLOR[s * 6 + 2] = red; // r
		VERTEX_DATA_COLOR[s * 6 + 3] = green; // g
		VERTEX_DATA_COLOR[s * 6 + 4] = blue; // b
		VERTEX_DATA_COLOR[s * 6 + 5] = a; // a

		// point 2
		s++;
		VERTEX_DATA_COLOR[s * 6 + 0] = x - width / 2; // x
		VERTEX_DATA_COLOR[s * 6 + 1] = y - height / 2; // y
		VERTEX_DATA_COLOR[s * 6 + 2] = red; // r
		VERTEX_DATA_COLOR[s * 6 + 3] = green; // g
		VERTEX_DATA_COLOR[s * 6 + 4] = blue; // b
		VERTEX_DATA_COLOR[s * 6 + 5] = a; // a

		// point 3
		s++;
		VERTEX_DATA_COLOR[s * 6 + 0] = x + width / 2; // x
		VERTEX_DATA_COLOR[s * 6 + 1] = y - height / 2; // y
		VERTEX_DATA_COLOR[s * 6 + 2] = red; // r
		VERTEX_DATA_COLOR[s * 6 + 3] = green; // g
		VERTEX_DATA_COLOR[s * 6 + 4] = blue; // b
		VERTEX_DATA_COLOR[s * 6 + 5] = a; // a

		// point 4
		s++;
		VERTEX_DATA_COLOR[s * 6 + 0] = x + width / 2; // x
		VERTEX_DATA_COLOR[s * 6 + 1] = y + height / 2; // y
		VERTEX_DATA_COLOR[s * 6 + 2] = red; // r
		VERTEX_DATA_COLOR[s * 6 + 3] = green; // g
		VERTEX_DATA_COLOR[s * 6 + 4] = blue; // b
		VERTEX_DATA_COLOR[s * 6 + 5] = a; // a

		// point 5
		s++;
		VERTEX_DATA_COLOR[s * 6 + 0] = x - width / 2; // x
		VERTEX_DATA_COLOR[s * 6 + 1] = y + height / 2; // y
		VERTEX_DATA_COLOR[s * 6 + 2] = red; // r
		VERTEX_DATA_COLOR[s * 6 + 3] = green; // g
		VERTEX_DATA_COLOR[s * 6 + 4] = blue; // b
		VERTEX_DATA_COLOR[s * 6 + 5] = a; // a

		// point 6
		s++;
		VERTEX_DATA_COLOR[s * 6 + 0] = x - width / 2; // x
		VERTEX_DATA_COLOR[s * 6 + 1] = y - height / 2; // y
		VERTEX_DATA_COLOR[s * 6 + 2] = red; // r
		VERTEX_DATA_COLOR[s * 6 + 3] = green; // g
		VERTEX_DATA_COLOR[s * 6 + 4] = blue; // b
		VERTEX_DATA_COLOR[s * 6 + 5] = a; // a

		return new VertexArray(VERTEX_DATA_COLOR);
	}
	

	/**
	 * 텍스트만 그리는 함수
	 * 
	 * @param gContext
	 * @param gResId
	 * @param gText
	 * @param ratioW 
	 * @param ratioH 
	 * @param maxLine 
	 * @return
	 */
	public static Bitmap drawTextToBitmap(String gText, int ratioW, int ratioH, int maxLine) {

		int width = 512;
		int height = 512;

		// Read in the resource
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// Canvas
		Canvas canvas = new Canvas(bitmap);
		// new antialised Paint
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// text color - #3D3D3D
		paint.setColor(Color.rgb(61, 61, 61));
		// text size in pixels
		int textSize = (int) (32);
		paint.setTextSize(textSize);
		// text shadow
		// paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

		// ##########
		// 텍스트 여러줄 처리
		// ##########
		String dividedText = BitmapHelper.getDividedText(gText);

		// 텍스트를 줄바꿈 단위로 쪼갠다.
		String[] dividedTextArray = dividedText.split("\n");

		// draw text to the Canvas center
		// TODO group에 적합하도록.
		int x = (int) (width * ratioW / 2);
		int y = (int) (height * ratioH / 2);

		int loopCnt = 0;
		int textoffsetYY = 0;
		int marginY = 4;
		int marginX = 10;
		int offsetY = (textSize + marginY) / 1;

		// 몇번 포문을 수행할지 결정
		if (dividedTextArray.length < maxLine) {
			loopCnt = dividedTextArray.length;
		} else {
			loopCnt = maxLine;
		}

		// 시작 높이 위치 정하기
		textoffsetYY = y - (offsetY / 2) * (loopCnt - 1) + marginY;

		// 폰트 설정
		paint.setTypeface(Font.getTf());

		// 여러줄 출력하기
		for (int i = 0; i < loopCnt; i++) {
			String text = dividedTextArray[i];
			int px = x - (text.length() * textSize) / 2 + marginX;
			int py = textoffsetYY + (i * offsetY);
			canvas.drawText(text, px, py, paint);
		}

		return bitmap;
	}
	
	//
	//

	// 텍스쳐 설정
	public void setTexture(int textureSource, Context context, Bitmap textBitmap, int textBitmapId) {
		if (textureSource != 0) {
			// 텍스쳐를 불러보고
			this.texture = TextureHelper.loadTexture(context, textureSource);
		} else if (textBitmap != null) {
			// 비트맵이 있으면 비트맵 텍스쳐를 입힌다.
			this.texture = TextureHelper.loadBitmpTexture(textBitmap,
					textBitmapId);
			this.textTexture = TextureHelper.loadTextBitmpTexture(this);
		}
	}
}
