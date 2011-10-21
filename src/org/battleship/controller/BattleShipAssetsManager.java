/**
 * 
 */
package org.battleship.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * @author songoku
 *
 */
public class BattleShipAssetsManager {

	private static final String CLASSTAG = BattleShipAssetsManager.class
			.getSimpleName();
	
	private static BattleShipAssetsManager mInstance;
	
	private AssetManager mAssetsManaager;
	
	private Map<String,Bitmap> bitMaps;
	
	private BattleShipAssetsManager(){
		bitMaps = new HashMap<String, Bitmap>();
	}
	
	public static BattleShipAssetsManager getInstance(){
		if ( mInstance == null ){
			mInstance = new BattleShipAssetsManager();
		}
		return mInstance;
	}
	
	public Bitmap getBiMapImage( String image ){
		Bitmap bm = null;
		if ( bitMaps.containsKey( image )){
			return bitMaps.get(image);
		}else{
			try {
				bm = BitmapFactory.decodeStream( mAssetsManaager.open(image) );
				bitMaps.put(image, bm);
			} catch (IOException e) {
				Log.e( CLASSTAG, e.getLocalizedMessage() );
			}
			return bm;
		}
	}
	
	public void setManager( AssetManager assetManager ){
		this.mAssetsManaager = assetManager;
	}
}
