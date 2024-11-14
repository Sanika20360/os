import java.util.*;

public class OptimalPageReplacement {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the length of the reference string
        System.out.print("Enter the length of the reference string: ");
        int length = scanner.nextInt();

        // Input the reference string
        int[] referenceString = new int[length];
        System.out.println("Enter the reference string (space-separated): ");
        for (int i = 0; i < length; i++) {
            referenceString[i] = scanner.nextInt();
        }

        // Input the number of frames
        System.out.print("Enter the number of frames: ");
        int numOfFrames = scanner.nextInt();

        // Calculate page faults
        int pageFaults = calculatePageFaults(referenceString, numOfFrames);
        System.out.println("Number of Page Faults: " + pageFaults);
    }

    public static int calculatePageFaults(int[] referenceString, int numOfFrames) {
        Set<Integer> frames = new HashSet<>();
        int pageFaults = 0;

        for (int i = 0; i < referenceString.length; i++) {
            int page = referenceString[i];

            // If the page is not in the frames, it's a page fault
            if (!frames.contains(page)) {
                pageFaults++;

                // If there is space in frames, add the page
                if (frames.size() < numOfFrames) {
                    frames.add(page);
                } else {
                    // Find the page to replace using the Optimal strategy
                    int farthestIndex = -1;
                    int pageToReplace = -1;

                    for (Integer frame : frames) {
                        // Find the index of the frame in the reference string after the current index
                        int nextUseIndex = findNextUseIndex(referenceString, i, frame);
                        if (nextUseIndex > farthestIndex) {
                            farthestIndex = nextUseIndex;
                            pageToReplace = frame;
                        }
                    }

                    // Replace the page
                    frames.remove(pageToReplace);
                    frames.add(page);
                }
            }
        }

        return pageFaults;
    }

    // Helper method to find the next use index of a page in the reference string
    private static int findNextUseIndex(int[] referenceString, int currentIndex, int page) {
        for (int i = currentIndex + 1; i < referenceString.length; i++) {
            if (referenceString[i] == page) {
                return i; // Return the index where the page will be used next
            }
        }
        return Integer.MAX_VALUE; // Return a large value if the page is not found again
    }
}
