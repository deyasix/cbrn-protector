package ua.nure.cbrnprotector.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ua.nure.cbrnprotector.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.sampleText.text = stringFromJNI()
    }

    private external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("cbrnprotector")
        }
    }
}