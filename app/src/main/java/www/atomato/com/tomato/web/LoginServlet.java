package www.atomato.com.tomato.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import www.atomato.com.tomato.data.User;
import www.atomato.com.tomato.utils.LogUtils;

/**
 * Created by Administrator on 2017/11/15.
 */

public class LoginServlet {
    String TAG = getClass().getSimpleName();

    public interface IUserBiz {
        @FormUrlEncoded
        @POST("LoginServlet")
        Call<ResponseBody> addUser(@FieldMap Map<String, String> maps);
    }

    public void regiterUser(User user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.2:8080/Tomato/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IUserBiz userBiz = retrofit.create(IUserBiz.class);
        Call<ResponseBody> call = userBiz.addUser(user.getMaps());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LogUtils.e(TAG, "onResponse");
                LogUtils.e(TAG,  response.code()+"");
                try {
                    LogUtils.e(TAG,  response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}