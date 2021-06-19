package com.e.commerce.ui.fragments.user.address.addresses;

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.e.commerce.R
import com.e.commerce.data.model.auth.AddressPojo.AddressDataPojo.AddressObjectPojo
import com.e.commerce.databinding.ItemAddressBinding
import com.e.commerce.util.SharedPref
import timber.log.Timber

class AddressAdapter(val addressInterface: AddressInterface) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    private var addressPojoList = ArrayList<AddressObjectPojo>()
    private var selected = -1
    private var context: Context? = null
    private var sharedPref: SharedPref? = null

    fun setData(addressPojos: ArrayList<AddressObjectPojo>) {
        addressPojos.also { this.addressPojoList = it }
    }

    fun clear() {
        addressPojoList.clear()
        notifyDataSetChanged()
    }

    fun removeProduct(index: Int) {
        addressPojoList.removeAt(index)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        context = parent.context
        sharedPref = SharedPref(context as Context)
        return AddressViewHolder(ItemAddressBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val addresses: AddressObjectPojo = addressPojoList[position]
        addresses.let { holder.bind(it, position) }
    }

    override fun getItemCount(): Int = addressPojoList.size

    inner class AddressViewHolder(var binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root) {
        private var addressBundle = Bundle()
        private val addressObject = ArrayList<String>()


        fun bind(addressPojo: AddressObjectPojo, position: Int) {
            binding.tvAddressName.text = addressPojo.name
            binding.tvAddressDetails.text = String.format("${addressPojo.details}, ${addressPojo.region}, ${addressPojo.city}")

            val onClick = View.OnClickListener { v ->
                selected = position
                addressObject.add(addressPojo.toString())
                context?.getString(R.string.address_parcelable).let { context ->
                    addressBundle.putParcelable(context, addressPojo)
                    Timber.d("getAddressIdParcelableClicked::${addressBundle.getParcelable<AddressObjectPojo>(context)}")
                    if (context != null) {
                        sharedPref?.setAddressGson(context, addressPojo)
                    }
                }

                context?.getString(R.string.address_id)?.let { context ->
                    sharedPref?.setInt(context, addressPojo.id)
                    Timber.d("getAddressIdClicked::${sharedPref?.getInt(context)}")
                }
                Timber.d("AddressName::${addressPojoList[position].name}, AddressId::${addressPojoList[position].id}, isChecked::${binding.cbCheckAddress.isChecked}")
                notifyDataSetChanged()
            }

            binding.cbCheckAddress.isChecked = position == selected
            binding.cbCheckAddress.setOnClickListener(onClick)
            binding.cbCheckAddress.isChecked =
                addressPojo.id == context?.getString(R.string.address_id)?.let { context -> sharedPref?.getInt(context) }

            binding.icEdit.setOnClickListener { v ->
                addressBundle.putParcelable(context?.getString(R.string.edit_address), addressPojo)
                addressBundle.putBoolean(context?.getString(R.string.isEditClicked), true)
                v.findNavController().navigate(R.id.action_address_to_addAddress, addressBundle)
                Timber.d("${addressPojo.name} Clicked Edit, True")
            }

            binding.icDelete.setOnClickListener {
                addressInterface.onAddressItemClick(addressPojo)
            }
        }
    }

    interface AddressInterface {
        fun onAddressItemClick(address: AddressObjectPojo)
    }
}