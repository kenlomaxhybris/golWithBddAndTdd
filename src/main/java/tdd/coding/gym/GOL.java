package tdd.coding.gym;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GOL {

   Set<Cell>allCells;
   IEvolver evolver;

   public GOL(IEvolver evolver){
      this.evolver= evolver;
   }

   public GOL evolve(Set<Cell> from){
      allCells = evolver.evolve(from);
      return this;
   }

   public GOL populate(String xys){ //"-1,0, 0,0, 1,0, 1,0"
      allCells=Cell.createCells(xys);
      return this;
   }

   public int getPopulationSize(){
      return allCells.size();
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

   public void play( int iter, int radius){
      while (iter-- >0){
         System.out.println(show(radius));
         evolve( allCells);
      }
   }

}

class Cell implements Comparable {
   int x;
   int y;


   public static Set<Cell> createCells(String xys){//"-1,0, 0,0, 1,0, 1,0"
      if (xys.isEmpty())
         return new TreeSet();
      return new TreeSet( Stream.of( xys.split(", ")).map(xy -> new Cell(xy)).collect(Collectors.toSet()));
   }

   public Cell(String xy){
      x = Integer.parseInt( xy.substring(0, xy.indexOf(",")));
      y = Integer.parseInt( xy.substring( xy.indexOf(",")+1 ));
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