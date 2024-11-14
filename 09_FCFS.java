import java.util.*;

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of processes: ");
        int processes = sc.nextInt();

        int[] arrivalTimes = new int[processes];
        int[] burstTimes = new int[processes];

        for (int i = 0; i < processes; i++) {
            System.out.println("Enter arrival time for process P" + i + ": ");
            arrivalTimes[i] = sc.nextInt();
            System.out.println("Enter burst time for process P" + i + ": ");
            burstTimes[i] = sc.nextInt();
        }

        // Array to store indices of processes sorted by arrival time
        Integer[] indices = new Integer[processes];
        for (int i = 0; i < processes; i++) indices[i] = i;

        // Sort indices by arrival time to ensure FCFS order
        Arrays.sort(indices, Comparator.comparingInt(i -> arrivalTimes[i]));

        int[] finishTimes = new int[processes];
        int[] turnaroundTimes = new int[processes];
        int[] waitingTimes = new int[processes];

        int time = 0;
        for (int i = 0; i < processes; i++) {
            int process = indices[i];

            // If the process arrives after the current time, jump to its arrival time
            if (time < arrivalTimes[process]) {
                time = arrivalTimes[process];
            }

            // Calculate finish time
            time += burstTimes[process];
            finishTimes[process] = time;

            // Calculate turnaround time and waiting time
            turnaroundTimes[process] = finishTimes[process] - arrivalTimes[process];
            waitingTimes[process] = turnaroundTimes[process] - burstTimes[process];
        }

        // Print the results
        System.out.println("Process \t Arrival Time \t Burst Time \t Finish Time \t Turnaround Time \t Waiting Time");
        for (int i = 0; i < processes; i++) {
            System.out.printf("P%d\t\t %d\t\t %d\t\t %d\t\t\t %d\t\t %d\n",
                    i, arrivalTimes[i], burstTimes[i], finishTimes[i], turnaroundTimes[i], waitingTimes[i]);
        }
    }
}
/*
input : 4 0 2 1 2 5 3 6 4
Process 	 Arrival Time 	 Burst Time 	 Finish Time 	 Turnaround Time 	 Waiting Time
P0				 0				 2				 2					 2				 0
P1				 1				 2				 4					 3				 1
P2				 5				 3				 8					 3				 0
P3				 6				 4				 12					 6				 2
 */
