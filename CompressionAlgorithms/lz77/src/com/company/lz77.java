package com.company;
import java.util.Vector;
public class
lz77 {
    //Take the data which will be compressed
    private String data;
    //Create vector to store number of tags
    Vector < Lz77_Data > tags = new Vector < Lz77_Data > ( );
    //pramaterized constructor
    public lz77 ( String data ) {
        this.data = data;
    }

    public void compress ( ) {
        //string which will store characters to check if found or not
        String temp = "";
        //store on it previous length and position
        int prevLength = 0;
        int prevPosition = 0;
        for ( int i = 0; i < data.length (); i++ ) {
            //check if string iterate before or not
            boolean ok = false;
            temp += data.charAt ( i );
            //represent length of string that will be iterated
            int length = 0;
            int j = 0;
            for ( ; j < i; j++ ) {
                //check if this string iterate in data string or not
                if (length== temp.length ()) {
                    ok = true;
                    prevPosition = j;
                    length=0;
                }
                if (temp.charAt ( length ) == data.charAt ( j )) {
                    length++;

                } else if (temp.charAt ( length ) != data.charAt ( j )) {
                    length = 0;
                }
            }
            if (length== temp.length ()) {
                ok = true;
                prevPosition = j;
            }
            if (!ok) {
                Lz77_Data tempp= new Lz77_Data();
                if(prevPosition>0){
                    tempp.setIndex ( Math.abs (   j - prevPosition));
                }
                else  {tempp.setIndex ( Math.abs (prevPosition));}
                tempp.setLength ( prevLength );
                tempp.setNextChar ( data.charAt ( i ) );
                tags.add ( tempp );

                prevLength = 0;
                prevPosition = 0;
                temp = "";
            } else {
                prevLength = temp.length ();

            }
        }
        //ffff
        if (prevLength>0) {
            Lz77_Data tempp= new Lz77_Data();
            tempp.setIndex (data.length () - prevPosition);
            tempp.setLength ( prevLength - 1 );
            tempp.setNextChar ( data.charAt ( data.length ()-1 ) );
            tags.add ( tempp );
        }
    }
    public void printTags(){
        System.out.println("Tages Are: ");
        for(int i=0;i<tags.size ();i++){
            System.out.println ("<"+tags.get ( i).getIndex ()+","+tags.get ( i).getLength ()+","+tags.get ( i).getNextChar ()+">");
        }
    }
    public void decompressed(){
        //string which will be returned to decompress
        String dataa="";
        //String temp="";
        for(int i=0;i<tags.size ();i++){
            if(tags.get ( i).getLength ()==0){
                dataa+=tags.get ( i).getNextChar ();
            }
            else{
                //get length of characters
                int sz=tags.get ( i).getLength ();
                //get length of string
                int dz=dataa.length ();
                // calculate posion that will be start from the string
                int ind=Math.abs(dz-(tags.get ( i).getIndex ()));
                while(sz>0){
                    dataa+=dataa.charAt ( ind );
                    ind++;
                    sz--;
                }
                dataa+=tags.get ( i).getNextChar ();
            }
        }
        if(dataa.equals(data)){
            System.out.println("After Decompresion: "+dataa);
        }
    }
}