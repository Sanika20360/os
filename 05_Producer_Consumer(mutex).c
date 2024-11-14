// 4
// 3

#include <stdio.h>
#include <stdlib.h>
#include <semaphore.h>
#include <pthread.h>

#define BUFFER_SIZE 5
int buffer[BUFFER_SIZE];
int count = 0;

sem_t full, empty;
pthread_mutex_t mutex;

void *produce(void *args)
{
    int id = *((int *)args);

    if (count == BUFFER_SIZE)
    {
        printf("\nProducer Cannot Produce Because Buffer is Full");
    }
    else
    {
        sem_wait(&empty);
        pthread_mutex_lock(&mutex);
        printf("\nProducer %d is Entered in Critical Region : ",id);
        printf int newProduct = count + 1;
        buffer[count] = newProduct;
        printf("\nProducer %d produce new product %d", id, newProduct);
        count = count + 1;
        printf("\nBuffer Status : \n");
        for (int i = 0; i < BUFFER_SIZE; i++)
        {
            printf("%d ", buffer[i]);
        }
        printf("\nProducer %d is Exit from Critical Region : ",id);
        pthread_mutex_unlock(&mutex);
        sem_post(&full);
    }
}   

void *consume(void *args)
{
    int id = *((int *)args);

    if (count == 0)
    {
        printf("\nConsumer Cannot Consume Because Buffer is Empty");
    }
    else
    {
        sem_wait(&full);
        pthread_mutex_lock(&mutex);
        printf("\nConsumer %d is Entered in Critical Region : ",id);
        int consumeProduct = buffer[count - 1];
        printf("\nConsumer %d consume product %d", id, consumeProduct);
        count = count - 1;
        buffer[count] = 0;
        printf("\nBuffer Status : \n");
        for (int i = 0; i < BUFFER_SIZE; i++)
        {
            printf("%d ", buffer[i]);
        }
        printf("\nConsumer %d is Exit from Critical Region : ",id);
        pthread_mutex_unlock(&mutex);
        sem_post(&empty);
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
    pthread_mutex_init(&mutex, NULL);

    for (pro; pro < Prod; pro++)
    {
        ProdId[pro] = pro + 1;
        pthread_create(&Producer[pro], NULL, produce, &ProdId[pro]);
    }
    for (con; con < Consu; con++)
    {
        ConsuId[con] = con + 1;
        pthread_create(&Consumer[con], NULL, consume, &ConsuId[con]);
    }

    for (int i = 0; i < Prod; i++)
    {
        pthread_join(Producer[i], NULL);
    }
    for (int i = 0; i < Consu; i++)
    {
        pthread_join(Consumer[i], NULL);
    }

    return 0;
}