package com.myapps.sorry;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int TWO_DRAW_AGAIN = 2;
    String previousHighlightName = "";

    private static final int TURN_ONE = 1;
    ImageButton s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,
            s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,
            s21, s22, s23, s24, s25, s26, s27, s28, s29, s30,
            s31, s32, s33, s34, s35, s36, s37, s38, s39, s40,
            s41, s42, s43, s44, s45, s46, s47, s48,s49, s50,
            s51, s52, s53, s54, s55, s56, s57, s58, s59;


    ImageButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12,
            y0, y1, y2, y3, y4, y5, y6, y7, y8, y9, y10, y11, y12,
            g0, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12,
            r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12;

    BoardModel boardModel;

    ImageView currentPlayerGraphic;

    TextView draw, card, card1, sevenMoveCounter;

    TextSwitcher textSwitcher;
    final int NOT_TURN_ONE = 5;

    int blueFinishNum = 0;
    int yellowFinishNum = 0;
    int greenFinishNum = 0;
    int redFinishNum = 0;
    int moveCounterSeven = 0;

    Button undoMove;

    boolean swap = false;
    View previousView;
    final int DISPLACE_TO_GOAL = 3;

    char topPiece = 'x';

    char topPiece1 = 'x';


    char flattenedPiece = 'x';

    char flattenedPiece1 = 'x';

    int flattenedPieceIndex = 0;

    int flattenedPieceIndex1 = 0;

    boolean scoredThisTurn = false;











    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardModel = BoardModel.getInstance();

        currentPlayerGraphic = findViewById(R.id.current_turn_color_image_view);


        sevenMoveCounter = findViewById(R.id.seven_move_counter_text_view);
        sevenMoveCounter.setAlpha(0);


        //button and text are shared for 7 and 11
        undoMove = findViewById(R.id.undo_move_seven_button);
        undoMove.setAlpha(0);
        undoMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boardModel.getActionPhase()) {
                    //11 switch button
                    if (boardModel.getCurrentCard().equals("11")) {
                        if (previousView != null) {
                            swap = !swap;
                            if (swap) {
                                sevenMoveCounter.setText(R.string.swap);
                                sevenMoveCounter.setGravity(Gravity.CENTER_HORIZONTAL);
                            }
                            else {
                                sevenMoveCounter.setText(R.string.move);
                                sevenMoveCounter.setGravity(Gravity.CENTER_HORIZONTAL);
                            }


                            unhighlight();
                            highlight(previousView);
                            updateUI();
                        }
                    }

                    if (boardModel.getCurrentCard().equals("7")) {

                        //reset values
                        flattenedPiece = 'x';
                        flattenedPiece1 = 'x';
                        flattenedPieceIndex = 0;
                        flattenedPieceIndex1 = 0;

                        topPiece = 'x';
                        topPiece1 = 'x';

                        //reset seven counter
                        moveCounterSeven = 7;
                        sevenMoveCounter.setText(Integer.toString(moveCounterSeven));
                        sevenMoveCounter.setGravity(Gravity.CENTER_HORIZONTAL);

                        if (scoredThisTurn) {
                            revertFinishNum(boardModel.getPlayerTurn());
                            scoredThisTurn = false;
                        }

                        //set board from backup, thereby undoing moves
                        boardModel.setBoardFromBackup();
                        updateUI();
                    }
                }
            }
        });












        //setup card draw
        draw = findViewById(R.id.current_card_text_view);
        card = findViewById(R.id.card_text_view);
        card1 = findViewById(R.id.card_text_view1);
        textSwitcher = findViewById(R.id.textSwitcher);
        //animatiomn for card draw
        textSwitcher.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        textSwitcher.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);
        //draw on click listener
        draw.setOnClickListener(new View.OnClickListener() {
            //click listener for draw card
            @Override
            public void onClick(View v) {

                //if in draw phase, draw card on click and print to screen
                if (boardModel.getDrawPhase()) {
                    //variable for dealing with 11 card
                    previousView = null;
                    //draw card
                    boardModel.drawCard();

                    textSwitcher.setText(boardModel.getCurrentCard());
                    card.setGravity(Gravity.CENTER);
                    card1.setGravity(Gravity.CENTER);
                    //set counter if 7 is drawn
                    if (boardModel.getCurrentCard().equals("7")) {
                        sevenMoveCounter.setAlpha(1);
                        undoMove.setAlpha(1);
                        undoMove.setText(R.string.undo_move);
                        moveCounterSeven = 7;
                        sevenMoveCounter.setText(Integer.toString(moveCounterSeven));
                        sevenMoveCounter.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    //if draw 11
                    if (boardModel.getCurrentCard().equals("11")) {
                        sevenMoveCounter.setAlpha(1);
                        sevenMoveCounter.setText(R.string.move);
                        sevenMoveCounter.setGravity(Gravity.CENTER_HORIZONTAL);
                        undoMove.setAlpha(1);
                        swap = false;
                        undoMove.setText(R.string.move_type);
                        undoMove.setGravity(Gravity.CENTER_HORIZONTAL);

                    }
                    //switch to action phase
                    boardModel.setDrawPhase(false);
                    boardModel.setActionPhase(true);
                    draw.setGravity(Gravity.CENTER_HORIZONTAL);
                }
                //end turn if button is clicked during action phase
                else {
                    unhighlight();
                    updateUI();
                    endTurn();
                }
            }
        });
        //need this for first setup
        draw.setClickable(false);


        //setup outer spots
        s0 = findViewById(R.id.s0);
        s0.setClickable(true);
        s0.setOnClickListener(this);

        s1 = findViewById(R.id.s1);
        s1.setClickable(true);
        s1.setOnClickListener(this);

        s2 = findViewById(R.id.s2);
        s2.setClickable(true);
        s2.setOnClickListener(this);

        s3 = findViewById(R.id.s3);
        s3.setClickable(true);
        s3.setOnClickListener(this);

        s4 = findViewById(R.id.s4);
        s4.setClickable(true);
        s4.setOnClickListener(this);

        s5 = findViewById(R.id.s5);
        s5.setClickable(true);
        s5.setOnClickListener(this);

        s6 = findViewById(R.id.s6);
        s6.setClickable(true);
        s6.setOnClickListener(this);

        s7 = findViewById(R.id.s7);
        s7.setClickable(true);
        s7.setOnClickListener(this);

        s8 = findViewById(R.id.s8);
        s8.setClickable(true);
        s8.setOnClickListener(this);

        s9 = findViewById(R.id.s9);
        s9.setClickable(true);
        s9.setOnClickListener(this);

        s10 = findViewById(R.id.s10);
        s10.setClickable(true);
        s10.setOnClickListener(this);

        s11 = findViewById(R.id.s11);
        s11.setClickable(true);
        s11.setOnClickListener(this);

        s12 = findViewById(R.id.s12);
        s12.setClickable(true);
        s12.setOnClickListener(this);

        s13 = findViewById(R.id.s13);
        s13.setClickable(true);
        s13.setOnClickListener(this);

        s14 = findViewById(R.id.s14);
        s14.setClickable(true);
        s14.setOnClickListener(this);

        s15 = findViewById(R.id.s15);
        s15.setClickable(true);
        s15.setOnClickListener(this);

        s16 = findViewById(R.id.s16);
        s16.setClickable(true);
        s16.setOnClickListener(this);

        s17 = findViewById(R.id.s17);
        s17.setClickable(true);
        s17.setOnClickListener(this);

        s18 = findViewById(R.id.s18);
        s18.setClickable(true);
        s18.setOnClickListener(this);

        s19 = findViewById(R.id.s19);
        s19.setClickable(true);
        s19.setOnClickListener(this);

        s20 = findViewById(R.id.s20);
        s20.setClickable(true);
        s20.setOnClickListener(this);

        s21 = findViewById(R.id.s21);
        s21.setClickable(true);
        s21.setOnClickListener(this);

        s22 = findViewById(R.id.s22);
        s22.setClickable(true);
        s22.setOnClickListener(this);

        s23 = findViewById(R.id.s23);
        s23.setClickable(true);
        s23.setOnClickListener(this);

        s24 = findViewById(R.id.s24);
        s24.setClickable(true);
        s24.setOnClickListener(this);

        s25 = findViewById(R.id.s25);
        s25.setClickable(true);
        s25.setOnClickListener(this);

        s26 = findViewById(R.id.s26);
        s26.setClickable(true);
        s26.setOnClickListener(this);

        s27 = findViewById(R.id.s27);
        s27.setClickable(true);
        s27.setOnClickListener(this);

        s28 = findViewById(R.id.s28);
        s28.setClickable(true);
        s28.setOnClickListener(this);

        s29 = findViewById(R.id.s29);
        s29.setClickable(true);
        s29.setOnClickListener(this);

        s30 = findViewById(R.id.s30);
        s30.setClickable(true);
        s30.setOnClickListener(this);

        s31 = findViewById(R.id.s31);
        s31.setClickable(true);
        s31.setOnClickListener(this);

        s32 = findViewById(R.id.s32);
        s32.setClickable(true);
        s32.setOnClickListener(this);

        s33 = findViewById(R.id.s33);
        s33.setClickable(true);
        s33.setOnClickListener(this);

        s34 = findViewById(R.id.s34);
        s34.setClickable(true);
        s34.setOnClickListener(this);

        s35 = findViewById(R.id.s35);
        s35.setClickable(true);
        s35.setOnClickListener(this);

        s36 = findViewById(R.id.s36);
        s36.setClickable(true);
        s36.setOnClickListener(this);

        s37 = findViewById(R.id.s37);
        s37.setClickable(true);
        s37.setOnClickListener(this);

        s38 = findViewById(R.id.s38);
        s38.setClickable(true);
        s38.setOnClickListener(this);

        s39 = findViewById(R.id.s39);
        s39.setClickable(true);
        s39.setOnClickListener(this);

        s40 = findViewById(R.id.s40);
        s40.setClickable(true);
        s40.setOnClickListener(this);

        s41 = findViewById(R.id.s41);
        s41.setClickable(true);
        s41.setOnClickListener(this);

        s42 = findViewById(R.id.s42);
        s42.setClickable(true);
        s42.setOnClickListener(this);

        s43 = findViewById(R.id.s43);
        s43.setClickable(true);
        s43.setOnClickListener(this);

        s44 = findViewById(R.id.s44);
        s44.setClickable(true);
        s44.setOnClickListener(this);

        s45 = findViewById(R.id.s45);
        s45.setClickable(true);
        s45.setOnClickListener(this);

        s46 = findViewById(R.id.s46);
        s46.setClickable(true);
        s46.setOnClickListener(this);

        s47 = findViewById(R.id.s47);
        s47.setClickable(true);
        s47.setOnClickListener(this);

        s48 = findViewById(R.id.s48);
        s48.setClickable(true);
        s48.setOnClickListener(this);

        s49 = findViewById(R.id.s49);
        s49.setClickable(true);
        s49.setOnClickListener(this);

        s50 = findViewById(R.id.s50);
        s50.setClickable(true);
        s50.setOnClickListener(this);

        s51 = findViewById(R.id.s51);
        s51.setClickable(true);
        s51.setOnClickListener(this);

        s52 = findViewById(R.id.s52);
        s52.setClickable(true);
        s52.setOnClickListener(this);

        s53 = findViewById(R.id.s53);
        s53.setClickable(true);
        s53.setOnClickListener(this);

        s54 = findViewById(R.id.s54);
        s54.setClickable(true);
        s54.setOnClickListener(this);

        s55 = findViewById(R.id.s55);
        s55.setClickable(true);
        s55.setOnClickListener(this);

        s56 = findViewById(R.id.s56);
        s56.setClickable(true);
        s56.setOnClickListener(this);

        s57 = findViewById(R.id.s57);
        s57.setClickable(true);
        s57.setOnClickListener(this);

        s58 = findViewById(R.id.s58);
        s58.setClickable(true);
        s58.setOnClickListener(this);

        s59 = findViewById(R.id.s59);
        s59.setClickable(true);
        s59.setOnClickListener(this);

        b0 = findViewById(R.id.b0);
        b0.setClickable(true);
        b0.setOnClickListener(this);

        b1 = findViewById(R.id.b1);
        b1.setClickable(true);
        b1.setOnClickListener(this);

        b2 = findViewById(R.id.b2);
        b2.setClickable(true);
        b2.setOnClickListener(this);

        b3 = findViewById(R.id.b3);
        b3.setClickable(true);
        b3.setOnClickListener(this);

        b4 = findViewById(R.id.b4);
        b4.setClickable(true);
        b4.setOnClickListener(this);

        b5 = findViewById(R.id.b5);
        b5.setClickable(true);
        b5.setOnClickListener(this);

        b6 = findViewById(R.id.b6);
        b6.setClickable(true);
        b6.setOnClickListener(this);

        b7 = findViewById(R.id.b7);
        b7.setClickable(true);
        b7.setOnClickListener(this);

        b8 = findViewById(R.id.b8);
        b8.setClickable(true);
        b8.setOnClickListener(this);

        b9 = findViewById(R.id.b9);
        b9.setClickable(true);
        b9.setOnClickListener(this);

        b10 = findViewById(R.id.b10);
        b10.setClickable(true);
        b10.setOnClickListener(this);

        b11 = findViewById(R.id.b11);
        b11.setClickable(true);
        b11.setOnClickListener(this);

        b12 = findViewById(R.id.b12);
        b12.setClickable(true);
        b12.setOnClickListener(this);



        y0 = findViewById(R.id.y0);
        y0.setClickable(true);
        y0.setOnClickListener(this);

        y1 = findViewById(R.id.y1);
        y1.setClickable(true);
        y1.setOnClickListener(this);

        y2 = findViewById(R.id.y2);
        y2.setClickable(true);
        y2.setOnClickListener(this);

        y3 = findViewById(R.id.y3);
        y3.setClickable(true);
        y3.setOnClickListener(this);

        y4 = findViewById(R.id.y4);
        y4.setClickable(true);
        y4.setOnClickListener(this);

        y5 = findViewById(R.id.y5);
        y5.setClickable(true);
        y5.setOnClickListener(this);

        y6 = findViewById(R.id.y6);
        y6.setClickable(true);
        y6.setOnClickListener(this);

        y7 = findViewById(R.id.y7);
        y7.setClickable(true);
        y7.setOnClickListener(this);

        y8 = findViewById(R.id.y8);
        y8.setClickable(true);
        y8.setOnClickListener(this);

        y9 = findViewById(R.id.y9);
        y9.setClickable(true);
        y9.setOnClickListener(this);

        y10 = findViewById(R.id.y10);
        y10.setClickable(true);
        y10.setOnClickListener(this);

        y11 = findViewById(R.id.y11);
        y11.setClickable(true);
        y11.setOnClickListener(this);

        y12 = findViewById(R.id.y12);
        y12.setClickable(true);
        y12.setOnClickListener(this);



        g0 = findViewById(R.id.g0);
        g0.setClickable(true);
        g0.setOnClickListener(this);

        g1 = findViewById(R.id.g1);
        g1.setClickable(true);
        g1.setOnClickListener(this);

        g2 = findViewById(R.id.g2);
        g2.setClickable(true);
        g2.setOnClickListener(this);

        g3 = findViewById(R.id.g3);
        g3.setClickable(true);
        g3.setOnClickListener(this);

        g4 = findViewById(R.id.g4);
        g4.setClickable(true);
        g4.setOnClickListener(this);

        g5 = findViewById(R.id.g5);
        g5.setClickable(true);
        g5.setOnClickListener(this);

        g6 = findViewById(R.id.g6);
        g6.setClickable(true);
        g6.setOnClickListener(this);

        g7 = findViewById(R.id.g7);
        g7.setClickable(true);
        g7.setOnClickListener(this);

        g8 = findViewById(R.id.g8);
        g8.setClickable(true);
        g8.setOnClickListener(this);

        g9 = findViewById(R.id.g9);
        g9.setClickable(true);
        g9.setOnClickListener(this);

        g10 = findViewById(R.id.g10);
        g10.setClickable(true);
        g10.setOnClickListener(this);

        g11 = findViewById(R.id.g11);
        g11.setClickable(true);
        g11.setOnClickListener(this);

        g12 = findViewById(R.id.g12);
        g12.setClickable(true);
        g12.setOnClickListener(this);


        r0 = findViewById(R.id.r0);
        r0.setClickable(true);
        r0.setOnClickListener(this);

        r1 = findViewById(R.id.r1);
        r1.setClickable(true);
        r1.setOnClickListener(this);

        r2 = findViewById(R.id.r2);
        r2.setClickable(true);
        r2.setOnClickListener(this);

        r3 = findViewById(R.id.r3);
        r3.setClickable(true);
        r3.setOnClickListener(this);

        r4 = findViewById(R.id.r4);
        r4.setClickable(true);
        r4.setOnClickListener(this);

        r5 = findViewById(R.id.r5);
        r5.setClickable(true);
        r5.setOnClickListener(this);

        r6 = findViewById(R.id.r6);
        r6.setClickable(true);
        r6.setOnClickListener(this);

        r7 = findViewById(R.id.r7);
        r7.setClickable(true);
        r7.setOnClickListener(this);

        r8 = findViewById(R.id.r8);
        r8.setClickable(true);
        r8.setOnClickListener(this);

        r9 = findViewById(R.id.r9);
        r9.setClickable(true);
        r9.setOnClickListener(this);

        r10 = findViewById(R.id.r10);
        r10.setClickable(true);
        r10.setOnClickListener(this);

        r11 = findViewById(R.id.r11);
        r11.setClickable(true);
        r11.setOnClickListener(this);

        r12 = findViewById(R.id.r12);
        r12.setClickable(true);
        r12.setOnClickListener(this);





        startGame();
    }

    private void revertFinishNum(char playerTurn) {
        if (playerTurn == 'b') {
            blueFinishNum--;
        }
        if (playerTurn == 'y') {
            yellowFinishNum--;
        }
        if (playerTurn == 'g') {
            greenFinishNum--;
        }
        if (playerTurn == 'r') {
            redFinishNum--;
        }
    }

    private void startGame() {
        startTurn(TURN_ONE);
        updateUI();
    }

    private void startTurn(int turnNum) {
        //backup the board in case a 7 is drawn and we need to do undo move
        boardModel.backupBoardState();


        //if first turn, randomize who goes first
        if (turnNum == TURN_ONE) {
            boardModel.setPlayerTurn(boardModel.randomizePlayer());
        }
        //else switch to the next player clock-wise
        else if (turnNum == TWO_DRAW_AGAIN){
            Toast.makeText(this, "Draw Again", Toast.LENGTH_SHORT).show();
        }
        else {
            boardModel.switchPlayer();
            updateCurrentPlayerGraphic();
        }
        //rotate UI elements so they face whomever's turn it is
        rotateElements();
        //draw card
        boardModel.setDrawPhase(true);
        draw.setClickable(true);
        textSwitcher.setText("");
    }

    private void rotateElements() {
        float degrees = 0;
        if (boardModel.getPlayerTurn() == 'b')
            degrees = 180;
        if (boardModel.getPlayerTurn() == 'y')
            degrees = 270;
        if (boardModel.getPlayerTurn() == 'g')
            degrees = 0;
        if (boardModel.getPlayerTurn() == 'r')
            degrees = 90;

        textSwitcher.setRotation(degrees);
        sevenMoveCounter.setRotation(degrees);
        undoMove.setRotation(degrees);
    }


    //click listener for spots
    @Override
    public void onClick(View v) {

        //can only move during action phase
        if (boardModel.getActionPhase()) {
            //variable for dealing with 11 card
            previousView = v;

            //move piece if there is a previously highlighted piece
            if (!previousHighlightName.equals("")) {
                //if a piece is moved
                if (movePiece(v, previousHighlightName)){

                    //2 draws again
                    if (boardModel.getCurrentCard().equals("2")) {
                        //draw cards
                        drawAgainFor2();
                        unhighlight();
                        updateUI();
                        endTurn(TWO_DRAW_AGAIN);
                        return;
                    }

                    unhighlight();
                    updateUI();
                    //if 7, turn doesn't end yet
                    if (!boardModel.getCurrentCard().equals("7")) {
                        boardModel.setActionPhase(false);
                    }
                    return;
                }


            }
            unhighlight();
            previousHighlightName = highlight(v);
            updateUI();
        }
    }

    private void drawAgainFor2() {
        boardModel.setActionPhase(false);
        boardModel.setDrawPhase(true);
        draw.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    private boolean movePiece(View v, String previousHighlightName) {

        String spotDestination = boardModel.getSpot(v);
        char destinationPieceType = boardModel.getSpotType(spotDestination);

        //prevent a piece from deleting its own type
        if (destinationPieceType != boardModel.getSpotType(previousHighlightName) &&
                destinationPieceType != topPiece && destinationPieceType != topPiece1) {

            if (destinationPieceType == 'h' ||
                    destinationPieceType == 'B' ||
                    destinationPieceType == 'Y' ||
                    destinationPieceType == 'G' ||
                    destinationPieceType == 'R') {

                //used to place spots into the goal. Tallies the score if the piece is moving into a high index
                //that is not part of the normalSpots array
                if (boardModel.getArrayToUse(spotDestination) != boardModel.getNormalSpots() &&
                        boardModel.getSpotIndex(spotDestination) > 8) {
                    addFinishNum(boardModel.getPlayerTurn());
                }


                //if turn 7 and trying to uncouple two pieces in one spot
                if (boardModel.getCurrentCard().equals("7") && boardModel.getSpotType(previousHighlightName) == 'f' ||
                        boardModel.getCurrentCard().equals("7") && boardModel.getSpotType(previousHighlightName) == 's') {
                    if (destinationPieceType == 'h') {
                        //change destination spot to the top piece
                        if (topPiece != 'x') {
                            boardModel.setArraySpot(boardModel.getArrayToUse(spotDestination),
                                    boardModel.getSpotIndex(spotDestination), topPiece);


                            //change the moving piece spot to the flattened piece
                            boardModel.setArraySpot(boardModel.getNormalSpots(),
                                    boardModel.getSpotIndex(previousHighlightName), flattenedPiece);

                            //reset the pieces for next move
                            topPiece = 'x';
                            flattenedPiece = 'x';
                            flattenedPieceIndex = 0;
                            decrementSevenCounter();
                            return true;
                        }
                        else if (topPiece1 != 'x') {
                            boardModel.setArraySpot(boardModel.getArrayToUse(spotDestination),
                                    boardModel.getSpotIndex(spotDestination), topPiece1);


                            //change the moving piece spot to the flattened piece
                            boardModel.setArraySpot(boardModel.getNormalSpots(),
                                    boardModel.getSpotIndex(previousHighlightName), flattenedPiece1);

                            //reset the pieces for next move
                            topPiece1 = 'x';
                            flattenedPiece1 = 'x';
                            flattenedPieceIndex = 0;
                            decrementSevenCounter();
                            return true;

                        }
                    }
                    //if moving to a spot with another piece again
                    else {

                        if (boardModel.getSpotIndex(previousHighlightName) == flattenedPieceIndex) {
                            //change the moving piece spot to the flat piece
                            boardModel.setArraySpot(boardModel.getArrayToUse(previousHighlightName),
                                    boardModel.getSpotIndex(previousHighlightName), flattenedPiece);

                            //gather new flattened piece
                            flattenedPiece = destinationPieceType;
                            flattenedPieceIndex++;

                            //top piece hasn't changed, so we don't have to reassign it

                            //change destination spot to f
                            boardModel.setArraySpot(boardModel.getNormalSpots(),
                                    boardModel.getSpotIndex(spotDestination), 'f');
                            decrementSevenCounter();
                            return true;
                        }
                        else if (boardModel.getSpotIndex(previousHighlightName) == flattenedPieceIndex1) {
                            //change the moving piece spot to the flat piece
                            boardModel.setArraySpot(boardModel.getArrayToUse(previousHighlightName),
                                    boardModel.getSpotIndex(previousHighlightName), flattenedPiece1);

                            //gather new flattened piece
                            flattenedPiece1 = destinationPieceType;
                            flattenedPieceIndex1++;

                            //top piece hasn't changed, so we don't have to reassign it

                            //change destination spot to f
                            boardModel.setArraySpot(boardModel.getNormalSpots(),
                                    boardModel.getSpotIndex(spotDestination), 's');
                            decrementSevenCounter();
                            return true;
                        }
                    }
                }


                //change highlighted spot to the moving piece
                boardModel.setArraySpot(boardModel.getArrayToUse(spotDestination),
                        boardModel.getSpotIndex(spotDestination), boardModel.getSpotType(previousHighlightName));



                //set the counter for moving on 7
                if (boardModel.getCurrentCard().equals("7")) {
                    decrementSevenCounter();
                }



                //swap for 11 card
                if (boardModel.getCurrentCard().equals("11") && swap) {
                    //change the moving piece spot to blank
                    boardModel.setArraySpot(boardModel.getArrayToUse(previousHighlightName),
                            boardModel.getSpotIndex(previousHighlightName), destinationPieceType);
                    return true;
                }


                //logic for having two pieces in one spot during 7 move
                if (destinationPieceType != 'h' && boardModel.getCurrentCard().equals("7") && topPiece == 'x') {

                    //set details for top and flat piece
                    flattenedPiece = destinationPieceType;
                    flattenedPieceIndex = boardModel.getSpotIndex(spotDestination);
                    topPiece = boardModel.getSpotType(previousHighlightName);

                    //change character to 'f' for the update logic to know to access both flattened piece and top piece
                    boardModel.setArraySpot(boardModel.getNormalSpots(), boardModel.getSpotIndex(spotDestination), 'f');

                    //change the moving piece spot to blank
                    boardModel.setArraySpot(boardModel.getArrayToUse(previousHighlightName),
                            boardModel.getSpotIndex(previousHighlightName), '0');
                    return true;
                }

                //logic for having two pieces in one spot during 7 move
                if (destinationPieceType != 'h' && boardModel.getCurrentCard().equals("7") && topPiece1 == 'x') {

                    //set details for top and flat piece
                    flattenedPiece1 = destinationPieceType;
                    flattenedPieceIndex1 = boardModel.getSpotIndex(spotDestination);
                    topPiece1 = boardModel.getSpotType(previousHighlightName);

                    //change character to 'f' for the update logic to know to access both flattened piece and top piece
                    boardModel.setArraySpot(boardModel.getNormalSpots(), boardModel.getSpotIndex(spotDestination), 's');

                    //change the moving piece spot to blank
                    boardModel.setArraySpot(boardModel.getArrayToUse(previousHighlightName),
                            boardModel.getSpotIndex(previousHighlightName), '0');
                    return true;
                }





                //change the moving piece spot to blank
                boardModel.setArraySpot(boardModel.getArrayToUse(previousHighlightName),
                        boardModel.getSpotIndex(previousHighlightName), '0');



                //if piece is an enemy and you land on it, send it back home.
                if (destinationPieceType != 'h') {

                    //return piece to home
                    addPieceToHome(destinationPieceType);
                    Toast.makeText(this, "Sorry!", Toast.LENGTH_SHORT).show();
                }

                Log.d("mess", Character.toString(boardModel.getBlueSpots()[1]));
                return true;
            }
        }
        return false;
    }

    private void decrementSevenCounter() {
        moveCounterSeven--;
        sevenMoveCounter.setText(Integer.toString(moveCounterSeven));
        sevenMoveCounter.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    private void addPieceToHome(char destinationPieceType) {
        boardModel.setArraySpot(boardModel.findWhichHome(destinationPieceType),
                findEmptyHomeSpot(boardModel.findWhichHome(destinationPieceType)), destinationPieceType);
    }

    private void addFinishNum(char playerTurn) {
        if (playerTurn == 'b') {
            blueFinishNum++;
            scoredThisTurn = true;
        }
        if(playerTurn == 'y') {
            yellowFinishNum++;
            scoredThisTurn = true;

        }
        if(playerTurn == 'g') {
            greenFinishNum++;
            scoredThisTurn = true;

        }
        if(playerTurn == 'r') {
            redFinishNum++;
            scoredThisTurn = true;
        }

        //figure out winner
        if (blueFinishNum == 4)
            gameOver('b');
        if (yellowFinishNum == 4)
            gameOver('y');
        if (greenFinishNum == 4)
            gameOver('g');
        if (redFinishNum == 4) {
            gameOver('r');
        }
    }

    private void gameOver(char winner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);


        if (winner == 'b')
            builder.setMessage("Blue Wins");
        if (winner == 'y')
            builder.setMessage("Yellow Wins");
        if (winner == 'g')
            builder.setMessage("Green Wins");
        if (winner == 'r')
            builder.setMessage("Red Wins");

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();


    }

    private void unhighlight() {
        //iterate through each array and unhighlight
        unhighlightIterate(boardModel.getNormalSpots());
        unhighlightIterate(boardModel.getBlueSpots());
        unhighlightIterate(boardModel.getYellowSpots());
        unhighlightIterate(boardModel.getGreenSpots());
        unhighlightIterate(boardModel.getRedSpots());
    }

    private void unhighlightIterate(char[] chosenArray) {
        for(int i = 0; i < chosenArray.length; i++) {
            if (chosenArray[i] == 'h') {
                boardModel.setArraySpot(chosenArray, i, '0');
            }
            if (chosenArray[i] == 'B') {
                boardModel.setArraySpot(chosenArray, i, 'b');
            }
            if (chosenArray[i] == 'Y') {
                boardModel.setArraySpot(chosenArray, i, 'y');
            }
            if (chosenArray[i] == 'G') {
                boardModel.setArraySpot(chosenArray, i, 'g');
            }
            if (chosenArray[i] == 'R') {
                boardModel.setArraySpot(chosenArray, i, 'r');
            }
        }
    }

    private String highlight(View v) {
        String pieceName = boardModel.getSpot(v);


        int index = boardModel.getSpotIndex(pieceName);
        char[] arrayToUse = boardModel.getArrayToUse(pieceName);
        char pieceType = boardModel.getSpotType(pieceName);


        //bug where sometimes the first spot of blue home, b0, shows up as having an 'x'
        //change the piece to 'b' then reassign all variables
        if (pieceType == 'x') {
            boardModel.setArraySpot(arrayToUse, index, 'b');
            pieceName = boardModel.getSpot(v);
            index = boardModel.getSpotIndex(pieceName);
            arrayToUse = boardModel.getArrayToUse(pieceName);
            pieceType = boardModel.getSpotType(pieceName);
        }

        //if the piece matches the current player, then highlight
        if (pieceType == boardModel.getPlayerTurn() || pieceType == 'f' || pieceType == 's') {

            highlightPiece(index, arrayToUse, pieceType);
            highlightMoves(pieceName, pieceType, arrayToUse, index);
        }

        return pieceName;

    }

    private void highlightPiece(int index, char[] arrayToUse, char pieceType) {
        if (pieceType == '0') {
            boardModel.setArraySpot(arrayToUse, index, 'h');
        }

        if (pieceType == 'b') {
            boardModel.setArraySpot(arrayToUse, index, 'B');
        }

        if (pieceType == 'y') {
            boardModel.setArraySpot(arrayToUse, index, 'Y');
        }

        if (pieceType == 'g') {
            boardModel.setArraySpot(arrayToUse, index, 'G');
        }

        if (pieceType == 'r') {
            boardModel.setArraySpot(arrayToUse, index, 'R');
        }
    }

    private void highlightMoves(String pieceName, char pieceType, char[] arrayToUse, int index) {
        //get card value for math
        String cardName = boardModel.getCurrentCard();
        int cardValue = 0;

        //prevents piece from moving once 7 counter is 0
        if (boardModel.getCurrentCard().equals("7") && moveCounterSeven == 0) {
            return;
        }


        //account for sorry! having value of 0
        if (!cardName.equals("Sorry!")) {
            cardValue = Integer.parseInt(boardModel.getCurrentCard());
        }
        //4 is minus 4
        if (cardValue == 4) {
            cardValue = -4;
        }

        //7 logic works more like a cardValue of 1
        if (cardValue == 7) {
            cardValue = 1;
        }

        int indexAfterWrapAround = 0;
        int indexToChange = 0;
        int x = 0;
        //if wrapping around the board
        if (index + cardValue > 59) {
            x = 59 - index;
            //-1 to account for index starting at 0
            indexAfterWrapAround = cardValue - x - 1;
            indexToChange = indexAfterWrapAround;
        }
        //if normal move
        else {
            indexToChange = index + cardValue;
        }

        //card is 1
        if (boardModel.getCurrentCard().equals("1")) {
            cardEquals1(index, indexToChange, arrayToUse, pieceType);
        }

        if (boardModel.getCurrentCard().equals("2")) {
            cardEquals2(index, indexToChange, arrayToUse, pieceType);
        }
        if (boardModel.getCurrentCard().equals("3")) {
            cardEquals3(index, indexToChange, arrayToUse, pieceType);
        }
        if (boardModel.getCurrentCard().equals("4")) {
            cardEquals4(index, indexToChange, arrayToUse, pieceType);

        }
        if (boardModel.getCurrentCard().equals("5")) {
            cardEquals5(index, indexToChange, arrayToUse, pieceType);

        }
        if (boardModel.getCurrentCard().equals("7")) {
            cardEquals7(index, indexToChange, arrayToUse, pieceType);

        }
        if (boardModel.getCurrentCard().equals("8")) {
            cardEquals8(index, indexToChange, arrayToUse, pieceType);
        }
        if (boardModel.getCurrentCard().equals("10")) {
            cardEquals10(index, indexToChange, arrayToUse, pieceType);

        }
        if (boardModel.getCurrentCard().equals("11")) {
            cardEquals11(index, indexToChange, arrayToUse, pieceType);
        }
        if (boardModel.getCurrentCard().equals("12")) {
            cardEquals12(index, indexToChange, arrayToUse, pieceType);

        }
        if (boardModel.getCurrentCard().equals("Sorry!")) {
            cardEqualsSorry(index, indexToChange, arrayToUse, pieceType);
        }
    }


    //logic for all card values
    private void cardEquals1(int index, int indexToChange, char[] arrayToUse, char pieceType) {
        //if moving through colored spots
        if (arrayToUse != boardModel.getNormalSpots()) {
            //if in home
            if (index < 4) {
                if (pieceType == 'b') {
                    indexToChange = 4;
                }
                if (pieceType == 'y') {
                    indexToChange = 19;
                }
                if (pieceType == 'g') {
                    indexToChange = 34;
                }
                if (pieceType == 'r') {
                    indexToChange = 49;
                }
                boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));
            }
            //if in end zone and almost to goal
            else if (index < 8){
                indexToChange = index + 1;
                boardModel.setArraySpot(arrayToUse, indexToChange, boardModel.determineHighlightChar(arrayToUse, indexToChange));
            }
            else if (index == 8){

                //get the point tally depending on color
                int currentPlayerPoints = getFinishNumColor(boardModel.getPlayerTurn());

                if (currentPlayerPoints == 0) {
                    indexToChange = index + 4;
                }
                if (currentPlayerPoints == 1) {
                    indexToChange = index + 3;
                }
                if (currentPlayerPoints == 2) {
                    indexToChange = index + 2;
                }
                if (currentPlayerPoints == 3) {
                    indexToChange = index + 1;
                }
                boardModel.setArraySpot(arrayToUse, indexToChange, boardModel.determineHighlightChar(arrayToUse, indexToChange));
            }
        }
        //if in a normal spot
        else {
            //entering blue end zone logic
            if (index == 2 && boardModel.getPlayerTurn() == 'b') {
                indexToChange = indexToChange + 1;
                boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                return;
            }
            //entering yellow zone logic
            if (index == 17 && boardModel.getPlayerTurn() == 'y') {
                indexToChange = indexToChange + 1 - 15;
                boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                return;
            }
            //entering green zone logic
            if (index == 32 && boardModel.getPlayerTurn() == 'g') {
                indexToChange = indexToChange + 1 - 30;
                boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                return;
            }
            //entering red zone logic
            if (index == 47 && boardModel.getPlayerTurn() == 'r') {
                indexToChange = indexToChange + 1 - 45;
                boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
            }

            else {
                boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));
            }
        }
    }

    private void cardEquals2(int index, int indexToChange, char[] arrayToUse, char pieceType) {
        //if moving through colored spots
        if (arrayToUse != boardModel.getNormalSpots()) {
            //if in home
            if (index < 4) {
                if (pieceType == 'b') {
                    indexToChange = 4;
                }
                if (pieceType == 'y') {
                    indexToChange = 19;
                }
                if (pieceType == 'g') {
                    indexToChange = 34;
                }
                if (pieceType == 'r') {
                    indexToChange = 49;
                }
                boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));
            }
            //if in end zone and almost to goal
            else if (index < 7){
                indexToChange = index + 2;
                boardModel.setArraySpot(arrayToUse, indexToChange, boardModel.determineHighlightChar(arrayToUse, indexToChange));
            }
            else if (index == 7){

                //get the point tally depending on color
                int currentPlayerPoints = getFinishNumColor(boardModel.getPlayerTurn());

                if (currentPlayerPoints == 0) {
                    indexToChange = index + 5;
                }
                if (currentPlayerPoints == 1) {
                    indexToChange = index + 4;
                }
                if (currentPlayerPoints == 2) {
                    indexToChange = index + 3;
                }
                if (currentPlayerPoints == 3) {
                    indexToChange = index + 2;
                }
                boardModel.setArraySpot(arrayToUse, indexToChange, boardModel.determineHighlightChar(arrayToUse, indexToChange));
            }
            else {
                return;
            }
        }
        //if in a normal spot
        else {
            //entering blue end zone logic
            if (boardModel.getPlayerTurn() == 'b' && index == 2 || boardModel.getPlayerTurn() == 'b' && index == 1) {
                indexToChange = indexToChange + 1;
                boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                return;
            }


            //entering yellow zone logic
            if (boardModel.getPlayerTurn() == 'y' && index == 17 || boardModel.getPlayerTurn() == 'y' && index == 16) {
                indexToChange = indexToChange + 1 - 15;
                boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                return;
            }


            //entering green zone logic
            if (boardModel.getPlayerTurn() == 'g' && index == 32 || boardModel.getPlayerTurn() == 'g' && index == 31) {
                indexToChange = indexToChange + 1 - 30;
                boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                return;
            }


            //entering red zone logic
            if (boardModel.getPlayerTurn() == 'r' && index == 47 || boardModel.getPlayerTurn() == 'r' && index == 46) {
                indexToChange = indexToChange + 1 - 45;
                boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
                return;
            }

            //moving and landing in normal spot
            else {
                boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));
            }
        }
    }

    private void cardEquals3(int index, int indexToChange, char[] arrayToUse, char pieceType) {
        //if moving through colored spots
        if (arrayToUse != boardModel.getNormalSpots()) {
            //cif in home, can't move
            if (index >= 0 && index < 4) {
                return;
            }
            //if in end zone and almost to goal
            if (index < 6){
                indexToChange = index + 3;
                boardModel.setArraySpot(arrayToUse, indexToChange, boardModel.determineHighlightChar(arrayToUse, indexToChange));
            }
            else if (index == 6){

                //get the point tally depending on color
                int currentPlayerPoints = getFinishNumColor(boardModel.getPlayerTurn());

                if (currentPlayerPoints == 0) {
                    indexToChange = index + 6;
                }
                if (currentPlayerPoints == 1) {
                    indexToChange = index + 5;
                }
                if (currentPlayerPoints == 2) {
                    indexToChange = index + 4;
                }
                if (currentPlayerPoints == 3) {
                    indexToChange = index + 3;
                }
                boardModel.setArraySpot(arrayToUse, indexToChange, boardModel.determineHighlightChar(arrayToUse, indexToChange));
            }
        }
        //if in a normal spot
        else {
            //entering blue end zone logic
            if (boardModel.getPlayerTurn() == 'b' && index == 2 ||
                    boardModel.getPlayerTurn() == 'b' && index == 1 ||
                    boardModel.getPlayerTurn() == 'b' && index == 0) {

                indexToChange = indexToChange + 1;
                boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                return;
            }


            //entering yellow zone logic
            if (boardModel.getPlayerTurn() == 'y' && index == 17 ||
                    boardModel.getPlayerTurn() == 'y' && index == 16 ||
                    boardModel.getPlayerTurn() == 'y' && index == 15) {
                indexToChange = indexToChange + 1 - 15;
                boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                return;
            }

            //entering green zone logic
            if (boardModel.getPlayerTurn() == 'g' && index == 32 ||
                    boardModel.getPlayerTurn() == 'g' && index == 31 ||
                    boardModel.getPlayerTurn() == 'g' && index == 30) {

                indexToChange = indexToChange + 1 - 30;
                boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                return;
            }


            //entering red zone logic
            if (boardModel.getPlayerTurn() == 'r' && index == 47 ||
                    boardModel.getPlayerTurn() == 'r' && index == 46 ||
                    boardModel.getPlayerTurn() == 'r' && index == 45) {

                indexToChange = indexToChange + 1 - 45;
                boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
                return;
            }

            //moving and landing in normal spot
            else {
                boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));
            }
        }
    }

    private void cardEquals4(int index, int indexToChange, char[] arrayToUse, char pieceType) {

        if(index >= 0 && index < 4) {
            indexToChange = 59 + index - 3;
        }

        //if moving through colored spots
        if (arrayToUse != boardModel.getNormalSpots()) {
            //if in last spot of end zone move back down end zone
            if (index == 8){
                indexToChange = index - 4;
                boardModel.setArraySpot(arrayToUse, indexToChange, boardModel.determineHighlightChar(arrayToUse, indexToChange));
                return;
            }
            //if in end zone must move out into normal spot array
            if (index >= 4 && index < 8) {
                //choose outcome by player color
                // if blue
                if(boardModel.getPlayerTurn() == 'b') {
                    //wraparound math for blue going back out of end zone
                    if (index == 4) {
                        indexToChange = 59;
                    }
                    //for indexes 5, 6, and 7
                    else {
                        indexToChange = index - 5;
                    }
                }
                //if yellow
                if(boardModel.getPlayerTurn() == 'y') {
                    indexToChange = 17 + index - 3 - 4;
                }
                //if green
                if(boardModel.getPlayerTurn() == 'g') {
                    indexToChange = 32 + index - 3 - 4;
                }
                //if red
                if(boardModel.getPlayerTurn() == 'r') {
                    indexToChange = 47 + index - 3 - 4;
                }
                boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange, boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));

            }
        }
        //if in a normal spot
        else {
            boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                    boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));

        }
    }

    private void cardEquals5(int index, int indexToChange, char[] arrayToUse, char pieceType) {
        //if moving through colored spots
        if (arrayToUse != boardModel.getNormalSpots()) {
            //4 is the only index within the end zone with which card 5 can interact
            if (index == 4 ){
                //get the point tally depending on color
                int currentPlayerPoints = getFinishNumColor(boardModel.getPlayerTurn());

                if (currentPlayerPoints == 0) {
                    indexToChange = index + 4 + 4;
                }
                if (currentPlayerPoints == 1) {
                    indexToChange = index + 3 + 4;
                }
                if (currentPlayerPoints == 2) {
                    indexToChange = index + 2 + 4;
                }
                if (currentPlayerPoints == 3) {
                    indexToChange = index + 1 + 4;
                }
                boardModel.setArraySpot(arrayToUse, indexToChange, boardModel.determineHighlightChar(arrayToUse, indexToChange));
            }
        }
        //if in a normal spot
        else {
            //entering blue end zone logic
            if (boardModel.getPlayerTurn() == 'b' && index >= 0 && index <= 2 ||
                    boardModel.getPlayerTurn() == 'b' && index >= 58) {
                indexToChange = indexToChange + 1;
                boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                return;
            }
            //entering yellow zone logic
            if (boardModel.getPlayerTurn() == 'y' && index >= 13 && index <= 17) {
                indexToChange = indexToChange + 1 - 15;
                boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                return;
            }
            //entering green zone logic
            if (boardModel.getPlayerTurn() == 'g' && index >= 28 && index <= 32) {
                indexToChange = indexToChange + 1 - 30;
                boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                return;
            }
            //entering red zone logic
            if (boardModel.getPlayerTurn() == 'r' && index >= 43 && index <= 47) {
                indexToChange = indexToChange + 1 - 45;
                boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
                return;
            }

            else {
                boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));
            }
        }
    }

    private void cardEquals7(int index, int indexToChange, char[] arrayToUse, char pieceType) {
        //if moving through colored spots
        if (arrayToUse != boardModel.getNormalSpots()) {
            //if in end zone and almost to goal
            if (index > 3 && index < 8){
                indexToChange = index + 1;
                boardModel.setArraySpot(arrayToUse, indexToChange, boardModel.determineHighlightChar(arrayToUse, indexToChange));
            }
            else if (index == 8){

                //get the point tally depending on color
                int currentPlayerPoints = getFinishNumColor(boardModel.getPlayerTurn());

                if (currentPlayerPoints == 0) {
                    indexToChange = index + 4;
                }
                if (currentPlayerPoints == 1) {
                    indexToChange = index + 3;
                }
                if (currentPlayerPoints == 2) {
                    indexToChange = index + 2;
                }
                if (currentPlayerPoints == 3) {
                    indexToChange = index + 1;
                }
                boardModel.setArraySpot(arrayToUse, indexToChange, boardModel.determineHighlightChar(arrayToUse, indexToChange));
            }
        }
        //if in a normal spot
        else {
            //entering blue end zone logic
            if (index == 2 && boardModel.getPlayerTurn() == 'b') {
                indexToChange = indexToChange + 1;
                boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                return;
            }
            //entering yellow zone logic
            if (index == 17 && boardModel.getPlayerTurn() == 'y') {
                indexToChange = indexToChange + 1 - 15;
                boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                return;
            }
            //entering green zone logic
            if (index == 32 && boardModel.getPlayerTurn() == 'g') {
                indexToChange = indexToChange + 1 - 30;
                boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                return;
            }
            //entering red zone logic
            if (index == 47 && boardModel.getPlayerTurn() == 'r') {
                indexToChange = indexToChange + 1 - 45;
                boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
            }

            else {
                boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));
            }
        }
    }

    private void cardEquals8(int index, int indexToChange, char[] arrayToUse, char pieceType) {
        if (arrayToUse != boardModel.getNormalSpots()) {
            return;
        }

        //entering blue end zone logic
        if (boardModel.getPlayerTurn() == 'b') {
            if (index > 0 && index <=2) {
                return;
            }
            if (index >= 55 && index <= 59) {
                indexToChange = indexToChange + 1;
                boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                return;
            }
            if (index == 0) {
                indexToChange = indexToChange + 1 + 3;
                boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                return;
            }

        }

        if (boardModel.getPlayerTurn() == 'y') {
            //entering yellow zone logic
            if (index > 15 && index <=17) {
                return;
            }

            if (index >= 10 && index <= 14) {
                indexToChange = indexToChange + 1 - 15;
                boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                return;
            }
            if (index == 15) {
                indexToChange = indexToChange + 1 + 3 - 15;
                boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                return;
            }
        }

        //entering green zone logic
        if (boardModel.getPlayerTurn() == 'g') {
            //prevents the piece from passing the goal
            if (index > 30 && index <=32) {
                return;
            }
            if (index >= 25 && index <= 29) {
                indexToChange = indexToChange + 1 - 30;
                boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                return;
            }
            if (index == 30) {
                indexToChange = indexToChange + 1 + 3 - 30;
                boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                return;
            }
        }

        //entering red zone logic
        if (boardModel.getPlayerTurn() == 'r') {
            //prevents the piece from passing the goal
            if (index > 45 && index <=47) {
                return;
            }
            if (index >= 40 && index <= 44) {
                indexToChange = indexToChange + 1 - 45;
                boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
                return;
            }
            if (index == 45) {
                indexToChange = indexToChange + 1 + 3 - 45;
                boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
            }
        }
        //if normal spot start and stop
        boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));

    }

    private void cardEquals10(int index, int indexToChange, char[] arrayToUse, char pieceType) {

        //if in end zone highlight minus 1
        if (arrayToUse != boardModel.getNormalSpots()) {
            if (index > 5 && index < 9) {
                indexToChange = indexToChange - 1;
                boardModel.setArraySpot(arrayToUse, indexToChange,
                        boardModel.determineHighlightChar(arrayToUse, indexToChange));
                return;
            }
            //subtract one from first spot of end zone, thus moving back into normal spot
            if (index == 4) {
                if (boardModel.getPlayerTurn() == 'b') {
                    indexToChange = 2;
                }
                if (boardModel.getPlayerTurn() == 'y') {
                    indexToChange = 17;
                }
                if (boardModel.getPlayerTurn() == 'g') {
                    indexToChange = 32;
                }
                if (boardModel.getPlayerTurn() == 'r') {
                    indexToChange = 47;
                }
                boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));
            }
        }

        //if in normal spot highlight minus 1 and forward 10
        else {
            int indexToChange1 = 0;

            //wraparound 0 to 59 when going backwards
            if (index == 0) {
                indexToChange1 = 59;
            }
            //if any normal other index, simply highlight minus 1
            else {
                indexToChange1 = index - 1;
            }

            //print the -1 spot always, since always in normal
            boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange1,
                    boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange1));

            //entering blue end zone logic
            if (boardModel.getPlayerTurn() == 'b') {
                if (index > 58 || index <=2) {
                    return;
                }
                if (index >= 53 && index <= 57) {
                    indexToChange = indexToChange + 1;
                    boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                    return;
                }
                if (index == 58) {
                    indexToChange = indexToChange + 1 + DISPLACE_TO_GOAL;
                    boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                    return;
                }
            }

            //entering yellow zone logic
            if (boardModel.getPlayerTurn() == 'y') {
                if (boardModel.getPlayerTurn() == 'y' && index > 13 && index <=17) {
                    return;
                }
                if (index >= 8 && index <= 12) {
                    indexToChange = indexToChange + 1 - 15;
                    //highlight + 10 in end zone
                    boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                    return;
                }
                if (index == 13) {
                    indexToChange = indexToChange + 1 + DISPLACE_TO_GOAL - 15;
                    boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                    return;
                }
            }

            //entering green zone logic
            if (boardModel.getPlayerTurn() == 'g') {
                //prevents the piece from passing the goal
                if (index > 28 && index <=32) {
                    return;
                }
                if (index >= 23 && index <= 27) {
                    indexToChange = indexToChange + 1 - 30;
                    boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                    return;
                }
                if (index == 28) {
                    indexToChange = indexToChange + 1 + DISPLACE_TO_GOAL - 30;
                    boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                    return;
                }

            }
            //entering red zone logic
            if (boardModel.getPlayerTurn() == 'r') {
                //prevents the piece from passing the goal
                if (index > 43 && index <= 47) {
                    return;
                }

                if (index >= 38 && index <= 42) {
                    indexToChange = indexToChange + 1 - 45;
                    boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
                    return;
                }
                if (index == 43) {
                    indexToChange = indexToChange + 1 + DISPLACE_TO_GOAL - 45;
                    boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
                    return;
                }
            }
            //if in normal spot
            boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                    boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));
        }
    }

    private void cardEquals11(int index, int indexToChange, char[] arrayToUse, char pieceType) {
        //if in a colored spot return, as there isn't enough space to move
        if (arrayToUse != boardModel.getNormalSpots()) {
            return;
        }

        //if swap field is true, highlight swap positions
        if (swap) {
            boardModel.highlightForSwap();
            //else highlight normal movement
        } else {
            //entering blue end zone logic
            if (boardModel.getPlayerTurn() == 'b') {
                if (index > 57 || index <= 2) {
                    return;
                }
                if (index >= 52 && index <= 56) {
                    indexToChange = indexToChange + 1;
                    boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                    return;
                }
                if (index == 57) {
                    indexToChange = indexToChange + 1 + DISPLACE_TO_GOAL;
                    boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                    return;
                }
            }

            //entering yellow zone logic
            if (boardModel.getPlayerTurn() == 'y') {
                if (boardModel.getPlayerTurn() == 'y' && index > 12 && index <= 17) {
                    return;
                }
                if (index >= 7 && index <= 11) {
                    indexToChange = indexToChange + 1 - 15;
                    //highlight + 10 in end zone
                    boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                    return;
                }
                if (index == 12) {
                    indexToChange = indexToChange + 1 + DISPLACE_TO_GOAL - 15;
                    boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                    return;
                }
            }

            //entering green zone logic
            if (boardModel.getPlayerTurn() == 'g') {
                //prevents the piece from passing the goal
                if (index > 27 && index <= 32) {
                    return;
                }
                if (index >= 22 && index <= 26) {
                    indexToChange = indexToChange + 1 - 30;
                    boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                    return;
                }
                if (index == 27) {
                    indexToChange = indexToChange + 1 + DISPLACE_TO_GOAL - 30;
                    boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                    return;
                }
            }

            //entering red zone logic
            if (boardModel.getPlayerTurn() == 'r') {
                //prevents the piece from passing the goal
                if (index > 42 && index <= 47) {
                    return;
                }

                if (index >= 37 && index <= 41) {
                    indexToChange = indexToChange + 1 - 45;
                    boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
                    return;
                }
                if (index == 42) {
                    indexToChange = indexToChange + 1 + DISPLACE_TO_GOAL - 45;
                    boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                            boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
                    return;
                }
            }
            //if in normal spot
            boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                    boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));
        }
    }

    private void cardEquals12(int index, int indexToChange, char[] arrayToUse, char pieceType) {

        //if in a colored spot return, as there isn't enough space to move
        if (arrayToUse != boardModel.getNormalSpots()) {
            return;
        }

        //entering blue end zone logic
        if (boardModel.getPlayerTurn() == 'b') {
            if (index > 56 || index <= 2) {
                return;
            }
            if (index >= 51 && index <= 55) {
                indexToChange = indexToChange + 1;
                boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                return;
            }
            if (index == 56) {
                indexToChange = indexToChange + 1 + DISPLACE_TO_GOAL;
                boardModel.setArraySpot(boardModel.getBlueSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getBlueSpots(), indexToChange));
                return;
            }
        }

        //entering yellow zone logic
        if (boardModel.getPlayerTurn() == 'y') {
            if (boardModel.getPlayerTurn() == 'y' && index > 11 && index <= 17) {
                return;
            }
            if (index >= 6 && index <= 10) {
                indexToChange = indexToChange + 1 - 15;
                //highlight + 10 in end zone
                boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                return;
            }
            if (index == 11) {
                indexToChange = indexToChange + 1 + DISPLACE_TO_GOAL - 15;
                boardModel.setArraySpot(boardModel.getYellowSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getYellowSpots(), indexToChange));
                return;
            }
        }

        //entering green zone logic
        if (boardModel.getPlayerTurn() == 'g') {
            //prevents the piece from passing the goal
            if (index > 26 && index <= 32) {
                return;
            }
            if (index >= 21 && index <= 25) {
                indexToChange = indexToChange + 1 - 30;
                boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                return;
            }
            if (index == 26) {
                indexToChange = indexToChange + 1 + DISPLACE_TO_GOAL - 30;
                boardModel.setArraySpot(boardModel.getGreenSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getGreenSpots(), indexToChange));
                return;
            }

        }
        //entering red zone logic
        if (boardModel.getPlayerTurn() == 'r') {
            //prevents the piece from passing the goal
            if (index > 41 && index <= 47) {
                return;
            }

            if (index >= 36 && index <= 40) {
                indexToChange = indexToChange + 1 - 45;
                boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
                return;
            }
            if (index == 41) {
                indexToChange = indexToChange + 1 + DISPLACE_TO_GOAL - 45;
                boardModel.setArraySpot(boardModel.getRedSpots(), indexToChange,
                        boardModel.determineHighlightChar(boardModel.getRedSpots(), indexToChange));
                return;
            }
        }
        //if in normal spot
        boardModel.setArraySpot(boardModel.getNormalSpots(), indexToChange,
                boardModel.determineHighlightChar(boardModel.getNormalSpots(), indexToChange));
    }

    private void cardEqualsSorry(int index, int indexToChange, char[] arrayToUse, char pieceType) {
        if (arrayToUse != boardModel.getNormalSpots() && index < 4) {
            boardModel.highlightForSwap();
        }
    }



    private int getFinishNumColor(char playerTurn) {
        if (playerTurn == 'b') {
            return blueFinishNum;
        }
        if (playerTurn == 'y') {
            return yellowFinishNum;
        }
        if (playerTurn == 'g') {
            return greenFinishNum;
        }
        if (playerTurn == 'r') {
            return redFinishNum;
        }
        return 0;
    }

    private void endTurn() {
        swap = false;
        moveCounterSeven = 0;
        sevenMoveCounter.setAlpha(0);
        undoMove.setAlpha(0);
        scoredThisTurn = false;


        //handling flattened pieces for moving 7
        //handling flattened pieces for moving 7
        if (flattenedPiece != 'x') {
            addPieceToHome(flattenedPiece);
            updateForF();
        }

        if (flattenedPiece1 != 'x') {
            addPieceToHome(flattenedPiece1);
            updateForF1();
        }

        topPiece = 'x';
        topPiece1 = 'x';
        flattenedPiece = 'x';
        flattenedPiece1 = 'x';
        flattenedPieceIndex = 0;
        flattenedPieceIndex1 = 0;

        unhighlight();
        updateUI();

        boardModel.setActionPhase(false);

        startTurn(NOT_TURN_ONE);
    }

    private void updateForF() {
        for (int i = 0; i < boardModel.getNormalSpots().length; i++) {
            if (boardModel.getNormalSpots()[i] == 'f') {
                boardModel.setArraySpot(boardModel.getNormalSpots(), i, topPiece);
            }
        }
    }

    private void updateForF1() {
        for (int i = 0; i < boardModel.getNormalSpots().length; i++) {
            if (boardModel.getNormalSpots()[i] == 's') {
                boardModel.setArraySpot(boardModel.getNormalSpots(), i, topPiece1);
            }
        }
    }


    //endturn with code function for passing turn number to startTurn()

    private void endTurn(int code) {
        swap = false;
        moveCounterSeven = 0;
        sevenMoveCounter.setAlpha(0);
        undoMove.setAlpha(0);
        scoredThisTurn = false;

        //handling flattened pieces for moving 7
        if (flattenedPiece != 'x') {
            addPieceToHome(flattenedPiece);
            updateForF();
        }
        if (flattenedPiece1 != 'x') {
            addPieceToHome(flattenedPiece1);
            updateForF1();
        }

        topPiece = 'x';
        topPiece1 = 'x';
        flattenedPiece = 'x';
        flattenedPiece1 = 'x';
        flattenedPieceIndex = 0;
        flattenedPieceIndex1 = 0;

        unhighlight();
        updateUI();

        boardModel.setActionPhase(false);

        startTurn(code);
    }


    private void updateUI() {
        updateSpots();
        updateCurrentPlayerGraphic();
    }

    private void updateSpots() {

        ImageButton temp;


        //normal spot rules
        for (int i = 0; i < boardModel.getNormalSpots().length; i++) {
            if (boardModel.getNormalSpots()[i] == 'b') {
                temp = findViewById(boardModel.getNormalSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.blue_piece);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);
            }

            if (boardModel.getNormalSpots()[i] == 'y') {
                temp = findViewById(boardModel.getNormalSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.yellow_piece);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);

            }
            if (boardModel.getNormalSpots()[i] == 'g') {
                temp = findViewById(boardModel.getNormalSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.green_piece);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);
            }
            if (boardModel.getNormalSpots()[i] == 'r') {
                temp = findViewById(boardModel.getNormalSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.red_piece);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);
            }
            if (boardModel.getNormalSpots()[i] == 'h') {
                temp = findViewById(boardModel.getNormalSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.white_space_highlight);
            }

            //f for flattened
            if (boardModel.getNormalSpots()[i] == 'f') {
                temp = findViewById(boardModel.getNormalSpotsViewPositions()[i]);
                temp.setImageResource(chooseTop(topPiece));
                temp.setBackgroundResource(chooseFlat(flattenedPiece));
            }

            //s for squished
            if (boardModel.getNormalSpots()[i] == 's') {
                temp = findViewById(boardModel.getNormalSpotsViewPositions()[i]);
                temp.setImageResource(chooseTop(topPiece1));
                temp.setBackgroundResource(chooseFlat(flattenedPiece1));
            }




            if (boardModel.getNormalSpots()[i] == '0') {
                temp = findViewById(boardModel.getNormalSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.white_space_black_outline);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);
            }
            if (boardModel.getNormalSpots()[i] == 'B') {
                temp = findViewById(boardModel.getNormalSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.blue_piece);
                temp.setBackgroundResource(R.drawable.white_space_highlight);
            }
            if (boardModel.getNormalSpots()[i] == 'Y') {
                temp = findViewById(boardModel.getNormalSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.yellow_piece);
                temp.setBackgroundResource(R.drawable.white_space_highlight);
            }
            if (boardModel.getNormalSpots()[i] == 'G') {
                temp = findViewById(boardModel.getNormalSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.green_piece);
                temp.setBackgroundResource(R.drawable.white_space_highlight);
            }
            if (boardModel.getNormalSpots()[i] == 'R') {
                temp = findViewById(boardModel.getNormalSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.red_piece);
                temp.setBackgroundResource(R.drawable.white_space_highlight);
            }
        }

        //blue spot rules
        for (int i = 0; i < boardModel.getBlueSpots().length; i++) {
            if (boardModel.getBlueSpots()[i] == 'b') {
                temp = findViewById(boardModel.getBlueSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.blue_piece);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);
            }
            if (boardModel.getBlueSpots()[i] == 'B') {
                temp = findViewById(boardModel.getBlueSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.blue_piece);
                temp.setBackgroundResource(R.drawable.white_space_highlight);
            }
            if (boardModel.getBlueSpots()[i] == 'h') {
                temp = findViewById(boardModel.getBlueSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.white_space_highlight);
            }
            if (boardModel.getBlueSpots()[i] == '0') {
                temp = findViewById(boardModel.getBlueSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.white_space_black_outline);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);
            }
        }

        //yellow spot rules
        for (int i = 0; i < boardModel.getYellowSpots().length; i++) {
            if (boardModel.getYellowSpots()[i] == 'y') {
                temp = findViewById(boardModel.getYellowSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.yellow_piece);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);
            }
            if (boardModel.getYellowSpots()[i] == 'Y') {
                temp = findViewById(boardModel.getYellowSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.yellow_piece);
                temp.setBackgroundResource(R.drawable.white_space_highlight);
            }

            if (boardModel.getYellowSpots()[i] == 'h') {
                temp = findViewById(boardModel.getYellowSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.white_space_highlight);
            }
            if (boardModel.getYellowSpots()[i] == '0') {
                temp = findViewById(boardModel.getYellowSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.white_space_black_outline);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);
            }
        }

        //green spot rules
        for (int i = 0; i < boardModel.getGreenSpots().length; i++) {
            if (boardModel.getGreenSpots()[i] == 'g') {
                temp = findViewById(boardModel.getGreenSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.green_piece);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);
            }
            if (boardModel.getGreenSpots()[i] == 'G') {
                temp = findViewById(boardModel.getGreenSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.green_piece);
                temp.setBackgroundResource(R.drawable.white_space_highlight);
            }
            if (boardModel.getGreenSpots()[i] == 'h') {
                temp = findViewById(boardModel.getGreenSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.white_space_highlight);
            }
            if (boardModel.getGreenSpots()[i] == '0') {
                temp = findViewById(boardModel.getGreenSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.white_space_black_outline);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);
            }
        }

        //red spot rules
        for (int i = 0; i < boardModel.getRedSpots().length; i++) {
            if (boardModel.getRedSpots()[i] == 'r') {
                temp = findViewById(boardModel.getRedSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.red_piece);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);
            }
            if (boardModel.getRedSpots()[i] == 'R') {
                temp = findViewById(boardModel.getRedSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.red_piece);
                temp.setBackgroundResource(R.drawable.white_space_highlight);
            }
            if (boardModel.getRedSpots()[i] == 'h') {
                temp = findViewById(boardModel.getRedSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.white_space_highlight);
            }
            if (boardModel.getRedSpots()[i] == '0') {
                temp = findViewById(boardModel.getRedSpotsViewPositions()[i]);
                temp.setImageResource(R.drawable.white_space_black_outline);
                temp.setBackgroundResource(R.drawable.white_space_black_outline);
            }
        }
    }

    private int chooseTop(char flatOrTop) {
        if (flatOrTop == 'B') {
            return R.drawable.blue_piece;
        }
        if (flatOrTop == 'Y') {
            return R.drawable.yellow_piece;
        }
        if (flatOrTop == 'G') {
            return R.drawable.green_piece;
        }
        if (flatOrTop == 'R') {
            return R.drawable.red_piece;
        }
        return R.drawable.white_space_black_outline;
    }

    private int chooseFlat(char flatOrTop) {
        if (flatOrTop == 'B') {
            return R.color.dark_blue;
        }
        if (flatOrTop == 'Y') {
            return R.color.dark_yellow;
        }
        if (flatOrTop == 'G') {
            return R.color.darker_green;
        }
        if (flatOrTop == 'R') {
            return R.color.darker_red;
        }
        return R.drawable.white_space_black_outline;
    }

    //iterate through home and see which piece is missing
    private int findEmptyHomeSpot(char[] arrayToUse) {
        for (int i = 3; i >= 0; i--) {
            if (arrayToUse[i] == '0') {
                return i;
            }
            //for weird bug where sometimes blue pieces or blue
            // empty spots are replaced with 'x' instead of 'b' or '0'.
            //this allows blue pieces to return home even when the spot has become an 'x'
            else if (arrayToUse[i] == 'x') {
                return i;
            }
        }
        return 0;
    }

    private void updateCurrentPlayerGraphic() {
        //update current player graphic
        char currentPlayer = boardModel.getPlayerTurn();

        if (currentPlayer == 'b') {
            currentPlayerGraphic.setImageResource(R.drawable.blue_oval);
        }
        else if (currentPlayer == 'y') {
            currentPlayerGraphic.setImageResource(R.drawable.yellow_oval);
        }
        else if (currentPlayer == 'g') {
            currentPlayerGraphic.setImageResource(R.drawable.green_home_oval);
        }
        else if (currentPlayer == 'r') {
            currentPlayerGraphic.setImageResource(R.drawable.red_oval);
        }
        else {
            Toast.makeText(this, "Error choosing player", Toast.LENGTH_SHORT).show();
        }
    }

}