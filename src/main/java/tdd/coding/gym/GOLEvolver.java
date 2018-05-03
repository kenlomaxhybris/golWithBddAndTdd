package tdd.coding.gym;

import java.util.*;

public class GOLEvolver implements IGOLEvolver {

    public Set<Cell> allNeighbours = new TreeSet();
    public SortedMap<Cell, Integer> mappingOfCellsToLiveNeighbours = new TreeMap();

    public Set<Cell> evolve(Set<Cell>from){
        locateNeighbours(from);
        mapNeighbours(from);

        Set<Cell> nextGeneration = new TreeSet<>();
        from.forEach(cell -> {
                    int n = mappingOfCellsToLiveNeighbours.get(cell);
                    if (n==2 || n==3)
                        nextGeneration.add(cell);
                }
        );
        allNeighbours.forEach(cell -> {
                    int n = mappingOfCellsToLiveNeighbours.get(cell);
                    if (n==3)
                        nextGeneration.add( cell);
                }
        );
        return nextGeneration;
    }

    private Set<Cell> locateNeighbours( Set<Cell>from ){
        allNeighbours.clear();
        Set<Cell> neighboursOfLiveCells = new TreeSet<>();
        from.stream().forEach(cell -> {
                    int x = cell.x;
                    int y = cell.y;
                    neighboursOfLiveCells.add( new Cell((x-1)+","+(y-1)));
                    neighboursOfLiveCells.add( new Cell((x-1)+","+(y-0)));
                    neighboursOfLiveCells.add( new Cell((x-1)+","+(y+1)));
                    neighboursOfLiveCells.add( new Cell((x-0)+","+(y-1)));
                    neighboursOfLiveCells.add( new Cell((x-0)+","+(y+1)));
                    neighboursOfLiveCells.add( new Cell((x+1)+","+(y-1)));
                    neighboursOfLiveCells.add( new Cell((x+1)+","+(y-0)));
                    neighboursOfLiveCells.add( new Cell((x+1)+","+(y+1)));
                }
        );
        neighboursOfLiveCells.removeAll(from);
        allNeighbours = neighboursOfLiveCells;
        return allNeighbours;
    }

    private SortedMap<Cell, Integer> mapNeighbours( Set<Cell>from ){
        from.stream().forEach(cell ->  setAllNeighbours(from, cell) );
        allNeighbours.stream().forEach(cell ->  setAllNeighbours(from, cell) );
        return mappingOfCellsToLiveNeighbours;
    }

    private void setAllNeighbours( Set<Cell>from , Cell cell) {
        int liveNeighbours = 0;
        for (int dx=-1; dx<=1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0)
                    continue;
                Cell neighbourCoords = new Cell((cell.x + dx) + "," + (cell.y + dy));
                if (from.contains(neighbourCoords))
                    liveNeighbours++;
            }
        }
        mappingOfCellsToLiveNeighbours.put( cell, liveNeighbours );
    }
}
