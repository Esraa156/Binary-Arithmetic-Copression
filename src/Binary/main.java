package Binary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class main {
    public static void main(String[] args) {

        Scanner INPUT = new Scanner(System.in);
        String data ="";
        try {
            File myObj = new File("test.txt");
            try (Scanner myReader = new Scanner(myObj)){
                while (myReader.hasNextLine()) {
                    data = myReader.nextLine();
                    System.out.println(data);

                }
                myReader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        char[] arr = data.toCharArray();
      //  System.out.println(data);
        Map<Character, Float> freq = new HashMap<>();
        Object chars = null;
        for (int i = 0; i < data.length(); i++) {
            Float prev = Float.valueOf(0);
            if (freq.get(arr[i]) != null) {
                prev = freq.get(arr[i]);
            }
            freq.put(arr[i], prev + 1);
        }
       // System.out.println(freq);
        for (Map.Entry<Character, Float> entry : freq.entrySet()) {
            char c = entry.getKey();
            Float i = (float) entry.getValue();
            i /= arr.length;
            freq.remove(entry);
            freq.put(c, i);
        }
       // Map<Character, Float> sorted = sort(freq);
        //System.out.println(sorted);
        Float [] low = new Float[28] , up = new Float[28];
        low [0] = 0.0f;
        Integer i = 0;
        Map<Character,Map<Float,Float>> upperMap = new HashMap<>();
        for (Map.Entry<Character, Float> entry : freq.entrySet())
        {
         Float v=entry.getValue();
         up[i]= v + low[i];
        // System.out.println(up[i] + " "+low[i]);
         i++;
         low[i] = up[i-1] ;
        }
        int q = 0;
        for(Map.Entry<Character, Float> entry : freq.entrySet())
        {
            char c=entry.getKey();
            Map <Float,Float>m=new HashMap<Float,Float>();
            m.put(low[q],up[q]);
            upperMap.put(c,m);
         //   System.out.println(c + " " + low[q] + " " +up[q]);
            q++;
        }
        float lower = 0;
        float upper = 1;
        ArrayList <Integer>v = new ArrayList<Integer>() ;
        for(int j = 0; j < data.length();j++)
        {
            Map <Float,Float>m=upperMap.get(arr[j]);
            //System.out.println(arr[j]);
            float x = 0;
            float y = 0;
            for (Map.Entry<Float,Float> entry : m.entrySet()) {
               x =  entry.getKey();  y = entry.getValue();
              // System.out.println(x + " " + y+" "+m.size());
            }
            float old_lower=lower,old_upper=upper;
            lower = old_lower+(old_upper-old_lower)*x;
            upper = old_lower+(old_upper-old_lower)*y;
          //System.out.println(lower +" "+upper +" one ");
            while((lower>0.5 && upper>0.5) || (lower<0.5 && upper<0.5) )
            {
              // System.out.println(lower+" "+upper);
                if(lower>0.5 && upper > 0.5)
                {
                    lower =(float)(lower-0.5)*2;
                    upper =(float)(upper-0.5)*2;
                    v.add(1);
                }
                else if(upper<.5 && lower <.5)
                {
                    lower =(float) lower*2;
                    upper =(float)upper*2;
                    v.add(0);
                }
                if(lower<=.5&&upper>=.5)
                    break;
            }
        }
        try {
            FileWriter myWriter = new FileWriter("ans.txt");
            for (int j = 0 ;j <v.size() ; j++) {
                String s="";
                s+=v.get(j);
                myWriter.write(s);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

       //System.out.println(v.size());
    }
}