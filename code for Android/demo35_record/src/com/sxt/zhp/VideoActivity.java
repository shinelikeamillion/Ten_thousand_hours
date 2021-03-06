package com.sxt.zhp;

import java.io.File;    
import java.io.IOException;    
    
import android.app.Activity;    
import android.media.MediaRecorder;    
import android.os.Bundle;    
import android.os.Environment;    
import android.view.SurfaceHolder;    
import android.view.SurfaceView;    
import android.view.View;    
import android.view.View.OnClickListener;    
import android.widget.Button;    
import android.widget.TextView;
 /**
  * 录制视频
  * <uses-permission android:name="android.permission.RECORD_AUDIO"/>  
  * @author zhangpeng
  *
  */
public class VideoActivity extends Activity {    
    
        
    private File myRecAudioFile;    
    private SurfaceView mSurfaceView;       
    private SurfaceHolder mSurfaceHolder;     
    private Button buttonStart;    
    private Button buttonStop;    
    private File dir;    
    private MediaRecorder recorder;    
        
        
    @Override    
    public void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.video);    
        mSurfaceView = (SurfaceView) findViewById(R.id.videoView);       
        mSurfaceHolder = mSurfaceView.getHolder();       
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);     
        buttonStart=(Button)findViewById(R.id.start);    
        buttonStop=(Button)findViewById(R.id.stop);  
        
        //创建存放视频的目录
        File defaultDir = Environment.getExternalStorageDirectory();    
        String path = defaultDir.getAbsolutePath()+File.separator+"V"+File.separator;//创建文件夹存放视频   
        dir = new File(path);    
        if(!dir.exists()){    
            dir.mkdir();    
        }   
        //实例刻录机
        recorder = new MediaRecorder();    
            
        buttonStart.setOnClickListener(new OnClickListener() {    
            @Override    
            public void onClick(View v) {    
                recorder();    
            }    
        });    
            
        buttonStop.setOnClickListener(new OnClickListener() {    
            @Override    
            public void onClick(View v) {    
                 recorder.stop();    
                 recorder.reset();    
                 recorder.release();    
                 recorder=null;  
                 TextView tv = (TextView)findViewById(R.id.textView1);
                 tv.setText("保存在："+myRecAudioFile.getAbsolutePath());
            }    
        });    
    }    
        
        
        
    public void recorder() {    
        try {   //mnt/sdcard/V/v-234234.3pg
            myRecAudioFile = File.createTempFile("v-", ".3gp",dir);//创建临时文件    
            recorder.setPreviewDisplay(mSurfaceHolder.getSurface());//预览    
            
            recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//视频源    
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //录音源为麦克风   
            
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//输出格式为3gp    
            recorder.setVideoSize(800, 480);//视频尺寸    
            recorder.setVideoFrameRate(15);//视频帧频率    
            recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);//视频编码    
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//音频编码    
            recorder.setMaxDuration(10000);//最大期限    
            recorder.setOutputFile(myRecAudioFile.getAbsolutePath());//保存路径    
            recorder.prepare();    
            recorder.start();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
    }    
} 
