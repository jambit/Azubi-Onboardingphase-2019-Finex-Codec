package com.jambit;

import java.math.BigInteger;
import java.util.Base64;

public class RSACodec {

  public BigInteger primeNumberGenerator(int bitSize) {
    String number = "";
    for (int i = 0; i < bitSize; i++) {
      number += "9";
    }
    BigInteger bi = new BigInteger(number);
    while (!isPrime(bi)) {
      bi.add(BigInteger.ONE);
    }
    return bi;
  }

  public String RCAEncrypt(String msg, String key) {
    String out = "";
    String[] keys = CaesarCodec.splitKey(key);
    byte[] base64ByteArrayKey1 = Base64.getDecoder().decode(keys[0]);
    byte[] base64ByteArrayKey2 = Base64.getDecoder().decode(keys[1]);
    keys[0] = new String(base64ByteArrayKey1);
    keys[1] = new String(base64ByteArrayKey2);
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
    return out;
  }

  public String RSADecoder(String msg, String key) {
    String out = "";
    String[] vars = msg.split(" ");

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
      out += x;
    }
    return out;
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
    System.out.println(e);
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

  private boolean isPrime(BigInteger num) {
    for (BigInteger divider = BigInteger.ONE;
        num.compareTo(divider) > 0;
        divider = divider.add(BigInteger.TWO)) {
      if (!divider.equals(num) && !divider.equals(BigInteger.ONE)) {
        if (num.mod(divider).equals(BigInteger.ZERO)
            || num.sqrt().compareTo(divider) >= 0
            || num.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
          return false;
        }
      }
    }
    return true;
  }
}
