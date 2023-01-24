package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TwoPlayerTicToc extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton[][] buttons = new AppCompatButton[3][3]; // matrix of buttons
    TextView tvWinner,tvTurns;
    String player1, player2;
    EditText player1name,player2name;
    Button btNew,btBot;
    ImageView back;

    int turns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_tic_toc);

//        player1 = "player1";// X
//        player2 = "player2";// O
        tvWinner = findViewById(R.id.tvWinner);
        tvTurns = findViewById(R.id.tvTurns);
        player1name = findViewById(R.id.player1_name);
        player2name = findViewById(R.id.player2_name);

        player1=player1name.getText().toString();
        player2=player2name.getText().toString();

        buttons[0][0] = findViewById(R.id.bt1);
        buttons[0][1]  = findViewById(R.id.bt2);
        buttons[0][2]  = findViewById(R.id.bt3);
        buttons[1][0]  = findViewById(R.id.bt4);
        buttons[1][1]  = findViewById(R.id.bt5);
        buttons[1][2]  = findViewById(R.id.bt6);
        buttons[2][0]  = findViewById(R.id.bt7);
        buttons[2][1]  = findViewById(R.id.bt8);
        buttons[2][2]  = findViewById(R.id.bt9);
        btNew = findViewById(R.id.btNew);
        back = findViewById(R.id.backtoMainScreen);

        btNew.setOnClickListener(this);
        buttons[0][0].setOnClickListener(this);
        buttons[0][1].setOnClickListener(this);
        buttons[0][2].setOnClickListener(this);
        buttons[1][0].setOnClickListener(this);
        buttons[1][1].setOnClickListener(this);
        buttons[1][2].setOnClickListener(this);
        buttons[2][0].setOnClickListener(this);
        buttons[2][1].setOnClickListener(this);
        buttons[2][2].setOnClickListener(this);
        turns = 0;
        tvTurns.setText("Turns: " + Integer.toString(turns));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }
    private boolean winner(String str)// check if player won in his click on button
    {
        boolean check = false;
        for(int k = 0;k < 3 ;k++)//rows
        {
            check = true;
            for(int i = 0;i < 3;i++)
            {
                if(buttons[k][i].getText() != str)
                    check = false;
            }
            if(check)
                return true;
        }
        for(int i = 0;i < 3 ;i++)//cols
        {
            check = true;
            for(int k = 0;k < 3;k++)
            {
                if(buttons[k][i].getText() != str)
                    check = false;
            }
            if(check)
                return true;
        }
        check = true;
        for(int i = 0;i < 3;i++)// diangele 1
        {
            if(buttons[i][i].getText() != str)
                check = false;
        }
        if(check)
            return true;
        check = true;
        for(int i = 0;i < 3;i++)// diangele 2
        {
            if(buttons[i][2-i].getText() != str)
                check = false;
        }
        if(check)
            return true;
        return check;
    }

    @Override
    public void onClick(View view) {
        turns++;
        tvTurns.setText("Turns: " + Integer.toString(turns));
        if(turns == 9)
        {
            endGame("The Game is Tie");
        }

        else if(view == btNew) // a new game button was pressed
        {
            newGame();
        }
        else {
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 3; k++) {
                    if (view == buttons[i][k]) {

                        if (turns % 2 == 0) {
                            buttons[i][k].setText("O");
                            buttons[i][k].setEnabled(false);

                            buttons[i][k].setBackgroundResource(R.drawable.playeronebox);

                            if(winner("O"))
                                endGame("congratulation " + player2name.getText().toString() + " Won");

                        }

                        else {

                            buttons[i][k].setText("X");
                            buttons[i][k].setEnabled(false);

                            buttons[i][k].setBackgroundResource(R.drawable.playertwobox);

                            if(winner("X"))
                                endGame("congratulation " + player1name.getText().toString() + " Won");
                        }
                    }
                }
            }

        }
    }
    public void endGame(String result)//when game is done alert pops up with option to start a new game or back to home screen
    {
        String text = "";
        if(result == "Tie")
        {
            text = "the game is tied";
        }
        else
        {
            text = result + " has won";
        }
        AlertDialog.Builder alert  = new AlertDialog.Builder(TwoPlayerTicToc.this);
        alert.setTitle(text);
        alert.setCancelable(false);
        alert.setMessage("choose new game or back to home screen");
        alert.setPositiveButton("new game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                newGame();
            }
        });

        alert.create().show();
    }
    @SuppressLint("ResourceAsColor")
    public void newGame()// delete all moves and turns become zero
    {
        for (int i = 0;i < 3; i++)
        {
            for (int k = 0; k < 3;k++)
            {
                buttons[i][k].setText("");
                buttons[i][k].setEnabled(true);

                buttons[i][k].setBackgroundResource(R.drawable.grey_bg);

            }
        }
        turns = 0;
        turn();
    }
    public void turn()// display number of turns
    {
        tvTurns.setText("Turns: " + Integer.toString(turns));
        tvWinner.setText("");


    }
}