#include <stdio.h>
#include <stdlib.h>
#include <semaphore.h>
#include <pthread.h>

int Data = 10;
int ReadCount = 0;

sem_t enter, db;

void *readDb(void *args)
{
    int id = *(int *)args;
    sem_wait(&enter);
    ReadCount = ReadCount + 1;
    if (ReadCount == 1)
    {
        sem_wait(&db);
    }
    sem_post(&enter);
    printf("\nReader %d Entering in Critical Region", id);
    printf("\nReader %d reading data %d", id, Data);
    printf("\nReader %d Exiting from Critical Region", id);
    sem_wait(&enter);
    ReadCount = ReadCount - 1;
    if (ReadCount == 0)
    {
        sem_post(&db);
    }
    sem_post(&enter);
}

void *writeDb(void *args)
{
    int id = *(int *)args;
    int newData;
    sem_wait(&db);
    printf("\nWriter %d Entering in Critical Region", id);
    printf("\nEnter data which you wants to update : ");
    scanf("%d", &newData);
    Data = newData;
    printf("\nWriter %d update the data to %d", id, Data);
    printf("\nWriter %d Exiting from Critical Region", id);
    sem_post(&db);
}

int main()
{
    // Producer Count
    int readC = 0;
    int writeC = 0;
    printf("\nEnter the number of Readers : ");
    scanf("%d", &readC);
    printf("\nEnter the number of Writers : ");
    scanf("%d", &writeC);
    int read = 0;
    int writer = 0;

    // creating threads
    pthread_t Reader[readC], Writer[writeC];
    int readId[readC];
    int writeId[writeC];

    // initialising semaphores
    sem_init(&enter, 0, 1);
    sem_init(&db, 0, 1);

    for (read; read < readC; read++)
    {
        readId[read] = read + 1;
        pthread_create(&Reader[read], NULL, readDb, &readId[read]);
    }
    for (writer; writer < writeC; writer++)
    {
        writeId[writer] = writer + 1;
        pthread_create(&Writer[writer], NULL, writeDb, &writeId[writer]);
    }

    for (int i = 0; i < readC; i++)
    {
        pthread_join(Reader[i], NULL);
    }
    for (int i = 0; i < writeC; i++)
    {
        pthread_join(Writer[i], NULL);
    }

    return 0;
}