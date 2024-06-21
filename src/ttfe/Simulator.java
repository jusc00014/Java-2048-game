package ttfe;

import java.util.Random;

public class Simulator implements SimulatorInterface {

    int w;
    int h;
    Random r;
    int mov;
    int p;
    int[] [] board;

    public Simulator(int width, int height, Random r){
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("The dimensions must be positive");
        }
        this.w = width;
        this.h = height;
        this.r = r;
        this.mov = 0;
        this.p = 0;
        this.board = new int[width][height];
        addPiece();
        addPiece();
    }

    @Override
    public void addPiece() {
        if (!this.isSpaceLeft()) {
            throw new IllegalStateException("The board is full");
        }
        int x = r.nextInt(this.w);
        int y = r.nextInt(this.h);
        int v = r.nextInt(10);
        if (v  == 0) {
            v = 4;
        } else {
            v = 2;
        }
        if (this.board[x][y] == 0) {
            this.board[x][y] = v;
        } else {
            addPiece();
        }
        //throw new UnsupportedOperationException("Unimplemented method 'addPiece'");
    }

    @Override
    public int getBoardHeight() {
        return(this.h);
        //throw new UnsupportedOperationException("Unimplemented method 'getBoardHeight'");
    }

    @Override
    public int getBoardWidth() {
        return(this.w);
        //throw new UnsupportedOperationException("Unimplemented method 'getBoardWidth'");
    }

    @Override
    public int getNumMoves() {
        return(mov); 
        //throw new UnsupportedOperationException("Unimplemented method 'getNumMoves'");
    }

    @Override
    public int getNumPieces() {
        int n = 0;
        for (int i = 0; i < this.w; i++) {
            for (int j = 0; j < this.h; j++) {
                if (!(this.board[i][j] == 0)){
                    n++;
                }
            }
        }
        return(n);
        //throw new UnsupportedOperationException("Unimplemented method 'getNumPieces'");
    }

    @Override
    public int getPieceAt(int x, int y) {
        if (x >= this.w || y >= this.h || x < 0 || y < 0) {
            throw new IllegalArgumentException("The coordinates must be inside the field");
        }
        return(this.board[x][y]);
        //throw new UnsupportedOperationException("Unimplemented method 'getPieceAt'");
    }

    @Override
    public int getPoints() {
        return(this.p);
       // throw new UnsupportedOperationException("Unimplemented method 'getPoints'");
    }

    @Override
    public boolean isMovePossible() {
        if (getNumPieces() < this.h*this.w) {
            return (true);
        }
        return(isMovePossible(MoveDirection.WEST) || isMovePossible(MoveDirection.EAST) || isMovePossible(MoveDirection.SOUTH) || isMovePossible(MoveDirection.NORTH));
        //throw new UnsupportedOperationException("Unimplemented method 'isMovePossible'");
    }

    @Override
    public boolean isMovePossible(MoveDirection direction) {
        switch (direction) {
            case MoveDirection.WEST: {
                for (int i = 0; i < this.h; i++) {
                    for (int j = (this.w-1); j > (0); j--) {
                        if (this.board[j][i] != 0 && (this.board[j-1][i] == 0 || this.board[j][i] == this.board[j-1][i]))
                            return(true);
                    }
                }
                return(false);
            }
            case MoveDirection.EAST: {
                for (int i = 0; i < this.h; i++) {
                    for (int j = 0; j < (this.w-1); j++) {
                        if (this.board[j][i] != 0 && (this.board[j+1][i] == 0 || this.board[j][i] == this.board[j+1][i]))
                            return(true);
                    }
                }
                return(false);
            }
            case MoveDirection.SOUTH: {
                for (int j = 0; j < (this.w); j++) {
                    for (int i = 0; i < (this.h-1); i++) {
                        if (this.board[j][i] != 0 && (this.board[j][i+1] == 0 || this.board[j][i] == this.board[j][i+1]))
                            return(true);
                    }
                }
                return(false);
            }
            case MoveDirection.NORTH: {
                for (int j = 0; j < (this.w); j++) {
                    for (int i = (this.h-1); i > 0; i--) {
                        if (this.board[j][i] != 0 && (this.board[j][i-1] == 0 || this.board[j][i] == this.board[j][i-1]))
                            return(true);
                    }
                }
                return(false);
            }
            default:
                throw new IllegalArgumentException("The direction is not valid");
        }
        //throw new UnsupportedOperationException("Unimplemented method 'isMovePossible'");
    }

    @Override
    public boolean isSpaceLeft() {
        return(getNumPieces() < this.h*this.w);
        //throw new UnsupportedOperationException("Unimplemented method 'isSpaceLeft'");
    }

    @Override
    public boolean performMove(MoveDirection direction) {
        if(direction != MoveDirection.WEST && direction != MoveDirection.EAST && direction != MoveDirection.NORTH && direction != MoveDirection.SOUTH) {
            throw new IllegalArgumentException("The direction is not valid");
        }
        if(!isMovePossible(direction)){
            return(false);
        }
        boolean b = false;
        switch (direction) {
            case MoveDirection.NORTH: {
                b = northmerge();
                break;
            }
            case MoveDirection.SOUTH: {
                b = southmerge();
                break;
            }
            case MoveDirection.EAST: {
                b = eastmerge();
                break;
            }
            case MoveDirection.WEST: {
                b = westmerge();
                break;
            }
            default: {
                throw new IllegalArgumentException("The direction is not valid");
            }
        }
        return(b);
        //throw new UnsupportedOperationException("Unimplemented method 'performMove'");
    }

    boolean northmerge() {
        int k = 0;
        for (int i = 0; i < this.w; i++) {
            k = 0;
            int v = 0;
            for (int j = 0; j < this.h; j++) {
                if (this.board[i][j] != 0){
                    if (this.board[i][j] == v){
                        this.board[i][j] = 0;
                        this.board[i][k-1] *= 2;
                        this.p += this.board[i][k-1];
                        v = 0;
                    } else {
                        v  = this.board[i][j];
                        if (j != k) {
                            this.board[i][k] = this.board[i][j];
                            this.board[i][j] = 0;
                        }
                        k++;
                    }
                }
                /*
                if (this.board[i][j] != 0 && this.board[i][j] == v) {
                    this.board[i][j] = 0;
                    this.board[k-1][i] *= 2;
                    this.p += this.board[k-1][i];
                    v = 0;
                } else if (this.board[i][j] != 0) {
                    v = this.board[i][j];
                    this.board[k][i] = this.board[i][j];
                    if (j != k) {
                        this.board[i][j] = 0;
                    }
                    k++;
                }*/
            }
        }
        return(true);
    }


    boolean southmerge() {
        int k = this.h-1;
        for (int i = 0; i < this.w; i++) {
            k = this.h-1;
            int v = 0;
            for (int j = this.h-1; j > -1; j--) {
                if (this.board[i][j] != 0){
                    if (this.board[i][j] == v){
                        this.board[i][j] = 0;
                        this.board[i][k+1] *= 2;
                        this.p += this.board[i][k+1];
                        v = 0;
                    } else {
                        v  = this.board[i][j];
                        if (j != k) {
                            this.board[i][k] = this.board[i][j];
                            this.board[i][j] = 0;
                        }
                        k--;
                    }
                }

            }
        }
        return(true);
    }

    boolean westmerge() {
        int k = 0;
        for (int i = 0; i < this.h; i++) {
            k = 0;
            int v = 0;
            for (int j = 0; j < this.w; j++) {
                if (this.board[j][i] != 0){
                    if (this.board[j][i] == v){
                        this.board[j][i] = 0;
                        this.board[k-1][i] *= 2;
                        this.p += this.board[k-1][i];
                        v = 0;
                    } else {
                        v  = this.board[j][i];
                        if (j != k) {
                            this.board[k][i] = this.board[j][i];
                            this.board[j][i] = 0;
                        }
                        k++;
                    }
                }
            }
        }
        return(true);
    }

    boolean eastmerge() {
        int k = this.w-1;
        for (int i = 0; i < this.h; i++) {
            k = this.w-1;
            int v = 0;
            for (int j = this.w-1; j > -1; j--) {
                if (this.board[j][i] != 0){
                    if (this.board[j][i] == v){
                        this.board[j][i] = 0;
                        this.board[k+1][i] *= 2;
                        this.p += this.board[k+1][i];
                        v = 0;
                    } else {
                        v  = this.board[j][i];
                        if (j != k) {
                            this.board[k][i] = this.board[j][i];
                            this.board[j][i] = 0;
                        }
                        k--;
                    }
                }
            }
        }
        return(true);
    }


