import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
// import Cell;

public class GameOfLife {
    private final static int MAX_X = 10;
    private final static int MAX_Y = 10;

    private int cycle = 1;

    private int countNeighborhood(TreeMap<Integer, TreeMap<Integer, Cell>> grid, int posX, int posY)
    {
        int nbAlive = 0;
        for (int y = posY - 1; y <= posY + 1; y++) {
            if (!grid.containsKey(y))
                continue;
            for (int x = posX - 1; x <= posX + 1; x++) {
                if (!grid.get(y).containsKey(x) || (x == posX  && y == posY) )
                    continue;
                if (grid.get(y).get(x).isAlive(cycle))
                    nbAlive += 1;
            }
        }
        return nbAlive;
    }

    private int checkNeighborhood(TreeMap<Integer, TreeMap<Integer, Cell>> grid, int posX, int posY)
    {
        for (int y = posY - 1; y <= posY + 1; y++) {
            for (int x = posX - 1; x <= posX + 1; x++) {
                int nbNeighborhood = countNeighborhood(grid, x, y);
                if (nbNeighborhood != 3 && (!grid.containsKey(y) || !grid.get(y).containsKey(x)))
                    continue;

                if (nbNeighborhood == 3 && (!grid.containsKey(y) || !grid.get(y).containsKey(x))) {
                    if (!grid.containsKey(y))
                        grid.put(y, new TreeMap<Integer, Cell>());
                    grid.get(y).put(x, new Cell(cycle));

                }
                if (grid.get(y).get(x).getCycle() < cycle)
                    grid.get(y).get(x).update(nbNeighborhood);
            }
        }
        return 0; 
    }

    public void main(String[] args)
    {
        TreeMap<Integer, TreeMap<Integer, Cell>> grid = new TreeMap<Integer, TreeMap<Integer, Cell>>();

        grid.put(0, new TreeMap<Integer, Cell>());
        grid.put(1, new TreeMap<Integer, Cell>());
        grid.put(2, new TreeMap<Integer, Cell>());
        grid.get(0).put(0, new Cell(0));
        grid.get(1).put(1, new Cell(0));
        grid.get(1).put(2, new Cell(0));
        grid.get(2).put(0, new Cell(0));
        grid.get(2).put(1, new Cell(0));

        while (1 == 1) {
            ArrayList<Map.Entry<Integer, TreeMap<Integer, Cell>>> gridIt = new ArrayList<Map.Entry<Integer, TreeMap<Integer, Cell>>>(grid.entrySet());
            for(Map.Entry<Integer, TreeMap<Integer, Cell>> entry : gridIt) {
                int y = entry.getKey();
                TreeMap<Integer, Cell> rows = entry.getValue();

                ArrayList<Map.Entry<Integer, Cell>> cellsIt = new ArrayList<Map.Entry<Integer, Cell>>(rows.entrySet());
                for (Map.Entry<Integer, Cell> cells :  cellsIt) {
                    int x = cells.getKey();
                    Cell cell = cells.getValue();

                    if (!cell.isAlive(cycle))
                        grid.get(y).remove(x);
                    checkNeighborhood(grid, x, y);
                }
                if (grid.get(y).isEmpty())
                    grid.remove(y);
            }

            cycle++;
            
            // print only on specific square
            System.out.println("new generation :");
            for (int y = 0; y < MAX_Y; y++) {
                if (!grid.containsKey(y)) {
                    for (int i = 0; i < MAX_X; i++)
                        System.out.print(" ");
                    System.out.println();
                    continue;
                }
                for (int x = 0; x < MAX_X; x++) {
                    if (!grid.get(y).containsKey(x)) {
                        System.out.print( " ");
                        continue;
                    }
                    System.out.print(grid.get(y).get(x).isAlive(cycle) ? "X" : " ");
                }
                System.out.println();
            }
            System.out.println();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.err.println("stop");
                Thread.currentThread().interrupt();
            }
        }
    }
}