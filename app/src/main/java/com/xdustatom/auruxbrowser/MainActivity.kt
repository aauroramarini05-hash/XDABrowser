package com.xdustatom.auruxbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val versionName = "1.303.02"
    private val updateUrl = "https://aauroramarini05-hash.github.io/XDABrowser/version.txt"
    private val websiteUrl = "https://aauroramarini05-hash.github.io/XDABrowser/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        val webView = findViewById<WebView>(R.id.webView)
        val urlBar = findViewById<EditText>(R.id.urlBar)
        val btnMenu = findViewById<ImageButton>(R.id.btnMenu)
        val btnGo = findViewById<ImageButton>(R.id.btnGo)
        val btnUpdate = findViewById<Button>(R.id.btnCheckUpdate)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://www.google.com")

        btnMenu.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

        btnGo.setOnClickListener {
            val url = urlBar.text.toString()
            if (!url.startsWith("http")) {
                webView.loadUrl("https://www.google.com/search?q=" + Uri.encode(url))
            } else {
                webView.loadUrl(url)
            }
        }

        btnUpdate.setOnClickListener {
            checkForUpdates()
        }
    }

    private fun checkForUpdates() {
        thread {
            try {
                val latest = URL(updateUrl).readText().trim()
                runOnUiThread {
                    if (latest != versionName) {
                        Toast.makeText(this, "Aggiornamento disponibile!", Toast.LENGTH_LONG).show()
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl)))
                    } else {
                        Toast.makeText(this, "Sei aggiornato.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Errore controllo update.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
