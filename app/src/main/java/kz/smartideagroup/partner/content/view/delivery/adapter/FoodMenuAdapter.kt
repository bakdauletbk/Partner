package kz.smartideagroup.partner.content.view.delivery.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.content.model.response.delivery.DishDto
import kz.smartideagroup.partner.content.view.delivery.MenuFragment

class FoodMenuAdapter(
    private val callback: MenuFragment,
    private val dishList: ArrayList<DishDto>
) :
    RecyclerView.Adapter<FoodMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dishList[position], callback)
    }

    override fun getItemCount(): Int = dishList.size

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val tvFoodName = root.findViewById<TextView>(R.id.tv_food)
        private val ivLogo = root.findViewById<ImageView>(R.id.iv_foods)
        private val clNotAvailable = root.findViewById<ConstraintLayout>(R.id.cl_not_available)

        @SuppressLint("SetTextI18n")
        fun bind(dishList: DishDto, callback: MenuFragment) {
            tvFoodName.text = dishList.name

            Glide
                .with(callback.requireContext())
                .load(Constants.BASE_IMAGE_URL + dishList.img)
                .centerCrop()
                .into(ivLogo)

            when (dishList.status) {
                Constants.ONE -> clNotAvailable.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                callback.foodAlertDialog(dishList)
            }

        }
    }

}