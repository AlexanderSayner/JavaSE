package sayner.sandbox.versionlist;

import java.util.List;

public interface VersionalList<E> extends List<E> {

    String getLastVersionNumber();

    String getLastFullVersion();

    String getLastVersionDate();

    String getLastVersionTime();

    String getLastFullVersionWithDate();

    List<String> getVersionList();
}
