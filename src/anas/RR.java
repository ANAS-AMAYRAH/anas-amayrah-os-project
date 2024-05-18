package anas;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class RR {
    public static void main(String[] args) {
        try {
            Scanner fileScanner = new Scanner(new File("input.txt"));

            int numberOfProcesses = fileScanner.nextInt();
            System.out.println("Number of processes: " + numberOfProcesses); 

            int pid[] = new int[numberOfProcesses];
            int bt[] = new int[numberOfProcesses];
            int ar[] = new int[numberOfProcesses];
            int ct[] = new int[numberOfProcesses];
            int wt[] = new int[numberOfProcesses];
            int tat[] = new int[numberOfProcesses];

            for (int i = 0; i < numberOfProcesses; i++) {
                ar[i] = fileScanner.nextInt();
                bt[i] = fileScanner.nextInt();
                pid[i] = i + 1;
                System.out.println("Process " + pid[i] + ": Arrival Time = " + ar[i] + ", Burst Time = " + bt[i]); 
            }

            int timeQuantum = fileScanner.nextInt();
            System.out.println("Time Quantum: " + timeQuantum); 

            System.out.println("Starting process scheduling..."); 

            Queue<Integer> readyQueue = new ArrayDeque<>();
            for (int i = 0; i < numberOfProcesses; i++) {
                if (ar[i] == 0) {
                    readyQueue.offer(i);
                }
            }

            int currentTime = 0;
            int remainingBurst[] = new int[numberOfProcesses];
            System.arraycopy(bt, 0, remainingBurst, 0, numberOfProcesses);

            StringBuilder ganttChart = new StringBuilder("|");
            while (!readyQueue.isEmpty()) {
                int currentProcess = readyQueue.poll();
                int executionTime = Math.min(remainingBurst[currentProcess], timeQuantum);
                currentTime += executionTime;
                remainingBurst[currentProcess] -= executionTime;
                ganttChart.append("P").append(pid[currentProcess]).append("|");
                if (remainingBurst[currentProcess] > 0) {
                    readyQueue.offer(currentProcess);
                } else {
                    ct[currentProcess] = currentTime;
                    tat[currentProcess] = ct[currentProcess] - ar[currentProcess];
                    wt[currentProcess] = tat[currentProcess] - bt[currentProcess];
                    if (wt[currentProcess] < 0) wt[currentProcess] = 0; // Ensure WT is not negative
                }
                for (int i = 0; i < numberOfProcesses; i++) {
                    if (ar[i] <= currentTime && remainingBurst[i] > 0 && i != currentProcess) {
                        readyQueue.offer(i);
                    }
                }
            }
            ganttChart.append("\n");

            System.out.println("Gantt chart: ");
            System.out.println(ganttChart.toString());

          
            System.out.println("Process\t\tAT\t\tBT\t\tCT\t\tWT\t\tTAT");
            double totalWaitingTime = 0;
            double totalTurnaroundTime = 0;
            for (int i = 0; i < numberOfProcesses; i++) {
                totalWaitingTime += wt[i];
                totalTurnaroundTime += tat[i];
                System.out.println(pid[i] + "\t\t" + ar[i] + "\t\t" + bt[i] + "\t\t" + ct[i] + "\t\t" + wt[i] + "\t\t" + tat[i]);
            }
            double avgWaitingTime = totalWaitingTime / numberOfProcesses;
            double avgTurnaroundTime = totalTurnaroundTime / numberOfProcesses;

            System.out.println("Average Waiting Time: " + avgWaitingTime);
            System.out.println("Average Turnaround Time: " + avgTurnaroundTime);

            
            double totalExecutionTime = ct[numberOfProcesses - 1]; 
            double totalCPUTime = 0;
            for (int i = 0; i < numberOfProcesses; i++) {
                totalCPUTime += bt[i];
            }
            double cpuUtilization = (totalCPUTime / totalExecutionTime) * 100;
            System.out.println("CPU Utilization: " + cpuUtilization + "%");

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}