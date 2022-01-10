package com.company;

import java.util.Scanner;

public class Lz78_comp {

    public static void main(String[] args) {
        Scanner input=new Scanner ( System.in );
        String data=input.next ();
        lz78 obj=new lz78(data);
        obj.compress();
        obj.printTags();
        obj.Decompress();
    }
}