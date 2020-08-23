package com.javadev.training;

import com.javadev.training.blockchain.Blockchain;

import static java.lang.System.out;

public class Main {
  
  /**
   * El resultado es 14 xq son numeros enteros, asi que al dividir 5/2 con esta agrupacion, la parte decimal es ignorada.
   */
  private static void operatorSeriesPrecedencesTests() {
    out.println((3 + 4) * (5 / 2));
  }
  
  /**
   * The operator == compare also data type. For compare just values use equals.
   */
  /*private static void operatorSeriesComparatorTests() {
    Integer a = new Integer(3);
    Integer b = new Integer(3);
    
    out.println(a == b);        // false
    out.println(a.equals(b));   // true
  }*/
  
  /**
   * A simple implementation of blockchain theory in cryptography field.
   */
  private static void blockchainHierarchyTests() {
    // IMPORTANT! The mine prefix must be less than 4 for demo purposes.
    Blockchain blockchain = new Blockchain(4);
    for (int i = 0; i < 5; i++) {
      blockchain.buildNextBlock("data extra");
    }
    out.print(blockchain);
    out.println("Valid blockchain?: " + (blockchain.validateChain() ? "YES" : "NOP"));
    blockchain.breakChain();
  }
  
  public static void main(String[] args) {
//    operatorSeriesPrecedencesTests();
//    out.println();
//    operatorSeriesComparatorTests();
//    out.println();
    blockchainHierarchyTests();
//    out.println();
  }
}
