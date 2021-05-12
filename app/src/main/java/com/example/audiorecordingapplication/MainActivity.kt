package com.example.audiorecordingapplication

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var results: String? = null
    private var stoptheRecording: Boolean = false
    private var place: Boolean = false
    private var medRecorder: MediaRecorder? = null
    lateinit var play: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO), 1)

        //medRecorder = MediaRecorder()
        //results = Environment.getExternalStorageDirectory().absolutePath + "/recordedFile.mp3"
        //medRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        //medRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        //medRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        //medRecorder?.setOutputFile(results)

        button_PlayAudio.setOnClickListener {
            val file = "Ultimate Battle Karoke.mp3"
            val asset = assets.openFd(file)
            play = MediaPlayer()
            //reference:  https://www.codota.com/code/java/methods/android.content.res.AssetFileDescriptor/getFileDescriptor
            //reference: https://stackoverflow.com/questions/3289038/play-audio-file-from-the-assets-directory
            play.setDataSource(asset.fileDescriptor, asset.startOffset, asset.length)
            volumeControlStream = AudioManager.STREAM_MUSIC
            try{
                play.prepare()
                play.start()
            }catch (error:Exception){
                error.printStackTrace()
            }
        }

        button_StopAudio.setOnClickListener {
            try{
                play.stop()
                play.prepare()
                play.release()
            } catch (error:Exception){
                error.printStackTrace()
            }
        }

        button_Record.setOnClickListener {
                startRecord()
        }

        button_Stop.setOnClickListener {
            stopRecord()
        }

        button_Play.setOnClickListener {
            playRecord()
        }
    }
    private fun startRecord(){

        medRecorder = MediaRecorder()
        results = Environment.getExternalStorageDirectory().absolutePath + "/recordedFile.mp3"
        medRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        medRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        medRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        medRecorder?.setOutputFile(results)
        try {
            medRecorder?.prepare()
            medRecorder?.start()
            place = true
            Toast.makeText(this, "Recording Commencing!", Toast.LENGTH_SHORT).show()
        }catch (error:Exception){
            error.printStackTrace()
        }
    }

    private fun stopRecord(){
        try {
            medRecorder?.stop()
            medRecorder?.release()
            place = false
            Toast.makeText(this, "Recording Concluding!", Toast.LENGTH_SHORT).show()
        }catch(error:Exception){
            error.printStackTrace()
        }
    }

    private fun playRecord(){
        try{
            play = MediaPlayer()
            play.setDataSource(results)
            play.prepare()
            play.start()
        }catch (error:Exception){
            error.printStackTrace()
        }
    }
}
