import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object Histogram {

  def main ( args: Array[ String ] ) {
    /* ... */
    val inputFile = args(0)
    
    val conf = new SparkConf().setAppName("Histogram")
    val sc = new SparkContext(conf)
    val r = sc.textFile(inputFile).map( line => { val a = line.split(",")
                                                ("1\t"+a(0).toString, 1) } ).reduceByKey((x,y) => (x+y)).collect().foreach(println)
    val g = sc.textFile(inputFile).map( line => { val a = line.split(",")
                                                ("2\t"+a(1).toString, 1) } ).reduceByKey((x,y) => (x+y)).collect().foreach(println)
    val b = sc.textFile(inputFile).map( line => { val a = line.split(",")
                                                ("3\t"+a(2).toString, 1) } ).reduceByKey((x,y) => (x+y)).collect().foreach(println)

    sc.stop()
  }
}