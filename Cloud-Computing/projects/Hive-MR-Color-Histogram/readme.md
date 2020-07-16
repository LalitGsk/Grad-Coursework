Develop your project
If you'd prefer, you may use your laptop to develop your program and then test it and run it on Comet.

To install Hive and the project:

cd
wget https://downloads.apache.org/hive/stable-2/apache-hive-2.3.7-bin.tar.gz
tar xfz apache-hive-2.3.7-bin.tar.gz
wget http://lambda.uta.edu/cse6331/project7.tgz
tar xfz project7.tgz
Every time you login, you need to execute these:
export HIVE_HOME=$HOME/apache-hive-2.3.7-bin
export HADOOP_HOME=$HOME/hadoop-2.6.5
export PATH=$HIVE_HOME/bin:$PATH
export HIVE_OPTS="--hiveconf mapreduce.framework.name=local --hiveconf fs.default.name=file://$HOME --hiveconf hive.metastore.warehouse.dir=file://$HOME/warehouse --hiveconf javax.jdo.option.ConnectionURL=jdbc:derby:;databaseName=/$HOME/metastore_db;create=true"
You also need to create an empty metastore database first (this must be done only once):
cd
rm -rf metastore_db  warehouse
schematool -dbType derby -initSchema
Then, to evaluate Hive commands interactively, do:
hive
Go to project7/example and look at the join.hql example. You can run it in local mode (after you setup your PATH) using:
hive -f join.hql
