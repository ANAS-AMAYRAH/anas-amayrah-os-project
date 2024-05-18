package anas;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fcfs {
    public static void main(String[] args) {
        try {
            Scanner fileScanner = new Scanner(new File("input.txt"));

            int numberOfProcesses = fileScanner.nextInt();

            int pid[] = new int[numberOfProcesses];
            int bt[] = new int[numberOfProcesses];
            int ar[] = new int[numberOfProcesses];
            int ct[] = new int[numberOfProcesses];
            int ta[] = new int[numberOfProcesses];
            int wt[] = new int[numberOfProcesses];

            double totalWaitingTime = 0;
            double totalTurnaroundTime = 0;
            double totalFinishTime = 0;
            double totalCpuTime = 0;

            for (int i = 0; i < numberOfProcesses; i++) {
                ar[i] = fileScanner.nextInt();
                bt[i] = fileScanner.nextInt();
                pid[i] = i + 1;
            }

            int temp;
            for (int i = 0; i < numberOfProcesses; i++) {
                for (int j = i + 1; j < numberOfProcesses; j++) {
                    if (ar[i] > ar[j]) {
                        temp = ar[i];
                        ar[i] = ar[j];
                        ar[j] = temp;

                        temp = pid[i];
                        pid[i] = pid[j];
                        pid[j] = temp;
                        temp = bt[i];
                        bt[i] = bt[j];
                        bt[j] = temp;
                    }
                }
            }

            ct[0] = bt[0] + ar[0];
            for (int i = 1; i < numberOfProcesses; i++) {
                ct[i] = ct[i - 1] + bt[i];
            }
            for (int i = 0; i < numberOfProcesses; i++) {
                ta[i] = ct[i] - ar[i];
                wt[i] = ta[i] - bt[i];
                totalWaitingTime += wt[i];
                totalTurnaroundTime += ta[i];
                totalFinishTime += ct[i];
                totalCpuTime += bt[i];
            }

            double avgWaitingTime = totalWaitingTime / numberOfProcesses;
            double avgTurnaroundTime = totalTurnaroundTime / numberOfProcesses;
            double avgFinishTime = totalFinishTime / numberOfProcesses;
            double cpuUtilization = (totalCpuTime / ct[numberOfProcesses - 1]) * 100;

            System.out.println("Process\t\tAT\t\tBT\t\tCT\t\tTAT\t\tWT");
            for (int i = 0; i < numberOfProcesses; i++) {
                System.out.println(pid[i] + "\t\t\t" + ar[i] + "\t\t" + bt[i] + "\t\t" + ct[i] + "\t\t" + ta[i] + "\t\t" + wt[i]);
            }

            System.out.println("Gantt chart: ");
            System.out.print("|");
            for (int i = 0; i < numberOfProcesses; i++) {
                for (int j = 0; j < bt[i]; j++) {
                    System.out.print("P" + pid[i] + "|");
                }
            }
            System.out.println();

            System.out.println("Average Waiting Time: " + avgWaitingTime);
            System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
            System.out.println("Average Finish Time: " + avgFinishTime);
            System.out.println("CPU Utilization: " + cpuUtilization + "%");

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
