package app.movie.tutorial.com.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.adapter.MoviesAdapter;
import app.movie.tutorial.com.model.Movie;
import app.movie.tutorial.com.model.MovieResponse;
import app.movie.tutorial.com.rest.MovieApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ListFilms extends AppCompatActivity{

    private static final String TAG = ListFilms.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    private ProgressDialog progressDialog;

    String Buscar;
    // insert your themoviedb.org API KEY here
    private final static String API_KEY = "95e41cb4dee5feb6f7b3b1593e823d56";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle intentdata = getIntent().getExtras();
        Buscar = intentdata.getString("buscar");


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        connectAndGetApiData();

    }

    // This method create an instance of Retrofit
    // set the base url
    public void connectAndGetApiData(){

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando Sesión");
        progressDialog.show();
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Call<MovieResponse> call = movieApiService.busqueda(API_KEY, Buscar);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
                Log.d(TAG, "Number of movies received: " + movies.size());
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
                progressDialog.dismiss();
            }
        });
    }
}
