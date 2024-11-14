package diskSchedulingAlgorithm;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class SSTF //Increasing
{
    public static void main(String[] args)
    {
        int indexForGreaterNumberHere, indexForSmallerNumber;
        indexForGreaterNumberHere = indexForSmallerNumber = 0;
        ArrayList<Integer>greaterNumberHere = new ArrayList<>();
        ArrayList<Integer>smallerNumberHere = new ArrayList<>();
        ArrayList<Integer>numberOfTracksTravelled = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
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
            if(element<currentHeadPosition)
                smallerNumberHere.add(element);
            else
                greaterNumberHere.add(element);
        }

        Collections.sort(greaterNumberHere);
        Collections.sort(smallerNumberHere);
        for(int i = 0; i < smallerNumberHere.size() / 2 ; i++)
        {
            Integer element = smallerNumberHere.get(i);
            smallerNumberHere.set(i,smallerNumberHere.get(smallerNumberHere.size() - i - 1));
            smallerNumberHere.set(smallerNumberHere.size() - i - 1,element);
        }
        System.out.println("Greater Number Here:"+greaterNumberHere);
        System.out.println("Smaller Number Here:"+smallerNumberHere);
        while(indexForSmallerNumber!= smallerNumberHere.size() && indexForGreaterNumberHere !=greaterNumberHere.size())
        {
            //Check for smaller difference
            int difference1 = Math.abs(smallerNumberHere.get(indexForSmallerNumber) - currentHeadPosition);
            int difference2 = Math.abs(greaterNumberHere.get(indexForGreaterNumberHere) - currentHeadPosition);
            if(difference1 >= difference2)
            {
                numberOfTracksTravelled.add(difference2);
                currentHeadPosition = greaterNumberHere.get(indexForGreaterNumberHere);
                indexForGreaterNumberHere++;
            }
            else
            {
                numberOfTracksTravelled.add(difference1);
                currentHeadPosition = smallerNumberHere.get(indexForSmallerNumber);
                indexForSmallerNumber++;
            }
        }

        if(indexForSmallerNumber == smallerNumberHere.size())
        {
            //Element of greaterNumberHere are remaining
            do
            {
                int difference = Math.abs(greaterNumberHere.get(indexForGreaterNumberHere) - currentHeadPosition);
                numberOfTracksTravelled.add(difference);
                currentHeadPosition = greaterNumberHere.get(indexForGreaterNumberHere);
                indexForGreaterNumberHere++;
            }while(indexForGreaterNumberHere != greaterNumberHere.size());
        }
        else
        {
            //Element of smallerNumberHere are remaining
            do
            {
                int difference = Math.abs(smallerNumberHere.get(indexForSmallerNumber) - currentHeadPosition);
                numberOfTracksTravelled.add(difference);
                currentHeadPosition = smallerNumberHere.get(indexForSmallerNumber);
                indexForSmallerNumber++;
            }while(indexForSmallerNumber != smallerNumberHere.size());
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
