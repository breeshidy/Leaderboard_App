package com.example.leaderboard_app;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Client {
    @POST("1FAIpQLSc5xu--EnVmLXZSpN8zSpZE0t871Nf28QCNkDvSFlQPqJ76SQ/formResponse")
    @FormUrlEncoded
    Call<ResponseBody> submitProject(
            @Field("entry.467158341") String name,
            @Field("entry.226577513") String last_name,
            @Field("entry.1498500323") String email,
            @Field("entry.405655578") String github_link
    );

}
