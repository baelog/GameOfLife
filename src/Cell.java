public class Cell {
    private final static int UNDER_POPULATED = 2;
    private final static int OVER_POPULATED = 3;

    private int _state;
    private int _cycle;

    Cell(int cycle)
    {
        _cycle = cycle;
        _state = 3;
    }


    public boolean isAlive(int globalCycle)
    {
        if (globalCycle > _cycle)
            return (_state % 10 >= UNDER_POPULATED && _state % 10 <= OVER_POPULATED);
        return (_state % 100 / 10 >= UNDER_POPULATED && _state % 100 / 10 <= OVER_POPULATED);
    }

    public void update(int neighborhoods)
    {
        _state = (_state * 10 + neighborhoods) % 100;
        _cycle++;
    }

    public int getCycle() {
        return _cycle;
    }
    
}
