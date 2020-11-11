package mylinkedlist;

/**
 * 测试 LinkedList 双向链表的使用
 * 增加获取指定索引的节点get方法、移除特定索引节点remove方法,增加在指定位置插入节点insert方法
 * 增加泛型
 *
 * @author 发达的范
 * @date 2020/11/11
 */
public class TestLinkedList02<E> {
    private Node First;//头指针
    private Node Last;//尾指针
    private int size;

    //添加节点
    public void add(E element) {
        Node node = new Node(element);//创建一个新的链表节点
        if (First == null) {//如果是第一个节点
            First = node;//头指针指向这个节点
            Last = node;//尾指针指向这个新添加的节点
        } else {//如果不是第一个节点
            node.Previous = Last;//新添加的节点的头指向上一个节点
//            node.Next = null;
            Last.Next = node;//此处有疑问！！！
            Last = node;//尾指针指向这个新添加的节点
            First.Previous = Last;//头尾相连
        }
        size++;
    }

    //校验传入的索引是否合法
    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new RuntimeException("索引不合法" + index);
        }
    }

    //获取指定索引的节点
    private Node getNode(int index) {
        checkIndex(index);
        Node temp = null;
        if (index <= (size >> 1)) {
            temp = First;
            for (int i = 0; i < index; i++) {
                temp = temp.Next;
            }
        } else {
            temp = Last;
            for (int i = size - 1; i > index; i++) {
                temp = temp.Previous;
            }
        }
        return temp;
    }

    public E getValue(int index) {
        Node temp = getNode(index);
        return (E) temp.element;
    }

    //移除特定索引节点
    public void remove(int index) {
        checkIndex(index);
        Node temp = null;
        if (index != (size - 1) && (index != 0)) {//不是最后一个节点和第一个节点
            temp = getNode(index);//获取指定位置的节点
            Node nodeNext = temp.Next;
            Node nodePrevious = temp.Previous;//借助两个中间节点指针
            nodePrevious.Next = nodeNext;
            nodeNext.Previous = nodePrevious.Next;
        } else if (index == (size - 1)) {//是最后一个节点
            temp = Last.Previous;
            temp.Next = null;
            First.Previous = temp;
        } else {
            temp = First.Next;
            temp.Previous = Last;
            First = temp;
        }
        size--;
    }

    //在指定位置插入节点
    public void insert(int index, E element) {
        checkIndex(index);
        Node nodeInsert = new Node(element);
        if ((index != 0)) {//不是最后一个节点和第一个节点
            Node nodeRight = getNode(index);
            Node nodeLeft = nodeRight.Previous;
            nodeLeft.Next = nodeInsert;
            nodeInsert.Previous = nodeLeft;
            nodeRight.Previous = nodeInsert;
            nodeInsert.Next = nodeRight;//连接新节点
        } else {//是第一个节点
            nodeInsert.Next = First;
            First.Previous = nodeInsert;
            nodeInsert.Previous = Last;
            First = nodeInsert;
        }
        size++;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        Node temp = First;
        while (temp != null) {
            stringBuilder.append(temp.element + ",");
            temp = temp.Next;
        }
        stringBuilder.setCharAt(stringBuilder.length() - 1, ']');
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        TestLinkedList02<String> testLinkedList01 = new TestLinkedList02<>();
        for (int i = 0; i < 5; i++) {
            testLinkedList01.add("a" + i);
        }
        System.out.println(testLinkedList01.getValue(2));
        System.out.println(testLinkedList01);//此处有疑问
        testLinkedList01.remove(0);
        System.out.println(testLinkedList01);
        testLinkedList01.insert(0, "fada");
        System.out.println(testLinkedList01);
        testLinkedList01.insert(4, "fada");
        System.out.println(testLinkedList01);
    }
}
