import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        threads();
        Runnable run = Main::result;
        Thread thread = new Thread(run);
        thread.join();
        thread.start();
    }

    public static  void threads() {
        for (int i = 0; i < 1000; i++) {
            Runnable runnable = () -> {
                generateRoute("RLRFR", 100);
            };
            new Thread(runnable).start();
        }

    }
    public static  void generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        route.toString();
        int maxSize = 0;
        for (int i = 0; i < route.length(); i++) {
            for (int j = 0; j < route.length(); j++) {
                if (i >= j) {
                    continue;
                }
                boolean found = false;
                for (int k = i; k < j; k++) {
                    if (route.charAt(k) == 'F' || route.charAt(k) == 'L') {
                        found = true;
                        break;
                    }
                }
                if (!found && maxSize < j - i) {
                    maxSize = j - i;

                }
            }
        }
        synchronized (sizeToFreq) {
            int rep = 1;
            if (sizeToFreq.containsKey(maxSize)) {

                sizeToFreq.replace(maxSize, sizeToFreq.get(maxSize) + rep);
            } else {
                sizeToFreq.put(maxSize, rep);

            }
        }

    }
    public static void result () {
        int max = Collections.max(sizeToFreq.values());
        for (int i = 1; i < sizeToFreq.size(); i++){
            if (sizeToFreq.containsKey(i) && sizeToFreq.get(i).equals(max)){
                System.out.println("Самое частое количество повторений "
                        + i + " (встретилось " + max+  " раз)");
                synchronized (sizeToFreq) {
                    sizeToFreq.remove(i);
                    break;
                }
            }
        }
        System.out.println("Другие размеры:");
        for (int i = 1; i < sizeToFreq.size(); i++) {
            if (sizeToFreq.containsKey(i)) {
                System.out.println("- " + i + " (" + sizeToFreq.get(i) + " раз)");

            }
        }
    }
}