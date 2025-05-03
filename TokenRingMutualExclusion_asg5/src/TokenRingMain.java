public class TokenRingMain {
    public static void main(String[] args) throws InterruptedException {
        int numProcesses = 3;
        TokenRingProcess[] processes = new TokenRingProcess[numProcesses];

        // Create and link processes in a ring
        for (int i = 0; i < numProcesses; i++) {
            processes[i] = new TokenRingProcess(i);
        }
        for (int i = 0; i < numProcesses; i++) {
            processes[i].setNext(processes[(i + 1) % numProcesses]);
        }

        // Start all processes
        for (TokenRingProcess p : processes) {
            p.start();
        }

        // Initially give token to Process 0
        processes[0].receiveToken();

        // Request critical section from other processes
        Thread.sleep(2000);
        processes[1].requestCriticalSection();

        Thread.sleep(4000);
        processes[2].requestCriticalSection();

        Thread.sleep(4000);
        processes[0].requestCriticalSection();
    }
}
