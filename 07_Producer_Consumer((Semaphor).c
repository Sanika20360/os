// 7
// 2

#include <stdio.h>
#include <stdlib.h>
#include <semaphore.h>
#include <pthread.h>

#define BUFFER_SIZE 5
int buffer[BUFFER_SIZE];
int count = 0;

sem_t full, empty;
sem_t S;

void *produce(void *args)
{
    int id = *((int *)args);

    if (count == BUFFER_SIZE)
    {
        printf("\nProducer Cannot Produce Because Buffer is Full");
        return;
    }
    else
    {
        sem_wait(&empty);
        sem_wait(&S);
        printf("\nProducer %d is Entered in Critical Region : ", id);
        int newProduct = count + 1;
        buffer[count] = newProduct;
        printf("\nProducer %d produce new product %d", id, newProduct);
        count = count + 1;
        printf("\nBuffer Status : \n");
        for (int i = 0; i < BUFFER_SIZE; i++)
        {
            printf("%d ", buffer[i]);
        }
        printf("\nProducer %d is Entered in Critical Region : ", id);
        sem_post(&S);
        sem_post(&full);
    }
}

void *consume(void *args)
{
    int id = *((int *)args);

    if (count == 0)
    {
        printf("\nConsumer Cannot Consume Because Buffer is Empty");
        return;
    }
    else
    {
        sem_wait(&full);
        sem_wait(&S);
        printf("\nConsumer %d is Entered in Critical Region : ", id);
        int consumeProduct = buffer[count - 1];
        printf("\nConsumer %d consume product %d", id, consumeProduct);
        count = count - 1;
        buffer[count] = 0;
        printf("\nBuffer Status : \n");
        for (int i = 0; i < BUFFER_SIZE; i++)
        {
            printf("%d ", buffer[i]);
        }
        sem_post(&S);
        sem_post(&empty);
        printf("\nConsumer %d is Exit from Critical Region : ", id);
    }
}

int main()
{
    // Producer Count
    int Prod = 0;
    int Consu = 0;
    printf("\nEnter the number of Producer : ");
    scanf("%d", &Prod);
    printf("\nEnter the number of Consumer : ");
    scanf("%d", &Consu);
    int pro = 0;
    int con = 0;
    int counter = 0;

    for (int i = 0; i < BUFFER_SIZE; i++)
    {
        buffer[i] = 0;
    }

    // creating threads
    pthread_t Producer[Prod], Consumer[Consu];
    int ProdId[Prod], ConsuId[Consu];

    // initialising semaphores
    sem_init(&full, 0, 0);
    sem_init(&empty, 0, BUFFER_SIZE);
    sem_init(&S, 0, 1);

    for (pro; pro <Prod; pro++)
    {
        ProdId[pro] = pro + 1;
        pthread_create(&Producer[pro], NULL, produce, &ProdId[pro]);
    }
    for (con; con < Consu; con++)
    {
        ConsuId[con] = con + 1;
        pthread_create(&Consumer[con], NULL, consume, &ConsuId[con]);
    }
    for (pro; pro < Prod; pro++)
    {
        ProdId[pro] = pro + 1;
        pthread_create(&Producer[pro], NULL, produce, &ProdId[pro]);
    }

    for (int i = 0; i < Consu; i++)
    {
        pthread_join(Consumer[i], NULL);
    }

    return 0;
}