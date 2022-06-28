package com.camelsoft.ratetz._presentation.utils

import android.Manifest
import android.content.Context
import com.camelsoft.ratetz.R
import com.camelsoft.ratetz.common.state.StateSync
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.lang.Exception

fun reqPermissions(context: Context, report: (StateSync<Boolean>) -> Unit) {
    try {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(permReport: MultiplePermissionsReport?) {
                    permReport?.let {
                        if(permReport.areAllPermissionsGranted())
                            report(StateSync.Success<Boolean>(true))
                        else
                            report(StateSync.Success<Boolean>(false))
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) { token?.continuePermissionRequest() }
            })
            .withErrorListener {
                val errMessage = context.resources.getString(R.string.error_in)+
                        " ReqPermissions.DexterError: ${it.name}"
                report(StateSync.Error<Boolean>(errMessage))
            }
            .check()

    }catch (e: Exception) {
        e.printStackTrace()
        val errMessage = context.resources.getString(R.string.error_in)+
                " ReqPermissions.getPermissions: ${e.message}"
        report(StateSync.Error<Boolean>(errMessage))
    }
}
