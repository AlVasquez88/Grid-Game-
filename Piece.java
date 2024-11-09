package gridgame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**This program contains the functionality of the red and blue pieces.
 * @author Alvin Vasquez 000857238
 * date: August 7, 2024
 * @version Piece.java*/
public abstract class Piece {
    /**@param row assigned as a protected integer variable for the pieces to move in the grid row of the board class.*/
    protected int row;
    /**@param column allows the piece to move horizontally.*/
    protected int column;
    /**@param color assigns the colors for the red and blue pieces.*/
    protected Color color;
    /**@param selected for when the piece is selected to move to another space. It is false by default.*/
    protected boolean selected = false;

    /**Constructor Piece method.*/
    public Piece(int row, int column, Color color){
        this.row = row;
        this.column = column;
        this.color = color;
    }
    /**getter method getRow that returns the instance row variable*/
    public int getRow(){
        return row;
    }
    /**getter method getColumn that returns the instance column variable*/
    public int getColumn(){
        return column;
    }
    /**setter method setSelected that uses the protected select variable*/
    public void setSelected(boolean selected){
        this.selected = selected;
    }

    /**setter method to set the position of a piece.*/
    public void setPosition(int row, int column){
        this.row = row;
        this.column = column;
    }

    /**draw method for implementing the illustrations of the red and blue pieces*/
    public abstract void draw(GraphicsContext gc);

    /**drawSelected method designs the oval shape size and placement of the piece*/
    public void drawSelected(GraphicsContext gc){
        gc.setFill(Color.GREEN);
        gc.fillOval(column * 40, row * 40, 40, 40);
    }

}
