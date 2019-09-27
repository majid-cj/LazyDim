package majid.cj.lazydim;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

public class ChangeSettings {
    private Context context;
    private PowerManager.WakeLock pmWakeLock ;
    public ChangeSettings(Context context) {
        this.context = context;
    }

    public void SetScreenTimeOut(int timeout, int brightness){
        android.provider.Settings.System.putInt(this.context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, (timeout == 0)? Integer.MAX_VALUE: timeout);
        Settings.System.putInt(this.context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Settings.System.putInt(this.context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
        ForceWake(timeout);
    }

    public void ForceWake(int timeout){
        PowerManager powerManager = (PowerManager)this.context.getSystemService(Context.POWER_SERVICE);
        this.pmWakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP, "my wake tag:");
        switch (timeout){
            case 0: this.pmWakeLock.acquire();break;
            default: this.pmWakeLock.acquire(timeout);break;
        }
    }

    public void ChangeSettings(int timeout, int brightness){
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(Settings.System.canWrite(this.context)){
                    SetScreenTimeOut(timeout, brightness);
                }else{
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + this.context.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.context.startActivity(intent);
                }
            }else {
                SetScreenTimeOut(timeout, brightness);
            }
        }catch (Exception e){
            Log.i("CAUGHT_SOMETHING", e.getMessage());
        }
    }

    public void AdjustToAuto(boolean checked){
        try{
            Settings.System.putInt(this.context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    (checked)? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC: Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }catch (Exception e){
            Log.i("CAUGHT_SOMETHING", e.getMessage());
        }
    }

    public int GetScreenMode(){
        return Settings.System.getInt(this.context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, 0);
    }

    public int GetScreenTimeOut(){
        return Settings.System.getInt(this.context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 0);
    }

    public int GetScreenBrightness(){
        return Settings.System.getInt(this.context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
    }

    public PowerManager.WakeLock getPmWakeLock(){
        return  this.pmWakeLock;
    }
}
