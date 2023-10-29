package ua.nure.cbrnprotector.base.popupplaceholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.nure.cbrnprotector.databinding.BasePopupItemBinding

class ConditionsAdapter(
    private val onItemClickListener: (BaseConditions) -> Unit
) : ListAdapter<BaseConditions, ConditionsViewHolder>(ConditionsDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionsViewHolder {
        return ConditionsViewHolder(
            BasePopupItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ConditionsViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }
}

class ConditionsViewHolder(
    val binding: BasePopupItemBinding,
    private val onItemClickListener: (BaseConditions) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(condition: BaseConditions) {
        binding.item.text = condition.name
        binding.root.setOnClickListener {
            onItemClickListener.invoke(condition)
        }
    }
}

class ConditionsDiffUtils() : DiffUtil.ItemCallback<BaseConditions>() {

    override fun areItemsTheSame(oldItem: BaseConditions, newItem: BaseConditions): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: BaseConditions, newItem: BaseConditions): Boolean {
        return oldItem == newItem
    }

}