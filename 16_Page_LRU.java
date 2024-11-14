import java.util.*;

public class LRUPageReplacement {

    public static int calculatePageFaults(int[] referenceString, int frameCount) {
        Set<Integer> frames = new HashSet<>();
        Map<Integer, Integer> lruMap = new HashMap<>();
        int pageFaults = 0;
        int time = 0; // To keep track of the time each page was last used

        for (int page : referenceString) {
            // Check if the page is not in frames (page fault)
            if (!frames.contains(page)) {
                pageFaults++;
                
                // If frames are full, find the least recently used page to replace
                if (frames.size() == frameCount) {
                    // Find the LRU page based on minimum last used time
                    int lruPage = Collections.min(lruMap.entrySet(), Map.Entry.comparingByValue()).getKey();
                    frames.remove(lruPage);
                    lruMap.remove(lruPage);
                }
                
                // Add the new page to frames and update last used time
                frames.add(page);
            }
            
            // Update the last used time for the current page
            lruMap.put(page, time++);
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

        // Calculate page faults using LRU
        int pageFaults = calculatePageFaults(referenceString, frameCount);
        
        // Output the number of page faults
        System.out.println("Total page faults using LRU: " + pageFaults);
    }
}

/*
Enter the number of pages in the reference string: 12
Enter the pages in the reference string:
1 3 0 3 5 6 3 1 3 6 1 2
Enter the number of page frames: 3

Output:
Total page faults using LRU: 9

*/
