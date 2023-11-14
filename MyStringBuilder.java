public class MyStringBuilder{
    private CharNode start;
    private CharNode end;
    private int length;

    public MyStringBuilder(char ch) {
        this.length=0;
        append(ch);
    }

    public MyStringBuilder(String str) {
        if (str == null){
            throw new NullPointerException("str is null");
        }
        if (str == ""){
            this.length=str.length();
            return;
        }
        this.length=0;
        for (int i = 0; i<str.length();i++){
            append(str.charAt(i));
        }
    }

    public MyStringBuilder(MyStringBuilder other) {
        if (other == null){
            throw new NullPointerException("other is null");
        }
        this.length = other.length;
        String str = other.toString();
        this.length=0;
        append(str);
    }

    public int length() {
        return length;
    }

    public MyStringBuilder append(char ch) {
        CharNode currNode = new CharNode(ch);
        if (this.length==0){
            this.start = currNode;
            this.end = currNode;
            this.length++;
            return this;
        }
        this.end.setNext(currNode);
        this.end = currNode;
        this.length++;
        return this;
    }
    public MyStringBuilder append(String str) {
        if (str == null){
            throw new NullPointerException("str is null");
        }
        if (str == ""){
            return this;
        }
        for (int i = 0;i<str.length();i++){
            this.append(str.charAt(i));
        }
        return this;
    }

    public String toString() {
        CharNode currNode = this.start;
        String str = new String("");
        if (this.length == 0){
            return str;
        }
        if (this.length==1){
            str = str + String.valueOf(currNode.getData());
            return str;
        }
        while (currNode.getNext()!=this.end){
            str = str + String.valueOf(currNode.getData());
            currNode = currNode.getNext();
        }
        str = str + String.valueOf(currNode.getData());
        str = str + String.valueOf(end.getData()); 
        return str;
    }
    public String subString(int startIdx) {
        if (startIdx < 0 || startIdx>=this.length){
            throw new IndexOutOfBoundsException(startIdx);
        }
        String str = toString();
        return(str.substring(startIdx));
    }
    public String subString(int startIdx, int endIdx) {
        if (startIdx < 0 || startIdx>=this.length){
            throw new IndexOutOfBoundsException(startIdx);
        }
        if (endIdx < 0 || endIdx>this.length){
            throw new IndexOutOfBoundsException(startIdx);
        }
        if (endIdx == this.length){
            return subString(startIdx);
        }
        String str = toString();
        return(str.substring(startIdx,endIdx));

    }
}