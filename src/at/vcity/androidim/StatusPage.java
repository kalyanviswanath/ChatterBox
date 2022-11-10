package at.vcity.androidim;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusPage extends Activity{
	
	private EditText updateholder;
	private EditText uptext;
	private Button updatestatus;
	private ProgressDialog progressDialog;
	
	private static int SPLASH_TIME_OUT = 3000;
	
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.status);
        setTitle(getString(R.string.update));
        
        final EditText updateholder = (EditText)findViewById(R.id.updateholder);
        final EditText uptext = (EditText)findViewById(R.id.uptext);
        updatestatus = (Button)findViewById(R.id.updateStatus);
        
        updatestatus.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) 
			{					
				progressDialog = ProgressDialog.show(StatusPage.this, "", "Updating...");
				
				new Handler().postDelayed(new Runnable() {

					/*
					 * Showing splash screen with a timer. This will be useful when you
					 * want to show case your app logo / company
					 */

					@Override
					public void run() {
						progressDialog.dismiss();
						updateholder.setText(uptext.getText());
						uptext.setText(" ");
						Toast.makeText(getApplicationContext(),
								"Status updated successfull", Toast.LENGTH_SHORT)
								.show();
						
						
					}
				}, SPLASH_TIME_OUT);
				
				
				
			}
        	
        });
        
        
}
	
	
}