package com.javadev.training.blockchain;

import java.io.Serializable;
import java.util.*;

public class Blockchain implements Serializable {
  protected static final long serialVersionUID = 1L;
  
  private static final String DEFAULT_PREV_HASH = "0";
  private final int minePrefix;
  
  private final List<Block> chain = new LinkedList<>();
  
  public Blockchain() {
    this(0);
  }
  
  public Blockchain(int minePrefix) {
    this.minePrefix = minePrefix;
  }
  
  public String getMinePrefixStr() {
    return !chain.isEmpty() ? chain.get(0).getPrefixStr() : null;
  }
  
  public void buildNextBlock() {
    buildNextBlock("");
  }
  
  public Block buildNextBlock(String metadata) {
    final boolean empty = chain.isEmpty();
    final int size = chain.size();
    final Block block = new Block(
        empty ? 1 : size + 1,
        empty ? DEFAULT_PREV_HASH : chain.get(size - 1).getHash(),
        metadata,
        minePrefix
    );
    chain.add(block);
    return block;
  }
  
  /**
   * Validate that the entire blockchain satisfies the following prerequisites:
   * 1. The hash of the block matches the hash generated from the value of its attributes.
   * 2. The reference to hash of previous block is only the default for the first block and,
   * 2.1. for rest, the reference to previous hash of current block is equal to the hash of previous block.
   * 3. The block hash complies with the preset prefix precondition.
   * 4. The chain can't contain repeated hashes.
   *
   * @return indicate if the blockchain is correctly formed.
   */
  public boolean isValid() {
    boolean valid = true;
    ListIterator<Block> it = chain.listIterator();
    if (!chain.isEmpty()) {
      Block c;                      // Current block vessel.
      Block p = it.next();          // Prev block would be the first in the list.
      String tmpCH;                 // Temporal current hash reference.
      String tmpPH;                 // Temporal previous hash reference.
      Set<String> uniqueHashChain = new LinkedHashSet<>();
      do {
        c = it.next();
        tmpCH = c.getHash();
        tmpPH = c.getPreviousHash();
        valid = tmpCH.equals(c.computeCurrentHash()) && // Comparing the current hash with a regenerated one.
            tmpPH.equals(p.getHash()) &&                // Check that the previous hash are equals.
            c.verifyMineCriteria(getMinePrefixStr()) && // Check that current and assigned hash have the same prefix code.
            uniqueHashChain.add(tmpCH);                 // Ensuring there are no repeated hashes.
        p = c;
      } while (it.hasNext() && valid);
    }
    return valid;
  }
  
  public void destroy() {
    chain.clear();
  }
  
  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    for (Block block : chain) {
      buffer.append(block.toString()).append("\n\n");
    }
    return buffer.toString();
  }
}
