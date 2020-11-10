package utils;

import java.util.ArrayList;

public class Utils {

    public static <T> ArrayList<T> getFilledList(int size, T value) {
        ArrayList<T> list = new ArrayList<>();
        for(int i = 0; i < size; ++i) {
            list.add(value);
        }
        return list;
    }
}
