package com.example.tictactoe;

import static com.example.tictactoe.R.color.green;
import static com.example.tictactoe.R.color.material_blue_grey_800;
import static com.example.tictactoe.R.color.material_blue_grey_900;
import static com.example.tictactoe.R.color.purple_500;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private AppCompatButton[][] buttons = new AppCompatButton[3][3]; // matrix buttons of the game
    Button btNew,btBot;
    TextView tvTurns;
    String player1, player2;
    EditText player1name,player2name;
    int turns;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_toc_toe_design_);

        player1 = "player1";// X
        player2 = "player2";// O
        btBot = findViewById(R.id.btBot);// remember to delete later!!!
        tvTurns = findViewById(R.id.tvTurns);
        player1name = findViewById(R.id.player1_name);
        player2name = findViewById(R.id.player2_name);
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
        btBot.setOnClickListener(this);//remember to delete later!!!
        turns = 0;
        turn();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }
    public void turn()// display number of turns
    {
        tvTurns.setText("Turns: " + Integer.toString(turns));
    }
    private boolean winner(String str)// check if player won in his last click on matrix button
    {
        boolean check = false;
        for(int k = 0;k < 3 ;k++)//rows check
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
        for(int i = 0;i < 3 ;i++)//cols check
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
        for(int i = 0;i < 3;i++)// diangele 1 check
        {
            if(buttons[i][i].getText() != str)
                check = false;
        }
        if(check)
            return true;
        check = true;
        for(int i = 0;i < 3;i++)// diangele 2 check
        {
            if(buttons[i][2-i].getText() != str)
                check = false;
        }
        if(check)
            return true;
        return check;
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
        AlertDialog.Builder alert  = new AlertDialog.Builder(MainActivity.this);
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

    @Override
    public void onClick(View view) { // when a player clicks on a button of the game
        if(view == btBot)// remember to delete or change this later!!!
        {
            player2 = "Bot";
            player2name.setText("Bot");
        }
        else if(view == btNew) // a new game button was pressed
        {
            newGame();
        }
        else { // if a button in the matrix was pressed
            turns++;
            turn();
            if (turns == 9) {
                endGame("Tie");
            } else {
                for (int i = 0; i < 3; i++) {
                    for (int k = 0; k < 3; k++) {
                        if (view == buttons[i][k]) {
                            if (turns % 2 == 0) {
                                buttons[i][k].setText("O");
                                buttons[i][k].setEnabled(false);
                                buttons[i][k].setBackgroundResource(R.drawable.playeronebox);
                                if (winner("O"))
                                    endGame(player2name.getText().toString());
                            } else {
                                buttons[i][k].setText("X");
                                buttons[i][k].setEnabled(false);

                                buttons[i][k].setBackgroundResource(R.drawable.playertwobox);

                                if (winner("X"))
                                    endGame(player1name.getText().toString());
                                if(player2 == "Bot")// bot makes a turn
                                {
                                    Bot();
                                }
                            }
                        }
                    }
                }

            }
        }
    }
    public void Bot()
    {
        Random rnd = new Random();
        boolean done = false;
        while(!done)
        {
            int i = rnd.nextInt(3);
            int k = rnd.nextInt(3);
                    if(buttons[i][k].getText() == "")
                    {
                        buttons[i][k].setText("O");
                        buttons[i][k].setEnabled(false);

                        buttons[i][k].setBackgroundResource(R.drawable.playeronebox);
                        done = true;
                    }
        }
        if(winner("O"))
        {
            endGame("Bot");
        }
        turns++;
        turn();
    }

}