package agh.cs.project.basics;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    public String toString(){
        return "(" + this.x +"," + this.y + ")";
    }
    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }
    public Vector2d upperRight(Vector2d other){
        int one = this.x;
        int two = this.y;
        if(this.x < other.x) one = other.x;
        if(this.y < other.y) two = other.y;
        return new Vector2d(one, two);
    }

    public Vector2d lowerLeft(Vector2d other){
        int one = this.x;
        int two = this.y;
        if(this.x > other.x) one = other.x;
        if(this.y > other.y) two = other.y;
        return new Vector2d(one, two);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d((this.x + other.x), (this.y + other.y));
    }

    public boolean isInArea(Vector2d lowerLeft, Vector2d upperRight){
        if(lowerLeft.x <= this.x && upperRight.x >= this.x && lowerLeft.y <= this.y && upperRight.y >= this.y){
            return true;
        }
        return false;
    }

    public Vector2d getCorrectPosition(Vector2d lowerLeft, Vector2d upperRight){
        //Vector2d resultantthis = this;
        if(this.x < lowerLeft.x && this.y < lowerLeft.y){
            return new Vector2d(this.x + upperRight.x + 1, this.y + upperRight.y + 1);
        }
        else if(this.x > upperRight.x && this.y < lowerLeft.y){
            return new Vector2d(this.x - upperRight.x - 1 , this.y + upperRight.y + 1);
        }
        else if(this.x < lowerLeft.x && this.y > upperRight.y){
            return new Vector2d(this.x + upperRight.x + 1, this.y - upperRight.y - 1);
        }
        else if(this.x > upperRight.x && this.y > upperRight.y){
            return new Vector2d(this.x - upperRight.x - 1, this.y - upperRight.y - 1);
        }
        else if(this.x < lowerLeft.x){
            return new Vector2d(this.x + upperRight.x + 1, this.y);
        }
        else if(this.x > upperRight.x){
            return new Vector2d(this.x - upperRight.x - 1, this.y);
        }
        else if(this.y < lowerLeft.y){
            return new Vector2d(this.x, this.y + upperRight.y + 1);
        }
        else if(this.y > upperRight.y){
            return new Vector2d(this.x , this.y - upperRight.y - 1);
        }
        return this;
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x,this.y - other.y);
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    public Vector2d opposite(){
        int one = -this.x;
        int two = -this.y;
        return new Vector2d(one, two);
    }
}