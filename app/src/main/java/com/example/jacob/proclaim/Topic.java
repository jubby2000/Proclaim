package com.example.jacob.proclaim;

/**
 * Created by Jacob on 3/1/2016.
 */
public class Topic {
    private String mName;

    public Topic(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Topic)) {
            return false;
        }
        Topic other = (Topic) obj;
        return this.mName.equals(other.mName);
    }

    @Override
    public int hashCode() {
        return mName.hashCode();
    }
    // now override hashCode()
}
