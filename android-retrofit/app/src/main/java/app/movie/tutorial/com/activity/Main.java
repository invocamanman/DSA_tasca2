package app.movie.tutorial.com.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.movie.tutorial.com.R;

/**
 * Created by JesusLigero on 17/12/2017.
 */

public class Main extends AppCompatActivity {

    EditText Buscar;
    Button botton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Buscar = (EditText) findViewById(R.id.Buscar);


        botton = (Button) findViewById(R.id.Boto);
        botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Buscar.getText().toString()))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Campo incompleto", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Intent intent = new Intent(Main.this,ListFilms.class);
                intent.putExtra("buscar", Buscar.getText().toString());
                startActivity(intent);
            }}
        );


    }




}
