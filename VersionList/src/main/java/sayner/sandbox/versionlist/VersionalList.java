package sayner.sandbox.versionlist;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface VersionalList<E> extends List<E> {

    String getLastVersionNumber();

    String getLastFullVersion();

    String getLastVersionDate();

    String getLastVersionTime();

    String getLastFullVersionWithDate();

    List<String> getVersionsList();

    List<E> getVersionalList(String version);

    List<E> getVersionalList(String version, Class<? extends List> listClass);

    List<E> getVersionalList(LocalDateTime localDateTime);

    List<E> getVersionalList(LocalDateTime localDateTime, Class<? extends List> listClass);

    List<E> getVersionalList(LocalTime localTime);

    List<E> getVersionalList(LocalTime localTime, Class<? extends List> listClass);

    List<E> getVersionalList(LocalDate localDate);

    List<E> getVersionalList(LocalDate localDate, Class<? extends List> listClass);

    List<E> getVersionalList(Integer hour, Integer minute);

    List<E> getVersionalList(Integer hour, Integer minute, Class<? extends List> listClass);

    E getVersionedElement(int index, String version);
}
