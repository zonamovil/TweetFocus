package cl.zonamovil.tweetfocus.lib.base;

/**
 * Created by cgall on 17-07-2016.
 */
public interface EventBus {

    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);

}
