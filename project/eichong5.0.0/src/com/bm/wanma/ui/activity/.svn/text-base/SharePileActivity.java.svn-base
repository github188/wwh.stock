package com.bm.wanma.ui.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.bm.wanma.R;
import com.bm.wanma.net.NetFile;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author cm
 * 分享电桩
 */
public class SharePileActivity extends BaseActivity implements OnClickListener{

	private ImageButton ib_back,ib_upload_photo;
	private ImageView iv_photo,iv_icon;
	private TextView tv_address,tv_lng,tv_lat,tv_commit;
	private EditText et_content;
	private String content,currentLat,currentLng,allMultiFile,currentAddr;
	
	//private TakePhotoDialog takePhotoDialog;
	/* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    //修改经纬度
    private static final int CODE_LATLNG_REQUEST = 0xa3;
    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 960;
    private static int output_Y = 960;
    private  Bitmap photoBitmap,nocropbm;
    private Uri photoUri;
    public static final int SELECT_PIC_BY_TAKE_PHOTO = 0xa4;
	public static final int SELECT_PIC_BY_PICK_PHOTO = 0xa5;
	/** 获取到的图片路径 */
	private String picPath = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_pile);
		ib_back = (ImageButton) findViewById(R.id.share_pile_back);
		ib_back.setOnClickListener(this);
		iv_photo = (ImageView) findViewById(R.id.share_pile_photo_iv);
		ib_upload_photo = (ImageButton) findViewById(R.id.share_pile_photo_btn);
		ib_upload_photo.setOnClickListener(this);
		iv_icon = (ImageView) findViewById(R.id.share_pile_icon_addr);
		iv_icon.setOnClickListener(this);
		tv_address = (TextView) findViewById(R.id.share_pile_tv_addr);
		tv_address.setOnClickListener(this);
		tv_lng = (TextView) findViewById(R.id.share_pile_lng);
		tv_lat = (TextView) findViewById(R.id.share_pile_lat);
		tv_commit = (TextView) findViewById(R.id.commit_feedback_commit);
		et_content = (EditText) findViewById(R.id.share_pile_content);	
		et_content.addTextChangedListener(new MyRegistTextWatch());
		// 获取当前经纬度
		currentLat = PreferencesUtil.getStringPreferences(this, "currentlat");
		currentLng = PreferencesUtil.getStringPreferences(this, "currentlng");
		currentAddr = PreferencesUtil.getStringPreferences(this,
				"currentaddres");
		tv_address.setText(""+currentAddr);
		tv_lng.setText(currentLng);
		tv_lat.setText(currentLat);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.share_pile_back:
			finish();
			break;
		case R.id.share_pile_photo_btn:
			//上传图片
			selectPhoto();
			break;
		case R.id.share_pile_icon_addr:
		case R.id.share_pile_tv_addr:
			//修改经纬度
			Intent checkIn = new Intent();
			checkIn.setClass(SharePileActivity.this, CheckLatLng.class);
			startActivityForResult(checkIn, CODE_LATLNG_REQUEST);
			
			break;
		case R.id.commit_feedback_commit:
			//发布桩体
			if(isNetConnection()){
				currentAddr = tv_address.getText().toString();//.replaceAll(" ", "")
				currentLat = tv_lat.getText().toString();
				currentLng = tv_lng.getText().toString();
				content = et_content.getText().toString();
				showPD("正在提交信息，请稍等..");
				NetFile.getInstance(this).sharePile(handler, currentAddr, currentLng, currentLat, content, allMultiFile);
				
			}else {
				showToast("网络不稳，请稍后再试");
			}
			
			break;
		default:
			break;
		}
		
	}
	
	private void selectPhoto(){
		/*takePhotoDialog = new TakePhotoDialog(this);
		takePhotoDialog.show();
		TextView takePhoto = (TextView) takePhotoDialog.getTakephoto();
		TextView selectPhoto = (TextView) takePhotoDialog.getSelectphoto();
		TextView canclePhoto = (TextView) takePhotoDialog.getCanclephoto();
		takePhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//choseHeadImageFromCameraCapture();
				takePhotoNoCrop();
				takePhotoDialog.dismiss();
			}
		});
		selectPhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				takePhotoDialog.dismiss();
				//choseHeadImageFromGallery();
				pickPhotoNoCrop();
				
			}
		});
		canclePhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				takePhotoDialog.dismiss();
			}
		}); */
	}
	
	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		cancelPD();
		showToast(bundle.getString(Protocol.MSG));
		finish();

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		showToast(bundle.getString(Protocol.MSG));
		

	}
	
	 @Override
	   protected void onActivityResult(int requestCode, int resultCode,
	           Intent intent) {
		   
		   if(resultCode == RESULT_CANCELED){
			   
	   			return;
	   		
	   		}
			switch (requestCode) {

			case SELECT_PIC_BY_TAKE_PHOTO:
				doPhoto(requestCode, intent);
				break;
			case SELECT_PIC_BY_PICK_PHOTO:
				//showToast("intent" +intent);
				doPhoto(requestCode, intent);
				break;

			case CODE_GALLERY_REQUEST:
				// intent.getData();
				// final Bitmap photo = intent.getParcelableExtra("data");
				if (intent != null) {
					cropRawPhoto(intent.getData());
				}

				break;

			case CODE_CAMERA_REQUEST:
				if (hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory(),
							IMAGE_FILE_NAME);
					cropRawPhoto(Uri.fromFile(tempFile));
				} else {
					showToast("没有SDCard!");
				}

				break;

			case CODE_RESULT_REQUEST:
				if (intent != null) {
					setImageToHeadView(intent);
				}

				break;
			case CODE_LATLNG_REQUEST:
				//修改经纬度
				tv_lat.setText(""+intent.getStringExtra("correctlat"));
				tv_lng.setText(""+intent.getStringExtra("correctlng"));
				tv_address.setText(""+intent.getStringExtra("addressName"));
				
				break;
			}

	       super.onActivityResult(requestCode, resultCode, intent);
	   }
	  /**
   	 * 图库选择获取图片
   	 */
     private void pickPhotoNoCrop(){
    	 Intent intent = new Intent();
 		// 如果要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
 		intent.setType("image/*");
 		intent.setAction(Intent.ACTION_GET_CONTENT);
 		startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
     }
	 /**
  	 * 拍照获取图片
  	 */
  	private void takePhotoNoCrop() {
  	    // 执行拍照前，应该先判断SD卡是否存在
  		
  	    String SDState = Environment.getExternalStorageState();
  	    if (SDState.equals(Environment.MEDIA_MOUNTED)) {
  	        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
  	        /***
  	         * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 
  	         * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
  	         * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
  	         */
  	        ContentValues values = new ContentValues();
  	        photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
  	        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
  	        startActivityForResult(intent, SELECT_PIC_BY_TAKE_PHOTO);
  	    } else {
  	        Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
  	    } 
  	}
