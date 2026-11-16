package com.example.postoffice.ui.fragment

import androidx.fragment.app.Fragment

class SavedPinFragment : Fragment() {
    var onSavedPinSelected: ((String) -> Unit)? = null
}
