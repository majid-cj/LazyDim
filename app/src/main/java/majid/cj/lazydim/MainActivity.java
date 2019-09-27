package majid.cj.lazydim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.SwitchCompat;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {
    private int time = 0, brightness = 0;
    private RadioGroup radioGroup;
    private SwitchCompat switchCompat ;
    private ChangeSettings changeSettings ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.screen_time_out_radio);
        radioGroup.setOnCheckedChangeListener((group, checkedId)->{
            switch (checkedId){
                case R.id.time_15000:{time = 15000;}break;

                case R.id.time_30000:{time = 30000;}break;

                case R.id.time_60000:{time = 60000;}break;

                case R.id.time_120000:{time = 120000;}break;

                case R.id.time_300000:{time = 300000;}break;

                case R.id.time_600000:{time = 600000;}break;

                case R.id.time_forever:{time = 0;}break;
            }
            changeSettings.ChangeSettings(time, brightness);
        });

        AppCompatSeekBar seekBar = findViewById(R.id.adjust_brightness);
        seekBar.setProgress(0);
        seekBar.setKeyProgressIncrement(51);
        seekBar.setMax(255);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightness = progress;
                switchCompat.setChecked(false);
                changeSettings.ChangeSettings(time, brightness);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        switchCompat = findViewById(R.id.adjust_brightness_auto);
        switchCompat.setOnCheckedChangeListener((button, bool) ->{
            changeSettings.AdjustToAuto(bool);
            seekBar.setProgress(0);
        });
    }

    @Override
    protected void onStart() {
        changeSettings = new ChangeSettings(this);
        switchCompat = findViewById(R.id.adjust_brightness_auto);
        switchCompat.setChecked(changeSettings.GetScreenMode() == 1);
        switch (changeSettings.GetScreenTimeOut()){
            case 15000:{
                RadioButton r = findViewById(R.id.time_15000);
                r.setChecked(true);
            }break;
            case 30000:{
                RadioButton r = findViewById(R.id.time_30000);
                r.setChecked(true);
            }break;
            case 60000:{
                RadioButton r = findViewById(R.id.time_60000);
                r.setChecked(true);
            }break;
            case 120000:{
                RadioButton r = findViewById(R.id.time_120000);
                r.setChecked(true);
            }break;
            case 300000:{
                RadioButton r = findViewById(R.id.time_300000);
                r.setChecked(true);
            }break;
            case 600000:{
                RadioButton r = findViewById(R.id.time_600000);
                r.setChecked(true);
            }break;
            default:{
                RadioButton r = findViewById(R.id.time_forever);
                r.setChecked(true);
            }break;
        }
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        changeSettings.getPmWakeLock().release();
        super.onDestroy();
    }
}
