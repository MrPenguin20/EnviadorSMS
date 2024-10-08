package mx.edu.itl.c85360673.u4smsenvioapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import teclag.c20130792.androlib.util.permisos.PermisoApp;
import teclag.c20130792.androlib.util.permisos.ChecadorDePermisos;

public class MainActivity extends AppCompatActivity {
    private EditText      edtTelefonoDestino;
    private EditText      edtMensaje;

    private int[] Results;

    private PermisoApp [] permisoReq = new PermisoApp [] {
      new PermisoApp ( Manifest.permission.SEND_SMS, "SMS", true ),
      new PermisoApp ( Manifest.permission.CALL_PHONE, "TELEFONO", true ),
      new PermisoApp ( Manifest.permission.READ_EXTERNAL_STORAGE, "ALMACENAMIENTO", false ),
      new PermisoApp ( Manifest.permission.WRITE_EXTERNAL_STORAGE, "Escribir", false ),
      new PermisoApp ( Manifest.permission.READ_MEDIA_IMAGES, "Imagen", true )
    };

    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTelefonoDestino = findViewById(R.id.edtTelefonoDestino);
        edtMensaje = findViewById(R.id.edtMensaje);

        ChecadorDePermisos.checarPermisos ( this, permisoReq );
    }

    //----------------------------------------------------------------------------------------------


    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ChecadorDePermisos.CODIGO_PEDIR_PERMISOS) {
            ChecadorDePermisos.verificarPermisosSolicitados(this, permisoReq, permissions,
                    grantResults);
            Results = grantResults;
        }

    }

    public void btnVerPermisosClick (View v ) {

    }

    //----------------------------------------------------------------------------------------------

    public void btnEnviarClick (View v ) {
        if ( validarDatos () == false )
            return;

        SmsManager smsMgr = SmsManager.getDefault();
        smsMgr.sendTextMessage (
                edtTelefonoDestino.getText().toString(),
                null,
                edtMensaje.getText().toString(),
                null,
                null
        );

        Toast.makeText (this, "SMS enviado",
                         Toast.LENGTH_SHORT ).show();
        edtMensaje.setText ( "" );
    }

    //----------------------------------------------------------------------------------------------

    private boolean validarDatos () {
        if ( edtTelefonoDestino.getText().toString().isEmpty() ) {
            edtTelefonoDestino.setError ( "Telefono no puede ser vacio" );
            return false;
        }

        if ( edtMensaje.getText().toString().isEmpty() ) {
            edtMensaje.setError ( "Proporcione un mensaje a enviar" );
            return false;
        }
        return true;
    }

    //----------------------------------------------------------------------------------------------

    public void btnAcercadeClick ( View v ) {
        Intent intent  = new Intent ( this, AcercaDe.class );
        startActivity ( intent );
        finish();
    }

}
