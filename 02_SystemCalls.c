// 1
// 2
// 3
// 4
// 5
// 6
// 7

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>

void fork_exit_wait();
void file_operations();
void create_link();
void create_pipe();
void create_fifo();
void get_process_ids();

int main() {
    int choice;
    
    while (1) {
        printf("\nMenu:\n");
        printf("1. Fork, Exit, Wait\n");
        printf("2. File Operations (Open, Read, Write, Close)\n");
        printf("3. Create Link and Unlink\n");
        printf("4. Pipe\n");
        printf("5. FIFO\n");
        printf("6. Get Process IDs (getpid, getppid)\n");
        printf("7. Exit\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);
        
        switch (choice) {
            case 1:
                fork_exit_wait();
                break;
            case 2:
                file_operations();
                break;
            case 3:
                create_link();
                break;
            case 4:
                create_pipe();
                break;
            case 5:
                create_fifo();
                break;
            case 6:
                get_process_ids();
                break;
            case 7:
                printf("Exiting program.\n");
                exit(0);
            default:
                printf("Invalid choice, please try again.\n");
        }
    }
    return 0;
}

void fork_exit_wait() {
    pid_t pid = fork();
    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    } else if (pid == 0) {
        printf("In Child Process:\n");
        printf("Child PID: %d, Parent PID: %d\n", getpid(), getppid());
        exit(0);
    } else {
        wait(NULL);
        printf("In Parent Process:\n");
        printf("Parent PID: %d\n", getpid());
    }
}

void file_operations() {
    int fd;
    char buffer[100];
    const char *filename = "/tmp/example.txt";
    
    // Open, Write, Close
    fd = open(filename, O_CREAT | O_WRONLY, 0644);
    if (fd < 0) {
        perror("Open failed");
        return;
    }
    write(fd, "Sample text written to file.\n", 29);
    close(fd);
    
    // Open, Read, Close
    fd = open(filename, O_RDONLY);
    if (fd < 0) {
        perror("Open failed");
        return;
    }
    read(fd, buffer, sizeof(buffer) - 1);
    printf("Read from file: %s\n", buffer);
    close(fd);
}

void create_link() {
    const char *filename = "/tmp/example.txt";
    const char *linkname = "/tmp/example_link.txt";
    
    if (link(filename, linkname) == 0) {
        printf("Link created: %s\n", linkname);
    } else {
        perror("Link creation failed");
    }
    
    if (unlink(linkname) == 0) {
        printf("Link removed: %s\n", linkname);
    } else {
        perror("Unlink failed");
    }
}

void create_pipe() {
    int pipe_fd[2];
    pid_t pid;
    char buffer[100];
    char message[] = "Message through pipe.\n";
    
    if (pipe(pipe_fd) == -1) {
        perror("Pipe creation failed");
        return;
    }
    
    pid = fork();
    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    } else if (pid == 0) {
        close(pipe_fd[0]);
        write(pipe_fd[1], message, strlen(message) + 1);
        close(pipe_fd[1]);
        exit(0);
    } else {
        close(pipe_fd[1]);
        read(pipe_fd[0], buffer, sizeof(buffer));
        printf("Received from pipe: %s\n", buffer);
        close(pipe_fd[0]);
        wait(NULL);
    }
}

void create_fifo() {
    const char *fifo_path = "/tmp/my_fifo";
    char buffer[100];
    char message[] = "Message through FIFO.\n";
    int fd;
    
    if (mkfifo(fifo_path, 0666) == -1) {
        perror("FIFO creation failed");
        return;
    }
    printf("FIFO created at %s\n", fifo_path);
    
    pid_t pid = fork();
    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    } else if (pid == 0) {
        fd = open(fifo_path, O_WRONLY);
        write(fd, message, strlen(message) + 1);
        close(fd);
        exit(0);
    } else {
        fd = open(fifo_path, O_RDONLY);
        read(fd, buffer, sizeof(buffer));
        printf("Received from FIFO: %s\n", buffer);
        close(fd);
        wait(NULL);
        unlink(fifo_path);
        printf("FIFO removed\n");
    }
}

void get_process_ids() {
    printf("Current PID: %d\n", getpid());
    printf("Parent PID: %d\n", getppid());
}
