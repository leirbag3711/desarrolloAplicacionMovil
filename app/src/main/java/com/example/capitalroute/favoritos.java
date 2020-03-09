package com.example.capitalroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.widget.Toast.LENGTH_LONG;

public class favoritos extends AppCompatActivity {
    FirebaseAuth firebaseAutenticacion; //Se declara la variable para interactuar con firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        //Menu de los items
        TextView casa= findViewById(R.id.textView_casa);
        TextView trabajo= findViewById(R.id.addjobtextview);

        registerForContextMenu(casa);
        registerForContextMenu(trabajo);
        //Fin de menu de los items

        cargarDatos();



    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()) {
            case R.id.textView_casa:
                Toast.makeText(this,"casa seleccionado", LENGTH_LONG).show();
                getMenuInflater().inflate(R.menu.menu_editar_lugares,menu);
                //menu.setHeaderTitle(getString(R.string.option1));
                //menu.add(0, v.getId(), 0, "ModificarCasa");
                //menu.add(0, v.getId(), 0, "EliminarCAsa");
                break;
            case R.id.addjobtextview:
                Toast.makeText(this,"trabajo seleccionado", LENGTH_LONG).show();
                getMenuInflater().inflate(R.menu.menu_editar_lugares,menu);
                //menu.add(0, v.getId(), 0, "ModificarTrabajo");
                //menu.add(0, v.getId(), 0, "EliminarJob");
                break;
        }
        //getMenuInflater().inflate(R.menu.menu_editar_lugares,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.modificarFav:
                Toast.makeText(this,"Modificar seleccionado", LENGTH_LONG).show();
                return true;
            case R.id.eliminarFav:
                Toast.makeText(this,"Eliminar seleccionado", LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);

        }
        //Toast.makeText(this, "Selected Item: " +item.getTitle()+"ID:"+item.getItemId(), Toast.LENGTH_SHORT).show();
        //return true;
    }

    private void cargarDatos() {

        firebaseAutenticacion= FirebaseAuth.getInstance();//Se inicializa la instacia de firebase
        //Checamos si el usuario ya está loggeado
        if (firebaseAutenticacion.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
        //Fin del "chequeo"

        String emailUsuario= firebaseAutenticacion.getCurrentUser().getEmail();

        OkHttpClient client= new OkHttpClient();
        String url= "http://lugares.us-east-2.elasticbeanstalk.com/?opcion=obtenerLugar&idUsuario="+emailUsuario+"&lat=&long=&nombre=";
        System.out.println(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();
                    favoritos.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(favoritos.this,myResponse, LENGTH_LONG).show();
                            Log.i("jsonlugares",myResponse);
                        }
                    });

                }
            }
        });

    }

    public void goBackHomeFromFav(View view) {
        Intent myIntent = new Intent(favoritos.this, Home.class);
        startActivity(myIntent);
    }
}
