package sayner.sandbox.versionlist;

import java.util.List;

public interface VersionalList<E> extends List<E> {

    String getLastVersionNumber();

    String getLastFullVersion();

    String getLastVersionDate();

    String getLastVersionTime();

    String getLastFullVersionWithDate();

    List<String> getVersionsList();

    List<E> getVersionalList(String version);

    E getVersionedElement(int index, String version);
}
