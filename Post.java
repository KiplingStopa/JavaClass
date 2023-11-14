import java.util.ArrayList;

public class Post {
    private String title;
    private String content;
    private Post replyTo;
    private User author;
    private int upvoteCount;
    private int downvoteCount;
    
    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.upvoteCount = 1;
        this.downvoteCount = 0;
    }

    public Post(String content, Post replyTo,User author){
        this.content = content;
        this.replyTo = replyTo;
        this.author = author;
        this.upvoteCount = 1;
        this.downvoteCount = 0;
    }

    public String getTitle(){
        return(title);
    }

    public Post getReplyTo(){
        return(replyTo);
    }

    public User getAuthor(){
        return(author);
    }

    public int getUpvoteCount(){
        return(upvoteCount);
    }

    public int getDownvoteCount(){
        return(downvoteCount);
    }

    public void updateUpvoteCount(boolean isIncrement){
        if (isIncrement == true){
            this.upvoteCount = getUpvoteCount() + 1;
            return;
        }
        this.upvoteCount = getUpvoteCount() - 1;
        return;
    }

    public void updateDownvoteCount(boolean isIncrement){
        if (isIncrement == true){
            this.downvoteCount = getDownvoteCount() + 1;
            return;
        }
        this.downvoteCount = getDownvoteCount() - 1;
        return;
    }
    
    public ArrayList<Post> getThread(){
        ArrayList<Post> temp = new ArrayList<Post>();
        ArrayList<Post> fin = new ArrayList<Post>();
        Post now = this;
        if (this.getReplyTo()==null){
            temp.add(this);
            return(temp);
        }
        while (now.getReplyTo()!=null){
            temp.add(now);
            now = now.getReplyTo();
        }
        temp.add(now);
        for (int i = temp.size()-1;i>-1;i--){
            fin.add(temp.get(i));
        }
        return(fin);
    }
    

    public String toString(){
        String TO_STRING_POST_FORMAT = "[%d|%d]\t%s\n\t%s";
        String TO_STRING_COMMENT_FORMAT = "[%d|%d]\t%s";
        if(getReplyTo()==null){
            return(String.format(TO_STRING_POST_FORMAT,
            upvoteCount,downvoteCount,title,content));

        }
        return(String.format(TO_STRING_COMMENT_FORMAT,
        upvoteCount,downvoteCount,content));
    }
}