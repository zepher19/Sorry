package com.myapps.sorry;

import android.view.View;

import java.util.Random;

public class BoardModel {

    private static final BoardModel instance = new BoardModel();

    // private constructor to avoid client applications using the constructor
    private BoardModel(){

    }

    public static BoardModel getInstance() {
        return instance;
    }




    private final char[] players = {'b','y','g','r'};

    private char playerTurn;

    private final String[] cards = {"1", "2", "3", "4", "5", "7", "8", "10", "11", "12", "Sorry!"};

    final char[] initialNormalSpots = {'0','0','0','0','0','0','0','0','0','0',
                                                '0','0','0','0','0','0','0','0','0','0',
                                                '0','0','0','0','0','0','0','0','0','0',
                                                '0','0','0','0','0','0','0','0','0','0',
                                                '0','0','0','0','0','0','0','0','0','0',
                                                '0','0','0','0','0','0','0','0','0','0'};
    private char[] normalSpots = initialNormalSpots;

    public char[] getNormalSpots() {
        return normalSpots;
    }

    private char[] blueStart = {'b','b','b','b','0','0','0','0','0','0','0', '0', '0'};

    private char[] blueSpots = blueStart;


    private char[] yellowStart = {'y','y','y','y','0','0','0','0','0','0','0','0','0'};

    public char[] yellowSpots = yellowStart;


    private char[] greenStart = {'g','g','g','g', '0','0','0','0','0','0','0', '0', '0'};

    private char[] greenSpots = greenStart;

    private char[] redStart = {'r','r','r','r', '0','0','0','0','0','0','0', '0', '0'};

    private char[] redSpots = redStart;

    private int[] blueSpotsViewPositions = {R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5,
                R.id.b6, R.id.b7, R.id.b8, R.id.b9, R.id.b10, R.id.b11, R.id.b12};

    private int[] yellowSpotsViewPositions = {R.id.y0, R.id.y1, R.id.y2, R.id.y3, R.id.y4, R.id.y5,
            R.id.y6, R.id.y7, R.id.y8, R.id.y9, R.id.y10, R.id.y11, R.id.y12};

    private int[] greenSpotsViewPositions = {R.id.g0, R.id.g1, R.id.g2, R.id.g3, R.id.g4, R.id.g5,
            R.id.g6, R.id.g7, R.id.g8, R.id.g9, R.id.g10, R.id.g11, R.id.g12};

    private int[] redSpotsViewPositions = {R.id.r0, R.id.r1, R.id.r2, R.id.r3, R.id.r4, R.id.r5,
            R.id.r6, R.id.r7, R.id.r8, R.id.r9, R.id.r10, R.id.r11, R.id.r12};


    private int[] normalSpotsViewPositions = {R.id.s0, R.id.s1, R.id.s2, R.id.s3, R.id.s4, R.id.s5, R.id.s6, R.id.s7, R.id.s8, R.id.s9, R.id.s10,
                                                R.id.s11, R.id.s12, R.id.s13, R.id.s14, R.id.s15, R.id.s16, R.id.s17, R.id.s18, R.id.s19, R.id.s20,
                                                R.id.s21, R.id.s22, R.id.s23, R.id.s24, R.id.s25, R.id.s26, R.id.s27, R.id.s28, R.id.s29, R.id.s30,
                                                R.id.s31, R.id.s32, R.id.s33, R.id.s34, R.id.s35, R.id.s36, R.id.s37, R.id.s38, R.id.s39, R.id.s40,
                                                R.id.s41, R.id.s42, R.id.s43, R.id.s44, R.id.s45, R.id.s46, R.id.s47, R.id.s48, R.id.s49, R.id.s50,
                                                R.id.s51, R.id.s52, R.id.s53, R.id.s54, R.id.s55, R.id.s56, R.id.s57, R.id.s58, R.id.s59};

    final int TURN_ONE = 1;

    int playerIndex;

    private String currentCard;

    private boolean actionPhase = false;
    private boolean drawPhase = false;



    char[] normalBackup;
    char[] blueBackup;
    char[] yellowBackup;
    char[] greenBackup;
    char[] redBackup;





    public int[] getNormalSpotsViewPositions() {
        return normalSpotsViewPositions;
    }


    public void setNormalSpot(int indexToChange, char characterToChange ) {
        normalSpots[indexToChange] = characterToChange ;
    }

    public void setBlueSpot(int indexToChange, char characterToChange ) {
        blueSpots[indexToChange] = characterToChange ;
    }

    public void setYellowSpot(int indexToChange, char characterToChange ) {
        yellowSpots[indexToChange] = characterToChange;
    }

    public void setGreenSpot(int indexToChange, char characterToChange ) {
        greenSpots[indexToChange] = characterToChange ;
    }

    public void setRedSpot(int indexToChange, char characterToChange ) {
        redSpots[indexToChange] = characterToChange ;
    }

    public boolean getDrawPhase() {
        return drawPhase;
    }

    public void setDrawPhase(boolean drawPhase) {
        this.drawPhase = drawPhase;
    }

    public void setActionPhase(boolean actionPhase) {
        this.actionPhase = actionPhase;
    }

    public boolean getActionPhase() {
        return actionPhase;
    }





