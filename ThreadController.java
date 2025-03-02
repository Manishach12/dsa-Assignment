import java.util.concurrent.Semaphore;

// NumberPrinter class (unchanged, as per requirement)
class NumberPrinter {
    public void printZero() {
        System.out.print("0");
    }

    public void printEven(int num) {
        System.out.print(num);
    }

    public void printOdd(int num) {
        System.out.print(num);
    }
}

// ThreadController using Semaphores
class ThreadController {
    private final NumberPrinter printer = new NumberPrinter();
    private final int maxNumber;

    private final Semaphore zeroSemaphore = new Semaphore(1); // Start with zero
    private final Semaphore oddSemaphore = new Semaphore(0); // Odd starts locked
    private final Semaphore evenSemaphore = new Semaphore(0); // Even starts locked

    public ThreadController(int n) {
        this.maxNumber = n;
    }

    public void printZero() throws InterruptedException {
        for (int i = 1; i <= maxNumber; i++) {
            zeroSemaphore.acquire(); // Wait for zero turn
            printer.printZero(); // Print 0

            // Signal the corresponding number semaphore
            if (i % 2 == 1) {
                oddSemaphore.release();
            } else {
                evenSemaphore.release();
            }
        }
    }

    public void printOdd() throws InterruptedException {
        for (int i = 1; i <= maxNumber; i += 2) {
            oddSemaphore.acquire(); // Wait for odd turn
            printer.printOdd(i);
            zeroSemaphore.release(); // Signal zero thread
        }
    }

    public void printEven() throws InterruptedException {
        for (int i = 2; i <= maxNumber; i += 2) {
            evenSemaphore.acquire(); // Wait for even turn
            printer.printEven(i);
            zeroSemaphore.release(); // Signal zero thread
        }
    }
}

// Main class to start the threads
class Main {
    public static void main(String[] args) {
        int n = 5; // Maximum number to print
        ThreadController controller = new ThreadController(n);

        Thread zeroThread = new Thread(() -> {
            try {
                controller.printZero();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread oddThread = new Thread(() -> {
            try {
                controller.printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread evenThread = new Thread(() -> {
            try {
                controller.printEven();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Start all threads
        zeroThread.start();
        oddThread.start();
        evenThread.start();

        // Wait for all threads to finish
        try {
            zeroThread.join();
            oddThread.join();
            evenThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
