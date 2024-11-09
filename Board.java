package gridgame;
/**This program is the board that holds all the pieces together.
 * The user can manually add or move red and blue pieces and remove any piece using the graphical user interface.
 * @author Alvin Vasquez 000857238
 * date: August 7, 2024
 * @version Piece.java*/
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;


public class Board extends Application {
    /**@param GRID_SIZE constains the number of spaces in the board */
    private static final int GRID_SIZE = 10;
    /**@param CELL_SIZE  is the size of each space in terms of height and width*/
    private static final int CELL_SIZE = 40;
    /**@param CANVAS_SIZE holds both the grid and the cells together.*/
    private static final int CANVAS_SIZE = GRID_SIZE * CELL_SIZE;

    /**@param Piece[][] interacts the pieces with the board using 2D array */
    private Piece[][] board;
    /**@param List<Piece></Piece> Keeps track of all the pieces on the board.*/
    private List<Piece> pieces;
    /**@param selectedPiece selects any piece on the board. It is set to null by default.*/
    private Piece selectedPiece = null;
    /**@param playerOne allows the user to play on the first turn. It is set to true by default.*/
    private boolean playerOne = true;
    /**@param canvas is what displays the entire board.*/
    private Canvas canvas;
    /**@param rowText is to input the vertical row location of the piece to remove.*/
    private TextField rowText;
    /**@param columnText is to input the horizontal column location of the piece to remove.*/
    private TextField columnText;

    //launching application
    public static void main(String[] args) {
        launch(args);
    }
    //starting the stage of the application
    @Override
    public void start(Stage primaryStage){
        board = new Piece[GRID_SIZE][GRID_SIZE];
        pieces = new ArrayList<>();


        BorderPane root = new BorderPane();
        canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        drawGrid();

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleMouseClick);

        /**controls for inputting piece location and using
         * @param removeButton to remove the piece.*/
        VBox controls = new VBox();
        rowText = new TextField();
        rowText.setPromptText("Row");

        columnText = new TextField();
        columnText.setPromptText("Column");
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removePiece());


        controls.getChildren().addAll(rowText, columnText, removeButton);//root of the GUI

        //application positioning
        root.setCenter(canvas);
        root.setLeft(controls);

        //Canvas size & title
        Scene scene = new Scene(root, CANVAS_SIZE + 200, CANVAS_SIZE + 150);
        primaryStage.setScene(scene);
        primaryStage.setTitle("-:B O A R D  G A M E:-");
        primaryStage.show();
    }

    /**handleMouseClick method for adding piece on the board and
     * selecting a piece to move*/
    private void handleMouseClick(MouseEvent event){
        int column = (int) (event.getX()/CELL_SIZE);
        int row = (int) (event.getY()/CELL_SIZE);


        if (selectedPiece == null){
            if (board[row][column] == null){
                addPiece(row,column);
            } else{
                selectPiece(row, column);
            }
        } else{
            movePiece(row, column);
        }
    }

    /**selectPiece method for when the user selects a piece on the board*/
    private void selectPiece(int row, int column){
        if(board[row][column] !=null){
            selectedPiece = board[row][column];
            selectedPiece.setSelected(true);
            redraw();
        }
    }

    /**addPiece method for when the user clicks on a space to add a piece on the board.*/
    private void addPiece(int row, int column){
        if (board[row][column] !=null){
            showAlert("Cell is already occupied!");
            return;
        }

        Piece piece;
        if (playerOne){
            piece = new RedPiece(row, column);
        } else{
            piece = new BluePiece(row, column);
        }

        board[row][column] = piece;
        pieces.add(piece);
        playerOne = !playerOne;
        redraw();
    }
    /**movePiece method allows the user to select and move a piece.
     * if the user places the piece in an occupied space, an alert message will appear.*/
    private void movePiece(int row, int column){
        if(board[row][column] != null){
            showAlert("Cell is already occupied!");
            return;
        }

        board[selectedPiece.getRow()][selectedPiece.getColumn()] = null;
        selectedPiece.setSelected(false);
        selectedPiece.setPosition(row, column);
        board[row][column] = selectedPiece;
        selectedPiece = null;
        redraw();
    }

    /**removePiece method ensures that the user must remove a piece properly.*/
    private void removePiece(){
        try{
            int row = Integer.parseInt(rowText.getText());
            int column = Integer.parseInt(columnText.getText());


            if(row<0 || row>=GRID_SIZE || column<0 || column>=GRID_SIZE){
                showAlert("Wrong position!");
                return;
            }


            if (board[row][column] == null){
                showAlert("There is no piece in this space!");
                return;
            }

            pieces.remove(board[row][column]);
            board[row][column] = null;
            redraw();
        } catch(NumberFormatException e){
            showAlert("Not a number! Please enter valid numbers.");
        }
    }
    /**drawGrid method for designing and illustrating the board.*/
    private void drawGrid() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);

        //setting background color to goldenrod
        gc.setFill(Color.GOLDENROD);
        gc.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);

        //drawing the grid pattern
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if ((row + column) % 2 == 0) {
                    gc.setFill(Color.BURLYWOOD);
                } else {
                    gc.setFill(Color.GOLDENROD);
                }
                gc.fillRect(column * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        //Borderline color
        gc.setStroke(Color.BLACK);
        for (int i = 0; i <= GRID_SIZE; i++) {
            gc.strokeLine(i * CELL_SIZE, 0, i * CELL_SIZE, CANVAS_SIZE);
            gc.strokeLine(0, i * CELL_SIZE, CANVAS_SIZE, i * CELL_SIZE);
        }

    }
    /**redraw method redraws the board whenever the user interacts with board and the pieces.*/
    private void redraw(){
        drawGrid();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for(Piece piece : pieces) {
            piece.draw(gc);
        }
    }

    /**showAlert method loads a window as a warning message.*/
    private void showAlert(String message){
        new Alert(Alert.AlertType.WARNING, message).showAndWait();
    }

}
