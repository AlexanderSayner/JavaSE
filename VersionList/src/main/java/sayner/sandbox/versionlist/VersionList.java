package sayner.sandbox.versionlist;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * итератор не работает
 *
 * @param <E>
 */
public class VersionList<E> extends AbstractList<E> implements VersionalList<E>, List<E>, Cloneable, Serializable {

    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA = new Object[0];
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = new Object[0];
    private transient Object[] internal = {};
    private int size;
    private static final int MAX_ARRAY_SIZE = 2147483639;

    private final List<Modification<E>> modifications;

    //
    // Конструкторы
    //

    public VersionList() {
        this.internal = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
        this.modifications = initiateVersionalList();
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

        this.modifications = initiateVersionalList();
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

        // КОСТЫЛЬ !!!
        this.modifications = initiateVersionalList();
    }

    //
    // private methods
    //

    /**
     * Создают первую, инициализирующую версию
     *
     * @return список, с первой версией
     */
    private LinkedList<Modification<E>> initiateVersionalList() {

        Version firstVersion = new Version(LocalDateTime.now());

        Map<Action, List<E>> actionListMap = new HashMap<>();
        actionListMap.put(Action.Initiate, null);

        Modification<E> initialModification = new Modification<E>(
                firstVersion,
                actionListMap,
                -1,
                -1
        );

        LinkedList<Modification<E>> modifications = new LinkedList<>();
        modifications.add(initialModification);

        return modifications;
    }

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

    // ====================
    // = Версионнсть списка
    // ====================

    /**
     * Версия последнего изменения
     *
     * @return
     */
    private Version getLastVersion() {

        // Преобразования не рискованны, так как все объекты цели жёстко инициализируются во время создания класса
        return ((Modification<E>) ((LinkedList) this.modifications).getLast()).getVersion();
    }

    /**
     * Сохраняет изменение
     *
     * @param actionListMap объекты и совершённые действия над ними
     * @param begin         индекс, начиная с которого все объекты бли изменены
     * @param end           конец интервала изменений
     */
    private void createNextVersion(Map<Action, List<E>> actionListMap, Integer begin, Integer end) {

        Modification<E> modification = new Modification<>(
                getLastVersion().nextVersion(LocalDateTime.now()),
                actionListMap,
                begin,
                end
        );

        this.modifications.add(modification);
    }

    /**
     * Чтобы удобно было
     *
     * @param actionListMap
     * @param index
     */
    private void createNextVersion(Map<Action, List<E>> actionListMap, Integer index) {
        createNextVersion(actionListMap, index, index);
    }

    /**
     * Сохраняет изменения списка
     *
     * @param index   Индекс элемента, куда его положили
     * @param element Сам объект
     */
    private void versionalAdd(Integer index, E element) {

        Map<Action, List<E>> actionListMap = new HashMap<>();

        List<E> list = new ArrayList<>();
        list.add(element);

        actionListMap.put(Action.Created, list);

        createNextVersion(actionListMap, index, index);
    }

    // =========================================================
    //
    // public methods
    //
    // =========================================================

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

        final Integer currentSize = this.size;

        ++this.modCount;
        this.add(element, this.internal, currentSize);

        versionalAdd(currentSize, element);

        return true;
    }

    @Override
    public void add(final int index, E element) {

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

        // Версионный функционал
        versionalAdd(index, element);
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

    @Override
    public String getLastVersionNumber() {
        return getLastVersion().getNumber().toString();
    }

    @Override
    public String getLastFullVersion() {
        return String.format("%s.%s", getLastVersion().getGeneration().getGeneration().toString(), getLastVersionNumber());
    }

    @Override
    public String getLastVersionDate() {
        return String.format("%s", getLastVersion().getLocalDateTime().toLocalDate());
    }

    @Override
    public String getLastVersionTime() {
        return String.format("%s", getLastVersion().getLocalDateTime().toLocalTime());
    }

    @Override
    public String getLastFullVersionWithDate() {
        return String.format("Полная информация о последней версии: %s", getLastVersion().toString());
    }

    @Override
    public List<String> getVersionList() {

        List<String> journal = new ArrayList<>();
        journal.add("Список всех созданных версий:");

        for (Modification<E> modification : this.modifications) {
            journal.add(String.format("Number: %s.%s; date and time: %s.",
                    modification.getVersion().getGeneration().getGeneration().toString(), modification.getVersion().getNumber(), modification.getVersion().getLocalDateTime().toString()));
        }

        return journal;
    }

    @Override
    public E getVersionedElement(int index, String version) {

    }
}
