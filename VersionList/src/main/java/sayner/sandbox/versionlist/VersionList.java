package sayner.sandbox.versionlist;

import java.io.Serializable;
import java.util.*;

public class VersionList<E> extends AbstractList<E> implements List<E>, Cloneable, Serializable {

    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA = new Object[0];
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = new Object[0];
    private transient Object[] internal = {};
    private int size;
    private static final int MAX_ARRAY_SIZE = 2147483639;

    private List<Modification<E>> modifications = new ArrayList<>();

    //
    // Конструкторы
    //

    public VersionList() {
        this.internal = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public VersionList(int initialCapacity) {

        if (initialCapacity > 0) {
            this.internal = new Object[initialCapacity];
        } else {
            if (initialCapacity != 0) {
                throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
            }

            this.internal = EMPTY_ELEMENTDATA;
        }
    }

    public VersionList(Collection<? extends E> collection) {

        this.internal = collection.toArray();
        if ((this.size = this.internal.length) != 0) {
            if (this.internal.getClass() != Object[].class) {
                this.internal = Arrays.copyOf(this.internal, this.size, Object[].class);
            }
        } else {
            this.internal = EMPTY_ELEMENTDATA;
        }
    }

    //
    // private methods
    //

    private void checkForComodification(int expectedModCount) {

        if (this.modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    private boolean equalsVersionList(VersionList<?> other) {

        int otherModCount = other.modCount;
        int s = this.size;
        boolean equal;
        if (equal = s == other.size) {
            Object[] otherEs = other.internal;
            Object[] es = this.internal;
            if (s > es.length || s > otherEs.length) {
                throw new ConcurrentModificationException();
            }

            for (int i = 0; i < s; ++i) {
                if (!Objects.equals(es[i], otherEs[i])) {
                    equal = false;
                    break;
                }
            }
        }

        other.checkForComodification(otherModCount);
        return equal;
    }

    private boolean equalsRange(List<?> other, int from, int to) {

        Object[] es = this.internal;
        if (to > es.length) {
            throw new ConcurrentModificationException();
        } else {
            Iterator oit;
            for (oit = other.iterator(); from < to; ++from) {
                if (!oit.hasNext() || !Objects.equals(es[from], oit.next())) {
                    return false;
                }
            }

            return !oit.hasNext();
        }
    }

    //
    // Capacity
    //

    /**
     * Отсечка памяти
     * Метод бессмысленен, так как провоцирует ошибку java.lang.ArrayIndexOutOfBoundsException
     * В ином же случае OutOfMemoryException, так как неизвество наверняка сколько занимает памяти Object и сколько памяти доступно
     *
     * @param minCapacity
     * @return кол-во элементов, а много не вернёт
     */
    @Deprecated
    private static int hugeCapacity(int minCapacity) {

        if (minCapacity < 0) {
            throw new OutOfMemoryError("Попытка выделить отрицательное значение памяти");
        } else {
            return minCapacity > 2147483639 ? 2147483647 : 2147483639;
        }
    }

    /**
     * Вычисление нового объёма памяти массива
     *
     * @param minCapacity
     * @return
     */
    @Deprecated
    private int newCapacity(int minCapacity) {

        int oldCapacity = this.internal.length; // сколько было
        int newCapacity = oldCapacity + (oldCapacity >> 1); // Чем больше элементов добавляется, тем большими скачками памяти выделяется
        // Если минимум надо больше, чем выделили
        if (newCapacity - minCapacity <= 0) {
            if (this.internal == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
                return Math.max(10, minCapacity);
            } else if (minCapacity < 0) {
                throw new OutOfMemoryError("Попытка выделить отрицательное значение памяти");
            } else {
                return minCapacity; // А хрен с ним, даём сколько надо
            }
        } else {
            return newCapacity - 2147483639 <= 0 ? newCapacity : hugeCapacity(minCapacity);
        }
    }

    /**
     * Выделение памяти
     *
     * @param minCapacity
     * @return
     */
    private Object[] grow(int minCapacity) {
        // Расширяем массив
        return this.internal = Arrays.copyOf(this.internal, this.newCapacity(minCapacity));
    }

    private Object[] grow() {
        return this.grow(this.size + 1);
    }

    /**
     * Конечка по пути к добавлению элемента
     *
     * @param element      новый элемент
     * @param thisInternal текущий массив передаётся сюда те
     * @param size
     */
    private void add(E element, Object[] thisInternal, int size) {

        // Простым языком:
        // Если не собираемся ничего обновлять, то + в размере (добавляем новый)
        if (size == thisInternal.length) {
            thisInternal = this.grow();
        }

        thisInternal[size] = element; // Ставим новый элемент на +1 место

        this.size = size + 1; // Индексируем
    }

    private void rangeCheckForAdd(int index) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + this.size;
    }

    /**
     * Простой get
     *
     * @param index
     * @return
     */
    private E internal(int index) {
        return (E) this.internal[index];
    }

    /**
     * Удаляшка
     *
     * @param array Массив, из которого необходи удалить элемент
     * @param index Индекс элемента
     */
    private void fastRemove(Object[] array, int index) {

        ++this.modCount;
        int newSize;

        if ((newSize = this.size - 1) > index) {
            // "склеить" массив "внахлёст"
            System.arraycopy(array, index + 1, array, index, newSize - index);
        }

        array[this.size = newSize] = null; // на место size присвоить null
    }

    //
    // public methods
    //

    public Object[] toArray() {
        return Arrays.copyOf(this.internal, this.size);
    }

    public <T> T[] toArray(T[] a) {
        if (a.length < this.size) {
            return (T[]) Arrays.copyOf(this.internal, this.size, a.getClass());
        } else {
            System.arraycopy(this.internal, 0, a, 0, this.size);
            if (a.length > this.size) {
                a[this.size] = null;
            }

            return a;
        }
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        } else if (!(other instanceof List)) {
            return false;
        } else {
            int expectedModCount = this.modCount;
            boolean equal = other.getClass() == VersionList.class ? this.equalsVersionList((VersionList<?>) other) : this.equalsRange((List) other, 0, this.size);
            this.checkForComodification(expectedModCount);
            return equal;
        }
    }


    @Override
    public Iterator<E> iterator() {
        return this.listIterator();
    }

    @Override
    public int size() {
        return this.size;
    }

    /**
     * Добавить в конец
     *
     * @param collection - то, что добавляем
     * @return ну, как, добавилось?
     */
    public boolean addAll(Collection<? extends E> collection) {

        // массив
        Object[] arrayCollection = collection.toArray();
        ++this.modCount;
        int numNew = arrayCollection.length; // количество добавляемых элементов

        if (numNew == 0) {
            return false;
        } else {

            Object[] elementData; // текущий массив сюда потом перезапишем
            int size; // а сюда его размер запишем

            // если количество вновь добавляемых превышает выделенную память (в количестве элементов),
            // то есть, добавляем больше элементов, чем можем себе позволить
            if (numNew > (elementData = this.internal).length - (size = this.size)) {
                // было бы неплохо добавить памяти
                elementData = this.grow(size + numNew);
            }

            // ctrl + C  &  ctrl + V
            // size => в конец добавляем
            // С 0-го жлемента скопировать numNew элементов arrayCollection'а в elementData, поставив их на size место
            System.arraycopy(arrayCollection, 0, elementData, size, numNew);
            this.size = size + numNew; // индексируем изменения
            return true;
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {

        this.rangeCheckForAdd(index); // Попал, не?
        Object[] arrayCollection = collection.toArray();
        ++this.modCount;
        int numNew = arrayCollection.length; // Сколько добавим

        if (numNew == 0) {
            return false;
        } else {

            Object[] elementData;
            int size;

            // если добавляем больше элементов, чем можем себе позволить ...
            if (numNew > (elementData = this.internal).length - (size = this.size)) {
                elementData = this.grow(size + numNew);
            }

            int numMoved = size - index; // Куда надо добавить
            // Не имеет смысла что-то там сдвигать, если вставка идёт в конец
            if (numMoved > 0) {
                // Надо разорвать массив
                // index - откуда, numNew - сколько, index + numNew - откуда вставлять, numMoved - сколько скопировать
                // скопировать надо ровно столько, насколько массив сместился
                // разрыв сделать равно настолько, сколько элементов будет вставлено
                System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
            }

            // С нуля, то есть целиком arrayCollection вставить в elementData, начиная с index пришло numNew элементов, получите и распишитесь
            System.arraycopy(arrayCollection, 0, elementData, index, numNew);
            this.size = size + numNew; // подпись
            return true;
        }
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, this.size);
        return this.internal(index);
    }

    /**
     * Вернёт старое значение, что очень удобно
     * встроена проверка существования индекса
     *
     * @param index   на это место встанет
     * @param element новый элемент
     * @return старое значение
     */
    @Override
    public E set(int index, E element) {

        Objects.checkIndex(index, this.size);
        E oldValue = this.internal(index);
        this.internal[index] = element;
        return oldValue;
    }

    public boolean add(E element) {

        ++this.modCount;
        this.add(element, this.internal, this.size);
        return true;
    }

    @Override
    public void add(int index, E element) {

        this.rangeCheckForAdd(index);
        ++this.modCount;
        int s;
        Object[] elementData;
        if ((s = this.size) == (elementData = this.internal).length) {
            elementData = this.grow();
        }

        System.arraycopy(elementData, index, elementData, index + 1, s - index);
        elementData[index] = element;
        this.size = s + 1;

        // Фиксация изменений
        Modification<E> eModification=new Modification<>();
    }

    @Override
    public E remove(int index) {

        Objects.checkIndex(index, this.size); // проверить
        Object[] es = this.internal; // скопировать
        E oldValue = (E) es[index]; // раздобыть старое значение
        this.fastRemove(es, index); // стереть
        return oldValue; // профит
    }

    @Override
    public int indexOf(Object o) {
        return this.indexOfRange(o, 0, this.size);
    }

    int indexOfRange(Object o, int start, int end) {

        Object[] es = this.internal;
        int i;
        if (o == null) {
            for (i = start; i < end; ++i) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (i = start; i < end; ++i) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.lastIndexOfRange(o, 0, this.size);
    }

    int lastIndexOfRange(Object o, int start, int end) {

        Object[] es = this.internal;
        int i;
        if (o == null) {
            for (i = end - 1; i >= start; --i) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (i = end - 1; i >= start; --i) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return this.listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(final int index) {

        this.rangeCheckForAdd(index);
        //return new VersionList<>().ListItr(index);
        return null;
    }

    /**
     * Простая реализация подсписка
     *
     * @param fromIndex
     * @param toIndex
     * @return
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {

        this.rangeCheckForAdd(fromIndex - 1);
        this.rangeCheckForAdd(toIndex - 1);

        List<E> sublist = new VersionList<>();
        for (int i = fromIndex; i <= toIndex; i++) {

            sublist.add(this.get(i));
        }

        return sublist;
    }
}
