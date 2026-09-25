import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class GameOfLife {
    private final static int MAX_X = 10;
    private final static int MAX_Y = 10;

    private final static int IS_ALIVE = 1;
    
    private final static int UNDER_POPULATED = 2;
    private final static int OVER_POPULATED = 3;

    

    private static int nbAlive(int array[][], int posX, int posY)
    {
        int nbAlive = 0;
        for (int y = posY - 1; y <= posY + 1; y++) {
            for (int x = posX - 1; x <= posX + 1; x++) {
                if (x < 0  || x >= MAX_X || y < 0  || y >= MAX_Y || (x == posX  && y == posY) )
                    continue;
                if (array[y][x] >= IS_ALIVE)
                    nbAlive += 1;
            }
        }
        return nbAlive;
    }

    public static void main(String[] args)
    {
        int array[][] = new int[MAX_Y][MAX_X];
        int update[][] = new int[MAX_Y][MAX_X];

        array[5][5] = 1;
        array[5][4] = 1;
        array[5][6] = 1;
        
        while (1 == 1) {
            for (int y = 0; y < MAX_Y; y++)
                Arrays.fill(update[y], 0);

            for (int y = 0; y < MAX_Y; y++) {
                for (int x = 0; x < MAX_X; x++) {
                    int alive = nbAlive(array, x, y);
                    if (array[y][x] >= IS_ALIVE && alive < UNDER_POPULATED)
                        update[y][x] = 0;
                    if (array[y][x] >= IS_ALIVE && alive > OVER_POPULATED)
                        update[y][x] = 0;
                    if (array[y][x] >= IS_ALIVE && alive <= OVER_POPULATED && alive >= UNDER_POPULATED)
                        update[y][x] = 1;
                    if (array[y][x] < IS_ALIVE && alive == OVER_POPULATED)
                        update[y][x] = 1;
                }
            }


            for (int y = 0; y < MAX_Y; y++)
                for (int x = 0; x < MAX_X; x++)
                    array[y][x] = update[y][x];
            
            System.out.println("new generation :");
            for (int y = 0; y < MAX_Y; y++) {
                for (int x = 0; x < MAX_X; x++) {
                    System.out.print(array[y][x] >= IS_ALIVE ? "X" : " ");
                }
                System.out.println();
            }
            System.out.println();

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.err.println("stop");
                Thread.currentThread().interrupt();
            }
        }
    }
}