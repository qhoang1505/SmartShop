package Model.Person;

public class ObjectCurrent {
    private static Object ObjectCurrent;

    private ObjectCurrent() {
        // Private constructor to prevent instantiation
    }
    public static Object getObjectCurrent() {
        return ObjectCurrent;
    }

    public static void setObjectCurrent(Object oc) {
        ObjectCurrent = oc;
    }
}
