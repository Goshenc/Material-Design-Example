package com.example.materialtest

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.materialtest.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val fruits = mutableListOf(Fruit("Apple", R.drawable.apple), Fruit("Banana", R.drawable.banana), Fruit("Orange", R.drawable.orange), Fruit("Watermelon", R.drawable.watermelon), Fruit("Pear", R.drawable.pear), Fruit("Grape", R.drawable.grape), Fruit("Pineapple", R.drawable.pineapple), Fruit("Strawberry", R.drawable.strawberry), Fruit("Cherry", R.drawable.cherry), Fruit("Mango", R.drawable.mango))

    val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "这是toolbar"
        supportActionBar?.let{
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.baseline_menu_24)
        }
        binding.navView.setCheckedItem(R.id.navCall)//默认选中 "Call" 菜单项。
        binding.navView.setNavigationItemSelectedListener {
            //点击菜单任意一个选项时 关闭 DrawerLayout（侧滑菜单）。
            //return true 表示点击事件 已处理，不需要再传播。
            binding.drawerLayout.closeDrawers()
            true
        }


        binding.fab.setOnClickListener { view ->
            val snackbar = Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                    Toast.makeText(this, "Data restored", Toast.LENGTH_SHORT).show()
                }

            //获取 Snackbar 的 View 并设置 tintList 强制背景为白色.tiniList超级超级超级级厉害的，能强制修改颜色，
            //可用在button,ImageView,FloatingActionButton,但不能用在popupmenu上，修改popupmenu需要在value中定义style，然后在layout中使用这个style
            val snackbarView = snackbar.view
            snackbarView.backgroundTintList = ColorStateList.valueOf(Color.WHITE)

            // 设置文字为黑色
            val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView?.setTextColor(Color.BLACK)

            // 设置 Action 按钮的颜色（这里示例用黑色，你可以换成其它颜色）
            val actionTextView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
            actionTextView?.setTextColor(Color.BLUE)

            snackbar.show()
        }

        initFruits()
        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapter(this, fruitList)
        binding.recyclerView.adapter = adapter

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        binding.swipeRefresh.setOnRefreshListener(){
            refreshFruits(adapter)
        }




    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbarhhh, menu)//作用是将 res/menu/toolbar.xml 里的菜单项加载到 Toolbar 上。

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> binding.drawerLayout.openDrawer(GravityCompat.START)
            R.id.backup -> Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show()
            R.id.delete -> Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun initFruits() {
        fruitList.clear()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }


    private fun refreshFruits(adapter:FruitAdapter){
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                binding.swipeRefresh.isRefreshing=false
            }
        }
    }

}