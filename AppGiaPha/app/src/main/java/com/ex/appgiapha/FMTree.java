package com.ex.appgiapha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ex.appgiapha.databinding.ActivityFmtreeBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FMTree extends AppCompatActivity {
    ActivityFmtreeBinding binding;
    private String savedImageFilePath;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFmtreeBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.loadUrl("file:///android_asset/giapha.html");
        binding.webView.getSettings().setBuiltInZoomControls(true);
        String json = getIntent().getStringExtra("json");
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.setInitialScale(2);
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                executeJavaScript(json);
            }
        });
    }
    private void executeJavaScript(String json) {
        String javascriptCode = "function createNode(member) {\n" +
                "        const div = document.createElement(\"div\");\n" +
                "        div.classList.add(\"family-member\");\n" +
                "        div.textContent = member.ten;\n" +
                "        return div;\n" +
                "    }\n" +
                "\n" +
                "    // Hàm để tạo cây gia phả từ dữ liệu\n" +
                "    function createFamilyTree(data, parentId, ul) {\n" +
                "        data.forEach(item => {\n" +
                "        item.thanhVien.forEach(member => {\n" +
                "            if (member.conCua === parentId) {\n" +
                "            const div = createNode(member);\n" +
                "            const li = document.createElement(\"li\");\n" +
                "            li.appendChild(div);\n" +
                "            const childUl = document.createElement(\"ul\");\n" +
                "            li.appendChild(childUl);\n" +
                "            ul.appendChild(li);\n" +
                "            createFamilyTree(data, member.id, childUl);\n" +
                "            }\n" +
                "        });\n" +
                "        });\n" +
                "    }\n" +
                "    // Gọi hàm tạo cây gia phả với dữ liệu và id gốc\n" +
                "    createFamilyTree("+json+" , -1, document.getElementById(\"familyTree\"));";

        // Thực thi mã JavaScript
        binding.webView.evaluateJavascript(javascriptCode, null);
        binding.btnAddGiaPha.setOnClickListener(v -> {
            saveHtmlAsImage(true);
        });
        binding.btnShare.setOnClickListener(v -> {
    shareImage();
        });
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    void saveHtmlAsImage(boolean showToast) {
        // Tạo một bitmap từ WebView
        Bitmap bitmap = Bitmap.createBitmap(binding.webView.getWidth(), binding.webView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        binding.webView.draw(canvas);

        //get name by date time
        StringBuilder name = new StringBuilder("giapha");
        String[] date = java.time.LocalDate.now().toString().split("-");
        String[] time = java.time.LocalTime.now().toString().split(":");
        for (String s : date) {
            name.append(s);
        }
        for (String s : time) {
            name.append(s);
        }
        // Lưu bitmap vào tệp ảnh
        String filename = name + ".png";
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "GiaphaImages");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder, filename);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
            savedImageFilePath = file.getAbsolutePath();
            if(showToast){
                Toast.makeText(this, "Lưu ảnh thành công", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(showToast){
                Toast.makeText(this, "Lưu ảnh thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void shareImage() {
        saveHtmlAsImage(false);
        // Kiểm tra xem đã có đường dẫn đến tệp ảnh đã lưu hay chưa
        if (savedImageFilePath != null) {
            File file = new File(savedImageFilePath);
            if (file.exists()) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));
            } else {
                Toast.makeText(this, "Không tìm thấy tệp ảnh", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Bạn cần lưu ảnh trước khi chia sẻ", Toast.LENGTH_SHORT).show();
        }
    }

}