package at.vcity.androidim;




import java.io.UnsupportedEncodingException;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import at.vcity.androidim.interfaces.IAppManager;
import at.vcity.androidim.services.IMService;
import at.vcity.androidim.tools.FriendController;
import at.vcity.androidim.tools.LocalStorageHandler;
import at.vcity.androidim.types.FriendInfo;
import at.vcity.androidim.types.MessageInfo;
import at.vcity.androidim.types.STATUS;
import at.vcity.androidim.FriendList;


public class PinActivity extends Activity {
	
	
	
	
private static String yourname;
	private static final int MESSAGE_CANNOT_BE_SENT = 0;
	private static String username;
	private EditText messageText;
	private EditText messageHistoryText;
	private Button sendMessageButton;
	private IAppManager imService;
	private FriendInfo friend = new FriendInfo();
	private LocalStorageHandler localstoragehandler; 
	private Cursor dbCursor;
	private String monline = " online";
	private String moffline = " offline" ;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		super.onCreate(savedInstanceState);	   
		
		setContentView(R.layout.pin_activity); //messaging_screen);
				
		messageHistoryText = (EditText) findViewById(R.id.messageHistory);
		
		messageText = (EditText) findViewById(R.id.inputMsg);
		
		messageText.requestFocus();			
		
		sendMessageButton = (Button) findViewById(R.id.btnSend);
		
		
		
		
		setTitle("Pin messages");
		
		
		
		
	//	EditText friendUserName = (EditText) findViewById(R.id.friendUserName);
	//	friendUserName.setText(friend.userName);
		
		
		
		
		sendMessageButton.setOnClickListener(new OnClickListener(){
			
			public void onClick(View arg0) {
				
				
				messageHistoryText.setText(messageText.getText());
				
				messageText.setText(" ");
				
				
				
			}});
		
		messageText.setOnKeyListener(new OnKeyListener(){
			public boolean onKey(View v, int keyCode, KeyEvent event) 
			{
				if (keyCode == 66){
					sendMessageButton.performClick();
					return true;
				}
				return false;
			}
			
			
		});
				
	}

	
	
	
}
