package com.aman.currencyconverter.core.utils

import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication

actual fun showMessage(message: String) {
    val alert = UIAlertController.alertControllerWithTitle(
        title = "Error",
        message = message,
        preferredStyle = UIAlertControllerStyleAlert
    )

    alert.addAction(UIAlertAction.actionWithTitle("OK", UIAlertActionStyleDefault, null))

    val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootViewController?.presentViewController(alert, animated = true, completion = null)
}