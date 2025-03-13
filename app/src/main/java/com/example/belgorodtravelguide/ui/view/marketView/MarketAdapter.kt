package com.example.belgorodtravelguide.ui.view.marketView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.belgorodtravelguide.R
import com.example.belgorodtravelguide.domain.modelMarket.Item
import com.example.belgorodtravelguide.ui.viewModel.profileAndMarketVM.ProfileAndMarketViewModel
import com.squareup.picasso.Picasso

class MarketAdapter(
    private val items: List<Item>,
    private val viewModel: ProfileAndMarketViewModel,
    private val lifecycleOwner: Fragment //Добавляем Fragment чтобы получить доступ к lifecycleScope
) : RecyclerView.Adapter<MarketAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        val price: TextView = itemView.findViewById(R.id.price)
        val seller: TextView = itemView.findViewById(R.id.seller)
        val description: TextView = itemView.findViewById(R.id.product_description)
        val buyButton: Button = itemView.findViewById(R.id.buy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item_market, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = items[position]

        Picasso.get()
            .load(product.imageRes)
            .into(holder.imgProduct)

        //текстовые значения
        holder.price.text = "${product.price} Rur"
        holder.seller.text = product.seller
        holder.description.text = product.description

        // Обработка нажатия на кнопку "Купить"
        // Подписываемся на LiveData с деньгами
        viewModel.money.observe(lifecycleOwner.viewLifecycleOwner) { money ->
            holder.buyButton.setOnClickListener {
                if (money >= product.price) {
                    val updatedMoney = money - product.price
                    viewModel.updateMoney(updatedMoney) // Передаем новое значение денег
                    Toast.makeText(holder.itemView.context, "Вы купили товар: ${product.description}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(holder.itemView.context, "Недостаточно средств для покупки", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
