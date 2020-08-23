package com.javadev.training;

import com.javadev.training.blockchain.Block;
import com.javadev.training.blockchain.Blockchain;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockchainUnitTest {
  private static final Logger logger = Logger.getLogger(BlockchainUnitTest.class.getName());
  
  private static final int PREFIX = 4;
  private static final String PREFIX_STR = new String(new char[PREFIX]).replace('\0', '0');
  
  private static final Blockchain blockchain = new Blockchain(PREFIX);
  
  @BeforeAll
  static void beforeClass() {
    blockchain.buildNextBlock("I'm the very first block.");
    blockchain.buildNextBlock("I'm the first next one block.");
    logger.log(Level.INFO, "Before class setup executed!");
  }
  
  @Test
  void whenAddBlockToBlockchainCheckHashMiningThenSuccess() {
    Block newBlock = blockchain.buildNextBlock("I'm the 2nd next one block.");
    assert newBlock.verifyMineCriteria(PREFIX_STR);
    logger.log(Level.INFO, "Testing criteria of hashing success!");
  }
  
  @Test
  void whenValidateBlockchainThenSuccess() {
    assertTrue(blockchain.isValid());
    logger.log(Level.INFO, "Testing validity of entire blockchain terminated!");
  }
  
  @AfterAll
  static void tearDown() {
    blockchain.destroy();
    logger.log(Level.INFO, "Testing process terminated!");
  }
}
