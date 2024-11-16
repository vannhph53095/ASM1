package fpoly.ph53095.asm1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class manhinhdangnhap extends AppCompatActivity {
    private EditText txtEmail, txtPassword;
    private Button btnDangNhap, accountIcon, btndenmanhinhdangky;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhdangnhap);
        btndenmanhinhdangky = findViewById(R.id.btndenmanhinhdangky);

        btndenmanhinhdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(manhinhdangnhap.this, manhinhdangky.class);
                startActivity(intent);
            }
        });
        txtEmail = findViewById(R.id.txtemail);
        txtPassword = findViewById(R.id.txtpassword);
        btnDangNhap = findViewById(R.id.btndangnhap);


        mAuth = FirebaseAuth.getInstance();


        btnDangNhap.setOnClickListener(v -> {
            String emailInput = txtEmail.getText().toString().trim();
            String passwordInput = txtPassword.getText().toString().trim();

            if (emailInput.isEmpty()) {
                txtEmail.setError("Vui lòng nhập email");
                return;
            }
            if (passwordInput.isEmpty()) {
                txtPassword.setError("Vui lòng nhập mật khẩu");
                return;
            }

            mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(manhinhdangnhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(manhinhdangnhap.this,MainActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(manhinhdangnhap.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    });
        });


    }
}