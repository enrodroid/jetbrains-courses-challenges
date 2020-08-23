package com.javadev.training.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CryptTool {
  private static final Logger logger = Logger.getLogger(CryptTool.class.getName());
  
  private CryptTool() {
    throw new UnsupportedOperationException();
  }
  
  public static String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder();
    for (byte item : hash) {
      String hex = Integer.toHexString(0xff & item);
      if (hex.length() == 1) hexString.append('0');
      hexString.append(hex);
    }
    return hexString.toString();
  }
  
  public static String getSHA256(String input) {
    String hashCodeResult = "NO HASH GENERATED! Please check logs.";
    try {
      // Starting from JDK 9, we can simply use the built-in SHA3-256 algorithm
      final MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));  //Applies sha256 to our input
      hashCodeResult = bytesToHex(hash);
    } catch (NoSuchAlgorithmException ex) {
      logger.log(Level.SEVERE, ex.getMessage());
    }
    return hashCodeResult;
  }
}
