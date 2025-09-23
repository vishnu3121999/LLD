package org.example.model;

public class Board {
    Cell[][] board;
    static Board instance;

    private Board(){
        board = new Cell[8][8];
        int color = 1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell cell = new Cell(i,j,color,null);
                color = 1- color;
                board[i][j]=cell;
            }
        }
    }
    public static Board getInstance(){
        if(instance==null){
            instance = new Board();
        }
        return instance;
    }

    public Cell getCell(int x, int y){
        return board[x][y];
    }

    public void setCell(int x, int y, Piece piece){
        board[x][y].setPiece(piece);
    }

    public void displayBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                var piece = board[i][j].getPiece();
                if(piece==null){
                    System.out.println("       ");
                }
                else System.out.print(""+piece.color+piece.pieceType+"  ");
            }
            System.out.println();
        }
    }

}
