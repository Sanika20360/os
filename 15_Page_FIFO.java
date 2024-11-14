import java.util.*;

public class FIFOPaging {

    public static int calculatePageFaults(int[] referenceString, int frameCount) {
        Set<Integer> frames = new HashSet<>();
        Queue<Integer> fifoQueue = new LinkedList<>();
        int pageFaults = 0;

        for (int page : referenceString) {
            // Check if page is already in one of the frames
            if (!frames.contains(page)) {
                // Page fault occurs
                pageFaults++;
                
                // If there is no empty frame, remove the oldest page (FIFO order)
                if (frames.size() == frameCount) {
                    int oldestPage = fifoQueue.poll();
                    frames.remove(oldestPage);
                }
                
                // Add the new page to frames and FIFO queue
                frames.add(page);
                fifoQueue.add(page);
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input for reference string
        System.out.print("Enter the number of pages in the reference string: ");
        int n = sc.nextInt();
        int[] referenceString = new int[n];
        
        System.out.println("Enter the pages in the reference string:");
        for (int i = 0; i < n; i++) {
            referenceString[i] = sc.nextInt();
        }

        // Input for number of frames
        System.out.print("Enter the number of page frames: ");
        int frameCount = sc.nextInt();

        // Calculate page faults using FIFO
        int pageFaults = calculatePageFaults(referenceString, frameCount);
        
        // Output the number of page faults
        System.out.println("Total page faults using FIFO: " + pageFaults);
    }
}
