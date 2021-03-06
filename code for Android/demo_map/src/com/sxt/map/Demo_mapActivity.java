package com.sxt.map;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Demo_mapActivity extends Activity {
	MapView mMapView = null;
	BaiduMap mBaiduMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.map1);
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		
		mBaiduMap = mMapView.getMap();
		
		//定义Maker坐标点  
		LatLng point = new LatLng(39.963175, 116.400244);  
		//构建Marker图标  
		BitmapDescriptor bitmap = BitmapDescriptorFactory  
		    .fromResource(R.drawable.icon_gcoding);  
		//构建MarkerOption，用于在地图上添加Marker  
		OverlayOptions option = new MarkerOptions()  
		    .position(point)  
		    .icon(bitmap);  
		//在地图上添加Marker，并显示  
		mBaiduMap.addOverlay(option);
		myclick();
	}
	
	
	private void myclick(){

		findViewById(R.id.btn_add).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EditText edtLat = (EditText) findViewById(R.id.editText1);
				EditText edtLong = (EditText) findViewById(R.id.editText2);
				
				//定义Maker坐标点  
				LatLng point = new LatLng(Double.parseDouble(edtLat.getText().toString()), Double.parseDouble(edtLong.getText().toString()));  
				//构建Marker图标  
				BitmapDescriptor bitmap = BitmapDescriptorFactory  
				    .fromResource(R.drawable.icon_gcoding);  
				//构建MarkerOption，用于在地图上添加Marker  
				OverlayOptions option = new MarkerOptions()  
				    .position(point)  
				    .icon(bitmap);  
				//在地图上添加Marker，并显示  
				mBaiduMap.addOverlay(option);
				
				
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

}