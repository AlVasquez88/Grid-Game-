package gridgame;
/**This program contains the design of the Blue Piece.
 * @author Alvin Vasquez 000857238
 * date: August 7, 2024
 * @version BluePiece.java*/
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class BluePiece extends Piece{
    /**Inheriting the variables from @version Piece.java to the constructor */
    public BluePiece(int row, int column){
        super(row, column, Color.BLUE);
    }
    /**Drawing the blue piece*/
    @Override
    public void draw(GraphicsContext gc){
        if(selected){
            drawSelected(gc);
        } else{
            gc.setFill(color);
            gc.fillOval(column * 40,row * 40, 40, 40);
        }
    }
}
