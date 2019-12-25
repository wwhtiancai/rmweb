package com.tmri.pub.util;

public class GenericSort {
    public static void main(String[] args){
   Integer[] intArray={2,4,3};
   Double[] doubleArray={3.4,1.3,-22.1};
   Character[] charArray={'a','j','r'};
   String[] strArray={"Apple","Tom","John","Fred"};
   
   asc(intArray);
   asc(doubleArray);
   asc(charArray);
   asc(strArray);
   
   System.out.println("Sorted Integer objects:");
   printList(intArray);
   
   System.out.println("Sorted Double objects:");
   printList(doubleArray);
   
   System.out.println("Sorted Character objects:");
   printList(charArray);
   
   System.out.println("Sorted String objects:");
   printList(strArray);
    }
    
    public static void asc(Object[] list){
   Object currentMax;
   int currentMaxIndex;
   
   for(int i= list.length-1;i>=1;i--){
      currentMax = list[i];
      currentMaxIndex = i;
      
      for(int j= i-1;j>=0;j--){
       if(((Comparable)currentMax).compareTo(list[j])<0){
        currentMax = list[j];
        currentMaxIndex = j;
       }
      }
      
      if(currentMaxIndex != i){
       list[currentMaxIndex]=list[i];
       list[i] = currentMax;
        
       }
      }
   }
    public static void desc(Object[] list){
    	   Object currentMax;
    	   int currentMaxIndex;
    	   
    	   for(int i= list.length-1;i>=1;i--){
    	      currentMax = list[i];
    	      currentMaxIndex = i;
    	      
    	      for(int j= i-1;j>=0;j--){
    	       if(((Comparable)currentMax).compareTo(list[j])>0){
    	        currentMax = list[j];
    	        currentMaxIndex = j;
    	       }
    	      }
    	      
    	      if(currentMaxIndex != i){
    	       list[currentMaxIndex]=list[i];
    	       list[i] = currentMax;
    	        
    	       }
    	      }
    	   }
    public static void printList(Object[] list){
   for(int i=0;i<list.length;i++)
      System.out.print(list[i]+" ");
   System.out.println();
    }
}
