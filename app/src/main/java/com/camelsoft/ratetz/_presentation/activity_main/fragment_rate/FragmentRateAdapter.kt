package com.camelsoft.ratetz._presentation.activity_main.fragment_rate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.camelsoft.ratetz.R
import com.camelsoft.ratetz._domain.models.MRateRv
import com.camelsoft.ratetz.databinding.FragmentRateItemBinding

class FragmentRateAdapter : RecyclerView.Adapter<FragmentRateAdapter.ViewHolder>(), Filterable {

    private var list: List<MRateRv> = ArrayList()
    var setOnItemClickListener: ((Int) -> Unit)? = null
    var setOnItemLongClickListener: ((Int) -> Unit)? = null

    fun getList() = list

    fun submitList(newList: List<MRateRv>) {
        val oldList = list
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ListItemsDiffCallback (oldList = oldList, newList = newList)
        )
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    private class ListItemsDiffCallback (
        var oldList: List<MRateRv>,
        var newList: List<MRateRv>
    ): DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    inner class ViewHolder(private var binding : FragmentRateItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (mRateRv: MRateRv) {
            binding.apply {
                textCurr.text = mRateRv.name
                textRate.text = mRateRv.rate
                if (mRateRv.isSelected)
                    imageSort.setImageResource(R.drawable.img_star_yellow_32)
                else
                    imageSort.setImageResource(R.drawable.img_star_black_32)
            }
            itemView.apply {
                setOnClickListener {
                    setOnItemClickListener?.invoke(adapterPosition)
                }
                setOnLongClickListener {
                    setOnItemLongClickListener?.invoke(adapterPosition)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentRateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    override fun getFilter(): Filter = favoriteFilter

    private val favoriteFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            val filteredList = mutableListOf<MRateRv>()
            list.forEach { mRateRv -> if (mRateRv.isSelected) filteredList.add(mRateRv) }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(charSequence: CharSequence?, results: FilterResults?) {
            list = ArrayList()
            (list as ArrayList<MRateRv>).addAll(results?.values as Collection<MRateRv>)
            notifyDataSetChanged()
        }
    }
}