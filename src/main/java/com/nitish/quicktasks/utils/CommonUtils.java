package com.nitish.quicktasks.utils;

import java.util.List;

public class CommonUtils {

    public static <T> List<T> getSubList(List<T> list, int offset, int numberOfElements) {
        int size = list.size();

        if (offset < 0 || offset >= size || numberOfElements < 0) {
            throw new IllegalArgumentException("Invalid offset or number of elements");
        }

        int endIndex = Math.min(offset + numberOfElements, size); // Avoid going beyond the list's bounds
        return list.subList(offset, endIndex);
    }
}
