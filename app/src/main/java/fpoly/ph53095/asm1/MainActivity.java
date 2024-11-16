package fpoly.ph53095.asm1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView lvMain;
    List<CarModel> listCarModel;

    CarAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvMain = findViewById(R.id.listviewMain);

        // Tạo đối tượng Retrofit để gọi API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        // Gọi API để lấy dữ liệu xe
        Call<List<CarModel>> call = apiService.getCars();

        call.enqueue(new Callback<List<CarModel>>() {
            @Override
            public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                if (response.isSuccessful()) {
                    listCarModel = response.body();

                    // Log dữ liệu phản hồi để kiểm tra
                    Log.d("Main", "Dữ liệu nhận được: " + response.body().toString());

                    // Kiểm tra xem danh sách có dữ liệu không
                    if (listCarModel != null && !listCarModel.isEmpty()) {
                        carAdapter = new CarAdapter(getApplicationContext(), listCarModel);
                        lvMain.setAdapter(carAdapter);
                    } else {
                        Log.e("Main", "Danh sách nhận được rỗng.");
                    }
                } else {
                    Log.e("Main", "Lỗi: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CarModel>> call, Throwable t) {
                // Log lý do thất bại
                Log.e("Main", "Yêu cầu thất bại: " + t.getMessage());
            }
        });

        // Xử lý khi người dùng click vào nút "Thêm xe"
        findViewById(R.id.btn_add).setOnClickListener(v -> {
            CarModel xe = new CarModel("Xe 1411", 2023, "Toyota", 1200);

            Call<List<CarModel>> callAddXe = apiService.addxe(xe);

            callAddXe.enqueue(new Callback<List<CarModel>>() {
                @Override
                public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                    if (response.isSuccessful()) {

                        listCarModel.clear();

                        listCarModel.addAll(response.body());

                        carAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<CarModel>> call, Throwable t) {
                    Log.e("Main", t.getMessage());
                }
            });
        });
    }
}
