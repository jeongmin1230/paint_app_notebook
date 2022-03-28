package org.techtown.paint_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AfterSaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_save);
    }

    public void onClickMove(View view) { // 저장 후 그림판으로 버튼 클릭했을 때 돌아가는 onClick 이벤트
        Intent intent = new Intent(AfterSaveActivity.this, MainActivity.class);

        startActivity(intent);
    }
}