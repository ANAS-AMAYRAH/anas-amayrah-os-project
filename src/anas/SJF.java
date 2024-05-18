package anas;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SJF {
    public static void main(String args[]) {
        try {
            Scanner fileScanner = new Scanner(new File("input.txt"));

            System.out.println("Process\tAT\tBT\tCT\tTAT\tWT");

            int n = fileScanner.nextInt();
            int pid[] = new int[n];
            int at[] = new int[n];
            int bt[] = new int[n];
            int ct[] = new int[n];
            int ta[] = new int[n];
            int wt[] = new int[n];
            boolean completed[] = new boolean[n];

            int st = 0, tot = 0;
            float avgwt = 0, avgta = 0, avgct = 0;

         
            for (int i = 0; i < n; i++) {
                at[i] = fileScanner.nextInt();
                bt[i] = fileScanner.nextInt();
                pid[i] = i + 1;
                completed[i] = false;
            }

            StringBuilder ganttChart = new StringBuilder();
            ganttChart.append("|");

          
            while (tot < n) {
                int min_bt = Integer.MAX_VALUE;
                int selected = -1;

          
                for (int i = 0; i < n; i++) {
                    if (at[i] <= st && !completed[i] && bt[i] < min_bt) {
                        min_bt = bt[i];
                        selected = i;
                    }
                }

            
                if (selected == -1) {
                    st++;
                    ganttChart.append("-");
                    continue;
                }

             
                ct[selected] = st + bt[selected];
                ta[selected] = ct[selected] - at[selected];
                wt[selected] = ta[selected] - bt[selected];
                completed[selected] = true;
                st += bt[selected];
                tot++;

            
                for (int i = 0; i < bt[selected]; i++) {
                    ganttChart.append("P").append(pid[selected]).append("|");
                }

         
                System.out.println(pid[selected] + "\t" + at[selected] + "\t" + bt[selected] + "\t" + ct[selected] + "\t" + ta[selected] + "\t" + wt[selected]);
            }

        
            for (int i = 0; i < n; i++) {
                avgwt += wt[i];
                avgta += ta[i];
                avgct += ct[i];
            }

       
            System.out.println("\nAverage TAT is " + (avgta / n));
            System.out.println("Average WT is " + (avgwt / n));
            System.out.println("Average CT is " + (avgct / n));

         
            int totalBurstTime = 0;
            for (int i = 0; i < n; i++) {
                totalBurstTime += bt[i]; // Sum up all burst times
            }
            float cpuUtilization = ((float) totalBurstTime) / ct[n - 1]; // Burst time divided by total execution time
            System.out.println("CPU Utilization is: " + (cpuUtilization * 100) + "%");

            // Print Gantt Chart
            System.out.println("Gantt Chart:");
            System.out.println(ganttChart.toString());

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
