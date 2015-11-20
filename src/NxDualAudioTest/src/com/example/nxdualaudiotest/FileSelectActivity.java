//--------------------------------------------------------------------------------
//
//	Copyright (C) 2015 Nexell Co. All Rights Reserved
//	Nexell Co. Proprietary & Confidential
//
//	NEXELL INFORMS THAT THIS CODE AND INFORMATION IS PROVIDED "AS IS" BASE
//  AND	WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING
//  BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS
//  FOR A PARTICULAR PURPOSE.
//
//	Module		: 
//	File		: 
//	Description	: 
//	Author		: 
//	Export		: 
//	History		: 
//
//--------------------------------------------------------------------------------

package com.example.nxdualaudiotest;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileSelectActivity extends Activity {
	private final String	NX_DTAG = "FileSelectActivity";

	private static final String[] STORAGE_PATH = {
		"/storage/sdcard0",
		"/storage/sdcard1",
		"/storage/usbdisk1",
	};
	
	private static final String[] AUDIO_EXTENSION = {
		"wav",
	};
	
	private ListView			mListView;
	private ArrayList<String>	mAudioList;
	private int					mItemNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_file_select );
		
		mListView	= (ListView)findViewById(R.id.listView1);
		mAudioList	= new ArrayList<String>();

		mItemNum = 0;
		for( int i = 0; i < STORAGE_PATH.length; i++ ) {
			GeteFileList( STORAGE_PATH[i] );
		}
		
		if( mItemNum == 0 ) {
			TextView textView = (TextView)findViewById(R.id.textView1);
			textView.setText("재생가능한 미디어를 찾을 수 없습니다.");
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mAudioList );
		mListView.setAdapter( adapter );
		mListView.setOnItemClickListener( mItemClickListener );
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			Intent intent = new Intent( FileSelectActivity.this, DualAudioTestActivity.class );
			startActivity( intent );
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Event Listener
	//
	AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Toast.makeText( FileSelectActivity.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
		
			Intent intent = new Intent( FileSelectActivity.this, DualAudioTestActivity.class);
			intent.putExtra( "path", parent.getItemAtPosition(position).toString() );
			startActivity( intent );
			finish();
		}
	};
	
	private void GeteFileList( String rootPath )
	{
		File rootFile = new File( rootPath );
		
		if( !rootFile.exists() ) {
			Log.w(NX_DTAG, "Invalid Directory Path. (" + rootPath + ")" );
			return;
		}
		
		if( rootFile.isDirectory() ) {
			String[] rootList = rootFile.list();
			
			if( null == rootList ) {
				return ;
			}
				
			for( int i = 0; i < rootList.length; i++ )
			{
				String subPath = rootPath + "/" + rootList[i];
				File subFile = new File( subPath );
				String[] subList = subFile.list();

				if( subFile.isDirectory() ) {
					for( int j = 0; j < subList.length; j++ )
					{
						GeteFileList( subPath + "/" + subList[j] );
					}
				}
				else {
					if( isAudio(subPath) ) {
						mAudioList.add(subPath);
						mItemNum++;
					}
				}
			}
		}
		else {
			if( isAudio(rootPath) ) {
				mAudioList.add(rootPath);
				mItemNum++;
			}
		}
	}
	
	private boolean isAudio( String fileName )
	{
		String strTemp = fileName.toLowerCase();
		
		for( int i = 0; i < AUDIO_EXTENSION.length; i++ ) {
			if( strTemp.endsWith(AUDIO_EXTENSION[i]) ) {
				return true;
			}
		}

		return false;
	}	
}
