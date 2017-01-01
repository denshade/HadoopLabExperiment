cd /home/hduser/
wget http://archive.apache.org/dist/hive/hive-2.1.0/apache-hive-2.1.0-bin.tar.gz
tar -xzf apache-hive-2.1.0-bin.tar.gz
sudo mv /home/hduser/apache-hive-2.1.0-bin/ /usr/local/hive


# as hduser
/usr/local/hadoop/sbin/start-all.sh
hdfs dfs -mkdir -p /user/hive/warehouse
hdfs dfs -mkdir /tmp

hdfs dfs -chmod g+w /user/hive/warehouse
hdfs dfs -chmod g+w /tmp
#mkdir: Call From precise64/127.0.1.1 to localhost:54310 failed on connection exception: java.net.ConnectException: Connection refused; For more details see:  http://wiki.apache.org/hadoop/ConnectionRefused
bin/schematool -initSchema -dbType derby
