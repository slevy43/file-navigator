import java.util.Comparator;
import java.util.Iterator;

public class NLNode<T> {

    // Instance variables
    private NLNode<T> parent;
    private ListNodes<NLNode<T>> children;
    private T data;

    // Constructors
    public NLNode() {
        parent = null;
        data = null;
        children = new ListNodes<NLNode<T>>();}

    public NLNode (T d, NLNode<T> p) {
        parent = p;
        data = d;
        children = new ListNodes<NLNode<T>>();}

    // [parent] Methods
    public void setParent(NLNode<T> p) {
        parent = p;}

    public NLNode<T> getParent() {
        return parent;}

    // [children] Methods
    public void addChild(NLNode<T> newChild) {
        this.children.add(newChild);
        newChild.setParent(this);}

    public Iterator<NLNode<T>> getChildren() {
        return children.getList();}

    public Iterator<NLNode<T>> getChildren(Comparator<NLNode<T>> sorter) {
        return children.sortedList(sorter);}

    // [data] Methods
    public T getData() {
        return data;}

    public void setData(T d) {
        data = d;}

} // class end