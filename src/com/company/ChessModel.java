package com.company;


import com.company.Pieces.*;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class ChessModel {
    LinkedList<Piece> pieces = new LinkedList<>();
    AutreEventNotifieur notifieur = new AutreEventNotifieur();
    LinkedList<Case> cases = new LinkedList<>();

    // a faire methodes move qui notifie la vue
    public ChessModel() {

        pieces.add(new Pawn(1, 2, Color.BLACK));
        pieces.add(new Pawn(2, 2, Color.BLACK));
        pieces.add(new Pawn(3, 2, Color.BLACK));
        pieces.add(new Pawn(4, 2, Color.BLACK));
        pieces.add(new Pawn(5, 2, Color.BLACK));
        pieces.add(new Pawn(6, 2, Color.BLACK));
        pieces.add(new Pawn(7, 2, Color.BLACK));
        pieces.add(new Pawn(8, 2, Color.BLACK));

        pieces.add(new Rook(1, 1, Color.BLACK));
        pieces.add(new Knight(2, 1, Color.BLACK));
        pieces.add(new Bishop(3, 1, Color.BLACK));
        pieces.add(new Queen(4, 1, Color.BLACK));
        pieces.add(new King(5, 1, Color.BLACK));
        pieces.add(new Bishop(6, 1, Color.BLACK));
        pieces.add(new Knight(7, 1, Color.BLACK));
        pieces.add(new Rook(8, 1, Color.BLACK));

        pieces.add(new Pawn(1, 6, Color.WHITE));
        pieces.add(new Pawn(2, 6, Color.WHITE));
        pieces.add(new Pawn(3, 6, Color.WHITE));
        pieces.add(new Pawn(4, 6, Color.WHITE));
        pieces.add(new Pawn(5, 6, Color.WHITE));
        pieces.add(new Pawn(6, 6, Color.WHITE));
        pieces.add(new Pawn(7, 6, Color.WHITE));
        pieces.add(new Pawn(8, 6, Color.WHITE));

        pieces.add(new Rook(1, 7, Color.WHITE));
        pieces.add(new Knight(2, 7, Color.WHITE));
        pieces.add(new Bishop(3, 7, Color.WHITE));
        pieces.add(new Queen(4, 7, Color.WHITE));
        pieces.add(new King(5, 7, Color.WHITE));
        pieces.add(new Bishop(6, 7, Color.WHITE));
        pieces.add(new Knight(7, 7, Color.WHITE));
        pieces.add(new Rook(8, 7, Color.WHITE));

        // Create board cases
        boolean colorTest = true;
        for (int y = 0; y < Settings.HEIGHT_CASES; y++) {
            for (int x = 0; x < Settings.WIDTH_CASES; x++) {
                if (colorTest) {
                    cases.add(new Case(x,y,Settings.CASE_COLOR1));
                } else {
                    cases.add(new Case(x,y,Settings.CASE_COLOR2));
                }
                colorTest = !colorTest;
            }
            colorTest = !colorTest;
        }

        for (Piece piece : pieces) {
            Case c = getCaseByCaseCoords(piece.xCase, piece.yCase);
            if(c != null)
                c.setPiece(piece);
        }

    }

    public void move(Piece piece, int x, int y) {


        for (Case c : cases) {
            c.setHovered(false);
        }
        Case c = getCaseByCaseCoords(
                (int) Math.ceil((x / (double) Settings.CASE_SIZE)),
                (int) Math.ceil((y / (double) Settings.CASE_SIZE))
        );
        if(c != null)
            c.setHovered(true);

        piece.coords.x = x - (Settings.CASE_SIZE*0.5208); //
        piece.coords.y = y - (Settings.CASE_SIZE*0.6770);
        notifieur.diffuserAutreEvent(new AutreEvent(this, piece));
    }

    public void place(Piece piece, int x, int y){
        Piece p;
        if((p = getPieceByCoords(x,y)) != null){

            if(canMovePiece(p, piece)){
            }

            if(p.color != piece.color){
                //remove piece from case who has it
                getCaseByCaseCoords(p.xCase, p.yCase).setPiece(null);
                pieces.remove(p);
                if(p instanceof King){
                    //TODO WIN
                }
                // TODO Add piece to player
            }
        }
        Case ca = getCaseByCaseCoords(piece.xCase, piece.yCase);
        if(ca != null)
            ca.setPiece(null);
        piece.xCase = (int) Math.ceil((x / (double) Settings.CASE_SIZE));
        piece.yCase = (int) Math.ceil((y / (double) Settings.CASE_SIZE));
        ca = getCaseByCaseCoords(piece.xCase, piece.yCase);
        if(ca != null)
            ca.setPiece(piece);

        piece.coords.x = (piece.xCase - 1)*((double)Settings.REAL_WIDTH/Settings.WIDTH_CASES);
        piece.coords.y = (piece.yCase - 1)*((double)Settings.REAL_HEIGHT/Settings.HEIGHT_CASES);
        notifieur.diffuserAutreEvent(new AutreEvent(this, "place"));
        pieceSound();
        for (Case c : cases) {
            c.setHovered(false);
            c.setHinted(false);
        }
    }

    public Piece getPieceByCoords(int x, int y) {
        int xCase = (int) Math.ceil((x / (double) Settings.CASE_SIZE));
        int yCase = (int) Math.ceil((y / (double) Settings.CASE_SIZE));
        for (Piece piece : pieces) {
            if(piece.xCase == xCase && piece.yCase == yCase){
                return piece;
            }
        }
        return null;
    }

    public Piece getPieceByCase(int xCase, int yCase){
        for (Piece piece : pieces) {
            if(piece.xCase == xCase && piece.yCase == yCase){
                return piece;
            }
        }
        return null;
    }

    public Case getCaseByCaseCoords(int xCase, int yCase){
        for (Case c : cases) {
            if(c.getX() == xCase-1 && c.getY() == yCase-1){
                return c;
            }
        }
        return null;
    }

    public void setNextPossiblesMovesCasesHinted(LinkedList<Coordinates> nextPossiblesMoves){
        for(Coordinates c : nextPossiblesMoves){
            Case ca = getCaseByCaseCoords((int) c.x, (int) c.y);
            if(ca != null){
                ca.setHinted(true);
            }
        }
    }

    public boolean canMovePiece(Piece next, Piece now){
        boolean response = false;
        Case c = getCaseByCaseCoords(next.xCase, next.yCase);
        if(c != null){
            for(Coordinates cd : now.nextMoves){
                if(c == getCaseByCaseCoords((int) cd.x, (int) cd.y)){
                    response = true;
                }
            }
        }
        return response;
    }

    public void pieceSound(){
        File file = new File("resources/piece_sound.wav");
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException | IOException unsupportedAudioFileException) {
            unsupportedAudioFileException.printStackTrace();
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException lineUnavailableException) {
            lineUnavailableException.printStackTrace();
        }
        try {
            assert clip != null;
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException lineUnavailableException) {
            lineUnavailableException.printStackTrace();
        }
        clip.start();
    }

}