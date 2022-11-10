package at.vcity.androidim;




import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import at.vcity.androidim.interfaces.IAppManager;
import at.vcity.androidim.services.IMService;
import at.vcity.androidim.tools.FriendController;
import at.vcity.androidim.types.FriendInfo;
import at.vcity.androidim.types.STATUS;


public class FriendList extends ListActivity 
{
	private static final int action_group = Menu.FIRST;
	private static final int PROFILE = Menu.FIRST + 1;
	private static final int STATUSMENU = Menu.FIRST + 2;
	private static final int ABOUT_US = Menu.FIRST + 3;
	private static final int LOGOUT = Menu.FIRST + 4;
	private static final int EXIT_APP_ID = Menu.FIRST + 5;
	private IAppManager imService = null;
	private FriendListAdapter friendAdapter;
	private String monline = "Online";
	private String moffline = "Offline";
	private String checkstat;
	
	
	public static String ownusername = new String();

	private class FriendListAdapter extends BaseAdapter 
	{		
		class ViewHolder {
			
			TextView text;
			ImageView icon;
		}
		private LayoutInflater mInflater;
		private Bitmap mOnlineIcon;
		private Bitmap mOfflineIcon;		

		private FriendInfo[] friends = null;


		public FriendListAdapter(Context context) {
			super();			

			mInflater = LayoutInflater.from(context);

			mOnlineIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.greenstar);
			mOfflineIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.redstar);

		}

		public void setFriendList(FriendInfo[] friends)
		{
			this.friends = friends;
		}


		public int getCount() {		

			return friends.length;
		}
		

		public FriendInfo getItem(int position) {			

			return friends[position];
		}

		public long getItemId(int position) {

			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// A ViewHolder keeps references to children views to avoid unneccessary calls
			// to findViewById() on each row.
			ViewHolder holder;

			// When convertView is not null, we can reuse it directly, there is no need
			// to reinflate it. We only inflate a new View when the convertView supplied
			// by ListView is null.
			if (convertView == null) 
			{
				convertView = mInflater.inflate(R.layout.friend_list_screen, null);

				// Creates a ViewHolder and store references to the two children views
				// we want to bind data to.
				holder = new ViewHolder();
				
				holder.text = (TextView) convertView.findViewById(R.id.text);
				holder.icon = (ImageView) convertView.findViewById(R.id.icon);                                       

				convertView.setTag(holder);
			}   
			else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
				holder = (ViewHolder) convertView.getTag();
			}

			// Bind the data efficiently with the holder.
			holder.text.setText(friends[position].userName);
			holder.icon.setImageBitmap(friends[position].status == STATUS.ONLINE ? mOnlineIcon : mOfflineIcon);
			
			
			return convertView;
		}

	}

	
	
	
	
	public class MessageReceiver extends  BroadcastReceiver  {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			Log.i("Broadcast receiver ", "received a message");
			Bundle extra = intent.getExtras();
			if (extra != null)
			{
				String action = intent.getAction();
				if (action.equals(IMService.FRIEND_LIST_UPDATED))
				{
					// taking friend List from broadcast
					//String rawFriendList = extra.getString(FriendInfo.FRIEND_LIST);
					//FriendList.this.parseFriendInfo(rawFriendList);
					FriendList.this.updateData(FriendController.getFriendsInfo(), 
												FriendController.getUnapprovedFriendsInfo());
					
				}
			}
		}

	};
	public MessageReceiver messageReceiver = new MessageReceiver();

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {          
			imService = ((IMService.IMBinder)service).getService();      
			
			FriendInfo[] friends = FriendController.getFriendsInfo(); //imService.getLastRawFriendList();
			if (friends != null) {    			
				FriendList.this.updateData(friends, null); // parseFriendInfo(friendList);
			}    
			
			setTitle(imService.getUsername() + "'s chat home");
			ownusername = imService.getUsername();
			
			
		}
		public void onServiceDisconnected(ComponentName className) {          
			imService = null;
			Toast.makeText(FriendList.this, R.string.local_service_stopped,
					Toast.LENGTH_SHORT).show();
		}
	};
	
	
	
	public static String getMyString(){
	    return ownusername;
	}

	protected void onCreate(Bundle savedInstanceState) 
	{		
		super.onCreate(savedInstanceState);

        setContentView(R.layout.list_screen);
        
		friendAdapter = new FriendListAdapter(this);
		
		


	}
	public void updateData(FriendInfo[] friends, FriendInfo[] unApprovedFriends)
	{
		if (friends != null) {
			friendAdapter.setFriendList(friends);	
			setListAdapter(friendAdapter);				
		}				
		
		if (unApprovedFriends != null) 
		{
			NotificationManager NM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			
			if (unApprovedFriends.length > 0)
			{					
				String tmp = new String();
				for (int j = 0; j < unApprovedFriends.length; j++) {
					tmp = tmp.concat(unApprovedFriends[j].userName).concat(",");			
				}
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
		    	.setSmallIcon(R.drawable.ic_chatter_box)
		    	.setContentTitle(getText(R.string.new_friend_request_exist));
				/*Notification notification = new Notification(R.drawable.stat_sample, 
						getText(R.string.new_friend_request_exist),
						System.currentTimeMillis());*/

				Intent i = new Intent(this, UnApprovedFriendList.class);
				i.putExtra(FriendInfo.FRIEND_LIST, tmp);				

				PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
						i, 0);

				mBuilder.setContentText("You have new friend request(s)");
				/*notification.setLatestEventInfo(this, getText(R.string.new_friend_request_exist),
												"You have new friend request(s)", 
												contentIntent);*/
				
				mBuilder.setContentIntent(contentIntent);

				
				NM.notify(R.string.new_friend_request_exist, mBuilder.build());			
			}
			else
			{
				// if any request exists, then cancel it
				NM.cancel(R.string.new_friend_request_exist);			
			}
		}

	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);		

		Intent i = new Intent(this, Messaging.class);
		FriendInfo friend = friendAdapter.getItem(position);
		i.putExtra(FriendInfo.USERNAME, friend.userName);
		i.putExtra(FriendInfo.PORT, friend.port);
		i.putExtra(FriendInfo.IP, friend.ip);		
		startActivity(i);
	}




	@Override
	protected void onPause() 
	{
		unregisterReceiver(messageReceiver);		
		unbindService(mConnection);
		super.onPause();
	}

	@Override
	protected void onResume() 
	{
			
		super.onResume();
		bindService(new Intent(FriendList.this, IMService.class), mConnection , Context.BIND_AUTO_CREATE);

		IntentFilter i = new IntentFilter();
		//i.addAction(IMService.TAKE_MESSAGE);	
		i.addAction(IMService.FRIEND_LIST_UPDATED);

		registerReceiver(messageReceiver, i);			
		

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	 //   return super.onCreateOptionsMenu(menu);
		boolean result = super.onCreateOptionsMenu(menu);		

		menu.add(0, action_group, 0, R.string.action_group);
		menu.add(0, PROFILE, 0, R.string.profile);
		menu.add(0, STATUSMENU, 0, R.string.statusmenu);
		menu.add(0, ABOUT_US, 0, R.string.about_us);
		menu.add(0, LOGOUT, 0, R.string.logout);
		menu.add(0, EXIT_APP_ID, 0, R.string.exit_application);		
		
		return result;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) 
	{		

		switch(item.getItemId()) 
		{	  
		case R.id.addFriend:
		{
			Intent i = new Intent(FriendList.this, AddFriend.class);
			startActivity(i);
			return true;
		}
		
		
			case action_group:
			{
				showDialog1();
				break;
			}		
			case PROFILE:
			{
				Intent i = new Intent(FriendList.this, Profile.class);
				startActivity(i);
				return true;
			}	
			case STATUSMENU:
			{
				Intent i = new Intent(FriendList.this, StatusPage.class);
				startActivity(i);
				return true;
			}	
			case EXIT_APP_ID:
			{
				imService.exit();
				finish();
				return true;
			}
			case LOGOUT:
			{
				showDialogMenu();
		        break;
			}	
			case ABOUT_US:
			{
				Intent i = new Intent(FriendList.this, AboutUs.class);
				startActivity(i);
				return true;
			}
		}

		return super.onMenuItemSelected(featureId, item);		
	}	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
	
		
		
	}
	
	
	public void showDialogMenu()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(FriendList.this);

		// Setting Dialog Title
		alertDialog.setTitle("Logout");

		// Setting Dialog Message
		alertDialog.setMessage("Are you sure you want to logout?");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.logout);

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
						// Write your code here to execute after dialog
						Intent i = new Intent(FriendList.this, Login.class);
						startActivity(i);
						//Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
					}
				});
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,	int which) {
						// Write your code here to execute after dialog
						//Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}
	
	
	public void showDialog1()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(
				FriendList.this).create();

		// Setting Dialog Title
		alertDialog.setTitle("Group chat");

		// Setting Dialog Message
		alertDialog.setMessage("This module is under development");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.ic_action_add_group);

		// Setting OK Button
		alertDialog.setButton("OK",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog,
							int which) {
						// Write your code here to execute after dialog
						// closed
						Toast.makeText(getApplicationContext(),
								"Thanks for your cooperation", Toast.LENGTH_SHORT)
								.show();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}
	
	
}
