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


 
public class LargeProfile extends Activity {
 
   
    private ImageView img;
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String yourpic = Messaging.yourprofilename();
        setContentView(R.layout.largeprofile);
        setTitle(yourpic+"'s Profile Picture");
       
        img = (ImageView)findViewById(R.id.user_photo);
       
        
        
       
        
        
        
    }
     
   
 
    
   
    
    
    



}





