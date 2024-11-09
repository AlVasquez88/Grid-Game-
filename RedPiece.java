package gridgame;
/**This program constains the design of the red piece.
 * @author Alvin Vasquez 000857238
 * date: August 7, 2024
 * @version RedPiece.java
 **/
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RedPiece extends Piece {

    /**Inheriting the variables from @version Piece.java into the constructor*/
    public RedPiece(int row, int column){
        super(row, column, Color.RED);
    }
    /**Drawing the red piece*/
    @Override
    public void draw(GraphicsContext gc){
        if(selected){
            drawSelected(gc);
        } else{
            gc.setFill(color);
            gc.fillOval(column * 40, row * 40, 40,40);
        }
    }
}
