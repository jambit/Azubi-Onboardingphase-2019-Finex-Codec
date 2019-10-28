package com.jambit;

public class Core {
    public static void main(String[] args) throws Exception {
        UserInterface input = new UserInterface();
        input.menu();
        String nut = input.getMessage();
        String nut2 = input.getEncryptionKey();
        System.out.println(nut);
        System.out.println(nut2);
    }
}
