# GridPagerSnapHelper

A powerful tools to impl grid paging layout by RecyclerView,support horizontal and vertical page

Source: https://github.com/hanhailong/GridPagerSnapHelper

# Preview

https://user-images.githubusercontent.com/26162164/134169613-954f0705-c12c-4517-954c-8c6bc685f6ed.mp4

# Usage:

for example:

```
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
```

**Step 1. setLayoutManager:**

You'd better set a horizontal direction *GridLayoutManager*

**Step 2. RecyclerView attach to GridPagerSnaperHelper**

```
GridPagerSnapHelper().apply {
    setRow(row).setColumn(column)
    attachToRecyclerView(rvUsers)
}
```

Here,you must set row and column

**Step 3. transform data list**

if your src data is *dataList*,you must transform it to dst data

```
val transformedUsers = GridPagerUtils.transformAndFillEmptyData(TwoRowDataTransform(column), users)
```
Here, i have provided three transform order functions

1. OneRowDataTransform

    mapping one row,n column
2. TwoRowDataTransform

    mapping two row,n column 
3. ThreeRowDataTransform

    mapping three row,n column

You can impl your custom row functions by extends **AbsRowDataTransform**

# Why use AbsRowDataTransform?

In general，horizontal direction GridLayoutManager layout like this:

![](https://raw.githubusercontent.com/hanhailong/GridPagerSnapHelper/master/screenshot/GridLayoutManager_Horizontal_Normal.png)

But, we want is the following case :

![](https://raw.githubusercontent.com/hanhailong/GridPagerSnapHelper/master/screenshot/GridLayoutManager_Horizontal_Tile.png)

so,We need to make a transformation of the data.Here,**AbsRowDataTransform** can meet your needs.

# Author

hanhailong worked in 58同城，A fantastic website

hanhailong.cool@163.com
