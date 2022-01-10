package com.company;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
public class lzw {
    //String which will be compressed
    private String data;
    //Store a unique strings in it
    private HashMap<String, Integer> Dictionary=new HashMap<String, Integer>();//Creating HashMap
    //store places of unique string
    private ArrayList<String> dic_compress=new ArrayList <String>(10000);
    // Store tags on it;
    private Vector<Integer>tags=new Vector<Integer>();
    public lzw(String data){
        this.data=data;
        String temp="";
        int lower=65,upper=97,st=128;
        for(char i='A';i<='Z';i++){
            temp="";
            temp+=i;
            Dictionary.put(temp,lower);
            lower++;
        }
        for(char i='a';i<='z';i++){
            temp="";
            temp+=i;
            Dictionary.put(temp,upper);
            upper++;
        }
        for (int i = 0; i < 10000; i++) {
            dic_compress.add("");
        }
        char lo='A',up='a';
        String tempp="";
        for(int i=65;i<=90;i++){
            tempp="";
            tempp+=lo;
            dic_compress.set(i, tempp);
            lo++;
        }
        for(int i=97;i<=122;i++){
            tempp="";
            tempp+=up;
            dic_compress.set(i,tempp);
            up++;
        }
    }
    public void compress(){
        int st=128;
        for(int i=0;i<data.length();i++){
            //check number of chars found and incerement in counter
            int incre=-1;
            String temp="",prev="";
            boolean ok=true;
            for(int j=i;j<data.length();j++){
                temp+=data.charAt(j);
                //check if char found
                if(Dictionary.containsKey(temp)){incre++;}
                else{
                    ok=false;
                    break;
                }
                prev=temp;

            }
            //put this unique string in dictionary
            if(!ok){
                Dictionary.put(temp,st);
                st++;
            }
            //push previous string in tags
            tags.add(Dictionary.get(prev));
            i+=incre;
        }
    }
    public void  Decompress(){
        int st=128;
        String prev="";
        String decompress="";
        for(int i=0;i<tags.size();i++){
            //if dictionary not empty
            if(dic_compress.get(tags.get(i))!=""){
                if(prev==""){
                    decompress+=dic_compress.get(tags.get(i));
                }
                else{
                    String temp=prev;
                    String first=dic_compress.get(tags.get(i));
                    temp+=first.charAt(0);
                    decompress+=dic_compress.get(tags.get(i));
                    dic_compress.set(st,temp);
                    st++;
                }
                prev=dic_compress.get(tags.get(i));
            }
            else{
                String temp=prev;
                temp+=prev.charAt(0);
                prev=temp;
                dic_compress.set(st,temp);
                decompress+=dic_compress.get(tags.get(i));
                st++;
            }
        }
        if(decompress.equals(data)){
            System.out.println("They are Same");
            System.out.println(decompress);
        }
    }
    public void printTags(){
        System.out.println("Tags are: ");
        for(int i=0;i<tags.size ();i++){
            System.out.print (tags.get(i)+" ");
        }
        System.out.println();
    }
}