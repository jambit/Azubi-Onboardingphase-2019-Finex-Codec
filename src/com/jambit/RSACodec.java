package com.jambit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;

public class RSACodec {

  private final int CHAR_PER_THREAD = 20;

  public String RSAEncrypt(String message, String key) {
    StringBuilder encryptedMessage = new StringBuilder();
    String[] keys = CaesarCodec.splitKey(key);

    ArrayList<Encrypt> activeThreads = new ArrayList<Encrypt>();
    int threadCount = message.length() / CHAR_PER_THREAD;
    if (threadCount == 0) threadCount = 1;

    // decodes keys
    keys[0] = new String(Base64.getDecoder().decode(keys[0]));
    keys[1] = new String(Base64.getDecoder().decode(keys[1]));

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

      activeThreads.add(new Encrypt(message.substring(startCharIndex, endCharIndex), keys));
      activeThreads.get(i).start();
    }

    // Join threads
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

    String[] keys = CaesarCodec.splitKey(key);
    byte[] base64ByteArrayKey1 = Base64.getDecoder().decode(keys[0]);
    byte[] base64ByteArrayKey2 = Base64.getDecoder().decode(keys[1]);
    keys[0] = new String(base64ByteArrayKey1);
    keys[1] = new String(base64ByteArrayKey2);

    for (int i = 0; i < vars.length; i++) {
      BigInteger bi = new BigInteger(vars[i]).add(BigInteger.ONE);
      bi = bi.modPow(new BigInteger(keys[0]), new BigInteger(keys[1]));
      String x;
      if (bi.longValue() == 0) {
        x = CaesarCodec.charSet.charAt(bi.intValue()) + "";
      } else {
        x = CaesarCodec.charSet.charAt(bi.intValue() - 1) + "";
      }
      out.append(x);
    }
    return out.toString();
  }

  public String[] asymmetricKeyGenerator(String _p1, String _p2) {
    _p1 = _p1.replace(",", "");
    _p2 = _p2.replace(",", "");
    BigInteger p1 = new BigInteger(_p1);
    BigInteger p2 = new BigInteger(_p2);
    String[] keys = {"", ""};
    BigInteger N = p1.multiply(p2);
    BigInteger o = p1.subtract(BigInteger.ONE).multiply(p2.subtract(BigInteger.ONE));
    BigInteger e = coPrimeFactors(N, o);
    BigInteger d = (e.modInverse(o));
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
    for (BigInteger i = new BigInteger("1"); i.compareTo(o) < 0; i = i.add(new BigInteger("1"))) {
      if (!i.mod(new BigInteger("2")).equals(new BigInteger("0"))
          && !factor.mod(i).equals(new BigInteger("0"))
          && !o.mod(i).equals(new BigInteger("0"))) {
        out = i;
        break;
      }
    }
    return out;
  }

  private class Encrypt extends Thread {
    String msg;
    String[] keys;
    public StringBuilder out = new StringBuilder();

    public Encrypt(String msg, String[] keys) {
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
      }
      System.out.println(this.getName() + " has finished!");
    }
  }
}
