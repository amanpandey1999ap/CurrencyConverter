package com.aman.currencyconverter.core.utils

import javax.swing.JOptionPane

actual fun showMessage(message: String) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE)
}