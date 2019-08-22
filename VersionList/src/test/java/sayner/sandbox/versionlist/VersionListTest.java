package sayner.sandbox.versionlist;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class VersionListTest {

    @Test
    public void emptyListInitSizeTest() throws NullPointerException {

        List<Object> list = new VersionList<>();
        Assert.assertEquals(0, list.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeInitialCapacityTest() throws IllegalArgumentException {

        List<Object> list = new VersionList<>(-1);
    }

    @Test
    public void cloneEmptyListInitTest() throws NullPointerException {

        List list = new VersionList();
        List listCopy = new VersionList(list);
        Assert.assertTrue(list.equals(listCopy));
    }

    @Test
    public void equalsItselfTest() throws NullPointerException {

        List<Object> list = new VersionList<>();
        Assert.assertEquals(list, list);
    }

    @Test
    public void sizeCapacityTest() throws NullPointerException {

        List list = new VersionList(10);
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void addNewElementTest() throws NullPointerException {

        List<String> list = new VersionList<>();
        list.add("Прив");
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void addIndexAllElementsTest() throws NullPointerException {

        List<String> list1 = new VersionList<>();    //list 1

        list1.add("A");
        list1.add("B");
        list1.add("C");
        list1.add("D");

        List<String> list2 = new VersionList<>();    //list 2

        list2.add("E");
        list2.add("F");

        list1.addAll(2, list2);

        List<String> resultList = new VersionList<>();

        // Менять местами элементы нельзя
        resultList.add("A");
        resultList.add("B");
        resultList.add("E");
        resultList.add("F");
        resultList.add("C");
        resultList.add("D");

        Assert.assertEquals(list1, resultList);
    }

    @Test
    public void getIndexListTest() {

        List<String> list = new VersionList<>();
        list.add("с нуля, с нуля начинается отсчёт");
        Assert.assertEquals("с нуля, с нуля начинается отсчёт", list.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getOutIndexListTest() {

        List<String> list = new VersionList<>();
        list.add("с нуля, с нуля начинается отсчёт");
        String testString = list.get(1);
    }

    @Test
    public void setElementTest() {

        List<String> list = new VersionList<>();
        list.add("Первый");
        list.add("Последний");
        list.add("Левый");
        list.set(2, "Новый");
        Assert.assertEquals("Новый", list.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setOutOfIndexElementTest() {

        List<String> list = new VersionList<>(10);
        list.set(2, "Новый");
    }

    @Test
    public void indexOfElementTest() {

        List<String> list = new VersionList<>();
        list.add("Первый");
        list.add("Последний");
        list.add("Ленивый");
        list.add("Ленивый");

        Assert.assertEquals(2, list.indexOf("Ленивый"));
    }

    @Test
    public void lastIndexOfElementTest() {

        List<String> list = new VersionList<>();
        list.add("Первый");
        list.add("Последний");
        list.add("Ленивый");
        list.add("Ленивый");

        Assert.assertEquals(3, list.lastIndexOf("Ленивый"));
    }

    @Test
    public void getInitVersionTest() {

        VersionalList<String> list = new VersionList<>();
        Assert.assertEquals(2, list.getVersionsList().size());
    }

    @Test
    public void initialLastVersionStringTest() {

        VersionalList<String> list = new VersionList<>();
        Assert.assertEquals("1.0", list.getLastFullVersion());
    }

    @Test
    public void addingVersionTest() {

        VersionalList<String> list = new VersionList<>();
        list.add("Let");
        list.add("it");
        list.add("happen");
        Assert.assertEquals("1.3", list.getLastFullVersion());
    }

    @Test
    public void recoveryVersionalListTest() {

        VersionalList<String> versionalList = new VersionList<>();
        versionalList.add("For");
        versionalList.add("whom");
        versionalList.add("the");
        versionalList.add("bell");
        versionalList.add("tolls");

        int version = 3;

        List<String> list = versionalList.getVersionalList(String.format("1.%d", version));

        for (int i = 0; i < version; i++) {
            String element1 = versionalList.get(i);
            String element2 = list.get(i);

            Assert.assertTrue(element1.equals(element2));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void recoveryVersionalListExceptionTest() {

        VersionalList<String> versionalList = new VersionList<>();
        versionalList.add("For");
        versionalList.add("whom");
        versionalList.add("the");
        versionalList.add("bell");
        versionalList.add("tolls");

        int version = 2;

        List<String> list = versionalList.getVersionalList(String.format("1.%d", version));

        list.get(version);
    }

    @Test
    public void recoveryVersionalLinkedListTest() {

        VersionalList<String> versionalList = new VersionList<>();
        versionalList.add("For");
        versionalList.add("whom");
        versionalList.add("the");
        versionalList.add("bell");
        versionalList.add("tolls");

        int version = 3;

        List<String> list = versionalList.getVersionalList(String.format("1.%d", version), LinkedList.class);

        for (int i = 0; i < version; i++) {
            String element1 = versionalList.get(i);
            String element2 = list.get(i);

            Assert.assertTrue(element1.equals(element2));
        }
    }

    @Test
    public void versionalRemoveTest() {

        VersionalList<String> versionalList = new VersionList<>();
        versionalList.add("The");
        versionalList.add("Void");
        versionalList.remove(0);

        Assert.assertEquals(1, versionalList.size());
    }

    @Test
    public void versionalObjectRemoveTest() {

        VersionalList<String> versionalList = new VersionList<>();
        versionalList.add("The");
        versionalList.add("Void");
        versionalList.remove("The");

        Assert.assertEquals(1, versionalList.size());
    }

    @Test
    public void versionalRemoveRecoveryTest() {

        VersionalList<String> versionalList = new VersionList<>();

        String _the = "The";
        String _void = "Void";

        List<String> sourceList = new ArrayList<>();
        sourceList.add(_the);
        sourceList.add(_void);

        versionalList.add(_the); // v1.1
        versionalList.add(_void); // v1.2
        versionalList.remove(0);

        int version = 2;

        List<String> list = versionalList.getVersionalList(String.format("1.%d", version), LinkedList.class);

        for (int i = 0; i < version; i++) {

            String element1 = sourceList.get(i);
            String element2 = list.get(i);

            Assert.assertTrue(element1.equals(element2));
        }
    }

    @Test
    public void versionalObjectRemoveRecoveryTest() {

        VersionalList<String> versionalList = new VersionList<>();

        String _the = "The";
        String _void = "Void";

        List<String> sourceList = new ArrayList<>();
        sourceList.add(_the);
        sourceList.add(_void);

        versionalList.add(_the); // v1.1
        versionalList.add(_void); // v1.2
        versionalList.remove("The");

        int version = 2;

        List<String> list = versionalList.getVersionalList(String.format("1.%d", version), LinkedList.class);

        for (int i = 0; i < version; i++) {

            String element1 = sourceList.get(i);
            String element2 = list.get(i);

            Assert.assertTrue(element1.equals(element2));
        }
    }

    @Test
    public void getAllBeforeCurrentTimeListByDate() {

        String paralyzed = "Paralyzed";
        String inFlames = "In Flames";
        String siren = "Siren";
        String charms = "Charms";

        List<String> sourceList = new ArrayList<>();
        sourceList.add(paralyzed);
        sourceList.add(inFlames);
        sourceList.add(siren);
        sourceList.add(charms);

        VersionalList<String> versionalList = new VersionList<>();
        versionalList.add(paralyzed); // 1.1
        versionalList.add(inFlames); // 1.2
        versionalList.add(siren); // 1.3
        versionalList.add(charms); // 1.4

        List<String> stringList = versionalList.getVersionalList(LocalDateTime.now());

        int version = 4;

        for (int i = 0; i < version; i++) {

            String element1 = sourceList.get(i);
            String element2 = stringList.get(i);

            Assert.assertTrue(element1.equals(element2));
        }
    }

    @Test
    public void getAllBeforeRandomTimeListByDate() {

        String paralyzed = "Paralyzed";
        String inFlames = "In Flames";
        String siren = "Siren";
        String charms = "Charms";

        List<String> sourceList = new ArrayList<>();
        sourceList.add(paralyzed);
        sourceList.add(inFlames);
        sourceList.add(siren);
        sourceList.add(charms);

        VersionalList<String> versionalList = new VersionList<>();
        versionalList.add(paralyzed); // 1.1
        versionalList.add(inFlames); // 1.2
        LocalDateTime localDateTime = LocalDateTime.now();
        versionalList.add(siren); // 1.3
        versionalList.add(charms); // 1.4

        List<String> stringList = versionalList.getVersionalList(localDateTime);

        int version = 2;

        for (int i = 0; i < version; i++) {

            String element1 = sourceList.get(i);
            String element2 = stringList.get(i);

            Assert.assertTrue(element1.equals(element2));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getAllBeforeRandomTimeListByDateOnException() {

        String paralyzed = "Paralyzed";
        String inFlames = "In Flames";
        String siren = "Siren";
        String charms = "Charms";

        List<String> sourceList = new ArrayList<>();
        sourceList.add(paralyzed);
        sourceList.add(inFlames);
        sourceList.add(siren);
        sourceList.add(charms);

        VersionalList<String> versionalList = new VersionList<>();
        versionalList.add(paralyzed); // 1.1
        versionalList.add(inFlames); // 1.2
        LocalDateTime localDateTime = LocalDateTime.now();
        versionalList.add(siren); // 1.3
        versionalList.add(charms); // 1.4

        List<String> stringList = versionalList.getVersionalList(localDateTime);

        int version = 3;

        for (int i = 0; i < version; i++) {

            String element1 = sourceList.get(i);
            String element2 = stringList.get(i);

            Assert.assertTrue(element1.equals(element2));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getVersionedListByDate() throws InterruptedException {

        String in = "In";
        String pain = "Pain";
        String view = "View";

        List<String> sourceList = new ArrayList<>();
        sourceList.add(in);
        sourceList.add(pain);
        sourceList.add(view);

        VersionalList<String> versionalList = new VersionList<>();
        versionalList.add(in); // v1.1
        versionalList.add(pain); // v1.2
        LocalTime localDateTime=LocalTime.now();
        versionalList.add(view); // v1.3

        List<String> stringList = versionalList.getVersionalList(localDateTime);

        int version = 3;

        for (int i = 0; i < version; i++) {

            String element1 = sourceList.get(i);
            String element2 = stringList.get(i);

            Assert.assertTrue(element1.equals(element2));
        }
    }
}
