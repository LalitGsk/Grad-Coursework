import org.apache.spark.graphx.{Graph, VertexId}
import org.apache.spark.graphx.util.GraphGenerators
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object GraphComponents {
  def main ( args: Array[String] ) {
    val conf = new SparkConf().setAppName("Graph-Connected-Components");
    val sc = new SparkContext(conf);

    val inputFile = sc.textFile(args(0));

    val rddEdges = inputFile.map( line => { val (node, adjacent) = line.split(",").splitAt(1)
          (node(0).toLong, adjacent.toList.map(_.toLong));

            }).flatMap(nodes => nodes._2.map(n => (nodes._1, n)))
        .map(no => Edge(no._1, no._2, no._1))

    var new_graph = Graph.fromEdges(rddEdges, "defaultProperty")

    val graph: Graph[Long, Long] = new_graph.mapVertices((v_id, _) => v_id)
    val initialMsg = Long.MaxValue
    val maxIterations = 5.toInt
    val activeDirection = EdgeDirection.Either
    val output = graph.pregel(initialMsg, maxIterations, activeDirection) (

    (vid, group, adj) => math.min(group, adj),
    
     triplet => {
      if(triplet.attr > triplet.srcAttr){
        Iterator((triplet.dstId, triplet.srcAttr))
      }
      else if(triplet.dstAttr > triplet.attr){
        Iterator((triplet.dstId, triplet.attr))
      }
      else{
        Iterator.empty;
      }
     },
    
     (x,y) => math.min(x,y)
    )

	output.vertices.map(graph => (graph._2, 1)).reduceByKey((x,y) => (x+y)).sortByKey().collect().foreach(println)
  }
}
