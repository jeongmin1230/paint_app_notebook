 package org.techtown.paint_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyPaintView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = new MyPaintView(this);

        ((LinearLayout) findViewById(R.id.ll2)).addView(myView);
    }

    public void onClick(View view) {

        switch(view.getId()){ // 텍스트뷰를 누를 때마다 switch - case 문으로 버튼 누를 때마다 글자 색이 toast 메세지로 출력됨
            case R.id.red:
                Toast.makeText(getApplicationContext(), "click red", Toast.LENGTH_SHORT).show();
                myView.mPaint.setColor(Color.parseColor("#ff0000")); // Color.parseColor("#16진수"); 하면 원하는 색으로 변경
                break;
            case R.id.orange:
                Toast.makeText(getApplicationContext(), "click orange", Toast.LENGTH_SHORT).show();
                myView.mPaint.setColor(Color.parseColor("#FFAB40"));
                break;
            case R.id.yellow:
                Toast.makeText(getApplicationContext(), "click yellow", Toast.LENGTH_SHORT).show();
                myView.mPaint.setColor(Color.parseColor("#FFFF00"));
                break;
            case R.id.green:
                Toast.makeText(getApplicationContext(), "click green", Toast.LENGTH_SHORT).show();
                myView.mPaint.setColor(Color.parseColor("#64DD17"));
                break;
            case R.id.blue:
                Toast.makeText(getApplicationContext(), "click blue", Toast.LENGTH_SHORT).show();
                myView.mPaint.setColor(Color.parseColor("#37AFF8"));
                break;
            case R.id.indigo:
                Toast.makeText(getApplicationContext(), "click indigo", Toast.LENGTH_SHORT).show();
                myView.mPaint.setColor(Color.parseColor("#304FFE"));
                break;
            case R.id.purple:
                Toast.makeText(getApplicationContext(), "click purple", Toast.LENGTH_SHORT).show();
                myView.mPaint.setColor(Color.parseColor("#AA00FF"));
                break;
            case R.id.pink:
                Toast.makeText(getApplicationContext(), "click pink", Toast.LENGTH_SHORT).show();
                myView.mPaint.setColor(Color.parseColor("#FF00BF"));
                break;
            case R.id.white:
                Toast.makeText(getApplicationContext(), "click white", Toast.LENGTH_SHORT).show();
                myView.mPaint.setColor(Color.parseColor("#FBF8F8"));
                break;
            case R.id.black:
                Toast.makeText(getApplicationContext(), "click black", Toast.LENGTH_SHORT).show();
                myView.mPaint.setColor(Color.parseColor("#000000"));
                break;
        }
    }

    public void onClickSave(View view) { // 이미지 저장 버튼 눌렀을 때 인텐트 이동
        Intent intent = new Intent(MainActivity.this, AfterSaveActivity.class);

        startActivity(intent);
    }
    private static class MyPaintView extends View {
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mPaint;
        public MyPaintView(Context context) {
            super(context);
            mPath = new Path();
            mPaint = new Paint();
            mPaint.setColor(Color.RED);
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(10);
            mPaint.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(mBitmap, 0, 0, null); //지금까지 그려진 내용
            canvas.drawPath(mPath, mPaint); //현재 그리고 있는 내용
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) { // 화면을 터치할 때의 이벤트
            int x = (int)event.getX();
            int y = (int)event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPath.reset();
                    mPath.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mPath.lineTo(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    mPath.lineTo(x, y);
                    mCanvas.drawPath(mPath, mPaint); //mBitmap 에 기록
                    mPath.reset();
                    break;
            }
            this.invalidate();
            return true;
        }
    }
}