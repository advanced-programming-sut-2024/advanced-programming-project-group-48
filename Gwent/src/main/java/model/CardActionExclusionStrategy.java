package model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;


public class CardActionExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getDeclaredClass().equals(CardAction.class);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}