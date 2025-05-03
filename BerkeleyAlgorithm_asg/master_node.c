#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

#define NUM_NODES 4  // Including master (rank 0)

int main(int argc, char** argv) {
    int rank, size;
    int clock_values[NUM_NODES - 1];
    int total_clock_value = 0;
    int average_clock_value;
    int difference;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (rank == 0) {
        // Master Node
        printf("Master: Waiting to receive clock values from nodes...\n");

        // 1. Receive clock values from participating nodes
        for (int i = 1; i < size; i++) {
            MPI_Recv(&clock_values[i - 1], 1, MPI_INT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            printf("Master: Received clock value %d from Node %d\n", clock_values[i - 1], i);
            total_clock_value += clock_values[i - 1];
        }

        // 2. Calculate average clock value
        average_clock_value = total_clock_value / size; // Includes master (assumed 0)
        printf("Master: Calculated average clock value: %d\n", average_clock_value);

        // 3. Send difference to each node
        for (int i = 1; i < size; i++) {
            difference = clock_values[i - 1] - average_clock_value;
            MPI_Send(&difference, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
            printf("Master: Sent adjustment value %d to Node %d\n", -difference, i);
        }
    }

    MPI_Finalize();
    return 0;
}
