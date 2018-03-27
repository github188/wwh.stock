package com.bm.wanma.ui.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.wanma.R;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.dialog.DoubleDatePickerDialog;
import com.bm.wanma.dialog.TakePhotoDialog;
import com.bm.wanma.entity.AreaBean;
import com.bm.wanma.entity.CityBean;
import com.bm.wanma.entity.ProvinceBean;
import com.bm.wanma.entity.UserInfoBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.NetFile;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.HeadImageUtils;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.view.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author cm
 * 我的个人资料界面
 */
public class MyUserInfoActivity extends BaseActivity implements OnClickListener{
	
	
	private ImageButton ib_back;
	private TextView tv_nick,tv_birthday,tv_area,tv_model,tv_addr,tv_car;
	private RoundImageView iv_photo;
	private RelativeLayout rl_photo,rl_nick,rl_car,rl_birthday,rl_area,rl_model;
	private TakePhotoDialog takePhotoDialog;
	private File outputImage;
	private Uri captureimageUri;
	private String allMultiFile,carName,carId;
	private static final int CODE_SELECT_CARTYPE_REQUEST = 0xa4;
	private FinalDb finalDb;
	private UserInfoBean userInfoBean;
	private String pkUserinfo,nickName,address,car;
	private String proviceCode,proviceName,cityCode,cityName ,areaCode,areaname;
	public static final int NICK = 0x01;
	public static final int SEX = 0x02;
	public static final int AREA = 0x03;
	public static final int CAR = 0x04;
	public static final int ADDR = 0x05;
	public static final int CARSIZE = 0x06;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myuserinfo_detail);
		finalDb = FinalDb.create(getActivity(),Protocol.DATABASE_NAME,true,Protocol.dbNumer,null);
		userInfoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfo");
		initView(); 
		pkUserinfo = PreferencesUtil.getStringPreferences(MyUserInfoActivity.this, "pkUserinfo");
	}

	private void initView(){
		ib_back = (ImageButton) findViewById(R.id.settings_back);
		ib_back.setOnClickListener(this);
		rl_photo = (RelativeLayout) findViewById(R.id.myuserinfo_photo_rl);
		rl_photo.setOnClickListener(this);
		iv_photo = (RoundImageView) findViewById(R.id.myuserinfo_photo_imageview);
		rl_nick = (RelativeLayout) findViewById(R.id.myuserinfo_nick_rl);
		rl_nick.setOnClickListener(this);
		tv_nick = (TextView) findViewById(R.id.myuserinfo_nick);
		rl_car = (RelativeLayout) findViewById(R.id.myuserinfo_car_rl);
		rl_car.setOnClickListener(this);
		rl_birthday = (RelativeLayout) findViewById(R.id.myuserinfo_birthday_rl);
		rl_birthday.setOnClickListener(this);
		tv_birthday = (TextView) findViewById(R.id.myuserinfo_birthday);
		rl_area = (RelativeLayout) findViewById(R.id.myuserinfo_area_rl);
		rl_area.setOnClickListener(this);
		tv_area = (TextView) findViewById(R.id.myuserinfo_area);
		rl_model = (RelativeLayout) findViewById(R.id.myuserinfo_model_rl);
		rl_model.setOnClickListener(this);
		tv_model = (TextView) findViewById(R.id.myuserinfo_model);
		tv_addr = (TextView) findViewById(R.id.myuserinfo_addr);
		tv_car = (TextView) findViewById(R.id.myuserinfo_car);
		tv_addr.setOnClickListener(this);
		initValueToView(userInfoBean);
	}
	
	private void initValueToView(UserInfoBean bean){
		if(!Tools.isEmptyString(bean.getUserImage())){
			DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.default_user_img)
			.showImageOnFail(R.drawable.default_user_img) 
			.cacheInMemory(true)
			.cacheOnDisk(false)
			.bitmapConfig(Config.RGB_565)
			.build();
			ImageLoader.getInstance().displayImage(bean.getUserImage(), iv_photo, options);
		}
		nickName = userInfoBean.getUserNickName();
		tv_nick.setText(""+nickName);
