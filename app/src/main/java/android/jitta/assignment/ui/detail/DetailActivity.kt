package android.jitta.assignment.ui.detail

import android.content.Context
import android.content.Intent
import android.jitta.assignment.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    companion object {

        fun launch(
            context: Context,
            extras: Bundle? = null
        ) {
            val intent = Intent(context, DetailActivity::class.java)
            context.startActivity(intent)
        }
    }
}