/*
    @Override
    public boolean performMove(MoveDirection direction) {
        if(direction != MoveDirection.WEST && direction != MoveDirection.EAST && direction != MoveDirection.NORTH && direction != MoveDirection.SOUTH) {
            throw new IllegalArgumentException("The direction is not valid");
        }
        if(!isMovePossible(direction)){
            return(false);
        }
        slide(direction);
        boolean b = merge(direction);
        if (b) {
            slide(direction);
        }
        return(b);
        //throw new UnsupportedOperationException("Unimplemented method 'performMove'");
    }

    boolean merge(MoveDirection direction) {
        switch (direction) {
            case MoveDirection.WEST: {
                for (int i = 0; i < this.h; i++) {
                    for (int j = (this.w-1); j > (0); j--) {
                        if (this.board[i][j] != 0 && (this.board[i][j] == this.board[i][j-1]))
                            this.board[i][j-1] *= 2;
                            this.board[i][j] = 0;
                            this.p += this.board[i][j-1];
                    }
                }

                return(true);
            }
            case MoveDirection.EAST: {
                for (int i = 0; i < this.h; i++) {
                    for (int j = 0; j < (this.w-1); j++) {
                        if (this.board[i][j] != 0 && (this.board[i][j] == this.board[i][j+1]))
                            this.board[i][j+1] *= 2;
                            this.board[i][j] = 0;
                    }
                }
                return(true);
            }
            case MoveDirection.SOUTH: {
                for (int j = 0; j < (this.w); j++) {
                    for (int i = 0; i < (this.h-1); i++) {
                        if (this.board[i][j] != 0 && (this.board[i][j] == this.board[i+1][j]))
                            this.board[i+1][j] *= 2;
                            this.board[i][j] = 0;
                    }
                }
                return(true);
            }
            case MoveDirection.NORTH: {
                for (int j = 0; j < (this.w); j++) {
                    for (int i = (this.h-1); i > 0; i--) {
                        if (this.board[i][j] != 0 && (this.board[i-1][j] == 0 || this.board[i][j] == this.board[i-1][j]))
                            this.board[i-1][j] *= 2;
                            this.board[i][j] = 0;
                    }
                }
                return(true);
            }
            default:
                throw new IllegalArgumentException("The direction is not valid");
        }
    }

    void slide(MoveDirection direction) {
        int k;
        switch (direction){
            case MoveDirection.NORTH: {
                for (int i = 0; i < this.h; i++){
                    k = 0;
                    for (int j = 0; j < this.w; j++) {
                        if (this.board[i][j] != 0){
                            if (k != j) {
                                this.board[i][k] = this.board[i][j];
                                this.board[i][j] = 0;
                            }
                            k++;
                        }
                    }
                }
                break;
            }
            case MoveDirection.SOUTH: {
                for (int i = 0; i < this.h; i++){
                    k = this.w-1;
                    for (int j = (this.w-1); j > -1; j--) {
                        if (this.board[i][j] != 0){
                            if(k != j) {
                                this.board[i][k] = this.board[i][j];
                                this.board[i][j] = 0;
                            }
                            k--;
                        }
                    }
                }
                break;
            }
            case MoveDirection.WEST: {
                for (int j = 0; j < this.w; j++){
                    k = 0;
                    for (int i = 0; i < this.h; i++) {
                        if (this.board[i][j] != 0){
                            if(k != i) {
                                this.board[k][j] = this.board[i][j];
                                this.board[i][j] = 0;
                            }
                            k++;
                        }
                    }
                }
                break;
            }
            case MoveDirection.EAST: {
                for (int j = 0; j < this.w; j++){
                    k = this.h-1;
                    for (int i = (this.h-1); i > -1; i--) {
                        if (this.board[i][j] != 0){
                            if(k != i) {
                                this.board[k][j] = this.board[i][j];
                                this.board[i][j] = 0;
                            }
                            k--;
                        }
                    }
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Direction invalid");
        }
    }
*/
    @Override
    public void run(PlayerInterface player, UserInterface ui) {
        if(player == null || ui == null) {
            throw new IllegalArgumentException("Invalid Player or UserInterface");
        }
        this.mov = 0;
        this.p = 0;
        ui.updateScreen(this);
        boolean b = false;
        while(isMovePossible()) {
            String PossibleAnswers [] = {"North", "north", "n", "N", "South", "south", "S", "s", "West", "west", "w", "W",  "East", "east", "e", "E",  "NORTH", "SOUTH", "EAST", "WEST"};
            String move = ui.getUserInput("North, South, West or East?", PossibleAnswers);
            boolean no = (move.charAt(0) == 'n' || move.charAt(0) == 'N');
            boolean so = (move.charAt(0) == 's' || move.charAt(0) == 'S');
            boolean we = (move.charAt(0) == 'w' || move.charAt(0) == 'W');
            boolean ea = (move.charAt(0) == 'e' || move.charAt(0) == 'E');
            if (no) {
                b = performMove(MoveDirection.NORTH);
            } else if (so) {
                b = performMove(MoveDirection.SOUTH);
            } else if (we) {
                b = performMove(MoveDirection.WEST);
            } else if (ea) {
                b = performMove(MoveDirection.EAST);
            } else {
                b = performMove(null);
            }
            if (b) {
                this.mov++;
                addPiece();
                ui.updateScreen(this);
            }
        }
        ui.showGameOverScreen(this);
        //throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    @Override
    public void setPieceAt(int x, int y, int piece) {
        if (x < 0 || y < 0 || x >= this.w || y >= this.h) {
            throw new IllegalArgumentException("The coordinates must be inside the field");
        }
        if (piece < 0) {
            throw new IllegalArgumentException("The value must not be negative");
        }
        this.board[x][y] = piece;
        //throw new UnsupportedOperationException("Unimplemented method 'setPieceAt'");
    }
    
}
