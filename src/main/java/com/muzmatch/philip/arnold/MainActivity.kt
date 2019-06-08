package com.muzmatch.philip.arnold

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muzmatch.philip.arnold.data.Message
import com.muzmatch.philip.arnold.data.MessageType
import org.joda.time.DateTime
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var edittext: EditText
    lateinit var adapter: MessageAdapter
    lateinit var layoutManager: LinearLayoutManager
    val messages = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupStartingMessages()

        edittext = findViewById(R.id.editText)
        adapter = MessageAdapter(messages)
        recycler = findViewById(R.id.recycler)
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

        findViewById<View>(R.id.entryButton).setOnClickListener {
            val message = Message(MessageType.FROM, edittext.text.toString(), Utils.toUTC(DateTime()))
            adapter.add(message)
            layoutManager.scrollToPosition(0)
            edittext.text = null
        }
    }

    // these are added "backwards" as the most recent message will be first in the array
    fun setupStartingMessages() {
        messages.add(Message(MessageType.FROM, "Hey hows it going", "2019-05-20 17:00:00"))
        messages.add(Message(MessageType.TO, "Hey hows it going?", "2019-05-16 15:58:30"))
        messages.add(Message(MessageType.TO, "Hey hows it going today?", "2019-05-16 16:55:15"))
        messages.add(Message(MessageType.STATUS, "Zippy rematched", "2019-05-15 16:50:00"))
        messages.add(Message(MessageType.FROM, "Hi", "2019-05-12 18:20:00"))
        messages.add(Message(MessageType.STATUS, "Jamill requested rematch", "2019-05-12 16:00:00"))
        messages.add(Message(MessageType.STATUS, "Zippy unmatched", "2019-05-12 15:30:00"))
        messages.add(Message(MessageType.TO, "Ndhd", "2019-05-11 15:05:15"))
        messages.add(Message(MessageType.TO, "Hi", "2019-05-11 15:05:00"))
        messages.add(Message(MessageType.FROM, "Hey", "2019-05-10 15:00:00"))
    }
}
