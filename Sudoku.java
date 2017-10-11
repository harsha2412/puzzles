/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author harsha
 */
public class Sudoku {

    int puzzle[][] = new int[9][9];
    int primes[] = {2, 3, 5, 7, 11, 13, 17, 19, 23};
    int dividedBy[][][] = new int[9][9][9];

    int primeMatrix[][] = new int[9][9];

    public Sudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                puzzle[i][j] = 0;
            }

        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    dividedBy[i][j][k] = 0;
                }
            }
        }

        puzzle[0][1] = 7;
        puzzle[0][3] = 2;
        puzzle[0][4] = 3;
        puzzle[0][5] = 8;

        puzzle[1][3] = 7;
        puzzle[1][4] = 4;
        puzzle[1][6] = 8;
        puzzle[1][8] = 9;

        puzzle[2][1] = 6;
        puzzle[2][2] = 8;
        puzzle[2][3] = 1;
        puzzle[2][5] = 9;
        puzzle[2][8] = 2;

        puzzle[3][1] = 3;
        puzzle[3][2] = 5;
        puzzle[3][3] = 4;
        puzzle[3][8] = 8;

        puzzle[4][0] = 6;
        puzzle[4][2] = 7;
        puzzle[4][3] = 8;
        puzzle[4][5] = 2;
        puzzle[4][6] = 5;
        puzzle[4][8] = 1;

        puzzle[5][0] = 8;
        puzzle[5][5] = 5;
        puzzle[5][6] = 7;
        puzzle[5][7] = 6;

        puzzle[6][0] = 2;
        puzzle[6][3] = 6;
        puzzle[6][5] = 3;
        puzzle[6][6] = 1;
        puzzle[6][7] = 9;

        puzzle[7][0] = 7;
        puzzle[7][2] = 9;
        puzzle[7][4] = 2;
        puzzle[7][5] = 1;

        puzzle[8][3] = 9;
        puzzle[8][4] = 7;
        puzzle[8][5] = 4;
        puzzle[8][7] = 8;
    }

    private void initializePrimeMatrix() {
        int product = 2 * 3 * 5 * 7 * 11 * 13 * 17 * 19 * 23;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] != 0) {
                    primeMatrix[i][j] = primes[puzzle[i][j] - 1];
                } else {
                    primeMatrix[i][j] = product;
                }
            }

        }

    }

    private void print() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(puzzle[i][j] + " |");
            }
            System.out.println();

        }
    }

    private void printPrimeMatrix() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(primeMatrix[i][j] + " |");
            }
            System.out.println();

        }
    }

    private boolean isValid(int val, int r, int c) {
        //row
        for (int i = 0; i < 9; i++) {
            if (puzzle[r][i] == val && (i != c)) {
                return false;
            }
        }
        //col 
        for (int i = 0; i < 9; i++) {
            if (puzzle[i][c] == val && (i != r)) {
                return false;
            }
        }

        //grid
        int posr = r % 3;
        int posc = c % 3;
        int str = r;
        int stc = c;
        if (posr == 1) {
            str = r - 1;
        }
        if (posc == 1) {
            stc = c - 1;
        }
        if (posr == 2) {
            str = r - 2;
        }
        if (posc == 2) {
            stc = c - 2;
        }

        for (int i = str; i < str + 3; i++) {
            for (int j = stc; j < stc + 3; j++) {
                if (puzzle[i][j] == val && (i != r && j != c)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean solve() {

        if (checkSolution()) {
            return true;
        }
        int r = -1;
        int c = -1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] == 0) {
                    r = i;
                    c = j;
                    break;
                }
            }
        }
        for (int i = 1; i <= 9; i++) {
            if (isValid(i, r, c)) {
                puzzle[r][c] = i;
                if (solve()) {
                    return true;
                } else {
                    puzzle[r][c] = 0;
                }

            }
        }

        return false;
        //solve();

    }

    private boolean checkSolution() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] == 0) {
                    return false;
                }
            }

        }
        return true;
    }

    private int isAssigned(int r, int c) {
        for (int i = 0; i < 9; i++) {
            if (primeMatrix[r][c] == primes[i]) {
                return i;
            }

        }
        return -1;
    }

    private void processMatrix() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (isAssigned(i, j) == -1) {
                    divideCell(i, j);
                }
            }
        }
        if (!checkSolutionPrime()) {

            // printPrimeMatrix();
            processMatrix();
        }

    }

    private void divideCell(int r, int c) {

        //row 
        for (int i = 0; i < 9; i++) {
            int check = isAssigned(r, i);
            if (check != -1 && dividedBy[r][c][check] == 0 && (i != c)) {
               // System.out.println("r =" + r + " c= " + c + " val = " + primeMatrix[r][c] + " Dividing by " + primes[check]);
                primeMatrix[r][c] = primeMatrix[r][c] / primes[check];
                dividedBy[r][c][check] = 1;
            }
        }

        //col
        for (int i = 0; i < 9; i++) {
            int check = isAssigned(i, c);
            if (check != -1 && dividedBy[r][c][check] == 0 && (i != r)) {
              //  System.out.println("r =" + r + " c= " + c + " val = " + primeMatrix[r][c] + " Dividing by " + primes[check]);
                primeMatrix[r][c] = primeMatrix[r][c] / primes[check];
                dividedBy[r][c][check] = 1;
            }
        }

        //grid
        int posr = r % 3;
        int posc = c % 3;
        int str = r;
        int stc = c;
        if (posr == 1) {
            str = r - 1;
        }
        if (posc == 1) {
            stc = c - 1;
        }
        if (posr == 2) {
            str = r - 2;
        }
        if (posc == 2) {
            stc = c - 2;
        }
        for (int i = str; i < str + 3; i++) {
            for (int j = stc; j < stc + 3; j++) {
                if (i != r && j != c) {
                    int check = isAssigned(i, j);
                 //   System.out.println("r =" + r + " c= " + c + "check =" + check + " val = " + primeMatrix[r][c]);
                    if (check != -1 && dividedBy[r][c][check] == 0) {
                       // System.out.println("r =" + r + " c= " + c + " val = " + primeMatrix[r][c] + " Dividing by " + primes[check]);
                        primeMatrix[r][c] = primeMatrix[r][c] / primes[check];
                        dividedBy[r][c][check] = 1;
                    }
                }
            }
        }

    }

    private boolean checkSolutionPrime() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (isAssigned(i, j) == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void mapPrimesToNumbers(){
        int solution[][] = new int[9][9];
        for(int i = 0;i<9;i++){
            for(int j=0;j<9;j++){
               solution[i][j] = isAssigned(i,j)+1;
            }
            
        }
           for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(solution[i][j] + " |");
            }
            System.out.println();

        }
        
    }
    
    public static void main(String args[]) {
        Sudoku s = new Sudoku();
        System.out.println("\n\nInitial---->\n\n");
        s.print();
        s.solve();
        System.out.println("\n\nSolution---->\n\n");
        s.print();
        Sudoku s1 = new Sudoku();
        System.out.println("\n\nInitial---->\n\n");
        s1.print();
        s1.initializePrimeMatrix();
       // s1.printPrimeMatrix();
        s1.processMatrix();
        System.out.println("\n\n Prime Solution---->\n\n");
        s1.printPrimeMatrix();
         System.out.println("\n\nSolution---->\n\n");
        s1.mapPrimesToNumbers();
    }
}
