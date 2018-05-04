package tdd.coding.gym

import org.junit.Rule
import org.springframework.boot.test.rule.OutputCapture
import spock.lang.Specification
import spock.lang.Unroll

class GOLSpec extends Specification {


    def "A new GOL populated with '-1,0, 0,0, 1,0, 1,0' evolves to '0,-1, 0,0, 0,1'"(){
        given:
        def evolver = new GOLEvolver();
        and:
        def gol = new GOL( evolver )
        gol.populate("-1,0, 0,0, 1,0, 1,0")

        when:
        gol.evolve()

        then:
        gol.allCells.toString() == "[0,-1, 0,0, 0,1]"
    }


    @Rule
    OutputCapture oc = new OutputCapture();
    def "Wheny you play GOL you should see ..."(){
        given:
        def evolver = new GOLEvolver();
        and:
        def gol = new GOL( evolver )
        gol.populate("-1,0, 0,0, 1,0, 1,0")

        when:
        gol.play( 20, 5 )

        then:
        oc.toString().contains("+++");

    }

    @Unroll
    def "A new GOL populated with #xys has #size cells, namely #cells"(){
        given:
        def gol = new GOL();
        gol.populate( xys)

        expect:
        gol.getPopulationSize()== size
        gol.allCells.toString() == cells

        where:
        xys||size||cells
        "" || 0 || "[]"
        "0,0" || 1  || "[0,0]"
        "-1,0, 0,0, 1,0, 1,0" || 3 || "[-1,0, 0,0, 1,0]"

    }

    def "STUBBED  A new GOL populated with '-1,0, 0,0, 1,0, 1,0' evolves to '0,-1, 0,0, 0,1'"(){
        given:
        def evolver = Stub(IGOLEvolver)
        evolver.evolve(_) >> Cell.createCells( "0,-1, 0,0, 0,1")
        and:
        def gol = new GOL( evolver )
        gol.populate("-1,0, 0,0, 1,0, 1,0")

        when:
        gol.evolve()

        then:
        gol.allCells.toString() == "[0,-1, 0,0, 0,1]"
    }

    @Unroll
    def "A new GOL populated with #xys looks like #display"(){
        given:"We populate with #xys "
        def gol = new GOL();
        gol.populate( xys)

        expect:"The display includes #display  "
        gol.show(5)== display

        where:
        xys||display
        "" || "---------- ---------- ---------- ---------- ---------- ---------- ---------- ---------- ---------- ---------- ---------- "
        "0,0" || "---------- ---------- ---------- ---------- ---------- -----+---- ---------- ---------- ---------- ---------- ---------- "
        "-1,0, 0,0, 1,0, 1,0" || "---------- ---------- ---------- ---------- ---------- ----+++--- ---------- ---------- ---------- ---------- ---------- "

    }


}
