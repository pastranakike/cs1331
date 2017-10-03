import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class PgnReader {

    /**
     * Find the tagName tag pair in a PGN game and return its value.
     *
     * @see http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm
     *
     * @param tagName the name of the tag whose value you want
     * @param game a `String` containing the PGN text of a chess game
     * @return the value in the named tag pair
     */
    public static String tagValue(String tagName, String game) {
        Scanner gameInfo = new Scanner(game);
        while (gameInfo.hasNext()) {
            String line = gameInfo.nextLine();
            if (!line.isEmpty() && line.charAt(0) == '[') {
                String tag = line.split(" ")[0].substring(1);
                if (tag.equals(tagName)) {
                    int tagLen = tag.length();
                    int lineLen = line.length();
                    String tagValue = line.substring(tagLen + 3, lineLen - 2);
                    return tagValue;
                }
            }
        }
        return "NOT GIVEN";
    }
    /* Method that will return all the moves of a game in a string array by
    taking all the .pgn file as a Scanner*/
    public static String[] getMoves(String game) {
        Scanner gameMoves = new Scanner(game);
        String line = gameMoves.nextLine();
        while (!line.isEmpty() && line.charAt(0) != '1') {
            line = gameMoves.nextLine();
        }
        String initialMove = line;
        while (gameMoves.hasNext()) {
            initialMove += gameMoves.nextLine();
        }
        initialMove = initialMove.substring(3);
        String[] moves = initialMove.split("\\.");
        for (int i = 0; i < moves.length; i++) {
            moves[i] = moves[i].substring(0, moves[i].length()).trim();
        }
        return moves;
    }
    /* Method used to move the white pawns */
    public static String[][] moveWhitePawn(String move, String[][] board) {
        /*Value of the rows and columns that will be used to facilitate the
        search of the desired index*/
        String columns = "abcdefgh";
        String rows = "87654321";
        int xPosition = move.indexOf('x');
        int updColumn;
        int updRow;
        int promote = move.indexOf('=');


        if (xPosition == -1) {
            updColumn = columns.indexOf(move.charAt(0)); //move.charAt(0))
            updRow = rows.indexOf(move.charAt(1));
        } else {
            updColumn = columns.indexOf(move.charAt(xPosition + 1));
            updRow = rows.indexOf(move.charAt(xPosition + 2));
        }
        if (updColumn < 8 && updRow < 8 && updColumn >= 0 && updRow >= 0) {
            board[updRow][updColumn] = (promote == - 1)
                ? "P" : "" + Character.toString(move.charAt(promote + 1));
        }

        Boolean isFound = false;


        while (!isFound) {
            Boolean case1 = updColumn < 8 && updRow + 1 < 8;
            Boolean case2 = updColumn < 8 && updRow + 2 < 8;
            Boolean case3 = updColumn + 1 < 8 && updRow + 1 < 8;
            Boolean case4 = updColumn - 1 < 8 && updRow + 1 < 8;

            if (xPosition == -1) {
                if (case1 && (board[updRow + 2][updColumn]).equals("P")) {
                    board[updRow + 2][updColumn] = "x";
                    isFound = true;
                } else {
                    board[updRow + 1][updColumn] = "x";
                    isFound = true;
                }
            } else {
                if (case3
                    && (board[updRow + 1][updColumn + 1]).equals("P")) {
                    board[updRow + 1][updColumn + 1] = "x";
                    isFound = true;
                    if (board[updRow + 1][updColumn].equals("p")) {
                        board[updRow + 1][updColumn] = "x";
                    }
                } else {
                    board[updRow + 1][updColumn - 1] = "x";
                    isFound = true;
                    if (board[updRow + 1][updColumn].equals("p")) {
                        board[updRow + 1][updColumn] = "x";
                    }
                }
            }
        }
        //printBoard(board);
        return board;
    }
    /* Method used to move the black pawns */
    public static String[][] moveBlackPawn(String move, String[][] board) {
        /*Value of the rows and columns that will be used to facilitate the
        search of the desired index*/
        String columns = "abcdefgh";
        String rows = "87654321";
        int xPosition = move.indexOf('x');
        int updColumn;
        int updRow;
        if (xPosition == -1) {
            updColumn = columns.indexOf(move.charAt(0)); //move.charAt(0))
            updRow = rows.indexOf(move.charAt(1));
        } else {
            updColumn = columns.indexOf(move.charAt(xPosition + 1));
            updRow = rows.indexOf(move.charAt(xPosition + 2));
        }

        if (updColumn < 8 && updRow < 8 && updColumn >= 0 && updRow >= 0) {
            board[updRow][updColumn] = "p";
        }

        Boolean isFound = false;
        while (!isFound) {
            Boolean case1 = updColumn < 8 && updRow + 1 < 8;
            case1 = case1 && updColumn >= 0 && updRow + 1 >= 0;
            Boolean case2 = updColumn < 8 && updRow + 2 < 8;
            case2 = case2 && updColumn >= 0 && updRow + 2 >= 0;
            Boolean case3 = updColumn - 1 < 8 && updRow  - 1 < 8;
            case3 = case3 && updColumn - 1 >= 0 && updRow - 1 >= 0;
            Boolean case4 = updColumn + 1 < 8 && updRow - 1 < 8;
            case4 = case4 && updColumn + 1 >= 0 && updRow - 1 >= 0;
            if (xPosition == -1) {
                if (case1 && (board[updRow - 2][updColumn]).equals("p")) {
                    board[updRow - 2][updColumn] = "x";
                    isFound = true;
                } else {
                    board[updRow - 1][updColumn] = "x";
                    isFound = true;
                }
            } else {
                if (case3
                    && (board[updRow - 1][updColumn - 1]).equals("p")) {
                    board[updRow - 1][updColumn - 1] = "x";
                    isFound = true;
                    if (board[updRow - 1][updColumn].equals("P")) {
                        board[updRow - 1][updColumn] = "x";
                    }
                } else {
                    board[updRow - 1][updColumn + 1] = "x";
                    isFound = true;
                    if (board[updRow - 1][updColumn].equals("P")) {
                        board[updRow - 1][updColumn] = "x";
                    }
                }
            }
        }
        //printBoard(board);
        return board;
    }
    /*This method is used for the movement of the Rook it will go in 1D and will
    return the updated board */
    public static String[][] moveRook(String move, String[][] board,
        int color) {
        String columns = "abcdefgh";
        String rows = "87654321";
        int xPosition = move.indexOf('x');
        int updColumn;
        int updRow;
        if (xPosition == -1) {
            updColumn = columns.indexOf(move.charAt(1));
            updRow = rows.indexOf(move.charAt(2));
        } else {
            updColumn = columns.indexOf(move.charAt(xPosition + 1));
            updRow = rows.indexOf(move.charAt(xPosition + 2));
        }

        String typePiece = (color % 2 == 0) ? "r" : "R";
        if (updColumn < 8 && updRow < 8) {
            board[updRow][updColumn] = typePiece;
        }
        for (int i = updRow + 1; i < updRow + 9; i++) {
            int j = i % 8;
            if ((board[j][updColumn]).equals(typePiece) && j != updRow)  {
                board[j][updColumn] = "x";
                //printBoard(board);
                return board;
            }
        }
        for (int i = updColumn + 1; i < updColumn + 9; i++) {
            int j = i % 8;
            System.out.println(updColumn);
            if (board[updRow][j].equals(typePiece) && j != updColumn) {
                board[updRow][j] = "x";
                //printBoard(board);
                return board;
            }
        }
        return board;
    }
    /* This method will have two arguments: the old board and the move
    that a white Knight will do. The output will be a new updated board*/
    public static String[][] moveKnight(String move, String[][] board,
        int color) {
        String columns = "abcdefgh";
        String rows = "87654321";
        int xPosition = move.indexOf('x');
        int updColumn;
        int updRow;
        if (xPosition == -1) {
            updColumn = columns.indexOf(move.charAt(1));
            updRow = rows.indexOf(move.charAt(2));
        } else {
            updColumn = columns.indexOf(move.charAt(xPosition + 1));
            updRow = rows.indexOf(move.charAt(xPosition + 2));
        }
        String typePiece = (color % 2 == 0) ? "n" : "N";
        if (updColumn < 8 && updRow < 8 && updColumn >= 0 && updRow >= 0) {
            board[updRow][updColumn] = typePiece;
        }
        Boolean isFound = false;

        while (!isFound) {
            Boolean case1 = updRow - 1 < 8 && updColumn - 2 < 8;
            case1 = case1 && updRow - 1 >= 0 && updColumn - 2 >= 0;
            Boolean case2 = updRow + 1 < 8 && updColumn - 2 < 8;
            case2 = case2 && updRow + 1 >= 0 && updColumn - 2 >= 0;
            Boolean case3 = updRow + 2 < 8 && updColumn - 1 < 8;
            case3 = case3 && updRow + 2 >= 0 && updColumn - 1 >= 0;
            Boolean case4 = updRow + 2 < 8 && updColumn + 1 < 8;
            case4 = case4 && updRow + 2 >= 0 && updColumn + 1 >= 0;
            Boolean case5 = updRow - 2 < 8 && updColumn - 1 < 8;
            case5 = case5 && updRow - 2 >= 0 && updColumn - 1 >= 0;
            Boolean case6 = updRow - 2 < 8 && updColumn + 1 < 8;
            case6 = case6 && updRow - 2 >= 0 && updColumn + 1 >= 0;
            Boolean case7 = updRow - 1 < 8 && updColumn + 2 < 8;
            case7 = case7 && updRow - 1 >= 0 && updColumn + 2 >= 0;
            Boolean case8 = updRow + 1 < 8 && updColumn + 2 < 8;
            case8 = case8 && updRow + 1 >= 0 && updColumn + 2 >= 0;

            if (case1 && (board[updRow - 1][updColumn - 2]).equals(typePiece)) {
                board[updRow - 1][updColumn - 2] = "x";
                isFound = true;
            } else if (case2
                    && (board[updRow + 2][updColumn + 1]).equals(typePiece)) {
                board[updRow + 2][updColumn + 1] = "x";
                isFound = true;
            } else if (case3
                    && (board[updRow + 2][updColumn - 1]).equals(typePiece)) {
                board[updRow + 2][updColumn - 1] = "x";
                isFound = true;
            } else if (case4
                    && (board[updRow + 2][updColumn + 1]).equals(typePiece)) {
                board[updRow + 2][updColumn + 1] = "x";
                isFound = true;
            } else if (case5
                    && (board[updRow - 2][updColumn - 1]).equals(typePiece)) {
                board[updRow - 2][updColumn - 1] = "x";
                isFound = true;
            } else if (case6
                    && (board[updRow - 2][updColumn + 1]).equals(typePiece)) {
                board[updRow - 2][updColumn + 1] = "x";
                isFound = true;
            } else if (case7
                    && (board[updRow - 1][updColumn + 2]).equals(typePiece)) {
                board[updRow - 1][updColumn + 2] = "x";
                isFound = true;
            }  else {
                board[updRow + 1][updColumn + 2] = "x";
                isFound = true;
            }
        }
        //printBoard(board);
        return board;
    }
    /* This method will update the position of the Bishop by taking three
    arguments: the final position, the board and color of the piece, and it will
    return the updated board.
    */
    public static String[][] moveBishop(String move, String[][] board,
        int color) {
        String columns = "abcdefgh";
        String rows = "87654321";
        int xPosition = move.indexOf('x');
        int updColumn;
        int updRow;
        if (xPosition == -1) {
            updColumn = columns.indexOf(move.charAt(1));
            updRow = rows.indexOf(move.charAt(2));
        } else {
            updColumn = columns.indexOf(move.charAt(xPosition + 1));
            updRow = rows.indexOf(move.charAt(xPosition + 2));
        }

        String typePiece = (color % 2 == 0) ? "b" : "B";
        if (updColumn < 8 && updRow < 8) {
            board[updRow][updColumn] = typePiece;
        }

        for (int i = updRow + 1, j = updColumn + 1;
            i < updRow + 8; i++, j++) {
            int indxRow = i % 8;
            int indxColumn = j % 8;
            Boolean isEmpty = board[indxRow][indxColumn].equals(typePiece);
            Boolean isFirst = i == updRow && j == updColumn;
            if (isEmpty && !isFirst) {
                board[indxRow][indxColumn] = "x";
                //printBoard(board);
                return board;
            }
        }
        for (int i = updRow - 1, j = updColumn + 1;
            i > updRow - 8; i--, j++) {
            int indxRow = ((i % 8) + 8) % 8;
            int indxColumn = j % 8;
            Boolean isEmpty = board[indxRow][indxColumn].equals(typePiece);
            Boolean isFirst = i == updRow && j == updColumn;
            if (isEmpty && !isFirst) {
                board[indxRow][indxColumn] = "x";
                //printBoard(board);
                return board;
            }
        }
        return board;
    }
    /* This method will joint the Rook and Bishop to move the Queen of the
    game. It will have three arguments: new position, old board and an int
    that represent the color of a piece, and it will return the updated board*/
    public static String[][] moveQueen(String move, String[][] board,
        int color) {
        String columns = "abcdefgh";
        String rows = "87654321";
        int xPosition = move.indexOf('x');
        int updColumn;
        int updRow;

        if (xPosition == -1) {
            updColumn = columns.indexOf(move.charAt(1));
            updRow = rows.indexOf(move.charAt(2));
        } else {
            updColumn = columns.indexOf(move.charAt(xPosition + 1));
            updRow = rows.indexOf(move.charAt(xPosition + 2));
        }

        String typePiece = (color % 2 == 0) ? "q" : "Q";

        if (updColumn < 8 && updRow < 8) {
            board[updRow][updColumn] = typePiece;
        }

        for (int i = updRow + 1, j = updColumn + 1;
            i < updRow + 8; i++, j++) {
            int indxRow = i % 8;
            int indxColumn = j % 8;
            Boolean isEmpty = board[indxRow][indxColumn].equals(typePiece);
            Boolean isFirst = i == updRow && j == updColumn;
            if (isEmpty && !isFirst) {
                board[indxRow][indxColumn] = "x";
                //printBoard(board);
                return board;
            }
        }

        for (int i = updRow - 1, j = updColumn + 1;
            i > updRow - 8; i--, j++) {
            int indxRow = ((i % 8) + 8) % 8;
            int indxColumn = j % 8;
            Boolean isEmpty = board[indxRow][indxColumn].equals(typePiece);
            Boolean isFirst = i == updRow && j == updColumn;
            if (isEmpty && !isFirst) {
                board[indxRow][indxColumn] = "x";
                //printBoard(board);
                return board;
            }
        }

        for (int i = updRow + 1; i < updRow + 9; i++) {
            int j = i % 8;
            if ((board[j][updColumn]).equals(typePiece) && j != updRow)  {
                board[j][updColumn] = "x";
                //printBoard(board);
                return board;
            }
        }

        for (int i = updColumn + 1; i < updColumn + 9; i++) {
            int j = i % 8;
            if (board[updRow][j].equals(typePiece) && j != updColumn) {
                board[updRow][j] = "x";
                //printBoard(board);
                return board;
            }
        }
        //printBoard(board);
        return board;
    }
    /* This method will update the position of the King. For that, it will
    take the new position, the old board and its color, and it will return the
    updated board*/
    public static String[][] moveKing(String move, String[][] board,
        int color) {
        String columns = "abcdefgh";
        String rows = "87654321";
        int xPosition = move.indexOf('x');
        int updColumn;
        int updRow;
        if (xPosition == -1) {
            updColumn = columns.indexOf(move.charAt(1));
            updRow = rows.indexOf(move.charAt(2));
        } else {
            updColumn = columns.indexOf(move.charAt(xPosition + 1));
            updRow = rows.indexOf(move.charAt(xPosition + 2));
        }
        String typePiece = (color % 2 == 0) ? "k" : "K";

        Boolean case1 = updRow + 1 < 8
            && (board[updRow + 1][updColumn]).equals(typePiece);
        Boolean case2 = updRow + 1 < 8 && updColumn + 1 < 8
            && (board[updRow + 1][updColumn + 1]).equals(typePiece);
        Boolean case3 = updRow + 1 < 8 && updColumn - 1 >= 0
            && (board[updRow + 1][updColumn - 1]).equals(typePiece);
        Boolean case4 = updRow - 1 >= 0 && updColumn + 1 < 8
            && (board[updRow - 1][updColumn + 1]).equals(typePiece);
        Boolean case5 = updRow - 1 >= 0 && updColumn - 1 >= 0
            && (board[updRow - 1][updColumn - 1]).equals(typePiece);
        Boolean case6 = updRow - 1 >= 0
            && (board[updRow - 1][updColumn]).equals(typePiece);
        Boolean case7 = updColumn - 1 >= 0
            && (board[updRow][updColumn - 1]).equals(typePiece);
        Boolean case8 = updColumn + 1 < 8
            && (board[updRow][updColumn + 1]).equals(typePiece);

        if (case1) {
            board[updRow + 1][updColumn] = "x";
        } else if (case2) {
            board[updRow + 1][updColumn + 1] = "x";
        } else if (case3) {
            board[updRow + 1][updColumn - 1] = "x";
        } else if (case4) {
            board[updRow - 1][updColumn + 1] = "x";
        } else if (case5) {
            board[updRow - 1][updColumn - 1] = "x";
        } else if (case6) {
            board[updRow - 1][updColumn] = "x";
        } else if (case7) {
            board[updRow][updColumn - 1] = "x";
        } else {
            board[updRow][updColumn + 1] = "x";
        }
        //printBoard(board);
        return board;
    }
    /*special move used for castling. It has three parameters: the board, the
    type of castling (move), and an int used for fin the color used
    */
    public static String[][] castling(String move, String[][] board,
        int color) {
        char piece = (color % 2 == 0) ? 'b' : 'w';
        Boolean kingSide = move.equals("O-O");
        Boolean queenSide = move.equals("O-O-O");
        if (kingSide && piece == 'b') {
            board[0][5] = "r";
            board[0][7] = "x";
            board[0][6] = "k";
            board[0][4] = "x";
        } else if (kingSide && piece == 'w') {
            board[7][5] = "R";
            board[7][7] = "x";
            board[7][6] = "K";
            board[7][4] = "x";
        } else if (queenSide && piece == 'b') {
            board[0][1] = "r";
            board[0][0] = "x";
            board[0][2] = "k";
            board[0][4] = "x";
        } else {
            board[7][1] = "R";
            board[7][0] = "x";
            board[7][2] = "K";
            board[7][4] = "x";
        }
        //printBoard(board);
        return board;
    }

    //Method to print the board
    // public static void printBoard(String[][] board) {
    //     for (int i = 0; i < board.length; i++) {
    //         String row = "";
    //         for (String element : board[i]) {
    //             row += element + " ";
    //         }
    //         System.out.println(row);
    //     }
    // }

    //Method that will read the board to translate in FEN notation
    public static String translateBoard(String[][] board) {
        String finalBoard = "";
        for (int i = 0; i < 8; i++) {
            int blankCounter = 0;
            for (int j = 0; j < 8; j++) {
                if (board[i][j].equals("x")) {
                    blankCounter++;
                    if (j == 7) {
                        finalBoard += blankCounter;
                    }
                } else {
                    finalBoard += (blankCounter > 0)
                        ? blankCounter + board[i][j] :  board[i][j];
                    blankCounter = 0;
                }
            }
            finalBoard += "/";
        }
        int lenFinal = finalBoard.length();
        return finalBoard.substring(0, lenFinal - 1);
    }

    /**
     * Play out the moves in game and return a String with the game's
     * final position in Forsyth-Edwards Notation (FEN).
     *
     * @see http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm#c16.1
     *
     * @param game a `Strring` containing a PGN-formatted chess game or opening
     * @return the game's final position in FEN.
     */
    public static String finalPosition(String game) {
        //Find the moves of the game
        String[] moves = getMoves(game);
        //Hardcode for the initial chess board
        /*  {"a";"b";"c";"d";"e";"f";"g";"h"}
        reminder: undercase are black pieces and uppercase are white pieces*/
        String[][] board = {{"r", "n", "b", "q", "k", "b", "n", "r"},
                            {"p", "p", "p", "p", "p", "p", "p", "p"},
                            {"x", "x", "x", "x", "x", "x", "x", "x"},
                            {"x", "x", "x", "x", "x", "x", "x", "x"},
                            {"x", "x", "x", "x", "x", "x", "x", "x"},
                            {"x", "x", "x", "x", "x", "x", "x", "x"},
                            {"P", "P", "P", "P", "P", "P", "P", "P"},
                            {"R", "N", "B", "Q", "K", "B", "N", "R"}};

        //Start the game
        for (int i = 0; i < moves.length; i++) {
            Scanner move = new Scanner(moves[i]);
            String whiteMove;
            String blackMove;
            if (move.hasNext()) {
                whiteMove = move.next();
            } else {
                whiteMove = " ";
            }
            if (move.hasNext()) {
                blackMove = move.next();
            } else {
                blackMove = " ";
            }
            //If statement to determine which method that piece will use
            //For White pieces
            if (Character.toString(whiteMove.charAt(0)).equals("R")) {
                board = moveRook(whiteMove, board, 1);
            } else if (Character.toString(whiteMove.charAt(0)).equals("N")) {
                board = moveKnight(whiteMove, board, 1);
            } else if (Character.toString(whiteMove.charAt(0)).equals("B")) {
                board = moveBishop(whiteMove, board, 1);
            } else if (Character.toString(whiteMove.charAt(0)).equals("Q")) {
                board = moveQueen(whiteMove, board, 1);
            } else if (Character.toString(whiteMove.charAt(0)).equals("K")) {
                board = moveKing(whiteMove, board, 1);
            } else if (whiteMove.equals("O-O") || whiteMove.equals("O-O-O")){
                board = castling(whiteMove, board, 1);
            } else if (!(whiteMove.equals(" "))) {
                board = moveWhitePawn(whiteMove, board);
            } else {
                board = board;
            }
            //For black pieces
            if (Character.toString(blackMove.charAt(0)).equals("R")) {
                board = moveRook(blackMove, board, 2);
            } else if (Character.toString(blackMove.charAt(0)).equals("N")) {
                board = moveKnight(blackMove, board, 2);
            } else if (Character.toString(blackMove.charAt(0)).equals("B")) {
                board = moveBishop(blackMove, board, 2);
            } else if (Character.toString(blackMove.charAt(0)).equals("Q")) {
                board = moveQueen(blackMove, board, 2);
            } else if (Character.toString(blackMove.charAt(0)).equals("K")) {
                board = moveKing(blackMove, board, 2);
            } else if (blackMove.equals("O-O") || blackMove.equals("O-O-O")){
                board = castling(blackMove, board, 2);
            } else if (!(blackMove.equals(" "))) {
                board = moveBlackPawn(blackMove, board);
            } else {
                board = board;
            }
        }
        String finalGame = translateBoard(board);
        return finalGame;
    }

    /**
     * Reads the file named by path and returns its content as a String.
     *
     * @param path the relative or abolute path of the file to read
     * @return a String containing the content of the file
     */
    public static String fileContent(String path) {
        Path file = Paths.get(path);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Add the \n that's removed by readline()
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            System.exit(1);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String game = fileContent(args[0]);
        System.out.format("Event: %s%n", tagValue("Event", game));
        System.out.format("Site: %s%n", tagValue("Site", game));
        System.out.format("Date: %s%n", tagValue("Date", game));
        System.out.format("Round: %s%n", tagValue("Round", game));
        System.out.format("White: %s%n", tagValue("White", game));
        System.out.format("Black: %s%n", tagValue("Black", game));
        System.out.format("Result: %s%n", tagValue("Result", game));
        System.out.println("Final Position:");
        System.out.println(finalPosition(game));


    }
}