//		usinSex = bean.getUserSex();
//		//0 男 1 女属性
//		if("0".equals(usinSex)){
//			tv_sex.setText("男");
//		}else if("1".equals(usinSex)){
//			tv_sex.setText("女");
//		}
		carName = bean.getUserCarTypeName();
		carId = bean.getUserCarType();
		tv_model.setText(""+carName);//车的品牌
		//转换城市区编码对应的名称
		if(!TextUtils.isEmpty(bean.getpCode())){
			// tempP = finalDb.findAllByWhere(ProvinceBean.class, "PROVINCE_ID="+bean.getpCode()).get(0).getPROVINCE_NAME();
			 List<ProvinceBean> tempLP = finalDb.findAllByWhere(ProvinceBean.class, "PROVINCE_ID="+bean.getpCode());
			 if(tempLP.size()>0){
				 proviceName = tempLP.get(0).getPROVINCE_NAME();
			 }
		}else{
			proviceName = " ";
		}
		if(!TextUtils.isEmpty(bean.getaCode())){
			 List<CityBean> tempLC = finalDb.findAllByWhere(CityBean.class, "CITY_ID="+bean.getcCode());
			 if(tempLC.size()>0){
				 cityName = tempLC.get(0).getCITY_NAME();
			 }
		}else {
			cityName = " ";
		}
		
		if(!TextUtils.isEmpty(bean.getaCode())){
			 List<AreaBean> tempLC = finalDb.findAllByWhere(AreaBean.class, "AREA_ID = "+bean.getaCode());
			 if(tempLC.size()>0){
				 areaname = tempLC.get(0).getAREA_NAME();
			 }
		}else {
			areaname = " ";
		}
		
		if(!TextUtils.isEmpty(proviceName)){
			tv_area.setText(proviceName+"  "+cityName+"  "+areaname);
		}
		try {			
			car = bean.getPlateNum();
		} catch (Exception e) {
			car = "";
		}

		address = bean.getAddress();
		if (!Tools.isEmptyString(bean.getUserBrithy())) {			
			tv_birthday.setText(TimeUtil.parseDate(bean.getUserBrithy(),
 				   "yyyy-MM-dd", "yyyy-MM-dd"));
		}
		if (!Tools.isEmptyString(car)) {			
			tv_car.setText(car);
		}
		if (!Tools.isEmptyString(address)) {			
			tv_area.setText(address);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.settings_back:
			finish();
			break;
		case R.id.myuserinfo_nick_rl:
			//昵称
			Intent nickIn = new Intent(MyUserInfoActivity.this,NickActivity.class);
			nickIn.putExtra("nick", nickName);
			startActivityForResult(nickIn, NICK);
			break;
		case R.id.myuserinfo_car_rl:
			//车牌号
			Intent carIn = new Intent(MyUserInfoActivity.this,CarActivity.class);
			carIn.putExtra("Car", car);
			startActivityForResult(carIn, CARSIZE);
			break;
		case R.id.myuserinfo_birthday_rl:
			//
			Calendar c = Calendar.getInstance();
			// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
			new DoubleDatePickerDialog(MyUserInfoActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
						int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
						int endDayOfMonth) {
					String textString = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);
						textString = TimeUtil.parseDate(textString,
				 				   "yyyy-M-d", "yyyy-MM-dd");
					GetDataPost.getInstance(getActivity()).modifyUserInfo(handler, pkUserinfo, "", "", textString, "");
					PreferencesUtil.setPreferences(MyUserInfoActivity.this, "usinBirthdate",
							textString);
					tv_birthday.setText(textString);
				}
			}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
			break;
		case R.id.myuserinfo_area_rl:
			//地区
			Intent areaIn = new Intent(MyUserInfoActivity.this,AreaActivity.class);
			//areaIn.putExtra("sex", usinSex);
			startActivityForResult(areaIn, AREA);
			break;
