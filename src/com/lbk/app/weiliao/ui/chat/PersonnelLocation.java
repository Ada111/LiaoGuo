package com.lbk.app.weiliao.ui.chat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
//import com.baidu.mapapi.GeoPoint;
//import com.baidu.mapapi.ItemizedOverlay;
//import com.baidu.mapapi.MapActivity;
//import com.baidu.mapapi.MapView;
//import com.baidu.mapapi.OverlayItem;
import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.utils.BMapApiDemoApp;

public class PersonnelLocation extends Activity{
	
	private MapView mMapView;
	
	private OverItemTs overitem;
	
	private GeoPoint geoPoint;
	
	private Button backButton;
	
	private BMapApiDemoApp app;
	
	public static double Lon;
	public static double Lat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personnel_location);
		
		Lon = Double.valueOf(this.getIntent().getStringExtra("Lon"));
		Lat = Double.valueOf(this.getIntent().getStringExtra("Lat"));
		
		geoPoint = new GeoPoint((int) (Lat * 1E6), (int) (Lon * 1E6));
		
		app = (BMapApiDemoApp)this.getApplication();
		if (app.mBMapMan == null) {
			app.mBMapMan = new BMapManager(getApplication());
			app.mBMapMan.init(app.mStrKey, new BMapApiDemoApp.MyGeneralListener());
		}
		app.mBMapMan.start();
//        super.initMapActivity(app.mBMapMan);
        
        mMapView = (MapView) findViewById(R.id.personnel_location_mapView);
        mMapView.setBuiltInZoomControls(true);
//        mMapView.setDrawOverlayWhenZooming(true);
        
        mMapView.getController().setCenter(geoPoint);
        mMapView.getController().setZoom(18);
		
        
        Drawable mdDrawable = this.getResources().getDrawable(R.drawable.title_location_nor);
		mdDrawable.setBounds(0, 0, mdDrawable.getIntrinsicWidth(), mdDrawable.getIntrinsicHeight());

		overitem = new OverItemTs(mdDrawable, this, geoPoint,"");
		mMapView.getOverlays().add(overitem); //添加ItemizedOverlay实例到mMapView
		mMapView.refresh();//刷新地图
        
        backButton = (Button)findViewById(R.id.personnel_location_backbtn);
        backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				app = (BMapApiDemoApp)PersonnelLocation.this.getApplication();
		        mMapView.destroyDrawingCache();
				app.mBMapMan.stop();
				
				PersonnelLocation.this.finish();
			}
		});
        
	}
	
	
	@Override
	protected void onPause() {
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		app.mBMapMan.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
		
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		app.mBMapMan.start();
		
		super.onResume();
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			
			app = (BMapApiDemoApp)this.getApplication();
	        mMapView.destroyDrawingCache();
			app.mBMapMan.stop();
			
			PersonnelLocation.this.finish();
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	

//	@Override
//	protected boolean isRouteDisplayed() {
//		return false;
//	}

	
}


class OverItemTs extends ItemizedOverlay<OverlayItem>{
	private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();

	public OverItemTs(Drawable marker, Context context, GeoPoint pt, String title) {
//		super(boundCenterBottom(marker));
		super(marker);
		mGeoList.add(new OverlayItem(pt, title, null));

		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mGeoList.get(i);
	}

	@Override
	public int size() {
		return mGeoList.size();
	}

//	@Override
//	public boolean onSnapToItem(int i, int j, Point point, MapView mapview) {
//		Log.e("ItemizedOverlayDemo","enter onSnapToItem()!");
//		return false;
//	}
}
