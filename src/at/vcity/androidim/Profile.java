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
import android.widget.Toast;
import at.vcity.androidim.interfaces.IAppManager;
import at.vcity.androidim.services.IMService;
import at.vcity.androidim.tools.FriendController;
import at.vcity.androidim.types.FriendInfo;
import at.vcity.androidim.services.IMService;


 
public class Profile extends Activity {
 
    private static final int SELECT_PICTURE = 1;
 
    private String selectedImagePath;
    private EditText profiletextbox;
    private ImageView img;
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        setTitle("Edit your profile");
        EditText profile = (EditText)findViewById(R.id.profiltextbox);
        ImageView edit = (ImageView)findViewById(R.id.edit1);
        img = (ImageView)findViewById(R.id.user_photo);
        edit.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
                    }
                });
        
        
        String myValue = FriendList.getMyString();
        profile.setText(myValue);
        
        
    }
     
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                img.setImageURI(selectedImageUri);
            }
        }
    }
 
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
         
   
    
        
    
    



}





