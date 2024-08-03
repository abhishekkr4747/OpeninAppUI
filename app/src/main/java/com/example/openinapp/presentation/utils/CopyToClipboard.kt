package com.example.openinapp.presentation.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.example.openinapp.model.Link

fun CopyToClipboard(
    context: Context,
    link: Link
) {
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("Link", link.webLink)
    clipboardManager.setPrimaryClip(clipData)

    ToastCopyToClipboard(context)
}


fun ToastCopyToClipboard(context: Context) {
    Toast.makeText(
        context,
        "Copy to clipboard",
        Toast.LENGTH_SHORT
    ).show()
}