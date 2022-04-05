 package org.techtown.paint_app;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

 public class MainActivity extends AppCompatActivity  {

    Dialog dialog_thickness, dialog_eraser;
    Button btnThicknessPen, btnThicknessEraser;
    private MyPaintView myView;

    // toggle 기능을 위해 count 변수 선언 -> 버튼을 누를 때마다 증가시켜서 버튼 활성화, 비활성화 할 것임
     int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = new MyPaintView(this);

        btnThicknessPen = findViewById(R.id.btnThicknessPen);
        btnThicknessEraser = findViewById(R.id.btnThicknessEraser);

        ((LinearLayout) findViewById(R.id.paintArea)).addView(myView);

        // storage 접근 권한 확인
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getApplicationContext(), "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
                }

                requestPermissions(new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            }
        }
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
        switch (view.getId()) { // 펜 굵기 버튼 눌렀을 때
            case R.id.rb_thin: //가늘게 버튼을 눌렀을 때
                Toast.makeText(getApplicationContext(), "펜 굵기를 [가늘게]로 선택했습니다.", Toast.LENGTH_SHORT).show();
                dialog_thickness.dismiss();
                myView.mPaint.setStrokeWidth(10);
                break;

            case R.id.rb_middle: //중간 버튼을 눌렀을 때
                Toast.makeText(getApplicationContext(), "펜 굵기를 [중간]으로 선택했습니다.", Toast.LENGTH_SHORT).show();
                dialog_thickness.dismiss();
                myView.mPaint.setStrokeWidth(20);
                break;

            case R.id.rb_thick: //두껍게 버튼을 눌렀을 때
                Toast.makeText(getApplicationContext(), "펜 굵기를 [두껍게]로 선택했습니다.", Toast.LENGTH_SHORT).show();
                dialog_thickness.dismiss();
                myView.mPaint.setStrokeWidth(30);
                break;
        }
        switch (view.getId()) { // 지우개 버튼 눌렀을 때
            case R.id.rb_thin_eraser: //가늘게 지우개 버튼을 눌렀을 때
                Toast.makeText(getApplicationContext(), "지우개 굵기를 [가늘게]로 선택했습니다.", Toast.LENGTH_SHORT).show();
                dialog_eraser.dismiss();
                myView.mPaint.setColor(Color.parseColor("#F0F4C3")); // 방법을 못찾아서 그냥 펜을 배경 색으로 변경..
                myView.mPaint.setStrokeWidth(10);
                break;

            case R.id.rb_middle_eraser: //중간 지우개 버튼을 눌렀을 때
                Toast.makeText(getApplicationContext(), "지우개 굵기를 [중간]으로 선택했습니다.", Toast.LENGTH_SHORT).show();
                dialog_eraser.dismiss();
                myView.mPaint.setColor(Color.parseColor("#F0F4C3"));
                myView.mPaint.setStrokeWidth(20);
                break;

            case R.id.rb_thick_eraser: //두껍게 지우개 버튼을 눌렀을 때
                Toast.makeText(getApplicationContext(), "지우개 굵기를 [두껍게]로 선택했습니다.", Toast.LENGTH_SHORT).show();
                dialog_eraser.dismiss();
                myView.mPaint.setColor(Color.parseColor("#F0F4C3"));
                myView.mPaint.setStrokeWidth(30);
                break;
        }
    }

    public void onClickSave(View view) { // 이미지 저장 버튼 눌렀을 때 이미지 저장하고 인텐트 이동
        Intent intent = new Intent(MainActivity.this, AfterSaveActivity.class);

        LinearLayout paintArea = (LinearLayout) findViewById(R.id.paintArea); // paintArea 영역을 가져옴

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss"); // 올해년도몇월며칠_몇시몇분몇초 형식으로 포맷하겠다.
        LayoutCapture(paintArea, simpleDateFormat.format(new Date()));

        startActivity(intent);
    }

    public void onClickPenDialog(View view) { // 펜 굵기 선택
        dialog_thickness = new Dialog(MainActivity.this); // Dialog 초기화
        dialog_thickness.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog_thickness.setContentView(R.layout.dialog_thickness); // 만든 dialog_thickness 랑 연결
        dialog_thickness.setCancelable(false); // 다이얼로그 영역 밖 눌러도 꺼지지 않도록 설정
        dialog_thickness.show(); // 다이얼로그 띄우기

        if(count % 2 == 0) { // count 가 짝수 일 경우
            btnThicknessEraser.setEnabled(true);
            btnThicknessEraser.setBackgroundColor((Color.parseColor("#EDE7F6")));

            btnThicknessPen.setEnabled(false);
            btnThicknessPen.setBackgroundColor((Color.parseColor("#E1BEE7")));
        }
        count++;
        Log.i("jeongmin", "펜 버튼 눌렀을 때의 count : "+ count);
    }

    public void onClickEraserDialog(View view)  { // 지우개
        dialog_eraser = new Dialog(MainActivity.this); // Dialog 초기화
        dialog_eraser.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog_eraser.setContentView(R.layout.dialog_eraser); // 만든 dialog_eraser 랑 연결
        dialog_eraser.setCancelable(false); // 다이얼로그 영역 밖 눌러도 꺼지지 않도록 설정
        dialog_eraser.show(); // 다이얼로그 띄우기

        if(count % 2 == 1) { // count 가 홀수 일 경우
            btnThicknessPen.setEnabled(true);
            btnThicknessPen.setBackgroundColor((Color.parseColor("#EDE7F6")));

            btnThicknessEraser.setEnabled(false);
            btnThicknessEraser.setBackgroundColor((Color.parseColor("#E1BEE7")));
        }
        count++;
        Log.i("jeongmin", "지우개 버튼 눌렀을 경우 count : "+ count);
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
            mPaint.setColor(Color.BLACK);
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
     // LayoutCapture 클래스 생성
     public void LayoutCapture(View view, String title) {
         if(view == null) { // 뷰가 비어있을 경우의 예외처리
             Log.i("jeongmin", "<LayoutCapture 클래스 if문 안>view is null!!");
             return;
         }
         Log.i("jeongmin", "<LayoutCapture 클래스 안>view is not null!!");

         view.buildDrawingCache(); // 캐시 비트맵 만들기
         Bitmap bitmap = view.getDrawingCache();
         FileOutputStream fos = null;

         // 파일 저장
         String strPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()+ "/paint";

         Log.i("jeongmin", "<LayoutCapture 클래스 안>경로 : " + strPath);

         File uploadFolder = new File(strPath);
         Log.i("jeongmin", "<LayoutCapture 클래스 안>업로드 경로 설정 : view_capture 라는 폴더 생성해서 그 폴더에 저장 하려고 위에처럼 경로 설정" + uploadFolder);

         String fileName = title + ".jpg";

         File tempFile = new File(strPath, fileName);

         try {
             if(!uploadFolder.exists()) { // 만약 지정 폴더가 없으면 생성
                 uploadFolder.mkdirs();
             }

             fos = new FileOutputStream(tempFile); // 경로 + 제목 + .jpg 로 FileOutputStream Setting
             bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
             Toast.makeText(getApplicationContext(), "이미지 파일로 저장했습니다.", Toast.LENGTH_SHORT).show();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
             Log.i("jeongmin", "<try - catch 의 catch 문 속>");
             Log.i("jeongmin", "파일을 찾을 수 없습니다.");
         }
         // 이미지 스캐닝 해서 갤러리에서 보이게 해 주는 코드
         sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(tempFile)));
     }
}