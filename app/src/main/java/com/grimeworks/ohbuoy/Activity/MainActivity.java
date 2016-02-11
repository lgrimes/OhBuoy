package com.grimeworks.ohbuoy.Activity;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.grimeworks.ohbuoy.Managers.BoardManager;
import com.grimeworks.ohbuoy.Managers.ShipManager;
import com.grimeworks.ohbuoy.Models.Board;
import com.grimeworks.ohbuoy.Models.Move;
import com.grimeworks.ohbuoy.Models.Ship;
import com.grimeworks.ohbuoy.R;
import com.grimeworks.ohbuoy.Utils.GameUtils;

public class MainActivity extends AppCompatActivity {

    private static final boolean AUTO_HIDE = true;
    private RelativeLayout gameBoard;
    private RelativeLayout controlPanel;
    private Button submitMove;
    private Button reportLocation;
    private EditText userInputEditText;
    private Board board;
    private View mainView;
    private int squareSize;
    private ImageView shipIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        mainView = this.findViewById(android.R.id.content);
        gameBoard = (RelativeLayout) mainView.findViewById(R.id.board_layout);
        ViewTreeObserver viewTreeObserver = mainView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mainView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    squareSize = mainView.getWidth() / GameUtils.BOARD_SIZE;
                    gameBoard.setLayoutParams(new RelativeLayout.LayoutParams(mainView.getWidth(), mainView.getWidth()));
                }
            });
        }
        controlPanel = (RelativeLayout) mainView.findViewById(R.id.control_panel_layout);
        userInputEditText = (EditText) mainView.findViewById(R.id.input_edit_text);
        submitMove = (Button) mainView.findViewById(R.id.submit_move_button);
        submitMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserMove();
            }
        });
        reportLocation = (Button) mainView.findViewById(R.id.report_coords_button);
        reportLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, ShipManager.reportShipCurrentLocation(board.getShip()), Toast.LENGTH_SHORT).show();
            }
        });
        shipIcon = (ImageView) mainView.findViewById(R.id.icon);
        startGame();
    }
    private void startGame(){
        Location dockLocation = new Location("SSReckoning");
        dockLocation.setLatitude(GameUtils.DOCK_START);
        dockLocation.setLongitude(GameUtils.DOCK_START);
        //Start a new board with a dock location and the ship starting at the dock
        board = new Board(new Ship(dockLocation,GameUtils.EAST), dockLocation);
    }

    public void getUserMove() {
        Move move = GameUtils.parseUserInputToMove(userInputEditText.getText().toString());
        if (move != null){
            //Parsing the move was successful
            BoardManager.moveShipOnBoard(board, move);
            animateShip(move);
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Move Forward: F\n" +
                    "Move Backward:B\n" +
                    "Rotate Left: L\n" + "Rotate Right: R\n" +
                    "Add the number of spaces (1-4) directly after the move.")
                    .setTitle("Game Rules").setPositiveButton("Aye aye, Cpt'n", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog1 = dialog.create();
            dialog1.show();
        }
    }

    public void animateShip(Move move) {
        if (move.getDirection() == GameUtils.LEFT_MOVE ||
                move.getDirection() == GameUtils.RIGHT_MOVE) {
            //Rotate the icon
            //icon is already 90* out
            shipIcon.setRotation((float) board.getShip().getCurrentDirection() - (float)GameUtils.TURN_ANGLE);
        } else if (board.getShip().getCurrentDirection() == GameUtils.EAST ||
                board.getShip().getCurrentDirection() == GameUtils.WEST) {
            try {
                if (board.getShip().getCurrentLocation().getLatitude() < 10
                        && board.getShip().getCurrentLocation().getLatitude() > 0) {
                    final ObjectAnimator oa = ObjectAnimator.ofFloat(shipIcon, "x",
                            (float) board.getShip().getCurrentLocation().getLatitude() * squareSize);
                    oa.setDuration(2000);
                    oa.start();
                }
            } catch (Exception ex) {
                return;
            }
        } else {
            try {
                if (board.getShip().getCurrentLocation().getLongitude() < 10 &&
                        board.getShip().getCurrentLocation().getLongitude() > 0) {
                    final ObjectAnimator oa = ObjectAnimator.ofFloat(shipIcon, "y",
                            (float) board.getShip().getCurrentLocation().getLongitude() * squareSize);
                    oa.setDuration(2000);
                    oa.start();
                }
            } catch (Exception ex){
                return;
            }
        }
    }
}
