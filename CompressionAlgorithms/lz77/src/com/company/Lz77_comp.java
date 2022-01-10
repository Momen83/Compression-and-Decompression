package com.company;

import java.util.Scanner;
import java.util.Vector;

public class Lz77_comp {

    public static void main ( String[] args ) {
        Scanner input=new Scanner ( System.in );
        String data=input.next ();
        lz77 obj =new lz77 ( data );
        obj.compress ();
        obj.printTags ();
        obj.decompressed();

    }
}
/*
  ABAABABAABBBBBBBBBBBBA
  AABABABBCBCBBBBBBBBBBBBBBBBBCBCBCBBBCABBA

 */