package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;

class HNode{
    public int freq;
    public char info;
    public HNode left,right;
}
class CompareFreq implements Comparator<HNode>{
    public int compare( HNode x, HNode y){
        return x.freq - y.freq;
    }
}
class AddGUI extends JFrame{

}
public class Main  {
    //Count number of iterate each char
    public static HashMap<Character,Integer> Freq =new HashMap<Character,Integer>();
    public static PriorityQueue<HNode> HuffmanTree = new PriorityQueue<>(100, new CompareFreq());
    //Store each char its code
    public static HashMap<Character,String>de=new HashMap<Character,String>();
    //Store each code his char
    public static HashMap<String,Character>de_in=new HashMap<String,Character>();
    public static HNode getParent(){
        HNode root = null;
        while (HuffmanTree.size() > 1)
        {
            HNode left = HuffmanTree.peek();
            HuffmanTree.poll();
            HNode right = HuffmanTree.peek();
            HuffmanTree.poll();
            HNode parent = new HNode();
            parent.freq = left.freq + right.freq;
            parent.info = '@';
            parent.left = left;
            parent.right = right;
            root = parent;
            HuffmanTree.add(parent);
        }
        return root;
    }
    public static void printNodes(HNode root,String s){
        if (root.left == null && root.right == null )
        {
            de.put(root.info,s);
            de_in.put(s,root.info);
            //System.out.println(root.info + ":" + s);
            return;
        }
        printNodes(root.left, s + "0");
        printNodes(root.right, s + "1");
    }
    public static String decompress(String data){
        String temp="";
        String comp="";
        for(int i=0;i<data.length();i++){
            temp+=data.charAt(i);
            if(de_in.containsKey(temp)){
                comp+=de_in.get(temp);
                temp="";
            }
        }
        return comp;
    }
    public static void main(String[] args) throws IOException {
        AddGUI agui=new AddGUI ();
        final Integer[] cnt = {0};
        JButton buRead;
        String path="E:\\compuer science material\\CS-3\\data comprtaion\\huffman\\input.txt";
        File myObj = new File (path ); // created a file object named myObj
        buRead=new JButton ( "Read File" );
        JLabel data=new JLabel (  );
        JLabel inPath=new JLabel (  );
        inPath.setText ( "File Path is: "+path );
        data.setFont ( new Font("SansSerif", Font.BOLD, 20)  );
        inPath.setFont ( new Font("SansSerif", Font.BOLD, 20)  );
        buRead.setFont ( new Font("SansSerif", Font.BOLD, 40) );
        buRead.setPreferredSize ( new Dimension ( 500,100 ) );
        ActionListener al=new ActionListener ( ) {
            @Override
            public void actionPerformed ( ActionEvent e ) {
                if(cnt[0]==0){
                    cnt[0]++;
                    File myobj1 =myObj;  // created a file object named myObj
                    try {
                        Scanner MyReader = new Scanner ( myobj1 );
                        String str="";
                        while ( MyReader.hasNextLine ( ) ){
                            String info = MyReader.nextLine ( );
                            str+=info;
                        }
                        MyReader.close ();
                        for(int i=0;i<str.length();i++){
                            Integer val= Freq.get(str.charAt(i));
                            if(val==null){val=0;}
                            Freq.put(str.charAt(i),val+1);
                        }
                        String txt="<html>";
                        txt+="<br/>";
                        for(Character it: Freq.keySet()){
                            txt+="Char is: ";
                            txt+=it;
                            txt+=" , Number of duplicate= ";
                            txt+= Freq.get(it);
                            txt+=" , Probability of char = ";
                            txt+= Freq.get(it)+"/"+str.length ();
                            // System.out.println("Char is: "+it+" ,Number of duplicate= "+dic.get(it)+" ,Probability of char = "+dic.get(it)+"/"+str.length ());
                            txt+="<br/>";
                        }

                        File dicFile = new File ( "E:\\compuer science material\\CS-3\\data comprtaion\\huffman\\dic.txt");
                        OutputStream out = new BufferedOutputStream ( new FileOutputStream ( dicFile ) );
                        byte[] buffer = new byte[1024];
                        for (Character it: Freq.keySet())
                        {
                            HNode Node1 = new HNode();
                            Node1.freq= Freq.get(it) ;
                            Node1.info=it;
                            Node1.left = null;
                            Node1.right = null;
                            HuffmanTree.add(Node1);
                        }
                        HNode root=getParent();
                        printNodes(root,"");

                        for(Character it: de.keySet()){
                            String te="";
                            te+=it;
                            te+=':';
                            te+=de.get ( it );
                            te+="\n";
                            buffer = te.getBytes ( );
                            out.write ( buffer );
                        }
                        out.close ( );
                        String decompress="";
                        File CompFile = new File ( "E:\\compuer science material\\CS-3\\data comprtaion\\huffman\\comp.txt");
                        OutputStream outCompress = new BufferedOutputStream ( new FileOutputStream ( CompFile ) );
                        for(int i=0;i<str.length();i++){
                            decompress+=de.get(str.charAt(i));
                        }
                        byte[] buffer2 = new byte[1024];
                        buffer2 = decompress.getBytes ( );
                        outCompress.write ( buffer2 );
                        outCompress.close ();
                        String comp=decompress(decompress);
                        txt+="<hr/>";
                        txt+="Orginal data:";
                        txt+=str.length ()*8;
                        txt+=" Bit";
                        txt+="<br/>";
                        txt+="Compressed data:";
                        txt+=decompress.length ();
                        txt+=" Bit";
                        txt+="<br/>";
                        if(comp.equals ( str )){
                            txt+="Data After compress: ";
                            txt+=comp;
                            txt += "</html>";
                        }
                        data.setText ( txt );

                    }
                    catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace ( );
                    } catch (IOException ioException) {
                        ioException.printStackTrace ( );
                    }

                }
            }

        };
        // b.addActionListener ( this );//is an interface
        agui.add ( inPath );
        agui.add ( buRead );
        buRead.addActionListener ( al );
        agui.add ( data );
        agui.setLayout ( new FlowLayout ( )  );
        agui. setVisible ( true );
        agui.setSize ( 800,1025 );
        //TO exit when choose exit
        agui.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
    }
}