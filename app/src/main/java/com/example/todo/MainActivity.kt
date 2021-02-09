package com.example.todo

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var listAdapter: MyAdapter
    private var todoListItems = ArrayList<ListItem>()
    private var comparator: Comparator<ListItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if(savedInstanceState!=null) {
            todoListItems = savedInstanceState.getParcelableArrayList("items")
        }

        setContentView(R.layout.activity_main)

        listView = findViewById(R.id._list)
        listAdapter = MyAdapter(this, todoListItems)
        listView.adapter = listAdapter


        fab.setOnClickListener{
            val intent  = Intent(this@MainActivity, AddTask::class.java)
            startActivityForResult(intent, 1)
        }

        listView.setOnItemLongClickListener { parent, view, position, _ ->
            val item = parent.getItemAtPosition(position)
            view.animate().setDuration(500).alpha(0F)  //
                .withEndAction {
                    todoListItems.remove(item)
                    listAdapter.notifyDataSetChanged()
                    view.alpha=1F
                }

            return@setOnItemLongClickListener true
        }

        setSupportActionBar(toolbar)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            R.id.item1 -> {
                comparator = object : Comparator<ListItem> {
                    override fun compare(p1: ListItem, p2: ListItem): Int = when {
                        p1.priority > p2.priority -> 1
                        p1.priority == p2.priority -> 0
                        else -> -1
                    }
                }
                Collections.sort(todoListItems, comparator)
                listAdapter.notifyDataSetChanged()
            }

            R.id.item2 -> {
                comparator = object : Comparator<ListItem> {
                    override fun compare(p1: ListItem, p2: ListItem): Int = when {
                        p1.priority > p2.priority -> -1
                        p1.priority == p2.priority -> 0
                        else -> 1
                    }
                }
                Collections.sort(todoListItems, comparator)
                listAdapter.notifyDataSetChanged()
            }

            R.id.item3 -> {
                comparator = object : Comparator<ListItem> {
                    override fun compare(p1: ListItem, p2: ListItem): Int = when {
                    p1.time.replace(":","").toInt() > p2.time.replace(":","").toInt() -> 1
                        p1.time.replace(":","").toInt() == p2.time.replace(":","").toInt() -> 1
                        else -> -1
                    }
                }
                Collections.sort(todoListItems, comparator)
                listAdapter.notifyDataSetChanged()
            }

            R.id.item4 -> {
                comparator = object : Comparator<ListItem> {
                    override fun compare(p1: ListItem, p2: ListItem): Int = when {
                        p1.time.replace(":","").toInt() > p2.time.replace(":","").toInt() -> -1
                        p1.time.replace(":","").toInt() == p2.time.replace(":","").toInt() -> 1
                        else -> 1
                    }
                }
                Collections.sort(todoListItems, comparator)
                listAdapter.notifyDataSetChanged()
            }

            else -> { }

            }
        return super.onOptionsItemSelected(item)
        }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                var bundle = data.extras
                var title = bundle.getString("title")
                var time = bundle.getString("time")
                var image = bundle.getInt("image")
                var priority = bundle.getInt("priority")
                var listItem = ListItem(priority,title,time,image)
                todoListItems.add(listItem)
                listAdapter.notifyDataSetChanged()

                var undoOnClickListener: View.OnClickListener = View.OnClickListener { view ->
                    todoListItems.removeAt(todoListItems.size - 1)
                    listAdapter.notifyDataSetChanged()
                    Snackbar.make(view, "Task removed", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
                }

                Snackbar.make(getWindow().getDecorView().getRootView(), "A new task has been added", Snackbar.LENGTH_LONG)
                    .setAction("Undo", undoOnClickListener).show()
            }
        }

    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putParcelableArrayList("items",todoListItems)
        super.onSaveInstanceState(savedInstanceState)
    }

}

