package dev.rohman.gridpagersnaphelper

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hhl.gridpagersnaphelper.GridPagerSnapHelper
import com.hhl.gridpagersnaphelper.GridPagerUtils
import com.hhl.gridpagersnaphelper.transform.TwoRowDataTransform
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View.inflate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val row = 2
        val column = 3

        val rvUsers = findViewById<RecyclerView>(R.id.rvUsers)
        rvUsers.setHasFixedSize(true)

        val layoutManager = GridLayoutManager(this, row, LinearLayoutManager.HORIZONTAL, false)
        rvUsers.layoutManager = layoutManager

        GridPagerSnapHelper().apply {
            setRow(row).setColumn(column)
            attachToRecyclerView(rvUsers)
        }

        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val itemWidth = screenWidth / column

        val users = listOf(
            "User 1",
            "User 2",
            "User 3",
            "User 4",
            "User 5",
            "User 6",
            "User 7",
            "User 8",
            "User 9",
            "User 10",
            "User 11",
            "User 12"
        )
        val transformedUsers =
            GridPagerUtils.transformAndFillEmptyData(TwoRowDataTransform(column), users)

        val adapter = RecyclerViewAdapter(this, itemWidth)
        adapter.list = transformedUsers

        rvUsers.adapter = adapter
    }
}

internal class RecyclerViewAdapter(
    private val context: Context,
    private val itemWidth: Int
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var itemWidth: Int = 0

        constructor(view: View, itemWidth: Int) : this(view) {
            this.itemWidth = itemWidth
        }

        fun bindData(user: String) {
            val layoutParams = view.layoutParams
            layoutParams.width = itemWidth

            val textView = view.findViewById<TextView>(R.id.tvUser)
            textView.text = user
        }
    }

    internal var list = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_user, parent, false),
            itemWidth
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size
}