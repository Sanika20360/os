package diskSchedulingAlgorithm;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class SCAN//Increasing
{
    public static void main(String[] args)
    {
        ArrayList<Integer>greaterNumberHere = new ArrayList<>();
        ArrayList<Integer>smallerNumberHere = new ArrayList<>();
        ArrayList<Integer>numberOfTracksTravelled = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter total Number Of Tracks:");
        int totalNumberOfTracks = scanner.nextInt();

        int[] tracksInfo = new int[totalNumberOfTracks];

        System.out.println("Enter Track Number:");
        for(int i = 0 ; i  < totalNumberOfTracks ; i++)
            tracksInfo[i] = scanner.nextInt();

        System.out.println("Enter Current Head:");
        int currentHeadPosition = scanner.nextInt();

        for(int element:tracksInfo)
        {
            if(element > currentHeadPosition)
                greaterNumberHere.add(element);
            else
                smallerNumberHere.add(element);
        }

        Collections.sort(greaterNumberHere);
        Collections.sort(smallerNumberHere);

        for(int i = 0 ; i < smallerNumberHere.size() / 2 ;i++)
        {
            Integer element = smallerNumberHere.get(i);
            smallerNumberHere.set(i, smallerNumberHere.get(smallerNumberHere.size() - i -1));
            smallerNumberHere.set(smallerNumberHere.size() - i - 1,element);
        }

        for(Integer element:greaterNumberHere)
        {
            int difference  = Math.abs(element - currentHeadPosition);
            numberOfTracksTravelled.add(difference);
            currentHeadPosition = element;
        }

        for(Integer element:smallerNumberHere)
        {
            int difference = Math.abs(element - currentHeadPosition);
            numberOfTracksTravelled.add(difference);
            currentHeadPosition = element;
        }

        System.out.println("Number Of Tracks Travelled:"+numberOfTracksTravelled);

        int totalSeekTime = 0;
        for(Integer element:numberOfTracksTravelled)
            totalSeekTime += element;

        System.out.println("Total Seek Time:"+totalSeekTime);

        double avgSeekTime = (totalSeekTime * 1.0) / totalNumberOfTracks;
        System.out.printf("Average Seek Time : %.2f",avgSeekTime);


    }
}
