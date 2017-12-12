package com.droidrank.tictactoe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    Button block1, block2, block3, block4, block5, block6, block7, block8, block9, restart;
    TextView result;

    private boolean isGameStarted;
    private int[][] cells = new int[3][3];
    private static final int PLAYER_1 = 1;
    private static final int PLAYER_2 = 2;
    private int currentPlayer = 0;
    private boolean initialPlay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView) findViewById(R.id.tv_show_result);

        restart = (Button) findViewById(R.id.bt_restart_game);
        if(!isGameStarted){
            restart.setText(R.string.restart_button_text_initially);
        }

        block1 = (Button) findViewById(R.id.bt_block1);
        block1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMove((Button)v, 0, 0);
            }
        });

        block2 = (Button) findViewById(R.id.bt_block2);
        block2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMove((Button)v, 0, 1);
            }
        });

        block3 = (Button) findViewById(R.id.bt_block3);
        block3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMove((Button)v, 0, 2);
            }
        });

        block4 = (Button) findViewById(R.id.bt_block4);
        block4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMove((Button)v, 1, 0);
            }
        });

        block5 = (Button) findViewById(R.id.bt_block5);
        block5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMove((Button)v, 1, 1);
            }
        });

        block6 = (Button) findViewById(R.id.bt_block6);
        block6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMove((Button)v, 1, 2);
            }
        });

        block7 = (Button) findViewById(R.id.bt_block7);
        block7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMove((Button)v, 2, 0);
            }
        });

        block8 = (Button) findViewById(R.id.bt_block8);
        block8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMove((Button)v, 2, 1);
            }
        });

        block9 = (Button) findViewById(R.id.bt_block9);
        block9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMove((Button)v, 2, 2);
            }
        });

        /**
         * Restarts the game
         */
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isGameStarted){
                    startGame();
                }else{
                    restartGame();
                }

            }
        });

    }

    /**
     * Initiates a move
     * @param block
     * @param row
     * @param col
     */
    private void makeMove(Button block, int row, int col){
        switchPlayer(block);
        cells[row][col] = currentPlayer;
        validateGame(col, row, currentPlayer);
    }

    /**
     * Switch player
     */
    private void switchPlayer(Button block){
        if(block == null)
            return;

        if(initialPlay || currentPlayer == PLAYER_2){
            initialPlay = false;
            currentPlayer = PLAYER_1;
            block.setText(R.string.player_1_move);
        }else {
            currentPlayer = PLAYER_2;
            block.setText(R.string.player_2_move);
        }
    }

    /**
     * validate game after every user input
     * @param column
     * @param row
     * @param player
     */
    private void validateGame(int column, int row, int player){
        if(isAWin(column, row, player)){
            result.setText(getString(R.string.player_wins, player));
            endGame();
        }else if(isATie()){
            result.setText(R.string.draw);
            endGame();
        }else{
            //Play on
        }
    }

    /**
     * Check of game is a tie by looking out for empty cells
     * @return
     */
    private boolean isATie(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    private boolean isAWin(int column, int row, int player){
        if(cells[row][0] == player && cells[row][1] == player && cells[row][2] == player){
            return true; //Horizontal win
        }else if(cells[0][column] == player && cells[1][column] == player && cells[2][column] == player){
            return true; //Vertical win
        }else if(row == column && cells[0][0] == player && cells[1][1] == player && cells[2][2] == player){
            return true; // Left diagonal win
        }else if(row + column == 2 && cells[0][2] == player && cells[1][1] == player && cells[2][0] == player){
            return true; // Right diagonal win.
        }else{
            return false;
        }
    }


    /**
     * Begins game
     */
    private void startGame(){
        isGameStarted = true;
        result.setText("");
        restart.setText(R.string.restart_button_text_in_middle_of_game);
    }

    /**
     * restarts game
     */
    private void restartGame(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setMessage(R.string.restart_message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        endGame();
                        startGame();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialog.show();

    }

    /**
     * Ends game
     */
    private void endGame(){
        isGameStarted = false;
        initialPlay = true;
        currentPlayer = 0;
        clearMoves();
        restart.setText(R.string.restart_button_text_initially);
    }

    private void clearMoves(){
        cells = new int[3][3];

        block1.setText("");
        block2.setText("");
        block3.setText("");
        block4.setText("");
        block5.setText("");
        block6.setText("");
        block7.setText("");
        block8.setText("");
        block9.setText("");
    }
}
