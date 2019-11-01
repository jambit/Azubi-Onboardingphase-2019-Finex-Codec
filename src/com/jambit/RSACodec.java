package com.jambit;

import java.math.BigInteger;

public class RSACodec {

  public String RCAEncrypt(String msg, String key) {
    String out = "";
    String[] keys = CaesarCodec.splitKey(key);
    for (int i = 0; i < msg.length(); i++) {
      long index = CaesarCodec.charSet.indexOf(msg.charAt(i)) + 1;
      BigInteger bi = new BigInteger(index + "");
      bi = bi.modPow(new BigInteger(keys[0]), new BigInteger(keys[1]));
      String x;
      if (bi.longValue() == 0) {
        x = bi.longValue() + "";
      } else {
        x = ((bi.longValue() - 1) + "");
      }
      out += x + " ";
    }
    System.out.println(out);
    return out;
  }

  public void RSADecoder(String msg, String key) {
    String out = "";
    String[] vars = msg.split(" ");

    String[] keys = CaesarCodec.splitKey(key);
    for (int i = 0; i < vars.length; i++) {
      long index = Long.parseLong(vars[i]) + 1;
      BigInteger bi = new BigInteger(index + "");
      bi = bi.modPow(new BigInteger(keys[0]), new BigInteger(keys[1]));
      String x;
      if (bi.longValue() == 0) {
        x = CaesarCodec.charSet.charAt(bi.intValue()) + "";
      } else {
        x = CaesarCodec.charSet.charAt(bi.intValue() - 1) + "";
      }
      out += x;
    }
    System.out.println(out);
  }

  public String[] asymmetricKeyGenerator(long p1, long p2) {
    String[] keys = {"", ""};
    long N = p1 * p2;
    long o = (p1 - 1) * (p2 - 1);
    long e = commonFactors(N, o);
    long i = 1;
    long d;
    int j = 1;
    long first = 0;
    while (true) {
      if ((e * i) % o == 1) {
        j++;
        d = i;
        if (first == 0) {
          first = d;
        } else {
          i = j * first;
        }
        System.out.println(d + "/" + e);
        if (j > 2) {
          break;
        }
      }
      i += 2;
    }
    keys[0] = e + ":" + N;
    keys[1] = d + ":" + N;
    return keys;
  }

  private long commonFactors(long factor, long o) {
    long out = 0;
    for (long i = 1; i < o; i++) {
      if (factor % i != 0 && o % i != 0 && i % 2 != 0) {
        out = i;
        if (Math.round(Math.random() * 100) > 1) {
          break;
        }
      }
    }
    return out;
  }
}
