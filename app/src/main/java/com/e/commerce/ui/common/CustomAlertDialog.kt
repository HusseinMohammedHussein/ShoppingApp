package com.e.commerce.ui.common

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment

class CustomAlertDialog {
    companion object {
        fun alertDialog(context: Context, fragment: Fragment, direction: NavDirections) {
            val dialog = AlertDialog.Builder(context)
            dialog.setMessage("Are you new user?")
            dialog.setPositiveButton("Yes") { _, _ ->
                NavHostFragment.findNavController(fragment).navigate(direction)
            }
            dialog.setNegativeButton("Dismiss") { dialog, _ -> dialog.dismiss() }
            dialog.create()
            dialog.show()
        }
    }
}