package th.co.cdgs.mobile.floatinglabel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import th.co.cdgs.mobile.floatlabel.FloatLabel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt1.setOnDialogListener(object : FloatLabel.DialogListener{
            override fun onOpen(view: View?) {
                Toast.makeText(this@MainActivity,"TEST",Toast.LENGTH_SHORT).show()
            }
        })

    }
}
