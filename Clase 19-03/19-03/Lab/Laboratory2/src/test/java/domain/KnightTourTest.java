package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTourTest {

    @Test
    void printSolution() {

        KnightTour kt = new KnightTour();

        System.out.println("-- KNIGHT TOUR PROBLEM SOLUTION --\n");
        kt.solveKnightTour();
        System.out.println(kt.printSolution());

    }


}