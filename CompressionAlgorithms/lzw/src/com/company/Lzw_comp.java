package com.company;
import java.util.Scanner;
public class Lzw_comp {

    public static void main(String[] args) {
        Scanner input=new Scanner ( System.in );
        String data=input.next ();
        lzw obj=new lzw(data);
        obj.compress();
        obj.printTags();
        obj.Decompress();
    }
}