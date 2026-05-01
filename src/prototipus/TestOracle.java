package prototipus;

import java.io.File;

public class TestOracle {
    public static void main(String[] args) {
        System.out.println("Test Oracle: A tesztek helyes működésének ellenőrzése");
        File testFolder = new File("tests");
        File assertFolder = new File("asserts");
    }

    public static void RunTest(String testName) {
        System.out.println("Running test: " + testName);
    }

    public static void BatchRunTests(){
        System.out.println("Batch running all tests...");
    }
}
