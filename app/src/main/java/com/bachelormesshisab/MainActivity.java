package com.bachelormesshisab;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {
    private static final int CREATE_BACKUP_REQUEST = 4101;
    private static final int OPEN_BACKUP_REQUEST = 4102;
    private WebView webView;
    private String pendingBackupJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new AndroidBridge(), "AndroidBridge");
        webView.loadUrl("file:///android_asset/index.html");
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void saveBackup(String json) {
            runOnUiThread(() -> {
                pendingBackupJson = json;
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/json");
                intent.putExtra(Intent.EXTRA_TITLE, "mess-hisab-backup.json");
                try {
                    startActivityForResult(intent, CREATE_BACKUP_REQUEST);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "ফাইল সেভ করার অ্যাপ পাওয়া যায়নি", Toast.LENGTH_LONG).show();
                }
            });
        }

        @JavascriptInterface
        public void importBackup() {
            runOnUiThread(() -> {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/json");
                try {
                    startActivityForResult(intent, OPEN_BACKUP_REQUEST);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "ফাইল খোলার অ্যাপ পাওয়া যায়নি", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null || data.getData() == null) return;
        Uri uri = data.getData();
        try {
            if (requestCode == CREATE_BACKUP_REQUEST && pendingBackupJson != null) {
                try (OutputStream output = getContentResolver().openOutputStream(uri)) {
                    if (output == null) throw new IllegalStateException("Output stream unavailable");
                    output.write(pendingBackupJson.getBytes(StandardCharsets.UTF_8));
                    output.flush();
                }
                pendingBackupJson = null;
                Toast.makeText(this, "ব্যাকআপ সেভ হয়েছে", Toast.LENGTH_LONG).show();
            } else if (requestCode == OPEN_BACKUP_REQUEST) {
                StringBuilder content = new StringBuilder();
                try (InputStream input = getContentResolver().openInputStream(uri);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) content.append(line);
                }
                JSONObject parsed = new JSONObject(content.toString());
                String safeJson = JSONObject.quote(parsed.toString());
                webView.evaluateJavascript("window.loadBackupFromAndroid && window.loadBackupFromAndroid(" + safeJson + ");", null);
                Toast.makeText(this, "ব্যাকআপ ফাইল খোলা হয়েছে", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "ফাইল প্রসেস করা যায়নি: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null) {
            boolean handled = webView.evaluateJavascript("window.handleAndroidBack ? window.handleAndroidBack() : false", null) != null;
            // Keep the normal Android back behavior; the web app has its own tab navigation.
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.removeJavascriptInterface("AndroidBridge");
            webView.destroy();
        }
        super.onDestroy();
    }
}
