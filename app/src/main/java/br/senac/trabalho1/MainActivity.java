package br.senac.trabalho1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carregarPontos();
        final Button button = (Button) findViewById(R.id.buttonInformar);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.layoutPontos);
                for (int i = 0; i < layout.getChildCount(); i++) {
                    EditText editText = (EditText) layout.getChildAt(i);
                    if (editText.getText().toString().isEmpty()) {
                        editText.setHintTextColor(Color.rgb(255,0,0));
                    } else {
                        editText.setTextColor(Color.rgb(0,255,0));
                        editText.setEnabled(false);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarPontos();
    }

    private void carregarPontos() {
        System.out.println("Limpando as views que estão no layout...");
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutPontos);
        layout.removeAllViews();

        System.out.println("Carregando horas...");
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        TextView horasView = (TextView) findViewById(R.id.textHoras);
        horasView.setText(sdf.format(calendar.getTime()));

        System.out.println("Carregando horários...");
        int hora = calendar.get(Calendar.HOUR_OF_DAY);

        int qtdPontos = calculaQuantidadePontos(hora);

        for (int i=0; i< qtdPontos; i++) {
            EditText editText = new EditText(this);
            editText.setHint(geraStringHoras(i));
            editText.setSingleLine(true);
            layout.addView(editText);
        }
    }

    private String geraStringHoras(int valorHora) {
        valorHora += 8;
        if (valorHora > 11) {
            valorHora++;
        }
        String horaInicial = String.valueOf(valorHora);
        String horaFinal = String.valueOf(valorHora + 1);
        return String.format("Período %s:00 - %s:00", horaInicial, horaFinal);
    }

    private int calculaQuantidadePontos(int hora) {
        if (hora > 17)
            hora = 17;
        return hora > 12? hora-9: hora-8;
    }

}