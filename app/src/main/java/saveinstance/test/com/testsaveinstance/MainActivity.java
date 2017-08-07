package saveinstance.test.com.testsaveinstance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String TAG_POSITION = "position";

    private EditText etMessage = null;
    private Button btnSave,btnNew;

    private TextView tvMessage = null;

    private String message = "";
    private int position = 0;

    private String temp = "";
    private String temp1 = "";

    public static void startMe(Context context, int position) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(TAG_POSITION, position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        position = getIntent().getIntExtra(TAG_POSITION,0);

        Log.e(TAG,"onCreate"+position);

        etMessage = (EditText) findViewById(R.id.etMessage);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnNew = (Button) findViewById(R.id.btnNew);
        tvMessage = (TextView) findViewById(R.id.tvMessage);



        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "保存", Toast.LENGTH_SHORT).show();

                message = etMessage.getText().toString();
            }
        });
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.startMe(v.getContext(),position+1);
            }
        });
        //多次加载消耗内存，测试出现oom如何恢复现场
        temp = FileUtils.readByAssets(this,"countrys.json");
        temp1 = FileUtils.readByAssets(this,"countrys.json");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart"+position);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e(TAG,"onResume"+position);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onPause"+position);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"onStop"+position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy"+position);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.e(TAG,"onSaveInstanceState"+position);
        savedInstanceState.putString("message", message);
        savedInstanceState.putInt("position", position);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        message = savedInstanceState.getString("message");
        position = savedInstanceState.getInt("position");

        Log.e(TAG,"onRestoreInstanceState"+position);
        Toast.makeText(getApplicationContext(), message+position, Toast.LENGTH_LONG).show();

    }
}
