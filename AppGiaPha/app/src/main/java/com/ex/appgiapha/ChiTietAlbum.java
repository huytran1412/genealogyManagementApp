package com.ex.appgiapha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ex.appgiapha.adapter.AnhAdapter;
import com.ex.appgiapha.databinding.ActivityChiTietAlbumBinding;
import com.ex.appgiapha.db.AnhDAO;
import com.ex.appgiapha.model.Anh;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Objects;

public class ChiTietAlbum extends AppCompatActivity {
    ActivityChiTietAlbumBinding binding;
    List<Anh> anhList;
    AnhDAO anhDAO;
    int id;
    String tenAlbum;
    private Uri selectedImageUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChiTietAlbumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = getIntent().getIntExtra("id", 0);
        binding.btnBack.setOnClickListener(v -> finish());
        tenAlbum = getIntent().getStringExtra("tenAlbum");
        binding.tenAlbum.setText(tenAlbum);

        // Khởi tạo StorageReference
        storageReference = FirebaseStorage.getInstance().getReference();

        // Bắt sự kiện khi nhấn nút để chọn ảnh từ album
        binding.btnAddGiaPha.setOnClickListener(v -> openGallery());
        anhDAO = new AnhDAO(this);
        makeList();
    }
    void makeList(){

        anhList = anhDAO.getAllAnhByAlbumId(id);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        binding.rvLevel.setLayoutManager(layoutManager);
        binding.rvLevel.setAdapter(new AnhAdapter(this, anhList));
    }


    // Mở hộp thoại chọn ảnh từ album
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        pickImage.launch(Intent.createChooser(intent, "Chọn ảnh"));
    }

    // Xử lý kết quả sau khi chọn ảnh từ album
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    // Upload ảnh lên Firebase Storage
                    uploadImageToFirebase(selectedImageUri);
                }
            });

    // Đẩy ảnh được chọn lên Firebase Storage
    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StorageReference fileRef = storageReference.child("images/" + System.currentTimeMillis() + ".jpg");
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Ảnh đã được tải lên thành công
                        Toast.makeText(this, "Ảnh đã được tải lên thành công", Toast.LENGTH_SHORT).show();

                        // Lấy link của ảnh sau khi tải lên thành công
                        fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            anhDAO.addAnh(new Anh(id, imageUrl));
                            makeList();
                            // Sử dụng link ảnh ở đây, ví dụ: lưu vào cơ sở dữ liệu
                            // Nếu bạn muốn thực hiện hành động nào đó với link ảnh, hãy viết code ở đây
                            progressDialog.dismiss(); // Đóng loading indicator sau khi hoàn thành
                        }).addOnFailureListener(e -> {
                            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss(); // Đóng loading indicator sau khi hoàn thành (thất bại)
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Đã xảy ra lỗi khi tải ảnh lên
                        Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss(); // Đóng loading indicator sau khi hoàn thành (thất bại)
                    });
        } else {
            Toast.makeText(this, "Không có ảnh được chọn", Toast.LENGTH_SHORT).show();
        }
    }
}
