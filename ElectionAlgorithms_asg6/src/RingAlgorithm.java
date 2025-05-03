import java.util.*;

class RingProcess {
    int id;
    boolean isActive;

    RingProcess(int id) {
        this.id = id;
        this.isActive = true;
    }
}

public class RingAlgorithm {
    static Scanner sc = new Scanner(System.in);
    static RingProcess[] processes;
    static int n;

    static void startElection(int initiatorIndex) {
        System.out.println("\nElection initiated by process " + processes[initiatorIndex].id);

        List<Integer> electionList = new ArrayList<>();
        int current = initiatorIndex;

        do {
            if (processes[current].isActive) {
                electionList.add(processes[current].id);
                System.out.println("Process " + processes[current].id + " passed election message.");
            }

            current = (current + 1) % n;
        } while (current != initiatorIndex);

        int newCoordinator = Collections.max(electionList);
        System.out.println("New Coordinator is Process " + newCoordinator);

        current = initiatorIndex;
        do {
            if (processes[current].isActive)
                System.out.println("Coordinator message passed to Process " + processes[current].id);
            current = (current + 1) % n;
        } while (current != initiatorIndex);
    }

    public static void main(String[] args) {
        System.out.print("Enter number of processes: ");
        n = sc.nextInt();
        processes = new RingProcess[n];

        System.out.println("Enter Process IDs:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + i + " ID: ");
            int id = sc.nextInt();
            processes[i] = new RingProcess(id);
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Start Election");
            System.out.println("2. Crash Process");
            System.out.println("3. Recover Process");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter initiator index (0 to " + (n - 1) + "): ");
                    int initiator = sc.nextInt();
                    if (processes[initiator].isActive)
                        startElection(initiator);
                    else
                        System.out.println("Process is inactive!");
                    break;

                case 2:
                    System.out.print("Enter process index to crash: ");
                    int crash = sc.nextInt();
                    processes[crash].isActive = false;
                    System.out.println("Process " + processes[crash].id + " crashed.");
                    break;

                case 3:
                    System.out.print("Enter process index to recover: ");
                    int recover = sc.nextInt();
                    processes[recover].isActive = true;
                    System.out.println("Process " + processes[recover].id + " recovered.");
                    break;

                case 4:
                    System.out.println("Exiting...");
                    return;
            }
        }
    }
}
