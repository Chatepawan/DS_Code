#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <mpi.h>

int main(int argc, char** argv) {
    int rank, size;
    int current_clock_value;
    int difference;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (rank != 0) {
        // Initialize random clock value
        srand(time(NULL) + rank); // Different seed for each process
        current_clock_value = rand() % 100; // Simulated local clock time
        printf("Node %d: Original clock value: %d\n", rank, current_clock_value);

        // 1. Send current time to master
        MPI_Send(&current_clock_value, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);

        // 2. Receive adjustment value from master
        MPI_Recv(&difference, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        // 3. Adjust clock
        current_clock_value -= difference;
        printf("Node %d: Adjusted clock value: %d\n", rank, current_clock_value);
    }

    MPI_Finalize();
    return 0;
}
