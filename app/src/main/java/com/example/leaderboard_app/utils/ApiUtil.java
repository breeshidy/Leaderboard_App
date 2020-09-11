package com.example.leaderboard_app.utils;

//contains static methods and will never instantiated

import android.net.Uri;
import android.util.Log;

import com.example.leaderboard_app.modals.LearningLearders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtil {

    public static final String BASE_API_URL = "https://gadsapi.herokuapp.com";
    private static final String QUERY_PARAMETER_KEY ="api";
    public static Retrofit sRetrofit = null;

    //the constructor is removed
    private ApiUtil(){}

    // Function to create URL
    public static URL buildUrl(String title){

        //to catch any exceptions
        URL url = null;

        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendPath(QUERY_PARAMETER_KEY)
                .appendPath(title)
                .build();

        try{
            //convert uri into url
            url = new URL(uri.toString()); //sidenote put a break point to check if url is correct
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    //connect to API
    public static final String getJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            //to read data
            InputStream stream = connection.getInputStream();

            //to convert from stream to String
            Scanner scanner = new Scanner(stream);

            //read everything in the page \\A talks on regular express
            scanner.useDelimiter("\\A");
            //checks if there is data
            boolean hasData = scanner.hasNext();
            if (hasData){
                return scanner.next();
            }
            else {
                return null;
            }
        }catch (Exception e){
            Log.d("Error", e.toString());
            return null;
        }finally {
            //always close the connection
            connection.disconnect();
        }
    }

    //get a list of the top learners
//    public static ArrayList<> getLearnersFromJson(String json) {
//        final String NAME = "name";
//        final String HOURS = "hours";
//        final String COUNTRY = "country";
//        String score = "score";
//
//        ArrayList<LearningLearders> leaningLeaders = new ArrayList<>();
//        try {
//            //array doesnt have a name
//            JSONArray jsonLeardersArray = new JSONArray(json);
//            int numberOfLearners = jsonLeardersArray.getL
//            for (int i = 0; i < jsonLeardersArray.length(); i ++){
//                JSONObject jsonLearders = jsonLeardersArray.getJSONObject(i);
//                LearningLearders leaders = new LearningLearders(
//                        jsonLearders.getString(NAME),
//                        jsonLearders.getString(HOURS),
//                        jsonLearders.getString(COUNTRY));
//
//                leaningLeaders.add(leaders);
//            }
//            } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return leaningLeaders;
//    }

    public static ArrayList getLearnersFromJson(String json){
        String name = "name";
        String hours = "hours";
        String score = "score";
        String country = "country";
        String imageUrl = "badgeUrl";

        ArrayList<LearningLearders> learners = new ArrayList<LearningLearders>();

        try {

            JSONArray jsonArray = new JSONArray(json);
            int numberOfLearners = jsonArray.length();

            for(int i = 0; i<numberOfLearners; i++){

                JSONObject leanersObject = jsonArray.getJSONObject(i);
                LearningLearders learners1 = new LearningLearders();

                learners1.setName(leanersObject.getString(name));
                if(leanersObject.isNull(hours)){
                    learners1.setScore(leanersObject.getString(score));
                }else {
                    learners1.setHours(leanersObject.getString(hours));
                }
                learners1.setCountry(leanersObject.getString(country));
//                learners1.setImageUrl(leanersObject.getString(imageUrl));

                learners.add(learners1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return learners;
    }

    public static Retrofit getClient(String baseUrl){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor);

        if(sRetrofit == null){
            sRetrofit =  new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientBuilder.build())
                    .build();
        }
        return sRetrofit;
    }
}
