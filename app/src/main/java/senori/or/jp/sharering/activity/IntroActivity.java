package senori.or.jp.sharering.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import senori.or.jp.sharering.R;

public class IntroActivity extends Activity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this).fromResource().load(R.drawable.intro).into(imageView);

        beforeIntro();
    }

    private void beforeIntro() {
        // 약 2초간 인트로 화면을 출력.
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                finish();
            }
        }, 3000);
    }
}
