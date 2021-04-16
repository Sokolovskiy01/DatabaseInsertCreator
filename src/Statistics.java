public class Statistics {

    private long mongoDBInsertTime; // in nanosecond
    private long postgreSQLInsertTime; // in nanosecond

    public Statistics(){
        this.mongoDBInsertTime = 0;
        this.postgreSQLInsertTime = 0;
    }

    public void print() {
        System.out.println("MongoDB records insertion took " + this.mongoDBInsertTime + " nanoseconds (" + this.postgreSQLInsertTime / 100000 + " ms)");
        System.out.println("PostgreSQL records insertion took " + this.postgreSQLInsertTime + " nanoseconds (" + this.postgreSQLInsertTime / 100000 + " ms)");
        if(this.mongoDBInsertTime > this.postgreSQLInsertTime) System.out.println("PostgreSQL faster");
        else System.out.println("MongoDB faster");
    }

    public long getMongoDBInsertTime() { return this.mongoDBInsertTime; }
    public long getPostgreSQLInsertTime() { return this.postgreSQLInsertTime; }

    public void addMongoDBInsertTime(long nanosecond) { this.mongoDBInsertTime += nanosecond; }
    public void addPostgreSQLInsertTime(long nanosecond) { this.postgreSQLInsertTime += nanosecond; }
}
