# Graph Processing using Pregel on Spark GraphX
The purpose of this project is to develop a graph analysis program using Map-Reduce.

Project Description
An undirected graph is represented in the input text file using one line per graph vertex. For example, the line

1,2,3,4,5,6,7
represents the vertex with ID 1, which is connected to the vertices with IDs 2, 3, 4, 5, 6, and 7. For example, the following graph:

is represented in the input file as follows:
3,2,1
2,4,3
1,3,4,6
5,6
6,5,7,1
0,8,9
4,2,1
8,0
9,0
7,6

The program finds the connected components of any undirected graph and prints the size of these connected components. A connected component of a graph is a subgraph of the graph in which there is a path from any two vertices in the subgraph.
For the above graph, there are two connected components: one 0,8,9 and another 1,2,3,4,5,6,7. Your program should print the sizes of these connected components: 3 and 7.

The graph can be represented as RDD[ ( Long, Long, List[Long] ) ], where the first Long is the graph node ID, the second Long is the group that this vertex belongs to (initially, equal to the node ID), and the List[Long] is the adjacent list (the IDs of the neighbors).

### The following pseudo-code finds the connected components using Pregel:

1. Read the input graph and construct the RDD of edges
2. Use the graph builder Graph.fromEdges to construct a Graph from the RDD of edges
3. Access the VertexRDD and change the value of each vertex to be the vertex ID (initial group number)
4. Call the Graph.pregel method in the GraphX Pregel API to find the connected components. For each vertex, this method changes its group number to the minimum group number of its neighbors (if it is less than its current group number)
5. Group the graph vertices by their group number and print the group sizes.


### Setting up your Project  
Login into Comet and download and untar project8:  

wget http://lambda.uta.edu/cse6331/project8.tgz  
tar xfz project8.tgz  
chmod -R g-wrx,o-wrx project8  
Look at the example example/src/main/scala/SSSPExample.scala  

### Documentation
You can learn more about GraphX at:

- https://spark.apache.org/docs/latest/graphx-programming-guide.html
- https://spark.apache.org/docs/latest/graphx-programming-guide.html#pregel-api
- https://spark.apache.org/docs/latest/graphx-programming-guide.html#graph-builders