    //returns the name of the spot
    String getSpot(View v) {
        String spotName = v.getResources().getResourceName(v.getId());
        spotName = spotName.substring(20);
        return spotName;
    }

    public char determineHighlightChar(char[] array, int index) {
        if (array[index] == '0') {
            return 'h';
        }
        if (array[index] == 'b') {
            return 'B';
        }
        if (array[index] == 'y') {
            return 'Y';
        }
        if (array[index] == 'g') {
            return 'G';
        }
        if (array[index] == 'r') {
            return 'R';
        }
        //error code
        return 'j';
    }



    //returns what piece is at the spot
    char getSpotType(String clickedSpot) {
        char[] arrayToUse;
        arrayToUse = getArrayToUse(clickedSpot);
        return arrayToUse[getSpotIndex(clickedSpot)] ;
    }


    //overload for when weve already calculated which array to use
    char getSpotType(String clickedSpot, char[] arrayToUse) {
        arrayToUse = getArrayToUse(clickedSpot);
        return arrayToUse[getSpotIndex(clickedSpot)] ;
    }

    //returns the array of the spot that was clicked. Doesn't necessarily tell you the piece type.
    char[] getArrayToUse(String clickedSpot) {
        char [] empty = new char[0];
        if (clickedSpot.charAt(0) == 's') {
            //use normal spots
            return normalSpots;
        }
        if (clickedSpot.charAt(0) == 'b') {
            return blueSpots;
            //use blue spots
        }
        if (clickedSpot.charAt(0) == 'y') {
            return yellowSpots;
        }
        if (clickedSpot.charAt(0) == 'g') {
            return greenSpots;
        }
        if (clickedSpot.charAt(0) == 'r') {
            return redSpots;
        }
        return empty;
    }

    int getSpotIndex(String clickedSpot) {
        //gather index from the spot name
        return Integer.parseInt(clickedSpot.substring(1));
    }

    void highlightForSwap() {
        //switching logic
        for (int i = 0; i < normalSpots.length; i++) {

            if (playerTurn != 'b' && normalSpots[i] == 'b') {
                normalSpots[i] = 'B';
            }
            if (playerTurn != 'y' && normalSpots[i] == 'y') {
                normalSpots[i] = 'Y';
            }
            if (playerTurn != 'g' && normalSpots[i] == 'g') {
                normalSpots[i] = 'G';
            }
            if (playerTurn != 'r' && normalSpots[i] == 'r') {
                normalSpots[i] = 'R';
            }
        }
    }



    void drawCard() {
        Random random = new Random();
        int cardIndex = random.nextInt(11);
        currentCard = cards[cardIndex];
    }



    public String getCurrentCard() {
        return currentCard;
    }

    public char[] getBlueSpots() {
        return blueSpots;
    }

    public int[] getBlueSpotsViewPositions() {
        return blueSpotsViewPositions;
    }

    void switchPlayer() {
        playerIndex++;
        if (playerIndex == 4) {
            playerIndex = 0;
        }
        setPlayerTurn(players[playerIndex]);
    }

    char randomizePlayer() {
        Random random = new Random();
        playerIndex = random.nextInt(4);
        return players[playerIndex];
    }


    public char[] getYellowSpots() {
        return yellowSpots;
    }

    public char[] getGreenSpots() {
        return greenSpots;
    }

    public char[] getRedSpots() {
        return redSpots;
    }

    public char getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(char playerTurn) {
        this.playerTurn = playerTurn;
    }

    public int[] getYellowSpotsViewPositions() {
        return yellowSpotsViewPositions;
    }

    public int[] getGreenSpotsViewPositions() {
        return greenSpotsViewPositions;
    }

    public int[] getRedSpotsViewPositions() {
        return redSpotsViewPositions;
    }

    public void setArraySpot(char[] arrayToUse, int indexToChange, char replacementChar) {
        arrayToUse[indexToChange] = replacementChar;
    }
    public void setArraySpot(char[] arrayToUse, int indexToChange, int indexToChange1, char replacementChar, char replacementChar1) {
        arrayToUse[indexToChange] = replacementChar;
        arrayToUse[indexToChange1] = replacementChar1;
    }


    char [] findWhichHome(char destinationPieceType) {
        if (destinationPieceType == 'B' || destinationPieceType == 'b') {
            return blueSpots;
        }
        if (destinationPieceType == 'Y' || destinationPieceType == 'y') {
            return yellowSpots;
        }
        if (destinationPieceType == 'G' || destinationPieceType == 'g') {
            return greenSpots;
        }
        if (destinationPieceType == 'R' || destinationPieceType == 'r') {
            return redSpots;
        }
        return blueSpots;
    }

    public void backupBoardState() {
        normalBackup = normalSpots.clone();
        blueBackup = blueSpots.clone();
        yellowBackup = yellowSpots.clone();
        greenBackup = greenSpots.clone();
        redBackup = redSpots.clone();
    }

    public void setBoardFromBackup() {
        normalSpots = normalBackup.clone();
        blueSpots = blueBackup.clone();
        yellowSpots = yellowBackup.clone();
        greenSpots = greenBackup.clone();
        redSpots = redBackup.clone();
    }
}
