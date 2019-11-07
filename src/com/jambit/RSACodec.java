package com.jambit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;

public class RSACodec {

  private final int CHAR_PER_THREAD = 20;
  private final int MAX_THREAD_COUNT = 30;

  public String RSAEncrypt(String message, String key) {
    StringBuilder encryptedMessage = new StringBuilder();
    String[] separatedKeys = CaesarCodec.splitKey(key);

    ArrayList<EncryptThread> activeThreads = new ArrayList<EncryptThread>();
    int threadCount = message.length() / CHAR_PER_THREAD;
    if (threadCount == 0) threadCount = 1;
    else if (threadCount > MAX_THREAD_COUNT) threadCount = MAX_THREAD_COUNT;

    // decodes keys
    separatedKeys[0] = new String(Base64.getDecoder().decode(separatedKeys[0]));
    separatedKeys[1] = new String(Base64.getDecoder().decode(separatedKeys[1]));

    // adds n+1 new thread when the message cant be divided equally
    int threadMessageLength = message.length() / threadCount;
    if (message.length() % threadCount != 0) {
      threadCount++;
    }

    // Create Threads
    for (int i = 0; i < threadCount; i++) {
      int startCharIndex = i * threadMessageLength;
      int endCharIndex = (i + 1) * threadMessageLength;

      if (endCharIndex >= message.length()) {
        endCharIndex = message.length();
      }

      activeThreads.add(
          new EncryptThread(message.substring(startCharIndex, endCharIndex), separatedKeys));
      activeThreads.get(i).start();
    }

    // Join threads
    message = null;
    encryptThreadPercentageBar(activeThreads);
    for (int i = 0; i < threadCount; i++) {
      try {
        activeThreads.get(i).join();
        encryptedMessage.append(activeThreads.get(i).out);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    return encryptedMessage.toString();
  }

  public String RSADecrypt(String msg, String key) {
    StringBuilder out = new StringBuilder();
    String[] vars = msg.split(" ");
    for (int i = 0; i < vars.length; i++) {
      vars[i] = new String(Base64.getDecoder().decode(vars[i]));
    }

    String[] separatedKeys = CaesarCodec.splitKey(key);
    separatedKeys[0] = new String(Base64.getDecoder().decode(separatedKeys[0]));
    separatedKeys[1] = new String(Base64.getDecoder().decode(separatedKeys[1]));

    for (String var : vars) {
      BigInteger bi = new BigInteger(var).add(BigInteger.ONE);
      bi = bi.modPow(new BigInteger(separatedKeys[0]), new BigInteger(separatedKeys[1]));
      if (bi.longValue() == 0) {
        out.append(CaesarCodec.charSet.charAt(bi.intValue()));
      } else {
        out.append(CaesarCodec.charSet.charAt(bi.subtract(BigInteger.ONE).intValue()));
      }
    }
    return out.toString();
  }

  public void encryptThreadPercentageBar(ArrayList<EncryptThread> threads) {
    long toDo = 0;
    long done = 0;
    double percentage = 0;
    boolean finished = false;
    for (int i = 0; i < threads.size(); i++) {
      toDo += threads.get(i).toDo;
    }
    System.out.print("toDo: " + toDo);
    while (!finished) {
      done = 0;
      for (int i = 0; i < threads.size(); i++) {
        done += threads.get(i).done;
      }
      percentage = (double) done / toDo;
      StringBuilder percent = new StringBuilder();
      percent.append('\r').append(percentage * 100).append('%');
      System.out.println(percent);
      if (toDo <= done) {
        finished = true;
      }
      try {
        Thread.sleep(1500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public String[] asymmetricKeyGenerator(String firstPrimeNumber, String secondPrimeNumber) {
    BigInteger firstPrimeNumberBigInt = new BigInteger(firstPrimeNumber.replace(",", ""));
    BigInteger secondPrimeNumberBigInt = new BigInteger(secondPrimeNumber.replace(",", ""));
    String[] keys = new String[2];
    BigInteger N = firstPrimeNumberBigInt.multiply(secondPrimeNumberBigInt);
    BigInteger o =
        firstPrimeNumberBigInt
            .subtract(BigInteger.ONE)
            .multiply(secondPrimeNumberBigInt.subtract(BigInteger.ONE));
    BigInteger e = coPrimeFactors(N, o);
    BigInteger d = e.modInverse(o);
    d = d.add(o);
    keys[0] =
        Base64.getEncoder().encodeToString(e.toString().getBytes())
            + ":"
            + Base64.getEncoder().encodeToString(N.toString().getBytes());
    keys[1] =
        Base64.getEncoder().encodeToString(d.toString().getBytes())
            + ":"
            + Base64.getEncoder().encodeToString(N.toString().getBytes());
    return keys;
  }

  private BigInteger coPrimeFactors(BigInteger factor, BigInteger o) {
    BigInteger out = null;
    for (BigInteger i = BigInteger.ONE; i.compareTo(o) < 0; i = i.add(BigInteger.ONE)) {
      if (!i.mod(new BigInteger("2")).equals(BigInteger.ZERO)
          && !factor.mod(i).equals(BigInteger.ZERO)
          && !o.mod(i).equals(BigInteger.ZERO)) {
        out = i;
        break;
      }
    }
    return out;
  }

  private static class EncryptThread extends Thread {
    public long toDo = 0;
    public long done = 0;

    String msg;
    String[] keys;
    public StringBuilder out = new StringBuilder();

    public EncryptThread(String msg, String[] keys) {
      toDo += msg.length();
      this.msg = msg;
      this.keys = keys;
    }

    @Override
    public void run() {
      BigInteger bi;
      for (int i = 0; i < msg.length(); i++) {
        bi =
            new BigInteger((CaesarCodec.charSet.indexOf(msg.charAt(i)) + 1) + "")
                .modPow(new BigInteger(keys[0]), new BigInteger(keys[1]));
        if (bi.longValue() == 0) {
          out.append(Base64.getEncoder().encodeToString((bi + "").getBytes())).append(" ");
        } else {
          out.append(
                  Base64.getEncoder().encodeToString((bi.subtract(BigInteger.ONE) + "").getBytes()))
              .append(" ");
        }
        done++;
      }
      // System.out.println(this.getName() + " has finished!");
    }
  }
}
