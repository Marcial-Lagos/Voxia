package com.example.guia8.ui.utils

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresPermission

@RequiresPermission(Manifest.permission.VIBRATE)
fun vibrarAcierto(context: Context) {

    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE)
                    as VibratorManager

        vibratorManager.defaultVibrator

    } else {

        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (!vibrator.hasVibrator()) {
        return
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        vibrator.vibrate(
            VibrationEffect.createOneShot(
                450L,  // duración: 450 milisegundos
                255    // intensidad máxima
            )
        )

    } else {

        @Suppress("DEPRECATION")
        vibrator.vibrate(450L)
    }
}