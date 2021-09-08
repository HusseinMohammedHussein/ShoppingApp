package com.e.commerce.ui.fragments.user.address.addresses;

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.e.commerce.R
import com.e.commerce.data.model.auth.address.AddressesDataPojo.AddressDataPojo
import com.e.commerce.databinding.ItemAddressBinding
import com.e.commerce.interfaces.AddressInterface
import com.e.commerce.util.SharedPref
import timber.log.Timber

@SuppressLint("NotifyDataSetChanged")
class AddressAdapter(var context: Context, val addressInterface: AddressInterface, var addresses: ArrayList<AddressDataPojo>) :
    RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    private var selected = -1
    private lateinit var sharedPref: SharedPref

    fun clear() {
        addresses.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        context = parent.context
        sharedPref = SharedPref(context)
        return AddressViewHolder(ItemAddressBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) = holder.bind(addresses[position], position)

    override fun getItemCount(): Int = addresses.size

    inner class AddressViewHolder(var binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root) {
        private var addressBundle = Bundle()
        private val addressObject = ArrayList<String>()

        fun bind(addressPojo: AddressDataPojo, position: Int) {
            binding.tvAddressName.text = addressPojo.name
            binding.tvAddressDetails.text = String.format("${addressPojo.details}, ${addressPojo.region}, ${addressPojo.city}")

            val onClick = View.OnClickListener {
                selected = position
                addressObject.add(addressPojo.toString())
                context.getString(R.string.address_parcelable).let {
                    addressBundle.putParcelable(it, addressPojo)
                    Timber.d("getAddressIdParcelableClicked::${addressBundle.getParcelable<AddressDataPojo>(it)}")
                    sharedPref.setAddressGson(it, addressPojo)
                }

                context.getString(R.string.address_id).let {
                    sharedPref.setInt(it, addressPojo.id)
                    with(Timber) { d("getAddressIdClicked::${sharedPref.getInt(it)}") }
                }
                Timber.d("AddressName::${addresses[position].name}, AddressId::${addresses[position].id}, isChecked::${binding.cbCheckAddress.isChecked}")
                notifyDataSetChanged()
            }

            binding.cbCheckAddress.isChecked = position == selected
            binding.cbCheckAddress.setOnClickListener(onClick)
            binding.cbCheckAddress.isChecked =
                addressPojo.id == context.getString(R.string.address_id).let { context -> sharedPref.getInt(context) }

            binding.icEdit.setOnClickListener { v ->
                addressBundle.putParcelable(context.getString(R.string.edit_address), addressPojo)
                addressBundle.putBoolean(context.getString(R.string.isEditClicked), true)
                v.findNavController().navigate(R.id.action_address_to_addAddress, addressBundle)
                Timber.d("${addressPojo.name} Clicked Edit, True")
            }

            binding.icDelete.setOnClickListener {
                addressInterface.onAddressItemClick(addressPojo)
            }
        }
    }
}