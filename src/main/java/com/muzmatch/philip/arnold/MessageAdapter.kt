package com.muzmatch.philip.arnold

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.*
import com.muzmatch.philip.arnold.data.Message
import com.muzmatch.philip.arnold.data.MessageType
import kotlin.collections.ArrayList

class MessageAdapter() : Adapter<ViewHolder>() {
    val TAG = "MessageAdapter"
    lateinit var messages: ArrayList<Message>

    constructor(messages: ArrayList<Message>) : this() {
        this.messages = messages
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages.get(position)
        val previous = if(position < messages.size - 1) { messages.get(position + 1) } else { null }
        val next = if(position > 0) { messages.get(position - 1) } else { null }

        var showDate = false
        var showTick = true

        if (position > 0) {
            if (next != null && next.type == message.type) {
                if (Utils.differenceOf20Seconds(next.timestamp, message.timestamp)) {
                    showTick = false
                }
            }
        }

        if (position == messages.size - 1) {
            showDate = true
        } else if (previous != null && !Utils.differenceOfAnHour(previous.timestamp, message.timestamp)){
            showDate = true
        }

        val messageDate = Utils.formatDateTimeLocal(Utils.fromUTC(message.timestamp))
        if (holder is FromMessageHolder) {
            holder.datetime.visibility = if (showDate) { View.VISIBLE } else { View.GONE }
            holder.datetime.text = messageDate
            holder.message.text = message.message
            holder.message.setBackgroundResource(if (showTick) { R.drawable.bubble_from_tail } else { R.drawable.bubble_from })
        } else if (holder is ToMessageHolder) {
            holder.datetime.visibility = if (showDate) { View.VISIBLE } else { View.GONE }
            holder.datetime.text = messageDate
            holder.message.text = message.message
            holder.message.setBackgroundResource(if (showTick) { R.drawable.bubble_to_tail } else { R.drawable.bubble_to })
        } else if (holder is StatusMessageHolder) {
            holder.datetime.visibility = if (showDate) { View.VISIBLE } else { View.GONE }
            holder.datetime.text = messageDate
            holder.message.text = message.message
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        when (viewType) {
            MessageType.FROM -> {
                val parent = layoutInflater.inflate(R.layout.message_layout_from, viewGroup,false)
                return FromMessageHolder(parent)
            }
            MessageType.TO -> {
                val parent = layoutInflater.inflate(R.layout.message_layout_to, viewGroup,false)
                return ToMessageHolder(parent)
            }
            MessageType.STATUS -> {
                val parent = layoutInflater.inflate(R.layout.message_layout_status, viewGroup,false)
                return StatusMessageHolder(parent)
            }
        }
        return FromMessageHolder(viewGroup)
    }

    override fun getItemViewType(position: Int): Int {
        return messages.get(position).type
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun add(message: Message) {
        messages.add(0, message)
        notifyItemInserted(0)
        notifyItemChanged(1)
    }

    class FromMessageHolder(view: View): ViewHolder(view) {
        val datetime: TextView = view.findViewById(R.id.datetime)
        val message: TextView = view.findViewById(R.id.message)
    }
    class ToMessageHolder(view: View) : ViewHolder(view) {
        val datetime: TextView = view.findViewById(R.id.datetime)
        val message: TextView = view.findViewById(R.id.message)
    }
    class StatusMessageHolder(view: View) : ViewHolder(view) {
        val datetime: TextView = view.findViewById(R.id.datetime)
        var message: TextView = view.findViewById(R.id.message)
    }
}

