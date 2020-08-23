package com.javadev.training.blockchain;

import com.javadev.training.util.CryptTool;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Block implements Serializable {
  protected static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(Block.class.getName());
  
  private int nonce;                  // An arbitrary number used for encryption.
  private final int prefix;           // Prefix (# of 0s) we desired to find mining (at the very beginning of) the block.
  private final String prefixStr;     // Arbitrary string to find desired prefix string pattern.
  private final String metadata;      // Custom data stored by the current block.
  
  private final int id;
  private final long timestamp;       // integer that represent a time mark of the moment of creation of the block.
  private final String previousHash;  // reference to the previous block hash code.
  private String hash;                // hash string for the current block.
  
  public Block(int id, String previousHash, String metadata, int minePrefix) {
    this.prefix = minePrefix;
    this.prefixStr = new String(new char[prefix]).replace('\0', '0');
    this.metadata = metadata;
    
    this.id = id;
    this.timestamp = new Date().getTime();
    this.previousHash = previousHash;
    this.hash = computeCurrentHash();
    
    this.mine();
    logger.log(Level.FINE, "Block created!");
  }
  
  private void mine() {
    while (!verifyMineCriteria(getPrefixStr())) {
      nonce++;
      hash = computeCurrentHash();
    }
    logger.log(Level.FINE, "Block mined successfully!");
  }
  
  //region Properties
  
  public int getId() {
    return id;
  }
  
  public long getTimestamp() {
    return timestamp;
  }
  
  public String getPreviousHash() {
    return previousHash;
  }
  
  public String getHash() {
    return hash;
  }
  
  public String getPrefixStr() {
    return prefixStr;
  }
  
  public String getMetadata() {
    return metadata;
  }
  
  //endregion
  
  public boolean verifyMineCriteria(String pattern) {
    return hash.substring(0, prefix).equals(pattern);
  }
  
  public String computeCurrentHash() {
    return CryptTool.getSHA256(getPreviousHash() + getTimestamp() + nonce + getMetadata());
  }
  
  @Override
  public String toString() {
    return "Block:\n" +
        "Id: " + getId() + "\n" +
        "Timestamp: " + getTimestamp() + "\n" +
        "Hash of the previous block:\n" +
        getPreviousHash() + "\n" +
        "Hash of the block:\n" +
        getHash();
  }
}
