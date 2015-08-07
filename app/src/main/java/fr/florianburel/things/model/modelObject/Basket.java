package fr.florianburel.things.model.modelObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fl0 on 04/08/15.
 */
public class Basket<T> {

    private final HashMap<T, Integer> content = new HashMap<T, Integer>();

    public void add(T item)
    {
        // verifier produit present
        if(content.containsKey(item))
        {
            // si oui : +1 qte
            int qt = content.get(item);
            content.remove(item);
            content.put(item, qt + 1);
        }
        else
        {
            // sinon ajouter pdt avec qte = 1
            content.put(item, 1);
        }

    }

    public void remove(T item)
    {
        if(content.containsKey(item) && content.get(item) > 1)
        {
            int qte = content.get(item);
            content.remove(item);
            content.put(item, qte - 1);
        }
        else
        {
            content.remove(item);
        }
    }

    public int size()
    {
        int count = 0;

        for(T key : content.keySet())
        {
             count += content.get(key);
        }
        return count;
    }

    public ArrayList<T> toArray()
    {
        ArrayList<T> result = new ArrayList<T>();

        for(T key : content.keySet())
        {
            int qte = content.get(key);

            for(int i = 0 ; i < qte ; i++)
            {
                result.add(key);
            }
        }

        return result;
    }

    public HashMap<T, Integer> getContent() {
        return new HashMap<T, Integer>(content);
    }
}
