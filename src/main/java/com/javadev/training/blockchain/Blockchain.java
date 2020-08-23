package com.javadev.training.blockchain;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Blockchain implements Serializable {
  protected static final long serialVersionUID = 1L;
  
  private static final String DEFAULT_PREV_HASH = "0";
  private final int minePrefix;
  
  private final List<Block> blocks = new LinkedList<>();
  
  public Blockchain() {
    this(0);
  }
  
  public Blockchain(int minePrefix) {
    this.minePrefix = minePrefix;
  }
  
  public String getMinePrefixStr() {
    return !blocks.isEmpty() ? blocks.get(0).getPrefixStr() : null;
  }
  
  public void buildNextBlock() {
    buildNextBlock("");
  }
  
  public Block buildNextBlock(String metadata) {
    boolean empty = blocks.isEmpty();
    final int size = blocks.size();
    final Block block = new Block(
        empty ? 1 : size + 1,
        empty ? DEFAULT_PREV_HASH : blocks.get(size - 1).getHash(),
        metadata,
        minePrefix
    );
    blocks.add(block);
    return block;
  }
  
  /**
   * Validate that the entire blockchain satisfies the following prerequisites:
   * 1. The hash of the block matches the hash generated from the value of its attributes.
   * 2. That the reference to the hash of the previous block is only the default for the first block and,
   * 2.1. for others, the reference to the previous hash of the current block is equal to the hash of the previous block.
   * 3. The block hash complies with the preset prefix precondition.
   *
   * @return indicate if the blockchain is correctly formed.
   */
  public boolean validateChain() {
    boolean flag = true;
    
    int counter = 0;
    int blockSize = blocks.size();
    
    Block prev = null;
    Block current;
    String currentPrevHashRef;
    Set<String> bcHashes = new LinkedHashSet<>();
    while (blockSize > counter && flag) {
      current = blocks.get(counter++);
      currentPrevHashRef = current.getPreviousHash();
      
      flag = current.getHash().equals(current.computeCurrentHash()) && // Comparing the current hash with a regenerated one.
          
          // verify that previous hash reference correspond in both blocks.
          currentPrevHashRef.equals(prev != null ? prev.getHash() : DEFAULT_PREV_HASH) &&
          
          // verify that no hash in the chain repeats.
          bcHashes.add(currentPrevHashRef) &&
          
          // Verify that the generated and assigned hash has the same prefix string previously defined.
          current.verifyMineCriteria(getMinePrefixStr());
      
      prev = current;
    }
    return flag;
  }
  
  public void breakChain() {
    blocks.clear();
  }
  
  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    for (Block block : blocks) {
      buffer.append(block.toString()).append("\n\n");
    }
    return buffer.toString();
  }
}
