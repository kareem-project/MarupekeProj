package marupeke.part2;



import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author ianw, benrhard
 */
public class MarupekeGrid {


    final int size;
    final MarupekeTile grid[][];

    public MarupekeGrid(int size) {
        this.size = size;
        grid = new MarupekeTile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new MarupekeTile();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public MarupekeTile get(int x, int y) {
        return grid[y][x];
    }

    public boolean setSolid(int x, int y) {
        return setGrid(x, y, false, MarupekeTile.Mark.SOLID);
    }

    public boolean markX(int x, int y) {
        return setX(x, y, grid[y][x].isEditable());
    }

    public boolean markO(int x, int y) {
        return setO(x, y, grid[y][x].isEditable());
    }

    public boolean setX(int x, int y, boolean canEdit) {
        return setGrid(x, y, canEdit, MarupekeTile.Mark.CROSS);
    }

    public boolean setO(int x, int y, boolean canEdit) {
        return setGrid(x, y, canEdit, MarupekeTile.Mark.NOUGHT);
    }

    public boolean unmark(int x, int y) {
        return setGrid(x, y, true, MarupekeTile.Mark.BLANK);
    }

    public boolean setGrid(int x, int y, boolean canEdit, MarupekeTile.Mark c) {
        if (!grid[y][x].isEditable()) {
            return false;
        }
        grid[y][x].setMark(c);
        grid[y][x].setEditable(canEdit);
        return true;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                s += grid[i][j];
            }
            s += "\n";
        }
        return s;
    }

    public boolean isLegalGrid() {
        return illegalitiesInGrid().size() == 0;
    }

    public List<Reason> illegalitiesInGrid() {
        List<Reason> problems = new ArrayList();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getMark() != MarupekeTile.Mark.CROSS
                        && grid[i][j].getMark() != MarupekeTile.Mark.NOUGHT) {
                    continue;
                }
                if (i > 0 && i < grid.length - 1) {
                    // do vertical tests
                    if (grid[i - 1][j].getMark() == grid[i][j].getMark()
                            && grid[i][j].getMark() == grid[i + 1][j].getMark()) {
                        problems.add(new GridReason(Reason.ViolationType.VERTICAL, grid[i][j].getMark(), j, i));
                    }
                }
                if (j > 0 && j < grid.length - 1) {
                    // do horizontal tests
                    if (grid[i][j - 1].getMark() == grid[i][j].getMark()
                            && grid[i][j].getMark() == grid[i][j + 1].getMark()) {
                        problems.add(new GridReason(Reason.ViolationType.HORIZONTAL, grid[i][j].getMark(), j, i));
                    }
                    if (i > 0 && i < grid.length - 1) {
                        if (grid[i - 1][j - 1].getMark() == grid[i][j].getMark()
                                && grid[i][j].getMark() == grid[i + 1][j + 1].getMark()) {
                            problems.add(new GridReason(Reason.ViolationType.DIAGONAL, grid[i][j].getMark(), j, i));
                        }
                        if (grid[i + 1][j - 1].getMark() == grid[i][j].getMark()
                                && grid[i][j].getMark() == grid[i - 1][j + 1].getMark()) {
                            problems.add(new GridReason(Reason.ViolationType.DIAGONAL, grid[i][j].getMark(), j, i));

                        }
                    }
                }
            }

        }
        return problems;
    }

    /**
     * Generate a random puzzle, filled with initial positions of the specified
     * numbers
     *
     * @param size
     * @param numFill
     * @param numX
     * @param numO
     * @return
     */
    // this method will create only legal grids in the first place
    public static MarupekeGrid randomPuzzle(int size,
                                            int numFill,
                                            int numX,
                                            int numO) throws TooManyMarkedSquares {
        if (numX + numO > size * size / 2) {
            throw new TooManyMarkedSquares();
        }
        MarupekeGrid mp = new MarupekeGrid(size);
        Random rand = new Random();
        // There is repetition of code here, but removing the repetition
        // requires some functional manipulation which we haven't covered yet
        int countSolid = 0;
        while (countSolid < numFill) {
            if (mp.setSolid(rand.nextInt(size), rand.nextInt(size))) {
                countSolid++;
            }
        }


        int countX = 0;
        int countO = 0;
        while (countX < numX || countO < numO) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            if (countX < numX && mp.setX(x, y, true)) {
                if (mp.isLegalGrid()) {
                    mp.setX(x, y, false);
                    countX++;
                } else {
                    mp.unmark(x, y);
                }

            }
            if (countO < numO && mp.setO(x, y, true)) {
                if (mp.isLegalGrid()) {
                    mp.setO(x, y, false);
                    countO++;
                } else {
                    mp.unmark(x, y);
                }

            }
        }
        return mp;
    }

    public int remainingSpaces() {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getMark() == MarupekeTile.Mark.BLANK) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isPuzzleComplete() {
        return remainingSpaces() == 0;
    }




    // static factory method for bulding a game grid
    public static MarupekeGrid buildGameGrid(int size) throws TooManyMarkedSquares{
        int nuFi,nuX,nuO;
        MarupekeGrid grid = null;
        Random rand = new Random();
        nuFi = rand.nextInt(size*size/2);
        nuX = rand.nextInt(size*size/2 - nuFi);
        nuO = rand.nextInt(size*size/2 - nuFi - nuX);
        // build the game grid
        grid=randomPuzzle(size, nuFi, nuX, nuO);
        while (!grid.isLegalGrid()) {
            System.out.println(grid.illegalitiesInGrid());
            grid=randomPuzzle(size, nuFi, nuX, nuO);

        }
        return grid;
    }

    // this is auxiliary for testing only
    public Pair<Integer,Integer> findCoordinatesWith(MarupekeTile.Mark match) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                MarupekeTile t = get(i, j);
                MarupekeTile.Mark m = t.getMark();
                if (m == match) {
                    return new Pair(i, j);
                }

            }
        }
        return null;
    }

}
