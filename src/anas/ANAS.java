package anas;

public class ANAS {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("                      anas amayrah 201144                    ");  
        System.out.println("============================================================");
        System.out.println();

        
        System.out.println("============================================================");
        System.out.println("                      Round-Robin (RR)                      ");
        System.out.println("============================================================");
        RR.main(args);
        
        System.out.println("------------------------------------------------------------");
        System.out.println();

       
        System.out.println("============================================================");
        System.out.println("          Shortest Remaining Time (SRT) (SJF)               ");
        System.out.println("============================================================");
        SJF.main(args);
      
        System.out.println("------------------------------------------------------------");
        System.out.println();

     
        System.out.println("============================================================");
        System.out.println("          First-Come First-Served (FCFS)                    ");
        System.out.println("============================================================");
        fcfs.main(args);
       
        System.out.println("------------------------------------------------------------");
    }
}
