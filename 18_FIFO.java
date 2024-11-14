package diskSchedulingAlgorithm;
import java.util.Scanner;
import java.util.ArrayList;

public class FIFO
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer>numberOfTracksTravelled = new ArrayList<>();
        System.out.println("Total Number Of Tracks:");
        int totalNumberOfTracks = scanner.nextInt();
        int[] tracksInfo = new int[totalNumberOfTracks];

        System.out.println("Enter Track Number:");
        for(int i = 0 ; i  < totalNumberOfTracks ; i++)
            tracksInfo[i] = scanner.nextInt();

        System.out.println("Enter Current Head:");
        int currentHeadPosition = scanner.nextInt();

        for(int element:tracksInfo)
        {
            numberOfTracksTravelled.add(Math.abs(currentHeadPosition - element));
            currentHeadPosition = element;
        }

        System.out.println("Number of Tracks Travelled each time:"+numberOfTracksTravelled);
        int totalSeekTime = 0;
        for(Integer element:numberOfTracksTravelled)
            totalSeekTime += element;
        System.out.println("Total Seek Time:"+totalSeekTime);

        double avgSeekTime = ((totalSeekTime * 1.0)/totalNumberOfTracks);
        System.out.printf("Average Seek Time:%.2f",avgSeekTime);
    }
}
