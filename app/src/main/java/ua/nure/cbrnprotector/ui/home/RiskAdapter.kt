package ua.nure.cbrnprotector.ui.home

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.databinding.RiskItemBinding
import ua.nure.cbrnprotector.domain.Risk

class RiskAdapter(val context: Context) : RecyclerView
.Adapter<RiskAdapter.MyViewHolder>() {

    private val risks = Risk.values().toList()

    class MyViewHolder(binding: RiskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvRiskName: TextView = binding.tvRiskName
        val tvRiskLevel: TextView = binding.tvRiskLevel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = RiskItemBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val risk = risks[position]
        val from = if (position == 0) "0" else risks[position - 1].value.toString()
        holder.tvRiskLevel.text =
            context.getString(R.string.risk_level_text, from, risk.value.toString())
        holder.tvRiskName.text = context.getString(risk.nameResource)
        holder.tvRiskName.backgroundTintList = ColorStateList.valueOf(context.getColor(risk.color))
    }

    override fun getItemCount() = risks.size
}