//		case R.id.myuserinfo_model_rl:
//			//座驾
//			Intent carIn = new Intent(MyUserInfoActivity.this,CarBrandActivity.class);
//			startActivityForResult(carIn, CAR);
//			break;
//		case R.id.myuserinfo_addr:
//			//地址
//			Intent addrIn = new Intent(MyUserInfoActivity.this,AddressActivity.class);
//			addrIn.putExtra("address", address);
//			startActivityForResult(addrIn, ADDR);
//			break;
		case R.id.myuserinfo_photo_rl:
			//选择头像
			selectPhoto();
			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void getData() {

	}
	private void commitcar(){
		if(isNetConnection()){
			GetDataPost.getInstance(getActivity()).modifyUserInfo(handler, pkUserinfo, "", "", "", car);
		}else {
			showToast("网络连接异常，请稍后再试...");
		}
	}
//	private void commitSex(){
//		if(isNetConnection()){
//			NetFile.getInstance(MyUserInfoActivity.this).modifyPersonalInfo(handler, pkUserinfo, null, 
//					null,usinSex, null, null, null, null, null);
//		}else {
//			showToast("网络连接异常，请稍后再试...");
//		}
//	}
	
	
	private void commitCarName(){
//		if(isNetConnection()){
//			NetFile.getInstance(MyUserInfoActivity.this).modifyPersonalInfo(handler, pkUserinfo, null, 
//					null, null, carId, null, null, null, null);
//		}else {
//			showToast("网络连接异常，请稍后再试...");
//		}
	}
	
	private void commitProvince(){
		if(isNetConnection()){
			NetFile.getInstance(MyUserInfoActivity.this).modifyPersonalInfo(handler, pkUserinfo, null, 
					null, null, null, null, proviceCode, cityCode, areaCode);
		}else {
			showToast("网络连接异常，请稍后再试...");
		}
	}
	
	private void commitAddress(){
		if(isNetConnection()){
			GetDataPost.getInstance(getActivity()).modifyUserInfo(handler, pkUserinfo, "", address, "", "");
		}else {
			showToast("网络连接异常，请稍后再试...");
		}
	}
	private void commitNick(){
		if(isNetConnection()){
			GetDataPost.getInstance(getActivity()).modifyUserInfo(handler, pkUserinfo, nickName, "", "", "");
		}else {
			showToast("网络连接异常，请稍后再试...");
		}
	}
	private void commitPhoto(){
		if(isNetConnection()){
			NetFile.getInstance(MyUserInfoActivity.this).modifyPersonalInfo(handler, pkUserinfo, allMultiFile, 
					null, null, null, null, null, null, null);
		}else {
			showToast("网络连接异常，请稍后再试...");
		}
	}

	
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode,
	            Intent intent) {
	        if (resultCode == RESULT_CANCELED) {
				return;
			}
			switch (requestCode) {
			
			case NICK:
				if(!TextUtils.isEmpty(intent.getStringExtra("nick")) && 
						!intent.getStringExtra("nick").equals(nickName)){
					nickName = intent.getStringExtra("nick");
					tv_nick.setText(nickName +"");
					commitNick();
				}
				break;
			case CARSIZE:
				if(!TextUtils.isEmpty(intent.getStringExtra("Car")) && 
						!intent.getStringExtra("Car").equals(car)){
					car = intent.getStringExtra("Car");
					//0 男 1 女属性
					tv_car.setText(""+car);
					
					commitcar();
				}
				break;
//			case SEX:
//				if(!TextUtils.isEmpty(intent.getStringExtra("sex")) && 
//						!intent.getStringExtra("sex").equals(usinSex)){
//					usinSex = intent.getStringExtra("sex");
//					//0 男 1 女属性
//					if("0".equals(usinSex)){
//						tv_sex.setText("男");
//					}else if("1".equals(usinSex)){
//						tv_sex.setText("女");
//					}
//					commitSex();
//				}
//				break;
			case ADDR:
				if(!TextUtils.isEmpty(intent.getStringExtra("address")) && 
						!intent.getStringExtra("address").equals(address)){
					address = intent.getStringExtra("address");
					commitAddress();
				}
				break;
			case CAR:
					if(!TextUtils.isEmpty(intent.getStringExtra("carTypeId")) && 
							!intent.getStringExtra("carTypeId").equals(carId)){
						carId = intent.getStringExtra("carTypeId");
						carName = intent.getStringExtra("carType");
						tv_model.setText(""+carName);//车的品牌
						commitCarName();
					}
				 
				
				break;
			case AREA:
				Toast.makeText(this, 
						"  pCode"+intent.getStringExtra("pCode")+"  " +
						"cCode"+intent.getStringExtra("cCode")
						
						+"  aCode"+intent.getStringExtra("aCode")
						
						+"  areaname"+intent.getStringExtra("areaname")
						
						+"  cityName"+intent.getStringExtra("cityName")
						
						+"  proviceName"+intent.getStringExtra("proviceName"),
						0).show();
				if(!TextUtils.isEmpty(intent.getStringExtra("areaname")) && 
						!intent.getStringExtra("areaname").equals(areaname)){
					areaname = intent.getStringExtra("areaname");
					cityName = intent.getStringExtra("cityName");
					proviceName = intent.getStringExtra("provinceName");
					
					
					areaCode = intent.getStringExtra("aCode");
					cityCode = intent.getStringExtra("cCode");
					proviceCode = intent.getStringExtra("pCode");
					tv_area.setText(proviceName+"  "+cityName+" "+areaname);
					commitProvince();
				}
				break;
			case 1111:
			//if (resultCode == RESULT_OK) {
				Intent intenttt = new Intent("com.android.camera.action.CROP");
				intenttt.setDataAndType(captureimageUri, "image/*");
				intenttt.putExtra("scale", true);
				intenttt.putExtra("aspectX",1);
				intenttt.putExtra("aspectY", 1);
				intenttt.putExtra("return-data", false);
				intenttt.putExtra(MediaStore.EXTRA_OUTPUT, captureimageUri);
				startActivityForResult(intenttt, 2222);// 启动裁剪程序
			//	}
				break;
			case 2222:
				try {
					//if (resultCode == RESULT_OK) {
						/*Bitmap bitmap = BitmapFactory
								.decodeStream(getContentResolver().openInputStream(
										captureimageUri));*/
						Bitmap bitmap = BitmapFactory.decodeFile(outputImage.getAbsolutePath());
						bitmap = resizeBitmap(bitmap, 320, 320);
						iv_photo.setImageURI(captureimageUri);
						allMultiFile = saveBitmap2file(bitmap);
						commitPhoto();
						outputImage.delete();
					//}
				} catch (Exception e) {
					e.printStackTrace();
				}


				break;
			
			// 拍照获取图片
			case HeadImageUtils.GET_IMAGE_BY_CAMERA:
				// uri传入与否影响图片获取方式,以下二选一
				// 方式一,自定义Uri(ImageUtils.imageUriFromCamera),用于保存拍照后图片地址
				if(HeadImageUtils.imageUriFromCamera != null) {
					// 可以直接显示图片,或者进行其他处理(如压缩或裁剪等)
					//iv.setImageURI(ImageUtils.imageUriFromCamera);
					// 对图片进行裁剪
					HeadImageUtils.cropImage(this, HeadImageUtils.imageUriFromCamera);
					break;
				}
				
				break;
			// 手机相册获取图片
			case HeadImageUtils.GET_IMAGE_BY_GALLARY:
				if(intent != null && intent.getData() != null) {
					// 可以直接显示图片,或者进行其他处理(如压缩或裁剪等)
					// iv.setImageURI(data.getData());
					// 对图片进行裁剪
					HeadImageUtils.cropImage(this, intent.getData());
				}
				break;
			// 裁剪图片后结果
			case HeadImageUtils.CROP_IMAGE:
				if(HeadImageUtils.cropImageUri != null) {
					// 可以直接显示图片,或者进行其他处理(如压缩等)
					iv_photo.setImageURI(HeadImageUtils.cropImageUri);
					Bitmap bm = convertUri2Bitmap(HeadImageUtils.cropImageUri);
					allMultiFile = saveBitmap2file(bm);
					commitPhoto();
				}
				break;
				//选择车型处理
			  case CODE_SELECT_CARTYPE_REQUEST:
		        	if(intent != null){
		        		/*carType = intent.getStringExtra("carType");
		        		usinCarinfoId = intent.getStringExtra("carTypeId");
		            		tv_model.setText(carType+"");*/
		        	}
		        	break;
			}
			super.onActivityResult(requestCode, resultCode, intent);
	        
	    }
	 
	 private void selectPhoto(){
		if(takePhotoDialog == null){
			takePhotoDialog = new TakePhotoDialog(this);
			takePhotoDialog.setCancelable(false);
			TextView takePhoto = takePhotoDialog.getTakeTextView();
			TextView selectPhoto = takePhotoDialog.getSelectTextView();
			TextView canclePhoto = takePhotoDialog.getCancelTextView();
			takePhoto.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					    outputImage = new File(Environment
							.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
							try {
							if (outputImage.exists()) {
							outputImage.delete();
							}
							outputImage.createNewFile();
							} catch (Exception e) {
							e.printStackTrace();
							}
							captureimageUri = Uri.fromFile(outputImage);
							Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
							intent.putExtra(MediaStore.EXTRA_OUTPUT, captureimageUri);
							startActivityForResult(intent, 1111);// 启动相机程序
							takePhotoDialog.dismiss();
				}
			}); 
			selectPhoto.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					takePhotoDialog.dismiss();
					HeadImageUtils.openLocalImage(MyUserInfoActivity.this);
				}
			});
			canclePhoto.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					takePhotoDialog.dismiss();
				}
			});
		}
		    if(!takePhotoDialog.isShowing()){
		    	takePhotoDialog.show();
		    }
	 }

	 /**
		 * 缩放bitmap
		 */
		public Bitmap resizeBitmap(Bitmap bitmap,int width,int height){
			  int bitmapWidth = bitmap.getWidth();  
	          int bitmapHeight = bitmap.getHeight();  
	          // 缩放图片的尺寸  
	          float scaleWidth = (float) width / bitmapWidth;  
	          float scaleHeight = (float) height / bitmapHeight;  
	          Matrix matrix = new Matrix();  
	          matrix.postScale(scaleWidth, scaleHeight);  
	          // 产生缩放后的Bitmap对象  
	          Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);  
			
			return resizeBitmap;
		}
		 /**
		 * 将bitmap转换为file
		 * 
		 * @param bmp
		 * @param filename
		 * @return
		 */
		public  String saveBitmap2file(Bitmap bmp) {
			//bmp = resizeBitmap(bmp, 320, 320);
			Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
			String localPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/eichong/";
			String filename = "eichong.jpg";
			//String filename = System.currentTimeMillis()+".jpg";
			File file = new File(localPath);
			if (!file.exists()) {
				file.mkdirs();
			} 
			File saveFile = new File(localPath + filename);
			int quality = 80;
			FileOutputStream stream = null;
			try {
				stream = new FileOutputStream(saveFile);
				bmp.compress(format, quality, stream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if(!bmp.isRecycled()){
				bmp.recycle();
			}
			return localPath + filename;
		}
		 private Bitmap convertUri2Bitmap(Uri uri) {
		        InputStream is = null;
		        try {
		            is = getContentResolver().openInputStream(uri);
		            Bitmap bitmap = BitmapFactory.decodeStream(is);
		            is.close();
		            return bitmap;
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		            return null;
		        } catch (IOException e) {
		            e.printStackTrace();
		            return null;
		        }
		    }
		 @Override
			public void onSuccess(String sign, Bundle bundle) {
				cancelPD();
				if(sign.equals(Protocol.MODIFY_USER_INFO)){
					/* Intent intent = new Intent(BroadcastUtil.BROADCAST_Modify_UserInfo);
					 sendBroadcast(intent);
					 boolean isFit = PreferencesUtil.getBooleanPreferences(this, "isFit", false);
					 
					 if(isFit && carType != null && !carType.equals(carname)){
						 Intent Inupdate = new Intent(BroadcastUtil.BROADCAST_MODIFY_USERINFO_UPDATE);
						 sendBroadcast(Inupdate); 
					 }*/
					
				}else if(sign.equals(Protocol.GET_USER_INFO)){
					userInfoBean = (UserInfoBean) bundle.getSerializable(Protocol.DATA);
					initValueToView(userInfoBean);
				}
				
			}

			@Override
			public void onFaile(String sign, Bundle bundle) {
				cancelPD();
				//showToast(bundle.getString(Protocol.MSG));
				//finish();
				
			}
}