private void doPhoto(int requestCode, Intent data) {
		
		// 从相册取图片，有些手机有异常情况，请注意
		if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
				if (data == null) {
					Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
					return;
				}
				photoUri = data.getData();
				if (photoUri == null) {
					Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
					return;
				}
		}
		String[] pojo = { MediaColumns.DATA };
		Cursor cursor = getContentResolver().query(photoUri, pojo, null, null, null);
		if (cursor != null) {
			int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
			cursor.moveToFirst();
			picPath = cursor.getString(columnIndex);
			LogUtil.i("cm_picpath", picPath);
			// 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
			if (Integer.parseInt(Build.VERSION.SDK) < 14) {
				cursor.close();
			}
			
			BitmapFactory.Options option = new BitmapFactory.Options();
			// 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图
			option.inSampleSize = 4;
			// 根据图片的SDCard路径读出Bitmap
			  if(nocropbm != null){
					nocropbm.recycle();
					nocropbm = null;
		       	}
			nocropbm = BitmapFactory.decodeFile(picPath, option);
			iv_photo.setImageBitmap(nocropbm);
	        allMultiFile = saveBitmap2file(nocropbm);
	        //deleteFile(picPath);
	      //picPath保存图片
			/*File saveToFile = new File(picPath);
			if(saveToFile.exists()){
				saveToFile.delete();
			}*/
	      
		}

	}
/**
 * 将bitmap转换为file
 * @param bmp
 * @param filename
 */
public static String saveBitmap2file(Bitmap bmp) {
	 
	/*int size = bmp.getByteCount();
	LogUtil.i("cm_size", "" +size/1024);*/
	
	Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
	String localPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/eichong/";
	String filename = System.currentTimeMillis()+".jpg";
	File file = new File(localPath);
	if (!file.exists()) {
		file.mkdirs();
	}
	File saveFile = new File(localPath + filename);
	int quality = 70;//70 是压缩率，表示压缩30%; 如果不压缩是100，
	FileOutputStream stream = null;
	try {
		stream = new FileOutputStream(saveFile);
		bmp.compress(format, quality, stream);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	return localPath + filename;
}
/**
 * 提取保存裁剪之后的图片数据，并设置头像部分的View
 */
private void setImageToHeadView(Intent intent) {
    Bundle extras = intent.getExtras();
    if (extras != null) {
    	if(photoBitmap != null){
    		photoBitmap.recycle();
    		photoBitmap = null;
    	}
        photoBitmap = extras.getParcelable("data");
        iv_photo.setImageBitmap(photoBitmap);
        allMultiFile = saveBitmap2file(photoBitmap);
    }
}
/**
 * 裁剪原始的图片
 */
public void cropRawPhoto(Uri uri) {

    Intent intent = new Intent("com.android.camera.action.CROP");
    intent.setDataAndType(uri, "image/*");

    // 设置裁剪
    intent.putExtra("crop", "true");
    // aspectX , aspectY :宽高的比例
    intent.putExtra("aspectX", 1);
    intent.putExtra("aspectY", 1);
    // outputX , outputY : 裁剪图片宽高
    intent.putExtra("outputX", output_X);
    intent.putExtra("outputY", output_Y);
    intent.putExtra("return-data", true);

    startActivityForResult(intent, CODE_RESULT_REQUEST); 
}
/**
 * 检查设备是否存在SDCard的工具方法
 */
public static boolean hasSdcard() {
    String state = Environment.getExternalStorageState();
    if (state.equals(Environment.MEDIA_MOUNTED)) {
        // 有存储的SDCard
        return true;
    } else {
        return false;
    }
}	 

	private class MyRegistTextWatch implements TextWatcher{
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}
		@Override
		public void onTextChanged(CharSequence str, int start, int before,
				int count) {}
		@SuppressLint("NewApi")
		@Override
		public void afterTextChanged(Editable s) {
			String contents = s.toString().trim();
			int length = contents.length();
			if (length == 0) {
				//输入框没值，立即充值置灰
				tv_commit.setOnClickListener(null);
				tv_commit.setBackground(getResources().getDrawable(
						R.drawable.recharge_commit_bg_light_white));
			}else {
				tv_commit.setOnClickListener(SharePileActivity.this);
				tv_commit.setBackground(getResources().getDrawable(
						R.drawable.popup_select_shape_confirm));
			}
		}
	}

}
