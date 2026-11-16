package com.example.postoffice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.postoffice.databinding.ActivityMainBinding
import com.example.postoffice.ui.auth.AuthManager
import com.example.postoffice.ui.fragment.NameSearchFragment
import com.example.postoffice.ui.fragment.PincodeSearchFragment
import com.example.postoffice.ui.fragment.SavedPinFragment
import com.example.postoffice.ui.viewmodel.PostalViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PostalViewModel

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Signed in as ${account.displayName ?: account.email}", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Firebase Auth error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Signed in locally as ${account.displayName ?: account.email}", Toast.LENGTH_SHORT).show()
                binding.tvUserName.text = account.displayName ?: "Google User"
                binding.tvUserEmail.text = account.email ?: ""
                binding.btnAuthAction.text = "Sign Out"
            }
        } catch (e: ApiException) {
            Toast.makeText(this, "Sign-in cancelled or failed (${e.statusCode})", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PostalViewModel::class.java]

        AuthManager.init(this)
        setupAuthUI()
        setupViewPager()
    }

    private fun setupAuthUI() {
        AuthManager.currentUser.observe(this) { user ->
            if (user != null) {
                binding.tvUserName.text = user.displayName ?: "User"
                binding.tvUserEmail.text = user.email ?: ""
                binding.btnAuthAction.text = "Sign Out"
                binding.btnAuthAction.setOnClickListener {
                    AuthManager.signOut()
                    Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.tvUserName.text = "Guest User"
                binding.tvUserEmail.text = "Tap to sign in with Google"
                binding.btnAuthAction.text = "Sign In"
                binding.btnAuthAction.setOnClickListener {
                    launchGoogleSignIn()
                }
            }
        }
    }

    private fun launchGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()
        val signInClient = GoogleSignIn.getClient(this, gso)
        googleSignInLauncher.launch(signInClient.signInIntent)
    }

    private fun setupViewPager() {
        val adapter = MainPagerAdapter(this)
        binding.viewPager.adapter = adapter

        com.google.android.material.tabs.TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "PIN Code"
                1 -> "Branch Name"
                2 -> "Saved PINs"
                else -> ""
            }
        }.attach()
    }

    private inner class MainPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): androidx.fragment.app.Fragment {
            return when (position) {
                0 -> PincodeSearchFragment()
                1 -> NameSearchFragment()
                2 -> SavedPinFragment().apply {
                    onSavedPinSelected = { pincode ->
                        binding.viewPager.currentItem = 0
                        viewModel.searchByPincode(pincode)
                    }
                }
                else -> PincodeSearchFragment()
            }
        }
    }
}