package edu.up.cs371.resop18.shogi.shogi;

public class legalMoves {
    public int[][] moves(shogiPiece[][] board, shogiPiece piece){
        int[][] moves = new int[16][];
        
        int row = 0;
        int col = 0;
        int i = 0;
        
        int xPos, yPos;
        
        for(row = 0; row < board.length; row++){
            for(col = 0; col < board[row].length; col++){
                if(board[row][col] == piece){
                    break;
                }
            }
        }
        
        if(piece == null){
            return moves;
        }
        
        if(piece.getPiece().equals("Pawn")){
            if(row != 0){
                if(piece.getPlayer()){
                    moves[0] = new int[]{row-1, col};
                }else{
                    moves[0] = new int[]{row+1, col};
                }
            }
        }else if(piece.getPiece().equals("Rook")){
            i = 0;
            
            xPos = piece.getX();
            yPos = piece.getY() - (int)ShogiGui.spaceDim;
            while(yPos > ShogiGui.topLeftY){
                yPos -= (int)ShogiGui.spaceDim;
                moves[i] = new int[]{xPos, yPos};
                i++;
            }
            
            yPos = piece.getY() + (int)ShogiGui.spaceDim;
            while(yPos < ShogiGui.topLeftY + 9*ShogiGui.spaceDim){
                yPos += ShogiGui.spaceDim;
                moves[i] = new int[] {xPos, yPos};
                i++;
            }
            
            yPos = piece.getY();
            xPos = piece.getX() - (int)ShogiGui.spaceDim;
            while(xPos > ShogiGui.topLeftX){
                xPos -= (int)ShogiGui.spaceDim;
                moves[i] = new int[] {xPos, yPos};
                i++;
            }
            
            xPos = piece.getX() + (int)ShogiGui.spaceDim;
            while(xPos < ShogiGui.topLeftX + 9*ShogiGui.spaceDim){
                xPos += (int)ShogiGui.spaceDim;
                moves[i] = new int[] {xPos, yPos};
                i++;
            }
        }else if(piece.getPiece().equals("Lance")){
            xPos = piece.getX();
            if(piece.getPlayer()){
                i = 0;
                yPos = piece.getY() - (int)ShogiGui.spaceDim;
                while(yPos > ShogiGui.topLeftY){
                    yPos -= (int)ShogiGui.spaceDim;
                    moves[i] = new int[] {xPos, yPos};
                }
            }else{
                i = 0;
                yPos = piece.getY() + (int)ShogiGui.spaceDim;
                while(yPos < ShogiGui.topLeftY + 9*ShogiGui.spaceDim){
                    yPos += (int)ShogiGui.spaceDim;
                    moves[i] = new int[] {xPos, yPos};
                }
            }
        }else{
            moves[0] = new int[]{row, col};
        }
        
        return moves; 
    }
}