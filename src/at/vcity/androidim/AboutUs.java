package at.vcity.androidim;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutUs extends Activity implements OnClickListener {

    
    private static Button mCloseButton;
    private static final String LOG_TAG = "About";
	
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about_us);
        setTitle(getString(R.string.about_us));
        
        
    }
    
    @Override
    public void onClick(View view) {
        if (view == mCloseButton) {
            finish();
        
        } else {
            Log.e(LOG_TAG, "onClick: view clicked is unknown");
        }
    }
}