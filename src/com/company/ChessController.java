package com.company;

import com.company.Pieces.Pawn;

import java.awt.*;
import java.awt.event.*;

public class ChessController implements MouseListener, MouseMotionListener {
    ChessModel model;
    ChessView view;
    Piece selectedPiece = null;

    public ChessController() {
    }

    public ChessController(ChessModel model, ChessView view) {
        this.model = model;
        this.view = view;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int xCase = (int) Math.ceil((e.getX() / (double) Settings.CASE_SIZE));
        int yCase = (int) Math.ceil((e.getY() / (double) Settings.CASE_SIZE));
        System.out.println("CLICK : X : "+xCase+" Y : "+yCase);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        selectedPiece = model.getPieceByCoords(e.getX(), e.getY());
        if(selectedPiece != null && selectedPiece instanceof Pawn && selectedPiece.color == Color.WHITE){
            selectedPiece.nextPossibleMoves(model);
            model.setNextPossiblesMovesCasesHinted(selectedPiece.nextMoves);
        }
        if(selectedPiece != null && selectedPiece instanceof Pawn && selectedPiece.color == Color.BLACK){
            Piece p;
            if((p = model.getPieceByCase(selectedPiece.xCase, selectedPiece.yCase+1)) == null){
                // avance
            } else {
                // bloqué
            }
            if((p = model.getPieceByCase(selectedPiece.xCase+1, selectedPiece.yCase+1)) != null && p.color == Color.WHITE){
                //attaque
            }
            if((p = model.getPieceByCase(selectedPiece.xCase-1, selectedPiece.yCase+1)) != null && p.color == Color.WHITE){
                //attaque
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if(selectedPiece != null){
            model.place(selectedPiece, e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(selectedPiece != null){
            model.move(selectedPiece, (e.getX()), (e.getY()));
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(model.getPieceByCoords(e.getX(), e.getY()) != null){
            this.view.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }else{
            this.view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
