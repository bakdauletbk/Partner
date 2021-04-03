package kz.smartideagroup.partner.content.view.delivery.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.content.model.response.delivery.CategoriesItems
import kz.smartideagroup.partner.content.view.delivery.MenuFragment


class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.ViewHolder> {

    private var categoryList: ArrayList<CategoriesItems> = ArrayList()

    private var callback: MenuFragment

    constructor(callback: MenuFragment) : super() {
        this.callback = callback
    }

    fun clear(){
        this.categoryList.clear()
    }

    fun addFoods(categoriesItems: List<CategoriesItems>) {
        this.categoryList.clear()
        this.categoryList.addAll(categoriesItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryList[position], callback)
    }

    override fun getItemCount(): Int = categoryList.size

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val tvFoodName = root.findViewById<TextView>(R.id.tv_category_name)
        private val recyclerView = root.findViewById<RecyclerView>(R.id.rv_food_count)
        private val viewPool = RecyclerView.RecycledViewPool()

        @SuppressLint("SetTextI18n")
        fun bind(categoriesItems: CategoriesItems, callback: MenuFragment) {
            tvFoodName.text = categoriesItems.name

            recyclerView.apply {
                layoutManager = GridLayoutManager(
                    callback.requireContext(),
                    Constants.THREE,
                    GridLayoutManager.VERTICAL,
                    false
                )
                adapter = FoodMenuAdapter(callback, categoriesItems.dishes)
                setRecycledViewPool(viewPool)
            }


        }
    }

}