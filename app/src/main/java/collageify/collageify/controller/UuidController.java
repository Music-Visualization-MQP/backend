package collageify.collageify.controller;
import java.util.UUID;
public class UuidController {
    private static final Object lock = new Object();
    public static UUID generateUUID() {
        synchronized (lock) {
            return UUID.randomUUID();
        }
    }
}