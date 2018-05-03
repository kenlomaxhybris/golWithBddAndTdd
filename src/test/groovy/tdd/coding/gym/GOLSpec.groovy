package tdd.coding.gym

import org.junit.Rule
import org.springframework.boot.test.rule.OutputCapture
import spock.lang.Specification
import spock.lang.Unroll

class GOLSpec extends Specification {


    @Unroll
    def "A new GOL populated with #xys looks like #display"(){
        given:
        def gol = new GOL();
        gol.populate(xys)
        expect:
        gol.show(5) == display
        where:
        xys || display
        "" || "---------- ---------- ---------- ---------- ---------- ---------- ---------- ---------- ---------- ---------- ---------- "
        "0,0"|| "---------- ---------- ---------- ---------- ---------- -----+---- ---------- ---------- ---------- ---------- ---------- "
        "-1,0, 0,0, 1,0"|| "---------- ---------- ---------- ---------- ---------- ----+++--- ---------- ---------- ---------- ---------- ---------- "
    }

    @Unroll
    def "A new GOL populated '#xys' with has a population of #size, namely #cells"(){
        given:
        def gol = new GOL();
        gol.populate(xys)
        expect:
        gol.populationSize == size
        gol.allCells.toString() == cells
        where:
        xys || size || cells  // [-1,0, 0,0, 1,0]
        "" || 0 || "[]"
        "0,0" || 1 || "[0,0]"
        "-1,0, 0,0, 1,0, 1,0" || 3 || "[-1,0, 0,0, 1,0]"
    }

    def "STUBBED A new GOL populated '-1,0, 0,0, 1,0, 1,0' evolves to ..."(){
        given:
        def evolver = Stub(IEvolver)
        evolver.evolve( _ ) >> Cell.createCells("0,-1, 0,0, 0,1")

        and:
        def gol = new GOL( evolver );
        gol.populate("-1,0, 0,0, 1,0, 1,0")

        expect:
        gol.evolve( Cell.createCells("-1,0, 0,0, 1,0, 1,0") ).allCells.toString() == "[0,-1, 0,0, 0,1]"
    }


    def "A new GOL populated '-1,0, 0,0, 1,0, 1,0' evolves to ..."(){
        given:
        def gol = new GOL( new GOLEvolver() );
        gol.populate("-1,0, 0,0, 1,0, 1,0")

        expect:
        gol.evolve(Cell.createCells("-1,0, 0,0, 1,0, 1,0")).allCells.toString() == "[0,-1, 0,0, 0,1]"
    }
    
    @Rule
    OutputCapture oc = new OutputCapture()
    def "When GOL is played, we see expected output"(){
        given:
        def gol = new GOL(new GOLEvolver());
        gol.populate("-1,0, 0,0, 1,0")
        when:
        gol.play(20, 5)

        then:
        oc.toString().contains("+++")

    }

}
