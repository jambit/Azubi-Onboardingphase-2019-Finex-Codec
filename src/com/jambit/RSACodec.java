package com.jambit;

import java.math.BigInteger;

public class RSACodec {

    public BigInteger primeNumber(int bitSize){
        long bs =(long)Math.pow(2,bitSize);
        new BigInteger(bs +"");
        return null;
    }

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
        System.out.println(out);
    }

    public String[] asymmetricKeyGenerator(String _p1, String _p2) {
        _p1 = _p1.replace(",", "");
        _p2 = _p2.replace(",", "");
        BigInteger p1 = new BigInteger(_p1);
        BigInteger p2 = new BigInteger(_p2);
        String[] keys = {"", ""};
        BigInteger N = p1.multiply(p2);
        BigInteger o = p1.subtract(BigInteger.ONE).multiply(p2.subtract(BigInteger.ONE));
        System.out.println(p1.gcd(p2) + "|" + o);
        BigInteger e = coPrimeFactors(N, o);
        System.out.println(e);
        BigInteger d = (e.modInverse(o));
        d = d.add(o);
        keys[0] = e + ":" + N;
        keys[1] = d + ":" + N;
        return keys;
    }

    private BigInteger coPrimeFactors(BigInteger factor, BigInteger o) {
        BigInteger out = null;
        for (BigInteger i = new BigInteger("1"); i.compareTo(o) < 0; i = i.add(new BigInteger("1"))) {
            if (!i.mod(new BigInteger("2")).equals(new BigInteger("0")) && !factor.mod(i).equals(new BigInteger("0")) && !o.mod(i).equals(new BigInteger("0"))) {
                out = i;
                break;
            }
        }
        return out;
    }
}
