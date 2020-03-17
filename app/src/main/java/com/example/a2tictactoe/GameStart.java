package com.example.a2tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class GameStart extends AppCompatActivity {

    private TextView playerTurn;
    private Dialog WinnerDialog;
    private TextView Player1Name;
    private TextView Player2Name;
    private TextView Player1Score;
    private  TextView Player2Score;
    



    /*
        0 - null
        1 - X
        2 - O
    */


    //Initialization
    private int playerActive = 1;
    static int [][] board = new int[3][3];
    static int boardSize = board[0].length;
    private int column,row;

    //Constants
    Constants con = new Constants ();


    //Get Player Name Variables
    CharSequence[] players;

    //Player 1:-
    private  int [] row_1 = new int[3];
    private int[] column_1 = new int[3];
    private int diag_1;
    private int antiDiag_1;
    private int score_1;

    //Player 2:-
    private int[] row_2 = new int[3];
    private int[] column_2 = new int[3];
    private int diag_2;
    private int antiDiag_2;
    private int score_2;

    private int countertap = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        players = getIntent().getCharSequenceArrayExtra("PlayerNames");
        playerTurn = (TextView)findViewById(R.id.tv_playterturn);
        WinnerDialog = new Dialog(this);
        Player1Name = (TextView)findViewById ( R.id.tv_p1name );
        Player2Name = (TextView)findViewById ( R.id.tv_p2name );
        Player1Score = (TextView)findViewById ( R.id.tv_p1score );
        Player2Score = (TextView)findViewById ( R.id.tv_p2score );

        playerTurn.setText ( players[0] +con.Add_STrun);
        Player1Name.setText ( players[0].toString () + con.SemiColon_Text );
        Player2Name.setText ( players[1].toString () + con.SemiColon_Text );



    }

    public static void BoardInit(){

        board = new int[3][3];

    }





    public void PlayerTap(View view){
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());
        row = tappedImage / 10;
        column = tappedImage % 10;



        if((board[row][column] == 1) || (board[row][column] == 2)){

            Toast Toast_RightSel = Toast.makeText(getApplicationContext(), con.Choose_Empty_Slot_Text, Toast.LENGTH_SHORT );
            Toast_RightSel.show();

        }


        if(board[row][column] == 0){
            board[row][column] = playerActive;
            img.setTranslationY(-1000f);

            if(playerActive == 1 ){
                img.setImageResource(R.drawable.x);
                countertap++;

                if(TapANDChkWinnerP1()){
                    img.animate().translationYBy(1000f).setDuration(250);
                    score_1++;
                    playerTurn.setText(con.Game_Over);
                    Player1Name.setText ( players[0].toString () + con.SemiColon_Text);
                    Player1Score.setText ( Integer.toString ( score_1 ) );
                    showDialog(players[0].toString () + con.Won_Text);
                    return;
                }
                playerActive = 2;
                playerTurn.setText(players[1].toString ()+con.Add_STrun);

            }

            else if(playerActive == 2){
                img.setImageResource(R.drawable.o);
                countertap++;

                if (TapANDChkWinnerP2()){
                    img.animate().translationYBy(1000f).setDuration(250);
                    score_2++;
                    playerTurn.setText(con.Game_Over);
                    Player2Name.setText ( players[1].toString () + con.SemiColon_Text);
                    Player2Score.setText ( Integer.toString ( score_2 ) );
                    showDialog(players[1].toString () +con.Won_Text);
                    return;
                }
                playerActive = 1;
                playerTurn.setText(players[0].toString () + con.Add_STrun);

            }

            img.animate().translationYBy(1000f).setDuration(250);

        }

        if(BoardFull()){
            playerTurn.setText(con.Game_Over);
            showDialog(con.GameDraw_Text);
        }




    }


    boolean TapANDChkWinnerP1(){

        row_1[row]++;
        column_1[column]++;
        if(row==column)
            diag_1++;
        if(row+column==boardSize -1)
            antiDiag_1++;

        if(row_1[row]==boardSize || column_1[column]==boardSize || diag_1==boardSize || antiDiag_1==boardSize)
            return true;


        else
            return  false;

    }

    boolean TapANDChkWinnerP2(){

        row_2[row]++;
        column_2[column]++;
        if(row==column)
            diag_2++;
        if(row+column==boardSize - 1)
            antiDiag_2++;

        if(row_2[row]==boardSize || column_2[column]==boardSize || diag_2==boardSize || antiDiag_2==boardSize)
            return true;


        else
            return  false;

    }

    boolean BoardFull(){
        if(countertap == boardSize * boardSize)
            return true;


        else
            return false;

    }

    public void showDialog(String whoWon){

        WinnerDialog.setContentView(R.layout.make_dialog);
        final Button Reset = (Button) WinnerDialog.findViewById(R.id.reset);
        final Button PlayAgain = (Button) WinnerDialog.findViewById(R.id.playagain );
        PlayAgain.setText ( con.PlayAgain_Text);
        Reset.setText ( con.NewGame_Text );
        TextView DisplayWinner = (TextView) WinnerDialog.findViewById(R.id.Display_Winner);
        WinnerDialog.setCanceledOnTouchOutside(false);
        WinnerDialog.setCancelable ( false );
        DisplayWinner.setText(whoWon);


        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WinnerDialog.dismiss();
                Reset();
            }
        });

        PlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WinnerDialog.dismiss();
                PlayAgain();

            }
        });

        //WinnerDialog.getWindow ().setBackgroundDrawableResource ( android.R.color.transparent );
        WinnerDialog.show();

    }


    public void Reset(){

        //Reset Board
        BoardInit ();

        //Player 1 - Reset
        playerActive = 1;
        row_1 = new int[3];
        column_1 = new int[3];
        diag_1 = 0;
        antiDiag_1 = 0;
        score_1 = 0;
        Player1Name.setText ( con.SetPlayer1_Text + con.SemiColon_Text );

        //Player 2 - Reset
        row_2 = new int[3];
        column_2 = new int[3];
        diag_2 = 0;
        antiDiag_2 = 0;
        score_2 = 0;
        Player2Name.setText ( con.SetPlayer2_Text + con.SemiColon_Text );

        countertap = 0;
        playerTurn.setText (con.SetPlayer1_Turn);

        //Set Boxes To Empty
        ((ImageView)findViewById(R.id.img_1)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_2)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_3)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_4)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_5)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_6)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_7)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_8)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_9)).setImageResource(0);

        WinnerDialog.dismiss();

        Intent intent_ResetBack = new Intent ( this, MainActivity.class );
        startActivity ( intent_ResetBack );
        finishAffinity();

    }


    public void PlayAgain(){

        //Reset Board
        BoardInit ();

        //Player 1 - Reset
        playerActive = 1;
        row_1 = new int[3];
        column_1 = new int[3];
        diag_1 = 0;
        antiDiag_1 = 0;

        //Player 2 - Reset
        row_2 = new int[3];
        column_2 = new int[3];
        diag_2 = 0;
        antiDiag_2 = 0;

        countertap = 0;
        playerTurn.setText (players[0].toString () + con.Add_STrun);

        //Set Boxes To Empty
        ((ImageView)findViewById(R.id.img_1)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_2)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_3)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_4)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_5)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_6)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_7)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_8)).setImageResource(0);
        ((ImageView)findViewById(R.id.img_9)).setImageResource(0);

        WinnerDialog.dismiss();


    }


    @Override
    public void onBackPressed() {
        ExitDialog ();
    }


    public void ExitDialog(){

        final Dialog Exit_MainDialog = new Dialog(this);
        Exit_MainDialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        Exit_MainDialog.setContentView(R.layout.goto_mainactivity);

        Exit_MainDialog.setCanceledOnTouchOutside(true);
        Exit_MainDialog.setCancelable ( true );

        final Button No = (Button) Exit_MainDialog.findViewById(R.id.bt_no);
        final Button Yes = (Button) Exit_MainDialog.findViewById(R.id.bt_yes );

        No.setText ( con.No_Text );
        Yes.setText ( con.Yes_Text);


        No.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Exit_MainDialog.dismiss ();

            }
        } );

        Yes.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Reset ();

            }
        } );

        Exit_MainDialog.show ();

    }


}


