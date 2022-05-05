package com.ihfazh.simpledorar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.menu_search) {
//            onSearchRequested()
//        }
//        return super.onOptionsItemSelected(item)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(myToolbar)
    }

//    fun onSearchClick(view: View?) {
//        val textInputEditText = findViewById<View>(R.id.skey) as TextInputEditText
//        val skey = Objects.requireNonNull(textInputEditText.text).toString()
//        doSearch(skey)
//    }
//
//    private fun doSearch(skey: String) {
//        // create intent
//        val intent = Intent(this, AHadithResultActivity::class.java)
//        // send the key
//        // activate the intent
//        intent.putExtra(SKEY, skey)
//        startActivity(intent)
//    }
//
//    fun onAudioSearchClick(view: View?) {
//        // create intent
//        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        intent.putExtra(
//            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//        )
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-SA")
//        startActivityForResult(intent, 10)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && data != null && requestCode == 10) {
//            val results: List<String> = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
//            val skey = results[0]
//            doSearch(skey)
//        } else {
//            Toast.makeText(applicationContext, "Failed to recognize speech!", Toast.LENGTH_LONG)
//                .show()
//        }
//    }

    companion object {
        const val SKEY = "com.ihfazh.simpledorar.skey"
        private val TAG = MainActivity::class.java.simpleName
    }
}