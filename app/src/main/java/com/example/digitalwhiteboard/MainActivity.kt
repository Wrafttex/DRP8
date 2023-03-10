package com.example.digitalwhiteboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.digitalwhiteboard.databinding.ActivityMainBinding
import android.util.Log;
import org.opencv.android.OpenCVLoader;

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()
    }

    /**
     * A native method that is implemented by the 'digitalwhiteboard' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'digitalwhiteboard' library on application startup.
        init {
            System.loadLibrary("digitalwhiteboard")
        }
    }

    private val TAG = "MainActivity"
    static
    {
        System.loadLibrary("native-lib")
        System.loadLibrary("opencv_java4")
    }

    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.sample_text) as TextView
        textView.setText(stringFromJNI())

        // Example of a call to a native method
        val tv: TextView = findViewById(R.id.sample_text)
        tv.setText(stringFromJNI())
        if (OpenCVLoader.initDebug()) {
            textView.setText(textView.getText() + "\n OPENCV LOADED SUCCESSFULLY")
            textView.setText(textView.getText() + "\n" + validate(500, 500))
        } else {
            Log.d(TAG, "OPENCV DİD NOT LOAD")
        }
    }

    external fun stringFromJNI(): String?
    external fun validate(madAddrGr: Long, matAddrRgba: Long): String
}