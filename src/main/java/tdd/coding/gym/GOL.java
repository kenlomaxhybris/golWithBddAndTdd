package tdd.coding.gym;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GOL {

    Set<Cell> allCells = new TreeSet();
    IGOLEvolver evolver;

    public GOL(IGOLEvolver evolver){
     this.evolver = evolver;
    }

    public int getPopulationSize() {
        return allCells.size();
    }

    public GOL populate(String xys) {
        allCells = Cell.createCells(xys);
        return this;
    }

    public String show( int radius  ){
        StringBuilder sb= new StringBuilder();
        for (int y = radius; y >= -radius; y --){
            for (int x = -radius; x < radius; x ++){
                String coords =x+","+y;
                sb.append( allCells.contains(new Cell(coords))?"+": "-");
            }
            sb.append(" ");
        }
        return sb.toString();
    }

    public GOL evolve(){
        allCells = evolver.evolve(allCells);
        return this;
    }

    public void play(int iterations, int radius ){
        while(iterations-- > 0){
            System.out.println(show(radius));
            evolve();
        }
    }

}

class Cell implements Comparable{
    int x;
    int y;

    public Cell(String xy) { // "0,0"
        x = Integer.parseInt( xy.substring(0, xy.indexOf(",")));
        y = Integer.parseInt(  xy.substring(xy.indexOf(",") +1 ));
    }

    public static Set<Cell>createCells( String xys ){  // "-1,0, 0,0, 1,0, 1,0"
        if (xys.isEmpty())
            return new TreeSet();

        return new TreeSet( Stream.of(   xys.split(", ") ).map(xy -> new Cell(xy)).collect(Collectors.toSet()) );
    }

    @Override
    public int compareTo(Object o) {
        Cell other = (Cell)o;
        if (x==other.x)
            return y-other.y;
        return x-other.x;
    }

    public String toString(){
        return x+","+y;
    }
}