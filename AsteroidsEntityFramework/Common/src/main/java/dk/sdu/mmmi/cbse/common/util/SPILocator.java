/**
 * This class is used to locate all implementations of a given interface.
 * And return a list of all implementations.
 *
 * This class is not used anymore since we moved to the ServiceLoader class as it was more simple since it is javas own implementation.
 */
package dk.sdu.mmmi.cbse.common.util;

import java.util.*;

public class SPILocator {

    private static final Map<Class, ServiceLoader> locator = new HashMap<>();

    private SPILocator(){

    }

    /**
     * This method is used to locate all implementations of a given interface.
     * @param gameservices The interface to locate implementations of (e.g. IEntityProcessingService.class).
     * @return A list of all implementations of the given interface.
     * @param <T> The interface to locate implementations of.
     */
    public static <T>List<T> locate(Class<T> gameservices){
        ServiceLoader<T> loadService = locator.get(gameservices);

        boolean printstatus = false;

        if(loadService == null){
            loadService = ServiceLoader.load(gameservices);
            locator.put(gameservices, loadService);
            printstatus = true;
        }

        List<T> serviceList = new ArrayList<>();

        if (loadService != null){
            try{
                for (T item : loadService){
                    serviceList.add(item);
                }
            } catch (ServiceConfigurationError error){
                error.printStackTrace();
            }

        }

        if (printstatus) {
            System.out.println("Found " + serviceList.size() + " implementations for interface: " + gameservices.getName());
        }
        return serviceList;
    }
}
