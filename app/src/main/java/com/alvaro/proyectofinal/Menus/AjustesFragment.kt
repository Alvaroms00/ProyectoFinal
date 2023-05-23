package com.alvaro.proyectofinal.Menus

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.alvaro.proyectofinal.R

class AjustesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferencias, rootKey)
    }


}