package collidable;
import counters.HitListener;

/**
 * the interface of all of the hit notifiers.
 * @author Alon Levkovitch
 */
public interface HitNotifier {
        /**
         * add a hit listener to the object.
         * @param hl the hit listener we will add.
         */
        void addHitListener(HitListener hl);
        /**
         * remove a hit listener from the object.
         * @param hl the hit listener we will remove.
         */
        void removeHitListener(HitListener hl);
}
