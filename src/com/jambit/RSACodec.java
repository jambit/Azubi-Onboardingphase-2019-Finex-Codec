package com.jambit;

import javax.swing.*;
import java.util.ArrayList;

public class RSACodec {
    public String RSAEncoder(String msg, String key){
        String out = "";
        String[] keys = CaesarCodec.splitKey(key);
        for (int i = 0; i < msg.length(); i++) {
            int index = CaesarCodec.charSet.indexOf(msg.charAt(i)) + 1;
            long charAt = (long)(Math.pow(index,Long.parseLong(keys[0]))%Long.parseLong(keys[1]));
            System.out.println(charAt);
            while(charAt > CaesarCodec.charSet.length()){
                charAt -= CaesarCodec.charSet.length();
            }
            while (charAt<0){
                charAt += CaesarCodec.charSet.length();
            }
            char x;
            if (charAt == 0){
                x = CaesarCodec.charSet.charAt((int)charAt);
            }else{
                x = CaesarCodec.charSet.charAt((int)charAt-1);
            }
            out += x;
        }
        System.out.println(out);
        return out;
    }

    public void RSADecoder(String msg, String key){
        String out = "";
        String[] keys = CaesarCodec.splitKey(key);
        for (int i = 0; i < msg.length(); i++) {

        }
    }

    public String[] asymmetricKeyGenerator(long p1, long p2){
        String[] keys = {"",""};
        long N = p1 * p2;
        long o = (p1-1)*(p2-1);
        long e = commonFactors(N,o).get(0);
        long i = p1;
        long d;
        int j = 0;
        while(true){
            i++;
            if ((e*i)%o == 1){
                System.out.println(i);
                if(j > 0) {
                    d = i;
                    break;
                }
                j++;
            }
        }
        keys[0] = e + ":" + N;
        keys[1] = d + ":" + N;
        return keys;
    }

    private ArrayList<Long> commonFactors(long factor, long o){
        ArrayList<Long> arrayList = new ArrayList<Long>();
        for (long i = 1; i < o; i++) {
            if(factor % i != 0 && o % i != 0 && i%2 != 0){
                arrayList.add(i);
            }
        }
        return arrayList;
    }
}
