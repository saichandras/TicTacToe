package com.example.a2tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private EditText Player1Name;
    private EditText Player2Name;
    private Button Start;

    //Constants
    Constants con = new Constants ();

    //Players Init
    CharSequence player1 = con.SetPlayer1_Text;
    CharSequence player2 = con.SetPlayer2_Text;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main );

        Player1Name = (EditText)findViewById (R.id.player1name);
        Player2Name = (EditText)findViewById (R.id.player2name);
        Start = (Button) findViewById ( R.id.startgame );

        player1 = con.SetPlayer1_Text;
        player2 = con.SetPlayer2_Text;


        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        Player1Name.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player1 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

        Player2Name.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player2 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

        Start.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                GameActivity();
            }
        } );

    }


    public void GameActivity(){

        if(CheckWhitespaces ( player1.toString () )){
            player1 = con.SetPlayer1_Text;
        }

        if(CheckWhitespaces ( player2.toString () )){
            player2 = con.SetPlayer2_Text;
        }

        CharSequence[] players = {player1, player2};
        Intent i = new Intent ( this, GameStart.class );
        i.putExtra("PlayerNames", players);
        startActivity (i);
    }


     boolean CheckWhitespaces(String str){


        if(Pattern.matches("^(?! +$)[A-Za-zăâîșțĂÂÎȘȚ -]+$", str))
            return false;

        else
            return true;

    }


    @Override
    public void onBackPressed() {

        ExitMainActivityDialog();

    }

    public void ExitMainActivityDialog(){

        final Dialog Exit_Dialog = new Dialog(MainActivity.this);
        Exit_Dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        Exit_Dialog.setContentView(R.layout.back_press);

        Exit_Dialog.setCanceledOnTouchOutside(true);
        Exit_Dialog.setCancelable ( true );

        final Button Cancel = (Button) Exit_Dialog.findViewById(R.id.bt_Cancel);
        final Button Exit = (Button) Exit_Dialog.findViewById(R.id.bt_Exit );

        Cancel.setText ( con.Cancel_Text );
        Exit.setText ( con.Exit_Text );


        Cancel.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Exit_Dialog.dismiss ();

            }
        } );

        Exit.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        } );

        Exit_Dialog.show ();

    }



 }
