package collageify.service.collageify.controller;
import java.util.UUID;
public class UuidController {
    private static final Object lock = new Object();
    public static String generateUUID() {
        synchronized (lock) {
            return UUID.randomUUID().toString();
        }
    }
}