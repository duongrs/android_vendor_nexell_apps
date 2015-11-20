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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class DualAudioTestActivity extends Activity {
	private final String	NX_DTAG = "DualAudioTestActivity";
	
	private final String[] STREAM_TYPE_NAME = {
		/* 0 */ "STREAM_VOICE_CALL",	// 0
		/* 1 */ "STREAM_SYSTEM",		// 1
		/* 2 */ "STREAM_RING",			// 2
		/* 3 */ "STREAM_MUSIC",			// 3
		/* 4 */ "STREAM_ALARM",			// 4
		/* 5 */ "STREAM_NOTIFICATION",	// 5
		/* 6 */ "STREAM_DTMF",			// 8
		/* 7 */ "STREAM_EXT_SPEAKER",	// 10
	};
	
	// Newly User Define Stream Type.
	private final int		STREAM_EXT_SPEAKER	= 10;
	
	private TextView		mTextPath0, mTextPath1, mTextPath2;
	private Spinner			mSpinnerType0, mSpinnerType1, mSpinnerType2;
	private CheckBox		mCheckEnable0, mCheckEnable1, mCheckEnable2, mCheckRepeat0, mCheckRepeat1, mCheckRepeat2;
	private SeekBar			mSeekVolume0, mSeekVolume1, mSeekVolume2;
	private Button			mBtnPlay0, mBtnPlay1, mBtnPlay2, mBtnStop0, mBtnStop1, mBtnStop2, mBtnPlayAll, mBtnStopAll;
	 
	private AudioManager	mAudioManager;
	
	private Thread 			mThreadTrack0 = null, mThreadTrack1 = null, mThreadTrack2 = null;
	private boolean			mThreadRun0 = false, mThreadRun1 = false, mThreadRun2 = false;

	private static String	mWavPath0 = "Touch and Select Wave Files..";
	private static String	mWavPath1 = "Touch and Select Wave Files..";
	private static String	mWavPath2 = "Touch and Select Wave Files..";
	private static int		mTextViewId;
	
	private static boolean	mEnable0 = true, mEnable1 = true, mEnable2 = true;
	private static boolean	mRepeat0 = true, mRepeat1 = true, mRepeat2 = true;
	
	private static int		mStreamType0 = 7;
	private static int		mStreamType1 = 7;
	private static int		mStreamType2 = 7;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dual_audio_test);

		mTextPath0		= (TextView)findViewById( R.id.textPathValue0 );
		mSpinnerType0	= (Spinner)findViewById( R.id.spinnerType0 );
		mSeekVolume0	= (SeekBar)findViewById( R.id.seekVolume0 );
		mCheckEnable0	= (CheckBox)findViewById( R.id.checkEnable0 );
		mCheckRepeat0	= (CheckBox)findViewById( R.id.checkRepeat0 );
		mBtnPlay0		= (Button)findViewById( R.id.btnPlay0 );
		mBtnStop0		= (Button)findViewById( R.id.btnStop0 );

		mTextPath1		= (TextView)findViewById( R.id.textPathValue1 );
		mSpinnerType1	= (Spinner)findViewById( R.id.spinnerType1 );
		mSeekVolume1	= (SeekBar)findViewById( R.id.seekVolume1 );
		mCheckEnable1	= (CheckBox)findViewById( R.id.checkEnable1 );
		mCheckRepeat1	= (CheckBox)findViewById( R.id.checkRepeat1 );
		mBtnPlay1		= (Button)findViewById( R.id.btnPlay1 );
		mBtnStop1		= (Button)findViewById( R.id.btnStop1 );
		
		mTextPath2		= (TextView)findViewById( R.id.textPathValue2 );
		mSpinnerType2	= (Spinner)findViewById( R.id.spinnerType2 );
		mSeekVolume2	= (SeekBar)findViewById( R.id.seekVolume2 );
		mCheckEnable2	= (CheckBox)findViewById( R.id.checkEnable2 );
		mCheckRepeat2	= (CheckBox)findViewById( R.id.checkRepeat2 );
		mBtnPlay2		= (Button)findViewById( R.id.btnPlay2 );
		mBtnStop2		= (Button)findViewById( R.id.btnStop2 );
		
		mBtnPlayAll		= (Button)findViewById( R.id.btnPlayAll );
		mBtnStopAll		= (Button)findViewById( R.id.btnStopAll );
		
		mAudioManager 	= (AudioManager)getSystemService( AUDIO_SERVICE );

		ArrayAdapter<String> adapterSpin = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, STREAM_TYPE_NAME);
		mSpinnerType0.setAdapter( adapterSpin );
		mSpinnerType1.setAdapter( adapterSpin );
		mSpinnerType2.setAdapter( adapterSpin );

		// Initialize Control
	  	Intent intent = getIntent();
    	String path = intent.getStringExtra("path");
    	
    	if( path != null ) {
    		switch( mTextViewId )
    		{
    		case R.id.textPathValue0:
    			mWavPath0 = path;
    			break;
    		case R.id.textPathValue1:
    			mWavPath1 = path;
    			break;
    		case R.id.textPathValue2:
    			mWavPath2 = path;
    			break;
    		}
    	}

		mTextPath0.setText( mWavPath0 );
		mTextPath1.setText( mWavPath1 );
		mTextPath2.setText( mWavPath2 );
		
		mSpinnerType0.setSelection( mStreamType0 );
		mSpinnerType1.setSelection( mStreamType1 );
		mSpinnerType2.setSelection( mStreamType2 );
		
		mCheckEnable0.setChecked( mEnable0 );
		mCheckEnable1.setChecked( mEnable1 );
		mCheckEnable2.setChecked( mEnable2 );

		mCheckRepeat0.setChecked( mRepeat0 );
		mCheckRepeat1.setChecked( mRepeat1 );
		mCheckRepeat2.setChecked( mRepeat2 );
		
		mTextPath0.setEnabled( mCheckEnable0.isChecked() );
		mSpinnerType0.setEnabled( mCheckEnable0.isChecked() );
		mSeekVolume0.setEnabled( mCheckEnable0.isChecked() );
		mCheckRepeat0.setEnabled( mCheckEnable0.isChecked() );
		mBtnPlay0.setEnabled( mCheckEnable0.isChecked() );
		mBtnStop0.setEnabled( mCheckEnable0.isChecked() );

		mTextPath1.setEnabled( mCheckEnable1.isChecked() );
		mSpinnerType1.setEnabled( mCheckEnable1.isChecked() );
		mSeekVolume1.setEnabled( mCheckEnable1.isChecked() );
		mCheckRepeat1.setEnabled( mCheckEnable1.isChecked() );
		mBtnPlay1.setEnabled( mCheckEnable1.isChecked() );
		mBtnStop1.setEnabled( mCheckEnable1.isChecked() );
		
		mTextPath2.setEnabled( mCheckEnable2.isChecked() );
		mSpinnerType2.setEnabled( mCheckEnable2.isChecked() );
		mSeekVolume2.setEnabled( mCheckEnable2.isChecked() );
		mCheckRepeat2.setEnabled( mCheckEnable2.isChecked() );
		mBtnPlay2.setEnabled( mCheckEnable2.isChecked() );
		mBtnStop2.setEnabled( mCheckEnable2.isChecked() );		
		
		mSeekVolume0.setMax( mAudioManager.getStreamMaxVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()) ) );
		mSeekVolume0.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()) ) );
		
		mSeekVolume1.setMax( mAudioManager.getStreamMaxVolume( StreamTypeToSpinnerPos((String)mSpinnerType1.getSelectedItem()) ) );
		mSeekVolume1.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType1.getSelectedItem()) ) );
		
		mSeekVolume2.setMax( mAudioManager.getStreamMaxVolume( StreamTypeToSpinnerPos((String)mSpinnerType2.getSelectedItem()) ) );
		mSeekVolume2.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType2.getSelectedItem()) ) );

		// Register Listener
		mTextPath0.setOnTouchListener( mTouchListener );
		mTextPath1.setOnTouchListener( mTouchListener );
		mTextPath2.setOnTouchListener( mTouchListener );
		
		mCheckEnable0.setOnCheckedChangeListener( mChekcedChangeListner );
		mCheckEnable1.setOnCheckedChangeListener( mChekcedChangeListner );
		mCheckEnable2.setOnCheckedChangeListener( mChekcedChangeListner );
		
		mSeekVolume0.setOnSeekBarChangeListener( mSeekBarChangeListener );
		mSeekVolume1.setOnSeekBarChangeListener( mSeekBarChangeListener );
		mSeekVolume2.setOnSeekBarChangeListener( mSeekBarChangeListener );
		
		mSpinnerType0.setOnItemSelectedListener( mItemSelectedListener );
		mSpinnerType1.setOnItemSelectedListener( mItemSelectedListener );
		mSpinnerType2.setOnItemSelectedListener( mItemSelectedListener );

		mBtnPlay0.setOnClickListener( mClickListener );
		mBtnStop0.setOnClickListener( mClickListener );
		mBtnPlay1.setOnClickListener( mClickListener );
		mBtnStop1.setOnClickListener( mClickListener );
		mBtnPlay2.setOnClickListener( mClickListener );
		mBtnStop2.setOnClickListener( mClickListener );
		mBtnPlayAll.setOnClickListener( mClickListener );
		mBtnStopAll.setOnClickListener( mClickListener );
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

    @Override
    public void onDestroy(){
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dual_audio_test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private int StreamTypeToSpinnerPos( String streamType )
	{
		if( streamType.equals("STREAM_VOICE_CALL") )
			return AudioManager.STREAM_VOICE_CALL;
		else if( streamType.equals("STREAM_SYSTEM") )
			return AudioManager.STREAM_SYSTEM;
		else if( streamType.equals("STREAM_RING") )
			return AudioManager.STREAM_RING;
		else if( streamType.equals("STREAM_MUSIC") )
			return AudioManager.STREAM_MUSIC;
		else if( streamType.equals("STREAM_ALARM") )
			return AudioManager.STREAM_ALARM;
		else if( streamType.equals("STREAM_NOTIFICATION") )
			return AudioManager.STREAM_NOTIFICATION;
		else if( streamType.equals("STREAM_DTMF") )
			return AudioManager.STREAM_DTMF;
		else if( streamType.equals("STREAM_EXT_SPEAKER") )
			return STREAM_EXT_SPEAKER;
		
		return -1; 
	}

	private int GetChannel( int channel )
	{
		if( channel == 2 )
			return AudioFormat.CHANNEL_OUT_STEREO;
		else if( channel == 1 )
			return AudioFormat.CHANNEL_OUT_MONO;
		
		return -1;
	}
	
	private int GetBitPerSample( int bitPerSample )

	{
		if( bitPerSample == 16 )
			return AudioFormat.ENCODING_PCM_16BIT;
		else if( bitPerSample == 8 )
			return AudioFormat.ENCODING_PCM_8BIT;
		
		return -1;
	}

	//----------------------------------------------------------------------------------------------------
	//
	//	TextView Event Listener
	//	
	private OnTouchListener mTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			if( mThreadTrack0 != null || mThreadTrack1 != null || mThreadTrack2 != null )
				return false;

			switch( arg1.getAction() )
			{
			case MotionEvent.ACTION_DOWN :
//				Intent intent = new Intent( Intent.ACTION_GET_CONTENT );
//				intent.setType("audio/x-wav");
//				startActivityForResult( Intent.createChooser( intent, "Select Wave File.."), 0 );
				mTextViewId = ((TextView)arg0).getId();

				Intent intent = new Intent( DualAudioTestActivity.this, FileSelectActivity.class );
				startActivity( intent );
				finish();
				break;

			case MotionEvent.ACTION_UP :
				break;
			}
			return false;
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if( resultCode == RESULT_OK ) {
			if( data != null ) {
				Log.i( NX_DTAG, data.getStringExtra("data") );
			}
		}
		else if( resultCode == RESULT_CANCELED ) {
		}
	}
	
	//----------------------------------------------------------------------------------------------------
	//
	//	CheckBox Event Listener
	//	
	private OnCheckedChangeListener mChekcedChangeListner = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			// TODO Auto-generated method stub
			switch( ((CheckBox)arg0).getId() )
			{
			case R.id.checkEnable0 :
				mEnable0 = arg1;
				mTextPath0.setEnabled( arg1 );
				mSpinnerType0.setEnabled( arg1 );
				mSeekVolume0.setEnabled( arg1 );
				mCheckRepeat0.setEnabled( arg1 );
				mBtnPlay0.setEnabled( arg1 );
				mBtnStop0.setEnabled( arg1 );
				
				if( arg1 ) {
					mSeekVolume0.setMax( mAudioManager.getStreamMaxVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()) ) );
					mSeekVolume0.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()) ) );
				}
				break;
			
			case R.id.checkEnable1 :
				mEnable1 = arg1;
				mTextPath1.setEnabled( arg1 );
				mSpinnerType1.setEnabled( arg1 );
				mSeekVolume1.setEnabled( arg1 );
				mCheckRepeat1.setEnabled( arg1 );
				mBtnPlay1.setEnabled( arg1 );
				mBtnStop1.setEnabled( arg1 );
				
				if( arg1 ) {
					mSeekVolume1.setMax( mAudioManager.getStreamMaxVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()) ) );
					mSeekVolume1.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()) ) );
				}				
				break;

			case R.id.checkEnable2 :
				mEnable2 = arg1;
				mTextPath2.setEnabled( arg1 );
				mSpinnerType2.setEnabled( arg1 );
				mSeekVolume2.setEnabled( arg1 );
				mCheckRepeat2.setEnabled( arg1 );
				mBtnPlay2.setEnabled( arg1 );
				mBtnStop2.setEnabled( arg1 );
				
				if( arg1 ) {
					mSeekVolume2.setMax( mAudioManager.getStreamMaxVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()) ) );
					mSeekVolume2.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()) ) );
				}				
				break;

			case R.id.checkRepeat0 :
				mRepeat0 = arg1;
				break;
				
			case R.id.checkRepeat1 :
				mRepeat1 = arg1;
				break;
				
			case R.id.checkRepeat2 :
				mRepeat2 = arg1;
				break;
			}
		}
	};
	
	//----------------------------------------------------------------------------------------------------
	//
	//	Seekbar Event Listener
	//	
	private OnSeekBarChangeListener mSeekBarChangeListener = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			if( !fromUser )
				return;
			
			switch( seekBar.getId() )
			{
			case R.id.seekVolume0 :
				mAudioManager.setStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()), progress, 0);
				
				if( mCheckEnable1.isChecked() ) mSeekVolume1.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType1.getSelectedItem()) ) );
				if( mCheckEnable2.isChecked() ) mSeekVolume2.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType2.getSelectedItem()) ) );
				break;
				
			case R.id.seekVolume1 :
				mAudioManager.setStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType1.getSelectedItem()), progress, 0);				

				if( mCheckEnable0.isChecked() ) mSeekVolume0.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()) ) );
				if( mCheckEnable2.isChecked() ) mSeekVolume2.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType2.getSelectedItem()) ) );
				break;
				
			case R.id.seekVolume2 :
				mAudioManager.setStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType2.getSelectedItem()), progress, 0);				

				if( mCheckEnable0.isChecked() ) mSeekVolume0.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()) ) );
				if( mCheckEnable1.isChecked() ) mSeekVolume1.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType1.getSelectedItem()) ) );
				break;
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
	};
	
	//----------------------------------------------------------------------------------------------------
	//
	//	Spinner Event Listener
	//
	private OnItemSelectedListener mItemSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			switch( ((Spinner)arg0).getId() )
			{
			case R.id.spinnerType0 :
				mStreamType0 = arg2;
				mSeekVolume0.setMax( mAudioManager.getStreamMaxVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()) ) );
				mSeekVolume0.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()) ) );
				break;
			
			case R.id.spinnerType1 :
				mStreamType1 = arg2;
				mSeekVolume1.setMax( mAudioManager.getStreamMaxVolume( StreamTypeToSpinnerPos((String)mSpinnerType1.getSelectedItem()) ) );
				mSeekVolume1.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType1.getSelectedItem()) ) );
				break;

			case R.id.spinnerType2 :
				mStreamType2 = arg2;
				mSeekVolume2.setMax( mAudioManager.getStreamMaxVolume( StreamTypeToSpinnerPos((String)mSpinnerType2.getSelectedItem()) ) );
				mSeekVolume2.setProgress( mAudioManager.getStreamVolume( StreamTypeToSpinnerPos((String)mSpinnerType2.getSelectedItem()) ) );
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};

	//----------------------------------------------------------------------------------------------------
	//
	//	Button Event Listener
	//	
	private View.OnClickListener mClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch( ((Button)v).getId() )
			{
			case R.id.btnPlay0 :
				if( !mCheckEnable0.isChecked() )
					return;
				
				if( mThreadTrack0 == null ) {
					mThreadRun0 = true;
					mThreadTrack0 = new Thread( mRunnableTrack0 );
					mThreadTrack0.start();
				}
				break;
				
			case R.id.btnStop0 :
				if( mThreadTrack0 != null ) {
					mThreadRun0 = false;
					mThreadTrack0.interrupt();
					
					try {
						mThreadTrack0.join();
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mThreadTrack0 = null;
				}				
				break;
			
			case R.id.btnPlay1 :
				if( !mCheckEnable1.isChecked() )
					return;
			
				if( mThreadTrack1 == null ) {
					mThreadRun1 = true;
					mThreadTrack1 = new Thread( mRunnableTrack1 );
					mThreadTrack1.start();
				}				
				break;
				
			case R.id.btnStop1 :
				if( mThreadTrack1 != null ) {
					mThreadRun1 = false;
					mThreadTrack1.interrupt();
					
					try {
						mThreadTrack1.join();
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mThreadTrack1 = null;
				}				
				break;
				
			case R.id.btnPlay2 :
				if( !mCheckEnable2.isChecked() )
					return;

				if( mThreadTrack2 == null ) {
					mThreadRun2 = true;
					mThreadTrack2 = new Thread( mRunnableTrack2 );
					mThreadTrack2.start();
				}
				break;
				
			case R.id.btnStop2 :
				if( mThreadTrack2 != null ) {
					mThreadRun2 = false;
					mThreadTrack2.interrupt();
					
					try {
						mThreadTrack2.join();
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mThreadTrack2 = null;
				}
				break;
			
			case R.id.btnPlayAll :
				if( mCheckEnable0.isChecked() && mThreadTrack0 == null ) {
					mThreadRun0 = true;
					mThreadTrack0 = new Thread( mRunnableTrack0 );
					mThreadTrack0.start();
				}
				if( mCheckEnable1.isChecked() && mThreadTrack1 == null ) {
					mThreadRun1 = true;
					mThreadTrack1 = new Thread( mRunnableTrack1 );
					mThreadTrack1.start();
				}
				if( mCheckEnable2.isChecked() && mThreadTrack2 == null ) {
					mThreadRun2 = true;
					mThreadTrack2 = new Thread( mRunnableTrack2 );
					mThreadTrack2.start();
				}
				break;

			case R.id.btnStopAll :
				if( mThreadTrack0 != null ) {
					mThreadRun0 = false;
					mThreadTrack0.interrupt();
					
					try {
						mThreadTrack0.join();
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mThreadTrack0 = null;
				}
				if( mThreadTrack1 != null ) {
					mThreadRun1 = false;
					mThreadTrack1.interrupt();
					
					try {
						mThreadTrack1.join();
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mThreadTrack1 = null;
				}
				if( mThreadTrack2 != null ) {
					mThreadRun2 = false;
					mThreadTrack2.interrupt();
					
					try {
						mThreadTrack2.join();
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mThreadTrack2 = null;
				}				
				break;
			}
		}		
	};

	//----------------------------------------------------------------------------------------------------
	//
	//	Wave Playback Runnable
	//	
	private Runnable mRunnableTrack0 = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			File inFile = new File( mWavPath0 );
			WaveHeaderParser wavParser = new WaveHeaderParser();
			try {
				InputStream inStream = new FileInputStream( inFile );
				wavParser.read( inStream );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int iChannel		= wavParser.getNumChannels();
			int iSampleRate		= wavParser.getSampleRate(); 
			int iBitPerSample 	= wavParser.getBitsPerSample();

			Log.i( NX_DTAG, "Wav Info : " + mWavPath0 + "( " + String.valueOf(iChannel) + "ch, " + String.valueOf(iBitPerSample) + "bit, "  + String.valueOf(iSampleRate) + "KHz )" );
			if( iChannel == 0 || iSampleRate == 0 || iBitPerSample == 0 ) {
				Log.i( NX_DTAG, "Not support wav format." );
				mThreadRun0 = false;
				mThreadTrack0 = null;
				return;				
			}
		
			int iBufSize		= AudioTrack.getMinBufferSize( iSampleRate, GetChannel(iChannel), GetBitPerSample(iBitPerSample) );
			byte[] byteBuffer 	= new byte[iBufSize];
			AudioTrack audioTrack = new AudioTrack( StreamTypeToSpinnerPos((String)mSpinnerType0.getSelectedItem()), iSampleRate, GetChannel(iChannel), GetBitPerSample(iBitPerSample), iBufSize, AudioTrack.MODE_STREAM );
			
			int iReadByte = 0, iRet = 0;
			FileInputStream inFileStream = null;

			try {
				inFileStream = new FileInputStream( inFile );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				audioTrack.play();
				while( mThreadRun0 )
				{
					iRet = inFileStream.read( byteBuffer );
					if( iRet != -1 ) {
						audioTrack.write( byteBuffer, 0, iRet );
						iReadByte += iRet;
						// Log.i( NX_DTAG, "readByte : " + iReadByte);
					}
					else {
						if( mCheckRepeat0.isChecked() ) {
							inFileStream.close();
							inFileStream = null;
							inFileStream = new FileInputStream( inFile );
						}
						else {
							break;
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				inFileStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			inFileStream = null;

			audioTrack.stop();
			audioTrack.release();
			audioTrack = null;
		}
	};


	private Runnable mRunnableTrack1 = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			File inFile = new File( mWavPath1 );
			WaveHeaderParser wavParser = new WaveHeaderParser();
			try {
				InputStream inStream = new FileInputStream( inFile );
				wavParser.read( inStream );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int iChannel		= wavParser.getNumChannels();
			int iSampleRate		= wavParser.getSampleRate(); 
			int iBitPerSample 	= wavParser.getBitsPerSample();

			Log.i( NX_DTAG, "Wav Info : " + mWavPath1 + "( " + String.valueOf(iChannel) + "ch, " + String.valueOf(iBitPerSample) + "bit, "  + String.valueOf(iSampleRate) + "KHz )" );
			if( iChannel == 0 || iSampleRate == 0 || iBitPerSample == 0 ) {
				Log.i( NX_DTAG, "Not support wav format." );
				mThreadRun1 = false;
				mThreadTrack1 = null;
				return;				
			}

			int iBufSize		= AudioTrack.getMinBufferSize( iSampleRate, GetChannel(iChannel), GetBitPerSample(iBitPerSample) );
			byte[] byteBuffer 	= new byte[iBufSize];
			AudioTrack audioTrack = new AudioTrack( StreamTypeToSpinnerPos((String)mSpinnerType1.getSelectedItem()), iSampleRate, GetChannel(iChannel), GetBitPerSample(iBitPerSample), iBufSize, AudioTrack.MODE_STREAM );
			
			int iReadByte = 0, iRet = 0;
			FileInputStream inFileStream = null;

			try {
				inFileStream = new FileInputStream( inFile );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				audioTrack.play();
				while( mThreadRun1 )
				{
					iRet = inFileStream.read( byteBuffer );
					if( iRet != -1 ) {
						audioTrack.write( byteBuffer, 0, iRet );
						iReadByte += iRet;
						// Log.i( NX_DTAG, "readByte : " + iReadByte);
					}
					else {
						if( mCheckRepeat1.isChecked() ) {
							inFileStream.close();
							inFileStream = null;
							inFileStream = new FileInputStream( inFile );
						}
						else {
							break;
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				inFileStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			inFileStream = null;

			audioTrack.stop();
			audioTrack.release();
			audioTrack = null;
		}
	};

	private Runnable mRunnableTrack2 = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			File inFile = new File( mWavPath2 );
			WaveHeaderParser wavParser = new WaveHeaderParser();
			try {
				InputStream inStream = new FileInputStream( inFile );
				wavParser.read( inStream );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int iChannel		= wavParser.getNumChannels();
			int iSampleRate		= wavParser.getSampleRate(); 
			int iBitPerSample 	= wavParser.getBitsPerSample();
			
			Log.i( NX_DTAG, "Wav Info : " + mWavPath2 + "( " + String.valueOf(iChannel) + "ch, " + String.valueOf(iBitPerSample) + "bit, "  + String.valueOf(iSampleRate) + "KHz )" );
			if( iChannel == 0 || iSampleRate == 0 || iBitPerSample == 0 ) {
				Log.i( NX_DTAG, "Not support wav format." );
				mThreadRun2 = false;
				mThreadTrack2 = null;
				return;				
			}
			
			int iBufSize		= AudioTrack.getMinBufferSize( iSampleRate, GetChannel(iChannel), GetBitPerSample(iBitPerSample) );
			byte[] byteBuffer 	= new byte[iBufSize];
			AudioTrack audioTrack = new AudioTrack( StreamTypeToSpinnerPos((String)mSpinnerType2.getSelectedItem()), iSampleRate, GetChannel(iChannel), GetBitPerSample(iBitPerSample), iBufSize, AudioTrack.MODE_STREAM );
			
			int iReadByte = 0, iRet = 0;
			FileInputStream inFileStream = null;

			try {
				inFileStream = new FileInputStream( inFile );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				audioTrack.play();
				while( mThreadRun2 )
				{
					iRet = inFileStream.read( byteBuffer );
					if( iRet != -1 ) {
						audioTrack.write( byteBuffer, 0, iRet );
						iReadByte += iRet;
						// Log.i( NX_DTAG, "readByte : " + iReadByte);
					}
					else {
						if( mCheckRepeat2.isChecked() ) {
							inFileStream.close();
							inFileStream = null;
							inFileStream = new FileInputStream( inFile );
						}
						else {
							break;
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				inFileStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			inFileStream = null;

			audioTrack.stop();
			audioTrack.release();
			audioTrack = null;
		}
	};
}