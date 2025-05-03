import java.util.concurrent.atomic.AtomicBoolean;

public class TokenRingProcess extends Thread {
    private int id;
    private TokenRingProcess next;
    private AtomicBoolean hasToken = new AtomicBoolean(false);
    private AtomicBoolean requestCS = new AtomicBoolean(false);

    public TokenRingProcess(int id) {
        this.id = id;
    }

    public void setNext(TokenRingProcess next) {
        this.next = next;
    }

    public void receiveToken() {
        hasToken.set(true);
    }

    public void requestCriticalSection() {
        requestCS.set(true);
    }

    @Override
    public void run() {
        while (true) {
            if (hasToken.get()) {
                if (requestCS.get()) {
                    System.out.println("Process " + id + " ENTERING critical section...");
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                    System.out.println("Process " + id + " EXITING critical section.");
                    requestCS.set(false);
                }
                hasToken.set(false);
                next.receiveToken();
            }

            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }
}
