package com.example.guia8.ui.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

fun vibrarAcierto(context: Context) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager

        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (!vibrator.hasVibrator()) {
        return
    }

    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            vibrator.vibrate(
                VibrationEffect.createPredefined(
                    VibrationEffect.EFFECT_CLICK
                )
            )
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    80L,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }

        else -> {
            @Suppress("DEPRECATION")
            vibrator.vibrate(80L)
        }
    }
}
