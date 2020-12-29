package assignment07;

public class OverflowTest {
	public static void main(String[] args) 
    {
        int x;
        int y;
        int sum;

        x = 2_000_000_000;
        y = 147_483_647;
   
        sum = x + y;

        System.out.print ("X contains ");
        System.out.print (x);
        System.out.println(); 

        System.out.print ("Y contains ");
        System.out.print (y);
        System.out.println();
        
        System.out.print ("sum contains ");
        System.out.print (sum);
        System.out.println();
        
    }

}
