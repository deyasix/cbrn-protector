package ua.nure.cbrnprotector.base.popupplaceholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.nure.cbrnprotector.databinding.BasePopupItemBinding
import ua.nure.cbrnprotector.domain.Valuable

class ConditionsAdapter(
    private val onItemClickListener: (Valuable) -> Unit
) : ListAdapter<Valuable, ConditionsViewHolder>(ConditionsDiffUtils()) {

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
    private val onItemClickListener: (Valuable) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(condition: Valuable) {
        binding.item.text = binding.root.context.getString(condition.nameResource)
        binding.root.setOnClickListener {
            onItemClickListener.invoke(condition)
        }
    }
}

class ConditionsDiffUtils : DiffUtil.ItemCallback<Valuable>() {

    override fun areItemsTheSame(oldItem: Valuable, newItem: Valuable): Boolean {
        return oldItem.nameResource == newItem.nameResource
    }

    override fun areContentsTheSame(oldItem: Valuable, newItem: Valuable): Boolean {
        return oldItem.equals(newItem)
    }

}