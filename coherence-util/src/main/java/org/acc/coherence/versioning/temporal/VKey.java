package org.acc.coherence.versioning.temporal;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;
import com.tangosol.io.pof.reflect.SimplePofPath;
import com.tangosol.util.ValueExtractor;
import com.tangosol.util.extractor.PofExtractor;
import com.tangosol.util.extractor.ReflectionExtractor;

import java.io.Serializable;

/**
 * Wrapper type for versioned keys. Note, for testing this supports both Pof and Java serialisation.
 */
@Portable
public class VKey<DomainKey> implements Versioned<DomainKey>, Serializable {
    private static final long serialVersionUID = -294143534112761393L;

    public static final int FIRST_VERSION = 1;
    public static final int VERSION_POF_ID = 1;
    public static final PofExtractor BUSINESS_KEY_POF_EXTRACTOR = new PofExtractor(null, new SimplePofPath(DOMAIN_POF_ID), PofExtractor.KEY);
    public static final ValueExtractor BUSINESS_KEY_JAVA_EXTRACTOR = new ReflectionExtractor("getDomainObject", new Object[0], PofExtractor.KEY);
    public static final PofExtractor VERSION_POF_EXTRACTOR = new PofExtractor(int.class, new SimplePofPath(VERSION_POF_ID), PofExtractor.KEY);
    public static final ValueExtractor VERSION_JAVA_EXTRACTOR = new ReflectionExtractor("getVersion", new Object[0], PofExtractor.KEY);

    @PortableProperty(value = VERSION_POF_ID)
    private int version;

    @PortableProperty(value = DOMAIN_POF_ID)
    private DomainKey domainKey;

    @SuppressWarnings("UnusedDeclaration")  // Used by Coherence
    @Deprecated                             // Only
    public VKey() {
    }

    public VKey(DomainKey domainKey, int version) {
        this.domainKey = domainKey;
        this.version = version;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public DomainKey getDomainObject() {
        return domainKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VKey that = (VKey) o;
        if (version != that.version) return false;
        if (domainKey != null ? !domainKey.equals(that.domainKey) : that.domainKey != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * version + (domainKey != null ? domainKey.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "VKey{domainKey=" + domainKey + ", version=" + version + '}';
    }
}