package com.company;

import java.util.HashMap;
import java.util.Vector;

public class lz78 {
    //String which will be compressed
    private String data;
    //Store a unique strings in it
    private HashMap<String, Integer> Dictionary=new HashMap<String, Integer>();//Creating HashMap
    // Store tags on it;
    private Vector<lz78_data>tags=new Vector<lz78_data>();
    // represent number of unique string in dictionary
    private int uniqueStrings;
    //parameterized constructor
    public lz78(String data){
        this.data=data;
        uniqueStrings =1;
    }
    public void compress(){
        String temp="";
        //Store previous temp
        String prevtemp="";
        // check if found in dictionary or not
        boolean ok=false;
        for(int i=0;i<data.length();i++){
            temp+=data.charAt(i);
            // if this string is unique
            if(Dictionary.containsKey(temp)==false){
                // Check if this substring of string in dictionary
                if(!ok){
                    lz78_data tempp = new lz78_data();
                    tempp.setIndex(0);
                    tempp.setNextChar(data.charAt(i));
                    tags.add(tempp);
                }
                else{
                    Integer val=Dictionary.get(prevtemp);
                    lz78_data tempp = new lz78_data();
                    tempp.setIndex(val);
                    tempp.setNextChar(data.charAt(i));
                    tags.add(tempp);
                }
                Dictionary.put(temp, uniqueStrings);
                temp="";
                prevtemp="";
                uniqueStrings++;
                ok=false;
            }
            else{
                prevtemp=temp;
                ok=true;
            }
        }
        //Check if string is found in dictionary
        if(ok){
            Integer val=Dictionary.get(prevtemp);
            lz78_data tempp = new lz78_data();
            tempp.setIndex(val);
            tempp.setNextChar('0');
            // System.out.println(tempp.getIndex()+' '+tempp.getNextChar());
            tags.add(tempp);
        }
    }
    public void printTags(){
        System.out.println("Tags are: ");
        for(int i=0;i<tags.size ();i++){
            System.out.println ("<"+tags.get ( i).getIndex ()+","+tags.get ( i).getNextChar ()+">");
        }
    }
    public void Decompress(){
        //take tags and return the string in this variable
        String comp="";
        //store unique strings
        Vector<String>dic=new Vector<String>();
        for(int i=0;i<tags.size();i++){
            String temp="";
            //check if string is unique
            if(tags.get(i).getIndex()==0){
                temp+=tags.get(i).getNextChar();
                comp+=tags.get(i).getNextChar();
                dic.add(temp);
            }
            else if(tags.get(i).getNextChar()=='0'){
                comp+=dic.get(tags.get(i).getIndex()-1);
            }
            else{
                temp+=dic.get(tags.get(i).getIndex()-1);
                comp+=dic.get(tags.get(i).getIndex()-1);
                comp+=tags.get(i).getNextChar();
                temp+=tags.get(i).getNextChar();;
                dic.add(temp);
            }
        }

        if(comp.equals(data))
            System.out.println("After Decompresion: "+comp);
    }

}