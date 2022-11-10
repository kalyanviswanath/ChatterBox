package at.vcity.androidim;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import at.vcity.androidim.interfaces.IAppManager;
import at.vcity.androidim.services.IMService;
import at.vcity.androidim.tools.FriendController;
import at.vcity.androidim.types.FriendInfo;
import at.vcity.androidim.services.IMService;
import at.vcity.androidim.Messaging;


 
public class YourProfile extends Activity {
 
    private static final int SELECT_PICTURE = 1;
 
    private String selectedImagePath;
    private EditText profiletextbox;
    private TextView yourprofile;
    private ImageView img;
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.yourprofile);
       
        EditText profile = (EditText)findViewById(R.id.profiltextbox);
        TextView yourprofile = (TextView)findViewById(R.id.yourprofile);
        img = (ImageView)findViewById(R.id.user_photo);
       
        
        String yourpic = Messaging.yourprofilename();
        yourprofile.setText("Name: "+yourpic);
        setTitle(yourpic+"'s Profile");
        
        
        img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(YourProfile.this ,LargeProfile.class);
				startActivity(i);
				
			}
		});
        
        
        
        
        
    }
     
   
 
    
   
    
    
    



}





