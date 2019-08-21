package sayner.sandbox.versionlist;

import org.junit.Assert;
import org.junit.Test;

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

}
