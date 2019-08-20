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
    
}
