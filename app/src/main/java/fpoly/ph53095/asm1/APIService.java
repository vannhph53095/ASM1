package fpoly.ph53095.asm1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface APIService {

    String DOMAIN = "https://192.168.1.9:3030/";

    @GET("/api/list")
    Call<List<CarModel>> getCars();


    @POST("/api/add_xe")
    Call<List<CarModel>> addxe(@Body CarModel xe);

    @PUT("/api/update_xe")
    Call<List<CarModel>> updateXe(@Body CarModel car);

    @DELETE("/api/xoa_xe")
     Call<List<CarModel>> deleteCar(String id);
}
